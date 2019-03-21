package com.sandu.gateway.pay.forward.service.wx.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.sandu.common.util.ConfigUtil;
import com.sandu.config.GatewayPayConfig;
import com.sandu.pay.wexin.common.WxConfigure;
import org.apache.commons.lang3.StringUtils;
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

@Service("wxH5PayService")
public class WxH5PayServiceImpl extends WxPayServiceImpl {
    private static Logger logger = LogManager.getLogger(WxH5PayServiceImpl.class);
    private static final ThreadLocal<String> TL = new ThreadLocal<String>();
    @Override
    protected Map<String, String> buildPayParameter(PayParam payParam) {
        //WxPayApiConfig apiConfig = super.getWxPayApiConfig();
        WxPayApiConfig apiConfig = getWxPayApiConfig();
        Map<String, String> params = apiConfig
                .setBody(payParam.getTradeDesc())
                .setOutTradeNo(payParam.getPayTradeNo())
                .setTotalFee(payParam.getTotalFee().toString())
                .setSpbillCreateIp(payParam.getIp())
				.setTradeType(WxPayApi.TradeType.MWEB)		//setSceneInfo(h5_info.toString()) 文档规定必填,但是好像可以不填
                .setSceneInfo("1324648") //直接写死,可以不传
                .build();
        if (payParam.getPassbackParams() != null){
            apiConfig.setAttach(payParam.getPassbackParams());
        }
        TL.set(payParam.getRedirectUrl());
        return params;	
    }


    protected WxPayApiConfig getWxPayApiConfig() {
        WxPayApiConfig apiConfig = WxPayApiConfig.New()
                .setAppId(WxConfigure.getAppid())
                .setMchId(WxConfigure.getMchid())
                .setPaternerKey(WxConfigure.getKey())
                .setNotifyUrl(GatewayPayConfig.WX_NOTIFY_URL)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
        return apiConfig;
    }

    @Override
	protected Integer getPayMethod() {
		return PayTradeRecord.PAY_METHOD_WX_H5PAY;
	}


	@Override
	protected String packageResult(String payTradeNo,String xmlResult) {
		
	 	Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(returnCode)) {
            logger.error("微信H5支付返回异常结果:"+xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011003, "微信H5支付下单异常:" + returnMsg);
        }
        String resultCode = result.get("result_code");
        String errCodeDes = result.get("err_code_des");
        if (!PaymentKit.codeIsOK(resultCode)) {
            logger.error("微信H5支付返回异常结果:"+xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011004, "微信H5支付下单结果异常:" + errCodeDes);
        }
 
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
 		String mwebUrl = result.get("mweb_url");
 		String redirectUrl = TL.get();
 		try {
 			if(StringUtils.isNotBlank(redirectUrl)) {
 				mwebUrl = mwebUrl+"&redirect_url=" +URLEncoder.encode(redirectUrl, "utf-8");
 			}
		} catch (UnsupportedEncodingException e) { 
			logger.error("Encode Error:"+redirectUrl,e);
		}
        Map<String, String> packageParams = new HashMap<String, String>();
 		packageParams.put("payTradeNo", payTradeNo);
 		packageParams.put("mwebUrl", mwebUrl);
		return GsonUtil.toJson(packageParams);
	}

	
}
