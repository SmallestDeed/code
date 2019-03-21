package com.nork.design.model;

import java.io.Serializable;
import java.util.List;

/**
 * 新增一个查询对象，用作查询渲染列表
 * @author yangzhun
 *
 */
public class ResRenderPicQO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer createUserId;
	private Integer DesignSceneId;
	private Integer businessId;
	private List<String> fileKeys;
	private Integer isDeleted;
	private String order;
	private Integer planRecommendedId;
	private Integer limit;
	private Integer start;
	private String orders;
	

	
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getDesignSceneId() {
		return DesignSceneId;
	}
	public void setDesignSceneId(Integer designSceneId) {
		DesignSceneId = designSceneId;
	}

	public Integer getPlanRecommendedId() {
		return planRecommendedId;
	}
	public void setPlanRecommendedId(Integer planRecommendedId) {
		this.planRecommendedId = planRecommendedId;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public List<String> getFileKeys() {
		return fileKeys;
	}
	public void setFileKeys(List<String> fileKeys) {
		this.fileKeys = fileKeys;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
