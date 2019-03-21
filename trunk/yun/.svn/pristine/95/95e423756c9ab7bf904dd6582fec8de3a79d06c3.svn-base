package com.sandu.pay.controller;

import com.google.gson.Gson;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.metadata.PayModelConstantType;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.service.PayModelConfigService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.vo.PayModelConfigVo;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserJurisdiction;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserJurisdictionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 付款方式表对应的controller
 *
 * @Author yzw
 * @Date 2018/1/16 15:43
 */
@Controller
@RequestMapping("/v1/web/pay/payModelConfig")
public class PayModelConfigController {

    private static Logger logger = LogManager.getLogger(PayModelConfigController.class);
    private final static Gson gson = new Gson();
    @Resource
    private PayModelConfigService payModelConfigService;
    @Resource
    private PayModelGroupRefService payModelGroupRefService;
    @Resource
    private BasePlatformService basePlatformService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;
    @Resource
    private UserJurisdictionService userJurisdictionService;

    /**
     * 判断是否具备渲染权限接口
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkRenderGroopRef")
    public Object addMobileLoginPay(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        responseEnvelope.setMsgId(null != payModelConfigVo ? payModelConfigVo.getMsgId() : null);

        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("payModelGroupRefId", payModelVoList.get(0).getPayModelGroupRefId());
        map.put("payModelConfigId", 21);
        map.put("expiryTime", "2022-12-12 12:12:12");
        map.put("state", PayModelConstantType.GIVE_STATE); // 这里先返回1，理论上应返回4
        map.put("isShow", Boolean.FALSE);
        map.put("showMsg", "");
        responseEnvelope.setStatus(Boolean.TRUE);
        responseEnvelope.setMessage("用户具备免费渲染的权限");
        responseEnvelope.setObj(map);
        responseEnvelope.setSuccess(Boolean.TRUE);
        responseEnvelope.setFlag(Boolean.TRUE);
        return responseEnvelope;

/*
         if (null == payModelConfigVo) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("参数错误：payModelConfigVo：{}");
            logger.info("参数错误");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String companyDomainName = request.getHeader("Referer");// 获取域名
        if (StringUtils.isNotBlank(payModelConfigVo.getCompanyDomainName())
                && basePlatform.getPlatformBussinessType().equals(PlatformConstants.TC)) {
            companyDomainName = payModelConfigVo.getCompanyDomainName();
        }
        //判断用户所在企业是否具备免费渲染 =>start
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, companyDomainName);
        if (payModelVoList != null && payModelVoList.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("payModelGroupRefId", payModelVoList.get(0).getPayModelGroupRefId());
            map.put("payModelConfigId", payModelVoList.get(0).getPayModelConfigId());
            map.put("expiryTime", payModelVoList.get(0).getExpiryTime());
            map.put("state", PayModelConstantType.GIVE_STATE); // 这里先返回1，理论上应返回4
            map.put("isShow", Boolean.FALSE);
            map.put("showMsg", "");
            responseEnvelope.setStatus(Boolean.TRUE);
            responseEnvelope.setMessage("用户具备免费渲染的权限");
            responseEnvelope.setObj(map);
            responseEnvelope.setSuccess(Boolean.TRUE);
            responseEnvelope.setFlag(Boolean.TRUE);
            return responseEnvelope;
        }
        //判断用户所在企业是否具备免费渲染 =>end

        //判断个人套餐
        platformCode = PlatformConstants.PC_2B; // 移动2和pc端都用pc端的平台编码，之前有需求pc端购买后，移动端也是可以渲染
        basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        ResultMessage message = new ResultMessage();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            message = payModelGroupRefService.checkRenderGroopRef(basePlatform.getId(), userId.intValue());
        } else {
            message = payModelGroupRefService.checkRenderShareGroopRef(basePlatform.getId(), companyFranchiserGroup.getId(), userId.intValue());
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;*/
    }

    /**
     * 获取渲染付款方式信息列表
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRenderPayConfigList")
    public Object getRenderPayConfigList(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = PlatformConstants.PC_2B; // 判断权限，这里的平台编码写死
        responseEnvelope.setMsgId(null != payModelConfigVo ? payModelConfigVo.getMsgId() : null);
        if (null == payModelConfigVo || StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("参数错误：payModelConfigVo：{}" + (null == payModelConfigVo ? null : gson.toJson(payModelConfigVo)));
            logger.info("参数错误");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            logger.info("当前用户信息为空：user:{} null,用户id：userId:{}" + userId);
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        List<Map<String, Object>> rnderPayConfigList = new ArrayList<Map<String, Object>>();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            rnderPayConfigList = payModelConfigService.getRenderPayConfigList(basePlatform.getId(), PayModelConstantType.RENDER_TYPE, PayModelConstantType.RANGER_TYPE_PERSONAGE);
        } else {
            rnderPayConfigList = payModelConfigService.getRenderPayConfigList(basePlatform.getId(), PayModelConstantType.RENDER_TYPE_FRANCHISER, PayModelConstantType.RANGE_TYPE_FRANCHISER);
        }
        responseEnvelope.setStatus(Boolean.TRUE);
        responseEnvelope.setMessage("success");
        responseEnvelope.setObj(rnderPayConfigList);
        responseEnvelope.setSuccess(Boolean.TRUE);
        responseEnvelope.setFlag(Boolean.TRUE);
        return responseEnvelope;
    }


    /**
     * 新用户第一次登陆时候赠送
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/addGiveGroopRef")
    public Object addGiveGroopRef(HttpServletRequest request) {
        logger.info("绑定序列号时候赠送start");
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = PlatformConstants.PC_2B;
        if (StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("平台编码为空：platformCode：{}" + platformCode);
            logger.info("平台编码为空：platformCode：{}");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            logger.info("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            logger.info("当前用户信息为空");
            return responseEnvelope;
        }
        // 判断是否有pc端登录权限
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(user.getId(), basePlatform.getId());
        if (null == userJurisdiction) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前账号不具备pc端权限，不符合赠送条件");
            logger.info("当前账号不具备pc端权限，不符合赠送条件");
            return responseEnvelope;
        }
        //只是赠送给经销商  ----注释掉这些表示赠送所有的用户 2018-4-25 wutehua
       /* if (null == user.getUserType() || (user.getUserType().intValue() != UserConstants.FRANCHISER_USER_TYPE
                && user.getUserType().intValue() != UserConstants.MANUFACTURER_USER_TYPE)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户不属于经销商或者厂商，不符合赠送条件");
            logger.info("当前用户不属于经销商或者厂商，不符合赠送条件");
            return responseEnvelope;
        }*/
        ResultMessage message = payModelGroupRefService.addGiveGroopRef(basePlatform, user);
        logger.info("绑定序列号时候赠送end，结果是：" + message.getMessage() + "用户id：" + user.getId());
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }
}
