package com.sandu.home.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GuideInfoDTO implements Serializable {

	private static final long serialVersionUID = 6484120640424262536L;

	/**
	 * 样板房id
	 */
	private Integer designTempletId;
	
	/**
	 * x坐标
	 */
	private String xCoordinate;
	
	/**
	 * y坐标
	 */
	private String yCoordinate;
	
	/**
	 * 空间类型value值(关联数据字典type = houseType)
	 */
	private Integer spaceType;
	
	/**
	 * 空间面积value值(关联数据字典)
	 */
	private Integer spaceArea;
	
	/**
	 * 渲染状态:
	 * 0.未渲染
	 * 1.渲染完成
	 * 2.渲染中
	 */
	private Integer renderStatus = 0;
	
	/**
	 * 表base_house_guide_pic_info的id
	 */
	private Integer baseHouseGuidePicInfoId;
	
	/**
	 * 该样板房匹配到的推荐方案id, 适用于匹配精选方案
	 */
	private Integer recommendedId;
	
}
