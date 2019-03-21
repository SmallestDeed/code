package com.sandu.interceptor;

import com.sandu.common.util.SpringContextHolder;
import com.sandu.user.service.UserSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户会话拦截
 *
 * @date 20171023
 * @auth pengxuangang
 */
public class UserSessionInterceptor extends HandlerInterceptorAdapter {

    private final static String CLASS_LOG_PREFIX = "[用户会话拦截控制]:";
    private final static Logger logger = LoggerFactory.getLogger(UserSessionInterceptor.class);
    //缓存服务
    private static UserSessionService userSessionService = SpringContextHolder.getBean(UserSessionService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //刷新用户会话有效期
        logger.info(CLASS_LOG_PREFIX + "用户会话拦截控制:刷新已登录用户会话--SessionId:{}.", request.getRequestedSessionId());
        boolean refreshStatus = userSessionService.refreshUserSessionIntervalToCache(request.getRequestedSessionId(), request.getSession().getMaxInactiveInterval());
        logger.info(CLASS_LOG_PREFIX + "用户会话拦截控制:刷新已登录用户会话--SessionId:{},刷新状态:{}.", request.getRequestedSessionId(), refreshStatus);

        return true;
    }
}
