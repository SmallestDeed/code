package com.sandu.util;

import com.sandu.api.user.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.systemutil.BaseModel;
import com.sandu.systemutil.Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * @author Sandu
 * @ClassName FetchLoginUser
 * @date 2018/11/6
 */

@Aspect
@Component
public class FetchLoginUser {
	@Pointcut(value = "execution(public * com.sandu.web..*Controller.*(..))" +
			"&& @target(org.springframework.web.bind.annotation.RestController)" +
			"&& args(com.sandu.systemutil.BaseModel,..)")

	public void loginUserPoint() {
	}

	@Before(value = "loginUserPoint()")
	private void doBefore(JoinPoint joinPoint) {
		Stream.of(joinPoint.getArgs())
				.filter(it -> it instanceof BaseModel)
				.findFirst()
				.ifPresent(it -> {
					LoginUser user = LoginContext.getLoginUser(LoginUser.class);
//					user = new LoginUser();
//					user.setLoginName("test");
//					user.setId(-1L);
//					user.setName("test Name");
					if (user == null) {
						throw new IllegalStateException("请登录!");
					}
					((BaseModel) it).setLoginUser(user);
					Utils.sysSave(it, user);
				});
	}

}
