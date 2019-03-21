package com.sandu.render.model;

import com.sandu.product.model.ProductReplaceTaskDetail;
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
	
	private Integer renderState;

	//已渲染数据格式
	private AlearyRenderData alearyRenderData;
	
	private String spaceName;

	private String failReason;

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

	private List<ProductReplaceTaskDetail> replaceProducts;
	// 判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
	private String isSuccess;
	// 方案名称,移动端我的替换列表需要显示
	private String planName;
	// 缩略图路径，移动端我的替换列表需要
	private String smallPicPath;
	//订单业务状态
	private String bizType;
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
	//平台编码
	private String platformCode;
	//平台id
	private Integer platformId;
	//平台业务类型
	private String platformBussinessType;
}
