package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DecorateSeckillUpdateStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 抢单id
	 */
	private Long id;
	
	/**
	 * 想要修改为 ? 状态
	 * status = 8: 已取消
	 */
	private Integer status;
	
}
