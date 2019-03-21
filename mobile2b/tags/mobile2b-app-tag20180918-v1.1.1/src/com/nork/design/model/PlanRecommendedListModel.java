package com.nork.design.model;

import com.nork.common.model.LoginUser;

public class PlanRecommendedListModel {
	
	String msgId;
	
	LoginUser loginUser;
	
	String creator;
	
	String brandName;
	
	String houseType;
	
	String livingName;
	
	String areaValue;
	
	String designRecommendedStyleId;
	
	String designRecommendedStyleIdTop;
	
	String displayType;
	
	Integer templateId;
	String planName;


	int start;
	int limit;
	
	/** 推荐方案审核状态 **/
	String planCheckState;
	Integer platformId;
	private Long companyId;
	//装修报价类型
	private Integer decoratePriceType;
	//装修报价区间
	private Integer decoratePriceRange;

	public Integer getDecoratePriceType() {
		return decoratePriceType;
	}

	public void setDecoratePriceType(Integer decoratePriceType) {
		this.decoratePriceType = decoratePriceType;
	}

	public Integer getDecoratePriceRange() {
		return decoratePriceRange;
	}

	public void setDecoratePriceRange(Integer decoratePriceRange) {
		this.decoratePriceRange = decoratePriceRange;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getDesignRecommendedStyleIdTop() {
		return designRecommendedStyleIdTop;
	}
	public void setDesignRecommendedStyleIdTop(String designRecommendedStyleIdTop) {
		this.designRecommendedStyleIdTop = designRecommendedStyleIdTop;
	}
	public Integer getTemplateId() {

		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public LoginUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getLivingName() {
		return livingName;
	}
	public void setLivingName(String livingName) {
		this.livingName = livingName;
	}
	public String getAreaValue() {
		return areaValue;
	}
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
	public String getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}
	public void setDesignRecommendedStyleId(String designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
    public String getPlanCheckState() {
      return planCheckState;
    }
    public void setPlanCheckState(String planCheckState) {
      this.planCheckState = planCheckState;
    }
	
}
