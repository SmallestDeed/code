package com.sandu.gateway.pay.callback.service.wx.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jpay.ext.kit.PaymentKit;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.callback.service.base.impl.BasePayCallbackServiceImpl;
import com.sandu.gateway.pay.trade.service.PayTradeRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;

@Service("wxPayCallbackService")
public class WxPayCallbackServiceImpl extends BasePayCallbackServiceImpl{

	private static Logger logger = LogManager.getLogger(WxPayCallbackServiceImpl.class);
	@Resource
    private PayTradeRecordService payTradeRecordService ;
    
    @Resource
    private PayTradeRecordLogService payTradeRecordLogService ;
    
    
    @Override
    public boolean verifySignature(Object notifyBody) {
    	Map<String, String> params = PaymentKit.xmlToMap(notifyBody.toString());
		return PaymentKit.verifyNotify(params, GatewayPayConfig.WX_MCH_KEY);
    }
    
    @Override
    public Map<String, String> getNotifyParams(Object notifyBody){
    	Map<String,String> params = new HashMap<String, String>();
    	Map<String, String> notifyParams = PaymentKit.xmlToMap(notifyBody.toString());
    	params.put("resultCode", notifyParams.get("result_code"));
		params.put("payTradeNo", notifyParams.get("out_trade_no"));//支付网关交易号
		params.put("extenalTradeNo", notifyParams.get("transaction_id"));// 微信支付订单号
		params.put("totalFee", notifyParams.get("total_fee")); //交易金额
		params.put("passbackParams",notifyParams.get("attach") == null?"0":notifyParams.get("attach"));
    	return params;
    }
}
