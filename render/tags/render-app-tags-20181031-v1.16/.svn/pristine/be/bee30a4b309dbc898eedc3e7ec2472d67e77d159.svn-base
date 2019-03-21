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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nork.common.jwt.Jwt;
import com.nork.common.jwt.TokenState;
import com.nork.common.util.Utils;

import net.minidev.json.JSONObject;

public class MyFilter  implements Filter {
	
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

//	private SysUserService sysUserService;
//	
//	
//	private ResPicService resPicService;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		exceptUrl = filterConfig.getInitParameter("exceptUrl");
		loginUrl = filterConfig.getInitParameter("loginUrl");
		loginWebUrl = filterConfig.getInitParameter("loginWebUrl");
		mobileLoginUrl = filterConfig.getInitParameter("mobileLoginUrl");
		
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
					logger.debug("error1:currentURL=" + currentURL + " 允许无登录通过,currentPath="+currentPath+",exceptUrl="+s);
					if(s.indexOf("/js/") != -1 || s.indexOf("/css/") != -1){
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
				/*arg2.doFilter(request, response);*/
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
			if(!stateFlag) {
			  return;
			}
		arg2.doFilter(request, response);
		return;
	}


	@Override
	public void destroy() {
		filterConfig = null;
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
