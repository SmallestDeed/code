package com.sandu.gateway.pay.forward.service.ali.impl;

import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.forward.service.base.impl.BasePayServiceImpl;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;

public abstract class AliPayServiceImpl extends  BasePayServiceImpl{
	
	
	protected void setAliPayApiConfig() {
		
		AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New()
	        		.setAppId(GatewayPayConfig.ALI_APP_ID)
	        		.setAlipayPublicKey(GatewayPayConfig.ALI_PUBLIC_KEY)
	        		.setCharset("UTF-8")
	        		.setPrivateKey(GatewayPayConfig.ALI_PRIVATE_KEY)
	        		.setServiceUrl(GatewayPayConfig.ALI_SERVICE_URL)
	        		.setSignType("RSA2")
	        		.build();
		AliPayApiConfigKit.putApiConfig(aliPayApiConfig);
		AliPayApiConfigKit.setThreadLocalAppId(GatewayPayConfig.ALI_APP_ID);
    }
	
	protected Object buildRefundParameter(RefundParam refundParam) {
		return null;
	}

	protected String executeRefund(Object params) {
		return null;
	}
	
	protected Object buildTransfersParameter(TransfersParam transfersParam) {
		return null;
	}
	
	protected String executeTransfers(Object params){
		return null;
	}
	
	@Override
	protected String updateAndPackageRefundResult(PayTradeRefundRecord tradeRefundRecord,String xmlResult) {
    	return null;
    }
	
	@Override
	protected String updateAndPackageTransfersResult(PayTradeTransfersRecord tradeTransfersRecord, String xmlOrJsonResult) {
		return null;
	}
	
	
	
}
