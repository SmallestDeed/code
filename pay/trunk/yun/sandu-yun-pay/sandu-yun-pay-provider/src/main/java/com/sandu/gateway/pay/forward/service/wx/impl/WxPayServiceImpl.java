package com.sandu.gateway.pay.forward.service.wx.impl;

import java.util.HashMap;
import java.util.Map;

import com.jpay.ext.kit.PaymentKit;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;
import com.sandu.pay.common.exception.BizException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.config.service.BaseCompanyMiniProgramConfigService;
import com.sandu.gateway.pay.forward.service.base.impl.BasePayServiceImpl;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;

public abstract class WxPayServiceImpl extends BasePayServiceImpl {
    private static Logger logger = LogManager.getLogger(WxPayServiceImpl.class);

    @Autowired
    private PayTradeRefundRecordService payTradeRefundRecordService;
    
    protected Object buildRefundParameter(RefundParam refundParam) {
		return null;
	}
    
    protected Object buildTransfersParameter(TransfersParam transfersParam) {
		return null;
	}
     
    protected String executePay(Object params) {
        String xmlResult = WxPayApi.pushOrder(false,  (Map<String, String>)params);
        return xmlResult;
    }

    protected WxPayApiConfig getWxPayApiConfig() {
        WxPayApiConfig apiConfig = WxPayApiConfig.New()
                .setAppId(GatewayPayConfig.WX_APP_ID)
                .setMchId(GatewayPayConfig.WX_MCH_ID)
                .setPaternerKey(GatewayPayConfig.WX_MCH_KEY)
                .setNotifyUrl(GatewayPayConfig.WX_NOTIFY_URL)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
        return apiConfig;
    }
    
    protected String executeRefund(Object params) {
    	Map<String, String> paramsMap = (Map<String, String>)params;
    	String apiCertPath = paramsMap.get("api_cert_path");
    	paramsMap.remove("api_cert_path");//要删除这个临时参数,否则微信接口会报错.
    	String xmlResult = WxPayApi.orderRefund(false, paramsMap , apiCertPath, paramsMap.get("mch_id"));
        return xmlResult;
    }

    protected String executeTransfers(Object params) {
    	Map<String, String> paramsMap = (Map<String, String>)params;
        logger.info("准备开始企业付款参数: =>{}" + paramsMap);
        String apiCertPath = paramsMap.get("api_cert_path");
    	paramsMap.remove("api_cert_path");//要删除这个临时参数,否则微信接口会报错.
        logger.info("开始调用微信企业付款到零钱接口");
    	String xmlResult = WxPayApi.transfers(paramsMap , apiCertPath,paramsMap.get("mchid"));
        logger.info("发红包"+xmlResult);
        return xmlResult;
	}
    
    @Override
	protected String updateAndPackageRefundResult(PayTradeRefundRecord tradeRefundRecord,String xmlResult) {
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            logger.error(xmlResult);
            throw new BizException("微信退款异常:" + return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            logger.error(xmlResult);
            throw new BizException("微信退款结果异常:" + result.get("err_code_des"));
        }
        this.modifyTradeRefundInfo(tradeRefundRecord.getId(),result);
        Map<String, String> resultParams = new HashMap<String, String>();
        resultParams.put("originInternalTradeNo", tradeRefundRecord.getOriginInternalTradeNo());
        resultParams.put("originPayTradeNo", tradeRefundRecord.getOriginPayTradeNo());
        resultParams.put("internalRefundNo", tradeRefundRecord.getInternalRefundNo());
        resultParams.put("payRefundNo", tradeRefundRecord.getPayRefundNo());
        resultParams.put("refundFee", result.get("refund_fee"));
        return GsonUtil.toJson(resultParams);
    }


    private void modifyTradeRefundInfo(Long id,Map<String, String> result) {
        PayTradeRefundRecord obj = new PayTradeRefundRecord();
        obj.setId(id);
        obj.setRefundFee(Long.valueOf(result.get("refund_fee").toString()));
        obj.setExternalRefundNo(result.get("refund_id"));
        payTradeRefundRecordService.modify(obj);
    }

    @Override
	protected String updateAndPackageTransfersResult(PayTradeTransfersRecord tradeTransfersRecord, String xmlOrJsonResult) {
		return null;
	}
    	
}
