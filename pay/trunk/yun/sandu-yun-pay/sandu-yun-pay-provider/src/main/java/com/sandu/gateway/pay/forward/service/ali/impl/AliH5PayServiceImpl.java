package com.sandu.gateway.pay.forward.service.ali.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.domain.AlipayTradePayModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.jpay.alipay.AliPayApi;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;

@Service("aliH5PayService")
public class AliH5PayServiceImpl extends AliPayServiceImpl {
	
    private static Logger logger = LogManager.getLogger(AliH5PayServiceImpl.class);
    private static final ThreadLocal<String> TL = new ThreadLocal<String>();
    @Override
	protected Object buildPayParameter(PayParam payParam) {
    	super.setAliPayApiConfig();
    	AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setBody(payParam.getTradeDesc());
		model.setSubject(payParam.getTradeDesc());
		model.setOutTradeNo(payParam.getPayTradeNo());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(String.valueOf((payParam.getTotalFee().doubleValue()/100)));
		model.setQuitUrl(payParam.getRedirectUrl());
		model.setProductCode("QUICK_WAP_PAY");
		if (payParam.getPassbackParams() != null){
			model.setPassbackParams(payParam.getPassbackParams());
		}
		TL.set(payParam.getRedirectUrl());
		return model;
	}
    
    @Override
	protected String executePay(Object params) {
		try {
			String redirectUrl = TL.get();
			String result = AliPayApi.wapPayStr(null, (AlipayTradeWapPayModel)params, redirectUrl, GatewayPayConfig.ALI_NOTIFY_URL);
			return result;
		} catch (AlipayApiException | IOException e) {
			logger.error("阿里H5支付异常:",e);
			throw new BizException(ExceptionCodes.CODE_20010015,"阿里H5支付异常:"+e.getMessage());
		}
	}
    
    @Override
   	protected Integer getPayMethod() {
   		return PayTradeRecord.PAY_METHOD_AL_H5PAY;
   	}
    
	@Override
	protected String packageResult(String payTradeNo,String jsonResult) {
		Map<String, String> packageParams = new HashMap<String, String>();
	 	packageParams.put("payTradeNo", payTradeNo);
	 	packageParams.put("mwebUrl", jsonResult);
		return GsonUtil.toJson(packageParams);
	}

	
	
	
}
