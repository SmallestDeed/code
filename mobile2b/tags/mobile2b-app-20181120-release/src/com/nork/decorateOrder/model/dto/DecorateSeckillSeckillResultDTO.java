package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 抢单接口对应的抢单结果DTO
 * 
 * @author huangsongbo
 *
 */
@Data
public class DecorateSeckillSeckillResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 抢单结果状态
	 * status = 1: 抢单成功
	 * status = 2: 抢单失败,已被别人抢去
	 */
	private Integer status;
	
	/**
	 * 备注信息
	 */
	private String message;

	/**
	 * 剩余支付时间, 
	 * 格式: 毫秒数
	 */
	private Long remainingTime = 0L;
	
	public DecorateSeckillSeckillResultDTO(Integer status, String message, Long remainingTime) {
		super();
		this.status = status;
		this.message = message;
		if(remainingTime != null) {
			this.remainingTime = remainingTime;
		}
	}
	
}
