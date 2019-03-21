package com.sandu.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sandu.design.model.po.DesignPlanPO;
import com.sandu.system.model.RenderPicInfo;

/**
 * @Title: DesignPlan.java
 * @Package com.nork.onekeydesign.model
 * @Description:设计模块-设计方案
 * @createAuthor pandajun
 * @CreateDate 2015-07-03 17:09:51
 * @version V1.0
 */
public class DesignPlan extends DesignPlanPO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer designPlanId;
	
	/** 3D空间俯视图 **/
	private String view3dPic;
	
	/** 绑定父产品ID **/
	private String bindParentProductId;

	// 区分渲染图和缩略图 （0,1）
	private Integer picType;

	private String mostType; // 最多评论 、最多分享
	
	private Integer collectState;// 收藏状态
	
	private Integer likeState;// 点赞状态

	/* 发布权限 0无 1有 */
	private Integer releasePermissions;

	/* 复制权限 0无 1有 */
	private Integer copyPermissions;

	/* 多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名 */
	private String multifunctionQuery;

	private String brandName;

	/* 720渲染图二维码路径 */
	private String qrCode720Path;

	/**
	 * 作用于 vendorPlanList 方法
	 */
	private List<Integer> brandIds = new ArrayList<Integer>();
	
	/**
	 * 作用于 vendorPlanList 方法
	 */

	private List<Integer>userIds = new ArrayList<Integer>();
	
	private String queryType;
	
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
	
	private String  deviceId = null;
	
	private String  msgId = null;
	
	private String  ids = null;
	
	private Integer start = 0;
	
	private Integer limit = 20;
	
	private String  order = null;
	
	private String  orderNum = null;
	
	private String  orders = null;
	
	/**级别限制的资源数量*/
	private int levelLimitCount=0;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public int getLevelLimitCount() {
		return levelLimitCount;
	}

	public void setLevelLimitCount(int levelLimitCount) {
		this.levelLimitCount = levelLimitCount;
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

	public String getQrCode720Path() {
		return qrCode720Path;
	}

	public void setQrCode720Path(String qrCode720Path) {
		this.qrCode720Path = qrCode720Path;
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

	public Integer getLikeState() {
		return likeState;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getBindParentProductId() {
		return bindParentProductId;
	}

	public void setBindParentProductId(String bindParentProductId) {
		this.bindParentProductId = bindParentProductId;
	}
	
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

	public String getDesignPlanState() {
		return designPlanState;
	}

	public void setDesignPlanState(String designPlanState) {
		this.designPlanState = designPlanState;
	}

	private Integer amount;

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

	public String getView3dPic() {
		return view3dPic;
	}

	public void setView3dPic(String view3dPic) {
		this.view3dPic = view3dPic;
	}

	// 0 非父方案 1 父方案
	private Integer isParent;

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
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
		obj.setPlanCode(this.getPlanCode());
		obj.setPlanName(this.getPlanName());
		obj.setPlanSource(this.getPlanSource());
		obj.setHouseId(this.getHouseId());
		obj.setLivingId(this.getLivingId());
		obj.setResidentialUnitsName(this.getResidentialUnitsName());
		obj.setUserId(this.getUserId());
		obj.setDesignSourceType(this.getDesignSourceType());
		obj.setDesignId(this.getDesignId());
		obj.setDesignStyleId(this.getDesignStyleId());
		obj.setPicId(this.getPicId());
		obj.setModelId(this.getModelId());
		obj.setModel3dId(this.getModel3dId());
		obj.setConfigFileId(this.getConfigFileId());
		obj.setSpaceCommonId(this.getSpaceCommonId());
		obj.setPlanDesc(this.getPlanDesc());
		obj.setSysCode(this.getSysCode());
		obj.setCreator(this.getCreator());
		obj.setGmtCreate(this.getGmtCreate());
		obj.setModifier(this.getModifier());
		obj.setGmtModified(this.getGmtModified());
		obj.setIsDeleted(this.getIsDeleted());
		obj.setRemark(this.getRemark());
		obj.setDesignTemplateId(this.getDesignTemplateId());
		obj.setIpadModelU3dId(this.getIpadModelU3dId());
		obj.setIosModelU3dId(this.getIosModelU3dId());
		obj.setAndroidModelU3dId(this.getAndroidModelU3dId());
		obj.setMacBookpcModelU3dId(this.getMacBookpcModelU3dId());
		obj.setPcModelU3dId(this.getPcModelU3dId());
		obj.setWebModelU3dId(this.getWebModelU3dId());
		obj.setMediaType(this.getMediaType());
		obj.setShareTotal(this.getShareTotal());
		obj.setIsOpen(this.getIsOpen());
		obj.setDraftState(this.getDraftState());
		obj.setBaiMoState(this.getBaiMoState());
		obj.setStuffFinishState(this.getStuffFinishState());
		obj.setDecorateFinishState(this.getDecorateFinishState());
		obj.setIsChange(this.getIsChange());
		obj.setIsDecorated(this.getIsDecorated());
		obj.setPlanSource(this.getPlanSource());
		obj.setResidentialUnitsName(this.getResidentialUnitsName());
		obj.setHouseId(this.getHouseId());
		obj.setLivingId(this.getLivingId());
		obj.setEffectsConfig(this.getEffectsConfig());
		return obj;
	}

	public DesignPlanRecommended recommendedCopy() {
		DesignPlanRecommended obj = new DesignPlanRecommended();
		obj.setPlanCode(this.getPlanCode());
		obj.setPlanName(this.getPlanName());
		obj.setPlanSource(this.getPlanSource());
		obj.setHouseId(this.getHouseId());
		obj.setLivingId(this.getLivingId());
		obj.setResidentialUnitsName(this.getResidentialUnitsName());
		obj.setUserId(this.getUserId());
		obj.setDesignSourceType(this.getDesignSourceType());
		obj.setDesignId(this.getDesignId());
		obj.setDesignStyleId(this.getDesignStyleId());
		obj.setPicId(this.getPicId());
		obj.setModelId(this.getModelId());
		obj.setModel3dId(this.getModel3dId());
		obj.setConfigFileId(this.getConfigFileId());
		obj.setSpaceCommonId(this.getSpaceCommonId());
		obj.setPlanDesc(this.getPlanDesc());
		obj.setSysCode(this.getSysCode());
		obj.setCreator(this.getCreator());
		obj.setGmtCreate(this.getGmtCreate());
		obj.setModifier(this.getModifier());
		obj.setGmtModified(this.getGmtModified());
		obj.setIsDeleted(this.getIsDeleted());
		obj.setRemark(this.getRemark());
		obj.setDesignTemplateId(this.getDesignTemplateId());
		obj.setIpadModelU3dId(this.getIpadModelU3dId());
		obj.setIosModelU3dId(this.getIosModelU3dId());
		obj.setAndroidModelU3dId(this.getAndroidModelU3dId());
		obj.setMacBookpcModelU3dId(this.getMacBookpcModelU3dId());
		obj.setPcModelU3dId(this.getPcModelU3dId());
		obj.setWebModelU3dId(this.getWebModelU3dId());
		obj.setMediaType(this.getMediaType());
		obj.setShareTotal(this.getShareTotal());
		obj.setIsOpen(this.getIsOpen());
		obj.setDraftState(this.getDraftState());
		obj.setBaiMoState(this.getBaiMoState());
		obj.setStuffFinishState(this.getStuffFinishState());
		obj.setDecorateFinishState(this.getDecorateFinishState());
		obj.setIsChange(this.getIsChange());
		obj.setIsDecorated(this.getIsDecorated());
		obj.setPlanSource(this.getPlanSource());
		obj.setResidentialUnitsName(this.getResidentialUnitsName());
		obj.setHouseId(this.getHouseId());
		obj.setLivingId(this.getLivingId());
		obj.setEffectsConfig(this.getEffectsConfig());
		return obj;
	}

	/** 获取对象的map **/
	/*public Map toMap() {
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
	}*/

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

}
