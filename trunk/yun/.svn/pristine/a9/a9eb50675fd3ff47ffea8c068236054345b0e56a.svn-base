package com.sandu.common.filter;

import com.sandu.common.constant.SystemCommonConstant;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.jwt.TokenState;
import com.sandu.common.util.Utils;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
public class MyFilter  implements Filter {
	
	private static Logger logger = LogManager.getLogger(MyFilter.class);
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
	
//	@Autowired
//	private SysUserService sysUserService;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		exceptUrl = filterConfig.getInitParameter("exceptUrl");
		loginUrl = filterConfig.getInitParameter("loginUrl");
		loginWebUrl = filterConfig.getInitParameter("loginWebUrl");
		mobileLoginUrl = filterConfig.getInitParameter("mobileLoginUrl");
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext());
//		sysUserService = wac.getBean(SysUserService.class);
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		String cacheEnable = Utils.getValue("redisCacheEnable", "0");
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String authorization = "";
		String currentURL = "";
		// jsonStr = Utils.getAuthorization(request);
		currentURL = request.getRequestURI();
		authorization = request.getHeader("Authorization") != null ? request.getHeader("Authorization") : "";
		if (request.getHeader("MediaType") != null
				&& request.getHeader("MediaType").toString().equals(SystemCommonConstant.MEDIA_TYPE_OF_MOBILE)) {
			if (request.getRequestURI().endsWith(mobileLoginUrl)) {
				// 登陆接口不校验token，直接放行
				arg2.doFilter(request, response);
				return;
			}
			// 其他API接口一律校验token
			// 从请求头中获取token
			String token = request.getHeader("Authorization");
			Map<String, Object> resultMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
			JSONObject outputMSg = new JSONObject();
			switch (state) {
			case VALID:
				// 取出payload中数据,放入到request作用域中
				request.setAttribute("AuthorizationData", resultMap.get("data"));
				// 放行
				arg2.doFilter(request, response);
				break;
			case EXPIRED:
				logger.error("token已过期");
				// token过期或者无效，则输出错误信息返回
				outputMSg.put("success", false);
				outputMSg.put("message", "认证失败，请重新登录");
				output(outputMSg.toJSONString(), response);
				break;
			case INVALID:
				logger.error("无效的token");
				// token过期或者无效，则输出错误信息返回
				outputMSg.put("success", false);
				outputMSg.put("message", "认证失败，请重新登录");
				output(outputMSg.toJSONString(), response);
				break;
			}
			return;
		}

		/** APP端验证 **/
		// 对不需要认证的url直接放行
		String currentPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
//		LoginUser loginUserInfo = null;
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
				if (StringUtils.isNotBlank(authorization)) {
//					loginUserInfo = (LoginUser) new JsonDataUtils<LoginUser>().getJsonToBean(authorization,LoginUser.class);
//					String token = loginUserInfo.getToken();
					String token = "";
					Map<String, Object> resultMap = Jwt.validToken(token);
					TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
					request.setAttribute("AuthorizationData", resultMap.get("data"));
				}
				arg2.doFilter(request, response);
				return;
			}

		}
		
		if (currentURL.indexOf("websocket") !=-1) {
			//logger.error("websocket request" + currentURL);
			arg2.doFilter(request, response);
			return;
		}
		
		if (1 == 2 && StringUtils.isBlank(authorization) && currentURL.lastIndexOf(".htm") != -1) {
			logger.error("authorization is null" + currentURL);
			dealWithErrorForApp(request, response, "认证失败，请重新登录", null);
			return;
		}
		
		//debug 模式直接放行
		if(StringUtils.isNotBlank(IS_DEBUG) && "true".equals(IS_DEBUG)){
			logger.error("IS_DEBUG url==>" +IS_DEBUG + ";requestUrl=" + currentURL);
			arg2.doFilter(request, response);
			return;
		}
		if(StringUtils.isNotBlank(authorization) && currentURL.lastIndexOf(".htm") != -1) {
//			loginUserInfo = (LoginUser) new JsonDataUtils<LoginUser>().getJsonToBean(authorization,
//					LoginUser.class);
//			if (loginUserInfo != null && StringUtils.isNoneBlank(loginUserInfo.getToken())) {
//				String token = loginUserInfo.getToken();
//				String mediaType = loginUserInfo.getMediaType();
//				Map<String, Object> resultMap = Jwt.validToken(token);
//				TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
//				request.setAttribute("mediaType", mediaType);
//				switch (state) {
//				case VALID:
//					// 取出payload中数据,放入到request作用域中
//					request.setAttribute("AuthorizationData", resultMap.get("data"));
//					// 放行
//					arg2.doFilter(request, response);
//					break;
//				case EXPIRED:
//					logger.error("APP token is EXPIRED" + ";requestUrl=" + currentURL);
//					// token过期或者无效，则输出错误信息返回
//					dealWithErrorForApp(request, response, "认证失败，请重新登录", loginUserInfo.getAppKey());
//					break;
//				case INVALID:
//					logger.error("APP token is INVALID!" + ";requestUrl=" + currentURL);
//					dealWithErrorForApp(request, response, "认证失败，请重新登录", loginUserInfo.getAppKey());
//					break;
//				}
//				return;
//			}
		}else {
			
		}
		
		arg2.doFilter(request, response);
		return;
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}

	/**
	 * 异常操作(超时,没有权限...)
	 * @param response
	 * @param msg
	 */
	private void dealWithError(
			HttpServletRequest request,HttpServletResponse response, String msg) {
		/*删除session中用户的登录信息*/
		request.getSession().removeAttribute("loginUser");
		/*删除session中用户的登录信息->end*/

//		LoginUser loginUser = getCurrentLoginUserInfo(request);
//		if(loginUser!=null){
////			sysUserService.removeTimeOutUser(loginUser.getId());
//		}
//		PrintWriter pw = null;
//		try {
//			pw = response.getWriter();
//			ResponseEnvelope res = new ResponseEnvelope(false, msg);
//			res.setObj("anonymous");
//			String result = new JsonDataUtils<SysUser>()
//					.getBeanToJsonData(res);
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
	}

	private void dealWithErrorForApp(HttpServletRequest request, HttpServletResponse response,
			String msg, String userKey) {
		if (userKey != null) {
//			sysUserService.removeCacheUserByUserkey(userKey);
		}
//		PrintWriter pw = null;
//		try {
//			pw = response.getWriter();
//			ResponseEnvelope res = new ResponseEnvelope(false, msg);
//			res.setObj("anonymous");
//			String result = new JsonDataUtils<SysUser>().getBeanToJsonData(res);
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
	}

	private void output(String jsonStr,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
		
	}
}
