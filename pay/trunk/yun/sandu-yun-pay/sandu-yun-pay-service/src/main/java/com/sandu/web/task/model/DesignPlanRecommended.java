package com.sandu.web.task.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sandu.common.model.Mapper;


/**
 * 方案推荐
 * @author Administrator
 *
 */
public class DesignPlanRecommended   extends Mapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	/*方案推荐类型  1分享  2一键装修*/
	private Integer recommendedType;
    private Integer planId;
	/**  方案编码  **/
	private String planCode;
	/**  方案名称  **/
	private String planName;
	/**  设计者id  **/
	private Integer userId;
	/**  方案来源类型  **/
	private Integer designSourceType;
	/**  来源id  **/
	private Integer designId;
	/**  设计风格  **/
	private Integer designStyleId;
	/**  方案图片id  **/
	private Integer picId;
	/**  模型文件id  **/
	private Integer model3dId;
//	/**  模型文件id  **/
	private Integer modelU3dId;
	/**  模型文件id  **/
	private Integer modelId;
	/**  配置文件id  **/
	private Integer configFileId;
	/**  空间id  **/
	private Integer spaceCommonId;
	/**  方案描述  **/
	private String planDesc;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  备注  **/
	private String remark;
	/**  设计效果图  **/
	private String designPlanRenderPicIds;
	/** 样板房ID **/
	private Integer designTemplateId;
	private Integer webModelU3dId;
	private Integer iosModelU3dId;
	private Integer androidModelU3dId;
	private Integer pcModelU3dId;
	private Integer ipadModelU3dId;
	private Integer macBookpcModelU3dId;
	/** 媒介类型 **/
	private Integer mediaType;
	/** 分享数量  **/
	private Integer shareTotal;
	/*公开*/
	private Integer isOpen;
	//草稿状态
	private Integer draftState;
	//白模状态
	private Integer baiMoState;
	//硬装完成状态
	private Integer stuffFinishState;
	//装修完成状态
	private Integer decorateFinishState;
	//方案是否变更
	private Integer isChange;
	//设计方案是否装修过 （1 装修过，0 没装修过）
	private Integer isDecorated;
	/**方案来源*/
	private String planSource;
	/**小区户型名称*/
	private String residentialUnitsName;
	private Integer houseId;
	
	private Integer livingId;
   /**设计方案封面*/
	private Integer coverPicId;
	/*方案推荐风格ID*/
	private Integer designRecommendedStyleId;
	/*如果这个有值，默认用这个风格将数据置顶，只用于一件装修页面*/
	private Integer designRecommendedStyleIdTop;
	
	/*设计方案是否发布 0 否 1是*/
	private Integer isRelease;
	
	private List<Integer>IsReleases;
	
	/*设计方案发布时间 */
	private Date releaseTime;
	/*特效配置*/
	private String effectsConfig;
	/*发布权限 0无  1有*/
	private Integer releasePermissions;
	/*复制权限 0无  1有*/
	private Integer copyPermissions;
	/*多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名*/
	private String multifunctionQuery;
	private String brandName;
	/*720渲染图二维码路径*/
	private String qrCode720Path;
	/*是否默认推荐  1 默认 0 非默认*/
	private Integer isRecommended;
	/*是否支持一键装修  1 支持  0不支持*/
	private Integer isDefaultDecorate;
	/*方案编号*/
	private String  planNumber;
	/*多点渲染图*/
	private Integer render720MultipointPicId; 
	/*全景渲染图id*/
	private Integer render720PanoramaPicId; 
	/*渲染视频id*/
	private Integer render720VideoId;

	private String displayType;
 
	
	/**用于列表查询 begin>>*/
	/*
	 *品牌id
	 */
	private List<Integer> BrandIds;
	/*
	 *空间类型value
	 */
	private Integer SpaceFunctionId;
	/*
	 *多个空间类型value
	 */
	List<Integer>spaceFunctionIds ;
	/*
	 *  面积类型value
	 */
	private String areaValue;
	/*
	 * 小区名
	 */
	private String livingName;
	/*
	 * 是否为内部用户
	 */
	private String IsInternalUser;
	/**
	 * 是否为方案审核管理员
	 */
	private String checkAdministrator;
	/**end<<*/
	
	private Integer picType;
	
	private List<RenderPicInfo> picList = new ArrayList<RenderPicInfo>();
	
	private String planRecommendedUserName;
	
	private String spaceCode;
	private String spaceName;
	private String spaceAreas;
	
	/*该方案推荐的产品数量*/
	private Integer planRecommendedProductCount;
	// '是否装修完成状态 (1.未装修完成 2.已装修完成)'
	private Integer recommendedDecorateState;
	
	private Integer designPlanId; 
 
	private String favoriteBid;

	//适用于空间面积（一键装修）
	private String applySpaceAreas;
	
	/**
	 * 拼花 json 传
	 */
	private Integer spellingFlowerFileId;
 	/**
 	 * 拼花  产品ids 
 	 */
	private String spellingFlowerProduct;
	
	
	public String getSpellingFlowerProduct() {
		return spellingFlowerProduct;
	}

	public void setSpellingFlowerProduct(String spellingFlowerProduct) {
		this.spellingFlowerProduct = spellingFlowerProduct;
	}
	public Integer getSpellingFlowerFileId() {
		return spellingFlowerFileId;
	}

	public void setSpellingFlowerFileId(Integer spellingFlowerFileId) {
		this.spellingFlowerFileId = spellingFlowerFileId;
	}
	public Integer getDesignRecommendedStyleIdTop() {
		return designRecommendedStyleIdTop;
	}

	public void setDesignRecommendedStyleIdTop(Integer designRecommendedStyleIdTop) {
		this.designRecommendedStyleIdTop = designRecommendedStyleIdTop;
	}

	public String getApplySpaceAreas() {
		return applySpaceAreas;
	}

	public void setApplySpaceAreas(String applySpaceAreas) {
		this.applySpaceAreas = applySpaceAreas;
	}

	public String getFavoriteBid() {
		return favoriteBid;
	}

	public void setFavoriteBid(String favoriteBid) {
		this.favoriteBid = favoriteBid;
	}

	public Integer getRecommendedDecorateState() {
		return recommendedDecorateState;
	}

	public void setRecommendedDecorateState(Integer recommendedDecorateState) {
		this.recommendedDecorateState = recommendedDecorateState;
	}

	public Integer getPlanRecommendedProductCount() {
		return planRecommendedProductCount;
	}

	public void setPlanRecommendedProductCount(Integer planRecommendedProductCount) {
		this.planRecommendedProductCount = planRecommendedProductCount;
	}

	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public String getPlanRecommendedUserName() {
		return planRecommendedUserName;
	}

	public void setPlanRecommendedUserName(String planRecommendedUserName) {
		this.planRecommendedUserName = planRecommendedUserName;
	}

	public List<RenderPicInfo> getPicList() {
		return picList;
	}

	public void setPicList(List<RenderPicInfo> picList) {
		this.picList = picList;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public DesignPlan recommendedCopy(){
		 DesignPlan obj = new DesignPlan();
	    	 obj.setPlanCode(this.planCode);
	         obj.setPlanName(this.planName);
	         obj.setPlanSource(this.planSource);
	         obj.setHouseId(this.houseId);
	         obj.setLivingId(this.livingId);
	         obj.setResidentialUnitsName(residentialUnitsName);
	         obj.setUserId(this.userId);
	         obj.setDesignSourceType(this.designSourceType);
	         obj.setDesignId(this.designId);
	         obj.setDesignStyleId(this.designStyleId);
	         obj.setPicId(this.picId);
	         obj.setModelId(this.modelId);
	         obj.setModel3dId(this.model3dId);
	         obj.setConfigFileId(this.configFileId);
	         obj.setSpaceCommonId(this.spaceCommonId);
	         obj.setPlanDesc(this.planDesc);
	         obj.setSysCode(this.sysCode);
	         obj.setCreator(this.creator);
	         obj.setGmtCreate(this.gmtCreate);
	         obj.setModifier(this.modifier);
	         obj.setGmtModified(this.gmtModified);
	         obj.setIsDeleted(this.isDeleted);
	         obj.setRemark(this.remark);
	         obj.setDesignTemplateId(this.designTemplateId);
	         obj.setIpadModelU3dId(this.ipadModelU3dId);
	         obj.setIosModelU3dId(this.iosModelU3dId);
	         obj.setAndroidModelU3dId(this.androidModelU3dId);
	         obj.setMacBookpcModelU3dId(this.macBookpcModelU3dId);
	         obj.setPcModelU3dId(this.pcModelU3dId);
	         obj.setWebModelU3dId(this.webModelU3dId);
	         obj.setMediaType(this.mediaType);
	        /*obj.setEveningFileId(this.eveningFileId);
 			 obj.setDawnFileId(this.dawnFileId );
	         obj.setNightFileId(this.nightFileId );*/
 			 obj.setShareTotal(this.shareTotal);
	         obj.setIsOpen(this.isOpen );
 			 obj.setDraftState(this.draftState );
	         obj.setBaiMoState(this.baiMoState );
 			 obj.setStuffFinishState(this.stuffFinishState);
	         obj.setDecorateFinishState(this.decorateFinishState);
 			 obj.setIsChange(this.isChange);
	         obj.setIsDecorated(this.isDecorated);
 			 obj.setPlanSource(this.planSource);
 			 obj.setResidentialUnitsName(this.residentialUnitsName );
 			 obj.setHouseId(this.houseId);
	         obj.setLivingId(this.livingId);
	        /*obj.setCoverPicId(this.coverPicId);*/
	         obj.setEffectsConfig(this.effectsConfig);
	         obj.setSpellingFlowerProduct(this.spellingFlowerProduct);
	         return obj;
	    }
	 
	public List<Integer> getSpaceFunctionIds() {
		return spaceFunctionIds;
	}
	public String getCheckAdministrator() {
		return checkAdministrator;
	}
	public void setCheckAdministrator(String checkAdministrator) {
		this.checkAdministrator = checkAdministrator;
	}
	public void setSpaceFunctionIds(List<Integer> spaceFunctionIds) {
		this.spaceFunctionIds = spaceFunctionIds;
	}
	public List<Integer> getIsReleases() {
		return IsReleases;
	}
	public void setIsReleases(List<Integer> isReleases) {
		IsReleases = isReleases;
	}
	public String getLivingName() {
		return livingName;
	}
	public void setLivingName(String livingName) {
		this.livingName = livingName;
	}
	public String getIsInternalUser() {
		return IsInternalUser;
	}
	public void setIsInternalUser(String isInternalUser) {
		IsInternalUser = isInternalUser;
	}
	public String getAreaValue() {
		return areaValue;
	}
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
	public Integer getSpaceFunctionId() {
		return SpaceFunctionId;
	}
	public void setSpaceFunctionId(Integer spaceFunctionId) {
		SpaceFunctionId = spaceFunctionId;
	}
	public List<Integer> getBrandIds() {
		return BrandIds;
	}
	public void setBrandIds(List<Integer> brandIds) {
		BrandIds = brandIds;
	}
	public Integer getRecommendedType() {
		return recommendedType;
	}
	public void setRecommendedType(Integer recommendedType) {
		this.recommendedType = recommendedType;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDesignSourceType() {
		return designSourceType;
	}
	public void setDesignSourceType(Integer designSourceType) {
		this.designSourceType = designSourceType;
	}
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	public Integer getDesignStyleId() {
		return designStyleId;
	}
	public void setDesignStyleId(Integer designStyleId) {
		this.designStyleId = designStyleId;
	}
	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	public Integer getModel3dId() {
		return model3dId;
	}
	public void setModel3dId(Integer model3dId) {
		this.model3dId = model3dId;
	}
	public Integer getModelU3dId() {
		return modelU3dId;
	}
	public void setModelU3dId(Integer modelU3dId) {
		this.modelU3dId = modelU3dId;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getConfigFileId() {
		return configFileId;
	}
	public void setConfigFileId(Integer configFileId) {
		this.configFileId = configFileId;
	}
	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}
	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDesignPlanRenderPicIds() {
		return designPlanRenderPicIds;
	}
	public void setDesignPlanRenderPicIds(String designPlanRenderPicIds) {
		this.designPlanRenderPicIds = designPlanRenderPicIds;
	}
	public Integer getDesignTemplateId() {
		return designTemplateId;
	}
	public void setDesignTemplateId(Integer designTemplateId) {
		this.designTemplateId = designTemplateId;
	}
	public Integer getWebModelU3dId() {
		return webModelU3dId;
	}
	public void setWebModelU3dId(Integer webModelU3dId) {
		this.webModelU3dId = webModelU3dId;
	}
	public Integer getIosModelU3dId() {
		return iosModelU3dId;
	}
	public void setIosModelU3dId(Integer iosModelU3dId) {
		this.iosModelU3dId = iosModelU3dId;
	}
	public Integer getAndroidModelU3dId() {
		return androidModelU3dId;
	}
	public void setAndroidModelU3dId(Integer androidModelU3dId) {
		this.androidModelU3dId = androidModelU3dId;
	}
	public Integer getPcModelU3dId() {
		return pcModelU3dId;
	}
	public void setPcModelU3dId(Integer pcModelU3dId) {
		this.pcModelU3dId = pcModelU3dId;
	}
	public Integer getIpadModelU3dId() {
		return ipadModelU3dId;
	}
	public void setIpadModelU3dId(Integer ipadModelU3dId) {
		this.ipadModelU3dId = ipadModelU3dId;
	}
	public Integer getMacBookpcModelU3dId() {
		return macBookpcModelU3dId;
	}
	public void setMacBookpcModelU3dId(Integer macBookpcModelU3dId) {
		this.macBookpcModelU3dId = macBookpcModelU3dId;
	}
	public Integer getMediaType() {
		return mediaType;
	}
	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}
	public Integer getShareTotal() {
		return shareTotal;
	}
	public void setShareTotal(Integer shareTotal) {
		this.shareTotal = shareTotal;
	}
	public Integer getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	public Integer getDraftState() {
		return draftState;
	}
	public void setDraftState(Integer draftState) {
		this.draftState = draftState;
	}
	public Integer getBaiMoState() {
		return baiMoState;
	}
	public void setBaiMoState(Integer baiMoState) {
		this.baiMoState = baiMoState;
	}
	public Integer getStuffFinishState() {
		return stuffFinishState;
	}
	public void setStuffFinishState(Integer stuffFinishState) {
		this.stuffFinishState = stuffFinishState;
	}
	public Integer getDecorateFinishState() {
		return decorateFinishState;
	}
	public void setDecorateFinishState(Integer decorateFinishState) {
		this.decorateFinishState = decorateFinishState;
	}
	public Integer getIsChange() {
		return isChange;
	}
	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}
	public Integer getIsDecorated() {
		return isDecorated;
	}
	public void setIsDecorated(Integer isDecorated) {
		this.isDecorated = isDecorated;
	}
	public String getPlanSource() {
		return planSource;
	}
	public void setPlanSource(String planSource) {
		this.planSource = planSource;
	}
	public String getResidentialUnitsName() {
		return residentialUnitsName;
	}
	public void setResidentialUnitsName(String residentialUnitsName) {
		this.residentialUnitsName = residentialUnitsName;
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
	public Integer getCoverPicId() {
		return coverPicId;
	}
	public void setCoverPicId(Integer coverPicId) {
		this.coverPicId = coverPicId;
	}
	public Integer getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}
	public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getEffectsConfig() {
		return effectsConfig;
	}
	public void setEffectsConfig(String effectsConfig) {
		this.effectsConfig = effectsConfig;
	}
	public Integer getReleasePermissions() {
		return releasePermissions;
	}
	public void setReleasePermissions(Integer releasePermissions) {
		this.releasePermissions = releasePermissions;
	}
	public Integer getCopyPermissions() {
		return copyPermissions;
	}
	public void setCopyPermissions(Integer copyPermissions) {
		this.copyPermissions = copyPermissions;
	}
	public String getMultifunctionQuery() {
		return multifunctionQuery;
	}
	public void setMultifunctionQuery(String multifunctionQuery) {
		this.multifunctionQuery = multifunctionQuery;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getQrCode720Path() {
		return qrCode720Path;
	}
	public void setQrCode720Path(String qrCode720Path) {
		this.qrCode720Path = qrCode720Path;
	}
	public Integer getIsRecommended() {
		return isRecommended;
	}
	public void setIsRecommended(Integer isRecommended) {
		this.isRecommended = isRecommended;
	}
	public Integer getIsDefaultDecorate() {
		return isDefaultDecorate;
	}
	public void setIsDefaultDecorate(Integer isDefaultDecorate) {
		this.isDefaultDecorate = isDefaultDecorate;
	}
	public String getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}
	public Integer getRender720MultipointPicId() {
		return render720MultipointPicId;
	}
	public void setRender720MultipointPicId(Integer render720MultipointPicId) {
		this.render720MultipointPicId = render720MultipointPicId;
	}
	public Integer getRender720PanoramaPicId() {
		return render720PanoramaPicId;
	}
	public void setRender720PanoramaPicId(Integer render720PanoramaPicId) {
		this.render720PanoramaPicId = render720PanoramaPicId;
	}
	public Integer getRender720VideoId() {
		return render720VideoId;
	}
	public void setRender720VideoId(Integer render720VideoId) {
		this.render720VideoId = render720VideoId;
	}

    public Integer getDesignPlanId() {
        return designPlanId;
    }

    public void setDesignPlanId(Integer designPlanId) {
        this.designPlanId = designPlanId;
    }
    /** 推荐搜索   “全部”的标识（包括品牌，设计者，小区名字） *//*
	private String all;

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}*/
	
 
}
