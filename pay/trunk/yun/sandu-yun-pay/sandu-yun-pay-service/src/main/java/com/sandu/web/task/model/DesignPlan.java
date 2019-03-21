package com.sandu.web.task.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.common.model.Mapper;


/**
 * @Title: DesignPlan.java
 * @Package com.nork.design.model
 * @Description:设计模块-设计方案
 * @createAuthor wutehua
 * @CreateDate 2018-01-10 9:55:51
 * @version V1.0
 */
public class DesignPlan extends Mapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer designPlanId;
	/** 方案编码 **/
	private String planCode;
	/** 方案名称 **/
	private String planName;
	/** 设计者id **/
	private Integer userId;
	/** 方案来源类型 **/
	private Integer designSourceType;
	/** 来源id **/
	private Integer designId;
	/** 设计风格 **/
	private Integer designStyleId;
	/** 方案图片id **/
	private Integer picId;
	/** 模型文件id **/
	private Integer modelId;
	/** 模型文件id **/
	private Integer model3dId;
	// /** 模型文件id **/
	private Integer modelU3dId;
	/** 配置文件id **/
	private Integer configFileId;
	/** 空间id **/
	private Integer spaceCommonId;
	/** 方案描述 **/
	private String planDesc;
	/** 系统编码 **/
	private String sysCode;
	/** 创建者 **/
	private String creator;
	/** 创建时间 **/
	private Date gmtCreate;
	/** 修改人 **/
	private String modifier;
	/** 修改时间 **/
	private Date gmtModified;
	/** 是否删除 **/
	private Integer isDeleted;
	/** 备注 **/
	private String remark;
	/** 3D空间俯视图 **/
	private String view3dPic;
	/** 样板房ID **/
	private Integer designTemplateId;
	/** 媒介类型 **/
	private Integer mediaType;
	/** 分享数量 **/
	private Integer shareTotal;
	/**
	 * 公开
	 */
	private Integer isOpen;

	private Integer webModelU3dId;
	private Integer iosModelU3dId;
	private Integer androidModelU3dId;
	private Integer pcModelU3dId;
	private Integer ipadModelU3dId;
	private Integer macBookpcModelU3dId;

	/** 绑定父产品ID **/
	private String bindParentProductId;

	/** 方案来源  */
	private String planSource;
	/** 小区户型名称 */
	private String residentialUnitsName;

	private Integer houseId;
	private Integer livingId;

	// 区分渲染图和缩略图 （0,1）
	private Integer picType;

	private String mostType; // 最多评论 、最多分享
	private Integer collectState;// 收藏状态
	private Integer likeState;// 点赞状态

	/** 设计方案封面 */
	private Integer coverPicId;

	/* 设计方案是否发布 0 否 1是 */
	private Integer isRelease;

	/* 设计方案发布时间 */
	Date releaseTime;

	/* 发布权限 0无 1有 */
	private Integer releasePermissions;

	/* 复制权限 0无 1有 */
	private Integer copyPermissions;

	/* 多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名 */
	private String multifunctionQuery;

	private String brandName;

	/* 方案推荐风格ID */
	private Integer designRecommendedStyleId;

	/* 720渲染图二维码路径 */
	private String qrCode720Path;

	/* 特效配置 */
	private String effectsConfig;

	/* 是否默认推荐 1 默认 0 非默认 */
	private Integer isRecommended;
	/* 是否支持一键装修 1 支持 0不支持 */
	private Integer isDefaultDecorate;
	/* 方案编号 */
	private String planNumber;
	/**
	 * 设计方案副本id
	 */
	private Integer designSceneId;
	/**
	 * 该方案是否可见 0:可见，1:不可见
	 */
	private Integer visible;
	/**
	 * 场景是否发生改变(时间戳) 副本/临时
	 */
	private long sceneModified;
	/**
	 * 设计方案类型 0:普通设计方案 1:一件装修设计方案(创建过一件装修组合)
	 */
	private Integer planType;
	
	/** 用户创建的方案来源类型 （0为DIY方案，1为一键替换方案，3为改造方案） **/
	private Integer businessType;
	
	/**
	 * 作用于 vendorPlanList 方法
	 */
	private List<Integer> brandIds = new ArrayList<Integer>();
	/**
	 * 作用于 vendorPlanList 方法
	 */

	private List<Integer>userIds = new ArrayList<Integer>();
	
	private String queryType;
	 
	
	/**
	 * 前段传来的 拼花 json 传
	 */
	private Integer spellingFlowerFileId;
	 //拼花  产品ids 
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

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public List<Integer> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Integer> brandIds) {
		this.brandIds = brandIds;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public Integer getDesignSceneId() {
		return designSceneId;
	}

	public void setDesignSceneId(Integer designSceneId) {
		this.designSceneId = designSceneId;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public long getSceneModified() {
		return sceneModified;
	}

	public void setSceneModified(long sceneModified) {
		this.sceneModified = sceneModified;
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

	public String getEffectsConfig() {
		return effectsConfig;
	}

	public void setEffectsConfig(String effectsConfig) {
		this.effectsConfig = effectsConfig;
	}

	public String getQrCode720Path() {
		return qrCode720Path;
	}

	public void setQrCode720Path(String qrCode720Path) {
		this.qrCode720Path = qrCode720Path;
	}

	public Integer getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}

	public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMultifunctionQuery() {
		return multifunctionQuery;
	}

	public void setMultifunctionQuery(String multifunctionQuery) {
		this.multifunctionQuery = multifunctionQuery;
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

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getCoverPicId() {
		return coverPicId;
	}

	public void setCoverPicId(Integer coverPicId) {
		this.coverPicId = coverPicId;
	}

	public Integer getLikeState() {
		return likeState;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
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

	public String getResidentialUnitsName() {
		return residentialUnitsName;
	}

	public void setResidentialUnitsName(String residentialUnitsName) {
		this.residentialUnitsName = residentialUnitsName;
	}

	public String getPlanSource() {
		return planSource;
	}

	public void setPlanSource(String planSource) {
		this.planSource = planSource;
	}

	public String getBindParentProductId() {
		return bindParentProductId;
	}

	public void setBindParentProductId(String bindParentProductId) {
		this.bindParentProductId = bindParentProductId;
	}

	// 草稿状态
	private Integer draftState;
	// 白模状态
	private Integer baiMoState;
	// 硬装完成状态
	private Integer stuffFinishState;
	// 装修完成状态
	private Integer decorateFinishState;
	// 方案是否变更
	private Integer isChange;
	// 设计方案是否装修过 （1 装修过，0 没装修过）
	private Integer isDecorated;
	/** 绑定点 **/
	private String bindPoint;
	/* 临时userId:用于数据传递 */
	private Integer userIdTemp;

	/** '是否装修完成状态 (1.未装修完成 2.已装修完成)', **/
	private String designPlanState;

	public Integer getUserIdTemp() {
		return userIdTemp;
	}

	public void setUserIdTemp(Integer userIdTemp) {
		this.userIdTemp = userIdTemp;
	}

	public Integer getIsDecorated() {
		return isDecorated;
	}

	public void setIsDecorated(Integer isDecorated) {
		this.isDecorated = isDecorated;
	}

	public String getDesignPlanState() {
		return designPlanState;
	}

	public void setDesignPlanState(String designPlanState) {
		this.designPlanState = designPlanState;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	private Integer amount;
	// 空房间借用
	// private Integer eveningFileId;
	// //样板房借用
	// private Integer dawnFileId;
	// //方案使用空房间存储借用
	// private Integer nightFileId;

	// 设计来源编码
	private String designSourceCode;

	public String getMainLength() {
		return mainLength;
	}

	public void setMainLength(String mainLength) {
		this.mainLength = mainLength;
	}

	public String getMainWidth() {
		return mainWidth;
	}

	public void setMainWidth(String mainWidth) {
		this.mainWidth = mainWidth;
	}

	// 设计模式
	private String designMode;
	/** 主体长度 **/
	private String mainLength;
	/** 主体宽度 **/
	private String mainWidth;
	private String planUserName;
	/** 空间功能类型 **/
	private Integer spaceFunctionId;
	// 原作者id
	private Integer authorId;
	private String planStyle;
	private String spaceCode;
	private String spaceName;
	private String spaceAreas;
	private String picPath;
	private String filePath;
	private Integer templeId;
	List<RenderPicInfo> picList = null;
	private Integer commentCount;// 评论数量
	private Integer likeCount;// 点赞数量
	private Integer shareCount;// 分享数量
	private Integer fansConut;// 关注数量

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	public String getDesignMode() {
		return designMode;
	}

	public void setDesignMode(String designMode) {
		this.designMode = designMode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getShareTotal() {
		return shareTotal;
	}

	public void setShareTotal(Integer shareTotal) {
		this.shareTotal = shareTotal;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public String getDesignSourceCode() {
		return designSourceCode;
	}

	public void setDesignSourceCode(String designSourceCode) {
		this.designSourceCode = designSourceCode;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
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

	public String getView3dPic() {
		return view3dPic;
	}

	public void setView3dPic(String view3dPic) {
		this.view3dPic = view3dPic;
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

	// public Integer getNightFileId() {
	// return nightFileId;
	// }
	//
	// public void setNightFileId(Integer nightFileId) {
	// this.nightFileId = nightFileId;
	// }
	//
	// public Integer getEveningFileId() {
	// return eveningFileId;
	// }
	//
	// public void setEveningFileId(Integer eveningFileId) {
	// this.eveningFileId = eveningFileId;
	// }
	//
	// public Integer getDawnFileId() {
	// return dawnFileId;
	// }
	//
	// public void setDawnFileId(Integer dawnFileId) {
	// this.dawnFileId = dawnFileId;
	// }

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

	// 推荐方案ID
	private Integer recommendedPlanId;
	// 0 非父方案 1 父方案
	private Integer isParent;

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public Integer getRecommendedPlanId() {
		return recommendedPlanId;
	}

	public void setRecommendedPlanId(Integer recommendedPlanId) {
		this.recommendedPlanId = recommendedPlanId;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		DesignPlan other = (DesignPlan) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getPlanCode() == null ? other.getPlanCode() == null
						: this.getPlanCode().equals(other.getPlanCode()))
				&& (this.getPlanName() == null ? other.getPlanName() == null
						: this.getPlanName().equals(other.getPlanName()))
				&& (this.getPlanSource() == null ? other.getPlanSource() == null
						: this.getPlanSource().equals(other.getPlanSource()))
				&& (this.getHouseId() == null ? other.getHouseId() == null
						: this.getHouseId().equals(other.getHouseId()))
				&& (this.getLivingId() == null ? other.getLivingId() == null
						: this.getLivingId().equals(other.getLivingId()))
				&& (this.getResidentialUnitsName() == null ? other.getResidentialUnitsName() == null
						: this.getResidentialUnitsName().equals(other.getResidentialUnitsName()))
				&& (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
				&& (this.getDesignSourceType() == null ? other.getDesignSourceType() == null
						: this.getDesignSourceType().equals(other.getDesignSourceType()))
				&& (this.getDesignId() == null ? other.getDesignId() == null
						: this.getDesignId().equals(other.getDesignId()))
				&& (this.getDesignStyleId() == null ? other.getDesignStyleId() == null
						: this.getDesignStyleId().equals(other.getDesignStyleId()))
				&& (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
				&& (this.getModelId() == null ? other.getModelId() == null
						: this.getModelId().equals(other.getModelId()))
				&& (this.getModel3dId() == null ? other.getModel3dId() == null
						: this.getModel3dId().equals(other.getModel3dId()))
				// && (this.getModelU3dId() == null ? other.getModelU3dId() == null :
				// this.getModelU3dId().equals(other.getModelU3dId()))
				&& (this.getConfigFileId() == null ? other.getConfigFileId() == null
						: this.getConfigFileId().equals(other.getConfigFileId()))
				&& (this.getSpaceCommonId() == null ? other.getSpaceCommonId() == null
						: this.getSpaceCommonId().equals(other.getSpaceCommonId()))
				&& (this.getPlanDesc() == null ? other.getPlanDesc() == null
						: this.getPlanDesc().equals(other.getPlanDesc()))
				&& (this.getSysCode() == null ? other.getSysCode() == null
						: this.getSysCode().equals(other.getSysCode()))
				&& (this.getCreator() == null ? other.getCreator() == null
						: this.getCreator().equals(other.getCreator()))
				&& (this.getGmtCreate() == null ? other.getGmtCreate() == null
						: this.getGmtCreate().equals(other.getGmtCreate()))
				&& (this.getModifier() == null ? other.getModifier() == null
						: this.getModifier().equals(other.getModifier()))
				&& (this.getGmtModified() == null ? other.getGmtModified() == null
						: this.getGmtModified().equals(other.getGmtModified()))
				&& (this.getIsDeleted() == null ? other.getIsDeleted() == null
						: this.getIsDeleted().equals(other.getIsDeleted()))
				&& (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getPlanCode() == null) ? 0 : getPlanCode().hashCode());
		result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
		result = prime * result + ((getPlanSource() == null) ? 0 : getPlanSource().hashCode());
		result = prime * result + ((getHouseId() == null) ? 0 : getHouseId().hashCode());
		result = prime * result + ((getLivingId() == null) ? 0 : getLivingId().hashCode());
		result = prime * result + ((getResidentialUnitsName() == null) ? 0 : getResidentialUnitsName().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getDesignSourceType() == null) ? 0 : getDesignSourceType().hashCode());
		result = prime * result + ((getDesignId() == null) ? 0 : getDesignId().hashCode());
		result = prime * result + ((getDesignStyleId() == null) ? 0 : getDesignStyleId().hashCode());
		result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
		result = prime * result + ((getPlanDesc() == null) ? 0 : getPlanDesc().hashCode());
		result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
		result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
		result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
		result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
		result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
		;
		return result;
	}

	/** 获取对象的copy **/
	public DesignPlan copy() {
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
		// obj.setModelU3dId(this.modelU3dId);
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
		/*
		 * obj.setEveningFileId(this.eveningFileId); obj.setDawnFileId(this.dawnFileId
		 * ); obj.setNightFileId(this.nightFileId );
		 */
		obj.setShareTotal(this.shareTotal);
		obj.setIsOpen(this.isOpen);
		obj.setDraftState(this.draftState);
		obj.setBaiMoState(this.baiMoState);
		obj.setStuffFinishState(this.stuffFinishState);
		obj.setDecorateFinishState(this.decorateFinishState);
		obj.setIsChange(this.isChange);
		obj.setIsDecorated(this.isDecorated);
		obj.setPlanSource(this.planSource);
		obj.setResidentialUnitsName(this.residentialUnitsName);
		obj.setHouseId(this.houseId);
		obj.setLivingId(this.livingId);
		/* obj.setCoverPicId(this.coverPicId); */
		obj.setEffectsConfig(effectsConfig);
		return obj;
	}

	public DesignPlanRecommended recommendedCopy() {
		DesignPlanRecommended obj = new DesignPlanRecommended();
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
		// obj.setDesignPlanRenderPicIds(this.designPlanRenderPicIds);
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
		/*
		 * obj.setEveningFileId(this.eveningFileId); obj.setDawnFileId(this.dawnFileId
		 * ); obj.setNightFileId(this.nightFileId );
		 */
		obj.setShareTotal(this.shareTotal);
		obj.setIsOpen(this.isOpen);
		obj.setDraftState(this.draftState);
		obj.setBaiMoState(this.baiMoState);
		obj.setStuffFinishState(this.stuffFinishState);
		obj.setDecorateFinishState(this.decorateFinishState);
		obj.setIsChange(this.isChange);
		obj.setIsDecorated(this.isDecorated);
		obj.setPlanSource(this.planSource);
		obj.setResidentialUnitsName(this.residentialUnitsName);
		obj.setHouseId(this.houseId);
		obj.setLivingId(this.livingId);
		/* obj.setCoverPicId(this.coverPicId); */
		obj.setEffectsConfig(effectsConfig);
		return obj;
	}

	/** 获取对象的map **/
	public Map toMap() {
		Map map = new HashMap();
		map.put("planCode", this.planCode);
		map.put("planName", this.planName);
		map.put("planSource", this.planSource);
		map.put("houseId", this.houseId);
		map.put("livingId", this.livingId);
		map.put("residentialUnitsName", this.residentialUnitsName);
		map.put("userId", this.userId);
		map.put("designSourceType", this.designSourceType);
		map.put("designId", this.designId);
		map.put("designStyleId", this.designStyleId);
		map.put("picId", this.picId);
		map.put("modelId", this.modelId);
		map.put("model3dId", this.model3dId);
		// map.put("modelU3dId",this.modelU3dId);
		map.put("configFileId", this.configFileId);
		map.put("spaceCommonId", this.spaceCommonId);
		map.put("planDesc", this.planDesc);
		map.put("sysCode", this.sysCode);
		map.put("creator", this.creator);
		map.put("gmtCreate", this.gmtCreate);
		map.put("modifier", this.modifier);
		map.put("gmtModified", this.gmtModified);
		map.put("isDeleted", this.isDeleted);
		map.put("remark", this.remark);

		return map;
	}

	public Integer getFansConut() {
		return fansConut;
	}

	public void setFansConut(Integer fansConut) {
		this.fansConut = fansConut;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public void setLikeState(Integer likeState) {
		this.likeState = likeState;
	}

	private Integer attentionState;// 是否关注
	private Integer planProductCount;// 方案产品数量

	public Integer getAttentionState() {
		return attentionState;
	}

	public void setAttentionState(Integer attentionState) {
		this.attentionState = attentionState;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getPlanProductCount() {
		return planProductCount;
	}

	public void setPlanProductCount(Integer planProductCount) {
		this.planProductCount = planProductCount;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public String getMostType() {
		return mostType;
	}

	public void setMostType(String mostType) {
		this.mostType = mostType;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getPlanUserName() {
		return planUserName;
	}

	public void setPlanUserName(String planUserName) {
		this.planUserName = planUserName;
	}

	public String getPlanStyle() {
		return planStyle;
	}

	public void setPlanStyle(String planStyle) {
		this.planStyle = planStyle;
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

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getTempleId() {
		return templeId;
	}

	public void setTempleId(Integer templeId) {
		this.templeId = templeId;
	}

	public String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public List<RenderPicInfo> getPicList() {
		return picList;
	}

	public void setPicList(List<RenderPicInfo> picList) {
		this.picList = picList;
	}

	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public String getBindPoint() {
		return bindPoint;
	}

	public void setBindPoint(String bindPoint) {
		this.bindPoint = bindPoint;
	}

  public Integer getBusinessType() {
    return businessType;
  }

  public void setBusinessType(Integer businessType) {
    this.businessType = businessType;
  }

  
	
}
