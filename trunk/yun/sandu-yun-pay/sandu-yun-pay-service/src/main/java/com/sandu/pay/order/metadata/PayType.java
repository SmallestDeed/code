package com.sandu.pay.order.metadata;

/**
 * 支付方式:目前支持微信支付、支付宝、预存款支付
 * @author Administrator
 *
 */
public class PayType {
	/***
	 * 微信支付
	 */
   public final static String WXPAY="weixinpay";
   /***
    * 支付宝支付
    */
   public final static String ALIPAY="alipay";
   
   /***
    * 预存款支付
    */
   public final static String PREDEPOSIT="predeposit";

    /**
     * 包年包月支付(用户进行渲染免费)
     */
    public final static String PAY_MODEL_CONFIG_PAY = "PayModelConfigPay";

    /**
     * 度币共享支付
     */
    public final static String INTEGRAL_SHARE_PAY = "integralSharePay";

    /**
     * 包年包月支付(经销商用户进行渲染免费)
     */
    public final static String PAY_MODEL_CONFIG_PAY_FRANCHISER = "franchiserPay";

    /**
     * 品牌商家免费
     */
    public final static String PAY_MODEL_COMPANY_PAY = "payModelCompanyPay";

    /**
     * 小程序绑定手机号赠送300度币
     */
    public final static String PAY_MINPRO_GIVE_BING_MOBILE = "payMinProGiveBingMobile";


}
