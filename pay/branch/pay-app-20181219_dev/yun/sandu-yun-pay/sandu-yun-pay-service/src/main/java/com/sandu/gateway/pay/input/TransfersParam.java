package com.sandu.gateway.pay.input;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class TransfersParam implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
	 * 收款方openId
	 */
	private String openId;
	/**
	 * 费用,单位为分
	 */
	private Long amount;
	
	/**
	 *  企业付款描述
	 */
	private String tradeDesc;
	
	/**
	 * 调用方ip
	 */
	private String ip;
	
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
