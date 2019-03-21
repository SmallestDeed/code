package com.nork.common.model;

import com.nork.common.util.Table;

public class SortCond {

	/**
	 * 排序类型
	 */
	private Order order;

	/**
	 * 排序字段
	 */
	private String column;

	/**
	 * 构造方法,默认按ASC排序
	 * 
	 * @param column
	 */
	public SortCond(String column) {
		this(column, Order.ASC);
	}

	/**
	 * 构造方法,默认按ASC排序,传入order参数"desc",按desc排序
	 * 
	 * @param column
	 * @param order
	 *            字符串排序参数
	 */
	public SortCond(String column, String order) {
		this(column, "desc".equals(order) ? Order.DESC : Order.ASC);
	}

	/**
	 * 构造方法,默认按ASC排序,传入枚举参数Order排序
	 * 
	 * @param column
	 * @param order
	 *            枚举排序参数
	 */
	public SortCond(String column, Order order) {
		this.column = column;
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getColumn() {
		return null == column ? null : Table.toClumn(column);
	}

	public void setColumn(String column) {
		this.column = column;
	}
}
