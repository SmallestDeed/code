package com.sandu.design.model;

import java.io.Serializable;

import com.sandu.design.model.po.DesignTempletPO;

/**
 * @Title: DesignTemplet.java
 * @Package com.nork.onekeydesign.model
 * @Description:设计模块-设计方案样板房表
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 * @version V1.0
 */
public class DesignTemplet extends DesignTempletPO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String spaceCode;
	
	// 空间布局图缩略图id
	private Integer picSmallId;
	// 空间设计图
	private String designPicPath;
	
	private Integer newPcModelU3dId;

	// 风格名称
	private String designStyleName;
	// 通用空间名称
	private String spaceCommonName;
	// 图片路径(渲染效果图path)
	private String picPath;
	// 图片路径(渲染效果缩略图)
	private String smallpicPath;
	// 面积
	private String spaceAreas;
	// 空间功能类型
	private Integer spaceFunctionId;

	// 效果图缩略图id
	private Integer effecPicSmallId;

	/** 效果图列表路径 **/
	private String[] effectPicListPath;

	/** 效果图列表缩略图，取列表第一个 **/
	private String effectSmallPicPath;
	
	/* 平面效果缩略图id */
	private Integer effectPlanSmallId;
	/* 平面效果图url */
	private String effectPlanUrl;
	/* 平面效果图对应的缩略图url */
	private String effectPlanSmallUrl;
	/** 场景缩略图 **/
	private Integer thumbnailId;
	
	/**空间的模型id*/
	private String spaceCommonCode;
	private String iosU3dModelId;
	
	private String macBookPcU3dModelId;
	private String windowsPcU3dModelId;
	private String ipadU3dModelId;
	private String androidU3dModelId;
	
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

	public Integer getNewPcModelU3dId() {
		return newPcModelU3dId;
	}

	public void setNewPcModelU3dId(Integer newPcModelU3dId) {
		this.newPcModelU3dId = newPcModelU3dId;
	}

	public String getSpaceCommonCode() {
		return spaceCommonCode;
	}

	public void setSpaceCommonCode(String spaceCommonCode) {
		this.spaceCommonCode = spaceCommonCode;
	}

	public String getIosU3dModelId() {
		return iosU3dModelId;
	}

	public void setIosU3dModelId(String iosU3dModelId) {
		this.iosU3dModelId = iosU3dModelId;
	}

	public String getMacBookPcU3dModelId() {
		return macBookPcU3dModelId;
	}

	public void setMacBookPcU3dModelId(String macBookPcU3dModelId) {
		this.macBookPcU3dModelId = macBookPcU3dModelId;
	}

	public String getWindowsPcU3dModelId() {
		return windowsPcU3dModelId;
	}

	public void setWindowsPcU3dModelId(String windowsPcU3dModelId) {
		this.windowsPcU3dModelId = windowsPcU3dModelId;
	}

	public String getIpadU3dModelId() {
		return ipadU3dModelId;
	}

	public void setIpadU3dModelId(String ipadU3dModelId) {
		this.ipadU3dModelId = ipadU3dModelId;
	}

	public String getAndroidU3dModelId() {
		return androidU3dModelId;
	}

	public void setAndroidU3dModelId(String androidU3dModelId) {
		this.androidU3dModelId = androidU3dModelId;
	}

	public String getEffectPlanUrl() {
		return effectPlanUrl;
	}

	public void setEffectPlanUrl(String effectPlanUrl) {
		this.effectPlanUrl = effectPlanUrl;
	}

	public String getEffectPlanSmallUrl() {
		return effectPlanSmallUrl;
	}

	public void setEffectPlanSmallUrl(String effectPlanSmallUrl) {
		this.effectPlanSmallUrl = effectPlanSmallUrl;
	}

	public Integer getEffectPlanSmallId() {
		return effectPlanSmallId;
	}

	public void setEffectPlanSmallId(Integer effectPlanSmallId) {
		this.effectPlanSmallId = effectPlanSmallId;
	}

	public Integer getPicSmallId() {
		return picSmallId;
	}

	public void setPicSmallId(Integer picSmallId) {
		this.picSmallId = picSmallId;
	}

	public Integer getEffecPicSmallId() {
		return effecPicSmallId;
	}

	public void setEffecPicSmallId(Integer effecPicSmallId) {
		this.effecPicSmallId = effecPicSmallId;
	}

	public String[] getEffectPicListPath() {
		return effectPicListPath;
	}

	public void setEffectPicListPath(String[] effectPicListPath) {
		this.effectPicListPath = effectPicListPath;
	}

	public String getEffectSmallPicPath() {
		return effectSmallPicPath;
	}

	public void setEffectSmallPicPath(String effectSmallPicPath) {
		this.effectSmallPicPath = effectSmallPicPath;
	}

	public String getSmallpicPath() {
		return smallpicPath;
	}

	public void setSmallpicPath(String smallpicPath) {
		this.smallpicPath = smallpicPath;
	}

	public String getDesignPicPath() {
		return designPicPath;
	}

	public void setDesignPicPath(String designPicPath) {
		this.designPicPath = designPicPath;
	}

	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	private Integer houseTypeValue;

	public Integer getHouseTypeValue() {
		return houseTypeValue;
	}

	public void setHouseTypeValue(Integer houseTypeValue) {
		this.houseTypeValue = houseTypeValue;
	}

	public String getDesignStyleName() {
		return designStyleName;
	}

	public void setDesignStyleName(String designStyleName) {
		this.designStyleName = designStyleName;
	}

	public String getSpaceCommonName() {
		return spaceCommonName;
	}

	public void setSpaceCommonName(String spaceCommonName) {
		this.spaceCommonName = spaceCommonName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public Integer getThumbnailId() {
		return thumbnailId;
	}

	public void setThumbnailId(Integer thumbnailId) {
		this.thumbnailId = thumbnailId;
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
		DesignTemplet other = (DesignTemplet) that;
		return (this.getId() == null ? other.getId() == null
				: this.getId().equals(other.getId()))
				&& (this.getDesignCode() == null ? other.getDesignCode() == null
						: this.getDesignCode().equals(other.getDesignCode()))
				&& (this.getDesignName() == null ? other.getDesignName() == null
						: this.getDesignName().equals(other.getDesignName()))
				&& (this.getUserId() == null ? other.getUserId() == null
						: this.getUserId().equals(other.getUserId()))
				&& (this.getDesignSourceType() == null
						? other.getDesignSourceType() == null
						: this.getDesignSourceType().equals(other.getDesignSourceType()))
				&& (this.getDesignStyleId() == null ? other.getDesignStyleId() == null
						: this.getDesignStyleId().equals(other.getDesignStyleId()))
				&& (this.getSpaceCommonId() == null ? other.getSpaceCommonId() == null
						: this.getSpaceCommonId().equals(other.getSpaceCommonId()))
				&& (this.getConfigFileId() == null ? other.getConfigFileId() == null
						: this.getConfigFileId().equals(other.getConfigFileId()))
				&& (this.getModelId() == null ? other.getModelId() == null
						: this.getModelId().equals(other.getModelId()))
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
				&& (this.getRemark() == null ? other.getRemark() == null
						: this.getRemark().equals(other.getRemark()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getDesignCode() == null) ? 0 : getDesignCode().hashCode());
		result = prime * result
				+ ((getDesignName() == null) ? 0 : getDesignName().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getDesignSourceType() == null) ? 0
				: getDesignSourceType().hashCode());
		result = prime * result
				+ ((getDesignStyleId() == null) ? 0 : getDesignStyleId().hashCode());
		result = prime * result
				+ ((getSpaceCommonId() == null) ? 0 : getSpaceCommonId().hashCode());
		result = prime * result
				+ ((getConfigFileId() == null) ? 0 : getConfigFileId().hashCode());
		result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
		result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
		result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result
				+ ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
		result = prime * result
				+ ((getModifier() == null) ? 0 : getModifier().hashCode());
		result = prime * result
				+ ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
		result = prime * result
				+ ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
		;
		return result;
	}

}
