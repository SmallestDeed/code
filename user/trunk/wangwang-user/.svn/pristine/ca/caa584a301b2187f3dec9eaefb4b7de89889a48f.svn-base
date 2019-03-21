package com.sandu.authz.aop;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sandu.common.LoginContext;

@Aspect
@Component
public class SessionTimeoutResetAspect {
	private static Logger logger = LoggerFactory.getLogger(SessionTimeoutResetAspect.class);
	@Before("execution(* com.sandu..controller..*.*(..)) || execution(* com.nork..controller..*.*(..))")
	public void sessionTimeoutResetMethod(JoinPoint point) throws Throwable {
		try {
			Map tokenDataMap = LoginContext.getTokenData();
			//如果已经登录,则更新,否则则表示是未登录状态的浏览,不需要做任何操作
			if(tokenDataMap!=null) {
				String signflat = (String)tokenDataMap.get("signflat");
				String uid =  tokenDataMap.get("uid").toString();
				String appKey = (String)tokenDataMap.get("appKey");
				int sessionTimeout = Double.valueOf(tokenDataMap.get("sessionTimeout").toString()).intValue();
				LoginContext.getRedisManager().expire(signflat+uid, sessionTimeout);
				LoginContext.getRedisManager().expire(signflat+appKey, sessionTimeout);
			}
		}catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		
    }
}
