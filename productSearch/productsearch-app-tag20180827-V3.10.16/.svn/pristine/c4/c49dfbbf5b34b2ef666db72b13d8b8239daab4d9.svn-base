package com.nork.design.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.nork.common.model.Mapper;
import com.nork.mobile.model.ProductReplaceTaskDetail;

public class AutoRenderTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer planId;// 推荐方案ID

	private Integer templateId;

	private Integer priority;

	private Integer renderPic;

	private Integer render720;

	private Integer renderN720;

	private Integer renderVideo;

	private Integer isDeleted;

	private Integer maxSize;

	/**
	 * The ID of design_plan
	 */
	private Integer designPlanId;// 方案ID

	/** 创建者 **/
	private String creator;
	/** 创建时间 **/
	private Date gmtCreate;
	/** 修改人 **/
	private String modifier;
	/** 修改时间 **/
	private Date gmtModified;
	// 空间编码
	private String spaceCode;

	private Integer operationUserId;// 操作用户ID

	private String operationUserName;// 操作用户名称

	private String orderNumber;// 订单号

	private String renderTypesStr;// 1照片级,2、720,3、N720,4、video

	private Integer taskType; // 任务类型，默认0= 自动渲染；1=产品替换

	private Integer livingId; // 推荐方案信息

	private Integer houseId;// 推荐方案信息
	private Integer operationSource;// 操作来源，0，我的设计，1,为推荐方案

	private List<ProductReplaceTaskDetail> replaceProducts;

	// 判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
	private Integer isSuccess;
	// 方案名称,移动端我的替换列表需要显示
	private String planName;
	// 缩略图路径，移动端我的替换列表需要
	private String smallPicPath;

	
	/**  方案编码  **/
	private String designCode;
	/**  样板房编码  **/
	private String templateCode;
	/**  方案名称  **/
	private String designName;
	/**  任务来源  **/
	private Integer taskSource;
	/**  主机ip  **/
	private String hostIp;
	// 关联design_plan_render_scene表的id
		private Integer businessId;
		//前端需要用到这个字段进行判断
		private Integer renderingType;
		//失败类型
	private Integer failType;
	//失败原因
	private String failReason;
	//任务状态
	private Integer state;
	
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
	public Integer getRenderingType() {
			return renderingType;
		}

		public void setRenderingType(Integer renderingType) {
			this.renderingType = renderingType;
		}

	public Integer getBusinessId() {
			return businessId;
		}

		public void setBusinessId(Integer businessId) {
			this.businessId = businessId;
		}

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

	//分页
	private Integer limit;
	private Integer start;
	//移动端我的替换列表需要这个值作为参数
	private Integer taskId;
	
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getSmallPicPath() {
		return smallPicPath;
	}

	public void setSmallPicPath(String smallPicPath) {
		this.smallPicPath = smallPicPath;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getOperationSource() {
		return operationSource;
	}

	public void setOperationSource(Integer operationSource) {
		this.operationSource = operationSource;
	}

	public String getOperationUserName() {
		return operationUserName;
	}

	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}

	public Integer getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(Integer operationUserId) {
		this.operationUserId = operationUserId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getRenderTypesStr() {
		return renderTypesStr;
	}

	public void setRenderTypesStr(String renderTypesStr) {
		this.renderTypesStr = renderTypesStr;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public List<ProductReplaceTaskDetail> getReplaceProducts() {
		return replaceProducts;
	}

	public void setReplaceProducts(List<ProductReplaceTaskDetail> replaceProducts) {
		this.replaceProducts = replaceProducts;
	}

	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getRenderPic() {
		return renderPic;
	}

	public void setRenderPic(Integer renderPic) {
		this.renderPic = renderPic;
	}

	public Integer getRender720() {
		return render720;
	}

	public void setRender720(Integer render720) {
		this.render720 = render720;
	}

	public Integer getRenderN720() {
		return renderN720;
	}

	public void setRenderN720(Integer renderN720) {
		this.renderN720 = renderN720;
	}

	public Integer getRenderVideo() {
		return renderVideo;
	}

	public void setRenderVideo(Integer renderVideo) {
		this.renderVideo = renderVideo;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getLivingId() {
		return livingId;
	}

	public void setLivingId(Integer livingId) {
		this.livingId = livingId;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

}
