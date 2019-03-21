package com.nork.design.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class DesignPlanModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer designPlanId;
	private String planCode;
	private String planName;
	private Integer modelId;
	private Integer configFileId;
	private Integer spaceCommonId;
	private Integer designTemplateId;
	private String modelPath;
	private String filePath;
	private String designTempletCode;
	private String spaceCode;
	private String spaceName;
	private String spaceAreas;
	private Integer houseTypeValue;
	private String houseTypeName;
	private String residentialUnitsName;
	private String planSource;
	private String planVersion;

	private Integer daylightU3dModelId;
	private Integer dusklightU3dModelId;
	private Integer nightlightU3dModelId;
	/**
	 * 拼花信息
	 */
	private Integer spellingFlowerFileId;
	/**
	 * 拼花  产品ids 
	 */
	private String spellingFlowerProduct;
	
	/**
	 * 水刀配置信息
	 */
	private String waterjetInfo;

	/**
	 * auth xiaoxc_20180828
	 * 定制产品订单编码 	 customizedProductOrderCode
	 * 定制产品配置文件ID  customizedProductConfigId
	 * 合作伙伴名称 	 	partnersName
	 * 外部订单Id  		exteriorOrderId
	 */
	private String customizedProductOrderCode;
	private Integer customizedProductConfigId;
	private String partnersName;
	private Integer orderId;
	private String exteriorOrderId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getPartnersName() {
		return partnersName;
	}

	public void setPartnersName(String partnersName) {
		this.partnersName = partnersName;
	}

	public String getExteriorOrderId() {
		return exteriorOrderId;
	}

	public void setExteriorOrderId(String exteriorOrderId) {
		this.exteriorOrderId = exteriorOrderId;
	}

	public String getCustomizedProductOrderCode() {
		return customizedProductOrderCode;
	}

	public void setCustomizedProductOrderCode(String customizedProductOrderCode) {
		this.customizedProductOrderCode = customizedProductOrderCode;
	}

	public Integer getCustomizedProductConfigId() {
		return customizedProductConfigId;
	}

	public void setCustomizedProductConfigId(Integer customizedProductConfigId) {
		this.customizedProductConfigId = customizedProductConfigId;
	}
	
	public String getWaterjetInfo() {
		return waterjetInfo;
	}

	public void setWaterjetInfo(String waterjetInfo) {
		this.waterjetInfo = waterjetInfo;
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

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public Integer getDaylightU3dModelId() {
		return daylightU3dModelId;
	}

	public void setDaylightU3dModelId(Integer daylightU3dModelId) {
		this.daylightU3dModelId = daylightU3dModelId;
	}

	public Integer getDusklightU3dModelId() {
		return dusklightU3dModelId;
	}

	public void setDusklightU3dModelId(Integer dusklightU3dModelId) {
		this.dusklightU3dModelId = dusklightU3dModelId;
	}

	public Integer getNightlightU3dModelId() {
		return nightlightU3dModelId;
	}

	public void setNightlightU3dModelId(Integer nightlightU3dModelId) {
		this.nightlightU3dModelId = nightlightU3dModelId;
	}

	public String getPlanVersion() {
		return planVersion;
	}

	public void setPlanVersion(String planVersion) {
		this.planVersion = planVersion;
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

	public Integer getDesignTemplateId() {
		return designTemplateId;
	}

	public void setDesignTemplateId(Integer designTemplateId) {
		this.designTemplateId = designTemplateId;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDesignTempletCode() {
		return designTempletCode;
	}

	public void setDesignTempletCode(String designTempletCode) {
		this.designTempletCode = designTempletCode;
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

	public Integer getHouseTypeValue() {
		return houseTypeValue;
	}

	public void setHouseTypeValue(Integer houseTypeValue) {
		this.houseTypeValue = houseTypeValue;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
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

}


