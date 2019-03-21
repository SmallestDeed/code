package com.sandu.gateway.pay.trade.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PayTradeRefundRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_BEGIN = 10;
	public static final Integer STATUS_NOTIFY_PROCESS = 20;
	public static final Integer STATUS_SUCCESS = 30;
	public static final Integer STATUS_FAILURE = 40;
	
	public static final Integer TRADE_TYPE_PAY = 1;
	public static final Integer TRADE_TYPE_REFUND = 2;
	

	public static final Integer SOURCE_SYSTEM = 1;
	public static final Integer SOURCE_ORDER = 2;
	
	public static final Integer NOTIFY_RESULT_SUCCESS = 10;
	public static final Integer NOTIFY_RESULT_FAILURE = 20;
	
	
	  
	/**
	 * id
	 */
	private Long id;
	/**
	 * 原支付系统生成的交易号
	 */
	private String originPayTradeNo;
	
	/**
	 * 原内部交易号,(订单,充值...)模块传过来的交易号,通常是订单号
	 */
	private String originInternalTradeNo;
	
	/**
	 * 原外部交易号(阿里,腾讯的交易号...)
	 */
	private String originExternalTradeNo;
	
	/**
	 * 原交易时间
	 */
	private Date originTradeDate;
	
	/**
	 * 原交易描述
	 */
	private String originTradeDesc; 
	  
	/**
	 * 原交易总金额，单位为分
	 */
	private Long originTotalFee;
	
	/**
	 * 内部系统生成的退款单号
	 */
	private String internalRefundNo;
	
	/**
	 * 支付网关生成的退款单号
	 */
	private String  payRefundNo;
	
	/**
	 * 外部系统生成的退款单号(阿里,腾讯)
	 */
	private String externalRefundNo;
	
	/**
	 * 退款金额，单位为分
	 */
	private Long refundFee; 
	
	/**
	 * 退款描述
	 */
	private String refundDesc;
	
	/**
	 * 退款状态
	 */
	private String refundStatus;
	
	/**
	 * 退款成功时间
	 */
	private String refundSuccessTime;
	
	/**
	 * '签名:使用MD5'
	 */
	private String sign;
	
	/**
	 * '交易状态:10.开始,20.回调处理中,30.成功,40.失败',
	 */
	private Integer status;
	
	/**
	 * 各个模块传过来的回调地址
	 */
	private String notifyUrl;
	
	/**
	 * 通知结果:10.成功,20.失败
	 */
	private Integer notifyResult; 
	
 
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
