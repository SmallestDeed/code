package com.sandu.pay.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款方式业务关联表对应的实体类
 *
 * @Author yzw
 * @Date 2018/1/16 15:58
 */
public class PayModelGroupRef implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 业务id（用户id，企业id，品牌id）
     */
    private Integer businessId;

    /**
     * 付款方式表id
     */
    private Integer payModelConfigId;

    /**
     * 到期时间
     */
    private Date expiryTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 创建者
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
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 小程序appId
     */
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    private static final long serialVersionUID = 1L;

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public PayModelGroupRef() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getPayModelConfigId() {
        return payModelConfigId;
    }

    public void setPayModelConfigId(Integer payModelConfigId) {
        this.payModelConfigId = payModelConfigId;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
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

    @Override
    public String toString() {
        return "PayModelGroupRef{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", payModelConfigId=" + payModelConfigId +
                ", expiryTime=" + expiryTime +
                ", isDeleted=" + isDeleted +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                '}';
    }
}