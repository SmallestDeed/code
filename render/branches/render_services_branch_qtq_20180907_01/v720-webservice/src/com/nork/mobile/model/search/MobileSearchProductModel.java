package com.nork.mobile.model.search;

import java.io.Serializable;

import com.nork.common.model.Mapper;

public class MobileSearchProductModel extends Mapper implements Serializable {

	private Integer userId;
	private String style;
	private String houseType;
	private Integer designPlanId;
	private Integer planProductId;
	private Integer spaceCommonId;
	private Integer productId;
	// 相当于样板房初始化该产品对应的白膜的样板房产品信息
	private String templateProductId;
	private Integer productTypeValue;
	private Integer smallTypeValue;
	private String queryType;
	private String productModelOrBrandName;
	private Integer userStatusType;
    private Integer isStandard;
    private String regionMark;
    private Integer styleId;
    private String measureCode;
    private String smallpox;
    private String categoryCode;
    private Integer structureId;

    
	public Integer getStructureId() {
		return structureId;
	}
	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public Integer getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}
	public Integer getPlanProductId() {
		return planProductId;
	}
	public void setPlanProductId(Integer planProductId) {
		this.planProductId = planProductId;
	}
	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}
	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getTemplateProductId() {
		return templateProductId;
	}
	public void setTemplateProductId(String templateProductId) {
		this.templateProductId = templateProductId;
	}
	public Integer getProductTypeValue() {
		return productTypeValue;
	}
	public void setProductTypeValue(Integer productTypeValue) {
		this.productTypeValue = productTypeValue;
	}
	public Integer getSmallTypeValue() {
		return smallTypeValue;
	}
	public void setSmallTypeValue(Integer smallTypeValue) {
		this.smallTypeValue = smallTypeValue;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getProductModelOrBrandName() {
		return productModelOrBrandName;
	}
	public void setProductModelOrBrandName(String productModelOrBrandName) {
		this.productModelOrBrandName = productModelOrBrandName;
	}
	public Integer getUserStatusType() {
		return userStatusType;
	}
	public void setUserStatusType(Integer userStatusType) {
		this.userStatusType = userStatusType;
	}
	public Integer getIsStandard() {
		return isStandard;
	}
	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
	}
	public String getRegionMark() {
		return regionMark;
	}
	public void setRegionMark(String regionMark) {
		this.regionMark = regionMark;
	}
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	public String getMeasureCode() {
		return measureCode;
	}
	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}
	public String getSmallpox() {
		return smallpox;
	}
	public void setSmallpox(String smallpox) {
		this.smallpox = smallpox;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
    
}
