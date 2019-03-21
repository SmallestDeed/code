package com.sandu.pay.minipro.service.impl;

import com.sandu.common.util.Utils;
import com.sandu.config.ResourceConfig;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.pay.minipro.model.WXMiniPayInfoModel;
import com.sandu.pay.minipro.service.WXMiniProPayService;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.wexin.common.Signature;
import com.sandu.pay.wexin.metadata.WxTradeType;
import com.sandu.pay.wexin.protocol.UnifiedOrderReqData;
import com.sandu.pay.wexin.protocol.UnifiedOrderResData;
import com.sandu.pay.wexin.service.UnifiedOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/2
 * @since : sandu_yun_1.0
 */
@Service("wxMiniProPayService")
public class WXMiniProPayServiceImpl implements WXMiniProPayService {
    private final static String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private static Logger logger = LogManager.getLogger(WXMiniProPayServiceImpl.class);


    @Autowired
    private MallBaseOrderService mallBaseOrderService;

    @Override
    public ResultMessage callWXUnifiedorderService(WXMiniPayInfoModel payInfo) {
        ResultMessage message = new ResultMessage();
        MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(payInfo.getOrderNo());
        if (baseOrder == null || baseOrder.getOrderAmount().intValue() <=0) {
            message.setSuccess(Boolean.FALSE);
            message.setObj(null);
            message.setMessage("订单信息异常");
        }
        //TODO : use the other service to getCompayInfoByAppId(), and then init payInfo;
        String appid = "wx1cbabc1754956e83";
        String mchId = "1394367302";
        String body = "小程序订单支付";
        //微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
        String key = "wxb3048NorKad782765143df7NorKcb2"; // 商户支付秘钥
        payInfo.setAppId(appid);
        payInfo.setMchid(mchId);
        payInfo.setBody(body);
        payInfo.setTotalFree(1);//TODO :baseOrder.getOrderAmount().intValue()*100
        payInfo.setNotifyUrl(ResourceConfig.WXPAY_MINI_RETURN_URL);
        logger.info("小程序回调url：" + payInfo.getNotifyUrl());
        payInfo.setTradeType(WxTradeType.JSAPI);
        payInfo.setWxPayKey(key);
        Map<String, Object> packageParams = getPayParamMap(payInfo);
        if(packageParams != null) {
            message.setSuccess(Boolean.TRUE);
            message.setObj(packageParams);
            message.setMessage("success");
        }else{
            message.setSuccess(Boolean.FALSE);
            message.setObj(null);
            message.setMessage("请求腾讯下单失败...");
        }
        return message;
    }

    @Override
    public Object callBackAfterWXMiniPay() {
        return null;
    }


    @Override
    public Object reSignParam() {
        return null;
    }

    @Override
    public Object updatePayStatus() {
        return null;
    }

    @Override
    public Object getPayStatus() {
        return null;
    }

    private Map<String, Object> getPayParamMap(WXMiniPayInfoModel payInfo) {
        Map<String, Object> packageParams = null;
        String appid = payInfo.getAppId();
        String mchId = payInfo.getMchid();
        String body = payInfo.getBody();
        String outTradeNo = payInfo.getOrderNo();
        int totalFee = payInfo.getTotalFree();
        String notifyUrl = payInfo.getNotifyUrl();
        String tradeType = payInfo.getTradeType();
        String openid = payInfo.getOpenId();
        String flag = "SUCCESS";
        //微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
        String key = payInfo.getWxPayKey(); // 商户支付秘钥
        String timeExpire = Utils.getTimeExpire(30);
        try {
            UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(appid, mchId, body, outTradeNo, totalFee,
                    notifyUrl, tradeType, openid, key, timeExpire);
            UnifiedOrderService orderService = new UnifiedOrderService();
            UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
            if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                    && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                packageParams = new HashMap<String, Object>();
                packageParams.put("appId", appid);
                packageParams.put("nonceStr", System.currentTimeMillis() + "");
                packageParams.put("package", "prepay_id="+ unifiedOrderResData.getPrepay_id());
                packageParams.put("signType","MD5");
                packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
                String packageSign = Signature.getSign(packageParams, key);
                packageParams.put("paySign", packageSign);
            } else {
                return packageParams;
            }
        } catch (Exception e) {
            logger.error("系统异常"+e);
            return packageParams;
        }

        return packageParams;
    }
}
