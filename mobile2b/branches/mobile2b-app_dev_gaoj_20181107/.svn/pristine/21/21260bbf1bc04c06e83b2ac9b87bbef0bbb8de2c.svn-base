package com.nork.common.cache.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.ConfigUtil;
import com.nork.common.util.collections.CustomerListUtils;

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
	 * @param limit
	 * @param start
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
	 * 生成获取带查询参数记录数的KEY
	 * 
	 * @param parameter
	 * @return
	 */
	public static String getTotalWithParameter(ModuleType module, PageParameter parameter) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "page_count_";
		if (CustomerListUtils.isNotEmpty(parameter.getLstParameter())) {
			for (QueryParameter p : parameter.getLstParameter()) {
				if (StringUtils.isNotBlank(p.getValue())) {
					key += p.getName() + "_" + p.getValue() + "_";
				}
			}
		}
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
	 * @param limit
	 * @param start
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
	 * 生成获取所有列表对象的KEY(带参数查询)
	 * 
	 * @param module
	 * @return
	 */
	public static String getAllListKeyWithParameter(ModuleType module, PageParameter parameter) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "all_list_";
		if (CustomerListUtils.isNotEmpty(parameter.getLstParameter())) {
			for (QueryParameter p : parameter.getLstParameter()) {
				if (StringUtils.isNotBlank(p.getValue())) {
					key += p.getName() + "_" + p.getValue() + "_";
				}
			}
		}
		return key;
	}

	/***
	 * 根据查询条件生成的key
	 * 
	 * @param module
	 * @param limit
	 * @param start
	 * @return
	 */
	public static String getConditionsKey(ModuleType module, String valueKey, HttpServletRequest request) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "_" + valueKey + "_" + request;
		return key;
	}

	/***
	 * 生成分页获取数据的KEY(包含查询关键词)
	 * 
	 * @param parameter
	 * @return
	 */
	public static String getPageQueryKeyParameter(ModuleType module, PageParameter parameter) {
		String key = null;
		key = ConfigUtil.getConfig("/dataSource/cache", module.toString()) + "page_list_";
		key += "_size_" + parameter.getPageSize() + "_" + "index_" + parameter.getPageIndex() + "_";
		if (CustomerListUtils.isNotEmpty(parameter.getLstParameter())) {
			for (QueryParameter p : parameter.getLstParameter()) {
				if (StringUtils.isNotBlank(p.getValue())) {
					key += p.getName() + "_" + p.getValue() + "_";
				}
			}
		}
		return key;
	}

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
