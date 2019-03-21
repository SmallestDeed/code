package com.nork.platform.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.platform.dao.BasePlatformMapper;
import com.nork.platform.model.BasePlatform;
import com.nork.platform.service.BasePlatformService;



/**   
 * @Title: BasePlatformServiceImpl.java 
 * @Package com.nork.platform.service.impl
 * @Description:基础-平台表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

	private BasePlatformMapper basePlatformMapper;

	@Autowired
	public void setBasePlatformMapper(
			BasePlatformMapper basePlatformMapper) {
		this.basePlatformMapper = basePlatformMapper;
	}

	

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BasePlatform 
	 */
	@Override
	public BasePlatform getByCode(String platformCode) {
		return basePlatformMapper.selectByCode(platformCode);
	}

	
}
