package com.sandu.authz.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import com.sandu.cache.RedisManager;

@Aspect
public class FieldsFilterAspect {

	private RedisManager redisManager;

	private FieldsFilterAnnotationMethodInterceptor fieldsFilterAnnotationMethodInterceptor;
	public void init() {
		fieldsFilterAnnotationMethodInterceptor = new FieldsFilterAnnotationMethodInterceptor(redisManager);
	}
	
	@Pointcut("@annotation(com.sandu.authz.annotation.FieldsFilter)")
    public void fieldsFilter() {
    }

	@Around("fieldsFilter()")
    public Object fieldFilterMethod(ProceedingJoinPoint pjp) throws Throwable {
		return fieldsFilterAnnotationMethodInterceptor.interceptFieldFilterMethod(pjp);
    }
	
	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
    
}
