package com.nork.decorateOrder.model.search;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DecorateOrderSearch implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id(decorate_order.user_id)
	 */
	private Long userId;
	
	/**
	 * 查询条件: 订单超时时间(decorate_order.order_timeout)小于orderTimeoutBefore时间
	 */
	private Date orderTimeoutBefore;
	
	/**
	 * 查询条件: 订单超时时间(decorate_order.order_timeout)大于orderTimeoutAfter时间
	 */
	private Date orderTimeoutAfter;
	
	/**
	 * 订单支付状态(decorate_order.order_status)
	 */
	private Integer orderStatus;
	
	/**
	 * 订单来源类型
	 * 0-客户店铺预约
	 * 1-抢单
	 * 2-平台自动派单
	 * 3-内部推荐
	 */
	private Integer orderType;
	
	private List<Integer> orderStatusList;
	
}
