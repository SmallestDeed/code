package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.Date;

import com.nork.common.model.Mapper;

public class DesignPlanCheck extends Mapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer id;
	
	/**产品ID*/
	Integer planId;
	
	/**用户ID*/
	Integer userId;
	
	/**系统编码*/
	String sysCode;
	
	/**创建者*/
	String creator;
	
	/**创建时间*/
	Date gmtCreate;
	
	/**修改人*/
	String modifier;
	
	/**修改时间*/
	Date gmtModified;
	
	/**是否删除*/
	Integer isDeleted;
	
	/**字符备用1*/
	String att1;
	
	/**字符备用2*/
	String att2;
	
	/**整数备用1*/
	Integer numa1;
	
	/**整数备用2*/
	Integer numa2;
	
	/**备注*/
	String remark;

	
	
	/*列表查询条件*/
	
	/*空间类型*/
	Integer spaceFunctionId;
	/*房型编码*/
	String planNumber;
	/*方案名*/
	String planName;
	/*品牌名*/
	String brandName;
	
	/*列表查询条件*/
	
	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public String getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getAtt1() {
		return att1;
	}

	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	public String getAtt2() {
		return att2;
	}

	public void setAtt2(String att2) {
		this.att2 = att2;
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
		this.remark = remark;
	}
	
	
}
