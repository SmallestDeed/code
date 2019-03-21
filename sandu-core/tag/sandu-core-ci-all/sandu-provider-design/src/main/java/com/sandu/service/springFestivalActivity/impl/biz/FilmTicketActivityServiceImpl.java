package com.sandu.service.springFestivalActivity.impl.biz;

import com.google.gson.Gson;
import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.base.model.ResPic;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.base.output.SmsVo;
import com.sandu.api.base.service.RedisService;
import com.sandu.api.base.service.ResPicService;
import com.sandu.api.springFestivalActivity.input.UserInviteRecordBo;
import com.sandu.api.springFestivalActivity.model.*;
import com.sandu.api.springFestivalActivity.output.*;
import com.sandu.api.springFestivalActivity.service.*;
import com.sandu.api.springFestivalActivity.service.biz.FilmTicketActivityService;
import com.sandu.service.springFestivalActivity.aspect.CheckMethodArgs;
import com.sandu.service.springFestivalActivity.aspect.SendTemplateMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("filmTicketActivityService")
public class FilmTicketActivityServiceImpl implements FilmTicketActivityService {
    private static final String CLASS_LOG_PREFIX = "[电影票活动服务]";
    private static final String REDIS_LOCK_PREFIX = "activity_lock:";

    @Autowired
    private RedisService redisService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private WxUserTaskService wxUserTaskService;
    @Autowired
    private WxSpringActivityService wxSpringActivityService;
    @Autowired
    private WxUserCardService wxUserCardService;
    @Autowired
    private WxUserInviteRecordService wxUserInviteRecordService;
    @Autowired
    private WxFilmTicketService wxFilmTicketService;

    /**
     * 获取红点标识
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return hasTaskFlag 是否有可领取的任务，activityOverFlag 活动是否结束，cardNumFlag 今日是否还有可领的卡片
     */
    @Override
    @CheckMethodArgs
    public RedPointFlagVo getRedPointFlag(Long userId, Long activityId) {
        RedPointFlagVo flag = new RedPointFlagVo(true, true, true);
        // 有可领取任务或可完成任务
        WxUserTask task = this.getUserTask(userId, activityId);
        if (task != null) {
            flag.setHasTaskFlag((task.getTaskOneStatus() == 0 || task.getTaskTwoStatus() == 0 || task.getTaskThreeStatus() == 0) &&
                    ((task.getTaskOneFinishTime() == null || !DateUtils.isSameDay(new Date(), task.getTaskOneFinishTime())) &&
                            (task.getTaskTwoFinishTime() == null || !DateUtils.isSameDay(new Date(), task.getTaskTwoFinishTime())) &&
                            (task.getTaskThreeFinishTime() == null || !DateUtils.isSameDay(new Date(), task.getTaskThreeFinishTime()))));
        }
        // 活动未结束
        WxSpringActivity activity = wxSpringActivityService.selectByPrimaryKey(activityId);
        if (activity != null) {
            flag.setActivityOverFlag(activity.getFilmRemainNum() > 0 && activity.getIsDeleted() == 0);
        } else {
            log.warn(CLASS_LOG_PREFIX + "当前活动不存在。 activityId:{}", activityId);
            flag.setActivityOverFlag(false);
        }
        // 今日获取拼图数量小于3
        List<WxUserCard> cardList = this.getTodayCard(userId, activityId);
        if (cardList != null && cardList.size() >= 3) {
            flag.setCardNumFlag(false);
        }
        // 返回结果
        return flag;
    }

    /**
     * 助力
     *
     * @param giveUser   助力的用户
     * @param getUser    获得助力的用户
     * @param activityId 活动ID
     * @return success 助力是否成功, message 助力消息
     */
    @Override
    @CheckMethodArgs
    @SendTemplateMessage
    public GiveMeFiveVo giveMeFive(SysUser giveUser, SysUser getUser, Long activityId) {
        if (this.check(activityId)) {
            byte cardFlag = 1;
            Long cardId = 0L;
            String remark = null;
            boolean isNewUser = false;
            String key = REDIS_LOCK_PREFIX + "new_user:" + giveUser.getId() + "," + activityId;
            String value = UUID.randomUUID().toString();
            if (this.getLock(key, value, 10, 1000, 120)) {
                isNewUser = this.isNewUser(giveUser, activityId);
                if (isNewUser) {
                    try {
                        if ((cardId = this.addCard(getUser.getId(), activityId, new Byte("3"))) != null) {
                            cardFlag = 0;
                        }
                        if (cardFlag == 0) {
                            WxUserCard card = wxUserCardService.selectByPrimaryKey(cardId);
                            if (card.getCardNumber() == 12) {
                                this.allotFilmTicket(getUser.getId(), activityId);
                            }
                        }
                    } catch (BizException e) {
                        log.warn(CLASS_LOG_PREFIX + e.getMessage());
                        remark = e.getMessage();
                    }
                    this.addInviteRecord(getUser, giveUser, activityId, new Byte(isNewUser ? "0" : "1"), cardFlag, cardId, remark);
                }
                this.releaseLock(key, value);
            }
            DateVo dateVo = this.getActivityTime(activityId);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            WxSpringActivityVo detail = this.getSpringActivityDetail(activityId);
            return GiveMeFiveVo.builder()
                    .success(isNewUser)
                    .message(isNewUser ? "助力成功" : "助力失败，你不是新用户")
                    .cardFlag(cardFlag)
                    .sendTemplateMsg(isNewUser ? new SendTemplateMsg(getUser, 10,
                            this.buildData(format.format(dateVo.getEndDate()), "邀好友领取拼图，可以免费拿电影票哦", "您已成功获得好友助力"),
                            new Object[]{detail.getId(), detail.getLuckyWheelId()}) : null)
                    .build();
        }
        return null;
    }

    /**
     * 获取邀请好友记录总数
     *
     * @param search 查询参数
     * @return 邀请记录列表
     */
    @Override
    @CheckMethodArgs
    public Integer getInviteRecordCount(UserInviteRecordBo search) {
        WxUserInviteRecord wxUserInviteRecord = WxUserInviteRecord.builder().userId(search.getUserId()).activityId(search.getActivityId()).isDeleted(0).build();
        return wxUserInviteRecordService.selectSelectiveCount(wxUserInviteRecord);
    }

    /**
     * 获取邀请好友记录列表
     *
     * @param search 查询参数
     * @return 邀请记录列表
     */
    @Override
    @CheckMethodArgs
    public List<UserInviteRecordVo> getInviteRecord(UserInviteRecordBo search) {
        WxUserInviteRecord wxUserInviteRecord = WxUserInviteRecord.builder().userId(search.getUserId()).activityId(search.getActivityId()).isDeleted(0).build();
        List<WxUserInviteRecord> list = wxUserInviteRecordService.selectSelective(wxUserInviteRecord, search.getStart(), search.getLimit());
        if (list != null && list.size() > 0) {
            list = list.stream().sorted((o1, o2) -> o1.getGmtCreate().after(o2.getGmtCreate()) ? -1 : 1).collect(Collectors.toList());
            List<UserInviteRecordVo> voList = new ArrayList<>();
            for (WxUserInviteRecord userInviteRecord : list) {
                String cardWord = null;
                if (userInviteRecord.getCardFlag() == 0 && userInviteRecord.getCardId() != null) {
                    WxUserCard wxUserCard = WxUserCard.builder().id(userInviteRecord.getCardId()).build();
                    List<WxUserCard> cardList = wxUserCardService.selectSelective(wxUserCard, 0, 1);
                    if (cardList != null && cardList.size() > 0) {
                        WxUserCard card = cardList.get(0);
                        cardWord = this.getCardWord(card.getCardNumber());
                    }
                }
                voList.add(UserInviteRecordVo.builder()
                        .id(userInviteRecord.getId())
                        .nickName(userInviteRecord.getNickName())
                        .headPic(userInviteRecord.getHeadPic())
                        .inviteDate(userInviteRecord.getGmtCreate())
                        .cardFlag(userInviteRecord.getCardFlag() != null && userInviteRecord.getCardFlag() == 0 ? true : false)
                        .cardWord(cardWord)
                        .remark(userInviteRecord.getRemark())
                        .build());
            }
            return voList;
        }
        return null;
    }

    /**
     * 获取用户任务状态
     *
     * @param user       用户
     * @param activityId 活动ID
     * @return
     */
    @Transactional
    @Override
    @CheckMethodArgs
    public UserTaskVo getUserTaskState(SysUser user, Long activityId) {
        String key = REDIS_LOCK_PREFIX + "userTask:" + activityId + ":" + user.getId();
        String value = UUID.randomUUID().toString();
        if (this.getLock(key, value, 3, 1000, 50)) {
            WxUserTask task = this.getUserTask(user.getId(), activityId);
            if (task != null) {
                if (task.getTaskOneStatus() == 1) {
                    if (StringUtils.isNotBlank(user.getMobile())) {
                        this.doneTask(user.getId(), activityId);
                    }
                }
            } else {
                task = this.createUserTask(activityId, user);
                if (task == null) {
                    throw new BizException("获取任务状态失败。");
                }
            }
            this.releaseLock(key, value);
            return new UserTaskVo(task.getId(), task.getTaskOneStatus(), task.getTaskTwoStatus(), task.getTaskThreeStatus());
        }
        return null;
    }

    /**
     * 领取任务
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @param taskId     任务ID
     * @return
     */
    @Transactional
    @Override
    @CheckMethodArgs
    public Boolean doTask(Long userId, Long activityId, Long taskId) {
        if (this.check(userId, activityId, taskId)) {
            WxUserTask task = wxUserTaskService.selectByPrimaryKey(taskId);
            byte taskOneState = task.getTaskOneStatus();
            byte taskTwoState = task.getTaskTwoStatus();
            byte taskThreeState = task.getTaskThreeStatus();
            task = task.toBuilder()
                    .taskOneStatus(taskOneState == 0 ? 1 : taskOneState)
                    .taskOneReceiveTime(taskOneState == 0 ? new Date() : null)
                    .taskTwoStatus(taskTwoState == 0 ? 1 : taskTwoState)
                    .taskTwoReceiveTime(taskTwoState == 0 ? new Date() : null)
                    .taskThreeStatus(taskThreeState == 0 ? 1 : taskThreeState)
                    .taskThreeReceiveTime(taskThreeState == 0 ? new Date() : null)
                    .gmtModified(new Date())
                    .build();
            int i = wxUserTaskService.updateByPrimaryKeySelective(task);
            if (i <= 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 完成任务
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return 返回完成任务获得的卡片
     */
    @Transactional
    @Override
    @CheckMethodArgs
    public String doneTask(Long userId, Long activityId) {
        WxUserTask task = this.getUserTask(userId, activityId);
        if (task != null) {
            byte taskOneStatus = task.getTaskOneStatus();
            byte taskTwoStatus = task.getTaskTwoStatus();
            byte taskThreeStatus = task.getTaskThreeStatus();
            byte businessType = taskOneStatus == 1 ? new Byte("0") : (taskTwoStatus == 1 ? new Byte("1") : (taskThreeStatus == 1 ? new Byte("2") : new Byte("-1")));
            if (businessType != -1) {
                task = task.toBuilder()
                        .taskOneStatus(businessType == 0 ? 2 : taskOneStatus)
                        .taskOneFinishTime(businessType == 0 ? new Date() : null)
                        .taskTwoStatus(businessType == 1 ? 2 : (businessType == 0 ? 0 : taskTwoStatus))
                        .taskTwoFinishTime(businessType == 1 ? new Date() : null)
                        .taskThreeStatus(businessType == 2 ? 2 : (businessType == 1 ? 0 : taskThreeStatus))
                        .taskThreeFinishTime(businessType == 2 ? new Date() : null)
                        .build();
                int i = wxUserTaskService.updateByPrimaryKeySelective(task);
                if (i > 0) {
                    try {
                        Long cardId = this.addCard(userId, activityId, businessType);
                        if (cardId != null) {
                            WxUserCard card = wxUserCardService.selectByPrimaryKey(cardId);
                            if (card.getCardNumber() == 12) {
                                this.allotFilmTicket(userId, activityId);
                            }
                            String word = this.getCardWord(card.getCardNumber());
                            return word;
                        }
                    } catch (BizException e) {
                        log.warn(CLASS_LOG_PREFIX + e.getMessage());
                    }
                    return null;
                }
            }
            return null;
        } else {
            throw new BizException("数据异常");
        }
    }

    /**
     * 查询拼图页面数据
     *
     * @param user       用户
     * @param activityId 活动ID
     * @return
     */
    @Override
    @CheckMethodArgs
    public CardPageVo getCardPageVo(SysUser user, Long activityId) {
        // 获得的卡片数
        WxUserCard wxUserCard = WxUserCard.builder().userId(user.getId()).activityId(activityId).isDeleted(0).build();
        List<WxUserCard> cardList = wxUserCardService.selectSelective(wxUserCard, null, null);
        // 是否剩余电影票
        WxSpringActivity activity = wxSpringActivityService.selectByPrimaryKey(activityId);
        if (activity == null || activity.getIsDeleted() == 1) {
            log.warn(CLASS_LOG_PREFIX + "活动不存在，activityId:{}", activityId);
            throw new BizException("活动不存在");
        }
        // 是否绑定了手机号
        return new CardPageVo(cardList == null ? 0 : cardList.size(), activity.getFilmRemainNum() > 0, StringUtils.isNotBlank(user.getMobile()));
    }

    /**
     * 获取电影票信息
     *
     * @param user       用户
     * @param activityId 活动ID
     * @return
     */
    @Override
    @CheckMethodArgs
    public FilmTicketVo getFilmTicket(SysUser user, Long activityId) {
        if (StringUtils.isBlank(user.getMobile())) {
            throw new BizException("还没绑定手机号");
        }
        WxFilmTicket wxFilmTicket = WxFilmTicket.builder().userId(user.getId()).isDeleted(0).build();
        List<WxFilmTicket> filmList = wxFilmTicketService.selectSelective(wxFilmTicket);
        if (filmList == null || filmList.size() <= 0) {
            return null;
        }
        wxFilmTicket = filmList.get(0);
        if (wxFilmTicket.getTicketStatus() == 1) {
            wxFilmTicket = wxFilmTicket.toBuilder().receiveTime(new Date()).mobile(user.getMobile()).ticketStatus(new Byte("2")).gmtModified(new Date()).build();
            wxFilmTicketService.updateByPrimaryKeySelective(wxFilmTicket);
        }
        return new FilmTicketVo(filmList.get(0).getTicketCode());
    }

    /**
     * 增加中奖几率
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return
     */
    @Override
    @CheckMethodArgs
    public Integer increaseLotteryRate(Long userId, Long activityId) {
        WxUserTask task = this.getUserTask(userId, activityId);
        if (task.getLotteryRate() < 60) {
            task.setLotteryRate(task.getLotteryRate() + 1);
            int i = wxUserTaskService.updateByPrimaryKeySelective(task);
            if (i <= 0) {
                throw new BizException("增加中奖概率失败");
            }
        }
        return task.getLotteryRate();
    }

    /**
     * 校验验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    @Override
    @CheckMethodArgs
    public Boolean checkPermission(String phone, String code) {
        String jsonValue = redisService.getMap(SmsVo.REDIS_SMS_CODE_KEY, phone);
        if (StringUtils.isBlank(jsonValue)) {
            throw new BizException("请先获取验证码");
        }
        SmsVo smsVo = new Gson().fromJson(jsonValue, SmsVo.class);
        if (smsVo == null) {
            throw new BizException("请先获取验证码");
        }
        if (System.currentTimeMillis() - smsVo.getSendTime() > SmsVo.SMS_CODE_VALID_TIME) {
            throw new BizException("验证码超时");
        }
        if (!code.equals(smsVo.getCode())) {
            return false;
        }
        try {
            redisService.delMap(SmsVo.REDIS_SMS_CODE_KEY, phone);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "校验用户验证码，删除缓存中的key异常：{}", e);
        }
        return true;
    }

    /**
     * 获取用户还没领取的卡片
     *
     * @param activityId 活动ID
     * @param sysUser    当前用户
     * @return
     */
    @Override
    @CheckMethodArgs
    public List<WxUserCardVo> getUnReceivedCard(Long activityId, SysUser sysUser) {
        WxUserCard wxUserCard = WxUserCard.builder().activityId(activityId).userId(sysUser.getId()).cardStatus(new Byte("0")).isDeleted(0).build();
        List<WxUserCard> cardList = wxUserCardService.selectSelective(wxUserCard, null, null);
        if (cardList == null || cardList.size() <= 0) {
            return null;
        }
        List<WxUserCardVo> wxUserCardVoList = cardList.stream().map(card -> {
            card.setCardStatus(new Byte("1"));
            card.setReceiveTime(new Date());
            wxUserCardService.updateByPrimaryKeySelective(card);
            return new WxUserCardVo(card.getBusinessType(), card.getCardNumber(), card.getCardDate(), this.getCardWord(card.getCardNumber()));
        }).collect(Collectors.toList());
        return wxUserCardVoList;
    }

    /**
     * 查询已被用户获取的电影票列表
     *
     * @param activityId 活动ID
     * @param limit      滚动条数
     * @return
     */
    @Override
    @CheckMethodArgs
    public List<WxFilmTicket> getNotEmptyTicketList(Long activityId, Integer limit) {
        List<WxFilmTicket> notEmptyTicketList = wxFilmTicketService.getNotEmptyTicketList(activityId, 0, limit);
        return notEmptyTicketList;
    }

    /**
     * 获取正在做任务或者有任务可领的用户ID集合
     *
     * @param activityId 活动ID
     * @return
     */
    @Override
    @CheckMethodArgs
    public List<Long> getUserListWhoDoingTask(Long activityId) {
        List<WxUserTask> taskList = wxUserTaskService.getDoingTaskList(activityId);
        if (taskList != null && taskList.size() > 0) {
            Set<Long> userIdSet = new HashSet<>(taskList.size());
            taskList.forEach(task -> {
                userIdSet.add(task.getUserId());
            });
            return new ArrayList<>(userIdSet);
        }
        return null;
    }

    /**
     * 获取今天获得卡片小于三张的用户集合
     *
     * @param activityId 活动ID
     * @return
     */
    @Override
    @CheckMethodArgs
    public Map<Long, Integer> getUserListWhoCardNumLessThanThree(Long activityId) {
        List<WxUserCard> cardList = wxUserCardService.getTodayCardList(activityId);
        if (cardList != null && cardList.size() > 0) {
            Map<Long, List<WxUserCard>> groupMap = cardList.stream().collect(Collectors.groupingBy(WxUserCard::getUserId));
            Iterator<Map.Entry<Long, List<WxUserCard>>> iterator = groupMap.entrySet().iterator();
            Map<Long, Integer> userCardNumMap = new HashMap<>();
            while (iterator.hasNext()) {
                Map.Entry<Long, List<WxUserCard>> entry = iterator.next();
                if (entry.getValue() == null || entry.getValue().size() < 3) {
                    userCardNumMap.put(entry.getKey(), entry.getValue().size());
                }
            }
            WxFilmTicket wxFilmTicket = WxFilmTicket.builder().isDeleted(0).activityId(activityId).ticketStatus(new Byte("1")).build();
            List<WxFilmTicket> ticketList = wxFilmTicketService.selectSelective(wxFilmTicket);
            ticketList.stream().forEach(ticket -> {
                if (ticket.getUserId() != null) {
                    if (userCardNumMap.containsKey(ticket.getUserId())) {
                        userCardNumMap.remove(ticket.getUserId());
                    }
                }
            });
            return userCardNumMap;
        }
        return null;
    }

    /**
     * 构建模板消息数据
     *
     * @param strings 消息
     * @return
     */
    @Override
    public Map<String, Map<String, String>> buildData(String... strings) {
        Map<String, Map<String, String>> map = new HashMap<>();
        for (int i = 1; i <= strings.length; i++) {
            Map<String, String> valueMap = new HashMap<>();
            String string = strings[i - 1];
            valueMap.put("value", string);
            map.put("keyword" + i, valueMap);
        }
        return map;
    }

    /**
     * 获取活动的开始时间和结束时间
     *
     * @param activityId 活动ID
     * @return
     */
    @Override
    public DateVo getActivityTime(Long activityId) {
        return wxSpringActivityService.getSignInDate(activityId);
    }

    /**
     * 获取春节活动详情
     *
     * @param activityId 活动ID
     * @return
     */
    @Override
    public WxSpringActivityVo getSpringActivityDetail(Long activityId) {
        WxSpringActivity wxSpringActivity = wxSpringActivityService.selectByPrimaryKey(activityId);
        if (wxSpringActivity != null && wxSpringActivity.getIsDeleted() != 1) {
            WxSpringActivityVo vo = WxSpringActivityVo.builder()
                    .id(wxSpringActivity.getId())
                    .luckyWheelId(wxSpringActivity.getWxActLuckyWheelId())
                    .build();
            return vo;
        }
        return null;
    }

    /**
     * 校验是否新用户(当天新生成的用户且没有助力过)
     *
     * @param user       被邀请用户
     * @param activityId 活动ID
     * @return
     */
    private boolean isNewUser(SysUser user, Long activityId) {
        if (DateUtils.isSameDay(user.getGmtCreate(), new Date())) {
            WxUserInviteRecord record = WxUserInviteRecord.builder().activityId(activityId).inviteUserId(user.getId()).isDeleted(0).build();
            List<WxUserInviteRecord> recordList = wxUserInviteRecordService.selectSelective(record, null, null);
            if (recordList != null && recordList.size() > 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 查询今日获得的卡片
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return
     */
    private List<WxUserCard> getTodayCard(Long userId, Long activityId) {
        WxUserCard wxUserCard = WxUserCard.builder().userId(userId).activityId(activityId).isDeleted(0).build();
        List<WxUserCard> cardList = wxUserCardService.selectSelective(wxUserCard, null, null);
        if (cardList != null && cardList.size() > 0) {
            List<WxUserCard> todayCardList = this.getTodayCard(cardList);
            if (todayCardList != null && todayCardList.size() > 3) {
                log.warn(CLASS_LOG_PREFIX + "用户今天获得的卡片大于3张。 userId:{},activityId:{}", userId, activityId);
            }
            return todayCardList;
        }
        return null;
    }

    /**
     * 创建用户任务
     *
     * @param activityId
     * @param user
     * @return
     */
    private WxUserTask createUserTask(Long activityId, SysUser user) {
        WxUserTask task = WxUserTask.builder()
                .activityId(activityId)
                .userId(user.getId())
                .openId(user.getOpenId())
                .taskOneStatus(StringUtils.isBlank(user.getMobile()) ? new Byte("0") : new Byte("2"))
                .taskOneReceiveTime(StringUtils.isBlank(user.getMobile()) ? null : user.getGmtCreate())
                .taskOneFinishTime(StringUtils.isBlank(user.getMobile()) ? null : user.getGmtCreate())
                .taskTwoStatus(StringUtils.isBlank(user.getMobile()) ? new Byte("3") : new Byte("0"))
                .taskThreeStatus(new Byte("3"))
                .lotteryRate(5)
                .isAutoComplete(StringUtils.isBlank(user.getMobile()) ? new Byte("0") : new Byte("1"))
                .creator(user.getNickName())
                .gmtCreate(new Date())
                .modifier(user.getNickName())
                .gmtModified(new Date())
                .isDeleted(0)
                .build();
        int i = wxUserTaskService.insertSelective(task);
        if (i <= 0) {
            return null;
        }
        return task;
    }

    /**
     * 获取今日获得的卡片
     *
     * @param cardList 用户获得的所有卡片集合
     * @return
     */
    private List<WxUserCard> getTodayCard(List<WxUserCard> cardList) {
        if (cardList != null && cardList.size() > 0) {
            List<WxUserCard> todayCardList = cardList.stream()
                    .filter(card -> DateUtils.isSameDay(new Date(), card.getCardDate()) && card.getIsDeleted() == 0)
                    .collect(Collectors.toList());
            return todayCardList;
        }
        return null;
    }

    /**
     * 查询用户领取的任务
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return
     */
    private WxUserTask getUserTask(Long userId, Long activityId) {
        WxUserTask wxUserTask = WxUserTask.builder().userId(userId).activityId(activityId).isDeleted(0).build();
        List<WxUserTask> userTaskList = wxUserTaskService.selectSelective(wxUserTask);
        if (userTaskList != null && userTaskList.size() > 0) {
            if (userTaskList.size() > 1) {
                log.warn(CLASS_LOG_PREFIX + "查出的用户任务表结果大于1条。 userId:{},activityId", userId, activityId);
            }
            return userTaskList.get(0);
        }
        return null;
    }

    /**
     * 获取卡片
     *
     * @param userId       用户ID
     * @param activityId   活动ID
     * @param businessType 获得途径(0-绑定手机号;1-装修我家;2-产品替换;3-邀请好友)
     * @return 卡片ID
     */
    private Long addCard(Long userId, Long activityId, Byte businessType) {
        if (this.check(userId, activityId)) {
            // 加锁防并发问题
            String key = REDIS_LOCK_PREFIX + userId + "," + activityId;
            String value = UUID.randomUUID().toString();
            if (this.getLock(key, value, 10, 1000, 100)) {
                try {
                    if (this.check(userId, activityId)) {
                        Date date = new Date();
                        WxUserCard wxUserCard = WxUserCard.builder().userId(userId).activityId(activityId).isDeleted(0).build();
                        List<WxUserCard> cardList = wxUserCardService.selectSelective(wxUserCard, null, null);
                        wxUserCard = WxUserCard.builder()
                                .activityId(activityId)
                                .userId(userId)
                                .businessType(businessType)
                                .cardNumber(cardList == null ? 1 : cardList.size() + 1)
                                .cardDate(date)
                                .cardStatus(businessType == 0 ? new Byte("1") : new Byte("0"))
                                .creator(userId + "")
                                .gmtCreate(date)
                                .modifier(userId + "")
                                .gmtModified(date)
                                .isDeleted(0)
                                .build();
                        int i = wxUserCardService.insertSelective(wxUserCard);
                        if (i <= 0) {
                            this.releaseLock(key, value);
                            return null;
                        }
                        this.releaseLock(key, value);
                        return wxUserCard.getId();
                    }
                } catch (BizException e) {
                    this.releaseLock(key, value);
                    throw e;
                }
            }
        }
        return null;
    }

    /**
     * 分配电影票
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return
     */
    private boolean allotFilmTicket(Long userId, Long activityId) {
        WxFilmTicket ticket = wxFilmTicketService.getEmptyTicket(null);
        if (ticket == null) {
            log.warn(CLASS_LOG_PREFIX + "活动已结束，activityId:{}", activityId);
            throw new BizException("活动已结束");
        }
        String key = REDIS_LOCK_PREFIX + "filmTicketLock";
        String value = UUID.randomUUID().toString();
        if (this.getLock(key, value, 9999, 2000, 101)) {
            ticket = wxFilmTicketService.getEmptyTicket(null);
            if (ticket == null) {
                log.warn(CLASS_LOG_PREFIX + "活动已结束，activityId:{}", activityId);
                this.releaseLock(key, value);
                throw new BizException("活动已结束");
            }
            ticket.setUserId(userId);
            ticket.setAllotTime(new Date());
            ticket.setTicketStatus(new Byte("1"));
            ticket.setModifier(userId + "");
            ticket.setGmtModified(new Date());
            int i = wxFilmTicketService.updateByPrimaryKeySelective(ticket);
            if (i > 0) {
                WxSpringActivity wxSpringActivity = wxSpringActivityService.selectByPrimaryKey(activityId);
                if (wxSpringActivity.getIsDeleted() == 1) {
                    log.warn(CLASS_LOG_PREFIX + "活动不存在，activityId:{}", activityId);
                    this.releaseLock(key, value);
                    throw new RuntimeException("活动不存在");
                }
                wxSpringActivity.setFilmRemainNum(wxSpringActivity.getFilmRemainNum() - 1);
                wxSpringActivity.setFilmUseNum(wxSpringActivity.getFilmUseNum() + 1);
                i = wxSpringActivityService.updateByPrimaryKeySelective(wxSpringActivity);
                if (i == 0) {
                    this.releaseLock(key, value);
                    throw new RuntimeException("更新春节活动信息失败。");
                }
                return true;
            }
            this.releaseLock(key, value);
        }
        return false;
    }

    /**
     * 添加邀请记录
     *
     * @param user        邀请人
     * @param invitedUser 被邀请人ID
     * @param activityId  活动ID
     * @param status      是否有效（邀请的是不是新用户）
     * @param cardFlag    是否获得卡片
     * @param cardId      卡片ID
     * @param remark      备注
     * @return
     */
    private void addInviteRecord(SysUser user, SysUser invitedUser, long activityId, byte status, byte cardFlag, Long cardId, String remark) {
        ResPic pic = null;
        if (invitedUser.getPicId() != null) {
            pic = resPicService.selectByPrimaryKey(invitedUser.getPicId().intValue());
        }
        WxUserInviteRecord wxUserInviteRecord = WxUserInviteRecord.builder()
                .activityId(activityId)
                .openId(invitedUser.getOpenId())
                .nickName(invitedUser.getNickName())
                .headPic(pic == null || pic.getIsDeleted() == 1 ? "" : pic.getPicPath())
                .userId(user.getId())
                .inviteUserId(invitedUser.getId())
                .status(status)
                .cardFlag(cardFlag)
                .cardId(cardId)
                .remark(remark)
                .creator(invitedUser.getNickName())
                .gmtCreate(new Date())
                .modifier(invitedUser.getNickName())
                .gmtModified(new Date())
                .isDeleted(0)
                .build();
        wxUserInviteRecordService.insert(wxUserInviteRecord);
    }

    /**
     * 校验是否有获得卡片资格
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return
     */
    private boolean check(Long userId, Long activityId) {
        if (this.check(activityId)) {
            // 活动是否已经结束
            WxSpringActivity activity = wxSpringActivityService.selectByPrimaryKey(activityId);
            if (activity != null) {
                if (activity.getFilmRemainNum() <= 0) {
                    log.warn(CLASS_LOG_PREFIX + "活动已结束，activityId:{}", activityId);
                    throw new BizException("活动已结束");
                }
            } else {
                log.warn(CLASS_LOG_PREFIX + "当前活动不存在，activityId:{}", activityId);
                throw new BizException("当前活动不存在");
            }
            // 是否已经获得影票
            WxFilmTicket wxFilmTicket = WxFilmTicket.builder().userId(userId).isDeleted(0).build();
            List<WxFilmTicket> filmList = wxFilmTicketService.selectSelective(wxFilmTicket);
            if (filmList != null && filmList.size() > 0) {
                throw new BizException("你已经获取过电影票了");
            }
            // 获得总卡片数
            WxUserCard wxUserCard = WxUserCard.builder().userId(userId).activityId(activityId).isDeleted(0).build();
            List<WxUserCard> cardList = wxUserCardService.selectSelective(wxUserCard, null, null);
            if (cardList != null && cardList.size() >= 12) {
                throw new BizException("已获得12张拼图");
            }
            // 今日获得卡片数
            cardList = this.getTodayCard(cardList);
            if (cardList != null && cardList.size() >= 3) {
                throw new BizException("每日最多获得3张拼图");
            }
            return true;
        }
        return false;
    }

    /**
     * 校验是否有领取任务资格
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @param taskId     用户任务ID
     * @return
     */
    private boolean check(Long userId, Long activityId, Long taskId) {
        if (this.check(userId, activityId)) {
            // 今天是否完成过任务
            WxUserTask task = wxUserTaskService.selectByPrimaryKey(taskId);
            if (task == null || task.getIsDeleted() == 1) {
                log.warn(CLASS_LOG_PREFIX + "查询任务为空，wxUserTaskId:{}", taskId);
                throw new BizException("数据异常");
            }
            if ((task.getTaskOneStatus() != null && task.getTaskOneStatus() == 1) ||
                    (task.getTaskTwoStatus() != null && task.getTaskTwoStatus() == 1) ||
                    (task.getTaskThreeStatus() != null && task.getTaskThreeStatus() == 1)) {
                throw new BizException("已有正在进行的任务");
            }
            if ((task.getTaskOneStatus() != null && task.getTaskOneStatus() != 0) &&
                    (task.getTaskTwoStatus() != null && task.getTaskTwoStatus() != 0) &&
                    (task.getTaskThreeStatus() != null && task.getTaskThreeStatus() != 0)) {
                throw new BizException("没有可领取的任务");
            }
            if ((task.getTaskOneFinishTime() != null && DateUtils.isSameDay(new Date(), task.getTaskOneFinishTime())) ||
                    (task.getTaskTwoFinishTime() != null && DateUtils.isSameDay(new Date(), task.getTaskTwoFinishTime())) ||
                    (task.getTaskThreeFinishTime() != null && DateUtils.isSameDay(new Date(), task.getTaskThreeFinishTime()))) {
                throw new BizException("今天已经完成过任务了，明天再来吧");
            }
            return true;
        }
        return false;
    }

    /**
     * 校验当前时间是否处于活动时间
     *
     * @param activityId 活动ID
     * @return
     */
    private boolean check(Long activityId) {
        DateVo dateVo = wxSpringActivityService.getSignInDate(activityId);
        Date now = new Date();
        if (now.before(dateVo.getBeginDate())) {
            throw new BizException("活动未开始。");
        }
        if (now.after(dateVo.getEndDate())) {
            throw new BizException("活动时间已过。");
        }
        return now.after(dateVo.getBeginDate()) && now.before(dateVo.getEndDate());
    }

    /**
     * 根据卡片数字获取卡片文字
     *
     * @param cardNumber 卡片数字标识
     * @return
     */
    private String getCardWord(Integer cardNumber) {
        if (cardNumber != null) {
            switch (cardNumber) {
                case 1:
                    return "随";
                case 2:
                    return "选";
                case 3:
                    return "家";
                case 4:
                    return "装";
                case 5:
                    return "欢";
                case 6:
                    return "度";
                case 7:
                    return "元";
                case 8:
                    return "宵";
                case 9:
                    return "送";
                case 10:
                    return "电";
                case 11:
                    return "影";
                case 12:
                    return "票";
            }
        }
        return null;
    }

    /**
     * 获取锁
     *
     * @param key   锁标识
     * @param value 释放锁的钥匙
     * @param retry 重试次数
     * @param time  持有锁的最大时间，单位ms
     * @param wait  重试间隔时间，单位ms
     * @return
     */
    private boolean getLock(String key, String value, int retry, long time, long wait) {
        int retryNum = 0;
        while (!redisService.lock(key, value, time)) {
            retryNum++;
            if (retryNum > retry) {
                log.warn(CLASS_LOG_PREFIX + "获取锁超时!");
                throw new RuntimeException("超时啦，请重新试试！");
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                log.error(CLASS_LOG_PREFIX + "获取锁异常!", e);
                throw new RuntimeException("出错啦，请重新试试！");
            }
        }
        return true;
    }

    /**
     * 释放锁
     *
     * @param key   锁标识
     * @param value 释放锁的钥匙
     */
    private void releaseLock(String key, String value) {
        if (value.equals(redisService.get(key))) {
            redisService.del(key);
        }
    }
}
