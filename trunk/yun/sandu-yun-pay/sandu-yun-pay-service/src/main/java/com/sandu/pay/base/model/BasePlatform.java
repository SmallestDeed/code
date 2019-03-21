package com.sandu.pay.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台表对应的实体
 */
public class BasePlatform implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 平台类型(1前台/2后台)
     */
    private String platformType;

    /**
     * 媒介类型 (1移动端/2pc端/3web端)
     */
    private String mediaType;

    /**
     * 平台编码
     */
    private String platformCode;

    /**
     * 所属平台分类(2b/2c/sandu)
     */
    private String platformBussinessType;

    /**
     * 系统编码
     */
    private String sysCode;

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
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 字符备用1
     */
    private String att1;

    /**
     * 字符备用2
     */
    private String att2;

    /**
     * 整数备用1
     */
    private Integer numa1;

    /**
     * 整数备用2
     */
    private Integer numa2;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public BasePlatform() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType == null ? null : mediaType.trim();
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode == null ? null : platformCode.trim();
    }

    public String getPlatformBussinessType() {
        return platformBussinessType;
    }

    public void setPlatformBussinessType(String platformBussinessType) {
        this.platformBussinessType = platformBussinessType == null ? null : platformBussinessType.trim();
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1 == null ? null : att1.trim();
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2 == null ? null : att2.trim();
    }

    public Integer getNuma1() {
        return numa1;
    }

    public void setNuma1(Integer numa1) {
        this.numa1 = numa1;
    }

    public Integer getNuma2() {
        return numa2;
    }

    public void setNuma2(Integer numa2) {
        this.numa2 = numa2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "BasePlatform{" +
                "id=" + id +
                ", platformName='" + platformName + '\'' +
                ", platformType='" + platformType + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", platformCode='" + platformCode + '\'' +
                ", platformBussinessType='" + platformBussinessType + '\'' +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", att1='" + att1 + '\'' +
                ", att2='" + att2 + '\'' +
                ", numa1=" + numa1 +
                ", numa2=" + numa2 +
                ", remark='" + remark + '\'' +
                '}';
    }
}