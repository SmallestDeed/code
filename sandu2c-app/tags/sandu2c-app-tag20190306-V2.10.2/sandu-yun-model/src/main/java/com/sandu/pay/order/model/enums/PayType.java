package com.sandu.pay.order.model.enums;

/**
 * Created by Administrator on 2017/9/20.
 */
public enum PayType {

    WEIXIN("微信支付"),ALIPAY("支付宝支付");

    private String name;

    PayType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
