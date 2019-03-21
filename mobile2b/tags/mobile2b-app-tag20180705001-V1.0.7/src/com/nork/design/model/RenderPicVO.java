package com.nork.design.model;

import java.io.Serializable;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.LoginUser;

public class RenderPicVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DesignPlan designPlan;
	private Map<String, MultipartFile> fileMap;
	private Integer viewPoint;
	private Integer scene;
	private Integer isTurnOn;
	private Integer renderingType;
	private String level;
	private Integer taskId;
	private LoginUser loginUser;
	/** 720全景图清晰度等级 **/
	private Integer panoLevel;
	/** 多720渲染图之间的坐标关系 **/
	private String roamJson;
	private Integer opType; //0：自动渲染:1：手动渲染
	
	private Integer templateId;
	
	private String jobType;
	
	private Integer sourcePlanId;
	//判断是否是任务：0，否； 1；是
	private Integer isAuto;

	private Integer platformId; //平台ID
	
	
	public Integer getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}
	/**
	 * 识别该方案渲染的时候,有没有改变(移动/新增/替换...)
	 */
	private Integer isChange;
	
	private Integer userRenderType; //1为替换渲染更新任务状态， 0或不传为DIY渲染更新任务状态
	
	
	
	public Integer getUserRenderType() {
		return userRenderType;
	}
	public void setUserRenderType(Integer userRenderType) {
		this.userRenderType = userRenderType;
	}
	public Integer getIsChange() {
		return isChange;
	}
	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}
	public Integer getSourcePlanId() {
		return sourcePlanId;
	}
	public void setSourcePlanId(Integer sourcePlanId) {
		this.sourcePlanId = sourcePlanId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public Integer getPanoLevel() {
		return panoLevel;
	}
	public void setPanoLevel(Integer panoLevel) {
		this.panoLevel = panoLevel;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public DesignPlan getDesignPlan() {
		return designPlan;
	}
	public void setDesignPlan(DesignPlan designPlan) {
		this.designPlan = designPlan;
	}
	public Map<String, MultipartFile> getFileMap() {
		return fileMap;
	}
	public void setFileMap(Map<String, MultipartFile> fileMap) {
		this.fileMap = fileMap;
	}
	public Integer getViewPoint() {
		return viewPoint;
	}
	public void setViewPoint(Integer viewPoint) {
		this.viewPoint = viewPoint;
	}
	public Integer getScene() {
		return scene;
	}
	public void setScene(Integer scene) {
		this.scene = scene;
	}
	public Integer getIsTurnOn() {
		return isTurnOn;
	}
	public void setIsTurnOn(Integer isTurnOn) {
		this.isTurnOn = isTurnOn;
	}

	
	public Integer getRenderingType() {
		return renderingType;
	}
	public void setRenderingType(Integer renderingType) {
		this.renderingType = renderingType;
	}
	public LoginUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRoamJson() {
		return roamJson;
	}
	public void setRoamJson(String roamJson) {
		this.roamJson = roamJson;
	}
	public Integer getOpType() {
		return opType;
	}
	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	@Override
	public String toString() {
		return "RenderPicVO{" +
				"designPlan=" + designPlan +
				", fileMap=" + fileMap +
				", viewPoint=" + viewPoint +
				", scene=" + scene +
				", isTurnOn=" + isTurnOn +
				", renderingType=" + renderingType +
				", level='" + level + '\'' +
				", taskId=" + taskId +
				", loginUser=" + loginUser +
				", panoLevel=" + panoLevel +
				", roamJson='" + roamJson + '\'' +
				", opType=" + opType +
				", templateId=" + templateId +
				", jobType='" + jobType + '\'' +
				", sourcePlanId=" + sourcePlanId +
				", isAuto=" + isAuto +
				", platformId=" + platformId +
				", isChange=" + isChange +
				", userRenderType=" + userRenderType +
				'}';
	}
}
