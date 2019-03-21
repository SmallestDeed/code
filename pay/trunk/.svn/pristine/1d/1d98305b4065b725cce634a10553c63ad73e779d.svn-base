/**
 * 
 */
package com.sandu.common.filter;

import com.sandu.common.util.NetworkUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用来记录请求路径的
 * @author louxinhua
 *
 */
public class TraceLogFilter implements Filter {

	
	private final Logger traceLogLogger = LogManager.getLogger("traceLog");
	
	/**
	 * 表示是否是 app 发起的请求
	 */
	public static final String APP_HEADER = "appHeader";
	
	/**
	 * iOS还是Android
	 */
	public static final String DEVICE_HEADER = "deviceType";
	
	/**
	 * 移动类型头
	 */
	private static final String MOBILE_DEVICE_HEADER = "mobile";
	
	/**
	 * iOS
	 */
	private static final String MOBILE_DEVICE_IOS = "iOS";
	
	/**
	 * ANDROID
	 */
	private static final String MOBILE_DEVICE_ANDROID = "Android";
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		//  Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain)
			throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		//  Auto-generated method stub
		
		HttpServletRequest httpRequest = (HttpServletRequest)arg0;
//		String uri = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		String servletPath = httpRequest.getServletPath();
//		String url = httpRequest.getRequestURL().toString();
		String ip = NetworkUtil.getIpAddress(httpRequest);
		
		
		String webOrApp = null;
		
		String appHeader = httpRequest.getHeader(APP_HEADER);
		String fromDevice = httpRequest.getHeader(DEVICE_HEADER);
		if ( MOBILE_DEVICE_HEADER.equals(appHeader) ) {
			webOrApp = "1";
			if ( MOBILE_DEVICE_IOS.equals(fromDevice) ) {
				
			}
			else {
				fromDevice = MOBILE_DEVICE_ANDROID;
			}
		}
		else {
			fromDevice = "web";
			webOrApp = "0";
		}
		
		// uri | contextPath | servletPath | ip | webOrApp | fromDevice 
		
		String trace = String.format("|%s|%s|%s|%s|%s", contextPath, servletPath, ip, webOrApp, fromDevice);
		
		traceLogLogger.info(trace);
		
		filterChain.doFilter(arg0, arg1);
		traceLogLogger.info(httpRequest.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
		
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//  Auto-generated method stub

	}

}
