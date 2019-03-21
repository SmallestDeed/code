package com.sandu.authz.aop;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.sandu.authz.AuthorizationException;
import com.sandu.cache.RedisManager;

@Aspect
public class PermissionCheckAspect {
	
	private Logger logger = LoggerFactory.getLogger(PermissionCheckAspect.class);
	private RedisManager redisManager;
	private PermissionAnnotationMethodInterceptor permissionAnnotationMethodInterceptor;
	private static final String RESPONSE_CHARSET = "UTF-8";
	public void init() {
		permissionAnnotationMethodInterceptor = new PermissionAnnotationMethodInterceptor(redisManager);
	}
	
	@Pointcut("@annotation(com.sandu.authz.annotation.RequiresPermissions)")
    public void permissionCheck() {
    }

	@Before("permissionCheck()")
    public void permissionCheckMethod(JoinPoint point) throws Throwable {
		permissionAnnotationMethodInterceptor.interceptPermissionCheckMethod(point);
    }

	@AfterThrowing(value = "permissionCheck()", throwing = "e")
    public void afterThrowing(Throwable e) {
        if (e instanceof AuthorizationException) {
        	writeToClient(e.getMessage());
        } else {
        	logger.error("系统异常", e);
			writeToClient("系统异常");
        }
    }

	public void writeToClient(String message) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		ServletOutputStream out = null;
		try {
			response.setCharacterEncoding(RESPONSE_CHARSET);
			response.setContentType("text/json;charset=" + RESPONSE_CHARSET);
			out = response.getOutputStream();
			out.write(message.getBytes(RESPONSE_CHARSET));
		} catch (IOException e) {
			logger.error("输出结果异常", e);
		} finally {
			if (null != out) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
    
}
