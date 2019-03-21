package com.sandu.pay.minipro.model;

import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/2
 * @since : sandu_yun_1.0
 */
public class WXMiniPayInfoModel implements Serializable{

    private Integer orderId;
    private String orderNo;
    private String appId;
    private String openId;

    private String nonceStr;
    private String wxPackageName;
    private String signType;
    private Long timeStamp;

    private String mchid;
    private Integer totalFree;

    private String body;

    private String notifyUrl;

    private String tradeType;

    private String wxPayKey;

    public String getWxPayKey() {
        return wxPayKey;
    }

    public void setWxPayKey(String wxPayKey) {
        this.wxPayKey = wxPayKey;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getTotalFree() {
        return totalFree;
    }

    public void setTotalFree(Integer totalFree) {
        this.totalFree = totalFree;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getWxPackageName() {
        return wxPackageName;
    }

    public void setWxPackageName(String wxPackageName) {
        this.wxPackageName = wxPackageName;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "WXMiniPayInfoModel{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", appId='" + appId + '\'' +
                ", openId='" + openId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", wxPackageName='" + wxPackageName + '\'' +
                ", signType='" + signType + '\'' +
                ", timeStamp=" + timeStamp +
                ", mchid='" + mchid + '\'' +
                ", totalFree=" + totalFree +
                '}';
    }
}
