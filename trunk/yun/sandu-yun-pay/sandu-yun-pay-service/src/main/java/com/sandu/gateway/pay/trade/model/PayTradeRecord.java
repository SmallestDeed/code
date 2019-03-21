package com.sandu.gateway.pay.trade.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PayTradeRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_BEGIN = 10;
	public static final Integer STATUS_NOTIFY_PROCESS = 20;
	public static final Integer STATUS_SUCCESS = 30;
	public static final Integer STATUS_FAILURE = 40;
	
	public static final Integer TRADE_TYPE_PAY = 1;
	public static final Integer TRADE_TYPE_REFUND = 2;
	
	
	public static final Integer PAY_METHOD_WX_SCANCODE = 1; //微信扫码支付
	public static final Integer PAY_METHOD_AL_SCANCODE = 2; //支付宝扫码支付

	public static final Integer PAY_METHOD_WX_MINIPRO = 3; //微信小程序支付
	
	public static final Integer PAY_METHOD_WX_APPPAY = 4;  //微信app支付
	public static final Integer PAY_METHOD_AL_APPPAY = 5;  //支付宝app支付
	
	public static final Integer PAY_METHOD_WX_H5PAY = 6;   //微信H5支付
	public static final Integer PAY_METHOD_AL_H5PAY = 7;   //支付宝H5支付
	

	public static final Integer SOURCE_SYSTEM = 1;
	public static final Integer SOURCE_ORDER = 2;
	
	public static final Integer NOTIFY_RESULT_SUCCESS = 10;
	public static final Integer NOTIFY_RESULT_FAILURE = 20;
	
	
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
	 * 交易描述
	 */
	private String tradeDesc;
	/**
	 * 支付金额
	 */
	private Long totalFee;
	
	
	/**
	  * 交易时间
	  */
	private Date  tradeDate;
	
	/**
	 * 交易类型
	 * 1:付款 2:退款
	 */
	private Integer tradeType;
	
	/**
	 * 支付方式
	 * 1.微信扫码支付,2.支付宝扫码支付
	 */
	private Integer payMethod;
	/**
	 * 业务类型
	 */
	private String  bizType;
	
	/**
	 * 回调地址
	 */
	private String notifyUrl;
	
	/**
	 * 通知结果:1.成功,2.失败
	 */
	private Integer notifyResult;
	/**
	 * 签名:使用MD5
	 */
	private String sign; 
	
	
	/**
	 * 交易状态
	 * 1处理中,2成功,3失败
	 */
	private Integer status;
	
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
