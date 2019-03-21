package com.sandu.im.interceptor;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sandu.common.LoginContext;
import com.sandu.im.common.LoginUser;
import com.sandu.im.common.ResponseEnvelope;
import com.sandu.im.common.exception.ErrorCode;


public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            logger.info("进入options方法");
            return true;
        }
        
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            loginTip(response);
            return false;
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {
    }

    public void loginTip(HttpServletResponse response) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ResponseEnvelope responseEnvelope = new ResponseEnvelope(ErrorCode.NOT_LOGIN);
            Gson gson = new Gson();
            String jsonString = gson.toJson(responseEnvelope);
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            logger.error("返回数据异常：",e);
        }
    }


}
