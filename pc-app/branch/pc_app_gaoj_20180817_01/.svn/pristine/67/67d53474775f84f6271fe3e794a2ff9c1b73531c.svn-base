package com.nork.design.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**
 * @Title: DesignTemplet.java
 * @Package com.nork.design.model
 * @Description:设计模块-设计方案样板房表
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 * @version V1.0
 */
public class DesignTemplet extends Mapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 方案编码 **/
	private String designCode;
	/** 方案名称 **/
	private String designName;
	/** 用户id **/
	private Integer userId;
	/** 方案来源类型 **/
	private String designSourceType;
	/** 设计风格id **/
	private Integer designStyleId;
	/** 通用空间id **/
	private Integer spaceCommonId;

	private String spaceCode;
	/** 样板图片id(空间设计图) **/
	private Integer picId;
	// 空间布局图缩略图id
	private Integer picSmallId;
	// 空间设计图
	private String designPicPath;
	/** 配置文件路径id **/
	private Integer configFileId;
	private Integer eveningFileId;
	private Integer dawnFileId;
	private Integer nightFileId;

	private Integer putawayState;

	/** 模型文件id **/
	private Integer modelId;
	/** 3d模型id **/
	private Integer model3dId;
	/** u3d模型id **/
	private Integer webModelU3dId;
	private Integer iosModelU3dId;
	private Integer androidModelU3dId;
	private Integer pcModelU3dId;
	private Integer ipadModelU3dId;
	private Integer macBookpcModelU3dId;
	private Integer newPcModelU3dId;
	/** 渲染图片ids **/
	private String renderPicIds;
	/** 是否为推荐空间布局 **/
	private Integer isRecommend;
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
	/** cad图id **/
	private Integer cadPicId;

	/** fbx文件ID **/
	private Integer fbxFileId;
	/** fbx贴图多个 **/
	private String fbxTexture;
	private Integer fbxState;

	public Integer getCadPicId() {
		return cadPicId;
	}

	public void setCadPicId(Integer cadPicId) {
		this.cadPicId = cadPicId;
	}

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

	/** 效果图列表 **/
	private String effectPic;
	// 效果图缩略图id
	private Integer effecPicSmallId;

	/** 效果图列表路径 **/
	private String[] effectPicListPath;

	/** 效果图列表缩略图，取列表第一个 **/
	private String effectSmallPicPath;
	/* 平面效果图ids */
	private String effectPlanIds;
	/* 平面效果缩略图id */
	private Integer effectPlanSmallId;
	/* 平面效果图url */
	private String effectPlanUrl;
	/* 平面效果图对应的缩略图url */
	private String effectPlanSmallUrl;
	/** 场景缩略图 **/
	private Integer thumbnailId;
	/* 产品配置文件 */
	private Integer configId;

	
	/**空间的模型id*/
	private String spaceCommonCode;
	private String iosU3dModelId;
	private String modelU3dId;
	private String macBookPcU3dModelId;
	private String windowsPcU3dModelId;
	private String ipadU3dModelId;
	private String androidU3dModelId;

	/**
	 * 天花布局标识
	 */
	private String smallpoxIdentify;
	
	/**
	 * 地面布局标识
	 */
	private String groundIdentify;

	/**
	 * 空间布局类型
	 * A、样板房中只有浴缸（嵌入式浴缸、独立式浴缸）
	 * B、样板房中只有淋浴房（一字型淋浴房、淋浴房）
	 * AB、样板房中既有浴缸也有淋浴房
	 * N、样板房既没有浴缸也没有淋浴房
	 */
	private  String spaceLayoutType;

	/**
	 * 去掉玄关/过道的空间面积
	 */
	private String mainArea;
	
	public String getMainArea() {
		return mainArea;
	}

	public void setMainArea(String mainArea) {
		this.mainArea = mainArea;
	}

	public String getSpaceLayoutType() {
		return spaceLayoutType;
	}

	public void setSpaceLayoutType(String spaceLayoutType) {
		this.spaceLayoutType = spaceLayoutType;
	}

	public String getSmallpoxIdentify() {
		return smallpoxIdentify;
	}

	public void setSmallpoxIdentify(String smallpoxIdentify) {
		this.smallpoxIdentify = smallpoxIdentify;
	}

	public String getGroundIdentify() {
		return groundIdentify;
	}

	public void setGroundIdentify(String groundIdentify) {
		this.groundIdentify = groundIdentify;
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

	public String getModelU3dId() {
		return modelU3dId;
	}

	public void setModelU3dId(String modelU3dId) {
		this.modelU3dId = modelU3dId;
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

	public Integer getFbxState() {
		return fbxState;
	}

	public void setFbxState(Integer fbxState) {
		this.fbxState = fbxState;
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

	public String getEffectPlanIds() {
		return effectPlanIds;
	}

	public void setEffectPlanIds(String effectPlanIds) {
		this.effectPlanIds = effectPlanIds;
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

	public Integer getPutawayState() {
		return putawayState;
	}

	public void setPutawayState(Integer putawayState) {
		this.putawayState = putawayState;
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

	public String getDesignCode() {
		return designCode;
	}

	public void setDesignCode(String designCode) {
		this.designCode = designCode;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDesignSourceType() {
		return designSourceType;
	}

	public void setDesignSourceType(String designSourceType) {
		this.designSourceType = designSourceType;
	}

	public Integer getDesignStyleId() {
		return designStyleId;
	}

	public void setDesignStyleId(Integer designStyleId) {
		this.designStyleId = designStyleId;
	}

	public Integer getConfigFileId() {
		return configFileId;
	}

	public void setConfigFileId(Integer configFileId) {
		this.configFileId = configFileId;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
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

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
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

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
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

	public Integer getModel3dId() {
		return model3dId;
	}

	public void setModel3dId(Integer model3dId) {
		this.model3dId = model3dId;
	}

	public String getRenderPicIds() {
		return renderPicIds;
	}

	public void setRenderPicIds(String renderPicIds) {
		this.renderPicIds = renderPicIds;
	}

	public String getEffectPic() {
		return effectPic;
	}

	public void setEffectPic(String effectPic) {
		this.effectPic = effectPic;
	}

	public Integer getThumbnailId() {
		return thumbnailId;
	}

	public void setThumbnailId(Integer thumbnailId) {
		this.thumbnailId = thumbnailId;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getFbxFileId() {
		return fbxFileId;
	}

	public void setFbxFileId(Integer fbxFileId) {
		this.fbxFileId = fbxFileId;
	}

	public String getFbxTexture() {
		return fbxTexture;
	}

	public void setFbxTexture(String fbxTexture) {
		this.fbxTexture = fbxTexture;
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

	/** 获取对象的copy **/
	public DesignTemplet copy() {
		DesignTemplet obj = new DesignTemplet();
		obj.setDesignCode(this.designCode);
		obj.setDesignName(this.designName);
		obj.setUserId(this.userId);
		obj.setDesignSourceType(this.designSourceType);
		obj.setDesignStyleId(this.designStyleId);
		obj.setSpaceCommonId(this.spaceCommonId);
		obj.setConfigFileId(this.configFileId);
		obj.setModelId(this.modelId);
		obj.setSysCode(this.sysCode);
		obj.setCreator(this.creator);
		obj.setGmtCreate(this.gmtCreate);
		obj.setModifier(this.modifier);
		obj.setGmtModified(this.gmtModified);
		obj.setIsDeleted(this.isDeleted);
		obj.setRemark(this.remark);

		return obj;
	}

	/** 获取对象的map **/
	public Map toMap() {
		Map map = new HashMap();
		map.put("designCode", this.designCode);
		map.put("designName", this.designName);
		map.put("userId", this.userId);
		map.put("designSourceType", this.designSourceType);
		map.put("designId", this.designStyleId);
		map.put("spaceCommonId", this.spaceCommonId);
		map.put("configFileId", this.configFileId);
		map.put("modelId", this.modelId);
		map.put("sysCode", this.sysCode);
		map.put("creator", this.creator);
		map.put("gmtCreate", this.gmtCreate);
		map.put("modifier", this.modifier);
		map.put("gmtModified", this.gmtModified);
		map.put("isDeleted", this.isDeleted);
		map.put("remark", this.remark);

		return map;
	}

}
