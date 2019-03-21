package com.sandu.gateway.pay.callback.service.ali.impl;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jpay.util.StringUtils;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.callback.service.base.impl.BasePayCallbackServiceImpl;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.trade.service.PayTradeRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;

@Service("aliPayCallbackService")
public class AliPayCallbackServiceImpl extends BasePayCallbackServiceImpl{

	private static Logger logger = LogManager.getLogger(AliPayCallbackServiceImpl.class);
	@Resource
    private PayTradeRecordService payTradeRecordService ;
    
    @Resource
    private PayTradeRecordLogService payTradeRecordLogService ;

  
    
    @Override
    public boolean verifySignature(Object notifyBody) {
    	boolean signFlag = false;
		try {
			Map<String,String> params = (Map<String,String>) notifyBody;
			signFlag = AlipaySignature.rsaCheckV1(params, 
					GatewayPayConfig.ALI_PUBLIC_KEY, 
					"UTF-8",
					"RSA2");
		} catch (AlipayApiException e) {
			logger.error("支付宝验证签名异常:", e);
		}
		return signFlag;
    }
    
    @Override
    public Map<String, String> getNotifyParams(Object notifyBody){
    	Map<String,String> params = new HashMap<String, String>();
    	Map<String,String> notifyParams = (Map<String,String>) notifyBody;
    	String tradeStatus = notifyParams.get("trade_status");
    	if ("TRADE_SUCCESS".equals(tradeStatus)) {
    		params.put("resultCode", "SUCCESS");
    	}else if("WAIT_BUYER_PAY".equals(tradeStatus)) {
    		throw new BizException(ExceptionCodes.CODE_20010014, "等待支付状态不处理回调!");
    	}else{
    		params.put("resultCode", "FAIL");
    	}
		params.put("payTradeNo", notifyParams.get("out_trade_no"));//支付网关交易号
		params.put("extenalTradeNo", notifyParams.get("trade_no"));// 支付宝交易号
		String strAmount = notifyParams.get("total_amount");
		if(StringUtils.isNotBlank(strAmount)) {
			Double d = Double.valueOf(strAmount)*100;
			strAmount = String.valueOf(d.longValue()); //金额用分表示
		}
		params.put("totalFee", strAmount); //交易金额
		params.put("passbackParams",notifyParams.get("passback_params"));
		/*try {
			params.put("passbackParams",URLDecoder.decode(notifyParams.get("passback_params"),"gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/

		return params;
    }

}
