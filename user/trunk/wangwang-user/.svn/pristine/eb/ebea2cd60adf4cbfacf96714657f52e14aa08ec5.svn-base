package com.sandu.authz.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import com.sandu.authz.annotation.FieldsFilter;
import com.sandu.cache.RedisManager;

public class FieldsFilterAnnotationMethodInterceptor  {

	private RedisManager redisManager;
	private FieldsFilterAnnotationHandler handler;
	public FieldsFilterAnnotationMethodInterceptor(RedisManager redisManager) {
		handler = new FieldsFilterAnnotationHandler(redisManager);
	}
	
    public RedisManager getRedisManager() {
		return redisManager;
	}
    
	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}


	public Object interceptFieldFilterMethod(ProceedingJoinPoint pjp) throws Throwable {
    	Signature sig = pjp.getSignature();
        MethodSignature msig =  (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    	Annotation a = AnnotationUtils.findAnnotation(currentMethod, FieldsFilter.class);
    	Object retObj = pjp.proceed();
    	return this.handler.doFilter(a,retObj);
    }
    
   

}
