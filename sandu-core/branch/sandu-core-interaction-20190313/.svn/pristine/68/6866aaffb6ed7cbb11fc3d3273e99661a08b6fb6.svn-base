package com.sandu.api.task.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
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

	private Integer livingId; // 小区id


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
	/**  任务来源 (1 移动b端 0 小程序c端) **/
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
	private Integer isSort;//0降序,1升序
	//平台ID
	private Integer platformId;
	
	private String platformCode;
	//渲染任务状态list  add by yangzhun
	private List<Integer> stateList;
	
	private String platformBussinessType;
	/** 出现重叠时的解决方案 **/
	private Integer overlapResolve;
	/**全屋方案主方案id**/
	private Integer fullHousePlanId;
	/**方案空间类型（1：单空间方案，2：全屋方案）**/
	private Integer planHouseType;
	/**全屋方案主任务id**/
	private Integer mainTaskId;
	/**户型id**/
	private Integer houseId;
	/**全屋方案子任务数量**/
	private Integer subtaskCount;
	/** 样板房对应的空间类型	**/
	private Integer spaceFunctionId;
	/** 新的全屋方案id **/
	private Integer newFullHousePlanId;
	/**装进我家后新的全屋方案的uuid**/
	private String fullHousePlanUUID;
	/**方案打组主方案id**/
	private Integer groupPrimaryId;
	/**原效果图方案id，全屋方案替换某个效果图方案**/
	private Integer preRenderSceneId;
	/**全屋方案操作(1:创建全屋方案,2:修改全屋方案,3:修改全屋方案生成新的全屋方案)**/
	private Integer fullHousePlanAction;
	/*空间ID*/
	private Integer spaceCommonId;
	/**供求信息id**/
	private Integer supplyDemandId;
	//分页
	private Integer limit;
	private Integer start;
	//移动端我的替换列表需要这个值作为参数
	private Integer taskId;
	

}
