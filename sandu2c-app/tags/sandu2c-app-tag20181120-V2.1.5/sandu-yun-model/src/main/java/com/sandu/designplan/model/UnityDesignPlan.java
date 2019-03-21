package com.sandu.designplan.model;

import java.io.Serializable;
import java.util.List;

public class UnityDesignPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer newFlag;
	
	private Integer designPlanId;

	private String designPlanConfigPath;
	
	private String serviceUrl;

	private String resourcesUrl;

//	private String uploadDir;
	
	private String designPlanUIPath;
	
	private String u3dModelPath;
	
	private String designMode;
	
	private String emptyModePicPath;
	
	private String templetModePicPath;
	
//	private String eveningFilePath;
//
//	private String dawnFilePath;
//
//	private String nightFilePath;

	private UnitySpaceCommon unitySpaceCommon;
	
	private List<UnityPlanProduct> datalist;
	
	//1是定制产品，0不是
	private Integer isCustomized;
	//设计方案模型版本状态5.1 或 5.6
	private String modelVersionState;
	
	/*特效配置*/
	private String effectsConfig;
	
	

	public String getEffectsConfig() {
		return effectsConfig;
	}

	public void setEffectsConfig(String effectsConfig) {
		this.effectsConfig = effectsConfig;
	}

	public String getModelVersionState() {
		return modelVersionState;
	}

	public void setModelVersionState(String modelVersionState) {
		this.modelVersionState = modelVersionState;
	}

	//	private String webModelU3dPath;
//
//	private String iosModelU3dPath;
//
//	private String androidModelU3dPath;
//
//	private String pcModelU3dPath;
//
//	private String ipadModelU3dPath;
//
//	private String macBookpcModelU3dPath;

	/**小区户型名称拼接显示*/
	private String residentialUnitsValue;

	public String getResidentialUnitsValue() {
		return residentialUnitsValue;
	}

	public void setResidentialUnitsValue(String residentialUnitsValue) {
		this.residentialUnitsValue = residentialUnitsValue;
	}

	public Integer getIsCustomized() {
		return isCustomized;
	}

	public void setIsCustomized(Integer isCustomized) {
		this.isCustomized = isCustomized;
	}

	public String getEmptyModePicPath() {
		return emptyModePicPath;
	}

	public String getDesignMode() {
		return designMode;
	}


	public void setDesignMode(String designMode) {
		this.designMode = designMode;
	}


	public void setEmptyModePicPath(String emptyModePicPath) {
		this.emptyModePicPath = emptyModePicPath;
	}

	public String getTempletModePicPath() {
		return templetModePicPath;
	}

	public void setTempletModePicPath(String templetModePicPath) {
		this.templetModePicPath = templetModePicPath;
	}
	public Integer getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(Integer newFlag) {
		this.newFlag = newFlag;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	public String getDesignPlanConfigPath() {
		return designPlanConfigPath;
	}

	public void setDesignPlanConfigPath(String designPlanConfigPath) {
		this.designPlanConfigPath = designPlanConfigPath;
	}

	public UnitySpaceCommon getUnitySpaceCommon() {
		return unitySpaceCommon;
	}

	public void setUnitySpaceCommon(UnitySpaceCommon unitySpaceCommon) {
		this.unitySpaceCommon = unitySpaceCommon;
	}

	public String getU3dModelPath() {
		return u3dModelPath;
	}

	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
	}

	public List<UnityPlanProduct> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<UnityPlanProduct> datalist) {
		this.datalist = datalist;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getResourcesUrl() {
		return resourcesUrl;
	}

	public void setResourcesUrl(String resourcesUrl) {
		this.resourcesUrl = resourcesUrl;
	}

	public String getDesignPlanUIPath() {
		return designPlanUIPath;
	}

	public void setDesignPlanUIPath(String designPlanUIPath) {
		this.designPlanUIPath = designPlanUIPath;
	}

//	public String getUploadDir() {
//		return uploadDir;
//	}
//
//	public void setUploadDir(String uploadDir) {
//		this.uploadDir = uploadDir;
//	}

//	public String getIosModelU3dPath() {
//		return iosModelU3dPath;
//	}
//
//	public void setIosModelU3dPath(String iosModelU3dPath) {
//		this.iosModelU3dPath = iosModelU3dPath;
//	}
//
//	public String getAndroidModelU3dPath() {
//		return androidModelU3dPath;
//	}
//
//	public void setAndroidModelU3dPath(String androidModelU3dPath) {
//		this.androidModelU3dPath = androidModelU3dPath;
//	}
//
//	public String getPcModelU3dPath() {
//		return pcModelU3dPath;
//	}
//
//	public void setPcModelU3dPath(String pcModelU3dPath) {
//		this.pcModelU3dPath = pcModelU3dPath;
//	}
//
//	public String getIpadModelU3dPath() {
//		return ipadModelU3dPath;
//	}
//
//	public void setIpadModelU3dPath(String ipadModelU3dPath) {
//		this.ipadModelU3dPath = ipadModelU3dPath;
//	}
//
//	public String getMacBookpcModelU3dPath() {
//		return macBookpcModelU3dPath;
//	}
//
//	public void setMacBookpcModelU3dPath(String macBookpcModelU3dPath) {
//		this.macBookpcModelU3dPath = macBookpcModelU3dPath;
//	}
//	public String getWebModelU3dPath() {
//		return webModelU3dPath;
//	}
//	public void setWebModelU3dPath(String webModelU3dPath) {
//		this.webModelU3dPath = webModelU3dPath;
//	}

/*	public String getNightFilePath() {
		return nightFilePath;
	}

	public void setNightFilePath(String nightFilePath) {
		this.nightFilePath = nightFilePath;
	}

	public String getEveningFilePath() {
		return eveningFilePath;
	}

	public void setEveningFilePath(String eveningFilePath) {
		this.eveningFilePath = eveningFilePath;
	}

	public String getDawnFilePath() {
		return dawnFilePath;
	}

	public void setDawnFilePath(String dawnFilePath) {
		this.dawnFilePath = dawnFilePath;
	}*/
	
	private String planName;
	
	private String planCode;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	
	
}
