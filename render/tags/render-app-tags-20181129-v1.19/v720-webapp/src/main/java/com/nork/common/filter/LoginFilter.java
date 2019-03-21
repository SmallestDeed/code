//package com.nork.common.filter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Enumeration;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import com.nork.base.service.impl.JsonDataServiceImpl;
//import com.nork.common.model.LoginUser;
//import com.nork.common.model.ResponseEnvelope;
//import com.nork.common.util.Utils;
//import com.nork.system.model.SysUser;
//import com.nork.system.service.SysUserService;
//
//public class LoginFilter implements Filter {
//	private static Logger logger = Logger.getLogger(Filter.class);
//	private final JsonDataServiceImpl JsonUtil = new JsonDataServiceImpl();
//	private FilterConfig filterConfig;
//
//	@Autowired
//	private SysUserService sysUserService;
//	
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		this.filterConfig = arg0;
//		WebApplicationContext wac=WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
// 		sysUserService =wac.getBean(SysUserService.class);
//	}
//
//	@Override
//	public void doFilter(ServletRequest arg0, ServletResponse arg1,
//			FilterChain arg2) throws IOException, ServletException {
//		long startTime = System.currentTimeMillis();
//		HttpServletRequest request = (HttpServletRequest) arg0;
//		HttpServletResponse response = (HttpServletResponse) arg1;
//		String currentURL = request.getRequestURI();
//		logger.info("RequestURI=" + currentURL);
//
//			if (currentURL.lastIndexOf(".htm") > 0
//			|| currentURL.lastIndexOf(".jsp") > 0
//			|| (currentURL.lastIndexOf("/login.") == -1
//			&& currentURL.lastIndexOf("/login") > 0
//			&& currentURL.indexOf("/images/") == -1)) {
//					Enumeration emu = request.getParameterNames();
//					StringBuffer condition = new StringBuffer("?");
//					boolean isHasCondition = false;
//					while (emu.hasMoreElements()) {
//						isHasCondition = true;
//						String paramName = (String) emu.nextElement();
//						condition.append(paramName);
//						condition.append("=");
//						condition.append(request.getParameter(paramName));
//						condition.append("&");
//					}
//		
//					if (isHasCondition) {
//						currentURL += condition.toString();
//					}
//		
//					currentURL = ""
//							+ ((currentURL.lastIndexOf("&") == currentURL.length() - 1) ? currentURL
//									.substring(0, currentURL.length() - 1) : currentURL);
//		
//		
//					logger.info("requestURI="
//							+ ((currentURL.lastIndexOf("&") == currentURL.length() - 1) ? currentURL
//									.substring(0, currentURL.length() - 1) : currentURL));
//		            if(sysUserService==null){
//		            	logger.info("sevice is null");
//		            }else{
//		            	logger.info("sevice is not null");
//		            }
//					//先去session中找，再在http头文件中找
//				
//					    //
//						String jsonStr = Utils.getAuthorization(request);
//						if (jsonStr != null && jsonStr.trim().length() > 0){
//							LoginUser loginUser = (LoginUser)new JsonDataServiceImpl<LoginUser>().getJsonToBean(jsonStr, LoginUser.class);
//							String token =loginUser.getToken(); 
//							String appkey= loginUser.getAppKey();
//							String deviceId = loginUser.getDeviceId();
//							String msgId = loginUser.getMsgId();
//							boolean flag = false;
//							 if(loginUser != null 
//									 && token != null  &&  !"".equals(token)
//									 && appkey != null &&  !"".equals(appkey)
//									 && deviceId != null &&  !"".equals(deviceId) ){
//								 flag  = saveSessionUser(request,response, token,appkey,deviceId);
//							}
//							 if(!flag){
//								 response.setContentType("text/html");
//								 
//								 //StringBuffer resultBuffer=new StringBuffer();
//								 PrintWriter pw=response.getWriter();
//								/* resultBuffer.append("{success:false,");
//								 resultBuffer.append("data:{}}") ;*/
//								 ResponseEnvelope<SysUser> res = new ResponseEnvelope<SysUser>(false, "没有权限访问服务器!",msgId); 
//								 String result =  new JsonDataServiceImpl<SysUser>().getBeanToJsonData(res);
//								 
//							/*	 pw.write(resultBuffer.toString());*/
//								 pw.write(result);
//								 pw.flush();
//								 pw.close();
//								 return;
//							}
//						}else{
//							//存在session，继续进行，不存在则跳到登录页面
//							LoginUser loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
//							if(loginUser == null){
//								response.sendRedirect("/login.jsp");
//								return;
//							}
//						}
//					}
//					arg2.doFilter(request, response);
//					logger.info(request.getRequestURI()+"_req_url_cost_time_"+(System.currentTimeMillis()-startTime));
//	}
//
//
//	@Override
//	public void destroy() {
//		filterConfig = null;
//	}
//
//	private boolean saveSessionUser(HttpServletRequest request,HttpServletResponse response, String token,String appKey,String deviceId) {
//		try {
//			SysUser sysUser = new SysUser();
//			sysUser.setIsDeleted(0);
//			sysUser.setToken(token);
//			sysUser.setAppKey(appKey);
//			sysUser.setMediaType(new Integer(deviceId));
//			List<SysUser> list = sysUserService.getList(sysUser);
//			if(list != null && list.size() == 1){
//				SysUser user = list.get(0);
//
//				LoginUser loginUser =  new LoginUser();
//				loginUser.setId(user.getId());
//				loginUser.setLoginName(user.getNickName());
//				loginUser.setLoginEmail(user.getEmail());
//				loginUser.setName(user.getUserName());
//				loginUser.setUserType(user.getUserType());
//				request.getSession().setAttribute("loginUser", loginUser);
//				return true;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			
//		}
//		return false;
//	}
//}
