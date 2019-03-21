package com.nork.decorateOrder.service;

import java.util.List;

import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.DecorateSeckillOrder;
import com.nork.system.model.SysUser;

public interface DecorateSeckillOrderService {

	/**
	 * A用户抢到单了, 根据decorateSeckill(抢单信息)生成DecorateSeckillOrder数据
	 * 
	 * @author huangsongbo
	 * @param decorateSeckill 抢单信息
	 * @param sysUser 用户信息
	 * @return 
	 */
	DecorateSeckillOrder add(DecorateSeckill decorateSeckill, SysUser sysUser);

	/**
	 * add decorateSeckillOrder 方法
	 * 
	 * @author huangsongbo
	 * @param decorateSeckillOrder
	 */
	void add(DecorateSeckillOrder decorateSeckillOrder);

	/**
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	DecorateSeckillOrder get(Long id);

	/**
	 * 
	 * @author huangsongbo
	 * @param seckillOrderIdList
	 * @return
	 */
	List<Long> getSeckillIdListBySeckillOrderIdList(List<Long> seckillOrderIdList);

}
