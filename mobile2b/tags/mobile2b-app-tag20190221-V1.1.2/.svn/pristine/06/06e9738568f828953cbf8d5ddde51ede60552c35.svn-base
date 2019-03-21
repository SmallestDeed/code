package com.nork.decorateOrder.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nork.decorateOrder.constant.DecoratePayConstants.DECORATEPAYDTO_ORDERTYPE_ENUM;
import com.nork.system.model.SysUser;

import lombok.Data;

@Data
public class DecoratePayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 待扣款金额(与pay_account.balance_amount单位一致, feeAmount / 10 = 度币; 度币 / 10 = 元)
	 */
	private Double feeAmount;
	
	/**
	 * 付款人信息
	 */
	private SysUser sysUser;
	
	/**
	 * 支付类型
	 * 请使用PayOrderConstants.PAYORDER_PRODUCTTYPE_*
	 */
	private String payType;
	
	/**
	 * 该订单的当前支付状态
	 * payStatus = 0: 未支付
	 * payStatus = 1: 已支付
	 * 请使用DecoratePayConstants.DECORATEPAYDTO_PAYSTATUS_* 常量
	 */
	private int payStatus = 0;
	
	/**
	 * 支付事件一些说明
	 */
	private String payMessage;
	
	/**
	 * 支付事件更详细的说明
	 */
	private String payDesc;
	
	/**
	 * 用户扣完此次费用的余额(单位: 元)
	 */
	private BigDecimal currentBalance;
	
	/**
	 * 平台派单/限时快抢
	 */
	private DECORATEPAYDTO_ORDERTYPE_ENUM orderType = DECORATEPAYDTO_ORDERTYPE_ENUM.decoratePrice;
	
	/**
	 * 装修订单的id(decorate_order.id)
	 */
	private Long decorateOrderId;
	
	/**
	 * 装修客户id(decorate_customer.id)
	 * decorate_seckill.customer_id or decorate_price.customer_id
	 */
	private Long customerId;
	
}
