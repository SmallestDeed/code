package com.sandu.aes.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sandu.common.properties.AesProperties;
import com.sandu.common.properties.AppProperties;
import com.sandu.common.util.Utils;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author Administrator
 * FIXME: 可以删掉这个文件，没用了
 */
public class FileEncrypt_old {
	private static Logger logger = Logger.getLogger(FileEncrypt_old.class);
	// 密匙
//	private static String key=Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
	@Value("${aes.resources.encrypt.key}")
	private static String key;
	// 加密开关
//	private static String encryptSwitch=Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_SWITCH_FILEKEY, "true").trim();
	@Value("${aes.resources.encrypt.switch}")
	private static String encryptSwitch;
	// 加密文件后缀配置(确定哪些文件需要加密)
//	public final static String encryptFileSuffix = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_FILESUFFIX_FILEKEY, "assetbundle,txt").trim();
	@Value("${aes.resources.encrypt.fileSuffix}")
	public static String encryptFileSuffix;
	// 加密文件存放地址
//	public static String encryptUploadRoot = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_UPLOAD_ROOT_FILEKEY, "/home/nork/resources_src").trim();
	@Value("${aes.resources.encrypt.upload.root}")
	public static String encryptUploadRoot;
	// 文件加密方式
//	public static String encryptWay = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_WAY_FILEKEY, "[{\"encryptWay\":\"DES\",\"suffix\":\"txt\"}]").trim();
	@Value("${aes.resources.encrypt.way}")
	public static String encryptWay;
	// 文件默认上传根目录
	public static String defaultUploadRoot = Utils.getValueByFileKey(AppProperties.APP, AppProperties.UPLOAD_ROOT_FILEKEY, "");
//	@Value("${app.upload.root}")
//	public static String defaultUploadRoot;
	// 原文件(非加密路径)存放地址
	public static String noEncryptUploadRoot = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_NOENCRYPT_UPLOAD_ROOT_FILEKEY, "/home/nork/resources").trim();
//	@Value("${aes.resources.noEncrypt.upload.root}")
//	public static String noEncryptUploadRoot;

	/**
	 * 默认访问路径(根,域名,url前面部分)
	 */
	public static String resourceUrl = Utils.getValueByFileKey(AppProperties.APP, AppProperties.RESOURCES_URL_FILEKEY, "");
//	@Value("${app.resources.url}")
//	public static String resourceUrl;

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
	
}
