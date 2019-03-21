package com.nork.decorateOrder.model.search;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 用于创建搜索条件
 * 
 * @author huangsongbo
 *
 */
@Data
public class DecorateSeckillSearch implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 决定是否要查询我的客户count, 平台派单count, 限时快抢count
	 * searchType = 1: 需要查询
	 */
	private Integer searchType;
	
	/**
	 * 决定如何排序
	 * orderType = 1: 会把我的抢到的单,并且未支付的,排到最前面
	 * 默认排序: 未抢订单 > 已抢订单
	 */
	private Integer orderType;
	
	/**
	 * 分页start
	 */
	private Integer start;
	
	/**
	 * 分页limit
	 */
	private Integer limit;
	
	/**
	 * 登录用户id
	 */
	private Long userId;
	
	/**
	 * select * from decorate_seckill where id not in (?)
	 */
	private List<Long> idListNotIn;
	
	/**
	 * select * from decorate_seckill where id in (?)
	 */
	private List<Long> idList;
	
	/**
	 * decorate_seckill.status: 订单状态
	 */
	private Integer status;
	
	/**
	 * proprietor_info.city_code: 市code
	 */
	private String cityCode;
	
	/**
	 * proprietor_info.decorate_budget: 预算value
	 */
	private Integer decorateBudgetValue;
	
	/**
	 * decorateTypeValue.decorate_type: 装修类型value值(关联数据字典(type = decorateType)的value值)
	 */
	private Integer decorateTypeValue;
	
	/**
	 * proprietor_info.decorate_style: 装修风格id
	 */
	private Long baseProductStyleId;
	
	/**
	 * decorate_seckill.is_deleted
	 */
	private Integer isDeleted;
	
}
