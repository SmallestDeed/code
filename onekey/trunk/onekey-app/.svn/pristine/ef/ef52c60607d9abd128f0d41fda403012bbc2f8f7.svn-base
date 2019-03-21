package com.nork.common.cache.utils;

import java.util.Map;

import com.nork.common.metadata.ModuleType;
import com.nork.common.util.ConfigUtil;

/***
 * 缓存KEY生成器
 * 
 * @author qiu.jun
 * @date 2016-05-11
 */
public class KeyGenerator {

	/***
	 * 根据Map 查询条件生成的key
	 * 
	 * @param module
	 * @param limit
	 * @param start
	 * @return
	 */
	public static String getKeyWithMap(ModuleType module, Map map) {
		StringBuffer keyBuffer = new StringBuffer();
		for (Object key : map.keySet()) {
			if (map.get(key) != null && map.get(key).toString().length() > 0) {
				keyBuffer.append(key.toString() + "=" + map.get(key) + "_");
			}
		}
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "_" + keyBuffer;
		return key;
	}

}
