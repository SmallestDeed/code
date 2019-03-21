package com.sandu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceConfig {

    public static String WX_PAY_NOTIFY_URL;

    public static String ALIPAY_NOTIFY_URL;

    public static String IMAGE_SERVER_URL;

    public static String WEB_SOCKET_SERVER_URL;

    public static String UPLOAD_ROOT; //生成二维码路径

    public static String NOTIFY_URL_MOBILE_PAY;  //开通移动端微信h5支付回调url

    public static String ALIPAY_PRIVATE_KEY_MOBILE_PAY; // 支付宝私钥（开通移动端）

    public static String ALIPAY_PUBLIC_KEY_MOBILE_PAY; // 支付宝公钥（开通移动端）

    public static String ALIPAY_NOTIFY_URL_MOBILE_PAY; // 接收支付宝通知结果页面地址(开通移动端)

    public static String ALIPAY_RETURN_URL; // 支付宝同步地址（HTTP/HTTPS开头字符串）(开通移动端)

    public static String WXPAY_REDIRECT_URL; //  微信h5支付同步的url

    public static String ALIPAY_PAY_MODEL_NOTIFY_URL; // 包年包月通用版支付宝扫码回调url

    public static String WXPAY_PAY_MODEL_NOTIFY_URL; // 包年包月通用版微信扫码回调url

 	public static String WXPAY_APPPAY_APPID; //微信app支付appId
    		
    public static String WXPAY_APPPAY_MUCHID; //微信app支付muchId

    public static String AGENCY_WXPAY_APPPAY_MUCHID; //微信app支付muchId

    public static String WXPAY_APPPAY_KEY; //密钥(商户的API安全)

    public static String AGENCY_WXPAY_APPPAY_KEY; //密钥(商户的API安全)

    public static String ALIPAY_INTEGRAL_SHARE_NOTIFY_URL; // 度币共享的支付宝扫码回调url

    public static String WXPAY_INTEGRAL_SHARE_NOTIFY_URL; // 度币共享的微信扫码回调url

    public static String WXPAY_MINI_RETURN_URL; //微信小程序下单的回调url

    public static String AGENCY_WXPAY_APPPAY_APPID;

    @Value("${agency.wxpay.appPay.appid}")
    public void setAgencyWxpayApppayAppid(String AgencyWxpayApppayAppid){
        AGENCY_WXPAY_APPPAY_APPID = AgencyWxpayApppayAppid;
    }

    @Value("${wxpay.mini_return_url}")
    public  void setWxpayMiniReturnUrl(String wxpayMiniReturnUrl) {
        WXPAY_MINI_RETURN_URL = wxpayMiniReturnUrl;
    }

    @Value("${wxpay.appPay.muchId}")
    public void setWxPayAppPayMuchId(String wxPayAppPayMuchId) {
    	WXPAY_APPPAY_MUCHID = wxPayAppPayMuchId;
    }

    @Value("${agency.wxpay.appPay.muchId}")
    public void setAgencyWxPayAppPayMuchId(String agencyWxPayAppPayMuchId) {
        AGENCY_WXPAY_APPPAY_MUCHID = agencyWxPayAppPayMuchId;
    }
    
    @Value("${wxpay.appPay.key}")
    public void setWxPayAppPayKey(String wxPayAppPayKey) {
    	WXPAY_APPPAY_KEY = wxPayAppPayKey;
    }

    @Value("${agency.wxpay.appPay.key}")
    public void setAgencyWxPayAppPayKey(String aencyWxPayAppPayKey) {
        AGENCY_WXPAY_APPPAY_KEY = aencyWxPayAppPayKey;
    }

    @Value("${wxpay.notify_url}")
    public void setWxPayNotifyUrl(String wxPayNotifyUrl) {
        WX_PAY_NOTIFY_URL = wxPayNotifyUrl;
    }

    @Value("${alipay.notify_url}")
    public void setAliPayNotifyUrl(String aliPayNotifyUrl) {
        ALIPAY_NOTIFY_URL = aliPayNotifyUrl;
    }

    @Value("${image.server.url}")
    public void setImageServerUrl(String imageServerUrl) {
        IMAGE_SERVER_URL = imageServerUrl;
    }

    @Value("${app.webSocket.payOrder}")
    public void setWebSocketServerUrl(String webSocketServerUrl) {
        WEB_SOCKET_SERVER_URL = webSocketServerUrl;
    }

    @Value("${aes.resources.encrypt.upload.root}")
    public void setUpladRoot(String upladRoot) {
        UPLOAD_ROOT = upladRoot;
    }

    @Value("${wxpay.notify_url_mobile_pay}")
    public void setNotifyUrlMobilePay(String notifyUrlMobilePay) {
        NOTIFY_URL_MOBILE_PAY = notifyUrlMobilePay;
    }


    @Value("${wxpay.appPay.appId}")
    public void setWxPayAppPayAppId(String wxPayAppPayAppId) {
        WXPAY_APPPAY_APPID = wxPayAppPayAppId;
    }

    @Value("${alipay.private_key_mobile_pay}")
    public void setAlipayPrivateKeyMobilePay(String alipayPrivateKeyMobilePay) {
        ALIPAY_PRIVATE_KEY_MOBILE_PAY = alipayPrivateKeyMobilePay;
    }

    @Value("${alipay.public_key_mobile_pay}")
    public void setAlipayPublicKeyMobilePay(String alipayPublicKeyMobilePay) {
        ALIPAY_PUBLIC_KEY_MOBILE_PAY = alipayPublicKeyMobilePay;
    }

    @Value("${alipay.notify_url_mobile_pay}")
    public void setAlipayNotifyUrlMobilePay(String alipayNotifyUrlMobilePay) {
        ALIPAY_NOTIFY_URL_MOBILE_PAY = alipayNotifyUrlMobilePay;
    }

    @Value("${alipay.return_url}")
    public void setAlipayReturnUrl(String alipayReturnUrl) {
        ALIPAY_RETURN_URL = alipayReturnUrl;
    }

    @Value("${wxpay.redirect_url}")
    public void setWxpayRedirectUrl(String wxpayRedirectUrl) {WXPAY_REDIRECT_URL = wxpayRedirectUrl; }


    @Value("${wxpay.pay_model_notify_url}")
    public  void setWxpayPayModelNotifyUrl(String wxpayPayModelNotifyUrl) {
        WXPAY_PAY_MODEL_NOTIFY_URL = wxpayPayModelNotifyUrl;
    }

    @Value("${alipay.pay_model_notify_url}")
    public  void setAlipayPayModelNotifyUrl(String alipayPayModelNotifyUrl) {
        ALIPAY_PAY_MODEL_NOTIFY_URL = alipayPayModelNotifyUrl;
    }
    @Value("${alipay.integral_share_notify_url}")
    public  void setAlipayIntegralShareNotifyUrl(String alipayIntegralShareNotifyUrl) {
        ALIPAY_INTEGRAL_SHARE_NOTIFY_URL = alipayIntegralShareNotifyUrl;
    }
    @Value("${wxpay.integral_share_notify_url}")
    public  void setWxpayIntegralShareNotifyUrl(String wxpayIntegralShareNotifyUrl) {
        WXPAY_INTEGRAL_SHARE_NOTIFY_URL = wxpayIntegralShareNotifyUrl;
    }

}
