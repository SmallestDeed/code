package com.sandu.gateway.pay.forward;

import javax.servlet.http.HttpServletRequest;

import com.sandu.common.sign.MD5SignTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.pay.common.exception.BizException;

import java.util.Date;


@Controller
@RequestMapping("/v1/gateway/pay")
public class GatewayPayController {

    private static Logger logger = LogManager.getLogger(GatewayPayController.class);

    private static final String ENCRYPT_KEY = "c431920fd2cd4dea9a68121ec73f9d6a";
    
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


    @RequestMapping(value = "/doRefund", method = RequestMethod.POST)
    @ResponseBody
    public Object doRefund(HttpServletRequest request, @ModelAttribute RefundParam refundParam) {
        try {
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            refundParam.setOperator(loginUser.getId().longValue());
            String platformCode = request.getHeader("Platform-Code");
            refundParam.setPlatformCode(platformCode);
            //验证签名
            validateSign(refundParam);
            PayService payService = SpringContextHolder.getBean(refundParam.getPayMethod());
            String obj = payService.doRefund(refundParam);
            return obj;
        } catch (BizException e) {
            return new ResponseEnvelope(true, e.getMessage(), null, false);
        } catch (Exception e) {
            logger.error("未知错误:", e);
            return new ResponseEnvelope(true, "未知错误", null, false);
        }
    }

    private void validateSign(RefundParam refundParam) {
        //间隔天数
        long day = (System.currentTimeMillis()-new Date(refundParam.getTimestamp()).getTime())/ (1000*3600*24);
        //一天之内有效
        if(day!=0) {
            throw new BizException("登录已过期,请重新登录");
        }

        //使用密钥生成md5签名
        String sign = MD5SignTool.MD5Encode("operator="+refundParam.getOperator()
                + "&platformCode="+refundParam.getPlatformCode()
                + "&timestamp="+refundParam.getTimestamp()
                + "&key="+ENCRYPT_KEY
                +"&refundFee="+refundParam.getRefundFee()
        ).toUpperCase();
        //验证用户签名是否正确
        if(!sign.equals(refundParam.getSign())){
            throw new BizException("签名错误");
        }
    }
}
