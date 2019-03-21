package com.sandu.gateway.pay.input;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class PayParam implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String PAY_METHOD_WX_SCANCODE_PAY="wxScanCodePay";
	public static final String PAY_METHOD_WX_APP_PAY="wxAppPay";
	public static final String PAY_METHOD_WX_MINI_PAY="wxMiniPay";
	public static final String PAY_METHOD_ALI_SCANCODE_PAY="aliScanCodePay";
	public static final String PAY_METHOD_ALI_APP_PAY="aliAppPay";
	
	public static final Integer SOURCE_SYSTEM=1;
	public static final Integer SOURCE_ORDER=2;
	
	/**
	 * 交易号
	 */
	private String intenalTradeNo;
	
	/**
	 * 支付网关交易号
	 */
	private String payTradeNo;
	
	/**
	 * 交易描述
	 */
	private String tradeDesc;
	/**
	 * 费用,单位为分
	 */
	private Long totalFee;
	
	/**
	 * 支付方式
	 */
	private String payMethod;
	
	/**
	 * 调用方ip
	 */
	private String ip;
	
	
	/**
	 * 重定向url H5支付使用
	 */
	private String redirectUrl;
	
	/**
	 * 回调通知url
	 */
	private String notifyUrl;
	
	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 操作人id
	 */
	private Long operator;
	
	
	/**
	 * 平台id
	 */
	private String platformCode;
	
	/**
	 * 交易来源
	 */
	private Integer source;
	
	

	/**
	 * 公用回传参数
	 */
	private String passbackParams;

}
