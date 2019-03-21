package com.sandu.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sandu.api.user.model.bo.ResourcesUrl;
import com.sandu.common.gson.GsonUtil;

@Component
public class ResourceConfig {

	/****/
    public static String PC_WEB_SOCKET_MSG_URL;

    /**支付回调后通知前端的websocket地址**/
    public static String PC_WEB_SOCKET_PAY_ORDER_URL;
    
    public static Integer PC_DEVICE_USER_CHECK_CONFIG;
    
    public static Integer PC_HEART_BEAT_TIME;
    
    public static String MOBILE_SERVER_URL;
    
    public static String MOBILE_RESOURCES_URL;
    
    public static String MOBILE_SITE_NAME;

    public static String PC_SERVER_URL;

    public static String PC_RESOURCES_URL;

    public static String PC_SERVER_SITE_NAME;

    public static String PC_SERVER_SITE_KEY;
    
    public static String PC_DRAW_RESOURCES_URL;
    
    public static String PC_DRAW_SERVER_URL;
    
    public static String PC_RESOURCES_Url_DISTRIBUTE;
    private static List<ResourcesUrl> RESOURCES_URLS;
    
    public static List<Map<String,String>> PC_SERVITIZATION_URLS;
    
    public static String AES_RESOURCES_ENCRYPT_KEY;
    
    public static String YUN_SERVER_URL;
    
    public static List<Map<String,String>> YUN_SERVITIZATION_URLS;
    
    public static String PC_SYSTEM_SYS_USER_LOGIN_LOG_UPLOAD_PATH;
   
    public static String PAY_PACKAGE_ADD_URL;
    
    public static String USER_CENTER_URL;
    
    public static String MINI_PROGRAM_GETOPENDID_URL;
    
    /**
     * 配置: pc.draw.houseDraw.resources.url
     * add by huangsongbo 2018.11.17
     */
    public static String PC_DRAW_HOUSEDRAW_RESOURCES_URL;
    
    @Value("${pc.webSocket.payOrder}")
    public void setPcWebSocketPayOrderUrl(String pcWebSocketPayOrderUrl) {
    	PC_WEB_SOCKET_PAY_ORDER_URL = pcWebSocketPayOrderUrl;
    }
    
    @Value("${pc.webSocket.message}")
    public void setPcWebSocketMsgUrl(String pcWebSocketMsgUrl) {
    	PC_WEB_SOCKET_MSG_URL = pcWebSocketMsgUrl;
    }
    
    @Value("${pc.isDeviceUserCheck}")
    public void setPcIsDeviceUserCheck(Integer pcIsDeviceUserCheck ) {
    	PC_DEVICE_USER_CHECK_CONFIG = pcIsDeviceUserCheck;
    }
    
    @Value("${pc.heartbeatTime}")
    public void setPcHeartbeatTime(Integer pcHeartbeatTime) {
    	PC_HEART_BEAT_TIME = pcHeartbeatTime;
    }
    
    @Value("${pc.server.url}")
    public void setPcServerUrl(String pcServerUrl) {
    	PC_SERVER_URL = pcServerUrl;
    }
    
    @Value("${pc.resources.url}")
    public void setPcResourcesUrl(String pcResourcesUrl) {
    	PC_RESOURCES_URL = pcResourcesUrl;
    }
    
    @Value("${pc.server.siteName}")
    public void setPcServerSiteName(String pcServerSiteName) {
    	PC_SERVER_SITE_NAME = pcServerSiteName;
    }
    
    @Value("${pc.server.siteKey}")
    public void setPcServerSiteKey(String pcServerSiteKey) {
    	PC_SERVER_SITE_KEY = pcServerSiteKey;
    }
    
    @Value("${pc.draw.resources.url}")
    public void setPcDrawResourcesUrl(String pcDrawResourcesUrl) {
    	PC_DRAW_RESOURCES_URL = pcDrawResourcesUrl;
    }
    
    @Value("${pc.draw.server.url}")
    public void setPcDrawServerUrl(String pcDrawServerUrl) {
    	PC_DRAW_SERVER_URL = pcDrawServerUrl;
    }
    
    
    @Value("${pc.resources.url.distribute}")
    public void setPcResourcesUrlDistribute(String pcResourcesUrlDistribute) {
    	PC_RESOURCES_Url_DISTRIBUTE = pcResourcesUrlDistribute;
    }
    
    public static List<ResourcesUrl> getResourcesUrlList() {
    	if(RESOURCES_URLS!=null) {
    		return RESOURCES_URLS;
    	}
    	String json = "";
		if(StringUtils.isEmpty(PC_RESOURCES_Url_DISTRIBUTE)) {
			json = "{\"cfg\":[{\"modelName\":\"d_userdesign,e_userlogs,b_base,c_basedesign,a_common,f_resource,g_other\",\"domain\":\"" + PC_RESOURCES_URL + "\"}]}";
		}else {
			json = PC_RESOURCES_Url_DISTRIBUTE;
		}
		RESOURCES_URLS = new ArrayList<ResourcesUrl>();
		Map<String,List<String>> resultMap = new HashMap<String,List<String>>();
		Map map = GsonUtil.fromJson(json,Map.class);
		List<Map> list = (List)map.get("cfg");
		for(Map mapEntity:list) {
			 String[] modelNameArray = mapEntity.get("modelName").toString().split(",");
			 String domain = mapEntity.get("domain").toString();
			 List<String> modelNameList = Arrays.asList(modelNameArray);
			 if(!resultMap.containsKey(domain)) {
				 resultMap.put(domain, modelNameList);
			 }else {
				 resultMap.get(domain).addAll(modelNameList);
			 }
		 }
		for (Map.Entry<String,List<String>> entity : resultMap.entrySet()) {  
			ResourcesUrl resourcesUrl = new ResourcesUrl();
			resourcesUrl.setDomain(entity.getKey());
			resourcesUrl.setModelName(entity.getValue());
			RESOURCES_URLS.add(resourcesUrl);
		}
		return RESOURCES_URLS;
    }
    
    @Value("${pc.servitization.urls}")
    public void setPcServitizationUrls(String pcServitizationUrls) {
    	if(StringUtils.isNotBlank(pcServitizationUrls)) {
    		PC_SERVITIZATION_URLS = GsonUtil.fromJson(pcServitizationUrls,List.class);
    	}
    }
    
    @Value("${pc.system.sysUser.loginLog.upload.path}")
    public void setPcSystemSysUserLoginLogUploadPath(String pcSystemSysUserLoginLogUploadPath) {
    	if(StringUtils.isNotBlank(pcSystemSysUserLoginLogUploadPath)) {
    		PC_SYSTEM_SYS_USER_LOGIN_LOG_UPLOAD_PATH = pcSystemSysUserLoginLogUploadPath;
    	}
    }
    
    
    
    @Value("${aes.resources.encrypt.key}")
    public void setAesResourcesEncryptKey(String aesResourcesEncryptKey) {
    	if(StringUtils.isNotBlank(aesResourcesEncryptKey)) {
    		AES_RESOURCES_ENCRYPT_KEY = aesResourcesEncryptKey;
    	}else {
    		AES_RESOURCES_ENCRYPT_KEY = "41e5c74dd46e4ddcb942dc8ce6224a2e";
    	}
    }
    
    @Value("${yun.server.url}")
    public void setYunServerUrl(String yunServerUrl) {
    	YUN_SERVER_URL = yunServerUrl;
    }
    
    @Value("${yun.servitization.urls}")
    public void setYunServitizationUrls(String yunServitizationUrls) {
    	if(StringUtils.isNotBlank(yunServitizationUrls)) {
    		YUN_SERVITIZATION_URLS = GsonUtil.fromJson(yunServitizationUrls,List.class);
    	}
    }
    
    @Value("${pay.package.add.url}")
    public void setPayPackageAddUrl(String payPackageAddUrl) {
    	PAY_PACKAGE_ADD_URL = payPackageAddUrl;
    }
    
    @Value("${user.center.url}")
    public void setUserCenterUrl(String userCenterUrl) {
    	USER_CENTER_URL = userCenterUrl;
    }
    
    @Value("${mini.program.getOpenid.url}")
    public void setMiniProgramGetOpenidUrl(String miniProgramGetOpenidUrl) {
    	MINI_PROGRAM_GETOPENDID_URL = miniProgramGetOpenidUrl;
    }
    	
    @Value("${mobile.server.url}")
    public void setMobileServerUrl(String mobileServerUrl) {
    	MOBILE_SERVER_URL = mobileServerUrl;
    }
    @Value("${mobile.resources.url}")
    public void setMobileResourcesUrl(String mobileRresourcesUrl) {
    	MOBILE_RESOURCES_URL = mobileRresourcesUrl;
    }
    @Value("${mobile.server.siteName}")
    public void setMobileSiteName(String mobileSiteName) {
    	MOBILE_SITE_NAME = mobileSiteName;
    }

    @Value("${pc.draw.houseDraw.resources.url}")
	public void setPC_DRAW_HOUSEDRAW_RESOURCES_URL(String pcDrawHouseDrawResourcesUrl) {
		PC_DRAW_HOUSEDRAW_RESOURCES_URL = pcDrawHouseDrawResourcesUrl;
	}
    
}
