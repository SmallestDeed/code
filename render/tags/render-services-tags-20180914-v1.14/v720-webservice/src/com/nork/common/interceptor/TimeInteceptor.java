package com.nork.common.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Util;
import com.nork.common.cache.CacheManager;
import com.nork.common.util.Utils;
import com.nork.pay.alipay.util.UtilDate;
import com.nork.system.model.SysUserSystemOperationLog;
import com.nork.system.service.SysUserSystemOperationLogService;

public class TimeInteceptor implements HandlerInterceptor {
	private static Logger logger = Logger.getLogger(TimeInteceptor.class);
	@Autowired
	private SysUserSystemOperationLogService sysUserSystemOperationLogService;
	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行
	 * ，而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的
	 * ，这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
	/*	response.setHeader("Access-Control-Allow-Origin", "*");  
		response.setHeader("Access-Control-Allow-Methods", "*");  
		response.setHeader("Access-Control-Max-Age", "3600");  
		response.setHeader("Access-Control-Allow-Headers",  
		"Origin, X-Requested-With, Content-Type, Accept");  */
		return true;
	}

	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
	 * 它的执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
	 * ，也就是说在这个方法中你可以对ModelAndView进行操
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用
	 * ，这跟Struts2里面的拦截器的执行过程有点像，
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法
	 * ，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前
	 * ，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String currentURL = request.getRequestURI();
		String authorization = (String) request.getHeader("Authorization");
		String ContentType = (String) request.getHeader("Content-Type");
		String userAgent = (String) request.getHeader("User-Agent");
		// String requestInputStream = Utils.getJsonStr(request);
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
		String msgId = request.getParameter("msgId");
		String uuid = request.getParameter("uuId");
		if (isHasCondition) {
			currentURL += condition.toString();
		}

		currentURL = ""
				+ ((currentURL.lastIndexOf("&") == currentURL.length() - 1) ? currentURL
						.substring(0, currentURL.length() - 1) : currentURL);
		
		String newcurrentURL = "currentURL:authorization=" + authorization
				+ ";ContentType=" + ContentType + ";User-Agent=" + userAgent
				+ ";>>>>>>>requestURI =" + currentURL + "";

		// TODO Auto-generated method stub
		long startTime = (Long) request.getAttribute("startTime");
		
		long endTime = System.currentTimeMillis();
		
		request.setAttribute("endTime", endTime);
		long executeTime = endTime - startTime;
		String log = Utils.getValue("userOperationLog", "");
		if(("1").equals(log)){
		
		//获取模块名称
		String[] split = currentURL.split("/");  
		//获得参数
		int size1 = currentURL.indexOf("?");
		String newString2 = "";
		if(size1>0){
		   int index = currentURL.lastIndexOf("?");  
		   newString2 = currentURL.substring(index+1);  
		}
		//获得接口名称
		int index = currentURL.lastIndexOf("/");  
		String newString = currentURL.substring(index + 1);  
		String d = newString.substring(0, newString.lastIndexOf("?")); //
		//URL地址
		 String str=currentURL.substring(0,currentURL.lastIndexOf("?"));
		if(!d.equals("sysUserQuit.htm") && !d.equals("uploadFile.htm")){
			SysUserSystemOperationLog sso = new SysUserSystemOperationLog();
			sso.setOperationUserId(   Integer.parseInt(CacheManager.getInstance().getCacher().get("userIdLog")));	//用户Id
			sso.setOperationLoginTime(UtilDate.ConverToDate(CacheManager.getInstance().getCacher().get("loginTimeLog"))); //登录时间
			sso.setUserMobile(CacheManager.getInstance().getCacher().get("mobileLog"));				//手机,拿过去命名
			sso.setOperationClientIp(CacheManager.getInstance().getCacher().get("clientIp"));		//客户端IP
			sso.setOperationLoginDevice(CacheManager.getInstance().getCacher().get("operationLoginDevice"));  //登录设备
			sso.setOperationSystemModel(CacheManager.getInstance().getCacher().get("operationSystemModel"));  //设备型号
			if(split[4]!=null){
				sso.setOperationModuleName(split[4]);		//模块名称
			}
			sso.setOperationUrlAddress(str);			//URL地址
			sso.setOperationBusiness(d);    			//操作业务
			if(newString2.length() > 1900) {
				sso.setOperationParameter("接口参数字符串长度为:"+newString2.length()+",不存储");		//接口参数
			}else {
				sso.setOperationParameter(newString2);	
			}
			sso.setOperationPortTime(executeTime+"ms");  //接口耗时
			sso.setMsgId(msgId);//MsgId'
			sso.setUuId(uuid); //UUID
			sysUserSystemOperationLogService.txtSaveOperationLog(sso);
			logger.debug("时间:"+UtilDate.getStringDate()+"接口URL:"+currentURL+"耗时:"+executeTime+"MS");
			}
		}
		if(executeTime>3000){
			logger.info("!!!!!!WARNING!!!!!!...  url executeTime > 3S   [" + handler + "][" + newcurrentURL
					+ "] postHandle executeTime : " + executeTime + "ms");
		}else{
			logger.warn("[" + handler + "][" + newcurrentURL
					+ "] postHandle executeTime : " + executeTime + "ms");
		}
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// logger.info( "-=-=--=--=-=-=-=-=-=-=-=-==-=--=-=-=-=" +ex);
		long endTime = 0;
		if(null!=request.getAttribute("endTime")){
			
			endTime = (Long) request.getAttribute("endTime");
		}

		long disendTime = System.currentTimeMillis();
        long startTime = (Long) request.getAttribute("startTime");
        long executeTime = disendTime - startTime;
		if (logger.isDebugEnabled()) {
			logger.warn("[" + handler==null?"":handler + "] afterCompletion executeTime : "
					+ executeTime + "ms");
		}
	}

}
