package com.sandu.interceptor;

import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
import com.sandu.common.ReturnData;
import com.sandu.commons.gson.GsonUtil;
import com.sandu.constant.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sandu
 */
@Slf4j
public class CheckTokenInterceptor extends HandlerInterceptorAdapter {

    private final Map<String, Boolean> requestMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getMethod().equalsIgnoreCase("options")) {
            return true;
        }
        boolean flag = true;
        if (handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getMethodAnnotation(CancelCheckToken.class) != null) {
                return true;
            }
            if (((HandlerMethod) handler).getMethodAnnotation(RequiresPermissions.class) == null) {
                return true;
            }
            flag = this.validLoginStatus(response, request);
        }
//        if (flag) {
//            flag = this.validCommits(request, response);
//        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    private boolean validLoginStatus(HttpServletResponse response, HttpServletRequest request) throws IOException {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            this.setMessageToResponse(response, request, "登陆失效,请重新登录!");
            return false;
        }
        Integer loginStatus = LoginContext.getLoginStatus();
        String message;
        switch (loginStatus) {
            case 1:
                message = "请登录";
                break;
            case 2:
                message = "请登录";
                break;
            case 3:
                message = "您的账号已在其他地方登录";
                break;
            default:
                message = null;
                break;
        }

        if (StringUtils.isNotEmpty(message)) {
            setMessageToResponse(response, request, message);
            return false;
        }
        return true;
    }

    private void setMessageToResponse(HttpServletResponse response, HttpServletRequest request, String message) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "authorization,content-type,platform-code");
        response.setHeader("Access-Control-Allow-Methods", "*");


        log.info("message:{}, url:{}", message, request.getRequestURL());
        response.getWriter().write(GsonUtil.toJson(ReturnData.builder().code(ResponseEnum.UNAUTHORIZED).success(false).message(message)));
    }

    private boolean validCommits(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String toke = request.getHeader("Authorization");

        String key = request.getRequestURL().append("_").append(toke).toString();
        if (requestMap.get(key) == null) {
            requestMap.put(key, true);
            return true;
        } else {
            this.setMessageToResponse(response, request, "请勿重复提交");
            return false;
        }
    }


    private void resetCurRequest(HttpServletRequest request) {
        String toke = request.getHeader("Authorization");
        String key = request.getRequestURL().append("_").append(toke).toString();
        requestMap.remove(key);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
