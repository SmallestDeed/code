package com.sandu.aes.util;

import com.sandu.common.properties.AesProperties;
import com.sandu.common.properties.AppProperties;
import com.sandu.common.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;


/**
 * @author Administrator
 *
 */
public class FileEncrypt {
	private static Logger logger = Logger.getLogger(FileEncrypt.class);
	// 密匙
	private static String key;
	// 加密开关
	private static String encryptSwitch;
	// 加密文件后缀配置(确定哪些文件需要加密)
	public static String encryptFileSuffix;
	// 加密文件存放地址
	public static String encryptUploadRoot;
	// 文件加密方式
	public static String encryptWay;
	// 文件默认上传根目录
	public static String defaultUploadRoot;
	// 原文件(非加密路径)存放地址
	public static String noEncryptUploadRoot;
	/**
	 * 默认访问路径(根,域名,url前面部分)
	 */
	public static String resourceUrl;

	/**
	 * 删除加密目录/非加密目录对应的该物理文件
	 * @param picPath
	 */
	public static void deleteAllFile(String picPath) {
		if(StringUtils.isEmpty(picPath)){
			logger.error("------function:deleteAllFile->StringUtils.isEmpty(picPath) = true");
			return;
		}
		deleteEncryptFile(picPath);
		deleteNoEncryptFile(picPath);
	}
	
	/**
	 * 删除非加密目录对应的该文件
	 * @author huangsongbo
	 * @param picPath
	 */
	private static void deleteNoEncryptFile(String picPath) {
		String encryptFilePath = Utils.getAbsolutePath(picPath, Utils.getAbsolutePathType.noEncrypt);
		encryptFilePath = Utils.dealWithPath(encryptFilePath,Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
		File file = new File(encryptFilePath);
		if(file.exists()){
			Utils.transferDeletedFile(picPath,encryptFilePath);
		}
	}
	
	/**
	 * 删除加密目录对应的该文件
	 * @author huangsongbo
	 * @param picPath
	 */
	private static void deleteEncryptFile(String picPath) {
		String encryptFilePath = Utils.getAbsolutePath(picPath, null);
		encryptFilePath = Utils.dealWithPath(encryptFilePath,Utils.getValueByFileKey(AppProperties.APP, AppProperties.SYSTEM_FORMAT_FILEKEY, "linux"));
		File file = new File(encryptFilePath);
		if(file.exists()){
			Utils.transferDeletedFile(picPath,encryptFilePath);
		}
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		FileEncrypt.logger = logger;
	}

/*	public static String getKey() {
		return key;
	}*/

	public static void setKey(String key) {
		FileEncrypt.key = key;
	}

/*	public static String getEncryptSwitch() {
		return encryptSwitch;
	}*/

	public static void setEncryptSwitch(String encryptSwitch) {
		FileEncrypt.encryptSwitch = encryptSwitch;
	}

	public static String getEncryptFileSuffix() {
		return encryptFileSuffix;
	}

	public static void setEncryptFileSuffix(String encryptFileSuffix) {
		FileEncrypt.encryptFileSuffix = encryptFileSuffix;
	}

	public static String getEncryptUploadRoot() {
		return encryptUploadRoot;
	}

	public static void setEncryptUploadRoot(String encryptUploadRoot) {
		FileEncrypt.encryptUploadRoot = encryptUploadRoot;
	}

	public static String getEncryptWay() {
		return encryptWay;
	}

	public static void setEncryptWay(String encryptWay) {
		FileEncrypt.encryptWay = encryptWay;
	}

	public static String getDefaultUploadRoot() {
		return defaultUploadRoot;
	}

	public static void setDefaultUploadRoot(String defaultUploadRoot) {
		FileEncrypt.defaultUploadRoot = defaultUploadRoot;
	}

	public static String getNoEncryptUploadRoot() {
		return noEncryptUploadRoot;
	}

	public static void setNoEncryptUploadRoot(String noEncryptUploadRoot) {
		FileEncrypt.noEncryptUploadRoot = noEncryptUploadRoot;
	}

	public static String getResourceUrl() {
		return resourceUrl;
	}

	public static void setResourceUrl(String resourceUrl) {
		FileEncrypt.resourceUrl = resourceUrl;
	}
}
