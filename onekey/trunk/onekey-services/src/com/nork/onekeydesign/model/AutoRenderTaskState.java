package com.nork.onekeydesign.model;

import java.io.Serializable;

public class AutoRenderTaskState extends AutoRenderTask implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer state;
	
	private Integer businessId;
	
	private Integer failType;
	
	private String failReason;
	
	private String hostName;

	private Integer isDeleted;
	
	private String remark;
	
	private Integer designPlanId;
	
	private Integer operationUserId;//操作用户ID
	
	private Integer taskType; //任务类型，默认0= 自动渲染；1=产品替换
	
	private String renderTypesStr;//1照片级,2、720,3、N720,4、video
	//分页
	private Integer limit;
	private Integer start;
	//方案名称,移动端我的替换列表需要显示
	private String planName;
	//缩略图路径，移动端我的替换列表需要
	private String smallPicPath;
	
	private Integer taskId;
	
	private String orderNumber;//订单号
	//判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
	private Integer isSuccess;
	/**  方案编码  **/
	private String designCode;
	/**  样板房编码  **/
	private String templateCode;
	/**  新方案编码  **/
	private String newDesignCode;
	/**  方案名称  **/
	private String designName;
	/**  任务来源  **/
	private Integer taskSource;
	/**  主机ip  **/
	private String hostIp;
	/**  渲染耗时  **/
	private String renderTimeConsuming;
	
	
	public String getDesignCode() {
		return designCode;
	}

	public void setDesignCode(String designCode) {
		this.designCode = designCode;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getNewDesignCode() {
		return newDesignCode;
	}

	public void setNewDesignCode(String newDesignCode) {
		this.newDesignCode = newDesignCode;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public Integer getTaskSource() {
		return taskSource;
	}

	public void setTaskSource(Integer taskSource) {
		this.taskSource = taskSource;
	}


	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getRenderTimeConsuming() {
		return renderTimeConsuming;
	}

	public void setRenderTimeConsuming(String renderTimeConsuming) {
		this.renderTimeConsuming = renderTimeConsuming;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getSmallPicPath() {
		return smallPicPath;
	}

	public void setSmallPicPath(String smallPicPath) {
		this.smallPicPath = smallPicPath;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(Integer operationUserId) {
		this.operationUserId = operationUserId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getRenderTypesStr() {
		return renderTypesStr;
	}

	public void setRenderTypesStr(String renderTypesStr) {
		this.renderTypesStr = renderTypesStr;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}



	public Integer getFailType() {
		return failType;
	}

	public void setFailType(Integer failType) {
		this.failType = failType;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Override
	public String toString() {
		return "AutoRenderTaskState [state=" + state + ", businessId=" + businessId + ", failType=" + failType
				+ ", failReason=" + failReason + ", hostName=" + hostName + ", isDeleted=" + isDeleted + ", remark="
				+ remark + ", designPlanId=" + designPlanId + ", operationUserId=" + operationUserId + ", taskType="
				+ taskType + ", renderTypesStr=" + renderTypesStr + ", limit=" + limit + ", start=" + start
				+ ", planName=" + planName + ", smallPicPath=" + smallPicPath + ", taskId=" + taskId + ", orderNumber="
				+ orderNumber + ", isSuccess=" + isSuccess + ", designCode=" + designCode + ", templateCode="
				+ templateCode + ", newDesignCode=" + newDesignCode + ", designName=" + designName + ", taskSource="
				+ taskSource + ", hostIp=" + hostIp + ", renderTimeConsuming=" + renderTimeConsuming + "]";
	}
	
	

}
