package com.nork.ramCache.service;

import com.nork.product.model.BaseProductStyle;
import com.nork.system.model.BaseArea;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;

/**
 * 利用内存存少量并且几乎不会update的数据
 * 
 * @author huangsongbo
 *
 */
public interface RAMCacheService {

	/**
	 * select * from base_area where area_code = ?
	 * 
	 * @author huangsongbo
	 * @param provinceCode
	 * @return
	 */
	BaseArea getBaseAreaByAreaCode(String provinceCode);

	/**
	 * select * form base_product_style where id = ?
	 * 
	 * @author huangsongbo
	 * @param decorateStyle
	 * @return
	 */
	BaseProductStyle getBaseProductStyleById(Integer decorateStyle);

	/**
	 * select * from sys_dictionary where type = ? and value = ?
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	SysDictionary getSysDictionaryByTypeAndValue(String type, Integer value);

	/**
	 * 获取用户信息
	 * 母公司id
	 * 
	 * @param userId
	 * @return
	 */
	SysUser getSysUser(Long userId);

	/**
	 * 定时钟清楚所有用于缓存的map
	 * 30分钟清除一次
	 * 
	 * @author huangsongbo
	 */
	void clearStaticMap();

}
