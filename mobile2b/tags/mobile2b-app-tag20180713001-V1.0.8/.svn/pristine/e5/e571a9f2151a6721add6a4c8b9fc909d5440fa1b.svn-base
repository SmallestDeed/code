package com.nork.aop;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SimpleAspect {
	public static final Logger log = Logger
			.getLogger(SimpleAspect.class);
	@Pointcut("execution(* com.nork.*.service.*Service*.*(..))")
	public void pointCut() {
	}

	@After("pointCut()")
	public void after(JoinPoint joinPoint) {
		//////System.out.println("after aspect executed");
	}

	@Before("pointCut()")
	public void before(JoinPoint joinPoint) {
		//如果需要这里可以取出参数进行处理
		Object[] args = joinPoint.getArgs();
        for(int i=0;i<args.length;i++){
            //////System.out.println("参数"+i+":"+args[i]);
        }
		//////System.out.println("before aspect executing");
	}

	@AfterReturning(pointcut = "pointCut()", returning = "returnVal")
	public void afterReturning(JoinPoint joinPoint, Object returnVal) {
		//////System.out.println("afterReturning executed, return result is "+ returnVal);
	}

	@Around("pointCut()")
	public Object  around(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		//////System.out.println("around start..");
		try {
			retVal = pjp.proceed();
		} catch (Throwable ex) {
			//////System.out.println("error in around");
			throw ex;
		}
		
		//////System.out.println("around end");
		return retVal;
	}

	@AfterThrowing(pointcut = "pointCut()", throwing = "error")
	public void afterThrowing(JoinPoint jp, Throwable error) {
		//////System.out.println("error:" + error);
	}
}