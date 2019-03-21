package com.sandu.common.util;

import java.util.Map;

import com.sandu.aes.util.FileEncrypt_old;
import com.sandu.common.properties.AppProperties;

/**
 * 资源分布式存储工具类
 * @author huangsongbo
 * FIXME:不直接读取app.properties,换成读取application的方法了
 */
//@Configuration
public class ResDistributeUtils_old {

	/**
	 * 分布式未加密文件上传路径配置
	 * app.upload.root.distribute
	 */
	public static final String noEncryptDistribute = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_RESOURCES_UPLOAD_ROOT_DISTRIBUTE_FILEKEY, "");
//	@Value("${app.resources.upload.root.distribute}")
//	public static String noEncryptDistribute;

	/**
	 * 分布式加密文件上传路径配置
	 * app.resources.encrypt.upload.root.distribute
	 */
	public static final String encryptDistribute = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_RESOURCES_ENCRYPT_UPLOAD_ROOT_DISTRIBUTE_FILEKEY, "");
//	@Value("${app.resources.encrypt.upload.root.distribute}")
//	public static String encryptDistribute;

	/**
	 * 域名配置
	 * app.resources.url.distribute
	 */
    public static final String urlDistribute = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_RESOURCES_URL_DISTRIBUTE_FILEKEY, "");
	/*@Value("${app.resources.url.distribute}")
	public static String urlDistribute;*/
	
	public static final Map<String, String> noEncryptDistributeMap = getNoEncryptDistributeMap();
	
	public static final Map<String, String> encryptDistributeMap = getEncryptDistributeMap();
	
	public static final Map<String, String> urlDistributeMap = getUrlDistributeMap();
	
	private static Map<String, String> getNoEncryptDistributeMap() {
//		InputStream in = Application.class.getClassLoader().getResourceAsStream("config/aes.properties");
//		Properties p = new Properties();
//		String noEncryptUploadRoot="";
//		try {
//			p.load(in);
//			noEncryptUploadRoot = p.getProperty("aes.resources.noEncrypt.upload.root");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		String 	json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"uploadRoot\":\"" + Utils.dealWithPath(noEncryptUploadRoot, null) + "\"}]}";
		String json = "";
		if(StringUtils.isEmpty(noEncryptDistribute)) {
			json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"uploadRoot\":\"" + Utils.dealWithPath(FileEncrypt_old.noEncryptUploadRoot, null) + "\"}]}";
		}else {
			json = noEncryptDistribute;
		}

		return Utils.getMapFromListJsonStr(json, "cfg", "modelName", "uploadRoot");
	}
	
	private static Map<String, String> getUrlDistributeMap() {
/*		InputStream in = Application.class.getClassLoader().getResourceAsStream("config/app.properties");
		Properties p = ne Pwroperties();
		String key="";
		try {
			p.load(in);
			key = p.getProperty("app.resources.url");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"domain\":\"" + key + "\"}]}";*/
		String json = "";
		if(StringUtils.isEmpty(urlDistribute)) {
			json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"domain\":\"" + Utils.getValueByFileKey(AppProperties.APP, AppProperties.RESOURCES_URL_FILEKEY, "") + "\"}]}";
		}else {
			json = urlDistribute;
		}
		return Utils.getMapFromListJsonStr(json, "cfg", "modelName", "domain");
	}

	private static Map<String, String> getEncryptDistributeMap() {
//		InputStream in = Application.class.getClassLoader().getResourceAsStream("config/aes.properties");
//		Properties p = new Properties();
//		String encryptUploadRoot="";
//		try {
//			p.load(in);
//			encryptUploadRoot = p.getProperty("aes.resources.encrypt.upload.root");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String 	json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"uploadRoot\":\"" + Utils.dealWithPath(encryptUploadRoot, null) + "\"}]}";
		String json = "";
		if(StringUtils.isEmpty(encryptDistribute)) {
			json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"uploadRoot\":\"" + Utils.dealWithPath(FileEncrypt_old.encryptUploadRoot, null) + "\"}]}";
		}else {
			json = encryptDistribute;
		}

		return Utils.getMapFromListJsonStr(json, "cfg", "modelName", "uploadRoot");
	}

}
