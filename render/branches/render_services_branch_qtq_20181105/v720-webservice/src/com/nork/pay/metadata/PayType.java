package com.nork.pay.metadata;

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
}
