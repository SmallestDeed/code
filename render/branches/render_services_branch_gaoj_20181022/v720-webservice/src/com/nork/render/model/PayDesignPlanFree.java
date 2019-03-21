package com.nork.render.model;

import java.io.Serializable;

public class PayDesignPlanFree implements Serializable {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 推荐方案id
     */
    private Long planRecommendedId;

    /**
     * 支付方式
     */
    private String payMethod;

    private String token;

    private String platformCode;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 方案使用类型
     */
    private Integer useType;

    public String redirectUrl;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanRecommendedId() {
        return planRecommendedId;
    }

    public void setPlanRecommendedId(Long planRecommendedId) {
        this.planRecommendedId = planRecommendedId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}

