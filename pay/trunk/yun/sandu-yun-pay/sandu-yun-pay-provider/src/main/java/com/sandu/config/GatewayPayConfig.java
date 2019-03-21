package com.sandu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GatewayPayConfig {

    public static String ALI_APP_ID;
    public static String ALI_PRIVATE_KEY;
    public static String ALI_PUBLIC_KEY;
    public static String ALI_SERVICE_URL;
    public static String ALI_NOTIFY_URL;
    public static String ALI_STORE_ID;
    public static String ALI_SELLER_ID;
    
    public static String WX_APP_ID;
    public static String WX_MCH_ID;
    public static String WX_MCH_KEY;
    public static String WX_NOTIFY_URL;
    public static String WX_REFUND_NOTIFY_URL;
  
   

    		
    @Value("${gateway.pay.ali.app_id}")
    public  void setAliAppId(String aliAppId) {
    	ALI_APP_ID = aliAppId;
    }
    
    @Value("${gateway.pay.ali.private_key}")
    public  void setAliPrivateKey(String aliPrivateKey) {
    	ALI_PRIVATE_KEY = aliPrivateKey;
    }
    
    @Value("${gateway.pay.ali.public_key}")
    public  void setAliPublicKey(String aliPublicKey) {
    	ALI_PUBLIC_KEY = aliPublicKey;
    }
    
    @Value("${gateway.pay.ali.service_url}")
    public  void setAliServiceUrl(String aliServiceUrl) {
    	ALI_SERVICE_URL = aliServiceUrl;
    }
    
    @Value("${gateway.pay.ali.notify_url}")
    public  void setAliNotifyUrl(String aliNotifyUrl) {
    	ALI_NOTIFY_URL = aliNotifyUrl;
    }
    
    @Value("${gateway.pay.ali.store_id}")
    public  void setAliStoreId(String aliStoreId) {
    	ALI_STORE_ID = aliStoreId;
    }
    
    @Value("${gateway.pay.ali.seller_id}")
    public  void setAliSellerId(String aliSellerId) {
    	ALI_SELLER_ID = aliSellerId;
    }
    
    @Value("${gateway.pay.wx.app_id}")
    public  void setWxAppId(String wxAppId) {
    	WX_APP_ID = wxAppId;
    }
    
    @Value("${gateway.pay.wx.mch_id}")
    public  void setWxMchId(String wxMchId) {
    	WX_MCH_ID = wxMchId;
    }
    
    @Value("${gateway.pay.wx.mch_key}")
    public  void setWxMchKey(String wxMchKey) {
    	WX_MCH_KEY = wxMchKey;
    }
    
    
    @Value("${gateway.pay.wx.notify_url}")
    public  void setWxNotifyUrl(String wxNotifyUrl) {
    	WX_NOTIFY_URL = wxNotifyUrl;
    }
    
    @Value("${gateway.refund.wx.notify_url}")
    public  void setWxRefundNotifyUrl(String wxRefundNotifyUrl) {
    	WX_REFUND_NOTIFY_URL = wxRefundNotifyUrl;
    }
    
    
}
