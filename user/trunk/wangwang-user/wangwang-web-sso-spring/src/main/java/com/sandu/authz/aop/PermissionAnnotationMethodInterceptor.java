package com.sandu.authz.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.cache.RedisManager;

public class PermissionAnnotationMethodInterceptor  {

	private RedisManager redisManager;
	private PermissionAnnotationHandler handler;
	public PermissionAnnotationMethodInterceptor(RedisManager redisManager) {
		handler = new PermissionAnnotationHandler(redisManager);
	}
	
    public RedisManager getRedisManager() {
		return redisManager;
	}
    
	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}


	public void interceptPermissionCheckMethod(JoinPoint pjp) throws Throwable {
    	Object obj = pjp.getArgs();
    	Signature sig = pjp.getSignature();
        MethodSignature msig =  (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    	Annotation a = AnnotationUtils.findAnnotation(currentMethod, RequiresPermissions.class);
    	this.handler.assertAuthorized(a);
    }
    
   

}
