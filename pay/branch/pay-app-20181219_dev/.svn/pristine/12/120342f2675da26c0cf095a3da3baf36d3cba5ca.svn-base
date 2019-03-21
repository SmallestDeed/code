package com.sandu.gateway.pay.input;

import java.io.Serializable;

import lombok.Data;

@Data
public class RefundParam implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final Integer SOURCE_SYSTEM=1;
	public static final Integer SOURCE_ORDER=2;
	
	
	private String originInternalTradeNo;
	
	private String originPayTradeNo;
	
	private String internalRefundNo;
	
	private String payRefundNo;
	
	private Long totalFee;
	
	private Long refundFee;
	
	private String refundDesc;
	
	
	/**
	 * 调用方ip
	 */
	private String ip;
	
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

}
