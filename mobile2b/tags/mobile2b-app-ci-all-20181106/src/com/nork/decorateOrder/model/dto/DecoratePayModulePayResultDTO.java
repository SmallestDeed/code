package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DecoratePayModulePayResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付状态
	 * 0: 支付成功
	 * 1: 支付失败, 系统异常
	 * 2: 支付失败, 余额不足
	 * 3: 支付失败, 该订单已被支付, 无需重复支付
	 * 4: 支付失败, 不能支付非本人订单
	 */
	private Integer payStatus;
	
	/**
	 * 支付返回信息
	 */
	private String message;

	/**
	 * 装修订单id
	 */
	private Long orderId;
	
	public DecoratePayModulePayResultDTO(Integer payStatus, String message, Long orderId) {
		super();
		this.payStatus = payStatus;
		this.message = message;
		this.orderId = orderId;
	}
	
}
