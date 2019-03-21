package com.sandu.rendermachine.common.cache;

import com.sandu.rendermachine.common.util.ConfigUtil;
import com.sandu.rendermachine.model.metadata.ModuleType;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * 缓存KEY生成器
 * 
 * @author qiu.jun
 * @date 2016-05-11
 */
public class KeyGenerator {

	/***
	 * 生成单个缓存对象的KEY
	 * 
	 * @param module
	 * @return
	 */
	public static String getIdKey(ModuleType module, int id) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "get_" + id;
		return key;
	}

	/***
	 * 生成获取所有列表对象记录数的KEY
	 * 
	 * @param module
	 * @return
	 */
	public static String getAllCountKey(ModuleType module) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "all_count";
		return key;
	}

	/***
	 * 根据Map 查询条件生成的key
	 * 
	 * @param module
	 * @param map
	 * @return
	 */
	public static String getAllCountKeyWithMap(ModuleType module, Map map) {
		StringBuffer keyBuffer = new StringBuffer();
		for (Object key : map.keySet()) {
			if (map.get(key) != null && map.get(key).toString().length() > 0) {
				keyBuffer.append(key.toString() + "=" + map.get(key) + "_");
			}
		}
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "all_m_count" + "_" + keyBuffer;
		return key;
	}


	/***
	 * 生成获取所有列表对象的KEY
	 * 
	 * @param module
	 * @return
	 */
	public static String getAllListKey(ModuleType module) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "all_list";
		return key;
	}

	/***
	 * 根据Map 查询条件生成的key
	 * 
	 * @param module
	 * @return
	 */
	public static String getAllListKeyWithMap(ModuleType module, Map map) {
		StringBuffer keyBuffer = new StringBuffer();
		for (Object key : map.keySet()) {
			if (map.get(key) != null && map.get(key).toString().length() > 0) {
				keyBuffer.append(key.toString() + "_" + map.get(key) + "_");
			}
		}
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "all_map" + "_" + keyBuffer;
		return key;
	}

	/***
	 * 根据查询条件生成的key
	 * 
	 * @param module
	 * @return
	 */
	public static String getConditionsKey(ModuleType module, String valueKey, HttpServletRequest request) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "_" + valueKey + "_" + request;
		return key;
	}


	/***
	 * 根据Map 查询条件生成的key
	 * 
	 * @param module
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
