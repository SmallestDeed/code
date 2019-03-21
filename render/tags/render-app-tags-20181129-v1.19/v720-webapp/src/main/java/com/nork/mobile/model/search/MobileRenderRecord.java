package com.nork.mobile.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.common.model.Mapper;
/**
 * 移动端 渲染记录  表  的实体类
 * @author yangz
 */
public class MobileRenderRecord extends Mapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 设计方案id，对应的是design_plan_render_scene表id
	 */
	private Integer planId;
	/**
	 * 样板房id
	 */
	private Integer templateId;
	/**
	 * 推荐方案id
	 */
	private Integer recommendedPlanId;
	/**
	 * 渲染类型
	 */
	private String renderingType;
	/**
	 * 渲染费用   单位：分
	 */
	private Double totalFee;
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
	 * 是否删除
	 */
	private Integer isDeleted;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 移动端替换渲染：1；移动端一键装修：0
	 */
	private Integer isDecorate;
	/**
	 * 普通渲染服务:"common_render"
	 * 720度全景渲染服务"panorama_render"
	 * 多点全景渲染"roam720";
	 * 漫游视频渲染"video";
	 */
	private String productType;
	/**
	 * 0 未渲染 1 渲染中 2 渲染成功 3渲染失败
	 */
	private Integer taskType;
	/**
	 * 移动端我的替换列表需要这个值作为参数
	 */
	private Integer taskId;
	
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Integer getIsDecorate() {
		return isDecorate;
	}
	public void setIsDecorate(Integer isDecorate) {
		this.isDecorate = isDecorate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public Integer getRecommendedPlanId() {
		return recommendedPlanId;
	}
	public void setRecommendedPlanId(Integer recommendedPlanId) {
		this.recommendedPlanId = recommendedPlanId;
	}
	public String getRenderingType() {
		return renderingType;
	}
	public void setRenderingType(String renderingType) {
		this.renderingType = renderingType;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
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
		this.remark = remark;
	}
	//picId  res_render_pic 表的主键，在此用作获取关联二维码
	private Integer picId;

	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	
}
