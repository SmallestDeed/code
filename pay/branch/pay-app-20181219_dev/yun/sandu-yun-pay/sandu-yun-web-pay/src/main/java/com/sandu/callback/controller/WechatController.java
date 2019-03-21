package com.sandu.callback.controller;

import com.google.gson.Gson;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.wexin.common.XMLParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2017/12/2 15:18
 */
@Controller
@RequestMapping("/v1/web/wxpay")
public class WechatController {
	private final static Gson gson = new Gson();
	private final static String CLASS_LOG_PREFIX = "[微信回调服务]:";
	private static Logger logger = LogManager.getLogger(WechatController.class);
	/** 支付开关 **/
	private static final Boolean PAYABLE = true;
	
	@Autowired
	private PayOrderService payOrderService;
	 /**
     * 微信支付回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notify")
    public void wxPayCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX + "微信支付回调。。。");
        if (!PAYABLE) {
            return;
        }
        try {
            boolean success = false;
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer sbResult = new StringBuffer();
            response.reset();
            PrintWriter writer = response.getWriter();
            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }
            if (reader != null) {
                reader.close();
            }
            //支付回调
            logger.info(CLASS_LOG_PREFIX + "sbResult = "+sbResult.toString());
            success = payOrderService.wxPayCallbackUpdateInfo(sbResult.toString(),success);

            if (success) {
                writer.write(XMLParser.getResponseWeixin(true, "OK"));
            } else {
                writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
            }
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("微信支付回调异常信息为：{}" + ex.getMessage());
        }
    }

    /**
     *
     * 移动端登录支付回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notifyMobilePay")
    public void wxNotifyMobilePay(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX + "微信移动端登录支付回调。。。");
        if (!PAYABLE) {
            return;
        }
        try {
            boolean success = false;
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer sbResult = new StringBuffer();
            response.reset();
            PrintWriter writer = response.getWriter();
            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }
            if (reader != null) {
                reader.close();
            }
            //支付回调
            logger.info(CLASS_LOG_PREFIX + "sbResult = "+sbResult.toString());
            success = payOrderService.wxNotifyMobilePay(sbResult.toString(),success);

            if (success) {
                writer.write(XMLParser.getResponseWeixin(true, "OK"));
            } else {
                writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
            }
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * 渲染包年包月回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notifyConfigPay")
    public void notifyConfigPay(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX +"渲染微信扫码支付的支付回调。。。 ");
        if (!PAYABLE) {
            return;
        }
        try {
            boolean success = false;
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer sbResult = new StringBuffer();
            response.reset();
            PrintWriter writer = response.getWriter();
            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }
            if (reader != null) {
                reader.close();
            }
            //支付回调
            logger.info(CLASS_LOG_PREFIX + "sbResult = "+sbResult.toString());
            success = payOrderService.wxNotifyConfigPay(sbResult.toString(),success);

            if (success) {
                writer.write(XMLParser.getResponseWeixin(true, "OK"));
            } else {
                writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
            }
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 度币共享充值回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notifySharePay")
    public void notifySharePay(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX +"微信度币共享充值回调。。。 ");
        if (!PAYABLE) {
            return;
        }
        try {
            boolean success = false;
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer sbResult = new StringBuffer();
            response.reset();
            PrintWriter writer = response.getWriter();
            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }
            if (reader != null) {
                reader.close();
            }
            //支付回调
            logger.info(CLASS_LOG_PREFIX + "sbResult = "+sbResult.toString());
            success = payOrderService.wxNotifySharePayPc(sbResult.toString(),success);

            if (success) {
                writer.write(XMLParser.getResponseWeixin(true, "OK"));
            } else {
                writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
            }
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Mini Program Pay
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notifyminipropay")
    public void wxminiproPayCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX + "微信小程序支付回调。。。");
        if (!PAYABLE) {
            return;
        }
        try {
            boolean success = false;
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer sbResult = new StringBuffer();
            response.reset();
            PrintWriter writer = response.getWriter();
            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }
            if (reader != null) {
                reader.close();
            }
            //支付回调
            logger.info(CLASS_LOG_PREFIX + "sbResult = "+sbResult.toString());
            // TODO: ADD the method to update the order status;
            success = payOrderService.wxMiniPayCallbackUpdateInfo(sbResult.toString(),success);
            if (success) {
                writer.write(XMLParser.getResponseWeixin(true, "OK"));
            } else {
                writer.write(XMLParser.getResponseWeixin(false, "FAIL"));
            }
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("微信支付回调异常信息为：{}" + ex.getMessage());
        }
    }

}
