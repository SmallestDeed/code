package com.sandu.home.model.search;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceCommonSearchVo implements Serializable{
	/**
	 * 空间搜索条件模型
	 */
	private static final long serialVersionUID = 1L;
	
	/*空间ID*/
	private Integer spaceCommonId;
	
	/*户型ID*/
	private Integer houseId;
	
	/*分页参数,每页多少条*/
	private String limit;
	
	/*分页参数第几页*/
	private String start;
	
	/*空间类型ID*/
	private String spaceFunctionId;

	/*空间面积*/
	private String areaValue;
	
	/*空间形状*/
	private String spaceShape;
	
	/*推荐方案id，方案组合功能添加*/
	private Integer designPlanRecommendId;
	/**主方案id 0没有打组，和designPlanRecommendId相同为主方案，不同则为主方案id**/
	private Integer groupPrimaryId;
}
