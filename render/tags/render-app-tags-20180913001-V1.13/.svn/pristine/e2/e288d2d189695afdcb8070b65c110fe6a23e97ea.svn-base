package com.nork.aop;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nork.common.model.LoginUser;
import com.nork.customer.model.CustomerOperateLog;
import com.nork.customer.service.CustomerOperateLogService;
import com.sandu.common.LoginContext;

/**
 * 获取用户的操作行为日志 a.记录每个用户查看每个方案的次数 c.分别记录查看每个商品详情的次数
 * 
 * @author Sandu
 */
@Component
@Aspect
public class CustomerOperateLogAspect {

	private final static Logger log = LogManager.getLogger(CustomerOperateLogAspect.class);

	@Autowired
	private CustomerOperateLogService customerOperateLogService;

	// 查看方案详情
	public static final String DESIGN_PLAN_VIEW_URL = "/v1/miniprogram/render/renderpic/getrenderres";

	/**
	 * 数据埋点
	 * 
	 */
	@Pointcut("execution(public * com.nork.render.controller.ResRenderPicController.*(..))")
	public void printWebRequest() {
	}

	@Before("printWebRequest()")
	public void printRequestLog(JoinPoint joinPoint) {
		printRequestDetail(joinPoint);
	}

	@After("printWebRequest()")
	public void printCallServiceLog(JoinPoint joinPoint) {
		// 开始插入数据库
		addCustomerOperateLog(joinPoint);
	}

	private void printRequestDetail(JoinPoint joinPoint) {
		// 写入操作日志
		// 更新积分规则
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getRequestURL().toString();
		String method = request.getMethod();
		String ip = request.getRemoteAddr();
		String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		String args = Arrays.toString(joinPoint.getArgs());
		String requestUri = request.getRequestURI();
		// 记录下请求内容
		log.info("URL:{"+url+"},METHOD:{"+method+"},IP:{"+ip+"},CLASS_METHOD:{"+classMethod+"},ARGS:{"+args+"},requestUri{"+requestUri+"}");
	}

	/**
	 * 写入操作日志
	 * 只记录查看方案详情和查看商品详情的请求地址
	 * @param joinPoint
	 */
	private void addCustomerOperateLog(JoinPoint joinPoint) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        log.info("addCustomerOperateLog方法获取loginUser:{"+loginUser+"}");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getRequestURL().toString();
		String method = request.getMethod();
		String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "."+ joinPoint.getSignature().getName();
		String args = Arrays.toString(joinPoint.getArgs());
		String requestUri = request.getRequestURI();
		// 记录下请求内容
		log.info("URL:{"+url+"},METHOD:{"+method+"},CLASS_METHOD:{"+classMethod+"},ARGS:{"+args+"},requestUri{"+requestUri+"}");
		//只记录查看方案详情和查看商品详情的请求地址
		if(requestUri.indexOf(DESIGN_PLAN_VIEW_URL)!=-1) {
//			loginUser = new LoginUser();
//			loginUser.setId(1);
	        if (loginUser != null) {
	        	try {
	        		String businessId = "";
		        	Byte operateType = null;
		        	//记录方案
		        	if(requestUri.indexOf(DESIGN_PLAN_VIEW_URL)!=-1) {
		        		businessId = request.getParameter("designPlanRecommendedId");//获取方案id
		        		operateType = 1;
		        	}
		        	if(StringUtils.isNotBlank(businessId)) {
		        		CustomerOperateLog log = new CustomerOperateLog();
			        	log.setUserId(Long.valueOf(loginUser.getId()));
			        	log.setOperateType(operateType);
			        	log.setCreator(String.valueOf(loginUser.getId()));
			        	log.setGmtCreate(new Date());
			        	log.setModifier(String.valueOf(loginUser.getId()));
			        	log.setGmtModified(new Date());
			        	log.setRequestUrl(url);
			        	log.setIpAddress(request.getRemoteAddr());
			        	log.setBusinessId(Integer.parseInt(businessId));
			        	customerOperateLogService.addCustomerOperateLog(log);
		        	}
	        	}catch(Exception e) {
	        		log.info("写入日志报错,错误信息{}",e);
	        	}
	        	
	        }
		}
	}
}