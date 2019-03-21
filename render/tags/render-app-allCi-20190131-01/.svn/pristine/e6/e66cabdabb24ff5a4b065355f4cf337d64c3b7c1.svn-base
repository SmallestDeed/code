package com.nork.system.controller.web;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nork.common.cache.CacheManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.common.jwt.Jwt;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.AppProperties;
import com.nork.common.util.ResDistributeUtils;
import com.nork.common.util.Utils;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserLoginLog;
import com.nork.system.model.SysUser.ResourcesUrl;
import com.nork.system.service.SysUserLoginLogService;
import com.nork.system.service.SysUserService;

/**
 * @Title: SysUserController.java
 * @Package com.nork.system.controller
 * @Description:系统-用户Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/system/sysUser")
public class WebSysUserController {

	String cacheEnable = Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE, "0");
	private final static Logger logger = LogManager.getLogger(WebSysUserController.class);

	//private final String PIC_UPLOAD_PATH = "system.sysUser.pic.upload.path";

	//服务化地址集合
//	private static final String APP_SERVITIZATION_URLS = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_SERVITIZATION_URLS,"");
		
	//服务化地址key
	private static final String SERVER_KEY = "serverKey";
	//服务化地址url
	private static final String SERVER_URL = "serverUrl";
	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	/*** 获取配置文件 webSocket.properties */
	private final static ResourceBundle webSocket = ResourceBundle
			.getBundle("config/webSocket");

//	private final String SERVERURL = app.getString("app.server.url");
	//渲染机调取服务路径
	private final String APP_SERVITIZATION_URLS =app.getString( "app.servitization.urls" );
	private final String RENDERTASKSERVERURL = app.getString("app.render.task.server.url");
	private final String RESOURCESURL = app.getString("app.resources.url");
	private final String SITENAME = Utils.getValue("app.server.siteName",
			"nork");
	private final String SITEKEY = Utils.getValue("app.server.siteKey",
			"online");
	private static List<ResourcesUrl> resourcesUrls = getResourcesUrls();
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserLoginLogService sysUserLoginLogService;
	
	/*@RequestMapping(value = "/app/loginForAuto")
	@ResponseBody
	public ResponseEnvelope <SysUser> loginForAuto(SysUser sysUser, String terminalImei,String mediaType,
			HttpServletRequest request, HttpServletResponse response,
			Integer forceLogin,Integer userId) throws SocketException {
		ResponseEnvelope <SysUser>res = null;
		logger.info("loginForAuto===>渲染机登录开始验证白名单=====ing");
		//验证白名单
		boolean flag = sysUserService.checkWhiteList(terminalImei);
		if (!flag ) {
			logger.info("loginForAuto===>渲染机登录验证白名单=====失败");
			return new ResponseEnvelope<SysUser>(false,"数据异常",sysUser.getMsgId());
		}
		String result = null;
		String account = null;
		String password = null;
		String platformCode = "pc2b";
		SysUser user = sysUserService.get(userId);
		if (user != null) {
			account = user.getMobile();
			password = user.getPassword();
		}
		result = loginForSSO(account, password, platformCode);
		if (result.equals("false")) {
			return new ResponseEnvelope<>(false,"登录失败！");
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
    	String msg = (String) jsonObject.get("message");
    	logger.info("SSO登录状态 msg="+msg);
    	logger.error("SSO登录状态 msg="+msg);
		String appWebSocketMessage = webSocket.getString("app.webSocket.message");
		String appWebSocketPayOrder = webSocket.getString("app.webSocket.payOrder");
		user.setServitization(APP_SERVITIZATION_URLS);
		user.setWebSocketMessage(appWebSocketMessage);
		user.setPayCallBackServer(appWebSocketPayOrder);
		user.setServitizationList(this.getServitizationUrls());
		user.setServitization(APP_SERVITIZATION_URLS);
		user.setServerUrl(RENDERTASKSERVERURL);
		user.setResourcesUrl(RESOURCESURL);
		user.setSitekey(SITEKEY);
		user.setSiteName(SITENAME);
		System.out.println("loginForAuto============APP_SERVITIZATION_URLS"+APP_SERVITIZATION_URLS);
		logger.info("loginForAuto============APP_SERVITIZATION_URLS"+APP_SERVITIZATION_URLS);
		logger.info("loginforauto================="+user.getServitizationList().toString());
		res = new ResponseEnvelope<SysUser>(user, sysUser.getMsgId(), true);
			
		return res;
	}*/
	@RequestMapping(value = "/app/loginForAuto")
	@ResponseBody
	public ResponseEnvelope <SysUser> loginForAuto(SysUser sysUser, String terminalImei,String mediaType,
			HttpServletRequest request, HttpServletResponse response,
			Integer forceLogin,Integer userId) throws SocketException {
		ResponseEnvelope <SysUser>res = null;
		logger.info("loginForAuto===>渲染机登录开始验证白名单=====ing");
		//验证白名单
		boolean flag = sysUserService.checkWhiteList(terminalImei);
		if (!flag ) {
			logger.info("loginForAuto===>渲染机登录验证白名单=====失败");
			return new ResponseEnvelope<SysUser>(false,"数据异常",sysUser.getMsgId());
		}
		SysUser user = sysUserService.get(userId);
		//拿到UserId 查用户信息。
		if(StringUtils.isEmpty(mediaType)) {
			mediaType = "3";
		}
		if (user != null) {
			String appWebSocketMessage = webSocket.getString("app.webSocket.message");
			String appWebSocketPayOrder = webSocket.getString("app.webSocket.payOrder");
			
			
			//用token加密app用来解密的key
			String userKey = UUID.randomUUID().toString();
			Map<String , Object> payload=new HashMap<String, Object>();
			Date date=new Date();
			payload.put("uid", user.getId());//用户ID
			payload.put("uname", user.getNickName());//
			payload.put("mtype", mediaType);//mediatype
			payload.put("uphone", user.getMobile());//
			payload.put("appKey", userKey);//
			payload.put("ukey", userKey);
			payload.put("utype", user.getUserType());
			payload.put("signflat",SystemCommonConstant.LOGIN_FROM_PC);
			payload.put("iat", date.getTime());//生成时间
			
			payload.put("ext",date.getTime()+1000*60*60*SystemCommonConstant.USER_TIME_OUT_HOUR);//过期时间6小时
			String token=Jwt.createToken(payload);
			user.setToken(token);
			
			user = sysUserService.EencryptKey(user);
			// 设置分布式域名配置
			user.setResourcesUrls(resourcesUrls);
			//获取服务化集合配置
//			
		
			
			 //TODO Check this code
			
			LoginUser loginUser = user.toLoginUser();
			loginUser.setMediaType(mediaType);
			loginUser.setUserKey(userKey);
			//Cache 用户登录信息
//			if (Utils.enableRedisCache()) {
//			  
//				SysUserCacher.cacheTheLoginUserInfo(loginUser, SystemCommonConstant.USER_TIME_OUT_HOUR*60*60); //单位为秒
//			}
			
			System.out.println("user="+user.getServitization());
			user.setServitization(APP_SERVITIZATION_URLS);
			user.setWebSocketMessage(appWebSocketMessage);
			user.setPayCallBackServer(appWebSocketPayOrder);
		
			/* 保存心跳间隔信息 */
			Integer heartbeatTime = Integer.valueOf(Utils.getValue("heartbeatTime", "120"));
			user.setHeartbeatTime(heartbeatTime);
			user.setServitizationList(this.getServitizationUrls());
			user.setServitization(APP_SERVITIZATION_URLS);
			user.setServerUrl(RENDERTASKSERVERURL);
			user.setResourcesUrl(RESOURCESURL);
			user.setSitekey(SITEKEY);
			user.setSiteName(SITENAME);
			System.out.println("loginForAuto============APP_SERVITIZATION_URLS"+APP_SERVITIZATION_URLS);
			logger.info("loginForAuto============APP_SERVITIZATION_URLS"+APP_SERVITIZATION_URLS);
			logger.info("loginforauto================="+user.getServitizationList().toString());
			res = new ResponseEnvelope<SysUser>(user, sysUser.getMsgId(), true);
			
		}
		
		return res;
	}
	
	/**
	 * 得到分布式资源存储对应的域名配置
	 * 
	 * @author huangsongbo
	 * @return
	 */
	private static List<ResourcesUrl> getResourcesUrls() {
		Map<String, String> map = ResDistributeUtils.urlDistributeMap;
		Map<String, List<String>> mapTemp = new HashMap<String, List<String>>();
		for(String modelName : map.keySet()) {
			String domain = map.get(modelName);
			if(mapTemp.containsKey(domain)) {
				List<String> keyList = mapTemp.get(domain);
				keyList.remove(modelName);
				keyList.add(modelName);
				mapTemp.put(domain, keyList);
			}else {
				List<String> keyList = new ArrayList<String>();
				keyList.add(modelName);
				mapTemp.put(domain, keyList);
			}
		}
		
		// *组装List<ResourcesUrl> ->start
		List<ResourcesUrl> resourcesUrlList = new ArrayList<SysUser.ResourcesUrl>();
		for(String domain : mapTemp.keySet()) {
			ResourcesUrl resourcesUrl = new SysUser().new ResourcesUrl();
			resourcesUrl.setDomain(domain);
			resourcesUrl.setModelName(mapTemp.get(domain));
			resourcesUrlList.add(resourcesUrl);
		}
		// *组装List<ResourcesUrl> ->end
		
		return resourcesUrlList;
	}
	
	//获取服务化地址
	private List<Map<String,String>> getServitizationUrls(){
		List<Map<String,String>> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(APP_SERVITIZATION_URLS)) {
			Map<String,String> map = null;
			JSONArray jsonArray = JSONArray.fromObject(APP_SERVITIZATION_URLS);
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					map = new HashMap<>();
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					map.put(SERVER_KEY, jsonObj.getString(SERVER_KEY));
					map.put(SERVER_URL,jsonObj.getString(SERVER_URL));
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 用户退出接口(删除缓存信息)
	 * 
	 * @author huangsongbo
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	@RequestMapping("/sysUserQuit")
	@ResponseBody
	public Object sysUserQuit(SysUserLoginLog sysUserLoginLog,HttpServletRequest request, String msgId) throws UnknownHostException, SocketException {
		
		LoginUser loginUser =  com.nork.common.constant.util.SystemCommonUtil.getLoginUserInfoByAuthData(request);
		if(loginUser != null) {
			//用户数据采集
			sysUserLoginLog.setUserId(loginUser.getId());        	//取得登录的用户
			sysUserLoginLog.setLoginTime(new Date());   			//登出时间
			sysUserLoginLog.setLoginType(2);						//登录类型(2:登出)
			sysUserLoginLog.setServerIp(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP());		//服务器IP
			sysUserLoginLogService.txtSaveUserLog(sysUserLoginLog);
		}
		return new ResponseEnvelope<>(true, "success", msgId);
	}
/*	public static void main(String[] args) {
		String reuslt= null;
		String account = "18681523032";
		String password = "be44dc89aaeff5e196ea0aa905bd5512";
		String platformCode = "pc2b";
		
		reuslt = loginForSSO(account, password, platformCode);
		JSONObject jsonObject = JSONObject.fromObject(reuslt);
    	String msg = (String) jsonObject.get("message");
    	System.out.println("msg="+msg);
    	if (jsonObject.get("obj").equals("null")) {
    		System.out.println("null");
    	}
	}*/
	private static String loginForSSO(String account,String password,String platformCode) {
    	String payPath = Utils.getPropertyName("app","login.server.url","");
//    	String url=payPath+"/user/login?account="+account+"&password="+password;
    	String url="http://sso.ci.sanduspace.com/v1/user/login?account="+account+"&password="+password;
    	logger.error("url========================="+url);
    	logger.info("渲染机登录===>url"+url);
    	Map<String,String> params=new HashMap<String,String>();
		params.put("account", account);
		params.put("password", password);
		try {
			String result = Utils.doPostMethod(url, params,null,platformCode);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			return "false";
		}
		return "false";
    }
}
