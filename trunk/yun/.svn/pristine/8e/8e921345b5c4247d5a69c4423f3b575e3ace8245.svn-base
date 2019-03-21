package com.sandu.common.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysDictionaryInterceptor  implements ThrowsAdvice{
	public static final Logger log = LogManager.getLogger(SysDictionaryInterceptor.class);

	/*
	 * 定义pointcunt
	 */
	public SysDictionaryInterceptor() {
		log.info("初始化切面...");
	}

	/**
	 * 环绕装备 用于拦截查询 如果缓存中有数据，直接从缓存中读取；否则从数据库读取并将结果放入缓存
	 * 
	 * @param call
	 * @param name
	 * @return
	 */
/*	@Around("aPointcut()")
	public Integer aroundAdd(ProceedingJoinPoint call) {
		SysDictionary d = (SysDictionary) call.getArgs()[0]; 
		Integer id = null;
		try {
			id = (Integer) call.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return id;
	}*/
	
	/*	//前置通知
		@Before(value="execution(* com.sandu.system.service.impl.SysDictionaryServiceImpl.update*(..))")
	    public void before() {
			log.info("已经记录下操作日志@Before 方法执行前");
	    }

		//环绕通知
	    @Around(value="execution(* com.sandu.system.service.impl.SysDictionaryServiceImpl.update*(..))")
	    public void around(ProceedingJoinPoint pjp) throws Throwable{
	    	log.info("已经记录下操作日志@Around 方法执行前");
	        pjp.proceed();
	        log.info("已经记录下操作日志@Around 方法执行后");
	    }
	    
	    //后置通知
	    @After(value="execution(* com.sandu.system.service.impl.SysDictionaryServiceImpl.update*(..))")
	    public void after(JoinPoint joinPoint) {
	    	log.info("已经记录下操作日志@After 方法执行后");
	    }
	    
	    //获取返回后通知
	    @AfterReturning(value="execution(* com.sandu.system.service.impl.SysDictionaryServiceImpl.update*(..))",returning="retVal")
	    public void doAfterReturning(JoinPoint jp,String retVal){
	        //////System.out.println("后置返回值通知，获得参数："+retVal);
	    }
	    
	    //异常通知
	    @AfterThrowing(value="execution(* com.sandu.system.service.impl.SysDictionaryServiceImpl.update*(..))",throwing="ex")
	    public void afterThrowing(Throwable ex){
	        //////System.out.println("抛出异常通知"+ex);
	    }*/
}
