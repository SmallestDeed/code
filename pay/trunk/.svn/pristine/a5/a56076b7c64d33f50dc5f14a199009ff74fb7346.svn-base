package com.sandu.gateway.pay.forward.service.wx.impl;

import java.util.HashMap;
import java.util.Map;

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

@Service("wxScanCodePayService")
public class WxScanCodePayServiceImpl extends WxPayServiceImpl {
	
    private static Logger logger = LogManager.getLogger(WxScanCodePayServiceImpl.class);

    @Override
    protected Map<String, String> buildPayParameter(PayParam payParam) {
        WxPayApiConfig apiConfig = super.getWxPayApiConfig();
        Map<String, String> params = apiConfig
                .setBody(payParam.getTradeDesc())
                .setOutTradeNo(payParam.getPayTradeNo())
                .setTotalFee(payParam.getTotalFee().toString())
                .setSpbillCreateIp(payParam.getIp())
				.setTradeType(WxPayApi.TradeType.NATIVE)
                .build();
        return params;
    }
    
    @Override
	protected Integer getPayMethod() {
		return PayTradeRecord.PAY_METHOD_WX_SCANCODE;
	}


	@Override
	protected String packageResult(String payTradeNo,String xmlResult) {
	 	Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(returnCode)) {
            logger.info(xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011001, "微信下单异常:" + returnMsg);
        }
        String resultCode = result.get("result_code");
        String errCodeDes = result.get("err_code_des");
        if (!PaymentKit.codeIsOK(resultCode)) {
            logger.info(xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011002, "微信下单结果异常:" + errCodeDes);
        }
        Map<String, String> packageRetParams = new HashMap<String, String>();
        String qrCodeUrl = result.get("code_url");
        packageRetParams.put("payTradeNo", payTradeNo);
        packageRetParams.put("qrCodeUrl", qrCodeUrl);
		return GsonUtil.toJson(packageRetParams);
	}

}
