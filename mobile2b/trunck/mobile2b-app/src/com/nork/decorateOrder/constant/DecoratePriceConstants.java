package com.nork.decorateOrder.constant;

public class DecoratePriceConstants {

	/**
	 * decorate_price.status = 0: 待报价
	 */
	public final static Integer DECORATEPRICE_STATUS_NO_BID = 0;

	/**
	 * decorate_price.status = 1: 已报价
	 */
	public final static Integer DECORATEPRICE_STATUS_HAVE_BID = 1;

	/**
	 * decorate_price.status = 2: 未选中
	 */
	public final static Integer DECORATEPRICE_STATUS_UNCHECKED = 2;
	
	/**
	 * decorate_price.status = 3: 超时取消报价
	 */
	public final static Integer DECORATEPRICE_STATUS_BID_OVERTIME = 3;
	
	/**
	 * decorate_price.status = 4: 已选中
	 */
	public final static Integer DECORATEPRICE_STATUS_CHECKED = 4;
	
	/**
	 * decorate_price.is_deleted = 0: 默认未删除
	 */
	public final static Integer DECORATEPRICE_ISDELETED_DEFAULT = 0;
	
}
