package com.sandu.rendermachine.model.render;


import com.sandu.rendermachine.model.product.ProductReplaceTaskDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AutoRenderTask implements Serializable {

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
	/**任务是否有效(0:有效;1:无效)*/
	private Integer isValid;
	//平台ID
	private Integer platformId;
	//分页
	private Integer limit;
	private Integer start;
	//移动端我的替换列表需要这个值作为参数
	private Integer taskId;

}
