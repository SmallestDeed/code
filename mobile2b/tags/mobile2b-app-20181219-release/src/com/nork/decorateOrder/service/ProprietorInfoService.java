package com.nork.decorateOrder.service;

import com.nork.decorateOrder.model.ProprietorInfo;

public interface ProprietorInfoService {

	/**
	 * 查询更多的属性
	 * eg: 装修风格名称, 装修类型名称, 省市信息...
	 * 
	 * @param proprietorInfo
	 * @return
	 */
	ProprietorInfo setMoreInfo(ProprietorInfo proprietorInfo);

}
