package com.nork.home.service;

/**
 * @Title: SpaceCommonService.java 
 * @Package com.nork.home.service
 * @Description:户型房型-通用空间表Service
 * @version V1.0   
 */
public interface SpaceCommonService {

	/**
	 * 获得该空间的房型类别
	 */
	Integer getSpaceFunctionIdBySpaceCommonId(Integer id);

}
