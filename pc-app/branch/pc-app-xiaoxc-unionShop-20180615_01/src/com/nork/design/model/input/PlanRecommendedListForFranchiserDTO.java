package com.nork.design.model.input;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * function:WebDesignPlanRecommendedControllerV2.planRecommendedListForFranchiser参数
 * 
 * @author huangsongbo
 * @date 2018.3.27
 *
 */
public class PlanRecommendedListForFranchiserDTO implements Serializable{

	private static final long serialVersionUID = -1605924304848265291L;

	@NotNull(message = "参数msgId不能为空")
	private String msgId;

	/**
	 * 公开状态
	 * OPEN:公开方案
	 */
	@NotNull(message = "参数shelfStatus不能为空")
	private String shelfStatus;
	
	/**
	 * 平台类型
	 * pc2b:通用版本PC 2B端
	 */
	@NotNull(message = "参数platformCode不能为空")
	private String platformCode;
	
	/**
	 * 将platformCode处理成platformId
	 */
	private Integer platformId;
	
	private Integer start;
	
	private Integer limit;
	
	/**
	 * 查询条件:方案名称
	 */
	private String planName;
	
	/**
	 * 查询条件:空间类型
	 */
	private Integer houseType;
	
	/**
	 * 查询条件:空间面积
	 */
	private String areaValue;
	
	/**
	 * 查询条件:推荐方案风格id
	 */
	private Integer designRecommendedStyleId;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 1:普通方案
	 * 2:智能方案
	 */
	private Integer recommendedType;
	
	/**
	 * 是否发布,对应constant:RecommendedDecorateState
	 */
	private Integer isRelease;
	
	/**
	 * 公司id
	 */
	private Integer companyId;
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getRecommendedType() {
		return recommendedType;
	}

	public void setRecommendedType(Integer recommendedType) {
		this.recommendedType = recommendedType;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}
	
	public String getAreaValue() {
		return areaValue;
	}

	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}

	public Integer getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}

	public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(String shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
