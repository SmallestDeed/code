package com.sandu.gateway.pay.forward.service.ali.impl;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.jpay.alipay.AliPayApi;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;

import java.util.HashMap;
import java.util.Map;

@Service("aliAppPayService")
public class AliAppPayServiceImpl extends AliPayServiceImpl {
	
    private static Logger logger = LogManager.getLogger(AliAppPayServiceImpl.class);

    @Override
	protected Object buildPayParameter(PayParam payParam) {
    	super.setAliPayApiConfig();
    	AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(payParam.getTradeDesc());
		model.setSubject(payParam.getTradeDesc());
		model.setOutTradeNo(payParam.getPayTradeNo());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(String.valueOf((payParam.getTotalFee().doubleValue()/100)));
		model.setProductCode("QUICK_MSECURITY_PAY");
		if (payParam.getPassbackParams() != null){
			model.setPassbackParams(payParam.getPassbackParams());
		}
		return model;
	}
    
    @Override
	protected String executePay(Object params) {
		try {
			String result = AliPayApi.startAppPay((AlipayTradeAppPayModel)params, GatewayPayConfig.ALI_NOTIFY_URL);
			return result;
		} catch (AlipayApiException e) {
			logger.error("阿里app支付异常:",e);
			throw new BizException(ExceptionCodes.CODE_20010015,"阿里app支付异常:"+e.getMessage());
		}
	}
    
    @Override
   	protected Integer getPayMethod() {
   		return PayTradeRecord.PAY_METHOD_AL_APPPAY;
   	}
    
	@Override
	protected String packageResult(String payTradeNo,String jsonResult) {
		Map<String,String> map= new HashMap<>();
		map.put("form",jsonResult);
		map.put("payTradeNo",payTradeNo);
    	return new Gson().toJson(map);
	}

	
	
	
}
