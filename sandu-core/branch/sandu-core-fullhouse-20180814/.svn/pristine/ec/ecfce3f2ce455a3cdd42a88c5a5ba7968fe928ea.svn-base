package com.sandu.interceptor;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sandu.api.banner.common.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.api.base.common.ResponseEnvelope;



public class LoginUserInterceptor implements HandlerInterceptor{

    private final static Logger logger  = LoggerFactory.getLogger(LoginUserInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String requestStr = request.getServletPath();
    	//不需要登录就能访问
        if (requestStr.contains("/v1/core")) {
            return true;
        }
    	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
    	if(null == loginUser){
            resposeMessageToCustomer(response,"请登陆");
            return false;
        }
        return true;
    }
      

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public void resposeMessageToCustomer(HttpServletResponse response,String message){
        try {
            response.setContentType("application/json;charset=utf-8");
            ResponseEnvelope<Object> responseEnvelope = new ResponseEnvelope<>(false, message);
            Gson gson = new Gson();
            String jsonString = gson.toJson(responseEnvelope);
            response.getWriter().write(jsonString);
        } catch (IOException e) {
        	logger.error("返回数据异常："+message);
        }
    }


}
