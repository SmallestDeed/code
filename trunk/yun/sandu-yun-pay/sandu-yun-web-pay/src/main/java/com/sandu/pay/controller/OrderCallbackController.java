package com.sandu.pay.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sandu.common.util.StringUtils;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.service.PayOrderService;

@Controller
@RequestMapping("/v1/web/pay/order/callback")
public class OrderCallbackController {

    private static Logger logger = LogManager.getLogger(OrderCallbackController.class);
    
    @Resource
    private PayOrderService payOrderService;
    
 //   @Autowired
    private MallBaseOrderService mallBaseOrderService;

    //demo
  //  @RequestMapping(value = "/miniPay/notify")
    public void miniPayCallback(HttpServletRequest request, HttpServletResponse response) {
        logger.info("微信小程序订单支付回调。。。");
        try {
        	Map<String,String> paramsMap = new HashMap<String,String>();
        	Map<String, String[]> requestParams = request.getParameterMap();
    		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
    			String name = (String) iter.next();
    			String[] values = (String[]) requestParams.get(name);
    			String valueStr = "";
    			for (int i = 0; i < values.length; i++) {
    				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
    			}
    			paramsMap.put(name, valueStr);
    		}
    		String resultCode = paramsMap.get("resultCode");
    		String resultMsg = paramsMap.get("resultMsg");
    		String intenalTradeNo = paramsMap.get("intenalTradeNo");
    		String payTradeNo = paramsMap.get("payTradeNo");
    		logger.info("小程序订单支付回调参数:{}",paramsMap.toString());
    		PayOrder payOrder = this.getPayOrder(intenalTradeNo);
    		if("SUCCESS".equals(resultCode)) {
    			//修改支付流水状态为支付成功
    			this.modifyMallOrderPayInfo(payOrder.getId(), PayState.SUCCESS,payTradeNo,resultMsg);
    			//获取订单
    			MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(payOrder.getOrderNo());
    			//修改订单支付状态为已支付
 //   	        mallBaseOrderService.modifyBaseOrderPayStatus(baseOrder.getId(), true);
    			response.getWriter().print("SUCCESS");
    		}else {
    			//修改支付流水状态为支付失败
    			this.modifyMallOrderPayInfo(payOrder.getId(), PayState.PAY_ERROR,payTradeNo,resultMsg);
    			//获取订单
    			MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(payOrder.getOrderNo());
    			//修改订单支付状态为已付款失败(现在只有未付款)
 //   			mallBaseOrderService.modifyBaseOrderPayStatus(baseOrder.getId(), false);
    			response.getWriter().print("FAIL");
    		}
    		
        } catch (Exception ex) {
            logger.error("微信订单支付回调异常信息为：",ex.getMessage());
        }
    }
    
    private PayOrder getPayOrder(String tradeNo) {
    	if(StringUtils.isBlank(tradeNo)) {
    		return null;
    	}
    	PayOrder payOrder = new PayOrder();
    	payOrder.setIsDeleted(0);
    	payOrder.setTradeNo(tradeNo);
    	List<PayOrder> list = payOrderService.getList(payOrder);
    	if(list!=null && list.size()>0) {
    		return list.get(0);
    	}
    	return null;
    }

    
	private void modifyMallOrderPayInfo(Integer payOrderId,String payState,String payTradeNo,String info) {
		if(payOrderId!=null) {
	        PayOrder payOrder = new PayOrder();
	        payOrder.setId(payOrderId);
	        payOrder.setPayState(payState);
	        payOrder.setPayTradeNo(payTradeNo);
	        payOrder.setRemark(info);
	        payOrder.setGmtModified(new Date());
	        payOrderService.update(payOrder);
		}
	} 
	
	
	//demo
	//@RequestMapping(value = "/miniRefund/notify")
	public void miniRefundCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("微信小程序退款回调。。。");
		try {
			Map<String, String> paramsMap = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				paramsMap.put(name, valueStr);
			}
			String resultCode = paramsMap.get("resultCode");
			String resultMsg = paramsMap.get("resultMsg");
			String internalRefundNo = paramsMap.get("internalRefundNo");
			logger.info("小程序订单支付回调参数:{}", paramsMap.toString());
			//获取退款请求流水
			PayOrder payOrder = this.getRefundPayOrder(internalRefundNo);
			if ("SUCCESS".equals(resultCode)) {
				//更新退款流水状态为退款成功
				this.modifyMallOrderRefundInfo(payOrder.getId(), PayState.REFUND_SUCCESS, resultMsg);
				//获取订单
				MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(payOrder.getOrderNo());
				//更新订单状态为取消
//				mallBaseOrderService.modifyBaseOrderRefundStatus(baseOrder.getId());
				response.getWriter().print("SUCCESS");
			} else {
				//更新退款流水状态为退款失败
				this.modifyMallOrderRefundInfo(payOrder.getId(), PayState.REFUND_FAIL, resultMsg);
				//订单状为已取消
				response.getWriter().print("FAIL");
			}

		} catch (Exception ex) {
			logger.error("微信订单退款回调异常信息为：", ex.getMessage());
		}
	}
	
	  private PayOrder getRefundPayOrder(String refundNo) {
	    	if(StringUtils.isBlank(refundNo)) {
	    		return null;
	    	}
	    	PayOrder payOrder = new PayOrder();
	    	payOrder.setIsDeleted(0);
	    	payOrder.setRefundNo(refundNo);
	    	List<PayOrder> list = payOrderService.getList(payOrder);
	    	if(list!=null && list.size()>0) {
	    		return list.get(0);
	    	}
	    	return null;
	    }
	
	private void modifyMallOrderRefundInfo(Integer payOrderId,String payState,String info) {
		if(payOrderId!=null) {
	        PayOrder payOrder = new PayOrder();
	        payOrder.setId(payOrderId);
	        payOrder.setPayState(payState);
	        payOrder.setRemark(info);
	        payOrder.setGmtModified(new Date());
	        payOrderService.update(payOrder);
		}
	} 
	    
	   
		

 
    
   
}
