package com.sandu.pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sandu.common.LoginContext;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.common.util.StringUtils;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay2.service.Pay2Service;
import com.sandu.user.model.LoginUser;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/2
 * @since : sandu_yun_1.0
 */
@Controller
@RequestMapping("/v1/web/pay/miniProPayOrder")
public class PayController {

    private static Logger logger = LogManager.getLogger(PayController.class);

    @Autowired
    private PayOrderService payOrderService;
    /**
     * 小程序商城订单支付
     *
     * @param request
     * @param orderNo
     * @param payMethod
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/mallOrderPaying",method = RequestMethod.POST)
    @ResponseBody
    public Object mallOrderPaying(HttpServletRequest request, String orderNo, String payMethod) {
        if (StringUtils.isBlank(orderNo)) {
            return new ResponseEnvelope(true, "orderNo不能为空", null, false);
        }
        String platformCode = request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不能为空", null, false);
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            Pay2Service pay2Service = SpringContextHolder.getBean(payMethod);
            Object obj = pay2Service.mallOrderPaying(orderNo, loginUser.getId(), platformCode);
            return new ResponseEnvelope(obj, null, true);
        } catch (BizException e) {
            return new ResponseEnvelope(true, e.getMessage(), null, false);
        } catch (Exception e) {
            logger.error("系统繁忙:", e);
            return new ResponseEnvelope(true, "系统繁忙", null, false);
        }
    }
    
   
    /**
     * 度币充值
     *
     * @param request
     * @param productId
     * @param payMethod
     * @return
     */
    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    @ResponseBody
    public Object recharge(HttpServletRequest request, Integer productId, String payMethod) {
        if (null == productId) {
            return new ResponseEnvelope(true, "productId不能为空", null, false);
        }
        String platformCode = request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不能为空", null, false);
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            Pay2Service pay2Service = SpringContextHolder.getBean(payMethod);
            Object obj = pay2Service.recharge(productId, loginUser.getId(), platformCode);
            return new ResponseEnvelope(obj, null, true);
        } catch (BizException e) {
            return new ResponseEnvelope(true, e.getMessage(), null, false);
        } catch (Exception e) {
            logger.error("系统繁忙:", e);
            return new ResponseEnvelope(true, "系统繁忙", null, false);
        }
    }

    /**
     *
     *包年包月套餐
     * @param request
     * @param payModelConfigId
     * @param payMethod
     * @return
     */
    @ApiOperation(value = "包年包月套餐支付",response =ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payModelConfigId",value = "套餐Id",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "payMethod",value = "miniPay",paramType = "query",dataType = "String")
    })
    @RequestMapping(value = "/packagePay",method = RequestMethod.POST)
    @ResponseBody
    public Object packagePay(HttpServletRequest request, Integer payModelConfigId, String payMethod) {
        if (null == payModelConfigId) {
            return new ResponseEnvelope(true, "payModelConfigId不能为空", null, false);
        }
        String platformCode = request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(false, "平台编码不能为空", null, false);
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info("获取用户信息 userId=>{}"+(loginUser == null?null:loginUser.getId()));
        try {
            Pay2Service pay2Service = SpringContextHolder.getBean(payMethod);
            Object obj = pay2Service.packagePay(payModelConfigId, loginUser.getId(), platformCode);
            return new ResponseEnvelope(obj, null, true);
        } catch (BizException e) {
            return new ResponseEnvelope(true, e.getMessage(), null, false);
        } catch (Exception e) {
            logger.error("系统繁忙:", e);
            return new ResponseEnvelope(true, "系统繁忙", null, false);
        }
    }
}
