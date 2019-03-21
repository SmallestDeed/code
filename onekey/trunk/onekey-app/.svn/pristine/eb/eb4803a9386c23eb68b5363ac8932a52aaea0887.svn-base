package com.nork.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.nork.common.constant.SystemCommonConstant;

public class Utils {
	
	/**
	 * 获取list大小
	 * 
	 * @author xiaoxc
	 * @param list
	 * @return
	 */
	public static int getListTotal(List list) {
		if (list != null && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}
	
	public static String getValue(String key, String defalut) {
		String value = "";

		try {
			ResourceBundle app = ResourceBundle.getBundle("app");
			value = app.getString(key);
		} catch (Exception e) {
			// e.printStackTrace();
			value = defalut;
		}
		return value;
	}
	
	public static String getDateStr(Date date, String dateFormatStr) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
		String str = "";
		try {
			str = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String getPropertyName(String proName, String key, String def) {
		String value = "";

		try {
			ResourceBundle app = ResourceBundle.getBundle(proName);
			value = app.getString(key);
			if (StringUtils.isBlank(value)) {
				value = def;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			value = def;
		}
		return value;
	}
	
	/**
	 * 
	 * 
	 * enableRedisCache 方法描述： 是否启用redis
	 * 
	 * @return
	 * 
	 * @return boolean 返回类型
	 * 
	 * @Exception 异常对象
	 * 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static boolean enableRedisCache() {
		return SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE.equalsIgnoreCase(
				Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE, SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE));
	}

	/**
	 * str(逗号隔开格式)转化为list
	 *
	 * @author huangsongbo
	 * @param str
	 * @return
	 */
	public static List<String> getListFromStr(String str) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isBlank(str))
			return list;
		if (str.startsWith(",")) {
			str = str.substring(1, str.length());
		}
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		String[] strs = str.split(",");
		list = Arrays.asList(strs);
		return list;
	}
	
}