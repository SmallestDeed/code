package com.sandu.pay.alipay.common;

import com.sandu.common.util.ConfigUtil;

public class AlipayConfig {
	private static String app_id = ConfigUtil.getConfig("/config/pay", "alipay.app_id");

	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	private static String partner = ConfigUtil.getConfig("/config/pay", "alipay.partner");
	// 商户的私钥
	private static String private_key = ConfigUtil.getConfig("/config/pay", "alipay.private_key");

	private static String key = ConfigUtil.getConfig("/config/pay", "alipay.key");

	private static String open_api_domain = ConfigUtil.getConfig("/config/pay", "alipay.open_api_domain");
	private static String mcloud_api_domain = ConfigUtil.getConfig("/config/pay", "alipay.mcloud_api_domain");
	// 支付宝的公钥，无需修改该值
	private static String public_key = ConfigUtil.getConfig("/config/pay", "alipay.public_key");

	private static int max_query_retry = Integer.parseInt(ConfigUtil.getConfig("/config/pay", "alipay.max_query_retry"));
	private static int max_cancel_retry = Integer.parseInt(ConfigUtil.getConfig("/config/pay", "alipay.max_cancel_retry"));
	private static int heartbeat_delay = Integer.parseInt(ConfigUtil.getConfig("/config/pay", "alipay.heartbeat_delay"));
	private static long query_duration = Long.parseLong(ConfigUtil.getConfig("/config/pay", "alipay.query_duration"));
	private static long cancel_duration = Long.parseLong(ConfigUtil.getConfig("/config/pay", "alipay.cancel_duration"));
	private static long heartbeat_duration = Long.parseLong(ConfigUtil.getConfig("/config/pay", "alipay.heartbeat_duration"));
	
	private static String service = ConfigUtil.getConfig("/config/pay", "alipay.service");

	private static String notify_url = ConfigUtil.getConfig("/config/pay", "alipay.notify_url");

	private static String refund_notify_url = ConfigUtil.getConfig("/config/pay", "alipay.refund_notify_url");

	private static String seller_id = ConfigUtil.getConfig("/config/pay", "alipay.seller_id");

	private static String seller_email = ConfigUtil.getConfig("/config/pay", "alipay.seller_email");

	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	private static String log_path = "E://pay";

	// 签名方式
	private static String sign_type = "RSA";

	private static String refund_sign_type = "MD5";

	// 字符编码格式 目前支持 gbk 或 utf-8
	private static String input_charset = "UTF-8";



	//加密方式
	private static String sign_type_two = "RSA2";
	private static String format = "json";
	private static String product_code = "QUICK_WAP_PAY";

	public static String getSign_type_two() {
		return sign_type_two;
	}
	public static String getFormat() {
		return format;
	}
	public static String getProduct_code() {
		return product_code;
	}

	
	public static String getPartner() {
		return partner;
	}

	public static String getPrivate_key() {
		return private_key;
	}

	public static String getPublic_key() {
		return public_key;
	}

	public static String getService() {
		return service;
	}

	public static String getNotify_url() {
		return notify_url;
	}

	public static String getSeller_id() {
		return seller_id;
	}

	public static String getLog_path() {
		return log_path;
	}

	public static String getSign_type() {
		return sign_type;
	}

	public static String getInput_charset() {
		return input_charset;
	}

	public static String getRefund_sign_type() {
		return refund_sign_type;
	}

	public static void setRefund_sign_type(String refund_sign_type) {
		AlipayConfig.refund_sign_type = refund_sign_type;
	}

	public static String getRefund_notify_url() {
		return refund_notify_url;
	}

	public static void setRefund_notify_url(String refund_notify_url) {
		AlipayConfig.refund_notify_url = refund_notify_url;
	}

	public static String getSeller_email() {
		return seller_email;
	}

	public static void setSeller_email(String seller_email) {
		AlipayConfig.seller_email = seller_email;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		AlipayConfig.key = key;
	}

	public static String getApp_id() {
		return app_id;
	}

	public static void setApp_id(String app_id) {
		AlipayConfig.app_id = app_id;
	}

	public static int getMax_query_retry() {
		return max_query_retry;
	}

	public static void setMax_query_retry(int max_query_retry) {
		AlipayConfig.max_query_retry = max_query_retry;
	}

	public static int getMax_cancel_retry() {
		return max_cancel_retry;
	}

	public static void setMax_cancel_retry(int max_cancel_retry) {
		AlipayConfig.max_cancel_retry = max_cancel_retry;
	}

	public static int getHeartbeat_delay() {
		return heartbeat_delay;
	}

	public static void setHeartbeat_delay(int heartbeat_delay) {
		AlipayConfig.heartbeat_delay = heartbeat_delay;
	}

	public static long getQuery_duration() {
		return query_duration;
	}

	public static void setQuery_duration(long query_duration) {
		AlipayConfig.query_duration = query_duration;
	}

	public static long getCancel_duration() {
		return cancel_duration;
	}

	public static void setCancel_duration(long cancel_duration) {
		AlipayConfig.cancel_duration = cancel_duration;
	}

	public static long getHeartbeat_duration() {
		return heartbeat_duration;
	}

	public static void setHeartbeat_duration(long heartbeat_duration) {
		AlipayConfig.heartbeat_duration = heartbeat_duration;
	}

	public static String getOpen_api_domain() {
		return open_api_domain;
	}

	public static void setOpen_api_domain(String open_api_domain) {
		AlipayConfig.open_api_domain = open_api_domain;
	}

	public static String getMcloud_api_domain() {
		return mcloud_api_domain;
	}

	public static void setMcloud_api_domain(String mcloud_api_domain) {
		AlipayConfig.mcloud_api_domain = mcloud_api_domain;
	}

}
