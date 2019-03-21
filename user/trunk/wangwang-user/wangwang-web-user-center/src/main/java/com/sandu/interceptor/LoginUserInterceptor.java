package com.sandu.interceptor;


import com.google.gson.Gson;
import com.sandu.api.user.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import com.sandu.common.Utils;
import com.sandu.config.SystemConfig;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


public class LoginUserInterceptor implements HandlerInterceptor {

    private final static Logger log = LoggerFactory.getLogger(LoginUserInterceptor.class);

    private final static String MOBILE_PLACTFORM_CODE = "mobile2b";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestStr = request.getServletPath();
        if (requestStr.contains("/v1/center/getCode")
                || requestStr.contains("/v1/center/getSms")
                || requestStr.contains("/v1/center/forgetPwd")
                || requestStr.contains("/v2/user/center/register")
                || requestStr.contains("/v2/user/center/editPassword")
                || requestStr.contains("center/bindCompanyFranchiser")
                || requestStr.contains("/v1/sys/dictionary/list")
                || requestStr.contains("/v1/user/manage/getLoginUserAdminPlatform")
                ) {
            log.info("进入不拦截的方法：" + requestStr);
            return true;
        }
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            log.info("进入options方法");
            return true;
        }
        String uri = request.getRequestURI();
        if (uri.indexOf("login") > 0 || uri.indexOf("logout") > 0 ||
                uri.indexOf("getRenderType") > 0 || uri.indexOf("getRechargeIntegral") > 0) {  //TODO
            return true;
        }

        //*********************检验用户登录转态start *************************
        String platformCode = request.getParameter("Platform-Code");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(platformCode) ||
                Objects.equals(MOBILE_PLACTFORM_CODE, platformCode)
                ) {
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
                case 5:
                    message = "无效的token";
                    break;
                default:
                    message = null;
                    break;
            }

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(message)) {
                JSONObject outputMSg = new JSONObject();
                outputMSg.put("success", false);
                outputMSg.put("message", message);
                output(outputMSg.toJSONString(), response);
                return false;
            }
        }
        //*********************检验用户登录转态END *************************

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null && SystemConfig.DEBUGMODEL) {
        	loginUser = Utils.getDebugUser(request);
		}
        
        if (null == loginUser) {
            resposeMessageToCustomer(response, "请登陆");
            return false;
        }
        request.setAttribute("tokenUserId", loginUser.getId());

        return true;

    }

	public void output(String jsonStr, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = response.getWriter();
        // out.println();
        out.write(jsonStr);
        out.flush();
        out.close();

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public void resposeMessageToCustomer(HttpServletResponse response, String message) {
        try {

            response.setContentType("application/json;charset=utf-8");
            ResponseEnvelope<Object> responseEnvelope = new ResponseEnvelope<>(false, message);
            Gson gson = new Gson();
            String jsonString = gson.toJson(responseEnvelope);
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            log.info("返回数据异常：" + message);
            e.printStackTrace();
        }
    }


}
