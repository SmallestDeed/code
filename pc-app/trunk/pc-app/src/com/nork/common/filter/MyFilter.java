package com.nork.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nork.common.constant.UserConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.filter.context.MySessionContext;
import com.nork.common.jwt.Jwt;
import com.nork.common.jwt.TokenState;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysUserService;
import com.sandu.common.LoginContext;

import net.minidev.json.JSONObject;

public class MyFilter  implements Filter {
	
	private static Logger logger = Logger.getLogger(MyFilter.class);
	private final static String CLASS_LOG_PREFIX = "Myfilter过滤器:";
	public final static String IS_DEBUG = Utils.getValue("app.server.debug.model", "false").trim();
	public final static String IS_MEDIATYPE = Utils.getValue("app.client.mediaType", "2").trim();
	private FilterConfig filterConfig;
	private String exceptUrl;
	private String loginUrl;
	private String loginWebUrl;
	private String mobileLoginUrl;

	public String getMobileLoginUrl() {
		return mobileLoginUrl;
	}

	public void setMobileLoginUrl(String mobileLoginUrl) {
		this.mobileLoginUrl = mobileLoginUrl;
	}

	public String getExceptUrl() {
		return exceptUrl==null?"":exceptUrl.replace("\r", "").replace("\n", "").replace("\t", "").trim();
	}

	public void setExceptUrl(String exceptUrl) {
		this.exceptUrl = exceptUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginWebUrl() {
		return loginWebUrl;
	}

	public void setLoginWebUrl(String loginWebUrl) {
		this.loginWebUrl = loginWebUrl;
	}

	private SysUserService sysUserService;
	
	
	private ResPicService resPicService;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		exceptUrl = filterConfig.getInitParameter("exceptUrl");
		loginUrl = filterConfig.getInitParameter("loginUrl");
		loginWebUrl = filterConfig.getInitParameter("loginWebUrl");
		mobileLoginUrl = filterConfig.getInitParameter("mobileLoginUrl");
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext());
		sysUserService = wac.getBean(SysUserService.class);
		
		resPicService = wac.getBean(ResPicService.class);
		
		
		//创建线程池
//		ThreadPoolManager tpm = ThreadPoolManager.getSingleton();
//        ThreadPool threadPool = tpm.getThreadPool();
//        tpm.init(); 
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String authorization = "";
		String currentURL = "";
		// jsonStr = Utils.getAuthorization(request);
		currentURL = request.getRequestURI();
		authorization = request.getHeader("Authorization") != null ? (String) request.getHeader("Authorization") : "";

		/** APP端验证 **/
		// 对不需要认证的url直接放行
		String currentMsgId = request.getParameter("msgId");
		String currentPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		LoginUser loginUserInfo = null;
		if (StringUtils.length(pathInfo) > 0) {
			currentPath = currentPath + pathInfo;
		}
		String[] str = getExceptUrl().split(",");
		for (String s : str) {
			String ss = (s == null ? "" : s.trim());
			if (StringUtils.isNotEmpty(ss) && currentPath.indexOf(ss) != -1) {
				if (s.indexOf("/js/") != -1 || s.indexOf("/css/") != -1) {
					request.getRequestDispatcher(currentPath).forward(arg0, arg1);
					return;
				}
				arg2.doFilter(request, response);
				return;
			}
		}

		if (currentURL.indexOf("websocket") !=-1) {
			logger.error("websocket request" + currentURL);
			arg2.doFilter(request, response);
			return;
		}

		Integer loginStatus = LoginContext.getLoginStatus();
		switch (loginStatus) {
			case UserConstant.LOGIN_STATUS_ONE:
				logger.error(CLASS_LOG_PREFIX + "authorization is null, requestUrl=" + currentURL);
				dealWithErrorForApp(request, response, currentMsgId, "请登录", null);
				break;
			case UserConstant.LOGIN_STATUS_TWO:
				logger.error(CLASS_LOG_PREFIX + "APP token is EXPIRED, requestUrl=" + currentURL);
				dealWithErrorForApp(request, response, currentMsgId, "请登录", null);
				break;
			case UserConstant.LOGIN_STATUS_FIVE:
				logger.error(CLASS_LOG_PREFIX + "App token is INVALID,requestUrl=" + currentURL);
				dealWithErrorForApp(request, response, currentMsgId, "请登录", null);
				break;
			case UserConstant.LOGIN_STATUS_THREE:
				logger.error(CLASS_LOG_PREFIX + "用户在其他地方登录,requestUrl=" + currentURL);
				dealWithErrorForApp(request, response, currentMsgId, "用户在其他地方登录", null);
				break;
			case UserConstant.LOGIN_STATUS_FOUR:
				arg2.doFilter(request, response);
				break;
			default:
				logger.error(CLASS_LOG_PREFIX + "无法匹配的登录状态,loginStatus=" + loginStatus);
				arg2.doFilter(request, response);
				break;
		}
	}

	
	private boolean checkOneLogin(FilterChain arg2, HttpServletRequest request, HttpServletResponse response, JSONObject outputMSg, Map<String, Object> resultMap) throws IOException, ServletException {

		boolean checkResult = true;
		logger.info("移动端接收token中信息:" + resultMap);
		JSONObject dataObj = (JSONObject) resultMap.get("data");
		String appkey = (String) dataObj.get("appKey");
		// 移动端登录标识
		String loginFlag = SystemCommonConstant.LOGIN_FROM_MOBILE;

		outputMSg.put("success", false);
		outputMSg.put("CheckOneLogin", true);

		if (StringUtils.isBlank(appkey)) {
			logger.error("token信息缺失appkey");
			outputMSg.put("message", "token信息缺失appkey");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}
		// 当前请求用户信息key值
		String loginUserKey = loginFlag + appkey;
		LoginUser user = LoginContext.getLoginUser(LoginUser.class);
		if (user == null) {
			logger.debug("用户缓存信息为空");
			// 同一账号在另一处登录时会将之前用户的缓存移去 (也可能是redis没连接上,请检查redis连接)
			outputMSg.put("message", "当前账号已在其他设备登录，请重新登录");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}
		// 当前已登录用户信息key
		// String onlineUserKey = loginFlag + user.getId().toString();

		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null || loginUser.getAppKey() == null) {
			logger.debug("loginUser为空loginUser:" + loginUser);
			outputMSg.put("message", "您的登录信息已过期，请重新登录！");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}

		if (!appkey.equals(loginUser.getAppKey())) {
			logger.debug("appkey不一致，强制退出！loginUser:" + loginUser);
			// 移除该请求用户的缓存

			LoginContext.removeUserCache(loginUserKey);
			outputMSg.put("message", "当前账号已在其他设备登录，请重新登录");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}

		return true;
	}
	
//	@Override
	public void doFilter2(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String jsonStr = "";
		String authorization = "";
		String currentURL = "";
		
		jsonStr = Utils.getAuthorization(request);
		currentURL = request.getRequestURI();
		authorization = (String)request.getHeader("Authorization");
//		jsonStr = "{\"appKey\":\"14175935217219161417780249487989\",\"token\":\"87639785642955672676419744456463\",\"deviceId\":\"1\",\"mediaType\":\"3\"}";
		String ContentType =  (String)request.getHeader("Content-Type");
		String userAgent = (String) request.getHeader("User-Agent");
		if(request.getHeader("MediaType") != null 
				&& request.getHeader("MediaType").toString().equals(SystemCommonConstant.MEDIA_TYPE_OF_MOBILE)) {
			if(request.getRequestURI().endsWith(mobileLoginUrl)){
				//登陆接口不校验token，直接放行
				arg2.doFilter(request, response);
				return;
			}
			/*String currentPath = request.getServletPath();
		    String pathInfo = request.getPathInfo();
		    if (StringUtils.length(pathInfo) > 0) {
		    	currentPath = currentPath + pathInfo;
		    }
			String[] str = getExceptUrl().split(",");
			for (String s : str) {
				String ss =(s==null?"":s.trim());
				if(StringUtils.isEmpty(ss)) {
					logger.error(" exceptUrl is exists null value!");
				}
				if (StringUtils.isNotEmpty(ss) && currentPath.indexOf(ss) != -1) {
					logger.debug("error1:currentURL=" + currentURL + " 允许无登录通过,currentPath="+currentPath+",exceptUrl="+s);
					if(s.indexOf("/js/") != -1 || s.indexOf("/css/") != -1){
						request.getRequestDispatcher(currentPath).forward(arg0, arg1);
						return;
					}
					arg2.doFilter(request, response);
					logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
					return;
				}
			
			}*/
			//其他API接口一律校验token
			//从请求头中获取token
			String token=request.getHeader("Authorization");
			Map<String, Object> resultMap=Jwt.validToken(token);
			TokenState state=TokenState.getTokenState((String)resultMap.get("state"));
			JSONObject outputMSg=new JSONObject();
			switch (state) {
				case VALID:
					//取出payload中数据,放入到request作用域中
					request.setAttribute("AuthorizationData", resultMap.get("data"));
					//放行
					arg2.doFilter(request, response);
					break;
				case EXPIRED:
					logger.error("token已过期");
					//token过期或者无效，则输出错误信息返回
					outputMSg.put("success", false);
					outputMSg.put("message", "登录超时，请重新登录");
					output(outputMSg.toJSONString(), response);
					break;
				case INVALID:
					logger.error("无效的token");
					//token过期或者无效，则输出错误信息返回
					outputMSg.put("success", false);
					outputMSg.put("message", "登录超时，请重新登录");
					output(outputMSg.toJSONString(), response);
					break;
			}
	        return;
	}
		
		// 判断请求是不是来自浏览器
		if (userAgent != null
				&& userAgent.indexOf("Mozilla/") != -1
				&& (userAgent.indexOf("Firefox/") != -1 || userAgent
						.indexOf("Chrome/") != -1 || ((userAgent.indexOf("Gecko")>0 && userAgent.indexOf("rv:11")>0)||userAgent.indexOf("MSIE")>0))) {
			jsonStr = Utils.getAuthorization(request);
			currentURL = request.getRequestURI();
			authorization = (String)request.getHeader("Authorization");
			logger.info("------识别请求来自浏览器,非app");
		}else{
			jsonStr = Utils.getAuthorization(request);
			currentURL = request.getRequestURI();
			authorization = (String)request.getHeader("Authorization");
			logger.info("authorization==========>"+authorization);
			
			if(null == jsonStr || "".equals(jsonStr) || StringUtils.isEmpty(jsonStr) || StringUtils.isBlank(jsonStr)){
				logger.info("jsonStr==========is null");
				jsonStr = FilterWrap.strWrapper(arg0);
			}if(null == authorization || "".equals(authorization) || StringUtils.isEmpty(authorization) || StringUtils.isBlank(authorization)){
				logger.info("authorization==========is null");
				authorization = FilterWrap.strWrapper(arg0);
			}
		}
		
			@SuppressWarnings("rawtypes")
			Enumeration emu = request.getParameterNames();
			StringBuffer condition = new StringBuffer("?");
			boolean isHasCondition = false;
			while (emu.hasMoreElements()) {
				isHasCondition = true;
				String paramName = (String) emu.nextElement();
				condition.append(paramName);
				condition.append("=");
				condition.append(request.getParameter(paramName));
				condition.append("&");
			}
			if (isHasCondition) {
				currentURL += condition.toString();
			}
			
		
			
		currentURL = ""
				+ ((currentURL.lastIndexOf("&") == currentURL.length() - 1) ? currentURL
						.substring(0, currentURL.length() - 1) : currentURL);
		
		logger.info(" Myfilter comming para ...requestURI =" + "\n"+ currentURL + "\n" + " >>>>>>> "
				+ "authorization=" + authorization + ",ContentType="
				+ ContentType + ",User-Agent=" + userAgent  
				);
	    
		LoginUser loginUserTemporary=null;
		SysUser sysUser2=null;
		
		if(request.getSession().getAttribute("loginUserToken") != null){
			loginUserTemporary = (LoginUser)request.getSession().getAttribute("loginUserToken");
		}
		if(StringUtils.isNotBlank(jsonStr) ){
			loginUserTemporary = (LoginUser) new JsonDataServiceImpl<LoginUser>().getJsonToBean(jsonStr, LoginUser.class);
		}
		if(loginUserTemporary!=null){
			if(Utils.enableRedisCache()){
				sysUser2=SysUserCacher.checkTimeOutUserByToken(loginUserTemporary.getToken());
			}/*else{
				sysUser2 = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)
			}*/
		}
		LoginUser loginUser=null;
		/*test*/
		loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		/*test->end*/
		if(sysUser2!=null){
			sysUser2.setAppKey(loginUserTemporary.getAppKey());
			sysUser2.setToken(loginUserTemporary.getToken());
			loginUser=sysUser2.toLoginUser();
			if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
				request.getSession().setAttribute("loginUser", loginUser);
			}
		}
		/*modification->end*/
		if ( loginUser == null) {
			loginUser = new LoginUser();
			loginUser.setId(0);
			loginUser.setLoginId("anonymous");
			loginUser.setLoginName("nologin");
			loginUser.setName("匿名用户");
			loginUser.setUserType(0);
			loginUser.setGroupId(0);
			logger.info("id="+loginUser.getId() + ";userType=" + loginUser.getUserType()
					+ ";loginId=" + loginUser.getLoginId() + ";loginName="
					+ loginUser.getLoginName() + ";loginEmail="
					+ loginUser.getLoginEmail() + ";loginPhone="
					+ loginUser.getLoginPhone() + ";name=" + loginUser.getName()
					+ ";appkey=" + loginUser.getAppKey() + ";token=" + loginUser.getToken()
					+ ";groupId=" + loginUser.getGroupId());
			request.getSession().setAttribute("loginUser", loginUser);
			SysUser sysUser=sysUserService.getSysUserByLoginUser(loginUser);
			if(Utils.enableRedisCache()){
				SysUserCacher.updateTimeOutUser(sysUser);
			}
		}else{
			logger.info("id="+loginUser.getId() + ";userType=" + loginUser.getUserType()
					+ ";loginId=" + loginUser.getLoginId() + ";loginName="
					+ loginUser.getLoginName() + ";loginEmail="
					+ loginUser.getLoginEmail() + ";loginPhone="
					+ loginUser.getLoginPhone() + ";name=" + loginUser.getName()
					+ ";appkey=" + loginUser.getAppKey() + ";token=" + loginUser.getToken()
					+ ";groupId=" + loginUser.getGroupId());
		}

	    String currentPath = request.getServletPath();
	    String pathInfo = request.getPathInfo();
	    if (StringUtils.length(pathInfo) > 0) {
	    	currentPath = currentPath + pathInfo;
	    }
		String[] str = getExceptUrl().split(",");
		for (String s : str) {
			String ss =(s==null?"":s.trim());
			if(StringUtils.isEmpty(ss)) {
				logger.error(" exceptUrl is exists null value!");
			}
			if (StringUtils.isNotEmpty(ss) && currentPath.indexOf(ss) != -1) {
				logger.info("error1:currentURL=" + currentURL + " 允许无登录通过,currentPath="+currentPath+",exceptUrl="+s);
				if(s.indexOf("/js/") != -1 || s.indexOf("/css/") != -1){
					request.getRequestDispatcher(currentPath).forward(arg0, arg1);
					return;
				}
				arg2.doFilter(request, response);
				logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
				return;
			}
		
		}

		if (currentURL.lastIndexOf(".htm") != -1
				|| currentURL.lastIndexOf(".jsp") != -1) {
			logger.info("requestURI with para=" + currentURL
					+ " >>>>>>> User-Agent=" + userAgent);

			String jsessionid = request.getParameter("jsessionid");
			HttpSession sessoin = request.getSession();

			if (!StringUtils.isEmpty(jsessionid)) {
				sessoin = MySessionContext.getInstance().getSession(jsessionid);
				if( sessoin != null){
					request.setAttribute("getSessionById", "true");
				}
			}

			if (sessoin == null) {
				sessoin = request.getSession();
			}

			loginUser = (LoginUser) sessoin.getAttribute("loginUser");
			logger.info("loginUser=" + loginUser+";loginUser.loginId="+loginUser==null?"":loginUser.getLoginId());

			if (loginUser == null) {
				// 为空系统产生了未知异常,跳转到各自的登录页面
				if (currentURL.indexOf("/online/") != -1) {
					logger.info("error2:currentURL=" + currentURL + " 跳转到登录页,currentPath="+currentPath+",loginWebUrl="+loginWebUrl);
					response.sendRedirect(request.getContextPath()
							+ loginWebUrl);
				} else {
					logger.info("error3:currentURL=" + currentURL + " 跳转到登录页,currentPath="+currentPath+",loginUrl="+loginUrl);
					response.sendRedirect(request.getContextPath() + loginUrl);
				}
				return;
			} else {
				 
				// 来自浏览器
				if (userAgent != null
						&& userAgent.indexOf("Mozilla/") != -1
						&& (userAgent.indexOf("Firefox/") != -1 || userAgent
								.indexOf("Chrome/") != -1 || ((userAgent.indexOf("Gecko")>0 && userAgent.indexOf("rv:11")>0)||userAgent.indexOf("MSIE")>0))) {

					// 如果是来自于浏览器 +
					// request流数据(优先判断流数据,为浏览器中某个插件或组件提供访问方式的方法,也是基于模拟客户端请求)
					
					
					if (jsonStr != null && jsonStr.trim().length() > 0) {
						loginUser = (LoginUser) new JsonDataServiceImpl<LoginUser>()
								.getJsonToBean(jsonStr, LoginUser.class);
						String token = loginUser.getToken();
						String appkey = loginUser.getAppKey();
						String msgId = loginUser.getMsgId();
						String deviceId = loginUser.getDeviceId();
						String mediaType =  loginUser.getMediaType();
						boolean flag = false;
						if (loginUser != null && !StringUtils.isEmpty(token)
								&& !StringUtils.isEmpty(appkey)
								&& !StringUtils.isEmpty(deviceId)
								&& (new Integer(deviceId).intValue() > 0)
								&& !StringUtils.isEmpty(mediaType)
								&& (new Integer(mediaType).intValue() > 0)
							) {
							flag = saveSessionUser(request, response, token,
									appkey, deviceId);
						}
						
						
						// 如果存在有效用户信息,则允许进入,无则返回无权限信息
						if (!flag) {
							logger.info("error4:currentURL=" + currentURL + " 浏览器http头校验不成功,返回无权限,currentPath="+currentPath);
							//noPermission(response, msgId);
							dealWithError(request, response, msgId, "noPermission");
							return;
						} else {
							if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
							   mediaType = IS_MEDIATYPE;
							}
							request.getSession().setAttribute("deviceId", deviceId);
							request.getSession().setAttribute("mediaType", mediaType);
							logger.info("currentURL=" + currentURL + " 浏览器http头校验成功,允许通过,currentPath="+currentPath);
							arg2.doFilter(request, response);
							logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
							return;
						}

					} else {
						//建立一种从参数构造的方式
						String token = (String)request.getParameter("token");
						String appkey = (String)request.getParameter("appKey"); 
						String msgId =  (String)request.getParameter("msgId");
						String deviceId = (String)request.getParameter("deviceId");
						String mediaType =  (String)request.getParameter("mediaType");
						
						logger.info("http(Firefox,Chrome).request.getParameter paraStr:"+"token="+token+",appkey="+appkey+",deviceId="+deviceId+",msgId="+msgId+",mediaType="+mediaType);
						
						if (StringUtils.isEmpty(token) || StringUtils.isEmpty(appkey) || StringUtils.isEmpty(deviceId) || (new Integer(deviceId).intValue() <= 0) || 
								StringUtils.isEmpty(mediaType)|| (new Integer(mediaType).intValue() <= 0)
							) {
							if(StringUtils.isNotEmpty(ContentType) && ContentType.startsWith("multipart/form-data;") && ContentType.contains(";Authorization=")){
								 String Authorization = ContentType.substring(ContentType.indexOf(";Authorization=")+";Authorization=".length());
								 if (Authorization != null && Authorization.trim().length() > 0) {
										loginUser = (LoginUser) new JsonDataServiceImpl<LoginUser>().getJsonToBean(Authorization.trim(), LoginUser.class);
									    token = loginUser.getToken();
										appkey = loginUser.getAppKey();
										msgId = loginUser.getMsgId();
										deviceId = loginUser.getDeviceId();
										mediaType =  (String)loginUser.getMediaType();
								 }
								 logger.info("客户端参数构造 ContentType.Authorization.multipartRequest.getParameter paraStr:"+"token="+token+",appkey="+appkey+",deviceId="+deviceId+",msgId="+msgId+",mediaType="+mediaType);
						  }else{
								 token = (String)request.getAttribute("token");
								 appkey = (String)request.getAttribute("appKey"); 
								 msgId =  (String)request.getAttribute("msgId");
								 deviceId = (String)request.getAttribute("deviceId");
								 mediaType =  (String)request.getAttribute("mediaType");
								 logger.info("http(Firefox,Chrome).request.getAttribute paraStr:"+"token="+token+",appkey="+appkey+",deviceId="+deviceId+",msgId="+msgId+",mediaType="+mediaType);
						  }
					}
						if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(appkey) && !StringUtils.isEmpty(deviceId) && (new Integer(deviceId).intValue() > 0) &&
								!StringUtils.isEmpty(mediaType)&& (new Integer(mediaType).intValue() > 0)) {
							   boolean flag = saveSessionUser(request, response, token,appkey, mediaType);
						
								// 如果存在有效用户信息,则允许进入,无则返回无权限信息
								if (!flag) {
									logger.info("currentURL=" + currentURL + " 浏览器构造参数校验不成功,返回无权限,currentPath="+currentPath);
									//noPermission(response, msgId);
									return;
								} else {
									if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
										mediaType = IS_MEDIATYPE;
								    }
									request.getSession().setAttribute("deviceId", deviceId);
									request.getSession().setAttribute("mediaType", mediaType);
							
									logger.info("currentURL=" + currentURL + " 浏览器构造参数校验成功,允许通过,currentPath="+currentPath);
									arg2.doFilter(request, response);
									logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
									return;
								}
						
						}else{
								// 如果流数据为空(正常浏览器访问)
								// 匿名用户直接返回到登录页面,其他的允许通过
								if ("anonymous".equals(loginUser.getLoginId())) {
									if (currentURL.indexOf("/online/") != -1) {
										logger.info("currentURL=" + currentURL + " 正常浏览器,无参数构造,匿名用户,包含online路径,强制跳转到登录页,currentPath="+currentPath+",loginWebUrl="+loginWebUrl);
										response.sendRedirect(request.getContextPath()
												+ loginWebUrl);
									}else{
										logger.info("currentURL=" + currentURL + " 强制跳转到登录页,currentPath="+currentPath+",loginUrl="+loginUrl);
										response.sendRedirect(request.getContextPath()
												+ loginUrl);
									}
									return;
								} else {
									if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
									   mediaType = IS_MEDIATYPE;
									}
									request.getSession().setAttribute("deviceId", deviceId);
									request.getSession().setAttribute("mediaType", mediaType);
									arg2.doFilter(request, response);
									logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
									return;
								}
					   }
					}
				} else {
					// 非支持的浏览器,有可能来自于其他客户端或浏览器,只支持流数据解析
					// 如果仅仅是来自于request流数据(客户端模拟)
					
					//客户端带头文件的访问方式
					//String jsonStr = Utils.getAuthorization(request);
					
					if (jsonStr != null && jsonStr.trim().length() > 0) {
						loginUser = (LoginUser) new JsonDataServiceImpl<LoginUser>()
								.getJsonToBean(jsonStr, LoginUser.class);
						String token = loginUser.getToken();
						String appkey = loginUser.getAppKey();
						String msgId = loginUser.getMsgId();
						String deviceId = loginUser.getDeviceId();
						String mediaType =  (String)loginUser.getMediaType();
						boolean flag = false;
						if (loginUser != null && !StringUtils.isEmpty(token)
								&& !StringUtils.isEmpty(appkey)
								&& !StringUtils.isEmpty(deviceId)
								&& (new Integer(deviceId).intValue() > 0)
								&& !StringUtils.isEmpty(mediaType)
								&& (new Integer(mediaType).intValue() > 0)
								) {
							flag = saveSessionUser(request, response, token,
									appkey, deviceId);
						}
						//客户端头文件+http方式(正常客户端访问)
						/*if (!flag) {
							logger.info("currentURL=" + currentURL + " 客户端http头校验不成功,返回无权限,currentPath="+currentPath);
							dealWithError(request, response, msgId, "noPermission");
							return;
						}else{*/
							if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
							   mediaType = IS_MEDIATYPE;
							}
							request.getSession().setAttribute("deviceId", deviceId);
							request.getSession().setAttribute("mediaType", mediaType);
							logger.info("currentURL=" + currentURL + " 客户端http头校验成功,允许通过,currentPath="+currentPath);
							
							/*超时验证*/
							 //LoginUser loginUser2=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
							 //if(loginUser2!=null&&loginUser2.getId()!=null&&loginUser2.getId()>0){
								 //SysUser sysUser=SysUserCacher.checkTimeOutUser(loginUser2.getId());
							
								if(Utils.enableRedisCache()){
									SysUser sysUser=SysUserCacher.checkTimeOutUserByToken(token);
									 /*超时操作*/
									 if(sysUser==null){
										/*删除session中用户的登录信息*/
										request.getSession().removeAttribute("loginUser");
										/*删除session中用户的登录信息->end*/
										 dealWithError(request, response, msgId, "操作超时");
										 ////System.out.println("id为:"+loginUser2.getId()+"的用户操作超时");
										 return;
									 }else{
										 /*更新超时时间*/
										 SysUserCacher.updateTimeOutUser(sysUser);
										 ////System.out.println("id为:"+loginUser2.getId()+"的用户更新超时时间");
									 }
								 //}
								/*超时验证->end*/
								}
							arg2.doFilter(request, response);
							logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
							return;
						//}

					} else {
						
						//建立一种从参数构造的方式
						String token = (String)request.getParameter("token");
						String appkey = (String)request.getParameter("appKey"); 
						String deviceId = (String)request.getParameter("deviceId");
						String msgId =  (String)request.getParameter("msgId");
						String mediaType =  (String)request.getParameter("mediaType");
						logger.info("客户端参数构造  request.getParameter paraStr:"
								+ "token=" + token + ",appkey=" + appkey
								+ ",deviceId=" + deviceId + ",msgId=" + msgId
								+ ",mediaType=" + mediaType);
						
						if (       StringUtils.isEmpty(token)
								|| StringUtils.isEmpty(appkey)
								|| StringUtils.isEmpty(deviceId)
								|| (new Integer(deviceId).intValue() <= 0)
								|| StringUtils.isEmpty(mediaType)
								|| (new Integer(mediaType).intValue() <= 0)
							) {
							if(StringUtils.isNotEmpty(ContentType) && ContentType.startsWith("multipart/form-data;") && ContentType.contains(";Authorization=")){
								
								 String Authorization = ContentType.substring(ContentType.indexOf(";Authorization=")+";Authorization=".length());
								 if (Authorization != null && Authorization.trim().length() > 0) {
										loginUser = (LoginUser) new JsonDataServiceImpl<LoginUser>()
												.getJsonToBean(Authorization.trim(), LoginUser.class);
									    token = loginUser.getToken();
										appkey = loginUser.getAppKey();
										msgId = loginUser.getMsgId();
										deviceId = loginUser.getDeviceId();
										mediaType =  (String)loginUser.getMediaType();
								 }
								 logger.info("客户端参数构造 ContentType.Authorization.multipartRequest.getParameter paraStr:"
											+ "token=" + token + ",appkey=" + appkey
											+ ",deviceId=" + deviceId + ",msgId=" + msgId
											+ ",mediaType=" + mediaType);
						  }else{
								 token = (String)request.getAttribute("token");
								 appkey = (String)request.getAttribute("appKey"); 
								 msgId =  (String)request.getAttribute("msgId");
								 deviceId = (String)request.getAttribute("deviceId");
								 mediaType =  (String)request.getAttribute("mediaType");
								 logger.info("客户端参数构造 request.getAttribute paraStr:"
											+ "token=" + token + ",appkey=" + appkey
											+ ",deviceId=" + deviceId + ",msgId=" + msgId
											+ ",mediaType=" + mediaType);
						  }
						}
						
						if (       !StringUtils.isEmpty(token)
								&& !StringUtils.isEmpty(appkey)
								&& !StringUtils.isEmpty(deviceId)
								&& (new Integer(deviceId).intValue() > 0)
								&& !StringUtils.isEmpty(mediaType)
								&& (new Integer(mediaType).intValue() > 0)
							) {
							  boolean flag = saveSessionUser(request, response, token,
									appkey, deviceId);
						
								// 如果存在有效用户信息,则允许进入,无则返回无权限信息
								if (!flag) {
									logger.info("currentURL=" + currentURL + " 客户端参数构造有值但未校验成功,返回无权限,currentPath="+currentPath);
									//noPermission(response, msgId);
									dealWithError(request, response, msgId, "noPermission");
									return;
								} else {
									if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
									   mediaType = IS_MEDIATYPE;
								    }
									request.getSession().setAttribute("deviceId", deviceId);
									request.getSession().setAttribute("mediaType", mediaType);
									logger.info("currentURL=" + currentURL + " 客户端参数构造校验成功,允许通过,currentPath="+currentPath);
									arg2.doFilter(request, response);
									logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
									return;
								}
						}else{
							//客户端调用模式
							//使用flash插件时,存在使用sessionid获取session的方式则运行通过,其他的均返回无权限
							String sessionKey = (String) request.getAttribute("getSessionById");
							logger.info("sessionKey=" + sessionKey+";userAgent=" + userAgent);
							//网页端的flash模式
							if("true".equals(sessionKey) && "Shockwave Flash".equals(userAgent)){
								// 匿名用户直接返回到登录页面,其他的允许通过
								if ("anonymous".equals(loginUser.getLoginId())) {
									if (currentURL.indexOf("/online/") != -1) {
										/*response.sendRedirect(request.getContextPath()
												+ loginWebUrl);*/
										//暂时允许通过
										if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
										   mediaType = IS_MEDIATYPE;
										}
										request.getSession().setAttribute("deviceId", deviceId);
										request.getSession().setAttribute("mediaType", mediaType);
										request.setAttribute("getSessionById", null);
										
//										logger.info("currentURL=" + currentURL + " flash客户端,匿名用户,包含online路径,暂时允许通过,currentPath="+currentPath);
//										arg2.doFilter(request, response);
										
										logger.info("currentURL=" + currentURL + " flash客户端,匿名用户,包含online路径,强制跳转到登录页,currentPath="+currentPath+",loginUrl="+loginUrl);
										response.sendRedirect(request.getContextPath() + loginUrl);
									}else{
										logger.info("currentURL=" + currentURL + " flash客户端,匿名用户,强制跳转到登录页,currentPath="+currentPath+",loginUrl="+loginUrl);
										request.setAttribute("getSessionById", null);
										response.sendRedirect(request.getContextPath()
												+ loginUrl);
									}
									return;
								} else {
									if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
									   mediaType = IS_MEDIATYPE;
									}
									request.getSession().setAttribute("deviceId", deviceId);
									request.getSession().setAttribute("mediaType", mediaType);
									request.setAttribute("getSessionById", null);
									logger.info("currentURL=" + currentURL + " flash客户端,通过id获取session并且存在有效用户数据,允许通过,currentPath="+currentPath);
									arg2.doFilter(request, response);
									logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
									return;
								}
							}else{
								logger.info("currentURL=" + currentURL + " 客户端无任何参数,返回无权限,currentPath="+currentPath);
								//noPermission(response, msgId);
								dealWithError(request, response, msgId, "noPermission");
								return;
							}
						}
					}
				}
			}
		}
		
		
		logger.info("currentURL=" + currentURL + " 其他不需要校验登录情况,允许通过,currentPath="+currentPath);
		arg2.doFilter(request, response);
		logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime)+" ms");
		return;
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}

//	private void noPermission(
//			HttpServletRequest request,HttpServletResponse response,
//			String msgId) {
//		/*删除session中用户的登录信息*/
//		request.getSession().removeAttribute("loginUser");
//		/*删除session中用户的登录信息->end*/
//		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//		SysUserCacher.removeTimeOutUser(loginUser.getId());
//		PrintWriter pw = null;
//		try {
//			// StringBuffer resultBuffer=new StringBuffer();
//			pw = response.getWriter();
//			/*
//			 * resultBuffer.append("{success:false,");
//			 * resultBuffer.append("data:{}}") ;
//			 */
//			ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(
//					false, "noPermission", msgId);
//			res.setObj("anonymous");
//			String result = new JsonDataServiceImpl<SysUser>()
//					.getBeanToJsonData(res);
//
//			/* pw.write(resultBuffer.toString()); */
//			pw.write(result);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (pw != null) {
//				pw.close();
//			}
//		}
//	}

	/**
	 * 异常操作(超时,没有权限...)
	 * @param response
	 * @param msgId
	 * @param msg
	 */
	private void dealWithError(
			HttpServletRequest request,HttpServletResponse response,
			String msgId, String msg) {
		/*删除session中用户的登录信息*/
		request.getSession().removeAttribute("loginUser");
		/*删除session中用户的登录信息->end*/
		LoginUser loginUser=com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		if(loginUser!=null){
			SysUserCacher.removeTimeOutUser(loginUser.getId());
		}
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(
					false, msg, msgId);
			res.setObj("anonymous");
			String result = new JsonDataServiceImpl<SysUser>()
					.getBeanToJsonData(res);
			pw.write(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	private boolean saveSessionUser(HttpServletRequest request,
			HttpServletResponse response, String token, String appKey,
			String deviceId) {
		try {
			SysUser sysUser = new SysUser();
			sysUser.setIsDeleted(0);
			sysUser.setToken(token);
			sysUser.setAppKey(appKey);
			sysUser.setMediaType(new Integer(deviceId));
			List<SysUser> list = sysUserService.getList(sysUser);
			if (list != null && list.size() == 1) {
				SysUser user = list.get(0);
				if(user != null){
					LoginUser loginUser = new LoginUser();
					loginUser.setId(user.getId());
					loginUser.setLoginId(user.getSysCode());
					loginUser.setLoginName(user.getNickName());
					loginUser.setLoginEmail(user.getEmail());
					loginUser.setName(user.getUserName());
					loginUser.setUserType(user.getUserType());
					loginUser.setGroupId(user.getGroupId());
					loginUser.setSex(user.getSex());
					ResPic resPic = null;
					if( user.getPicId() != null && user.getPicId() != 0 ){
						resPic = resPicService.get(Integer.valueOf(user.getPicId()));
					}
					String picPath = "";
					if( resPic != null ){
						picPath = Utils.getValue("app.resources.url","http://localhost:89")+resPic.getPicPath();
					}else{
						if(user.getSex()==null || user.getSex() == 1 ){//男
							picPath = request.getContextPath() + "/pages/online/images/user/manIcon.jpg";
						}else if( user.getSex() == 2 ){//女
							picPath = request.getContextPath() + "/pages/online/images/user/womenIcon.jpg";
						}
					}
					loginUser.setPicPath(picPath);
					request.getSession().setAttribute("loginUser", loginUser);
					logger.info("loginUser =" + com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request));
					logger.info("userid =" + (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)).getLoginName());
					return true;
				}else{
					logger.info("user is null.....");
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			logger.info("token:"+token);
		}
		logger.info("loginUser error=");
		return false;
	}
	
	private void dealWithErrorForApp(HttpServletRequest request, HttpServletResponse response, String msgId,
			String msg, String userKey ) {
	   
	    //PC端退出登录接口会从缓存中查询信息记录用户退出信息，所以异常退出时userKey设为null,不清除用户缓存
		if (userKey != null) {
			SysUserCacher.removeCacheUserByUserkey(userKey);
		}
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(false, msg, msgId);
			res.setObj("anonymous");
			String result = new JsonDataServiceImpl<SysUser>().getBeanToJsonData(res);
			pw.write(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	public void output(String jsonStr,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		// out.println();
		out.write(jsonStr);
		out.flush();
		out.close();
		
	}
}
