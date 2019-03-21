package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 返回抢单列表接口的DTO
 * 
 * @author huangsongbo
 */
@Data
public class DecorateSeckillDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 抢单订单id
	 */
	private Long id;

	/**
	 * 姓名
	 */
	private String userName;

	/**
	 * 位置信息
	 */
	private String positionInfo;

	/**
	 * 装修风格
	 */
	private String decorateStyleInfo;

	/**
	 * 装修类型info 新房装修/旧房改造
	 */
	private String decorateHouseTypeInfo;

	/**
	 * 装修方式info 半包/全包...
	 */
	private String decorateTypeInfo;

	/**
	 * 房屋面积信息
	 */
	private String houseAcreageInfo;

	/**
	 * 预算信息
	 */
	private String budgetInfo;

	/**
	 * 订单价格(需支付多少度币)
	 */
	private Long priceInfo;

	/**
	 * 订单状态: 
	 * orderStatus = 0: 待抢购 
	 * orderStatus = 1: 已抢购 
	 * orderStatus = 2: 待支付
	 */
	private Integer orderStatus;

	/**
	 * 剩余支付时间, 
	 * 格式: 毫秒数
	 */
	private Long remainingTime = 0L;

}
