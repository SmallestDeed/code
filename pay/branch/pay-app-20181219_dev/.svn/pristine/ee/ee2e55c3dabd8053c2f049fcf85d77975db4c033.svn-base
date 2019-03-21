package com.sandu.gateway.pay.forward.service.ali.impl;

import java.util.HashMap;
import java.util.Map;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.jpay.alipay.AliPayApi;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;

@Service("aliScanCodePayService")
public class AliScanCodePayServiceImpl extends AliPayServiceImpl {
	
    private static Logger logger = LogManager.getLogger(AliScanCodePayServiceImpl.class);

    @Override
	protected Object buildPayParameter(PayParam payParam) {
    	super.setAliPayApiConfig();
		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		model.setOutTradeNo(payParam.getPayTradeNo());
		model.setSubject(payParam.getTradeDesc());
		model.setTotalAmount(String.valueOf((payParam.getTotalFee().doubleValue()/100)));
		model.setStoreId(GatewayPayConfig.ALI_STORE_ID);
		model.setTimeoutExpress("30m");
		model.setSellerId(GatewayPayConfig.ALI_SELLER_ID);
		return model;
	}
    
    @Override
	protected String executePay(Object params) {
		try {
			String result = AliPayApi.tradePrecreatePay((AlipayTradePrecreateModel)params, GatewayPayConfig.ALI_NOTIFY_URL);
			return result;
		} catch (AlipayApiException e) {
			logger.error("阿里扫码支付异常:",e);
			throw new BizException(ExceptionCodes.CODE_20010010,"阿里扫码支付异常:"+e.getMessage());
		}
	}
    
    @Override
   	protected Integer getPayMethod() {
   		return PayTradeRecord.PAY_METHOD_AL_SCANCODE;
   	}
    
	@Override
	protected String packageResult(String payTradeNo,String jsonResult) {
		Map resultMap = GsonUtil.fromJson(jsonResult, Map.class);
		Map responseMap = (Map)resultMap.get("alipay_trade_precreate_response");
		Map<String, String> packageRetParams = new HashMap<String, String>();
		packageRetParams.put("payTradeNo", payTradeNo);
        packageRetParams.put("qrCodeUrl", responseMap.get("qr_code").toString());
		return GsonUtil.toJson(packageRetParams);
	}

	
	
}
