package com.sandu.pay.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付账户表
 */
public class PayAccount implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 消费度币
     */
    private Double consumAmount;

    /**
     * 账户余额
     */
    private Double balanceAmount;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 平台id（这里暂时为空）
     */
    private Integer platformId;

    /**
     * 删除状态
     */
    private Integer isDeleted;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 所属平台分类(2b/2c/sandu)
     */
    private String platformBussinessType;

    private static final long serialVersionUID = 1L;

    public PayAccount() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getConsumAmount() {
        return consumAmount;
    }

    public void setConsumAmount(Double consumAmount) {
        this.consumAmount = consumAmount;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getPlatformBussinessType() {
        return platformBussinessType;
    }

    public void setPlatformBussinessType(String platformBussinessType) {
        this.platformBussinessType = platformBussinessType == null ? null : platformBussinessType.trim();
    }

    @Override
    public String toString() {
        return "PayAccount{" +
                "id=" + id +
                ", consumAmount=" + consumAmount +
                ", balanceAmount=" + balanceAmount +
                ", userId=" + userId +
                ", platformId=" + platformId +
                ", isDeleted=" + isDeleted +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", platformBussinessType='" + platformBussinessType + '\'' +
                '}';
    }
}