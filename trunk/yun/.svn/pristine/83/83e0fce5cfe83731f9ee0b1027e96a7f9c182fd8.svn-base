package com.sandu.pay.controller;

import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.model.PayOrderModel;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.order.vo.PayModelConfigVo;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/agency/pay")
public class AgencyPayController {

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PayModelGroupRefService payModelGroupRefService;

    @Autowired
    private PayOrderService payOrderService;

    /**
     * 同城联盟购买包年包月接口（支付宝和微信APP支付）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/agencyPayModelByAppPay")
    public Object agencyPayModelByAppPay(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (null == payModelConfigVo || StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空：payModelConfigVo:{}");
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
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, companyDomainName);
        if (payModelVoList != null && payModelVoList.size() > 0) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("企业已付费，过期后可购买");
            return responseEnvelope;
        }
        ResultMessage message = payOrderService.agencyPayModelByAppPay(payModelConfigVo, userId.intValue(), basePlatform.getId());
        //ResultMessage message = payOrderService.rechargePayModelByAppPay(payModelConfigVo, userId.intValue(), basePlatform.getId());
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 同城度币充值（微信APP支付，支付宝APP支付）
     *
     * @param request
     * @param payOrderModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/agencyRechargeIntegralByAppPay")
    public Object agencyRechargeIntegralByAppPay(HttpServletRequest request, @RequestBody PayOrderModel payOrderModel) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payOrderModel) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付类型为空：payType:{}" + payOrderModel.getPayType());
            return responseEnvelope;
        }
        if (null == payOrderModel.getProductId()) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("产品id为空");
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.agencyRechargeIntegralByAppPay(userId.intValue(), payOrderModel, platformCode);
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }
}
