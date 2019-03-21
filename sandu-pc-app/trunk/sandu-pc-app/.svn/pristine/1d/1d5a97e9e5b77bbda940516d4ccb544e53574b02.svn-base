package com.sandu.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sandu.design.model.po.DesignPlanRecommendedPO;
import com.sandu.system.model.RenderPicInfo;

/**
 * 方案推荐
 * @author Administrator
 *
 */
public class DesignPlanRecommended extends DesignPlanRecommendedPO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*如果这个有值，默认用这个风格将数据置顶，只用于一件装修页面*/
	private Integer designRecommendedStyleIdTop;

	private List<Integer>IsReleases;

	/*发布权限 0无  1有*/
	private Integer releasePermissions;
	/*复制权限 0无  1有*/
	private Integer copyPermissions;
	/*多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名*/
	private String multifunctionQuery;
	private String brandName;
	/*720渲染图二维码路径*/
	private String qrCode720Path;
	
	/*是否支持一键装修  1 支持  0不支持*/
	private Integer isDefaultDecorate;
	
	private String displayType;
 
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
	
	public Integer getDesignRecommendedStyleIdTop() {
		return designRecommendedStyleIdTop;
	}
	public void setDesignRecommendedStyleIdTop(Integer designRecommendedStyleIdTop) {
		this.designRecommendedStyleIdTop = designRecommendedStyleIdTop;
	}
	public List<Integer> getIsReleases() {
		return IsReleases;
	}
	public void setIsReleases(List<Integer> isReleases) {
		IsReleases = isReleases;
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
	public Integer getIsDefaultDecorate() {
		return isDefaultDecorate;
	}
	public void setIsDefaultDecorate(Integer isDefaultDecorate) {
		this.isDefaultDecorate = isDefaultDecorate;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public List<Integer> getBrandIds() {
		return BrandIds;
	}
	public void setBrandIds(List<Integer> brandIds) {
		BrandIds = brandIds;
	}
	public Integer getSpaceFunctionId() {
		return SpaceFunctionId;
	}
	public void setSpaceFunctionId(Integer spaceFunctionId) {
		SpaceFunctionId = spaceFunctionId;
	}
	public List<Integer> getSpaceFunctionIds() {
		return spaceFunctionIds;
	}
	public void setSpaceFunctionIds(List<Integer> spaceFunctionIds) {
		this.spaceFunctionIds = spaceFunctionIds;
	}
	public String getAreaValue() {
		return areaValue;
	}
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
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
	public String getCheckAdministrator() {
		return checkAdministrator;
	}
	public void setCheckAdministrator(String checkAdministrator) {
		this.checkAdministrator = checkAdministrator;
	}
	public Integer getPicType() {
		return picType;
	}
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	public List<RenderPicInfo> getPicList() {
		return picList;
	}
	public void setPicList(List<RenderPicInfo> picList) {
		this.picList = picList;
	}
	public String getPlanRecommendedUserName() {
		return planRecommendedUserName;
	}
	public void setPlanRecommendedUserName(String planRecommendedUserName) {
		this.planRecommendedUserName = planRecommendedUserName;
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
	public Integer getPlanRecommendedProductCount() {
		return planRecommendedProductCount;
	}
	public void setPlanRecommendedProductCount(Integer planRecommendedProductCount) {
		this.planRecommendedProductCount = planRecommendedProductCount;
	}
	public Integer getRecommendedDecorateState() {
		return recommendedDecorateState;
	}
	public void setRecommendedDecorateState(Integer recommendedDecorateState) {
		this.recommendedDecorateState = recommendedDecorateState;
	}
	public Integer getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}
	public String getFavoriteBid() {
		return favoriteBid;
	}
	public void setFavoriteBid(String favoriteBid) {
		this.favoriteBid = favoriteBid;
	}
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

	/**
	 * 方案推荐类型  1分享  2一键装修
	 */
	public class RecommendedType{
		/** 1分享 **/
		public static final int RECOMMENDED_TYPE_SHARE = 1;
		/** 2一键装修 **/
		public static final int RECOMMENDED_TYPE_DECORATE = 2;
	}

	/**
	 * 方案发布状态
	 */
	public class ReleaseStatus {

		/**
		 * 未发布
		 */
		public static final int NO_RELEASE = 0;
		/**
		 * 发布中
		 */
		public static final int IS_RELEASEING = 1;
		/**
		 * 测试发布中
		 */
		public static final int IS_TEST_RELEASE = 2;
		/**
		 *  待审核
		 */
		public static final int WAITING_CHECK_RELEASE = 3;
		/**
		 *	审核失败
		 */
		public static final int FAILURE_CHECK_RELEASE = 4;
	}
}
