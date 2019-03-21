package com.nork.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nork.common.model.Mapper;

/**   
 * copy from DesignPlan.java
 * @Title: DesignPlanEctype.java 
 * @Package com.nork.design.model
 * @Description:设计模块-设计方案副本
 * @createAuthor huangsongbo
 * @CreateDate 2017.7.14
 * @version V1.0   
 */
public class DesignPlanRenderScene  extends Mapper implements Serializable{

	private static final long serialVersionUID = 3096956281578156380L;

	private Integer id;
    
    private Integer designPlanId;
    
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
	private Integer modelId;
	
	/**  模型文件id  **/
	private Integer model3dId;
	
	/**  模型文件id  **/
	private Integer modelU3dId;
	
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
	
	/** 3D空间俯视图 **/
	private String view3dPic;
	
	/** 样板房ID **/
	private Integer designTemplateId;
	
	/** 媒介类型 **/
	private Integer mediaType;
	
	/** 分享数量  **/
	private Integer shareTotal;
	
	/**公开*/
	private Integer isOpen;

	private Integer webModelU3dId;
	
	private Integer iosModelU3dId;
	
	private Integer androidModelU3dId;
	
	private Integer pcModelU3dId;
	
	private Integer ipadModelU3dId;
	
	private Integer macBookpcModelU3dId;
	
	/**  绑定父产品ID  **/
	private String bindParentProductId;
	
	/**方案来源*/
	private String planSource;
	
	/**小区户型名称*/
	private String residentialUnitsName;
	
	private Integer houseId;
	
	private Integer livingId;

	//区分渲染图和缩略图 （0,1）
	private Integer picType;
	
	private String mostType;  //最多评论 、最多分享
	
    private Integer collectState;//收藏状态
    
    private Integer likeState;//点赞状态

    /**设计方案封面*/
	private Integer coverPicId;
	
	/*设计方案是否发布 0 否 1是*/
	private Integer isRelease;
	
	/*设计方案发布时间 */
	private Date releaseTime;
	
	/*发布权限 0无  1有*/
	private Integer releasePermissions;
	
	/*复制权限 0无  1有*/
	private Integer copyPermissions;
	
	/*多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名*/
	private String multifunctionQuery;
	
	private String brandName;
	
	/*方案推荐风格ID*/
	private Integer designRecommendedStyleId;
	
	/*720渲染图二维码路径*/
	private String qrCode720Path;
	
	/*特效配置*/
	private String effectsConfig;
	
	/*是否默认推荐  1 默认 0 非默认*/
	private Integer isRecommended;
	
	/*是否支持一键装修  1 支持  0不支持*/
	private Integer isDefaultDecorate;
	
	/*方案编号*/
	private String  planNumber;
	
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
	
	/** 绑定点 **/
	private String bindPoint;
	
	/*临时userId:用于数据传递*/
	private Integer userIdTemp;
	
	/**  '是否装修完成状态 (1.未装修完成   2.已装修完成)', **/
	private String designPlanState;

	private Integer amount;
	
	//空房间借用
//	private Integer eveningFileId;
//	//样板房借用
//	private Integer dawnFileId;
//	//方案使用空房间存储借用
//	private Integer nightFileId;
	/*审核失败原因*/
	private String  failCause;
	
	/*审核人id*/
	private String checkUserName;
	
	/*是否为内部用户*/
	private boolean internalUsers; 
 
	/**
	 * 拼花 json 传
	 */
	private Integer spellingFlowerFileId;
 	/**
 	 * 拼花  产品ids 
 	 */
	private String spellingFlowerProduct;

	/**平台Id*/
	private Integer platformId;
	/**  平台所属分类 */
	private String platformBussinessType;
	
	/**
     * 经销商(主账号)id
     */
    private Integer franchiserId;
    

	public Integer getFranchiserId() {
		return franchiserId;
	}

	public void setFranchiserId(Integer franchiserId) {
		this.franchiserId = franchiserId;
	}

	public String getPlatformBussinessType() {
		return platformBussinessType;
	}

	public void setPlatformBussinessType(String platformBussinessType) {
		this.platformBussinessType = platformBussinessType;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public Integer getSpellingFlowerFileId() {
		return spellingFlowerFileId;
	}

	public void setSpellingFlowerFileId(Integer spellingFlowerFileId) {
		this.spellingFlowerFileId = spellingFlowerFileId;
	}

	public String getSpellingFlowerProduct() {
		return spellingFlowerProduct;
	}

	public void setSpellingFlowerProduct(String spellingFlowerProduct) {
		this.spellingFlowerProduct = spellingFlowerProduct;
	}

	public boolean isInternalUsers() {
		return internalUsers;
	}

	public void setInternalUsers(boolean internalUsers) {
		this.internalUsers = internalUsers;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}
	//设计来源编码
	private String designSourceCode;
	
	//设计模式
	private String designMode;
	
	/**  主体长度  **/
	private String mainLength;
	
	/**  主体宽度  **/
	private String mainWidth;
	
	private String planUserName;
	
    /**  空间功能类型  **/
	private Integer spaceFunctionId;
	
    //原作者id
    private Integer authorId;
    
	private String planStyle;
	
    private String spaceCode;
    
    private String spaceName;
    
    private String spaceAreas;
    
    private String picPath;
    
    private String filePath;
    
    private Integer templeId;
    
    private List<RenderPicInfo> picList = null;
    
    private Integer commentCount;//评论数量
    
    private Integer likeCount;//点赞数量
    
    private Integer shareCount;//分享数量
    
    private Integer fansConut;//关注数量
    
	private Integer attentionState;//是否关注
	
    private Integer planProductCount;//方案产品数量

    /**
     * scene_modified
     *  场景是否发生改变(时间戳)
     */
    private long sceneModified;

    List<Integer>brandIds = new ArrayList<Integer>();
    
    List<Integer>userIds = new ArrayList<Integer>();
    
    
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

	public long getSceneModified() {
		return sceneModified;
	}

	public void setSceneModified(long sceneModified) {
		this.sceneModified = sceneModified;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
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

	public String getBindParentProductId() {
		return bindParentProductId;
	}

	public void setBindParentProductId(String bindParentProductId) {
		this.bindParentProductId = bindParentProductId;
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

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getMostType() {
		return mostType;
	}

	public void setMostType(String mostType) {
		this.mostType = mostType;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public Integer getLikeState() {
		return likeState;
	}

	public void setLikeState(Integer likeState) {
		this.likeState = likeState;
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

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
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

	public Integer getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}

	public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}

	public String getQrCode720Path() {
		return qrCode720Path;
	}

	public void setQrCode720Path(String qrCode720Path) {
		this.qrCode720Path = qrCode720Path;
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

	public String getBindPoint() {
		return bindPoint;
	}

	public void setBindPoint(String bindPoint) {
		this.bindPoint = bindPoint;
	}

	public Integer getUserIdTemp() {
		return userIdTemp;
	}

	public void setUserIdTemp(Integer userIdTemp) {
		this.userIdTemp = userIdTemp;
	}

	public String getDesignPlanState() {
		return designPlanState;
	}

	public void setDesignPlanState(String designPlanState) {
		this.designPlanState = designPlanState;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDesignSourceCode() {
		return designSourceCode;
	}

	public void setDesignSourceCode(String designSourceCode) {
		this.designSourceCode = designSourceCode;
	}

	public String getDesignMode() {
		return designMode;
	}

	public void setDesignMode(String designMode) {
		this.designMode = designMode;
	}

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

	public String getPlanUserName() {
		return planUserName;
	}

	public void setPlanUserName(String planUserName) {
		this.planUserName = planUserName;
	}

	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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

	public List<RenderPicInfo> getPicList() {
		return picList;
	}

	public void setPicList(List<RenderPicInfo> picList) {
		this.picList = picList;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getFansConut() {
		return fansConut;
	}

	public void setFansConut(Integer fansConut) {
		this.fansConut = fansConut;
	}

	public Integer getAttentionState() {
		return attentionState;
	}

	public void setAttentionState(Integer attentionState) {
		this.attentionState = attentionState;
	}

	public Integer getPlanProductCount() {
		return planProductCount;
	}

	public void setPlanProductCount(Integer planProductCount) {
		this.planProductCount = planProductCount;
	}
	
	
	public DesignPlanRecommended recommendedCopy(){
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
//         obj.setDesignPlanRenderPicIds(this.designPlanRenderPicIds);
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
         obj.setEffectsConfig(effectsConfig);
         return obj;
    }
	
	public DesignPlan designPlanCopy() {
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
		obj.setSpellingFlowerProduct(this.spellingFlowerProduct);
		/* obj.setCoverPicId(this.coverPicId); */
		obj.setEffectsConfig(effectsConfig);
		return obj;
	}

	@Override
	public String toString() {
		return "DesignPlanRenderScene{" +
				"id=" + id +
				", designPlanId=" + designPlanId +
				", planCode='" + planCode + '\'' +
				", planName='" + planName + '\'' +
				", userId=" + userId +
				", designSourceType=" + designSourceType +
				", designId=" + designId +
				", designStyleId=" + designStyleId +
				", picId=" + picId +
				", modelId=" + modelId +
				", model3dId=" + model3dId +
				", modelU3dId=" + modelU3dId +
				", configFileId=" + configFileId +
				", spaceCommonId=" + spaceCommonId +
				", planDesc='" + planDesc + '\'' +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", remark='" + remark + '\'' +
				", view3dPic='" + view3dPic + '\'' +
				", designTemplateId=" + designTemplateId +
				", mediaType=" + mediaType +
				", shareTotal=" + shareTotal +
				", isOpen=" + isOpen +
				", webModelU3dId=" + webModelU3dId +
				", iosModelU3dId=" + iosModelU3dId +
				", androidModelU3dId=" + androidModelU3dId +
				", pcModelU3dId=" + pcModelU3dId +
				", ipadModelU3dId=" + ipadModelU3dId +
				", macBookpcModelU3dId=" + macBookpcModelU3dId +
				", bindParentProductId='" + bindParentProductId + '\'' +
				", planSource='" + planSource + '\'' +
				", residentialUnitsName='" + residentialUnitsName + '\'' +
				", houseId=" + houseId +
				", livingId=" + livingId +
				", picType=" + picType +
				", mostType='" + mostType + '\'' +
				", collectState=" + collectState +
				", likeState=" + likeState +
				", coverPicId=" + coverPicId +
				", isRelease=" + isRelease +
				", releaseTime=" + releaseTime +
				", releasePermissions=" + releasePermissions +
				", copyPermissions=" + copyPermissions +
				", multifunctionQuery='" + multifunctionQuery + '\'' +
				", brandName='" + brandName + '\'' +
				", designRecommendedStyleId=" + designRecommendedStyleId +
				", qrCode720Path='" + qrCode720Path + '\'' +
				", effectsConfig='" + effectsConfig + '\'' +
				", isRecommended=" + isRecommended +
				", isDefaultDecorate=" + isDefaultDecorate +
				", planNumber='" + planNumber + '\'' +
				", draftState=" + draftState +
				", baiMoState=" + baiMoState +
				", stuffFinishState=" + stuffFinishState +
				", decorateFinishState=" + decorateFinishState +
				", isChange=" + isChange +
				", isDecorated=" + isDecorated +
				", bindPoint='" + bindPoint + '\'' +
				", userIdTemp=" + userIdTemp +
				", designPlanState='" + designPlanState + '\'' +
				", amount=" + amount +
				", failCause='" + failCause + '\'' +
				", checkUserName='" + checkUserName + '\'' +
				", internalUsers=" + internalUsers +
				", spellingFlowerFileId=" + spellingFlowerFileId +
				", spellingFlowerProduct='" + spellingFlowerProduct + '\'' +
				", platformId=" + platformId +
				", platformBussinessType='" + platformBussinessType + '\'' +
				", franchiserId=" + franchiserId +
				", designSourceCode='" + designSourceCode + '\'' +
				", designMode='" + designMode + '\'' +
				", mainLength='" + mainLength + '\'' +
				", mainWidth='" + mainWidth + '\'' +
				", planUserName='" + planUserName + '\'' +
				", spaceFunctionId=" + spaceFunctionId +
				", authorId=" + authorId +
				", planStyle='" + planStyle + '\'' +
				", spaceCode='" + spaceCode + '\'' +
				", spaceName='" + spaceName + '\'' +
				", spaceAreas='" + spaceAreas + '\'' +
				", picPath='" + picPath + '\'' +
				", filePath='" + filePath + '\'' +
				", templeId=" + templeId +
				", picList=" + picList +
				", commentCount=" + commentCount +
				", likeCount=" + likeCount +
				", shareCount=" + shareCount +
				", fansConut=" + fansConut +
				", attentionState=" + attentionState +
				", planProductCount=" + planProductCount +
				", sceneModified=" + sceneModified +
				", brandIds=" + brandIds +
				", userIds=" + userIds +
				'}';
	}
}
