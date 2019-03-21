package com.sandu.gateway.pay.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpay.alipay.AliPayApi;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.gateway.pay.callback.service.PayCallbackService;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;


@Controller
@RequestMapping("/v1/gateway/pay/callback")
public class GatewayPayCallbackController {

    private static Logger logger = LogManager.getLogger(GatewayPayCallbackController.class);

  
    @RequestMapping(value = "/wx/notify")
    public void wxPayCallback(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("wx支付回调");
        //获取xml回调报文
    	String xmlResult = HttpKit.readData(request);
    	boolean success = false;
    	String errMsg = "";
    	try {
    		PayCallbackService payCallbackService = SpringContextHolder.getBean("wxPayCallback");
    		logger.info("wx回调结果为 =>{}"+xmlResult);
    		success = payCallbackService.callback(xmlResult);
    	}catch(BizException ex) {
    		errMsg = ex.getMessage();
    	}catch(Exception ex) {
    		logger.error("系统错误:", ex);
    		errMsg = "未知错误";
    	}
    	//响应微信服务器
    	responseToWxServer(success,errMsg,response);
    }
    
    private	void responseToWxServer(boolean success,String errMsg,HttpServletResponse response) {
    	Map<String, String> xml = new HashMap<String, String>();
    	if(success) {
    		xml.put("return_code","SUCCESS");
    		xml.put("return_msg", "OK");
    	}else {
    		xml.put("return_code","FAIL");
    		xml.put("return_msg", errMsg);
    	}
    	try {
    		logger.info("付款后,通知微信服务器:{}",xml);
    		PrintWriter writer = response.getWriter();
    		writer.write(PaymentKit.toXml(xml));
			writer.flush();
		} catch (IOException e) {
			logger.error("网络异常:", e);
		}
    }

    @RequestMapping(value = "/ali/notify")
    public void aliPayCallback(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, String> map = AliPayApi.toMap(request);
    	logger.info("支付宝通知参数:{}",map);
    	boolean success = false;
    	boolean notifyFlag = true;
    	try {
    		PayCallbackService payCallbackService = SpringContextHolder.getBean("aliPayCallback");
    		success = payCallbackService.callback(map);
    	}catch(BizException ex) {
    		if(ExceptionCodes.CODE_20010014 == ex.getCode()) {
    			notifyFlag = false;
    		}
    	}catch(Exception ex) {
    		logger.error("系统错误:", ex);
    	}
    	if(notifyFlag) {
    		//响应支付宝服务器
    		responseToAliServer(success,response);
    	}
    }
    
    private	void responseToAliServer(boolean success,HttpServletResponse response) {
    	try {
    		logger.info("付款后,开始通知支付宝服务器:{}",success);
    		PrintWriter writer = response.getWriter();			
    		if(success) {
    			writer.write("success");
    		}else {
    			writer.write("failure");
    		}
    		writer.flush();
		} catch (IOException e) {
			logger.error("网络异常:", e);
		}
    }
    
    @RequestMapping(value = "/test/notify")
    public void test(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		logger.error("返回参数:");
    		Map<String, String[]> requestParams = request.getParameterMap();
    		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
    			String name = (String) iter.next();
    			String[] values = (String[]) requestParams.get(name);
    			String valueStr = "";
    			for (int i = 0; i < values.length; i++) {
    				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
    			}
    			// 乱码解决，这段代码在出现乱码时使用。
    			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
    			logger.error(name+" : "+valueStr);
    		}
    		
			response.getWriter().print("SUCCESS");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
