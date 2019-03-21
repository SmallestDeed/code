package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 讲抢单记录存在redis里, 用于获取用户的今日抢单次数
 * 
 * @author huangsongbo
 *
 */
@Data
public class SeckillRecordFromRedisDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * create_time(date.getTime)
	 */
	private Long createTime;
	
	/**
	 * 本次记录的抢单状态
	 * 0: 已抢单, 未支付
	 * 1: 已支付
	 */
	private Integer status;
	
	/**
	 * 本条redis的超时时间, 超过改时间本条记录失效(date.getTime)
	 */
	private Long endTime;
	
	/**
	 * 本条redis记录的存活时间(毫秒)
	 */
	private Long survivalTime;
	
}
