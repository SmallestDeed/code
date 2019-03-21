package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.act3.service.LuckyWheelRegistrationService;
import com.sandu.api.base.common.cache.RedisKeyConstans;
import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.base.common.util.DateUtil;
import com.sandu.api.base.common.util.GsonUtil;
import com.sandu.api.base.service.BaseCompanyMiniProgramConfigService;
import com.sandu.api.base.service.RedisService;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.springFestivalActivity.model.WxRedPacketSummary;
import com.sandu.api.springFestivalActivity.model.WxSigninSummary;
import com.sandu.api.springFestivalActivity.model.WxSpringActivity;
import com.sandu.api.springFestivalActivity.model.WxUserSignin;
import com.sandu.api.springFestivalActivity.output.DateVo;
import com.sandu.api.springFestivalActivity.output.SignInVo;
import com.sandu.api.springFestivalActivity.output.UserSignInRecordVo;
import com.sandu.api.springFestivalActivity.service.WxRedPacketSummaryService;
import com.sandu.api.springFestivalActivity.service.WxSigninSummaryService;
import com.sandu.api.springFestivalActivity.service.WxSpringActivityService;
import com.sandu.api.springFestivalActivity.service.WxUserSigninService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.service.springFestivalActivity.dao.WxUserSigninMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service("wxUserSigninService")
public class WxUserSigninServiceImpl implements WxUserSigninService {

    private static final String CLASS_LOG_PREFIX = "【签到记录service】";
    private static final String SIGN_IN_ACTIVITY_PREFIX = "spring:signIn:";
    private static final Integer RED_PACKET_SIGN_IN_COUNT = 3;
    @Autowired
    private WxUserSigninMapper wxUserSigninMapper;
    @Autowired
    private WxSpringActivityService wxSpringActivityService;
    @Autowired
    private WxRedPacketSummaryService wxRedPacketSummaryService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WxSigninSummaryService wxSigninSummaryService;
    @Autowired
    private LuckyWheelRegistrationService luckyWheelRegistrationService;
    @Resource(name = "wangWangSysUserService")
    private SysUserService sysUserService;
    @Resource(name = "miniProPay")
    private PayService payService;
    @Autowired
    private TemplateMsgService templateMsgService;
    @Autowired
    private BaseCompanyMiniProgramConfigService baseCompanyMiniProgramConfigService;

    /**
     * 获取用户签到记录列表
     * 1.获取活动时间段（是否需要判断活动是否结束？我想的是在签到的时候判断，活动列表一直会显示）
     * 2.获取用户签到记录
     * 3.组装所有数据
     */
    @Override
    public List<UserSignInRecordVo> getUserSignInRecordList(long activityId, Integer userId) throws BizException {
        if (0L == activityId) {
            throw new BizException("获取用户签到记录，参数activityId为空");
        }
        if (null == userId || 0 == userId) {
            throw new BizException("获取用户签到记录，参数userId为空");
        }
        log.info(CLASS_LOG_PREFIX + "获取用户签到记录，activityId={},userId={}", activityId, userId);
        // 获取签到活动开始和结束时间
        DateVo signInDate = this.getSignInDate(activityId);
        //组装签到活动的每一天的签到记录集合
        List<UserSignInRecordVo> allSignInList = this.getActivitySignInList(signInDate);
        //获取用户已经签到天数的集合
        List<WxUserSignin> userSigninList = wxUserSigninMapper.getSignInListByUserId(activityId, userId.longValue());
        //把已经签到的数据放入返回的签到集合中
        byte signIn = 1;
        if (!CollectionUtils.isEmpty(userSigninList)) {
            for (WxUserSignin wxUserSignin : userSigninList) {
                for (UserSignInRecordVo daySignIn : allSignInList) {
                    if (DateUtils.isSameDay(wxUserSignin.getSigninDate(), daySignIn.getSigninDate())) {
                        BeanUtils.copyProperties(wxUserSignin, daySignIn);
                        daySignIn.setIsSignIn(signIn);
                    }
                }
            }
        }
        return allSignInList;
    }

    // @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SignInVo signIn2(Long activityId, SysUser sysUser) throws BizException {
        if (null == sysUser || null == sysUser.getId() || 0 >= sysUser.getId()) {
            throw new BizException("请登录");
        }
        if (null == activityId || 0 >= activityId) {
            throw new BizException("活动id为空");
        }
        Integer userId = sysUser.getId().intValue();
        log.info(CLASS_LOG_PREFIX + "签到活动，activityId={}，userId={}", activityId, userId);

        WxSpringActivity wxSpringActivity = wxSpringActivityService.selectByPrimaryKey(activityId);
        // 1.获取活动时间段，判断活动是否结束
        DateVo signInDate = this.getSignInDate(activityId);
        Date beginDate = signInDate.getBeginDate();
        Date endDate = signInDate.getEndDate();
        Date now = new Date();
        if (now.before(beginDate)) {
            throw new BizException("活动未开始");
        }
        if (now.after(endDate)) {
            throw new BizException("活动已结束");
        }

        // 2.判断用户今日是否已签到
        WxUserSignin wxUserSignin = WxUserSignin.builder().build();
        wxUserSignin.setUserId(userId.longValue());
        wxUserSignin.setActivityId(activityId);
        wxUserSignin.setSigninDate(now);
        wxUserSignin.setIsDeleted(0);
        wxUserSignin = wxUserSigninMapper.getWxUserSignInSelective(wxUserSignin);
        if (null != wxUserSignin) {
            throw new BizException("今日已经签到过了，明天再来哦~");
        }

        List<String> brpopList = redisService.getBrpopList(RedisKeyConstans.SIGN_IN_ACTIVITY_LIST);
        if (CollectionUtils.isEmpty(brpopList)) {
            throw new BizException("今日红包已领完");
        }
        String json = brpopList.get(1);
        wxUserSignin = GsonUtil.json2Bean(json, WxUserSignin.class);
        wxUserSignin.setUserId(userId.longValue());
        wxUserSignin.setSigninDate(now);
        wxUserSignin.setReceiveStatus(WxUserSignin.ReceviceStatus.RECEIVE_YES);
        wxUserSignin.setReceiveTime(now);
        // 获取用户已签到次数
        Integer signInCount = wxUserSigninMapper.getSignInCountOfUser(activityId, userId);
        Byte haveLotteryChance = WxUserSignin.LotteryChance.LOTTERY_CHANCE_NO;
        int incrLotteryTime = 0;
        if ((signInCount + 1) % RED_PACKET_SIGN_IN_COUNT == 0) {
            haveLotteryChance = WxUserSignin.LotteryChance.LOTTERY_CHANCE_YES;
            incrLotteryTime = 1;
            //转盘添加可抽奖次数
            this.increaseLotteryCount(activityId, sysUser);
            //发送模板消息
            this.sendTemplateMessage(sysUser, activityId, wxSpringActivity.getWxActLuckyWheelId());
        }
        wxUserSignin.setHaveLotteryChance(haveLotteryChance);
        wxUserSignin.setIsLotteryFlag(WxUserSignin.LotteryFlag.LOTTERY_NO);
        wxUserSignin.setModifier(sysUser.getNickName());
        wxUserSignin.setGmtModified(now);
        int result = wxUserSigninMapper.updateByPrimaryKeySelective(wxUserSignin);
        if (result != 1) {
            throw new BizException("签到人数太多，请稍后重试");
        }
        BigDecimal redPacketNum = wxUserSignin.getRedPacketNum();
        // 生成用户签到红包汇总记录
        WxSigninSummary wxSigninSummary = WxSigninSummary.builder().build();
        wxSigninSummary.setActivityId(activityId);
        wxSigninSummary.setUserId(userId.longValue());
        wxSigninSummary.setIsDeleted(0);
        wxSigninSummary = wxSigninSummaryService.getBySelective(wxSigninSummary);
        // if (null == wxSigninSummary) {
        //     //表示第一次签到
        //     wxSigninSummary = WxSigninSummary.builder().build();
        //     wxSigninSummary.setActivityId(activityId);
        //     wxSigninSummary.setUserId(userId.longValue());
        //     wxSigninSummary.setRedPacketNum(redPacketNum);
        //     wxSigninSummary.setSignTimes(1);
        //     wxSigninSummary.setLotteryTimes(0);
        //     wxSigninSummary.setLotteryUseTimes(0);
        //     wxSigninSummary.setLotteryRemainTimes(0);
        //     wxSigninSummary.setCreator(sysUser.getNickName());
        //     wxSigninSummary.setModifier(sysUser.getNickName());
        //     wxSigninSummary.setGmtCreate(now);
        //     wxSigninSummary.setGmtModified(now);
        //     wxSigninSummary.setIsDeleted(0);
        //     result = wxSigninSummaryService.insert(wxSigninSummary);
        //     if (result != 1) {
        //         log.error(CLASS_LOG_PREFIX + "添加当日红包汇总数据失败，wxSigninSummary={}", wxSigninSummary);
        //         throw new BizException("签到人数太多，请稍后重试");
        //     }
        // } else {
        //     //表示之前已经签到过了
        //     wxSigninSummary.setRedPacketNum(wxSigninSummary.getRedPacketNum().add(redPacketNum));
        //     wxSigninSummary.setSignTimes(wxSigninSummary.getSignTimes() + 1);
        //     wxSigninSummary.setLotteryTimes(wxSigninSummary.getLotteryTimes() + incrLotteryTime);
        //     wxSigninSummary.setLotteryRemainTimes(wxSigninSummary.getLotteryRemainTimes() + incrLotteryTime);
        //     result = wxSigninSummaryService.update(wxSigninSummary);
        //     if (result != 1) {
        //         log.error(CLASS_LOG_PREFIX + "修改当日红包汇总数据失败，wxSigninSummary={}", wxSigninSummary);
        //         throw new BizException("签到人数太多，请稍后重试");
        //     }
        // }

        // 6.调用伟文接口发红包
        // new Thread(() -> {
        //     Double moneyAmount = redPacketNum.doubleValue() * 100;
        //     try {
        //         this.doCashAward(sysUser, moneyAmount);
        //     } catch (Exception e) {
        //         log.info(CLASS_LOG_PREFIX + "签到发红包异常，exception={},回滚。。。。", e);
        //         throw new BizException("领取失败");
        //     }
        // }).start();

        //构建返回值
        SignInVo signInVo = new SignInVo();
        signInVo.setRedPacketAmount(redPacketNum);
        signInVo.setHaveLotteryChance(haveLotteryChance);

        return signInVo;
    }

    /**
     * 签到
     * 1.获取活动时间段，判断活动是否结束
     * 2.判断今日红包剩余金额,判断红包总剩余金额,判断用户今日是否已签到
     * 3.随机获取红包金额（除夕当天还要特殊判断，红包更大一点。）
     * 4.插入签到数据
     * 5.修改今日发红包金额数，今日剩余红包数，总红包剩余数
     * 7.调用伟文接口发红包
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SignInVo signIn(Long activityId, SysUser sysUser) throws BizException {
        if (null == sysUser || null == sysUser.getId() || 0 >= sysUser.getId()) {
            throw new BizException("请登录");
        }
        if (null == activityId || 0 >= activityId) {
            throw new BizException("活动id为空");
        }
        Integer userId = sysUser.getId().intValue();
        log.info(CLASS_LOG_PREFIX + "签到活动，activityId={}，userId={}", activityId, userId);

        // 1.获取活动时间段，判断活动是否结束
        DateVo signInDate = this.getSignInDate(activityId);
        Date beginDate = signInDate.getBeginDate();
        Date endDate = signInDate.getEndDate();
        Date now = new Date();
        if (now.before(beginDate)) {
            throw new BizException("活动未开始");
        }
        if (now.after(endDate)) {
            throw new BizException("活动已结束");
        }

        // 2.判断用户今日是否已签到
        WxUserSignin wxUserSignin = WxUserSignin.builder().build();
        wxUserSignin.setUserId(userId.longValue());
        wxUserSignin.setActivityId(activityId);
        wxUserSignin.setSigninDate(now);
        wxUserSignin.setIsDeleted(0);
        wxUserSignin = wxUserSigninMapper.getWxUserSignInSelective(wxUserSignin);
        if (null != wxUserSignin) {
            throw new BizException("今日已经签到过了，明天再来哦~");
        }

        // 3.加分布式锁
        long startTime = System.currentTimeMillis();
        String key = SIGN_IN_ACTIVITY_PREFIX + activityId;
        String value = UUID.randomUUID().toString();
        long time = 2000L;
        while (!redisService.lock(key, value, time)) {
            if (System.currentTimeMillis() - startTime > 2000) {
                throw new BizException("请稍后再试");
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                throw new BizException("请稍后再试");
            }
        }
        WxSpringActivity wxSpringActivity = wxSpringActivityService.selectByPrimaryKey(activityId);
        if (null == wxSpringActivity) {
            throw new BizException("获取活动数据失败，请检查活动id：" + activityId);
        }
        // 4.判断今日红包剩余金额,判断红包总剩余金额,并随机生成红包金额
        BigDecimal redPacketAmount = this.createRandomRedPacketAmount(activityId, wxSpringActivity);

        // 5.插入签到成功的数据
        wxUserSignin = WxUserSignin.builder().build();
        wxUserSignin.setUserId(userId.longValue());
        wxUserSignin.setActivityId(activityId);
        wxUserSignin.setSigninDate(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        wxUserSignin.setSigninDay(calendar.get(Calendar.DAY_OF_MONTH));
        wxUserSignin.setRedPacketNum(redPacketAmount);
        wxUserSignin.setReceiveStatus(WxUserSignin.ReceviceStatus.RECEIVE_YES);
        wxUserSignin.setReceiveTime(now);
        // 获取用户已签到次数
        Integer signInCount = wxUserSigninMapper.getSignInCountOfUser(activityId, userId);
        Byte haveLotteryChance = WxUserSignin.LotteryChance.LOTTERY_CHANCE_NO;
        int incrLotteryTime = 0;
        if ((signInCount + 1) % RED_PACKET_SIGN_IN_COUNT == 0) {
            haveLotteryChance = WxUserSignin.LotteryChance.LOTTERY_CHANCE_YES;
            incrLotteryTime = 1;
            //转盘添加可抽奖次数
            this.increaseLotteryCount(activityId, sysUser);
            //发送模板消息
            this.sendTemplateMessage(sysUser, activityId, wxSpringActivity.getWxActLuckyWheelId());
        }
        wxUserSignin.setHaveLotteryChance(haveLotteryChance);
        wxUserSignin.setIsLotteryFlag(WxUserSignin.LotteryFlag.LOTTERY_NO);
        wxUserSignin.setCreator(sysUser.getNickName());
        wxUserSignin.setModifier(sysUser.getNickName());
        wxUserSignin.setGmtCreate(now);
        wxUserSignin.setGmtModified(now);
        wxUserSignin.setIsDeleted(0);
        int result = wxUserSigninMapper.insertSelective(wxUserSignin);
        if (result != 1) {
            log.error(CLASS_LOG_PREFIX + "插入用户签到数据异常，wxUserSignin={}", wxUserSignin);
            throw new BizException("签到人数太多，请稍后重试");
        }
        // 生成用户签到红包汇总记录
        WxSigninSummary wxSigninSummary = WxSigninSummary.builder().build();
        wxSigninSummary.setActivityId(activityId);
        wxSigninSummary.setUserId(userId.longValue());
        wxSigninSummary.setIsDeleted(0);
        wxSigninSummary = wxSigninSummaryService.getBySelective(wxSigninSummary);
        if (null == wxSigninSummary) {
            //表示第一次签到
            wxSigninSummary = WxSigninSummary.builder().build();
            wxSigninSummary.setActivityId(activityId);
            wxSigninSummary.setUserId(userId.longValue());
            wxSigninSummary.setRedPacketNum(redPacketAmount);
            wxSigninSummary.setSignTimes(1);
            wxSigninSummary.setLotteryTimes(0);
            wxSigninSummary.setLotteryUseTimes(0);
            wxSigninSummary.setLotteryRemainTimes(0);
            wxSigninSummary.setCreator(sysUser.getNickName());
            wxSigninSummary.setModifier(sysUser.getNickName());
            wxSigninSummary.setGmtCreate(now);
            wxSigninSummary.setGmtModified(now);
            wxSigninSummary.setIsDeleted(0);
            result = wxSigninSummaryService.insert(wxSigninSummary);
            if (result != 1) {
                log.error(CLASS_LOG_PREFIX + "添加当日红包汇总数据失败，wxSigninSummary={}", wxSigninSummary);
                throw new BizException("签到人数太多，请稍后重试");
            }
        } else {
            //表示之前已经签到过了
            wxSigninSummary.setRedPacketNum(wxSigninSummary.getRedPacketNum().add(redPacketAmount));
            wxSigninSummary.setSignTimes(wxSigninSummary.getSignTimes() + 1);
            wxSigninSummary.setLotteryTimes(wxSigninSummary.getLotteryTimes() + incrLotteryTime);
            wxSigninSummary.setLotteryRemainTimes(wxSigninSummary.getLotteryRemainTimes() + incrLotteryTime);
            result = wxSigninSummaryService.update(wxSigninSummary);
            if (result != 1) {
                log.error(CLASS_LOG_PREFIX + "修改当日红包汇总数据失败，wxSigninSummary={}", wxSigninSummary);
                throw new BizException("签到人数太多，请稍后重试");
            }
        }
        // 修改每日红包汇总表数据
        WxRedPacketSummary wxRedPacketSummary = wxRedPacketSummaryService.getBySignInDate(activityId, now);
        if (null != wxRedPacketSummary) {
            wxRedPacketSummary.setPreRedPackRemainNum(wxRedPacketSummary.getRedPacketRemainNum());
            wxRedPacketSummary.setRedPacketUseNum(wxRedPacketSummary.getRedPacketUseNum().add(redPacketAmount));
            wxRedPacketSummary.setRedPacketRemainNum(wxRedPacketSummary.getRedPacketRemainNum().subtract(redPacketAmount));
            result = wxRedPacketSummaryService.updateByRedPacketRemainNum(wxRedPacketSummary);
            if (result != 1) {
                log.error(CLASS_LOG_PREFIX + "修改每日红包汇总数据失败，wxRedPacketSummary={}", wxRedPacketSummary);
                throw new BizException("签到人数太多，请稍后重试");
            }
        } else {
            log.error(CLASS_LOG_PREFIX + "查询每日红包汇总数据异常，activityId={}, now={}", activityId, now);
            throw new BizException("签到人数太多，请稍后重试");
        }
        //修改总红包剩余金额
        WxSpringActivity wxSpringActivityUpdate = WxSpringActivity.builder().build();
        wxSpringActivityUpdate.setPreRedPacketRemainNum(wxSpringActivity.getRedPacketRemainNum());
        wxSpringActivityUpdate.setId(activityId);
        wxSpringActivityUpdate.setRedPacketRemainNum(redPacketAmount);
        result = wxSpringActivityService.decrRedPacketRemainNum(wxSpringActivityUpdate);
        if (result != 1) {
            log.error(CLASS_LOG_PREFIX + "修改总红包剩余金额数据失败，wxSpringActivityUpdate={}", wxSpringActivityUpdate);
            throw new BizException("签到人数太多，请稍后重试");
        }

        //7.释放分布式锁
        /*String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        redisService.eval(script, Collections.singletonList(key), Collections.singletonList(value));*/
        if (value.equals(redisService.get(key))) {
            redisService.del(key);
        }

        // 6.调用伟文接口发红包
        new Thread(() -> {
            Double moneyAmount = redPacketAmount.doubleValue() * 100;
            try {
                this.doCashAward(sysUser, moneyAmount);
            } catch (Exception e) {
                log.info(CLASS_LOG_PREFIX + "签到发红包异常，exception={},回滚。。。。", e);
                throw new BizException("领取失败");
            }
        }).start();

        //构建返回值
        SignInVo signInVo = new SignInVo();
        signInVo.setRedPacketAmount(redPacketAmount);
        signInVo.setHaveLotteryChance(haveLotteryChance);

        return signInVo;
    }

    private void doCashAward(SysUser sysUser, Double moneyAmount) {
        Integer payOrderId = luckyWheelRegistrationService.buildPayOrderOfSignIn(sysUser, moneyAmount.intValue());
        luckyWheelRegistrationService.doCashAward(sysUser, payOrderId, moneyAmount);
    }

    /**
     * 发送可以抽奖的模板消息
     */
    private void sendTemplateMessage(SysUser sysUser, Long activityId, String luckWheelId) {
        try {
            Map map = buildWheelLotteryMsgMap();
            Object[] pathParams = new Object[2];
            pathParams[0] = activityId;
            pathParams[1] = luckWheelId;
            log.info(CLASS_LOG_PREFIX + "开始调用system....");
            TemplateMsgReqParam templateMsgReqParam = templateMsgService.buildTemplateReqParam(sysUser, BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_ACT_WHEEL_LOTTERY_RECEIVE, map, pathParams);
            log.info(CLASS_LOG_PREFIX + "调用system构建param完成....templateMsgReqParam={}", templateMsgReqParam);
            templateMsgService.sendTemplateMsg(templateMsgReqParam);
            log.info(CLASS_LOG_PREFIX + "调用system发送模板消息完成....");
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "发送抽奖模板消息失败..........");
        }
    }

    /**
     * 构建模板消息的显示值
     */
    private Map buildWheelLotteryMsgMap() {
        Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
        Map<String, String> keywordTempMap;

        keywordTempMap = new HashMap<String, String>();
        keywordTempMap.put("value", "您已获得一次抽奖机会");
        keywordsMap.put("keyword1", keywordTempMap);

        keywordTempMap = new HashMap<String, String>();
        keywordTempMap.put("value", "签到三次即可参与抽奖，最高拿2019元回家基金");
        keywordsMap.put("keyword2", keywordTempMap);

        return keywordsMap;
    }

    /**
     * 功能描述: 增加抽奖次数
     *
     * @param activityId
     * @param sysUser
     * @return void
     * @throws
     * @author gaoj
     * @date 2019/1/24 10:19
     */
    private void increaseLotteryCount(Long activityId, SysUser sysUser) {
        WxSpringActivity wxSpringActivity = wxSpringActivityService.selectByPrimaryKey(activityId);
        if (null == wxSpringActivity) {
            throw new BizException("获取活动数据失败，请检查活动id：" + activityId);
        }
        luckyWheelRegistrationService.increaseLotteryCount(wxSpringActivity.getWxActLuckyWheelId(), sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLotteryCount(Long activityId, SysUser sysUser) throws BizException {
        //1.修改签到汇总表抽奖次数的数量
        wxSigninSummaryService.decrOnceLotteryCount(activityId, sysUser.getId());
        //2.修改签到详情表抽奖的状态（修改最早的那条抽奖状态为已兑换）
        wxUserSigninMapper.updateOneLotteryFlag(activityId, sysUser.getId());

        return true;
    }

    @Override
    public SysUser getUserById(Integer userId) {
        return wxUserSigninMapper.getUserById(userId);
    }

    @Override
    public int updateUser(SysUser record) {
        return wxUserSigninMapper.updateUser(record);
    }

    @Override
    public BigDecimal getTodayTotalRedPacketNum(Long activityId) {
        return wxUserSigninMapper.getTodayTotalRedPacketNum(activityId);
    }

    @Override
    public int insertRedPacketBatch(ArrayList<WxUserSignin> wxUserSignInList) {
        if (CollectionUtils.isEmpty(wxUserSignInList)) {
            log.warn(CLASS_LOG_PREFIX + "批量插入数据，参数为空，wxUserSignInList={}", wxUserSignInList);
            return 0;
        }

        return wxUserSigninMapper.insertRedPacketBatch(wxUserSignInList);
    }

    @Override
    public List<WxUserSignin> getAllBySignInDay(Long activityId, int day) {
        if (null == activityId || 0 == activityId || 0 == day) {
            return null;
        }

        return wxUserSigninMapper.getAllBySignInDay(activityId, day);
    }

    /**
     * 功能描述: 判断今日红包剩余金额,判断红包总剩余金额，并随机生成红包金额
     *
     * @param activityId
     * @return void
     * @throws
     * @author gaoj
     * @date 2019/1/21 16:14
     */
    private BigDecimal createRandomRedPacketAmount(Long activityId, WxSpringActivity wxSpringActivity) {
        Date now = new Date();
        // 2.1.判断今日是否有人领过红包，没有则在红包表插入一条记录
        WxRedPacketSummary wxRedPacketSummary = wxRedPacketSummaryService.getBySignInDate(activityId, now);
        if (null == wxRedPacketSummary) {
            //表示当日还没有人领过红包，需在红包表插入一条记录，数据从活动表获取
            if (null == wxSpringActivity) {
                throw new BizException("获取活动数据失败，请检查活动id：" + activityId);
            }
            wxRedPacketSummary = WxRedPacketSummary.builder()
                    .activityId(activityId)
                    .signinDate(now)
                    .redPacketDayNum(wxSpringActivity.getRedPacketDayNum())
                    .redPacketRemainNum(wxSpringActivity.getRedPacketDayNum())
                    .redPacketUseNum(new BigDecimal(0))
                    .creator("system")
                    .modifier("system")
                    .gmtCreate(now)
                    .gmtModified(now)
                    .isDeleted(0)
                    .build();
            wxRedPacketSummaryService.insert(wxRedPacketSummary);
        }
        //判断红包剩余金额
        BigDecimal dayRedPacketRemainNum = wxRedPacketSummary.getRedPacketRemainNum();
        BigDecimal redPacketMaxAmount;
        BigDecimal redPacketMinAmount;
        //获取红包最大金额和最小金额
        if (DateUtils.isSameDay(DateUtil.getNewYearEveOf2019(), now)) {
            redPacketMaxAmount = wxSpringActivity.getNewYearEveRedPacketMaxAmount();
            redPacketMinAmount = wxSpringActivity.getNewYearEveRedPacketMinAmount();
        } else {
            redPacketMaxAmount = wxSpringActivity.getRedPacketMaxAmount();
            redPacketMinAmount = wxSpringActivity.getRedPacketMinAmount();
        }
        BigDecimal allRedPacketRemainNum = wxSpringActivity.getRedPacketRemainNum();
        if (allRedPacketRemainNum.compareTo(redPacketMaxAmount) < 0) {
            log.info(CLASS_LOG_PREFIX + "判断红包金额：总剩余红包比最大红包数小，allRedPacketRemainNum={},redPacketMaxAmount={}",
                    allRedPacketRemainNum, redPacketMaxAmount);
            throw new BizException("春节红包已领完");
        }
        if (dayRedPacketRemainNum.compareTo(redPacketMaxAmount) < 0) {
            log.info(CLASS_LOG_PREFIX + "判断红包金额：今日剩余红包比最大红包数小，dayRedPacketRemainNum={},redPacketMaxAmount={}",
                    dayRedPacketRemainNum, redPacketMaxAmount);
            throw new BizException("今日红包已领完");
        }

        //随机生成最大值和最小值中间的数值
        DecimalFormat df = new DecimalFormat("#.00");
        //多线程并发使用的随机数生成器(有边界)
        double doubleAmount = ThreadLocalRandom.current().nextDouble(redPacketMinAmount.doubleValue(), redPacketMaxAmount.doubleValue());
        log.info(CLASS_LOG_PREFIX + "随机生成红包金额：原随机数：{}", doubleAmount);
        //格式化为2位小数
        BigDecimal amount = new BigDecimal(df.format(doubleAmount));
        log.info(CLASS_LOG_PREFIX + "随机生成红包金额：最终的红包金额：{}", amount);
        return amount;
    }

    /**
     * 功能描述: 根据活动时间组装每天的签到列表集合
     *
     * @param signInDateVo
     * @return List<UserSignInRecordVo>
     * @author gaoj
     * @date 2019/1/20 22:12
     */
    private List<UserSignInRecordVo> getActivitySignInList(DateVo signInDateVo) {
        // 查询起始时间和截止时间
        Date beginDate = signInDateVo.getBeginDate();
        Date endDate = signInDateVo.getEndDate();
        log.info(CLASS_LOG_PREFIX + "获取用户签到记录，beginDate={},endDate={}", beginDate, endDate);
        // 算出起始时间和截止时间中间的每一天，返回每天对象集合
        long day = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24) + 1;
        log.info(CLASS_LOG_PREFIX + "获取用户签到记录，活动间隔天数：{}", day);
        List<UserSignInRecordVo> allSignInRecordVoList = new ArrayList<>((int) day);
        UserSignInRecordVo daySignInRecordVo;
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < day; i++) {
            calendar.setTime(beginDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            daySignInRecordVo = new UserSignInRecordVo();
            daySignInRecordVo.setSigninDate(calendar.getTime());
            daySignInRecordVo.setSigninDay(calendar.get(Calendar.DAY_OF_MONTH));
            allSignInRecordVoList.add(daySignInRecordVo);
        }
        return allSignInRecordVoList;
    }

    /**
     * 功能描述: 获取签到活动的开始和结束时间
     *
     * @param activityId
     * @return com.sandu.api.springFestivalActivity.output.DateVo
     * @throws
     * @author gaoj
     * @date 2019/1/21 10:43
     */
    private DateVo getSignInDate(long activityId) {
        // 查询起始时间和截止时间
        log.info(CLASS_LOG_PREFIX + "获取签到活动时间,activityId={}", activityId);
        DateVo signInDateVo = wxSpringActivityService.getSignInDate(activityId);
        if (null == signInDateVo) {
            throw new BizException("获取签到活动时间为空");
        }
        if (null == signInDateVo.getBeginDate()) {
            throw new BizException("获取签到活动开始时间为空");
        }
        if (null == signInDateVo.getEndDate()) {
            throw new BizException("获取签到活动结束时间为空");
        }
        log.info(CLASS_LOG_PREFIX + "获取用户签到记录，beginDate={},endDate={}", signInDateVo.getBeginDate(), signInDateVo.getEndDate());

        return signInDateVo;
    }

}
