package com.sandu.api.company.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerAlotZone implements Serializable {

	/**
	 * 自增长id
	 */
	private Integer id;

	/**
	 * 企业id
	 */
	private Integer companyId;

	/**
	 * 经销商id
	 */
	private Integer channelCompanyId;

	/**
	 * 省编码
	 */
	private String provinceCode;

	/**
	 * 市编码
	 */
	private String cityCode;

	/**
	 * 区编码
	 */
	private String areaCode;

	/**
	 * 数据来源
	 */
	private Integer sourceType;

	/**
	 * 长编码
	 */
	private String longCode;


	private String creator;

	private Date gmtCreated;

	private String modifier;

	private Date gmtModified;

	private Integer isDeleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getChannelCompanyId() {
		return channelCompanyId;
	}

	public void setChannelCompanyId(Integer channelCompanyId) {
		this.channelCompanyId = channelCompanyId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
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

	public String getLongCode() {
		return longCode;
	}

	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}
}
