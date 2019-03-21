package com.nork.onekeydesign.model.vo;


import java.io.Serializable;
import java.util.List;

public class UnityDesignPlanRecommended implements Serializable {
	private static final long serialVersionUID = -4145570758832226470L;

	/**推荐方案id*/
	private String id;
	/**草图方案id*/
	private String designPlanId;
	/**样板房id*/
	private String designTempletId;
	/**推荐方案类型*/
	private String recommendedType;
	/**推荐方案编码*/
	private String planCode;
	/**推荐方案名称*/
	private String planName;
	/**推荐方案模型id*/
	private String modelId;
	/**推荐方案模型路径*/
	private String modelPath;
	/**推荐方案配置文件id*/
	private String configFileId;
	/**推荐方案配置文件路径*/
	private String configFilePath;
	/**推荐方案拼花配置文件id*/
	private String spellingFlowerFileId;
	/**推荐方案拼花配置文件路径*/
	private String flowerFilePath;
	/**推荐方案拼花配置文件产品id*/
	private String spellingFlowerProduct;
	/**推荐方案空间对应白天模型路径*/
	private String daylightU3dModelId;
	/**推荐方案空间对应白天模型id（*/
	private String daylightModelPath;
	/**推荐方案空间对应黄昏模型id*/
	private String dusklightU3dModelId;
	/**推荐方案空间对应黄昏模型路径*/
	private String dusklightModelPath;
	/**推荐方案空间对应黑夜模型id*/
	private String nightlightU3dModelId;
	/**推荐方案空间对应黑夜模型路径*/
	private String nightlightModelPath;
	/**推荐方案空间对应水刀*/
	private String waterjetInfo;
	/**推荐方案空间对应产品集合*/
	private List<UnityPlanProductRecommended> unityPlanProductRecommendedList;
	/**推荐方案空间对应水刀集合*/
	private List<WaterjetInfoReturnDTO> waterjetInfoList;
	/**推荐方案空间对应拼花集合*/
	private List<UnitySpellingFlower> flowerList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecommendedType() {
		return recommendedType;
	}

	public void setRecommendedType(String recommendedType) {
		this.recommendedType = recommendedType;
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

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getConfigFileId() {
		return configFileId;
	}

	public void setConfigFileId(String configFileId) {
		this.configFileId = configFileId;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getSpellingFlowerFileId() {
		return spellingFlowerFileId;
	}

	public void setSpellingFlowerFileId(String spellingFlowerFileId) {
		this.spellingFlowerFileId = spellingFlowerFileId;
	}

	public String getFlowerFilePath() {
		return flowerFilePath;
	}

	public void setFlowerFilePath(String flowerFilePath) {
		this.flowerFilePath = flowerFilePath;
	}

	public String getSpellingFlowerProduct() {
		return spellingFlowerProduct;
	}

	public void setSpellingFlowerProduct(String spellingFlowerProduct) {
		this.spellingFlowerProduct = spellingFlowerProduct;
	}

	public String getDaylightU3dModelId() {
		return daylightU3dModelId;
	}

	public void setDaylightU3dModelId(String daylightU3dModelId) {
		this.daylightU3dModelId = daylightU3dModelId;
	}

	public String getDaylightModelPath() {
		return daylightModelPath;
	}

	public void setDaylightModelPath(String daylightModelPath) {
		this.daylightModelPath = daylightModelPath;
	}

	public String getDusklightU3dModelId() {
		return dusklightU3dModelId;
	}

	public void setDusklightU3dModelId(String dusklightU3dModelId) {
		this.dusklightU3dModelId = dusklightU3dModelId;
	}

	public String getDusklightModelPath() {
		return dusklightModelPath;
	}

	public void setDusklightModelPath(String dusklightModelPath) {
		this.dusklightModelPath = dusklightModelPath;
	}

	public String getNightlightU3dModelId() {
		return nightlightU3dModelId;
	}

	public void setNightlightU3dModelId(String nightlightU3dModelId) {
		this.nightlightU3dModelId = nightlightU3dModelId;
	}

	public String getNightlightModelPath() {
		return nightlightModelPath;
	}

	public void setNightlightModelPath(String nightlightModelPath) {
		this.nightlightModelPath = nightlightModelPath;
	}

	public List<UnityPlanProductRecommended> getUnityPlanProductRecommendedList() {
		return unityPlanProductRecommendedList;
	}

	public void setUnityPlanProductRecommendedList(List<UnityPlanProductRecommended> unityPlanProductRecommendedList) {
		this.unityPlanProductRecommendedList = unityPlanProductRecommendedList;
	}

	public String getWaterjetInfo() {
		return waterjetInfo;
	}

	public void setWaterjetInfo(String waterjetInfo) {
		this.waterjetInfo = waterjetInfo;
	}

	public List<WaterjetInfoReturnDTO> getWaterjetInfoList() {
		return waterjetInfoList;
	}

	public void setWaterjetInfoList(List<WaterjetInfoReturnDTO> waterjetInfoList) {
		this.waterjetInfoList = waterjetInfoList;
	}

	public List<UnitySpellingFlower> getFlowerList() {
		return flowerList;
	}

	public void setFlowerList(List<UnitySpellingFlower> flowerList) {
		this.flowerList = flowerList;
	}

	public String getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(String designPlanId) {
		this.designPlanId = designPlanId;
	}

	public String getDesignTempletId() {
		return designTempletId;
	}

	public void setDesignTempletId(String designTempletId) {
		this.designTempletId = designTempletId;
	}
}
