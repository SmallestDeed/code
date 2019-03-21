package com.sandu.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

/**
 * 读取配置文件公共类
 * @author pandajun
 * 2013-11-7
 * @version 1.0
 *
 */
public class ConfigUtil {
	static Logger logger = LogManager.getLogger(ConfigUtil.class.getName());

	private static String resourceName = "app";
	private static String device = "device";
	private static ResourceBundle resource = null;
	private static ConfigUtil instance = null;
	
	public static ConfigUtil getInstance(String fileName){
		if(resource == null || !resourceName.equals(fileName)){
			resourceName = fileName;
			logger.info("加载"+resourceName+".properties资源文件");
			resource = ResourceBundle.getBundle(fileName);
		}
		if(instance == null)
			instance = new ConfigUtil();
		return instance;
	}
	
	/**
	 * 根据key获取application.properties文件的属性值
	 * 要读取其他文件先通过getInstance(String fileName)获取单例,再通过getValue(String key)获取属性值
	 */
	public static String getAttribute(String key) {
		if(resource==null || !resourceName.equals("application")){
			resourceName = "application";
			logger.info("加载"+resourceName+".properties资源文件");
			resource = ResourceBundle.getBundle(resourceName);
		}
		logger.info("加载"+resourceName+".properties资源文件的["+key+"]属性值");
		return resource.getString(key);
	}
	
	/**
	 * 根据key获取属性值
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		logger.info("加载"+resourceName+".properties资源文件的["+key+"]属性值");
		return resource.getString(key);
	}
	
	/**
	 * 根据文件名和key值获取value
	 * @param fileName 文件名
	 * @param key	键值
	 * @return
	 */
	public static String getConfig(String resourceName,String key){
		logger.info("加载"+resourceName+".properties资源文件的["+key+"]属性值");
		return getInstance(resourceName).getValue(key);
	}

	public static void main(String[] args) {
		String str = ConfigUtil.getConfig("app", "functionPid");
		logger.info(str);
	}

}
