package com.sandu.aop;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.sandu.common.LoginContext;
import com.sandu.customer.model.CustomerOperateLog;
import com.sandu.customer.service.CustomerOperateLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取用户的操作行为日志 a.记录每个用户查看每个方案的次数 c.分别记录查看每个商品详情的次数
 * 
 * @author Sandu
 */
@Aspect
@Component
@Slf4j
public class CustomerOperateLogAspect {

	@Autowired
	private CustomerOperateLogService customerOperateLogService;

	// 获取商品详情的url
	public static final String GOODS_DETAIL_URL = "/v1/miniprogram/goods/basegoods/detail";
	// 查看方案详情
	public static final String DESIGN_PLAN_VIEW_URL = "/v1/miniprogram/render/renderpic/getrenderres";

	/**
	 * 数据埋点
	 * 
	 */
	@Pointcut("execution(public * com.sandu.web.render.ResRenderPicController.*(..))|| "
			+ "execution(public * com.sandu.web.goods.controller.BaseGoodsController.*(..)) ")
	public void printWebRequest() {
	}

	@Pointcut("execution(public * com.sandu.web.render.ResRenderPicController.*(..))|| "
			+ "execution(public * com.sandu.web.goods.controller.BaseGoodsController.*(..)) ")
	public void createCustomerOperateLog() {

	}

	@Before("printWebRequest()")
	public void printRequestLog(JoinPoint joinPoint) {
		printRequestDetail(joinPoint);
	}

	@After("createCustomerOperateLog()")
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
		log.info("URL:{},METHOD:{},IP:{},CLASS_METHOD:{},ARGS:{},requestUri", url, method, ip, classMethod, args,
				requestUri);
	}

	/**
	 * 写入操作日志
	 * 只记录查看方案详情和查看商品详情的请求地址
	 * @param joinPoint
	 */
	private void addCustomerOperateLog(JoinPoint joinPoint) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        log.info("addCustomerOperateLog方法获取loginUser:{}",loginUser);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getRequestURL().toString();
		String method = request.getMethod();
		String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "."+ joinPoint.getSignature().getName();
		String args = Arrays.toString(joinPoint.getArgs());
		String requestUri = request.getRequestURI();
		// 记录下请求内容
		log.info("URL:{},METHOD:{},CLASS_METHOD:{},ARGS:{}",url,method,classMethod,args);
		//只记录查看方案详情和查看商品详情的请求地址
		if(requestUri.indexOf(GOODS_DETAIL_URL) != -1 || requestUri.indexOf(DESIGN_PLAN_VIEW_URL)!=-1) {
//			loginUser = new LoginUser();
//			loginUser.setId(1);
	        if (loginUser != null) {
	        	try {
	        		String businessId = "";
		        	Byte operateType = null;
		        	//记录商品详情
		        	if(requestUri.indexOf(GOODS_DETAIL_URL)!=-1) {
		        		businessId = request.getParameter("id");//获取商品id
		        		operateType = 2;
		        	}else if(requestUri.indexOf(DESIGN_PLAN_VIEW_URL)!=-1) {
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