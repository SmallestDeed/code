package com.nork.render.model;


import java.io.Serializable;
import java.util.List;

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
     * 支付类型 0.支付宝 1.微信
     */
    private Integer payType;

    public String redirectUrl;

    /**
     * 方案购买使用类型 0.装进我家 1.产品替换
     */
    private Integer useType;

    /**
     * 购买类型:0.方案售卖,1.版权方案
     */
    private Integer buyType;

    private String planIds;

    //方案类型:0.全屋,1.普通
    private Integer planType;

    private List<Integer> chargeDesignPlanIds;

    public List<Integer> getChargeDesignPlanIds() {
        return chargeDesignPlanIds;
    }

    public void setChargeDesignPlanIds(List<Integer> chargeDesignPlanIds) {
        this.chargeDesignPlanIds = chargeDesignPlanIds;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getPlanIds() {
        return planIds;
    }

    public void setPlanIds(String planIds) {
        this.planIds = planIds;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
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
}
