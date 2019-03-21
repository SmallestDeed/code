package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DecoratePayService.pay方法的返回DTO
 * 
 * @author huangsongbo
 *
 */
@Data
public class DecoratePayServicePayReturnDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付状态
	 */
	private int payStatus = 0;
	
	/**
	 * 支付后剩余金额
	 */
	private Double currentBalance;
	
}
