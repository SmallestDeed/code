package com.nork.design.model.output;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.nork.design.model.constant.DesignPlanRecommendedConstant;
import com.nork.design.model.constant.ShelfStatusEnum;

/**
 * function:function:WebDesignPlanRecommendedControllerV2.franchiserPlanList对应VO
 * 
 * @author huangsongbo
 * @date 2018.3.28
 *
 */
public class FranchiserPlanListVO implements Serializable{

	private static final long serialVersionUID = -6989983239619912670L;

	/** 组合id */
	private Integer groupPrimaryId;

	/**
	 * 方案编码
	 */
	private String planCode;
	
	/**
	 * 方案名称
	 */
	private String planName;
	
	/**
	 * 空间面积
	 */
	private String spaceAreas;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 创建人姓名
	 */
	private String creator;
	
	/**
	 * 推荐方案id
	 */
	private Integer id;
	
	/**
	 * 封面图片路径
	 */
	private String coverPath;
	
	/**
	 * 用户身份
	 */
	private String companyName;
	
	/**
	 * 空间类型名称
	 */
	private String houseTypeName;
	
	/**
	 * 风格名称
	 */
	private String styleName;
	
	/**
	 * 对应显示平台名称
	 */
	@SuppressWarnings("unused")
	private String platformTypeName;
	
	/**
	 * 智能方案/普通方案
	 */
	/*@SuppressWarnings("unused")
	private String shelfStatusName;*/
	
	/**
	 * 智能方案/普通方案
	 */
	@SuppressWarnings("unused")
	private String recommendedTypeName;
	
	/**
	 * 方案类型:
	 * 1:普通方案
	 * 2:智能方案
	 */
	private Integer recommendedType;
	
	/**
	 * 0:非2b平台方案
	 * 1:2b平台方案
	 */
	private Integer toBStatus;
	
	/**
	 * 0:非2c平台方案
	 * 1:2c平台方案
	 */
	private Integer toCStatus;
	
	/**
	 * 方案类型:ONEKEY/...
	 */
	private String shelfStatus;
	
	private Integer planRecommendedId;
	
	private Integer planId;
	
	/**
	 * 设计方案发布时间
	 * @return
	 */
	private Date releaseTime;

	public Integer getGroupPrimaryId() {
		return groupPrimaryId;
	}

	public void setGroupPrimaryId(Integer groupPrimaryId) {
		this.groupPrimaryId = groupPrimaryId;
	}

	public Integer getRecommendedType() {
		return recommendedType;
	}

	public void setRecommendedType(Integer recommendedType) {
		this.recommendedType = recommendedType;
	}

	public String getRecommendedTypeName() {
		/*return recommendedTypeName;*/
		if(this.recommendedType == null) {
			return "未知方案";
		}
		
		if(this.recommendedType.intValue() == DesignPlanRecommendedConstant.RECOMMENDEDTYPE_COMMON.intValue()) {
			return "普通方案";
		}else if(this.recommendedType.intValue() == DesignPlanRecommendedConstant.RECOMMENDEDTYPE_NOOPSYCHE.intValue()) {
			return "智能方案";
		}else {
			return "未知方案";
		}
	}

	public void setRecommendedTypeName(String recommendedTypeName) {
		this.recommendedTypeName = recommendedTypeName;
	}

	public Integer getPlanRecommendedId() {
		return planRecommendedId;
	}

	public void setPlanRecommendedId(Integer planRecommendedId) {
		this.planRecommendedId = planRecommendedId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(String shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public Integer getToBStatus() {
		return toBStatus;
	}

	public void setToBStatus(Integer toBStatus) {
		this.toBStatus = toBStatus;
	}

	public Integer getToCStatus() {
		return toCStatus;
	}

	public void setToCStatus(Integer toCStatus) {
		this.toCStatus = toCStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getPlatformTypeName() {
		StringBuffer stringBuffer = new StringBuffer();
		if(toBStatus != null && 1 == toBStatus.intValue()) {
			stringBuffer.append("渠道");
		}
		if(toCStatus != null && 1 == toCStatus.intValue()) {
			if(stringBuffer.length() > 0) {
				stringBuffer.append("+");
			}
			stringBuffer.append("线上");
		}
		if(stringBuffer.length() > 0) {
			stringBuffer.append("管理");
		}else {
			stringBuffer.append("未分配");
		}
		return stringBuffer.toString();
		/*return platformTypeName;*/
	}

	public void setPlatformTypeName(String platformTypeName) {
		this.platformTypeName = platformTypeName;
	}

	/*public String getShelfStatusName() {
		if(StringUtils.equals(ShelfStatusEnum.ONEKEY.getCode(), shelfStatus)) {
			return "智能方案";
		}else {
			return "普通方案";
		}
	}

	public void setShelfStatusName(String shelfStatusName) {
		this.shelfStatusName = shelfStatusName;
	}*/

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Date getReleaseTime() {
      return releaseTime;
    }
  
    public void setReleaseTime(Date releaseTime) {
      this.releaseTime = releaseTime;
    }
	
}