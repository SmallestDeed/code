package com.sandu.api.task.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AutoRenderTaskState extends AutoRenderTask implements Serializable {

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
	/**任务是否有效(0:有效;1:无效)*/
	private Integer isValid;
	/**	渲染机ip */
	private String renderMachineIp;
	/**
	 * 渲染等级（1-7）
	 */
	private Integer renderLevel;
	/**
	 * 渲染程序版本
	 */
	private String renderProgramVersion;
	/**  平台id  */
	private Integer platformId;


}
