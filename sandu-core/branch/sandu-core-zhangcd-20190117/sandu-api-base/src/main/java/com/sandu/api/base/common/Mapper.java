package com.sandu.api.base.common;



import lombok.Data;

import java.io.Serializable;

@Data
public class Mapper implements Serializable{
	private static final long serialVersionUID = -5084759450031494382L;
	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;


	public String getOrder() {
		return order == null ? null : Table.toClumn(order);
	}

	public String getOrders() {
		return orders == null ? null : Table.toOrders(orders);
	}

}
