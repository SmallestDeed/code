package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
