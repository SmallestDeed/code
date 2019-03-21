package com.nork.decorateOrder.constant;

/**
 * DecorateSeckill 模块常量
 * 
 * @author huangsongbo
 *
 */
public class DecorateSeckillConstants {

	/**
	 * 决定是否要查询我的客户count, 平台派单count, 限时快抢count -> 1:需要
	 */
	public final static Integer DECORATESECKILLSEARCH_SEARCHTYPE_NEED = 1;
	
	/**
	 * 设置查询抢单的排序规则
	 * 查询抢单时,优先查出本人未支付的抢单, 再查为抢购的订单, 再查已抢购的订单
	 */
	public final static Integer DECORATESECKILLSEARCH_ORDERTYPE_NON_PAYMENT_ORDER_PRIORITY = 1;

	/**
	 * 设置查询抢单的排序规则
	 * 查询抢单时,优先查出未抢购的订单, 再查已抢购的订单
	 */
	public final static Integer DECORATESECKILLSEARCH_ORDERTYPE_NON_PAYMENT_ORDER_PRIORITY_2 = 2;
	
	/**
	 * 设置查询抢单的排序规则
	 * 查询抢单时,优先查出未抢购的订单, 再查已抢购的订单
	 */
	public final static Integer DECORATESECKILLSEARCH_ORDERTYPE_NON_PAYMENT_ORDER_PRIORITY_DEFAULT = 0;
	
	/**
	 * decorate_seckill.status字段
	 * status = 1: 被抢购
	 */
	public final static Integer DECORATESECKILLSEARCH_STATUS_BEING_SNAPPED_UP = 1;
	
	/**
	 * decorate_seckill.status sql字段
	 * status = 0: 未抢购
	 */
	public final static Integer DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP = 0;
	
	/**
	 * decorateSeckill.resultStatus字段
	 * resultStatus = 1: 被抢购
	 */
	public final static Integer DECORATESECKILLSEARCH_RESULTSTATUS_BEING_SNAPPED_UP = 1;
	
	/**
	 * decorateSeckill.resultStatus字段
	 * resultStatus = 0: 未抢购
	 */
	public final static Integer DECORATESECKILLSEARCH_RESULTSTATUS_NOT_BEING_SNAPPED_UP = 0;
	
	/**
	 * decorateSeckill.resultStatus字段
	 * resultStatus = 2: 待支付
	 */
	public final static Integer DECORATESECKILLSEARCH_RESULTSTATUS_UNPAID = 2;
	
	/**
	 * DecorateSeckillSeckillResultDTO.status = 1: 抢单成功
	 */
	public final static Integer DECORATESECKILLSECKILLRESULTDTO_STATUS_SUCCESS = 1;
	
	/**
	 * DecorateSeckillSeckillResultDTO.status = 2: 抢单失败
	 */
	public final static Integer DECORATESECKILLSECKILLRESULTDTO_STATUS_FAILED = 2;
	
	/**
	 * DecorateSeckillSeckillResultDTO.status = 2: 抢单失败, 今日抢单次数已经用完
	 */
	public final static Integer DECORATESECKILLSECKILLRESULTDTO_STATUS_FAILED_NO_CHANCE = 3;
	
	/**
	 * SeckillRecordFromRedisDTO.status = 0: 抢到该订单, 但是没有支付
	 */
	public final static Integer SECKILLRECORDFROMREDISDTO_STATUS_NOPAY = 0;
	
	/**
	 * SeckillRecordFromRedisDTO.status = 1: 抢到该订单, 并且已经支付
	 */
	public final static Integer SECKILLRECORDFROMREDISDTO_STATUS_PAY = 1;
	
}
