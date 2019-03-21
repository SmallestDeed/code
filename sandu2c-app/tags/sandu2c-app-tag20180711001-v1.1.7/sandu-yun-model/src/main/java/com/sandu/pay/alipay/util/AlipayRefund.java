package com.sandu.pay.alipay.util;

import com.sandu.pay.alipay.common.AlipayConfig;
import com.sandu.pay.order.model.PayRefund;

import java.util.HashMap;
import java.util.Map;

public class AlipayRefund {
	
    public static String getRefundRequest(PayRefund refund){
    	Map<String, String> sParaTemp = new HashMap<String, String>();
    	String detail_data=refund.getRefId()+"^"+refund.getTotalFee()*0.01+"^协商退款";
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", AlipayConfig.getInput_charset());
        sParaTemp.put("_input_charset", AlipayConfig.getInput_charset());
		sParaTemp.put("notify_url", AlipayConfig.getRefund_notify_url());
		sParaTemp.put("seller_email", AlipayConfig.getSeller_email());
		sParaTemp.put("seller_user_id", AlipayConfig.getSeller_id());
		sParaTemp.put("refund_date", UtilDate.getDateFormatter());
		sParaTemp.put("batch_no", refund.getRefundNo());
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", detail_data);
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		return sHtmlText;
    }
    
    public static String getRequestUrl(PayRefund refund){
    	Map<String, String> sParaTemp = new HashMap<String, String>();
    	String detail_data=refund.getRefId()+"^"+refund.getTotalFee()*0.01+"^协商退款";
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", AlipayConfig.getInput_charset());
        sParaTemp.put("_input_charset", AlipayConfig.getInput_charset());
		sParaTemp.put("notify_url", AlipayConfig.getRefund_notify_url());
		sParaTemp.put("seller_email", AlipayConfig.getSeller_email());
		sParaTemp.put("seller_user_id", AlipayConfig.getSeller_id());
		sParaTemp.put("refund_date", UtilDate.getDateFormatter());
		sParaTemp.put("batch_no", refund.getRefundNo());
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", detail_data);
		String url = AlipaySubmit.getRefundUrl(sParaTemp);
		return url;
    }
    
}
