package com.sandu.pay.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款方式表对应的实体类
 *
 * @Author yzw
 * @Date 2018/1/16 15:18
 */
public class PayModelConfig implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐详情
     */
    private String packageDetails;

    /**
     * 套餐图标
     */
    private Integer packageLcon;

    /**
     * 套餐单价（单位为：分）
     */
    private Double packagePrice;

    /**
     * 时间单位（月/年）
     */
    private Integer timeUnit;

    /**
     * 时间类型（0表示月，1表示年）
     */
    private Integer timeType;

    /**
     * 业务类型（保存数据字典表中）
     */
    private String bizType;

    /**
     * 平台id
     */
    private Integer platformId;

    /**
     * 范围类型（0表示个人，1表示品牌，2表示企业）
     */
    private Integer rangeType;

    /**
     * 套餐用户类型（这里保存用户类型，使用逗号隔开；当这里为‘all_user’的时候表示所有用户）
     */
    private String packageUserType;

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
     * 业务类型:0为渲染,1为购买户型
     */
    private Integer businessType;

    private static final long serialVersionUID = 1L;

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public PayModelConfig() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails == null ? null : packageDetails.trim();
    }

    public Integer getPackageLcon() {
        return packageLcon;
    }

    public void setPackageLcon(Integer packageLcon) {
        this.packageLcon = packageLcon;
    }

    public Double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Integer getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(Integer timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

    public String getPackageUserType() {
        return packageUserType;
    }

    public void setPackageUserType(String packageUserType) {
        this.packageUserType = packageUserType == null ? null : packageUserType.trim();
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
        return "PayModelConfig{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", packageDetails='" + packageDetails + '\'' +
                ", packageLcon=" + packageLcon +
                ", packagePrice=" + packagePrice +
                ", timeUnit=" + timeUnit +
                ", timeType=" + timeType +
                ", bizType='" + bizType + '\'' +
                ", platformId=" + platformId +
                ", rangeType=" + rangeType +
                ", packageUserType='" + packageUserType + '\'' +
                ", isDeleted=" + isDeleted +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                '}';
    }
}