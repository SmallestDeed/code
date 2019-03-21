package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 模板方案表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-07-22 14:21:29.670
 */

public class DesignPlanTemplate implements Serializable {
    
	 private static final long serialVersionUID = 1L;
	
    /**
     * design_plan_template.id
     */
    private Long id;

    /**
     * 推荐方案ID<p>
     * design_plan_template.recommended_plan_id
     */
    private Long recommendedPlanId;

    /**
     * 样板房ID<p>
     * design_plan_template.design_template_id
     */
    private String designTemplateId;

    /**
     * 方案编码<p>
     * design_plan_template.plan_code
     */
    private String planCode;

    /**
     * 方案名称<p>
     * design_plan_template.plan_name
     */
    private String planName;

    /**
     * 设计者id<p>
     * design_plan_template.user_id
     */
    private Integer userId;

    /**
     * 方案来源类型<p>
     * design_plan_template.design_source_type
     */
    private Integer designSourceType;

    /**
     * 来源id<p>
     * design_plan_template.design_id
     */
    private Integer designId;

    /**
     * 设计风格<p>
     * design_plan_template.design_style_id
     */
    private Integer designStyleId;

    /**
     * 方案图片<p>
     * design_plan_template.pic_id
     */
    private Integer picId;

    /**
     * design_plan_template.model_3d_id
     */
    private Integer model3dId;

    /**
     * design_plan_template.model_u3d_id
     */
    private Integer modelU3dId;

    /**
     * 方案模型<p>
     * design_plan_template.model_id
     */
    private Integer modelId;

    /**
     * 配置文件<p>
     * design_plan_template.config_file_id
     */
    private Integer configFileId;

    /**
     * 空间id<p>
     * design_plan_template.space_common_id
     */
    private Integer spaceCommonId;

    /**
     * 方案描述<p>
     * design_plan_template.plan_desc
     */
    private String planDesc;

    /**
     * design_plan_template.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * design_plan_template.creator
     */
    private String creator;

    /**
     * design_plan_template.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * design_plan_template.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * design_plan_template.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * design_plan_template.is_deleted
     */
    private Integer isDeleted;

    /**
     * 备注<p>
     * design_plan_template.remark
     */
    private String remark;

    /**
     * design_plan_template.ipad_model_u3d_id
     */
    private Integer ipadModelU3dId;

    /**
     * design_plan_template.ios_model_u3d_id
     */
    private Integer iosModelU3dId;

    /**
     * design_plan_template.android_model_u3d_id
     */
    private Integer androidModelU3dId;

    /**
     * design_plan_template.macBookpc_model_u3d_id
     */
    private Integer macbookpcModelU3dId;

    /**
     * design_plan_template.pc_model_u3d_id
     */
    private Integer pcModelU3dId;

    /**
     * design_plan_template.web_model_u3d_id
     */
    private Integer webModelU3dId;

    /**
     * design_plan_template.media_type
     */
    private Integer mediaType;

    /**
     * design_plan_template.evening_file_id
     */
    private Integer eveningFileId;

    /**
     * design_plan_template.dawn_file_id
     */
    private Integer dawnFileId;

    /**
     * design_plan_template.night_file_id
     */
    private Integer nightFileId;

    /**
     * design_plan_template.share_total
     */
    private Integer shareTotal;

    /**
     * design_plan_template.is_open
     */
    private Integer isOpen;

    /**
     * design_plan_template.draft_state
     */
    private Integer draftState;

    /**
     * design_plan_template.baiMo_state
     */
    private Integer baimoState;

    /**
     * design_plan_template.stuff_finish_state
     */
    private Integer stuffFinishState;

    /**
     * design_plan_template.decorate_finish_state
     */
    private Integer decorateFinishState;

    /**
     * design_plan_template.is_change
     */
    private Integer isChange;

    /**
     * 是否装修过<p>
     * design_plan_template.is_decorated
     */
    private Integer isDecorated;

    /**
     * 方案来源<p>
     * design_plan_template.plan_source
     */
    private String planSource;

    /**
     * 小区户型名称<p>
     * design_plan_template.residential_units_name
     */
    private String residentialUnitsName;

    /**
     * 户型ID<p>
     * design_plan_template.house_id
     */
    private Integer houseId;

    /**
     * 小区ID<p>
     * design_plan_template.living_id
     */
    private Integer livingId;

    /**
     * 设计方案封面<p>
     * design_plan_template.cover_pic_id
     */
    private Integer coverPicId;

    /**
     * 设计方案是否发布 0 否 1是<p>
     * design_plan_template.is_release
     */
    private Integer isRelease;

    /**
     * 方案推荐风格ID<p>
     * design_plan_template.design_recommended_style_id
     */
    private Integer designRecommendedStyleId;

    /**
     * 设计方案发布时间 （需要通过发布时间进行排序）<p>
     * design_plan_template.release_time
     */
    private Date releaseTime;

    /**
     * 特效配置<p>
     * design_plan_template.effects_config
     */
    private String effectsConfig;

    /**
     * 是否默认推荐<p>
     * design_plan_template.is_recommended
     */
    private Integer isRecommended;

    /**
     * 是否支持一键装修<p>
     * design_plan_template.is_default_decorate
     */
    private Integer isDefaultDecorate;

    /**
     * 方案编号<p>
     * design_plan_template.plan_number
     */
    private String planNumber;

    /**
     * 表明该设计由进入渲染场景时创建的临时方案，0代表不可见，1代表可见<p>
     * design_plan_template.visible
     */
    private Byte visible;

    /**
     * 设计方案副本id<p>
     * design_plan_template.design_scene_id
     */
    private Long designSceneId;

    /**
     * 和副本比较，判断场景是否发生改变<p>
     * design_plan_template.scene_modified
     */
    private Long sceneModified;

    /**
     * 设计方案类型(0:普通设计方案;1:一件装修设计方案)<p>
     * design_plan_template.plan_type
     */
    private Byte planType;

    /**
     * 用户创建的方案来源(0为DIY方案，1为一键替换方案，3为改造方案)<p>
     * design_plan_template.business_type
     */
    private Byte businessType;

    /**
     * 拼花产品集合 <p>
     * design_plan_template.spelling_flower_product
     */
    private String spellingFlowerProduct;

    /**
     * 拼花信息文件id <p>
     * design_plan_template.spelling_flower_file_id
     */
    private Integer spellingFlowerFileId;

    /**
     * 平台ID<p>
     * design_plan_template.platform_id
     */
    private Long platformId;

    /**
     * 水刀配置<p>
     * design_plan_template.waterjet_info
     */
    private String waterjetInfo;

    /**
     * 使用状态(0:废弃，1:启用)<p>
     * design_plan_template.use_state
     */
    private Integer useState;

    /**
     * 异常备注<p>
     * design_plan_template.exception_remark
     */
    private String exceptionRemark;

    /**
     * 替换类型(1:硬装替换, 2:全屋替换)<p>
     * design_plan_template.replace_type
     */
    private Integer replaceType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRecommendedPlanId() {
		return recommendedPlanId;
	}

	public void setRecommendedPlanId(Long recommendedPlanId) {
		this.recommendedPlanId = recommendedPlanId;
	}

	public String getDesignTemplateId() {
		return designTemplateId;
	}

	public void setDesignTemplateId(String designTemplateId) {
		this.designTemplateId = designTemplateId;
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

	public Integer getIpadModelU3dId() {
		return ipadModelU3dId;
	}

	public void setIpadModelU3dId(Integer ipadModelU3dId) {
		this.ipadModelU3dId = ipadModelU3dId;
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

	public Integer getMacbookpcModelU3dId() {
		return macbookpcModelU3dId;
	}

	public void setMacbookpcModelU3dId(Integer macbookpcModelU3dId) {
		this.macbookpcModelU3dId = macbookpcModelU3dId;
	}

	public Integer getPcModelU3dId() {
		return pcModelU3dId;
	}

	public void setPcModelU3dId(Integer pcModelU3dId) {
		this.pcModelU3dId = pcModelU3dId;
	}

	public Integer getWebModelU3dId() {
		return webModelU3dId;
	}

	public void setWebModelU3dId(Integer webModelU3dId) {
		this.webModelU3dId = webModelU3dId;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getEveningFileId() {
		return eveningFileId;
	}

	public void setEveningFileId(Integer eveningFileId) {
		this.eveningFileId = eveningFileId;
	}

	public Integer getDawnFileId() {
		return dawnFileId;
	}

	public void setDawnFileId(Integer dawnFileId) {
		this.dawnFileId = dawnFileId;
	}

	public Integer getNightFileId() {
		return nightFileId;
	}

	public void setNightFileId(Integer nightFileId) {
		this.nightFileId = nightFileId;
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

	public Integer getBaimoState() {
		return baimoState;
	}

	public void setBaimoState(Integer baimoState) {
		this.baimoState = baimoState;
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

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}

	public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
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

	public Byte getVisible() {
		return visible;
	}

	public void setVisible(Byte visible) {
		this.visible = visible;
	}

	public Long getDesignSceneId() {
		return designSceneId;
	}

	public void setDesignSceneId(Long designSceneId) {
		this.designSceneId = designSceneId;
	}

	public Long getSceneModified() {
		return sceneModified;
	}

	public void setSceneModified(Long sceneModified) {
		this.sceneModified = sceneModified;
	}

	public Byte getPlanType() {
		return planType;
	}

	public void setPlanType(Byte planType) {
		this.planType = planType;
	}

	public Byte getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Byte businessType) {
		this.businessType = businessType;
	}

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

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getWaterjetInfo() {
		return waterjetInfo;
	}

	public void setWaterjetInfo(String waterjetInfo) {
		this.waterjetInfo = waterjetInfo;
	}

	public Integer getUseState() {
		return useState;
	}

	public void setUseState(Integer useState) {
		this.useState = useState;
	}

	public String getExceptionRemark() {
		return exceptionRemark;
	}

	public void setExceptionRemark(String exceptionRemark) {
		this.exceptionRemark = exceptionRemark;
	}

	public Integer getReplaceType() {
		return replaceType;
	}

	public void setReplaceType(Integer replaceType) {
		this.replaceType = replaceType;
	}

}