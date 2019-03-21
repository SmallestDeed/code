package com.sandu.gateway.pay.forward.service.wx.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;

@Service("wxAppPayService")
public class WxAppPayServiceImpl extends WxPayServiceImpl {
	private static final ThreadLocal<String> TL = new ThreadLocal<String>();
	private static final Map<String, WxPayApiConfig> CFG_MAP = new ConcurrentHashMap<String, WxPayApiConfig>();
    private static Logger logger = LogManager.getLogger(WxAppPayServiceImpl.class);

    @Override
    protected Map<String, String> buildPayParameter(PayParam payParam) {
        WxPayApiConfig apiConfig = super.getWxPayApiConfig();
        Map<String, String> params = apiConfig
                .setBody(payParam.getTradeDesc())
                .setOutTradeNo(payParam.getPayTradeNo())
                .setTotalFee(payParam.getTotalFee().toString())
                .setSpbillCreateIp(payParam.getIp())
				.setTradeType(WxPayApi.TradeType.APP)
                .build();
        if (payParam.getPassbackParams() != null){
        	apiConfig.setAttach(payParam.getPassbackParams());
		}
        CFG_MAP.put(apiConfig.getAppId(), apiConfig);
		TL.set(apiConfig.getAppId());
        return params;	
    }
    
    @Override
	protected Integer getPayMethod() {
		return PayTradeRecord.PAY_METHOD_WX_APPPAY;
	}


	@Override
	protected String packageResult(String payTradeNo,String xmlResult) {
	 	Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(returnCode)) {
            logger.error("微信app支付返回异常结果:"+xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011003, "微信app支付下单异常:" + returnMsg);
        }
        String resultCode = result.get("result_code");
        String errCodeDes = result.get("err_code_des");
        if (!PaymentKit.codeIsOK(resultCode)) {
            logger.error("微信app支付返回异常结果:"+xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011004, "微信app支付下单结果异常:" + errCodeDes);
        }
        
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
 		String prepay_id = result.get("prepay_id");
 		//封装调起微信支付的参数 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
 		Map<String, String> packageParams = new HashMap<String, String>();
 		WxPayApiConfig apiConfig = CFG_MAP.get(TL.get());
 		packageParams.put("appid", apiConfig.getAppId());
 		packageParams.put("partnerid", apiConfig.getMchId());
 		packageParams.put("prepayid", prepay_id);
 		packageParams.put("package", "Sign=WXPay");
 		packageParams.put("noncestr", System.currentTimeMillis() + "");
 		packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
 		String packageSign = PaymentKit.createSign(packageParams,apiConfig.getPaternerKey());
 		packageParams.put("sign", packageSign);
		return GsonUtil.toJson(packageParams);
	}

	
}
