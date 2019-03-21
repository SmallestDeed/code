package com.nork.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sandu.common.LoginContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.utils.JedisUserUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.jwt.Jwt;
import com.nork.common.jwt.TokenState;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.system.cache.SysUserCacher;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysUserService;

import net.minidev.json.JSONObject;

public class MyFilter implements Filter {

	private static Logger logger = Logger.getLogger(MyFilter.class);
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
		return exceptUrl == null ? "" : exceptUrl.replace("\r", "").replace("\n", "").replace("\t", "").trim();
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
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		sysUserService = wac.getBean(SysUserService.class);

		resPicService = wac.getBean(ResPicService.class);

		// 创建线程池
		// ThreadPoolManager tpm = ThreadPoolManager.getSingleton();
		// ThreadPool threadPool = tpm.getThreadPool();
		// tpm.init();

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

		if (request.getHeader("MediaType") != null && ("5".equals(request.getHeader("MediaType").toString())
				|| "6".equals(request.getHeader("MediaType").toString()))) {
			String currentPath = request.getServletPath();
			String pathInfo = request.getPathInfo();
			if (StringUtils.length(pathInfo) > 0) {
				currentPath = currentPath + pathInfo;
			}
			String[] str = getExceptUrl().split(",");
			for (String s : str) {
				String ss = (s == null ? "" : s.trim());
				if (StringUtils.isEmpty(ss)) {
					logger.error(" exceptUrl is exists null value!");
				}
				if (StringUtils.isNotEmpty(ss) && currentPath.indexOf(ss) != -1) {
					logger.debug("error1:currentURL=" + currentURL + " 允许无登录通过,currentPath=" + currentPath
							+ ",exceptUrl=" + s);
					if (s.indexOf("/js/") != -1 || s.indexOf("/css/") != -1) {
						request.getRequestDispatcher(currentPath).forward(arg0, arg1);
						return;
					}
					arg2.doFilter(request, response);
					return;
				}

			}
			// 其他API接口一律校验token
			// 从请求头中获取token
			JSONObject outputMSg = new JSONObject();
			String token = request.getHeader("Authorization");
			Map<String, Object> resultMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) resultMap.get("state"));

			boolean stateFlag = false;
			switch (state) {
			case VALID:
				// 取出payload中数据,放入到request作用域中
				request.setAttribute("AuthorizationData", resultMap.get("data"));
				stateFlag = true;
				// 放行
				/* arg2.doFilter(request, response); */
				break;
			case EXPIRED:
				logger.error("token已过期");
				// token过期或者无效，则输出错误信息返回
				outputMSg.put("success", false);
				outputMSg.put("message", "登录超时，请重新登录");
				output(outputMSg.toJSONString(), response);
				break;
			case INVALID:
				logger.error("无效的token");
				// token过期或者无效，则输出错误信息返回
				outputMSg.put("success", false);
				outputMSg.put("message", "登录超时，请重新登录");
				output(outputMSg.toJSONString(), response);
				break;
			}
			if (!stateFlag) {
				return;
			}
			// 校验同一时刻只能同一用户登录
			boolean checkResult = false;
			try {
				checkResult = checkOneLogin(arg2, request, response, outputMSg, resultMap);
			} catch (Exception e) {
				logger.info("checkOneLogin Error:" + e.getMessage());
				System.out.println("checkOneLogin Error:" + e.getMessage());
			}
			if (checkResult) {
				// 放行
				arg2.doFilter(request, response);
			}

			return;

		}

		/** APP端验证 **/
		// 对不需要认证的url直接放行
		String currentMsgId = request.getParameter("msgId");
		String currentPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		LoginUser loginUserInfo = null;
		if (StringUtils.length(pathInfo) > 0) {
			currentPath = currentPath + pathInfo;
		}

		logger.error("currentPath==================" + currentPath);
		String[] str = getExceptUrl().split(",");
		for (String s : str) {
			String ss = (s == null ? "" : s.trim());
			if (StringUtils.isEmpty(ss)) {
				logger.error(" exceptUrl is exists null value!");
			}
			if (StringUtils.isNotEmpty(ss) && currentPath.indexOf(ss) != -1) {
				logger.debug("error1:currentURL=" + currentURL + " 允许无登录通过,currentPath=" + currentPath
						+ ",exceptUrl=" + s);
				if (s.indexOf("/js/") != -1 || s.indexOf("/css/") != -1) {
					request.getRequestDispatcher(currentPath).forward(arg0, arg1);
					return;
				}
				arg2.doFilter(request, response);
				return;
			}

		}

		if (currentURL.indexOf("websocket") != -1) {
			// logger.error("websocket request" + currentURL);
			arg2.doFilter(request, response);
			return;
		}

		if (StringUtils.isBlank(authorization) && currentURL.lastIndexOf(".htm") != -1) {
			logger.error("authorization is null" + currentURL);
			dealWithErrorForApp(request, response, currentMsgId, "登录超时，请重新登录", null);
			return;
		}

		// debug 模式直接放行
		if (StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)) {
			logger.error("IS_DEBUG url==>" + IS_DEBUG + "msgId=" + currentMsgId + ";requestUrl=" + currentURL);
			arg2.doFilter(request, response);
			return;
		}
		if (StringUtils.isNotBlank(authorization) && currentURL.lastIndexOf(".htm") != -1) {
				Map<String, Object> resultMap = Jwt.validToken(authorization);
				TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
				request.setAttribute("mediaType" , "3");
				boolean stateFlag = false;
				switch (state) {
				case VALID:
					// 取出payload中数据,放入到request作用域中
					request.setAttribute("AuthorizationData", resultMap.get("data"));
					stateFlag = true;
					/*
					 * // 放行 arg2.doFilter(request, response);
					 */
					break;
				case EXPIRED:
					logger.error("APP token is EXPIRED" + "msgId=" + currentMsgId + ";requestUrl=" + currentURL);
					// token过期或者无效，则输出错误信息返回
					dealWithErrorForApp(request, response, currentMsgId, "登录超时，请重新登录", loginUserInfo.getAppKey());
					break;
				case INVALID:
					logger.error("APP token is INVALID!" + "msgId=" + currentMsgId + ";requestUrl=" + currentURL);
					dealWithErrorForApp(request, response, currentMsgId, "登录超时，请重新登录", loginUserInfo.getAppKey());
					break;
				}

				// 放行
				arg2.doFilter(request, response);
				return;
		}

		arg2.doFilter(request, response);
		return;
	}

	private boolean checkOneLogin(FilterChain arg2, HttpServletRequest request, HttpServletResponse response,
			JSONObject outputMSg, Map<String, Object> resultMap) throws IOException, ServletException {

		boolean checkResult = true;
		logger.info("resultMap:" + resultMap);
		JSONObject dataObj = (JSONObject) resultMap.get("data");
		String appkey = (String) dataObj.get("appKey");
		// 移动端登陆标识
		String loginFlag = SystemCommonConstant.LOGIN_FROM_MOBILE;
		if (StringUtils.isBlank(appkey)) {
			logger.error("token信息缺失appkey");
			outputMSg.put("success", false);
			outputMSg.put("message", "token信息缺失appkey");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}


//		LoginUser user = new LoginUser();
//		user = (LoginUser) JedisUtils2.getObject(loginFlag + appkey);
		LoginUser user = LoginContext.getLoginUser(LoginUser.class);
		if (user == null) {
			logger.error("user为空");
			// 同一账号在另一处登录时会将之前用户的缓存移去
			outputMSg.put("success", false);
			outputMSg.put("CheckOneLogin", true);
			outputMSg.put("message", "当前账号已在其他设备登录，请重新登录");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}


//		LoginUser loginUser = new LoginUser();
//		loginUser = (LoginUser) JedisUtils2.getObject(loginFlag + user.getId().toString());
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null || loginUser.getAppKey() == null) {
			logger.error("loginUser为空loginUser:" + loginUser);
			outputMSg.put("success", false);
			outputMSg.put("CheckOneLogin", true);
			outputMSg.put("message", "您的登录信息已过期，请重新登录！");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}

		if (!appkey.equals(loginUser.getAppKey())) {
			logger.error("appkey不一致，强制退出 loginUser:" + loginUser);
			// 移除之前登录用户的缓存

//			JedisUtils2.remove((loginFlag + appkey));
			LoginContext.removeUserCache(loginFlag + appkey);
			outputMSg.put("success", false);
			outputMSg.put("CheckOneLogin", true);
			outputMSg.put("message", "当前账号已在其他设备登录，请重新登录");
			output(outputMSg.toJSONString(), response);
			checkResult = false;
			return checkResult;
		}

		return true;
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}


	private void dealWithErrorForApp(HttpServletRequest request, HttpServletResponse response, String msgId, String msg,
			String userKey) {
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

	public void output(String jsonStr, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		// out.println();
		out.write(jsonStr);
		out.flush();
		out.close();

	}
	
}
