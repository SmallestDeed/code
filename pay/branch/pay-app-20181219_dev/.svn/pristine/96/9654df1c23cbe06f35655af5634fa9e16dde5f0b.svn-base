package com.sandu.system.controller;

import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.constant.SystemCommonConstant;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.model.StatusCodeConstant;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.UtilDate;
import com.sandu.common.util.Utils;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.metadata.PayModelConstantType;
import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.vo.PackageInfoVo;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserFinanceService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @version V1.0
 * @Title: SysUserController.java
 * @Package com.sandu.system.controller
 * @Description:系统-用户Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
@Controller
@RequestMapping("/v1/web/system/sysUser")
public class WebSysUserController {

    String cacheEnable = Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE, "0");
    private static Logger logger = Logger.getLogger(WebSysUserController.class);
    private static final String CLASS_LOG_PREFIX = "[支付用户服务]";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserFinanceService userFinanceService;
    @Resource
    private BasePlatformService basePlatformService;
    @Resource
    private PayModelGroupRefService payModelGroupRefService;
    @Resource
    private PayAccountService payAccountService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 获取用户余额
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserBalance")
    @ResponseBody
    public Object getUserBalance(HttpServletRequest request,
                                 @RequestParam(value = "msgId", required = false) String msgId) {
        //从请求头中获取token
        Long userId = (Long) request.getAttribute("tokenUserId");
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(msgId);
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (null == sysUser) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        double balanceAmount = 0;
        double consumAmount = 0;
        Map<String, String> map = new HashMap<>();
        String companyDomainName = request.getHeader("Referer");// 获取域名
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(sysUser, basePlatform, companyDomainName);
        boolean isfranchiserGroup = false;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
        if (null == companyFranchiserGroup) {
            PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
            if (null != payAccount) {
                balanceAmount = payAccount.getBalanceAmount();
                consumAmount = Optional.ofNullable(payAccount.getConsumAmount()).isPresent() ? payAccount.getConsumAmount():0;
            }
        } else {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0d : companyFranchiserGroup.getCommonalityIntegral().doubleValue();
            consumAmount = null == companyFranchiserGroup.getConsumeIntegral() ? 0d : companyFranchiserGroup.getConsumeIntegral().doubleValue();
            isfranchiserGroup = true;
        }
        if (null == payModelVoList || payModelVoList.size() == 0) {
            //个人套餐信息这里，和产品罗春意沟通，如果存在多个套餐，显示“渲染套餐”，如果只存在一个套餐，则显示套餐名称
            List<PackageInfoVo> packageInfoVoList = null;
            if (isfranchiserGroup) {
                packageInfoVoList = payModelGroupRefService.getPackageInfoVoList(sysUser.getFranchiserGroupId(), 1);
            } else {
                packageInfoVoList = payModelGroupRefService.getPackageInfoVoList(sysUser.getId(), 0);
            }
            if (null == packageInfoVoList || packageInfoVoList.size() == 0) {
                map.put("state", PayModelConstantType.NOT_PERMISSION_STATE);
                map.put("expiryTime", "");
                map.put("enjoyPackageInfo", "渲染套餐");
            } else if (packageInfoVoList.size() == 1){
                map.put("state", packageInfoVoList.get(0).getState() + "");
                map.put("expiryTime", UtilDate.ConverToStringYMD(packageInfoVoList.get(0).getMaxExpiryTime()));
                map.put("enjoyPackageInfo", packageInfoVoList.get(0).getEnjoyPackageInfo());
            } else {
                map.put("state", PayModelConstantType.FIVE_STATE);
                map.put("expiryTime", UtilDate.ConverToStringYMD(packageInfoVoList.get(0).getMaxExpiryTime()));
                map.put("enjoyPackageInfo", "渲染套餐");
            }
        } else {
            // 用户可以免费渲染
            map.put("state", PayModelConstantType.FREE_STATE);
            map.put("expiryTime", UtilDate.ConverToStringYMD(payModelVoList.get(0).getExpiryTime()));
            map.put("enjoyPackageInfo", "包月套餐");
        }
        map.put("balanceIntegral", balanceAmount / 10 + ""); // 剩余金额
        map.put("consumAmount", consumAmount / 10 + ""); // 消费金额
        //查询用户可使用户型套数
        Integer userValidHouseCount = userFinanceService.queryUserValidHouseCount(userId.intValue());
        map.put("usableHouseNumber", String.valueOf(userValidHouseCount));
        responseEnvelope.setStatus(Boolean.TRUE);
        responseEnvelope.setObj(map);
        responseEnvelope.setFlag(true);
        return responseEnvelope;
    }

    /**
     * 获取用户余额----PC专用
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserBalancePc")
    @ResponseBody
    public Object getUserBalancePc(HttpServletRequest request,
                                   @RequestParam(value = "msgId", required = false) String msgId) {
        logger.info("进入获取用户余额的方法（pc专用）：getUserBalancePc====start");
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(msgId);
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (null == sysUser) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        double balanceAmount = 0;
        double consumAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
        String companyDomainName = request.getHeader("Referer");// 获取域名
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(sysUser, basePlatform, companyDomainName);
        boolean flag = false;
        if (null == companyFranchiserGroup) {
            PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
            if (null != payAccount) {
                balanceAmount = payAccount.getBalanceAmount();
                consumAmount = payAccount.getConsumAmount();
            }
        } else {
            flag = true;
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0d : companyFranchiserGroup.getCommonalityIntegral().doubleValue();
            consumAmount = null == companyFranchiserGroup.getConsumeIntegral() ? 0d : companyFranchiserGroup.getConsumeIntegral().doubleValue();
        }
        if (null == payModelVoList || payModelVoList.size() == 0) {
            PackageInfoVo packageInfoVo = null;
            if (flag) {
                // 用户属于经销商
                packageInfoVo = payModelGroupRefService.getPackageInfoVoPc(sysUser.getFranchiserGroupId(), 1);
            } else {
                // 普通用户
                packageInfoVo = payModelGroupRefService.getPackageInfoVoPc(sysUser.getId(), 0);
            }
            if (null != packageInfoVo && null != packageInfoVo.getRemainDate() && StringUtils.isNotBlank(packageInfoVo.getEnjoyPackageInfo())) {
                sysUser.setEnjoyPackageInfo(packageInfoVo.getEnjoyPackageInfo()); // 当前享受的服务信息
                sysUser.setRemainDate(packageInfoVo.getRemainDate()); // 剩余时长
            }
        } else {
            // 用户可以免费渲染
            StringBuffer sb = new StringBuffer();
            sb.append("包月套餐");
            sb.append("(");
            sb.append("以上服务总共有效期至" + UtilDate.getStringDate(payModelVoList.get(0).getExpiryTime()));
            sb.append(")");
            int remainDate = UtilDate.getDayNumber(new Date(), payModelVoList.get(0).getExpiryTime());
            sysUser.setRemainDate(remainDate);
            sysUser.setEnjoyPackageInfo(sb.toString());
        }
        sysUser.setBalanceAmount(balanceAmount); // 用户余额
        sysUser.setConsumAmount(consumAmount); // 消费总金额
        responseEnvelope.setMsgId(msgId);
        responseEnvelope.setObj(sysUser);
        responseEnvelope.setMessage("查询用户余额成功!");
        responseEnvelope.setFlag(true);
        return responseEnvelope;
    }


    /**
     * 获取账户余额（开通移动端的付费页面获取）
     *
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserBalanceAmount")
    @ResponseBody
    public Object getUserBalanceAmount(HttpServletRequest request, Integer userId, String msgId) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(msgId);
        if (null == userId) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("用户id为空");
            return responseEnvelope;
        }
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        double balanceAmount = 0;
        double consumAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
        if (null == companyFranchiserGroup) {
            PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
            if (null != payAccount) {
                balanceAmount = payAccount.getBalanceAmount();
                consumAmount = payAccount.getConsumAmount();
            }
        } else {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0d : companyFranchiserGroup.getCommonalityIntegral().doubleValue();
            consumAmount = null == companyFranchiserGroup.getConsumeIntegral() ? 0d : companyFranchiserGroup.getConsumeIntegral().doubleValue();
        }
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_INTEGRAL_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_INTEGRAL_KEY);
        String dredgeIntegral = "0";
        if (null != sysDictionary && null != sysDictionary.getAtt1()) {
            dredgeIntegral = sysDictionary.getAtt1();
        }
        Map<String, String> map = new HashMap<>();
        map.put("dredgeIntegral", Double.valueOf(Double.valueOf(dredgeIntegral) / 10).intValue() + ""); // 开通移动端所需要的度币
        map.put("balanceIntegral", balanceAmount / 10 + ""); // 剩余金额
        map.put("consumAmount", consumAmount / 10 + ""); // 消费金额
        //查询用户可使用户型套数
        Integer userValidHouseCount = userFinanceService.queryUserValidHouseCount(userId.intValue());
        map.put("usableHouseNumber", String.valueOf(userValidHouseCount));
        responseEnvelope.setStatus(Boolean.TRUE);
        responseEnvelope.setObj(map);
        responseEnvelope.setFlag(true);
        return responseEnvelope;
    }

}
