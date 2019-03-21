package com.nork.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DesignPlanRecommendedResult  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer planRecommendedId;
	
	Integer planId;
	
	String planName;
	
	/*封面图路径*/
	String coverPath;
	
	/*设计方案是否发布 0 否 1是*/
	Integer isRelease;
	
	Date gmtModified;
	
	Date gmtCreate;

	/*面积*/
	String spaceAreas;
	
	/*风格*/
	String styleName;
	
	/*创建者*/
	String creator;
	
	String houseTypeName;
	
	Integer spaceFunctionId;
	
	/*发布时间*/
	Date releaseTime;
	
	Integer livingId;
	
	Integer houseId;
	
	String remark;
	
	String planSource;
	
	/*是否支持一件装修*/
	Integer isDefaultDecorate;
	 
	/*最新720渲染图地址链接*/
    String resRenderPicPath;
    /*推荐收藏Bid*/
    String bid;
    /*
     * 收藏夹name*/
    String favoriteName;
    //适用于空间面积
    String applySpaceAreas;

    List<String> applySpaceAreasList = new ArrayList<>();
    /**空间编码**/
    String spaceCode;
    /**主方案id 0没有打组，和designPlanRecommendId相同为主方案，不同则为主方案id**/
    private Integer groupPrimaryId;
	//装修类型名称和价格集合
	private List<PlanDecoratePriceBO> planDecoratePriceList;
	//方案空间类型(1：单空间方案，2：全屋方案)
	private Integer planHouseType;
	//全屋方案720UUID
	private String fullHousePlanUUID;

	public String getFullHousePlanUUID() {
		return fullHousePlanUUID;
	}

	public void setFullHousePlanUUID(String fullHousePlanUUID) {
		this.fullHousePlanUUID = fullHousePlanUUID;
	}

	public Integer getPlanHouseType() {
		return planHouseType;
	}

	public void setPlanHouseType(Integer planHouseType) {
		this.planHouseType = planHouseType;
	}

	//方案费用类型:0.免费 1.收费
	private Integer chargeType;

	//方案收费价格
	private Double planPrice;

	//用户是否需要购买方案版权:0.不需要;1.需要
	private Integer copyRightPermission;

	private Integer companyId;

	//用户是否已购买该方案:0.未购买 1.已购买
	private Integer havePurchased;

	private Boolean isPublic;

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
	}

	public Integer getHavePurchased() {
		return havePurchased;
	}

	public void setHavePurchased(Integer havePurchased) {
		this.havePurchased = havePurchased;
	}

	public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCopyRightPermission() {
		return copyRightPermission;
	}

	public void setCopyRightPermission(Integer copyRightPermission) {
		this.copyRightPermission = copyRightPermission;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Double getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}

	public List<PlanDecoratePriceBO> getPlanDecoratePriceList() {
		return planDecoratePriceList;
	}

	public void setPlanDecoratePriceList(List<PlanDecoratePriceBO> planDecoratePriceList) {
		this.planDecoratePriceList = planDecoratePriceList;
	}

	public Integer getGroupPrimaryId() {
		return groupPrimaryId;
	}

	public void setGroupPrimaryId(Integer groupPrimaryId) {
		this.groupPrimaryId = groupPrimaryId;
	}

	public String getSpaceCode() {
      return spaceCode;
    }

    public void setSpaceCode(String spaceCode) {
      this.spaceCode = spaceCode;
    }

   public String getApplySpaceAreas() {
		return applySpaceAreas;
	}

	public void setApplySpaceAreas(String applySpaceAreas) {
		this.applySpaceAreas = applySpaceAreas;
	}

	public List<String> getApplySpaceAreasList() {
		return applySpaceAreasList;
	}

	public void setApplySpaceAreasList(List<String> applySpaceAreasList) {
		this.applySpaceAreasList = applySpaceAreasList;
	}

	public String getFavoriteName() {
		return favoriteName;
	}

	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public Integer getPlanRecommendedId() {
		return planRecommendedId;
	}

	public void setPlanRecommendedId(Integer planRecommendedId) {
		this.planRecommendedId = planRecommendedId;
	}

	public Integer getIsDefaultDecorate() {
		return isDefaultDecorate;
	}

	public void setIsDefaultDecorate(Integer isDefaultDecorate) {
		this.isDefaultDecorate = isDefaultDecorate;
	}

	public String getPlanSource() {
		return planSource;
	}

	public void setPlanSource(String planSource) {
		this.planSource = planSource;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public Integer getLivingId() {
		return livingId;
	}

	public void setLivingId(Integer livingId) {
		this.livingId = livingId;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
 

	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

 

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getResRenderPicPath() {
		return resRenderPicPath;
	}

	public void setResRenderPicPath(String resRenderPicPath) {
		this.resRenderPicPath = resRenderPicPath;
	}


	
	
	
}
