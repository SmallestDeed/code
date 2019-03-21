package com.sandu.gateway.pay.trade.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PayTradeTransfersRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_BEGIN = 10;
	public static final Integer STATUS_SUCCESS = 30;
	public static final Integer STATUS_FAILURE = 40;
	
	
	public static final Integer PAY_METHOD_WX_MINIPRO = 1; //微信小程序付款
	

	public static final Integer SOURCE_SYSTEM = 1;
	public static final Integer SOURCE_ORDER = 2;
	
	
	/**
	 * id
	 */
	private Long id;
	/**
	 * 支付系统生成的交易号
	 */
	private String payTradeNo;
	
	/**
	 * 内部交易号,(订单,充值...)模块传过来的交易号,通常是订单号
	 */
	private String intenalTradeNo;
	
	/**
	 * 外部交易号(阿里,腾讯的交易号...)
	 */
	private String extenalTradeNo;
	
	/**
	 * 付款方式:1.微信小程序付款
	 */
	private Integer payMethod;
	
	/**
	 * 付款金额
	 */
	private Long amount;
	
	/**
	  * 交易时间
	  */
	private Date  tradeDate;
	
	/**
	 * 付款描述
	 */
	private String tradeDesc;
	
	/**
	 * 收款人微信openid
	 */
	private String openId;
	
	/**
	 * 签名:使用MD5
	 */
	private String sign; 
	
	
	/**
	 * 交易状态:10.开始,30.成功,40.失败
	 */
	private Integer status;
	
	/**
	 * 付款成功时间
	 */
	private String paymentTime;
	
 
	/**
	 * ip
	 */
	private String ip;
	
	
	/**
	 * 平台编码
	 */
	private String platformCode; 
	/**
	 * 交易来源(1.基础服务,2.订单服务...)
	 */
	private Integer source;
	/**
	 * 操作人ID
	 */
	private Long operator;
	/**
	 * 系统编码
	 */
	private String sysCode; 
	/**
	 * 创建者
	 */
	private String creator; 
	/**
	 * 创建时间
	 */
	private Date gmtCreate; 
	/**
	 * 修改人
	 */
	private String modifier; 
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	/**
	 * 是否删除
	 */
	private Integer isDeleted; 
	/**
	 * 备注
	 */
	private String remark;
	
}
