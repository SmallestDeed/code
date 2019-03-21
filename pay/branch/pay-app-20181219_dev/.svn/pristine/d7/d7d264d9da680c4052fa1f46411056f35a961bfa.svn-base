package com.sandu.render.model;

import com.sandu.product.model.ProductReplaceTaskDetail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AutoRenderTask implements Serializable {

	//渲染任务类型：自动渲染
	public static final int RENDER_TASK_TYPE_AUTORENDER = 0;
	//渲染任务类型：替换渲染
	public static final int RENDER_TASK_TYPE_MANRUALRENDER = 1;

	//渲染状态名:未渲染
	public static final String RENDER_TASK_NAME_NORENDER = "未渲染";
	//渲染状态名:渲染中
	public static final String RENDER_TASK_NAME_RENDERING = "渲染中";
	//渲染状态名:渲染成功
	public static final String RENDER_TASK_NAME_SUCCESS = "渲染成功";
	//渲染状态名:渲染失败
	public static final String RENDER_TASK_NAME_FAIL = "失败已退款";

	//渲染状态：未渲染
	public static final int NO_RENDER_TASK = 0;
	//渲染状态：渲染中
	public static final int RENDERING_TASK = 1;
	//渲染状态：渲染成功
	public static final int RENDER_TASK_SUCCESS = 2;
	//渲染状态：渲染失败
	public static final int RENDER_TASK_FAIL = 3;
	
	//能渲染
	public static final int RENDER_TASK = 1;
	//不能渲染
	public static final int RENDER_TASK_NOT = 0;
	
	//照片级渲染
	public static final String RENDER_TYPES_PHOTO = "1";
	//720P渲染
	public static final String RENDER_TYPES_720P = "2";
	//720P多点渲染
	public static final String RENDER_TYPES_MULTE_POINT_720P = "3";
	//视频渲染
	public static final String RENDER_TYPES_VIDEO = "4";
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
	
	private Integer renderState;

	//已渲染数据格式
	private AlearyRenderData alearyRenderData;
	
	private String spaceName;
	
	
	private String failReason;
	
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Integer getRenderState() {
		return renderState;
	}

	public void setRenderState(Integer renderState) {
		this.renderState = renderState;
	}

	/**
	 * The ID of design_plan
	 */
	private Integer designPlanId;// 方案ID
	
	
	/**
	 * 创建者
	 **/
	private String creator;
	/**
	 * 创建时间
	 **/
	private Date gmtCreate;
	/**
	 * 修改人
	 **/
	private String modifier;
	/**
	 * 修改时间
	 **/
	private Date gmtModified;
	// 空间编码
	private String spaceCode;

	private Integer operationUserId;// 操作用户ID
	
	private Integer spaceFunctionId;
	
	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	private String operationUserName;// 操作用户名称

	private String orderNumber;// 订单号

	private String renderTypesStr;// 1照片级,2、720,3、N720,4、video

	private Integer taskType; // 任务类型，默认0= 自动渲染；1=产品替换

	private Integer livingId; // 推荐方案信息

	//户型ID
	private Integer houseId;
	//户型名称
    private String houseName;

	private Integer operationSource;// 操作来源，0，我的设计，1,为推荐方案
	private Integer state; //0：任务失败，1，任务成功，
	private Integer spaceCommonId;
	private String houseCommonCode;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseCommonCode() {
		return houseCommonCode;
	}

	public void setHouseCommonCode(String houseCommonCode) {
		this.houseCommonCode = houseCommonCode;
	}

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private List<ProductReplaceTaskDetail> replaceProducts;

	// 判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
	private String isSuccess;
	// 方案名称,移动端我的替换列表需要显示
	private String planName;
	// 缩略图路径，移动端我的替换列表需要
	private String smallPicPath;
	//订单业务状态
	private String bizType;
	
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public AlearyRenderData getAlearyRenderData() {
		return alearyRenderData;
	}

	public void setAlearyRenderData(AlearyRenderData alearyRenderData) {
		this.alearyRenderData = alearyRenderData;
	}

	// 分页
	private Integer limit;
	private Integer start;
	// 移动端我的替换列表需要这个值作为参数
	private Integer taskId;
	/** 方案编码 **/
	private String designCode;
	/** 样板房编码 **/
	private String templateCode;
	/** 方案名称 **/
	private String designName;
	/** 任务来源 **/
	private Integer taskSource;
	/** 主机ip **/
	private String hostIp;
	// 关联design_plan_render_scene表的id
	private Integer businessId;
	// 前端需要用到这个字段进行判断
	private Integer renderingType;
	
	/*排序规则,0是降序,1是升序*/
	private Integer isSort;

	//已渲染数据------渲染完成后此数据不为空
	private List<String> renderDateList;
	
	private ReplaceRenderData replaceRenderData;
	
	
	public ReplaceRenderData getReplaceRenderData() {
		return replaceRenderData;
	}

	public void setReplaceRenderData(ReplaceRenderData replaceRenderData) {
		this.replaceRenderData = replaceRenderData;
	}

	public Integer getIsSort() {
		return isSort;
	}

	public void setIsSort(Integer isSort) {
		this.isSort = isSort;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getRenderingType() {
		return renderingType;
	}

	public void setRenderingType(Integer renderingType) {
		this.renderingType = renderingType;
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

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
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

	public List<String> getRenderDateList() {
		return renderDateList;
	}

	public void setRenderDateList(List<String> renderDateList) {
		this.renderDateList = renderDateList;
	}

    @Override
    public String toString() {
        return "AutoRenderTask{" +
                "id=" + id +
                ", planId=" + planId +
                ", templateId=" + templateId +
                ", priority=" + priority +
                ", renderPic=" + renderPic +
                ", render720=" + render720 +
                ", renderN720=" + renderN720 +
                ", renderVideo=" + renderVideo +
                ", isDeleted=" + isDeleted +
                ", maxSize=" + maxSize +
                ", renderState=" + renderState +
                ", alearyRenderData=" + alearyRenderData +
                ", spaceName='" + spaceName + '\'' +
                ", designPlanId=" + designPlanId +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", spaceCode='" + spaceCode + '\'' +
                ", operationUserId=" + operationUserId +
                ", spaceFunctionId=" + spaceFunctionId +
                ", operationUserName='" + operationUserName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", renderTypesStr='" + renderTypesStr + '\'' +
                ", taskType=" + taskType +
                ", livingId=" + livingId +
                ", houseId=" + houseId +
                ", houseName=" + houseName +
                ", operationSource=" + operationSource +
                ", state=" + state +
                ", spaceCommonId=" + spaceCommonId +
                ", houseCommonCode='" + houseCommonCode + '\'' +
                ", replaceProducts=" + replaceProducts +
                ", isSuccess='" + isSuccess + '\'' +
                ", planName='" + planName + '\'' +
                ", smallPicPath='" + smallPicPath + '\'' +
                ", bizType='" + bizType + '\'' +
                ", limit=" + limit +
                ", start=" + start +
                ", taskId=" + taskId +
                ", designCode='" + designCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", designName='" + designName + '\'' +
                ", taskSource=" + taskSource +
                ", hostIp='" + hostIp + '\'' +
                ", businessId=" + businessId +
                ", renderingType=" + renderingType +
                ", isSort=" + isSort +
                ", renderDateList=" + renderDateList +
                ", replaceRenderData=" + replaceRenderData +
                '}';
    }
}
