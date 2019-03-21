package com.sandu.pay.order.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.service.PayRedisService;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.constant.UserConstants;
import com.sandu.pay.base.service.BaseCompanyService;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.common.exception.ExceptionCodes;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.UtilDate;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.dao.PayModelGroupRefMapper;
import com.sandu.pay.order.metadata.PayModelConstantType;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayModelGroupRef;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.service.PayModelConfigService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.order.vo.PackageInfoVo;
import com.sandu.pay.order.vo.PayModelInfoVo;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.CompanyVo;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author yzw
 * @Date 2018/1/16 15:57
 */
@Service("payModelGroupRefService")
public class PayModelGroupRefServiceImpl implements PayModelGroupRefService {

    private static final Logger logger = LoggerFactory.getLogger(PayModelGroupRefServiceImpl.class);
    private final static Gson gson = new Gson();
    @Autowired
    private PayModelGroupRefMapper payModelGroupRefMapper;
    @Resource
    private PayModelConfigService payModelConfigService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private PayRedisService payRedisService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;
    @Autowired
    private BaseCompanyService baseCompanyService;

    /**
     * 添加
     *
     * @param record
     * @return
     */
    public PayModelGroupRef add(PayModelGroupRef record) {
        if (this.payModelGroupRefMapper.insertSelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 删除
     *
     * @param id 付款方式业务关联表id
     * @return
     */
    public boolean delete(Integer id) {
        return this.payModelGroupRefMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    public PayModelGroupRef update(PayModelGroupRef record) {
        if (this.payModelGroupRefMapper.updateByPrimaryKeySelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 查询
     *
     * @param id
     * @return 付款方式业务关联表id
     */
    public PayModelGroupRef get(Integer id) {
        return this.payModelGroupRefMapper.selectByPrimaryKey(id);
    }

    /**
     * 判断是否具备渲染权限接口
     *
     * @param platformId 平台id
     * @param userId     用户id
     * @return
     */
    @Override
    public ResultMessage checkRenderGroopRef(Integer platformId, Integer userId) {
        ResultMessage message = new ResultMessage();
        if (null == platformId || null == userId) {
            message.setMessage("参数为空：platformId：{}" + platformId + ",userId:{}" + userId);
            logger.info("参数为空：platformId：{}" + platformId + ",userId:{}" + userId);
            return message;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map = this.getUserPayModelState(platformId, userId);
        boolean flag = false;
        String showMsg = "";
        if (null != map && map.size() > 0) {
            logger.info("计算时间start，用户id：userId：{}" + userId);
            if (null != map.get("expiryTime")) {
                try {
                    Date endDate = (Date) map.get("expiryTime");
                    int dayNumber = UtilDate.getDayNumber(new Date(), endDate);
                    logger.info("剩余的时间：" + dayNumber + "天，用户id：userId:{}" + userId);
                    if (dayNumber <= PayModelConstantType.FIRST_CHECK_DAY && dayNumber >= 0) {
                        boolean b1 = this.checkMessage(userId);
                        logger.info("用户id：userId:{}" + userId + "，是否要显示：" + b1);
                        if (b1) {
                            // 显示
                            flag = true;
                            PayModelConfig payModelConfig = payModelConfigService.get(Integer.valueOf(map.get("payModelConfigId").toString()));
                            StringBuffer sb = new StringBuffer();
                            String packageName = "";
                            if (payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_GIVE)) {
                                packageName = PayModelConstantType.PACKAGE_GIVE;
                            } else {
                                if (String.valueOf(payModelConfig.getTimeType()).equals(PayModelConstantType.TIME_TYPE_MOUTH)) {
                                    packageName = PayModelConstantType.PACKAGE_MONTH;
                                } else {
                                    packageName = PayModelConstantType.PACKAGE_YEAR;
                                }
                            }
                            sb.append("您当前享受的“" + packageName + "”服务剩余时间为" + dayNumber);
                            sb.append("天，为了不影响您的使用，请及时充值。");
                            showMsg = sb.toString();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("计算时间异常，内容是：",e);
                }
            }
            logger.info("计算时间end，用户id：userId{}" + userId);
        }
        map.put("isShow", flag);
        map.put("showMsg", showMsg);
        message.setObj(map);
        message.setSuccess(Boolean.TRUE);
        message.setMessage("success");
        return message;
    }

    /**
     * 用户绑定序列号赠送
     *
     * @param basePlatform
     * @param sysUser
     * @return
     */
    @Override
    public ResultMessage addGiveGroopRef(BasePlatform basePlatform, SysUser sysUser) {
        logger.info("调用赠送包年包月service");
        ResultMessage message = new ResultMessage();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
        Integer businessId = null;
        List<PayModelConfig> payModelConfigList = null;
        if (null == companyFranchiserGroup) {
            businessId = sysUser.getId();
            payModelConfigList = payModelConfigService.getPayModelConfigList(basePlatform.getId(), PayModelConstantType.RENDER_TYPE_GIVE, PayModelConstantType.RANGER_TYPE_PERSONAGE);
        } else {
            businessId = companyFranchiserGroup.getId();
            payModelConfigList = payModelConfigService.getPayModelConfigList(basePlatform.getId(), PayModelConstantType.RENDER_TYPE_GIVE_FRANCHISER, PayModelConstantType.RANGE_TYPE_FRANCHISER);
        }
        if (null == payModelConfigList || payModelConfigList.size() == 0) {
            message.setMessage("暂无可赠送的包年包月");
            logger.info("暂无可赠送的包年包月");
            return message;
        } else {
            /**
             * 1、用户首次登录时赠送三个月免费渲染，以首次登录的时间为准开始计时，一个账号的情况，如果是PC端先登录则已PC登录时间开始计时，如果是移动2B先登录则以移动2B登录时间开始计时
             2、如果是子账号，则以主账号首次登录PC端的时间为准开始计时赠送三个月
             3、被设置为子账号之前，如果该账号已经有3个月免费渲染，设置为子账号后不带入共享，取消子账号后，免费渲染在有效期内的话还能正常使用
             4、被设置为主账号之前，如果该账号已经有3月免费渲染，设置为主账号之后带入共享，与度币共享保持一致
             */
            PayModelGroupRef payModelGroupRef = getPayModelGroupRef(businessId, payModelConfigList.get(0).getId());
            if (null == payModelGroupRef) {
                payModelGroupRef = new PayModelGroupRef();
                payModelGroupRef.setBusinessId(businessId);
                payModelGroupRef.setPayModelConfigId(payModelConfigList.get(0).getId());
                int timeType = payModelConfigList.get(0).getTimeType();
                Date expriyTime = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(expriyTime);
                if (timeType == 0) {
                    calendar.add(calendar.MONTH, payModelConfigList.get(0).getTimeUnit());// 把日期往后增加月份.整数往后推,负数往前移动
                } else if (timeType == 1) {
                    calendar.add(calendar.YEAR, payModelConfigList.get(0).getTimeUnit());// 把日期往后增加年份.整数往后推,负数往前移动
                }
                expriyTime = calendar.getTime();
                payModelGroupRef.setEffectiveTime(new Date());
                payModelGroupRef.setExpiryTime(expriyTime);
                payModelGroupRef.setIsDeleted(0);
                payModelGroupRef.setCreator(sysUser.getMobile());
                payModelGroupRef.setGmtCreate(new Date());
                payModelGroupRef.setModifier(sysUser.getMobile());
                payModelGroupRef.setGmtModified(new Date());
                int i = this.payModelGroupRefMapper.insertSelective(payModelGroupRef);
                if (i != 1) {
                    message.setMessage("赠送包年包月套餐失败");
                    logger.info("赠送包年包月套餐失败");
                    return message;
                }
            } else {
                message.setMessage("已赠送包年包月套餐");
                logger.info("已赠送包年包月套餐");
                return message;
            }
        }
        message.setMessage("赠送包年包月套餐成功");
        logger.info("赠送包年包月套餐成功");
        message.setSuccess(Boolean.TRUE);
        return message;
    }

    /**
     * 获取付款方式业务关联表数据
     *
     * @param businessId       业务id
     * @param payModelConfigId 付款方式业务关联表id
     * @return
     */
    @Override
    public PayModelGroupRef getPayModelGroupRef(Integer businessId, Integer payModelConfigId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessId", businessId);
        map.put("payModelConfigId", payModelConfigId);
        return this.payModelGroupRefMapper.getPayModelGroupRef(map);
    }

    /**
     * 用户付款后插入记录到付款方式业务关联表
     *
     * @param orderNo 订单号
     */
    @Override
    public void addPayModelGroupRef(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            logger.info("orderNo：订单号为空");
            return;
        }
        PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
        logger.info("订单结果：payOrder:{}" + (null == payOrder ? "null" : gson.toJson(payOrder)));
        if (null == payOrder) {
            logger.info("payOrder:找不到订单信息");
            return;
        }
        logger.info("回调成功，插入或者更新关联表信息，用户id：userId:{}" + payOrder.getUserId());
        Integer businessId = StringUtils.isEmpty(payOrder.getAtt1()) == true ? null : Integer.valueOf(payOrder.getAtt1());
        Integer payModelConfigId = StringUtils.isEmpty(payOrder.getAtt2()) == true ? null : Integer.valueOf(payOrder.getAtt2());
        SysUser sysUser = sysUserService.get(payOrder.getUserId());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(payOrder.getUserId());
        if (null == companyFranchiserGroup) {
            this.addOrUpdate(sysUser, payModelConfigId, businessId, 0);
        } else {
            this.addOrUpdate(sysUser, payModelConfigId, businessId, 1);
        }
    }

    /**
     * 添加或者更新付款方式业务关联表
     *
     * @param sysUser
     * @param payModelConfigId 付款方式表id
     * @param businessId       业务id（用户id或者经销商组id）
     * @param isFranchiser     是否属于经销商 0否 1是
     */
    public void addOrUpdate(SysUser sysUser, Integer payModelConfigId, Integer businessId, Integer isFranchiser) {
        //获取付款方式表数据
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigId);
        //获取个人的套餐信息list
        List<PayModelInfoVo> list = this.getPayModelVoList(businessId, isFranchiser);
        //判断插入或者更新的开关
        boolean flag = true;
        // 先定义一个null的时间
        Date expiryTime = null;
        if (null != list && list.size() > 0) {
            expiryTime = list.get(0).getMaxExpiryTime();
            for (int i = 0; i < list.size(); i++) {
                if (payModelConfigId.intValue() == list.get(i).getPayModelConfigId().intValue()) {
                    //更新
                    PayModelGroupRef payModelGroupRef = this.payModelGroupRefMapper.selectByPrimaryKey(list.get(i).getPayModelGroupRefId());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(expiryTime);
                    if (payModelConfig.getTimeType().intValue() == 0) {
                        // 包月
                        calendar.add(calendar.MONTH, payModelConfig.getTimeUnit());
                    } else if (payModelConfig.getTimeType().intValue() == 1) {
                        //包年
                        calendar.add(calendar.YEAR, payModelConfig.getTimeUnit());
                    }
                    if (null == payModelGroupRef.getEffectiveTime()) {
                        //兼容老数据
                        payModelGroupRef.setEffectiveTime(new Date());
                    }
                    payModelGroupRef.setExpiryTime(calendar.getTime());
                    payModelGroupRef.setGmtModified(new Date());
                    int j = this.payModelGroupRefMapper.updateByPrimaryKeySelective(payModelGroupRef);
                    if (j != 1) {
                        logger.info("用户付款成功，更新付款方式关联表记录失败");
                    }
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            // 新增
            PayModelGroupRef payModelGroupRef = new PayModelGroupRef();
            payModelGroupRef.setBusinessId(businessId);
            payModelGroupRef.setPayModelConfigId(payModelConfigId);
            Calendar calendar = new GregorianCalendar();
            //处理时间
            calendar.setTime(null == expiryTime ? new Date() : expiryTime);
            if (payModelConfig.getTimeType().intValue() == 0) {
                // 包月
                calendar.add(calendar.MONTH, payModelConfig.getTimeUnit());
            } else if (payModelConfig.getTimeType().intValue() == 1) {
                //包年
                calendar.add(calendar.YEAR, payModelConfig.getTimeUnit());
            }
            payModelGroupRef.setExpiryTime(calendar.getTime());
            payModelGroupRef.setEffectiveTime(new Date());
            payModelGroupRef.setIsDeleted(0);
            payModelGroupRef.setCreator(sysUser.getMobile());
            payModelGroupRef.setGmtCreate(new Date());
            payModelGroupRef.setModifier(sysUser.getMobile());
            payModelGroupRef.setGmtModified(new Date());
            int i = this.payModelGroupRefMapper.insertSelective(payModelGroupRef);
            if (i != 1) {
                logger.info("用户付款成功，插入付款方式关联表记录失败");
            }
        }
    }

    /**
     * 获取渲染时候的提示信息
     *
     * @param payModelGroupRefId 付款方式业务关联表id
     * @return
     */
    @Override
    public Map<String, Object> getMessage(Integer payModelGroupRefId) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        String showMsg = "";
        PayModelGroupRef payModelGroupRef = this.payModelGroupRefMapper.selectByPrimaryKey(payModelGroupRefId);
        logger.info("用户的付款方式业务关联表新：payModelGroupRef:{}" + (null == payModelGroupRef ? null : gson.toJson(payModelGroupRef)));
        if (null != payModelGroupRef) {
            PayModelConfig payModelConfig = payModelConfigService.get(payModelGroupRef.getPayModelConfigId());
            if (null != payModelConfig) {
                int dayNumber = 0;
                int endNumber = 0;
                dayNumber = UtilDate.getDayNumber(new Date(), payModelGroupRef.getExpiryTime()); // 计算时间差
                if (payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_GIVE)) {
                    //绑定序列号赠送
                    logger.info("绑定序列号赠送，还剩" + dayNumber + "过期，过期前" + PayModelConstantType.GIVE_DATA_NUMBER + "天要有通知");
                    endNumber = PayModelConstantType.GIVE_DATA_NUMBER;
                } else {
                    if (payModelConfig.getTimeType() == 0) {
                        // 月
                        logger.info("包月，还剩" + dayNumber + "过期，过期前" + PayModelConstantType.CONFIG_MONTH + "天要有通知");
                        endNumber = PayModelConstantType.CONFIG_MONTH;
                    } else if (payModelConfig.getTimeType() == 1) {
                        // 年
                        logger.info("包年，还剩" + dayNumber + "过期，过期前" + PayModelConstantType.CONFIG_YEAR + "天要有通知");
                        endNumber = PayModelConstantType.CONFIG_YEAR;
                    }
                }
                //  时间差天数大于等于0，并且小于等于过期天数，并且过期天数不为0方可符合通知
                if (dayNumber >= 0 && dayNumber <= endNumber) {
                    flag = true;
                    StringBuffer sb = new StringBuffer();
                    String packageName = "";
                    if (payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_GIVE) || payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_GIVE_FRANCHISER)) {
                        packageName = PayModelConstantType.PACKAGE_GIVE;
                    } else {
                        if (String.valueOf(payModelConfig.getTimeType()).equals(PayModelConstantType.TIME_TYPE_MOUTH)) {
                            packageName = PayModelConstantType.PACKAGE_MONTH;
                        } else {
                            packageName = PayModelConstantType.PACKAGE_YEAR;
                        }
                    }
                    sb.append("您正使用“" + packageName + "”,该服务可用至" + UtilDate.getStringDate(payModelGroupRef.getExpiryTime()));
                    logger.info("渲染通知信息为：" + sb.toString());
                    showMsg = sb.toString();
                }
            }
        }
        map.put("isShow", flag);
        map.put("showMsg", showMsg);
        return map;
    }

    /**
     * 获取用户的套餐列表
     *
     * @param businessId 业务id
     * @param bizType    业务类型
     * @param rangeType  范围类型
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserRefInfoList(Integer businessId, String bizType, Integer rangeType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessId", businessId);
        map.put("bizType", bizType);
        map.put("rangeType", rangeType);
        return this.payModelGroupRefMapper.getUserRefInfoList(map);
    }

    /**
     * 判断经销商用户是否具备包年包月的权限
     *
     * @param platformId        平台id
     * @param franchiserGroupId 经销商账号组id
     * @param userId            用户id
     * @return
     */
    @Override
    public ResultMessage checkRenderShareGroopRef(Integer platformId, Integer franchiserGroupId, Integer userId) {
        ResultMessage message = new ResultMessage();
        if (null == platformId) {
            message.setMessage("平台id：platformId:{}为空");
            return message;
        }
        if (null == franchiserGroupId) {
            message.setMessage("经销商账号组id：franchiserGroupId：{}为空");
            return message;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map = this.getUserSharePayModelState(platformId, franchiserGroupId);
        boolean flag = false;
        String showMsg = "";
        if (null != map && map.size() > 0) {
            logger.info("计算时间start，经销商组id：franchiserGroupId：{}" + franchiserGroupId);
            if (null != map.get("expiryTime")) {
                try {
                    Date endDate = (Date) map.get("expiryTime");
                    int dayNumber = UtilDate.getDayNumber(new Date(), endDate);
                    logger.info("剩余的时间：" + dayNumber + "天，经销商组id：franchiserGroupId:{}" + franchiserGroupId);
                    if (dayNumber <= PayModelConstantType.FIRST_CHECK_DAY && dayNumber >= 0) {
                        boolean b1 = this.checkMessage(userId);
                        if (b1) {
                            // 显示
                            flag = true;
                            PayModelConfig payModelConfig = payModelConfigService.get(Integer.valueOf(map.get("payModelConfigId").toString()));
                            String packageName = "";
                            if (payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_GIVE_FRANCHISER)) {
                                packageName = PayModelConstantType.PACKAGE_GIVE;
                            } else {
                                if (String.valueOf(payModelConfig.getTimeType()).equals(PayModelConstantType.TIME_TYPE_MOUTH)) {
                                    packageName = PayModelConstantType.PACKAGE_MONTH;
                                } else {
                                    packageName = PayModelConstantType.PACKAGE_YEAR;
                                }
                            }
                            StringBuffer sb = new StringBuffer();
                            sb.append("您当前享受的“" + packageName + "”服务剩余时间为" + dayNumber);
                            sb.append("天，为了不影响您的使用，请及时充值。");
                            showMsg = sb.toString();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("计算时间异常，内容是：",e);
                }
            }
            logger.info("计算时间end，经销商组id：franchiserGroupId：{}" + franchiserGroupId);
        }
        map.put("isShow", flag);
        map.put("showMsg", showMsg);
        message.setObj(map);
        message.setSuccess(Boolean.TRUE);
        message.setMessage("success");
        return message;
    }

    /**
     * 获取用户可免费渲染的关联信息 =>用户所在企业是否具有免费渲染权限
     *
     * @param sysUser
     * @param basePlatform      平台信息
     * @param companyDomainName 公司品牌网站域名
     * @return
     */
    @Override
    public List<PayModelVo> getPayModelVoRenderList(SysUser sysUser, BasePlatform basePlatform, String companyDomainName) {
        //用户的公司list
        List<CompanyVo> companyVoList = new ArrayList<CompanyVo>();
        CompanyVo companyVo = new CompanyVo();
        if (basePlatform.getPlatformBussinessType().equals(PlatformConstants.TC)) {
            if (StringUtils.isBlank(companyDomainName)) {
                return null;
            }
            String[] arr = companyDomainName.split("//");
            if (arr != null && arr.length > 1) {
                companyDomainName = arr[1];
            }
            arr = companyDomainName.split("\\.");
            logger.info("2c的域名：" + (arr.length == 0 ? companyDomainName : arr[0]));
            Integer companyId = this.payModelGroupRefMapper.getCompanyNameByDoMianName(arr.length == 0 ? companyDomainName : arr[0]);
            if (null == companyId) {
                return null;
            }
            companyVo.setCompanyId(companyId);
            companyVoList.add(companyVo);
        } else {
            if (null != sysUser.getUserType() && sysUser.getUserType().intValue() == UserConstants.FRANCHISER_USER_TYPE) {
                // 经销商
                companyVoList = sysUserService.getFranchiserCompanyVoList(sysUser.getMobile());
            } else if (null != sysUser.getUserType() && sysUser.getUserType().intValue() == UserConstants.MANUFACTURER_USER_TYPE) {
                // 厂商
                companyVoList = sysUserService.getManufacturerCompanyVoList(sysUser.getMobile());
            }
        }
        if (null != companyVoList && companyVoList.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("platformId", basePlatform.getId());
            map.put("companyVoList", companyVoList);
            map.put("packageUserType", PayModelConstantType.BIZ_TYPE); // 所有用户
            map.put("userType", sysUser.getUserType());
            map.put("businessType", PayModelConstantType.BUSINESS_TYPE_RENDER); // 渲染
            return this.payModelGroupRefMapper.getPayModelVoRenderList(map);
        }
        return null;
    }

    /**
     * 获取用户可免费购买户型的关联信息
     *
     * @param sysUser
     * @param platformId        平台id
     * @param companyDomainName 公司品牌网站域名
     * @return
     */
    @Override
    public List<PayModelVo> getPayModelVoHouseList(SysUser sysUser, Integer platformId, String companyDomainName) {
        //用户的公司list
        if (null != sysUser && StringUtils.isNotBlank(companyDomainName)) {
            if (StringUtils.isBlank(companyDomainName)) {
                return null;
            }
            String[] arr = companyDomainName.split("//");
            if (arr != null && arr.length > 1) {
                companyDomainName = arr[1];
            }
            arr = companyDomainName.split("\\.");
            logger.info("2c的域名：" + (arr.length == 0 ? companyDomainName : arr[0]));
            Integer companyId = this.payModelGroupRefMapper.getCompanyNameByDoMianName(arr.length == 0 ? companyDomainName : arr[0]);
            if (null == companyId) {
                return null;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("platformId", platformId);
            map.put("packageUserType", PayModelConstantType.BIZ_TYPE); // 所有用户
            map.put("userType", sysUser.getUserType());
            map.put("businessType", PayModelConstantType.BUSINESS_TYPE_HOUSE); // 户型
            map.put("companyId", companyId);// 公司id
            return this.payModelGroupRefMapper.getPayModelVoHouseList(map);
        }
        return null;
    }

    /**
     * 判断经销商用户的包年包月的权限
     *
     * @param platformId        平台id
     * @param franchiserGroupId 经销商组id
     * @return
     */
    public Map<String, Object> getUserSharePayModelState(Integer platformId, Integer franchiserGroupId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断购买 start
        List<PayModelConfig> payModelConfigList = payModelConfigService.getPayModelConfigList(platformId, PayModelConstantType.RENDER_TYPE_FRANCHISER, PayModelConstantType.RANGE_TYPE_FRANCHISER);
        if (null != payModelConfigList && payModelConfigList.size() > 0) {
            Map<String, Object> refMap = new HashMap<String, Object>();
            refMap.put("list", payModelConfigList);
            refMap.put("businessId", franchiserGroupId);
            refMap.put("rangeType", PayModelConstantType.RANGE_TYPE_FRANCHISER);
            refMap.put("timeType", PayModelConstantType.TIME_TYPE_YEAR); // 1表示年
            List<PayModelGroupRef> payModelGroupRefList = this.payModelGroupRefMapper.getPayModelGroupRefList(refMap);
            if (null != payModelGroupRefList && payModelGroupRefList.size() > 0) {
                // 表示用户存在购买的订单并且时间未过期
                map.put("state", PayModelConstantType.PACKAGE_YEAR_STATE);
                //免费渲染到期的时间
                map.put("expiryTime", payModelGroupRefList.get(0).getExpiryTime());
                //付款方式表id
                map.put("payModelConfigId", payModelGroupRefList.get(0).getPayModelConfigId());
                //付款方式业务关联表id
                map.put("payModelGroupRefId", payModelGroupRefList.get(0).getId());
                return map;
            }
            refMap.put("timeType", PayModelConstantType.TIME_TYPE_MOUTH); // 0表示月
            payModelGroupRefList = this.payModelGroupRefMapper.getPayModelGroupRefList(refMap);
            if (null != payModelGroupRefList && payModelGroupRefList.size() > 0) {
                // 表示用户存在购买的订单并且时间未过期
                map.put("state", PayModelConstantType.PACKAGE_MONTH_STATE);
                //免费渲染到期的时间
                map.put("expiryTime", payModelGroupRefList.get(0).getExpiryTime());
                //付款方式表id
                map.put("payModelConfigId", payModelGroupRefList.get(0).getPayModelConfigId());
                //付款方式业务关联表id
                map.put("payModelGroupRefId", payModelGroupRefList.get(0).getId());
                return map;
            }
        }
        // 判断赠送 start
        payModelConfigList = payModelConfigService.getPayModelConfigList(platformId, PayModelConstantType.RENDER_TYPE_GIVE_FRANCHISER, PayModelConstantType.RANGE_TYPE_FRANCHISER);
        if (null != payModelConfigList && payModelConfigList.size() > 0) {
            Map<String, Object> refMap = new HashMap<String, Object>();
            refMap.put("list", payModelConfigList);
            refMap.put("businessId", franchiserGroupId);
            refMap.put("rangeType", PayModelConstantType.RANGE_TYPE_FRANCHISER);
            List<PayModelGroupRef> payModelGroupRefList = this.payModelGroupRefMapper.getPayModelGroupRefList(refMap);
            if (null != payModelGroupRefList && payModelGroupRefList.size() > 0) {
                // 表示用户存在购买的订单并且时间未过期
                map.put("state", PayModelConstantType.GIVE_STATE);
                //免费渲染到期的时间
                map.put("expiryTime", payModelGroupRefList.get(0).getExpiryTime());
                //付款方式表id
                map.put("payModelConfigId", payModelGroupRefList.get(0).getPayModelConfigId());
                //付款方式业务关联表id
                map.put("payModelGroupRefId", payModelGroupRefList.get(0).getId());
                return map;
            }
        }
        // 判断赠送 end
        map.put("state", PayModelConstantType.NOT_PERMISSION_STATE);
        //免费渲染到期的时间
        map.put("expiryTime", null);
        //付款方式表id
        map.put("payModelConfigId", null);
        //付款方式业务关联表id
        map.put("payModelGroupRefId", null);
        return map;
    }


    /**
     * 判断用户权限(个人)
     *
     * @param platformId 平台id
     * @param userId     用户id
     * @return
     */
    public Map<String, Object> getUserPayModelState(Integer platformId, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断购买 start
        List<PayModelConfig> payModelConfigList = payModelConfigService.getPayModelConfigList(platformId, PayModelConstantType.RENDER_TYPE, PayModelConstantType.RANGER_TYPE_PERSONAGE);
        if (null != payModelConfigList && payModelConfigList.size() > 0) {
            Map<String, Object> refMap = new HashMap<String, Object>();
            refMap.put("list", payModelConfigList);
            refMap.put("businessId", userId);
            refMap.put("rangeType", PayModelConstantType.RANGER_TYPE_PERSONAGE);
            refMap.put("timeType", PayModelConstantType.TIME_TYPE_YEAR); // 1表示年
            List<PayModelGroupRef> payModelGroupRefList = this.payModelGroupRefMapper.getPayModelGroupRefList(refMap);
            logger.info("付款方式业务关联表数据：payModelGroupRefList：{}" + (null == payModelGroupRefList ? null : gson.toJson(payModelGroupRefList)));
            if (null != payModelGroupRefList && payModelGroupRefList.size() > 0) {
                // 表示用户存在购买的订单并且时间未过期
                map.put("state", PayModelConstantType.PACKAGE_YEAR_STATE);
                //免费渲染到期的时间
                map.put("expiryTime", payModelGroupRefList.get(0).getExpiryTime());
                //付款方式表id
                map.put("payModelConfigId", payModelGroupRefList.get(0).getPayModelConfigId());
                //付款方式业务关联表id
                map.put("payModelGroupRefId", payModelGroupRefList.get(0).getId());
                return map;
            }
            refMap.put("timeType", PayModelConstantType.TIME_TYPE_MOUTH); // 0表示月
            payModelGroupRefList = this.payModelGroupRefMapper.getPayModelGroupRefList(refMap);
            logger.info("付款方式业务关联表数据：payModelGroupRefList：{}" + (null == payModelGroupRefList ? null : gson.toJson(payModelGroupRefList)));
            if (null != payModelGroupRefList && payModelGroupRefList.size() > 0) {
                // 表示用户存在购买的订单并且时间未过期
                map.put("state", PayModelConstantType.PACKAGE_MONTH_STATE);
                //免费渲染到期的时间
                map.put("expiryTime", payModelGroupRefList.get(0).getExpiryTime());
                //付款方式表id
                map.put("payModelConfigId", payModelGroupRefList.get(0).getPayModelConfigId());
                //付款方式业务关联表id
                map.put("payModelGroupRefId", payModelGroupRefList.get(0).getId());
                return map;
            }
        }
        // 判断购买 end
        // 判断赠送 start
        payModelConfigList = payModelConfigService.getPayModelConfigList(platformId, PayModelConstantType.RENDER_TYPE_GIVE, PayModelConstantType.RANGER_TYPE_PERSONAGE);
        if (null != payModelConfigList && payModelConfigList.size() > 0) {
            Map<String, Object> refMap = new HashMap<String, Object>();
            refMap.put("list", payModelConfigList);
            refMap.put("businessId", userId);
            refMap.put("rangeType", PayModelConstantType.RANGER_TYPE_PERSONAGE);
            List<PayModelGroupRef> payModelGroupRefList = this.payModelGroupRefMapper.getPayModelGroupRefList(refMap);
            logger.info("付款方式业务关联表数据：payModelGroupRefList：{}" + (null == payModelGroupRefList ? null : gson.toJson(payModelGroupRefList)));
            if (null != payModelGroupRefList && payModelGroupRefList.size() > 0) {
                // 表示用户存在购买的订单并且时间未过期
                map.put("state", PayModelConstantType.GIVE_STATE);
                //免费渲染到期的时间
                map.put("expiryTime", payModelGroupRefList.get(0).getExpiryTime());
                //付款方式表id
                map.put("payModelConfigId", payModelGroupRefList.get(0).getPayModelConfigId());
                //付款方式业务关联表id
                map.put("payModelGroupRefId", payModelGroupRefList.get(0).getId());
                return map;
            }
        }

        /**
         * 增加B端企业赠送包年套餐逻辑
         * add BY qtq 2018-6-22
         */
        PayModelConfig p = payModelConfigService.getPayModelConfigByBizType("2bCompanyGiveRenderPlan");
        if (p != null) {
            Map<String, Object> m = this.buildReturnMap(userId,p.getId());
            if(null != m){
                return m;
            }
        }

        /**
         * 增加B端套餐用户免费渲染逻辑
         * add BY qtq 2018-7-3
         * 前面逻辑实在加不动,只能新增逻辑
         */
        PayModelConfig pc = payModelConfigService.getPayModelConfigByBizType("2bPackageUserGiveRender");
        if (pc != null) {
            Map<String, Object> m = buildReturnMap(userId, pc.getId());
            if(null != m){
                return m;
            }
        }


        // 判断赠送 end
        map.put("state", PayModelConstantType.NOT_PERMISSION_STATE);
        //免费渲染到期的时间
        map.put("expiryTime", null);
        //付款方式表id
        map.put("payModelConfigId", null);
        //付款方式业务关联表id
        map.put("payModelGroupRefId", null);
        return map;
    }

    private Map<String,Object> buildReturnMap(Integer userId, Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        PayModelGroupRef payModelGroupRef = payModelGroupRefMapper.selectByBusinessIdAndPayModelConfigId(new Long(userId), id);
        if (payModelGroupRef != null && new Date().compareTo(payModelGroupRef.getExpiryTime()) < 0) {
            map.put("state", PayModelConstantType.GIVE_STATE);
            map.put("expiryTime", payModelGroupRef.getExpiryTime());
            map.put("payModelConfigId", id);
            map.put("payModelGroupRefId", payModelGroupRef.getId());
            return map;
        }
        return null;
    }

    /**
     * 判断点击按钮是否需要提示
     *
     * @param userId
     * @return
     */
    public boolean checkMessage(Integer userId) {
        logger.info("判断点击按钮是否需要提示start,用户id：userId:{}" + userId);
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
        String str = time.format(nowTime) + userId;
        Map map = payRedisService.getMap(str);
        if (null == map || map.size() == 0) {
            boolean flag = payRedisService.addMap(str, str, str);
            logger.info("保存redis中是否成功？" + flag + ",用户id：userId:{}" + userId);
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now();
            LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
            LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
            // 现在时间到今晚凌晨的秒数
            long seconds = TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
            payRedisService.expire(str, Math.toIntExact(seconds));
            logger.info("判断点击按钮是否需要提示end,用户id：userId:{}" + userId + ",计算的秒数为：" + seconds);
            return true;
        }
        return false;
    }

    /**
     * 普通用户或者经销商用户渲染的时候获取的产品名称（假如用户具备包年、包月、赠送，则按照时间最早的来取）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    @Override
    public String getRenderPackageName(Integer businessId, Integer isFranchiser) {
        return this.payModelGroupRefMapper.getRenderPackageName(businessId, isFranchiser);
    }

    /**
     * 获取付款方式关联表id（假如用户具备包年、包月、赠送，则按照时间最迟的来取）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    @Override
    public Integer getPayModelGroupRefId(Integer businessId, Integer isFranchiser) {
        return this.payModelGroupRefMapper.getPayModelGroupRefId(businessId, isFranchiser);
    }

    /**
     * pc端获取用户的套餐信息
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    @Override
    public PackageInfoVo getPackageInfoVoPc(Integer businessId, Integer isFranchiser) {
        return this.payModelGroupRefMapper.getPackageInfoVoPc(businessId, isFranchiser);
    }

    /**
     * 获取用户的渲染套餐（包含过期）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    @Override
    public List<PayModelInfoVo> getPayModelVoList(Integer businessId, Integer isFranchiser) {
        return this.payModelGroupRefMapper.getPayModelVoList(businessId, isFranchiser);
    }

    /**
     * 获取用户的套餐信息（未过期的套餐）
     *
     * @param businessId   业务id
     * @param isFranchiser 是否属于经销商子主账号 0否 1是
     * @return
     */
    @Override
    public List<PackageInfoVo> getPackageInfoVoList(Integer businessId, Integer isFranchiser) {
        return this.payModelGroupRefMapper.getPackageInfoVoList(businessId, isFranchiser);
    }

    @Override
    public List<Map<String, Object>> getUserRefInfoList2C(Integer businessId, String renderType2c) {
        return payModelGroupRefMapper.getUserRefInfoList2C(businessId, renderType2c);
    }

    @Override
    public void addPayModelGroupRef2C(PayOrder payOrder) {
        logger.info("回调成功，插入或者更新关联表信息，用户id：userId:{}" + payOrder.getUserId());
        Integer businessId = StringUtils.isEmpty(payOrder.getAtt1()) == true ? null : Integer.valueOf(payOrder.getAtt1());
        Integer payModelConfigId = StringUtils.isEmpty(payOrder.getAtt2()) == true ? null : Integer.valueOf(payOrder.getAtt2());

        SysUser sysUser = sysUserService.get(payOrder.getUserId());

        this.updateModelGroupRef(sysUser, payModelConfigId, businessId, payOrder);
    }

    private void updateModelGroupRef(SysUser sysUser, Integer payModelConfigId, Integer businessId, PayOrder payOrder) {

        //根据套餐信息
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigId);

        //获取出用户已开通的套餐信息
        PayModelGroupRef payModelGroupRef = payModelGroupRefMapper.getPayModelVoListByBusinessId(businessId);

        //更新套餐有效时间
        this.updateEfftiveTime(payModelConfig, payModelGroupRef, payModelConfigId, businessId, sysUser);
    }

    private void updateEfftiveTime(PayModelConfig payModelConfig, PayModelGroupRef payModelGroupRef, Integer payModelConfigId, Integer businessId, SysUser sysUser) {
        logger.info("开始处理插入包年包月套餐信息 =>{} payModelConfigId,businessId" + payModelConfigId + "," + businessId);
        if (payModelGroupRef == null) {
            //不存在,直接插入记录
            savePayModelGroupRef(payModelConfig, sysUser, null);
        } else {
            //判断判断是否过期
            if (new Date().compareTo(payModelGroupRef.getExpiryTime()) > 0) {
                //包年包月已过期
                savePayModelGroupRef(payModelConfig, sysUser, payModelGroupRef.getExpiryTime());
            } else {
                //没有过期
                savePayModelGroupRef(payModelConfig, sysUser, payModelGroupRef.getExpiryTime());
            }
        }

    }

    private void savePayModelGroupRef(PayModelConfig payModelConfig, SysUser sysUser, Date expiryTime) {
        int month = 1;
        if (payModelConfig.getTimeType().intValue() == 1) {
            //包年
            month = 12;
        }
        Calendar calendar = new GregorianCalendar();
        if (expiryTime != null && new Date().compareTo(expiryTime) < 0) {
            calendar.setTime(expiryTime);
        }
        calendar.add(calendar.MONTH, month);
        PayModelGroupRef payModelGroupRef = new PayModelGroupRef();
        payModelGroupRef.setBusinessId(sysUser.getId());
        payModelGroupRef.setPayModelConfigId(payModelConfig.getId());
        payModelGroupRef.setExpiryTime(calendar.getTime());
        if (expiryTime != null && new Date().compareTo(expiryTime) < 0) {
            payModelGroupRef.setEffectiveTime(expiryTime);
        } else {
            payModelGroupRef.setEffectiveTime(new Date());
        }
        payModelGroupRef.setIsDeleted(0);
        payModelGroupRef.setCreator(sysUser.getMobile());
        payModelGroupRef.setGmtCreate(new Date());
        payModelGroupRef.setModifier(sysUser.getMobile());
        payModelGroupRef.setGmtModified(new Date());
        int i = this.payModelGroupRefMapper.insertSelective(payModelGroupRef);
        if (i == 1) {
            logger.info("用户付款成功，插入付款方式关联表记录成功");
        }
    }


    @Override
    public List<PayModelVo> getPayModelVoRenderList2C(Long userId) {

        //获取用户信息
        SysUser user = sysUserService.get(userId.intValue());
        if (user == null) {
            logger.error("用户信息为空");
            throw new BizException(ExceptionCodes.CODE_10010013, "用户信息为空");
        }
        //获取用户包年包月套餐
        return payModelGroupRefMapper.getPayModelVoRenderList2CBybusinessId(user.getId());
    }

    @Override
    public boolean checkUserPackages(Integer userId) {

        //校验企业是否买断渲染
        PayModelGroupRef companyP = checkCompanyBuyRender(new Long(userId));

        if (companyP != null) {
            return true;
        }
        PayModelGroupRef payModelGroupRef = payModelGroupRefMapper.selectByBusinessId(userId);

        if (payModelGroupRef == null) {
            throw new BizException(ExceptionCodes.CODE_10010014, "您没有开通包年包月套餐!");
        }

        if (new Date().compareTo(payModelGroupRef.getExpiryTime()) > 0) {
            throw new BizException(ExceptionCodes.CODE_10010015, "你的包年包月套餐已过期");
        }
        logger.info("C端用户具有包年包月权限 =>END");
        return true;
    }

    @Override
    public PayModelGroupRef checkCompanyGivePackage(Long companyId) {
        logger.info("检验B端企业是否有赠送包年套餐begin");
        PayModelConfig pmf = payModelConfigService.getPayModelConfigByBizType("2bCompanyGiveRenderPlan");
        if (pmf != null) {
            //企业是否开通了该套餐
            PayModelGroupRef p = payModelGroupRefMapper.selectByBusinessIdAndPayModelConfigId(companyId, pmf.getId());
            if (p != null && new Date().compareTo(p.getExpiryTime()) < 0) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void add2bCompanyGivePackage(Long userId, PayModelGroupRef p) {
        logger.info("B端用户赠送插入用户包年套餐begin");
        //检验用户是否已经插入过数据
        PayModelGroupRef isExitsP = payModelGroupRefMapper.selectByBusinessIdAndPayModelConfigId(userId, p.getId());

        Date ExpiryTime = null;

        if (null == isExitsP) {
            //获取用户是否存在套餐
            PayModelGroupRef pmgr = payModelGroupRefMapper.getByBusinessId(userId.intValue());
            if (pmgr != null && new Date().compareTo(pmgr.getExpiryTime()) < 0) {
                ExpiryTime = pmgr.getExpiryTime();
            }
        }
        //用户不存在套餐
        this.bulidPayModelGroupRef(userId, 12 ,p.getPayModelConfigId(), ExpiryTime);

    }

    private int bulidPayModelGroupRef(Long userId, int month,Integer payModelConfigId, Date expiryTime) {
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (sysUser == null) {
            throw new BizException(ExceptionCodes.CODE_10010013, "查找用户信息失败");
        }

        Calendar calendar = new GregorianCalendar();
        if (expiryTime != null) {
            calendar.setTime(expiryTime);
        }
        calendar.add(calendar.MONTH, month);

        Date now = new Date();
        PayModelGroupRef newP = new PayModelGroupRef();
        newP.setBusinessId(userId.intValue());
        newP.setPayModelConfigId(payModelConfigId);
        newP.setCreator(sysUser.getMobile());
        newP.setGmtCreate(now);
        newP.setGmtModified(now);
        newP.setModifier(sysUser.getMobile());
        newP.setIsDeleted(0);
        newP.setEffectiveTime(now);
        newP.setExpiryTime(calendar.getTime());
        return payModelGroupRefMapper.insertSelective(newP);
    }

    @Override
    public PayModelGroupRef checkCompanyBuyRender(Long userId) {
        SysUser sysUser = sysUserService.get(userId.intValue());
        logger.info("C端检查用户所在企业是否买断渲染begin{appId} =>{}"+sysUser.getAppId());
        PayModelConfig pmc = payModelConfigService.getPayModelConfigByBizType("companyFreeRenderPlanMinPro");
        if (pmc != null) {
            PayModelGroupRef p = payModelGroupRefMapper.selectByBusinessIdAndPayModelConfigId(new Long(sysUser.getMiniProgramCompanyId()), pmc.getId());
            if (p != null && new Date().compareTo(p.getExpiryTime()) < 0 && Objects.equals(sysUser.getAppId(),p.getAppId())) {
                logger.info("企业包年app_id =>{}"+p.getAppId());
                //C端企业存在包年套餐
                return p;
            }
        }
        return null;
    }

    @Override
    public int add2bUserPackageGiveRender(Long userId, Integer month) {
        logger.info("b端套餐用户赠送免费渲染:用户Id =>{},赠送月份数 =>{}"+userId+","+month);
        PayModelConfig pmc = payModelConfigService.getPayModelConfigByBizType("2bPackageUserGiveRender");

        SysUser sysUser = sysUserService.get(userId.intValue());

        PayModelGroupRef isExitsP = payModelGroupRefMapper.selectByBusinessIdAndPayModelConfigId(userId, pmc.getId());

        if(pmc != null && null == isExitsP){
            //插入套餐
            Date expiryTime = null;
            PayModelGroupRef pmgr = payModelGroupRefMapper.getByBusinessId(userId.intValue());
            if (pmgr != null && new Date().compareTo(pmgr.getExpiryTime()) < 0) {
                expiryTime = pmgr.getExpiryTime();
            }
            return bulidPayModelGroupRef(new Long(sysUser.getId()),month,pmc.getId(),expiryTime);
        }
        return 0;
    }

    @Override
    public boolean updatePackageGiveRender(Long userId, Integer month) {
        logger.info("套餐用户升级赠送免费渲染:用户Id =>{},赠送月份数 =>{}"+userId+","+month);
        PayModelConfig pmc = payModelConfigService.getPayModelConfigByBizType("2bPackageUserGiveRender");

        Date expiryTime = null;
        PayModelGroupRef pmgr = payModelGroupRefMapper.getByBusinessId(userId.intValue());
        if (pmgr != null && new Date().compareTo(pmgr.getExpiryTime()) < 0) {
            expiryTime = pmgr.getExpiryTime();
        }
        int row = bulidPayModelGroupRef(userId, month, pmc.getId(), expiryTime);
        if (row >0){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
