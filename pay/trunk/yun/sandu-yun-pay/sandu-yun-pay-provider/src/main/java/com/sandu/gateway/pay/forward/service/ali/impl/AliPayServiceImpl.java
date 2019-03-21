package com.sandu.gateway.pay.forward.service.ali.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.jpay.alipay.AliPayApi;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.callback.service.async.AsyncQueryAliRefundResult;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.forward.service.base.impl.BasePayServiceImpl;
import com.sandu.gateway.pay.forward.service.wx.impl.WxPayServiceImpl;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AliPayServiceImpl extends  BasePayServiceImpl{

	@Autowired
	private AsyncQueryAliRefundResult asyncQueryAliRefundResult;

	private static Logger logger = LogManager.getLogger(AliPayServiceImpl.class);
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
		try {
			return AliPayApi.tradeRefund((AlipayTradeRefundModel)params);
		} catch (AlipayApiException e) {
			logger.error("阿里退款异常:",e);
			throw new BizException(ExceptionCodes.CODE_20010010,"阿里扫码支付异常:"+e.getMessage());
		}
	}
	
	protected Object buildTransfersParameter(TransfersParam transfersParam) {
		return null;
	}
	
	protected String executeTransfers(Object params){
		return null;
	}
	
	@Override
	protected String updateAndPackageRefundResult(PayTradeRefundRecord tradeRefundRecord,String jsonResult) {
		Map resultMap = GsonUtil.fromJson(jsonResult, Map.class);
		Map<String, Object> resultParams = new HashMap<String, Object>();
		Map responseMap = (Map)resultMap.get("alipay_trade_refund_response");
//		packageRetParams.put("code",responseMap.get("code"));
//		packageRetParams.put("payTradeNo",responseMap.get("out_trade_no"));
//		packageRetParams.put("extenalTradeNo",responseMap.get("trade_no"));
//		packageRetParams.put("refund_fee",responseMap.get("refund_fee"));

		resultParams.put("originInternalTradeNo", tradeRefundRecord.getOriginInternalTradeNo());
		resultParams.put("originPayTradeNo", tradeRefundRecord.getOriginPayTradeNo());
		resultParams.put("internalRefundNo", tradeRefundRecord.getInternalRefundNo());
		resultParams.put("payRefundNo", tradeRefundRecord.getPayRefundNo());
		resultParams.put("refundFee", responseMap.get("refund_fee"));

		//异步调用支付宝退款查询接口
		if (Objects.equals(responseMap.get("code"),"10000") && Objects.equals("Success",responseMap.get("msg"))){
			asyncQueryAliRefundResult.asyncQueryAliRefundResult((String) responseMap.get("out_trade_no"),responseMap.get("gmt_refund_pay"));
		}
		return GsonUtil.toJson(resultParams);
    }
	
	@Override
	protected String updateAndPackageTransfersResult(PayTradeTransfersRecord tradeTransfersRecord, String xmlOrJsonResult) {
		return null;
	}
	
	
	
}
