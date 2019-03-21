package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 抢单详情接口DTO
 * 
 * @author huangsongbo
 *
 */
@Data
public class DecorateSeckillGetDetailsDTO implements Serializable {

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
	 * 房屋信息
	 */
	private String houseInfo;
	
	/**
	 * 装修信息
	 */
	private String decorateInfo;
	
	/**
	 * 预算
	 */
	private String budgetInfo;
	
	/**
	 * 来源
	 */
	private String clientSource;
	
	/**
	 * 三度备注
	 */
	private String remark;
	
	/**
	 * 客单价
	 */
	private String priceInfo;
	
	/**
	 * 订单状态
	 * orderStatus = 0: 待抢购
	 * orderStatus = 1: 已抢购
	 * orderStatus = 2: 待支付
	 */
	private Integer orderStatus;
	
	/**
	 * 业主手机号
	 */
	private String mobile;
	
	/**
	 * 剩余支付时间
	 * 单位: 毫秒数
	 */
	private Long remainingTime = 0L;
	
}
