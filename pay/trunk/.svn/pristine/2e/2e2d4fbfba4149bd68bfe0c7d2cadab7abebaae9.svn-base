package com.sandu.gateway.pay.callback.service.wx.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpay.ext.kit.PaymentKit;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.callback.service.base.impl.BaseRefundCallbackServiceImpl;
import com.sandu.gateway.pay.config.model.BaseCompanyMiniProgramConfig;
import com.sandu.gateway.pay.config.service.BaseCompanyMiniProgramConfigService;
import com.sandu.gateway.pay.trade.service.PayTradeRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;
import com.sandu.gateway.pay.util.AESTool;
import com.sandu.pay.common.exception.BizException;

@Service("wxRefundCallbackService")
public class WxRefundCallbackServiceImpl extends BaseRefundCallbackServiceImpl{

	private static Logger logger = LogManager.getLogger(WxRefundCallbackServiceImpl.class);
	@Resource
    private PayTradeRecordService payTradeRecordService ;
    
    @Resource
    private PayTradeRecordLogService payTradeRecordLogService ;
    
    @Autowired
    private BaseCompanyMiniProgramConfigService miniProgramConfigService;
    
    @Override
    public boolean verifySignature(Object notifyBody) {
    	Map<String, String> params = PaymentKit.xmlToMap(notifyBody.toString());
		return PaymentKit.verifyNotify(params, GatewayPayConfig.WX_MCH_KEY);
    }
    
    @Override
    public Map<String, String> getNotifyParams(Object notifyBody){
    	Map<String,String> params = new HashMap<String, String>();
    	Map<String, String> notifyParams = PaymentKit.xmlToMap(notifyBody.toString());
    	params.put("returnCode", notifyParams.get("return_code"));
		String reqInfo = notifyParams.get("req_info");
		BaseCompanyMiniProgramConfig miniConfig = miniProgramConfigService.getMiniProgramConfig(notifyParams.get("appid"));
        if(miniConfig==null) {
        	logger.error("appId:{}",notifyParams);
			throw new BizException("未找到小程序相关配置!");
		}
		try {
			String xmlData = new AESTool(miniConfig.getMchKey()).decryptData(reqInfo);
			Map<String, String> retParamsMap = PaymentKit.xmlToMap(xmlData);
			params.put("extenalTradeNo", retParamsMap.get("transaction_id"));
			params.put("payTradeNo",retParamsMap.get("out_trade_no"));
			params.put("externalRefundNo",retParamsMap.get("refund_id"));
			params.put("payRefundNo",retParamsMap.get("out_refund_no"));
			params.put("totalFee",retParamsMap.get("total_fee"));
			params.put("refundFee",retParamsMap.get("refund_fee"));
			params.put("refundStatus",retParamsMap.get("refund_status"));
			params.put("successTime",retParamsMap.get("success_time"));
		} catch (Exception e) {
			logger.error("解密错误:", e);
			throw new RuntimeException(e);
		}
    	return params;
    }

	

}
