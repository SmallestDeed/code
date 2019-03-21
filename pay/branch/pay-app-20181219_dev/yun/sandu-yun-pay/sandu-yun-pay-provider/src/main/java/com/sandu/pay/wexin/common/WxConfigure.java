package com.sandu.pay.wexin.common;

import com.sandu.common.util.ConfigUtil;

public class WxConfigure {
	private static String key = ConfigUtil.getConfig("config/pay", "wxpay.key");

	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = ConfigUtil.getConfig("config/pay", "wxpay.appid");

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = ConfigUtil.getConfig("config/pay", "wxpay.mch_id");

	//受理模式下给子商户分配的子商户号
	private static String subMchID = "";

	//HTTPS证书的本地路径
	private static String certLocalPath = "";

	//HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = "";

	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	//机器IP
	private static String ip = ConfigUtil.getConfig("config/pay", "wxpay.ip");

	private static String notify_Url = ConfigUtil.getConfig("config/pay", "wxpay.notify_url");
	
	//以下是几个API的路径:
	//统一下单API
	public static String UNIFIED_ORDER_API=ConfigUtil.getConfig("config/pay", "wxpay.unified_order_api");
	
	//被扫支付API
	public static String SCAN_PAY_API =ConfigUtil.getConfig("config/pay", "wxpay.scan_pay_api");

	//被扫支付查询API
	public static String SCAN_PAY_QUERY_API = ConfigUtil.getConfig("config/pay", "wxpay.scan_pay_query_api");

	//退款API
	public static String REFUND_API = ConfigUtil.getConfig("config/pay", "wxpay.refund_api");

	//退款查询API
	public static String REFUND_QUERY_API = ConfigUtil.getConfig("config/pay", "wxpay.refund_api");

	//撤销API
	public static String REVERSE_API = ConfigUtil.getConfig("config/pay", "wxpay.reverse_api");

	//下载对账单API
	public static String DOWNLOAD_BILL_API = ConfigUtil.getConfig("config/pay", "wxpay.download_bill_api");

	// 统计上报API
	public static String REPORT_API = ConfigUtil.getConfig("config/pay", "wxpay.report_api");

	public static String getNotify_Url() {
		return notify_Url;
	}

	public static void setNotify_Url(String notify_Url) {
		WxConfigure.notify_Url = notify_Url;
	}

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		WxConfigure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.sandu.pay.wexin.service.HttpsRequest";

	public static void setKey(String key) {
		WxConfigure.key = key;
	}

	public static void setAppID(String appID) {
		WxConfigure.appID = appID;
	}

	public static void setMchID(String mchID) {
		WxConfigure.mchID = mchID;
	}

	public static void setSubMchID(String subMchID) {
		WxConfigure.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		WxConfigure.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		WxConfigure.certPassword = certPassword;
	}

	public static void setIp(String ip) {
		WxConfigure.ip = ip;
	}

	public static String getKey(){
		return key;
	}
	
	public static String getAppid(){
		return appID;
	}
	
	public static String getMchid(){
		return mchID;
	}

	public static String getSubMchid(){
		return subMchID;
	}
	
	public static String getCertLocalPath(){
		return certLocalPath;
	}
	
	public static String getCertPassword(){
		return certPassword;
	}

	public static String getIP(){
		return ip;
	}

	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

}
