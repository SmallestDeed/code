package com.nork.platform.service;


import com.nork.platform.model.BasePlatform;




/**   
 * @Title: BasePlatformService.java 
 * @Package com.nork.platform.service
 * @Description:基础-平台表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
public interface BasePlatformService {

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BasePlatform 
	 */
	public BasePlatform getByCode(String platformCode);

	
}
