package com.nork.pay.model.search;

import java.io.Serializable;

import lombok.Data;

@Data
public class PayAccountSearch implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分页start
	 */
	private Long start;
	
	/**
	 * 分页limit
	 */
	private Long limit;
	
	/**
	 * pay_account.user_id: 用户id
	 */
	private Integer userId;
	
	/**
	 * pay_account.platform_bussiness_type: 平台类型
	 */
	private String platformBussinessType;
	
}
