package com.sandu.interceptor;


import com.google.gson.Gson;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


public class LoginUserInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginUserInterceptor.class);
    private final static String MOBILE_PLACTFORM_CODE = "mobile2b";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestStr = request.getServletPath();

        //不需要登录就能访问
        if (requestStr.contains("/franchiser/getList")
                || requestStr.contains("/franchiser/cabrand")
                || requestStr.contains("/franchiser/u3d/save")
                || requestStr.contains("/fa/user/cf/confirm")
                || requestStr.contains("/area/u3d/list")
                || requestStr.contains("v1/base/banner/web")
                || requestStr.contains("/v1/services")
                || requestStr.contains("v1/base/brand/company")
                || requestStr.contains("/v1/base/area/list")
                || requestStr.contains("v1/company/shop/getCompanyType")
                || requestStr.contains("v1/pro/category")
				||requestStr.contains("/v1/notify/wx/sendRenderTemplateMsg")
                || requestStr.contains("/v1/proprietor")
                || requestStr.contains("/v1/collect")
                || requestStr.contains("/v1/merchantManagePay/pay/callback")
                || requestStr.contains("/v1/group/purchase/callBack")
                || requestStr.contains("/v1/group/purchase/refund/callBack")
                || requestStr.contains("/v1/group/purchase/mini/code")
                || requestStr.contains("/v1/group/purchase/test/refund")
                || requestStr.contains("/v1/llt/trade")
                || requestStr.contains("/v1/act/bargain/award/getWxActBargainAwardMsgRandomList")
                || requestStr.contains("/v1/notify/wx/sendHandlerApplyHouseMsg")
                || requestStr.contains("/v1/feedback/web")
				|| requestStr.contains("/v1/group/purchase/goods/detail")
                ) {
            return true;
        }


        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            logger.info("进入options方法");
            return true;
        }

        //*********************检验用户登录转态start *************************
        String platformCode = request.getParameter("Platform-Code");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(platformCode) ||
                Objects.equals(MOBILE_PLACTFORM_CODE,platformCode)
                ){
            Integer loginStatus = LoginContext.getLoginStatus();
            String message;
            switch (loginStatus){
                case 1 :
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

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(message)){
                JSONObject outputMSg = new JSONObject();
                outputMSg.put("success", false);
                outputMSg.put("message", message);
                output(outputMSg.toJSONString(), response);
                return false;
            }
        }
        //*********************检验用户登录转态END *************************

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            resposeMessageToCustomer(response, "请登陆");
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

    public void resposeMessageToCustomer(HttpServletResponse response, String message) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ResponseEnvelope<Object> responseEnvelope = new ResponseEnvelope<>(false, message);
            Gson gson = new Gson();
            String jsonString = gson.toJson(responseEnvelope);
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            logger.error("返回数据异常：" + message);
        }
    }

    public void output(String jsonStr, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = response.getWriter();
        // out.println();
        out.write(jsonStr);
        out.flush();
        out.close();

    }

}
