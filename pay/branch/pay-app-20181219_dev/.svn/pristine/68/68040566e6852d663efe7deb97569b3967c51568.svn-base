package com.sandu.pay2.service.impl;

import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.sandu.pay.order.metadata.PayType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public abstract class WechatPayServiceImpl extends BasePayServiceImpl {
    private static Logger logger = LogManager.getLogger(WechatPayServiceImpl.class);

    protected String execute(Map<String, String> params) {
        String xmlResult = WxPayApi.pushOrder(false, params);
        return xmlResult;
    }

    protected String getPayType() {
        return PayType.WXPAY;
    }


    protected WxPayApiConfig getWxPayApiConfig() {
        /*WxPayApiConfig apiConfig = WxPayApiConfig.New()
                .setAppId(Con.jj)
                .setMchId(mchId)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
        return apiConfig;*/
        return null;
    }
}
