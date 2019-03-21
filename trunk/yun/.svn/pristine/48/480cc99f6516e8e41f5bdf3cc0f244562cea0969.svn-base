package com.sandu.pay.order.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/24 0024.
 */
public class PayOrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //总费用
    private int totalFee;
    //赠送费用
    private int adjustFee;
    //支付类型
    private String payType;
    //产品ID
    private Integer productId;
    //产品类型
    private String productType;
    //产品描述
    private String productDesc;
    //交易类型
    private String tradeType;
    //用户ID
    private Integer userId;
    //用户临时会话Key(获取UserSO对象)
    private String userTempKey;

    /**
     * 改变的url（http://nork.m.ci.sanduspace.cn/#/payresult?orderNo=），前端只需要传“http://nork.m.ci.sanduspace.cn/#/payresult”就可以了
     */
    public String redirectUrl;

    public String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(int adjustFee) {
        this.adjustFee = adjustFee;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserTempKey() {
        return userTempKey;
    }

    public void setUserTempKey(String userTempKey) {
        this.userTempKey = userTempKey;
    }


    @Override
    public String toString() {
        return "PayOrderModel{" +
                "totalFee=" + totalFee +
                ", adjustFee=" + adjustFee +
                ", payType='" + payType + '\'' +
                ", productId='" + productId + '\'' +
                ", productType='" + productType + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", userId=" + userId +
                ", userTempKey='" + userTempKey + '\'' +
                '}';
    }
}
