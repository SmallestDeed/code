package com.nork.design.model;

import java.io.Serializable;
import java.util.Date;

public class CompanyDesignPlanIncome  implements Serializable{

    private Long id;

    private Integer planId;

    private String planCode;

    private Long buyerId;

    private String buyerName;

    private Integer platformId;

    private String platformName;

    private Integer planCompanyId;

    private Integer useType;

    private Integer buyType;

    private Date payTime;

    private Double payDubi;

    private Integer isDeleted;

    private Integer planType;

    private String planCreator;

    public String getPlanCreator() {
        return planCreator;
    }

    public void setPlanCreator(String planCreator) {
        this.planCreator = planCreator;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Integer getPlanCompanyId() {
        return planCompanyId;
    }

    public void setPlanCompanyId(Integer planCompanyId) {
        this.planCompanyId = planCompanyId;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Double getPayDubi() {
        return payDubi;
    }

    public void setPayDubi(Double payDubi) {
        this.payDubi = payDubi;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
