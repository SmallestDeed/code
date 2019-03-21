package com.nork.common.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.cache.CacheManager;
import com.sandu.common.LoginContext;
import net.minidev.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.nork.common.jwt.Jwt;
import com.nork.common.jwt.TokenState;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyFilter implements Filter{

	private static Logger logger = LogManager.getLogger(MyFilter.class);
	private final static String CLASS_LOG_PREFIX = "[过滤器]:";

	private FilterConfig filterConfig;
	private String exceptUrl;

	public String getExceptUrl() {
		return exceptUrl==null?"":exceptUrl.replace("\r", "").replace("\n", "").replace("\t", "").trim();
	}

	public void setExceptUrl(String exceptUrl) {
		this.exceptUrl = exceptUrl;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		exceptUrl = filterConfig.getInitParameter("exceptUrl");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
						 FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		//判断是否免登陆
		String currentPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		if (StringUtils.length(pathInfo) > 0) {
			currentPath = currentPath + pathInfo;
		}
		String[] str = getExceptUrl().split(",");
		for (String s : str) {
			String ss = (s == null ? "" : s.trim());
			if (StringUtils.isNotEmpty(ss) && currentPath.indexOf(ss) != -1) {
				request.getRequestDispatcher(currentPath).forward(arg0, arg1);
				return;
			}
		}


//		//从请求头中获取token
		if(StringUtils.isNotEmpty(request.getHeader("isRenderServer"))) {
			String token = request.getHeader(Constants.REQUESTHEADER_AUTHORIZATION);
			//解析token
			Map<String, Object> dataMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) dataMap.get("state"));
			//判断token是否超时
			boolean stateFlag = false;
			switch (state) {
				case VALID:
					// 取出payload中数据,放入到request作用域中
					request.setAttribute("AuthorizationData", dataMap.get("data"));
					stateFlag = true;
					break;
				case EXPIRED:
					logger.error("token已过期");
					dataMap.put("success", false);
					resposeMessageToCustomer(response,"请登陆");
					break;
				case INVALID:
					logger.error("无效的token");
					// token过期或者无效，则输出错误信息返回
					resposeMessageToCustomer(response,"请登陆");
					break;
			}
		}


//		LoginUser jsonUser = getUserInfoToCache(signflat + appkey);
//		LoginUser jsonUser = LoginContext.getLoginUser(LoginUser.class);
//		if(jsonUser == null){
//			resposeMessageToCustomer(response,"登录超时");
//			return;
//		}
		arg2.doFilter(request, response);
		return;
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}
	
	public void resposeMessageToCustomer(HttpServletResponse response,String message){
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(message);
		} catch (IOException e) {
			logger.info("返回数据异常："+message);
			e.printStackTrace();
		}
	}

	public LoginUser getUserInfoToCache(String redisKey){
		return (LoginUser) CacheManager.getInstance().getCacher().getObject(redisKey);
	}
}