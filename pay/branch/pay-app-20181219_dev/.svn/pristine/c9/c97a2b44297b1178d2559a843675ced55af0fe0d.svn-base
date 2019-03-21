package com.sandu.callback.controller;

import com.google.gson.Gson;
import com.sandu.pay.alipay.util.AlipayNotify;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.PayProductConstans;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2017/12/2 15:17
 */
@Controller
@RequestMapping("/v1/web/alipay")
public class AlipayController {
	private final static Gson gson = new Gson();
	private final static String CLASS_LOG_PREFIX = "[支付宝回调服务]:";
	private static Logger logger = LogManager.getLogger(WechatController.class);
	/** 支付开关 **/
	private static final Boolean PAYABLE = true;
	@Autowired
	private PayOrderService payOrderService;
	@Resource
    private PayModelGroupRefService payModelGroupRefService;
	
	/**
     * 支付宝支付回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notify")
    public void aliPayCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX +"支付宝支付回调。。。 ");
        if (!PAYABLE) {
            return;
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            // 商户订单号
            String orderNo = request.getParameter(PayProductConstans.ALIPAY_OUT_TRADE_NO);
            // 支付宝交易号
            String tradeNo = request.getParameter(PayProductConstans.ALIPAY_TRADE_NO);
            // 交易状态
            String tradeStatus = request.getParameter(PayProductConstans.ALIPAY_TRADE_STATUS);
            // 异步通知ID
            String notify_id = request.getParameter(PayProductConstans.ALIPAY_NOTIFY_ID);
            // sign
            String sign = request.getParameter(PayProductConstans.ALIPAY_SIGN);
            PrintWriter writer = response.getWriter();

            if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotify.verifyResponse(notify_id).equals("true")) // 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    PayOrder order = new PayOrder();
                    order.setOrderNo(orderNo);
                    order.setPayState(PayState.PAY_ERROR);
                    order.setRefId(tradeNo);
                    logger.info("使用支付宝公钥验签：" + AlipayNotify.getSignVeryfy(params, sign));
                    if (AlipayNotify.getSignVeryfy(params, sign)) // 使用支付宝公钥验签
                    {
                        // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                        if (tradeStatus.equals("TRADE_FINISHED")) {

                        } else if (tradeStatus.equals("TRADE_SUCCESS")) {

                            order.setPayState(PayState.SUCCESS);
                            payOrderService.updateAliPayCallbackInfo(order);
                        }
                        //给客户端发送订单支付状态信息,更新任务状态，退款
                        payOrderService.sendPayStateNew(orderNo);
                        writer.print("success");
                    }
                    // 验证签名失败
                    else {
                        writer.print("sign fail");
                        PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
                        if (null != payOrder &&  !PayState.SUCCESS.equals(payOrder.getPayState())) {
                            payOrderService.updatePayState(order);
                        }
                        // 给客户端发送支付状态消息
                        logger.info("支付结果：" + order.getPayState());
                        payOrderService.sendPayStateNew(orderNo);
                    }
                }
                // 验证是否来自支付宝的通知失败
                else {
                    writer.print("response fail");
                }
            } else {
                writer.print("no notify message");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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
    public void aliPayNotifyMobilePay(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX +"移动端登录支付的支付宝支付回调。。。 ");
        if (!PAYABLE) {
            return;
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            // 商户订单号
            String orderNo = request.getParameter(PayProductConstans.ALIPAY_OUT_TRADE_NO);
            // 支付宝交易号
            String tradeNo = request.getParameter(PayProductConstans.ALIPAY_TRADE_NO);
            // 交易状态
            String tradeStatus = request.getParameter(PayProductConstans.ALIPAY_TRADE_STATUS);
            // 异步通知ID
            String notify_id = request.getParameter(PayProductConstans.ALIPAY_NOTIFY_ID);
            // sign
            String sign = request.getParameter(PayProductConstans.ALIPAY_SIGN);
            PrintWriter writer = response.getWriter();

            if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
                PayOrder order = new PayOrder();
                order.setOrderNo(orderNo);
                order.setPayState(PayState.PAY_ERROR);
                order.setRefId(tradeNo);
                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (tradeStatus.equals("TRADE_FINISHED")) {

                } else if (tradeStatus.equals("TRADE_SUCCESS")) {

                    order.setPayState(PayState.SUCCESS);
                    // 更新订单状态
                    payOrderService.updatePayState(order);
                    // 更新用户的移动端开通信息
                    payOrderService.updateUserMobileInfo(orderNo);
                    //更新度币
                    payOrderService.updateUserFinance(orderNo);
                    logger.info("支付结果：" + order.getPayState());
                    writer.print("success");

                } else {
                    writer.print("sign fail");
                    PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
                    if (null != payOrder &&  !PayState.SUCCESS.equals(payOrder.getPayState())) {
                        payOrderService.updatePayState(order);
                    }
                    logger.info("支付结果：" + order.getPayState());
                }
            } else {
                writer.print("no notify message");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 渲染包年包月回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notifyConfigPay")
    public void notifyConfigPay(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CLASS_LOG_PREFIX +"渲染包年包月支付宝扫码支付的支付回调。。。 ");
        if (!PAYABLE) {
            return;
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            // 商户订单号
            String orderNo = request.getParameter(PayProductConstans.ALIPAY_OUT_TRADE_NO);
            // 支付宝交易号
            String tradeNo = request.getParameter(PayProductConstans.ALIPAY_TRADE_NO);
            // 交易状态
            String tradeStatus = request.getParameter(PayProductConstans.ALIPAY_TRADE_STATUS);
            // 异步通知ID
            String notify_id = request.getParameter(PayProductConstans.ALIPAY_NOTIFY_ID);
            // sign
            String sign = request.getParameter(PayProductConstans.ALIPAY_SIGN);
            PrintWriter writer = response.getWriter();

            if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
                PayOrder order = new PayOrder();
                order.setOrderNo(orderNo);
                order.setPayState(PayState.PAY_ERROR);
                order.setRefId(tradeNo);
                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (tradeStatus.equals("TRADE_FINISHED")) {

                } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                    logger.info("渲染包年包月支付宝扫码支付的支付回调订单号orderNo:{}" + orderNo);
                    order.setPayState(PayState.SUCCESS);
                    // 更新订单状态
                    payOrderService.updatePayState(order);
                    //更新度币
                    payOrderService.updateUserFinance(orderNo);
                    //插入数据到付款方式业务关联表
                    payModelGroupRefService.addPayModelGroupRef(orderNo);
                    //websocket通知
                    payOrderService.sendRenderPayStateNew(orderNo);
                    logger.info("支付结果：" + order.getPayState());
                    writer.print("success");

                } else {
                    writer.print("sign fail");
                    PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
                    if (null != payOrder &&  !PayState.SUCCESS.equals(payOrder.getPayState())) {
                        payOrderService.updatePayState(order);
                    }
                    logger.info("支付结果：" + order.getPayState());
                }
            } else {
                writer.print("no notify message");
            }

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
        logger.info(CLASS_LOG_PREFIX +"度币共享充值支付宝扫码支付的支付回调。。。 ");
        if (!PAYABLE) {
            return;
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            // 商户订单号
            String orderNo = request.getParameter(PayProductConstans.ALIPAY_OUT_TRADE_NO);
            // 支付宝交易号
            String tradeNo = request.getParameter(PayProductConstans.ALIPAY_TRADE_NO);
            // 交易状态
            String tradeStatus = request.getParameter(PayProductConstans.ALIPAY_TRADE_STATUS);
            // 异步通知ID
            String notify_id = request.getParameter(PayProductConstans.ALIPAY_NOTIFY_ID);
            // sign
            String sign = request.getParameter(PayProductConstans.ALIPAY_SIGN);
            PrintWriter writer = response.getWriter();

            if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
                PayOrder order = new PayOrder();
                order.setOrderNo(orderNo);
                order.setPayState(PayState.PAY_ERROR);
                order.setRefId(tradeNo);
                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (tradeStatus.equals("TRADE_FINISHED")) {

                } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                    logger.info("度币共享充值支付宝的支付回调订单号orderNo:{}" + orderNo);
                    order.setPayState(PayState.SUCCESS);
                    // 更新订单状态
                    payOrderService.updatePayState(order);
                    //更新度币
                    payOrderService.updateUserFinance(orderNo);
                    //websocket通知
                    payOrderService.sendRenderPayStateNew(orderNo);
                    logger.info("支付结果：" + order.getPayState());
                    writer.print("success");

                } else {
                    writer.print("sign fail");
                    PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
                    if (null != payOrder &&  !PayState.SUCCESS.equals(payOrder.getPayState())) {
                        payOrderService.updatePayState(order);
                    }
                    logger.info("支付结果：" + order.getPayState());
                }
            } else {
                writer.print("no notify message");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
