package com.sandu.interceptor;


import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginUserInterceptor implements HandlerInterceptor{

    private final static Logger log  = LoggerFactory.getLogger(LoginUserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
		String requestStr = request.getServletPath();

		// 不需要登录也能访问
		if (requestStr.contains("/running/check")) {
			return true;
		}
		
		String method = request.getMethod();
		if (method.equals("OPTIONS")) {
			log.info("进入options方法");
			return true;
		}
		String uri = request.getRequestURI();
		if (uri.indexOf("login") > 0 || uri.indexOf("logout") > 0 || uri.indexOf("getRenderType") > 0
				|| uri.indexOf("getRechargeIntegral") > 0) {
			return true;
		}
		
		//判断用户是否已登录
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
/*if (null == loginUser) {
	resposeMessageToCustomer(response, "请登陆");
	return false;
}*/
		return true;
		
    }
    
    private void resposeMessageToCustomer(HttpServletResponse response,String message){
        try {
            response.setContentType("application/json;charset=utf-8");
            ResponseEnvelope<Object> responseEnvelope = new ResponseEnvelope<>(false, message);
            Gson gson = new Gson();
            String jsonString = gson.toJson(responseEnvelope);
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            log.info("返回数据异常："+message);
            e.printStackTrace();
        }
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


}
