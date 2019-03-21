package com.sandu.gateway.pay.forward;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.pay.common.exception.BizException;


@Controller
@RequestMapping("/v1/gateway/pay")
public class GatewayPayController {

    private static Logger logger = LogManager.getLogger(GatewayPayController.class);
    
    @RequestMapping(value = "/doPay",method = RequestMethod.POST)
    @ResponseBody
    public Object doPay(HttpServletRequest request, @ModelAttribute PayParam payParam) {
        try {
        	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        	payParam.setOperator(loginUser.getId().longValue());
        	String platformCode = request.getHeader("Platform-Code");
        	payParam.setPlatformCode(platformCode);
            PayService payService = SpringContextHolder.getBean(payParam.getPayMethod());
            String obj = payService.doPay(payParam);
            return obj;
        } catch (BizException e) {
            return new ResponseEnvelope(true, e.getMessage(), null, false);
        } catch (Exception e) {
            logger.error("未知错误:", e);
            return new ResponseEnvelope(true, "未知错误", null, false);
        }
    }
    

}
