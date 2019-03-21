package com.sandu.customer.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerBaseInfo implements Serializable{
    private Long id;

    private Integer userId;

    private Integer companyId;

    private Byte level;

    private Double score;

    private Byte alotStatus;

    private Integer alotUserId;

    private Integer channelCompanyId;

    private Date alotTime;

    private Byte alotType;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String streetCode;

    private String address;

    private Integer isDeleted;

    private String remark;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Byte getAlotStatus() {
        return alotStatus;
    }

    public void setAlotStatus(Byte alotStatus) {
        this.alotStatus = alotStatus;
    }

    public Integer getAlotUserId() {
        return alotUserId;
    }

    public void setAlotUserId(Integer alotUserId) {
        this.alotUserId = alotUserId;
    }

    public Integer getChannelCompanyId() {
        return channelCompanyId;
    }

    public void setChannelCompanyId(Integer channelCompanyId) {
        this.channelCompanyId = channelCompanyId;
    }

    public Date getAlotTime() {
        return alotTime;
    }

    public void setAlotTime(Date alotTime) {
        this.alotTime = alotTime;
    }

    public Byte getAlotType() {
        return alotType;
    }

    public void setAlotType(Byte alotType) {
        this.alotType = alotType;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode == null ? null : streetCode.trim();
    }

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}