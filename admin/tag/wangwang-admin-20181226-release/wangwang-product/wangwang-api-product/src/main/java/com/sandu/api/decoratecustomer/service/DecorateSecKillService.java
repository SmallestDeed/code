package com.sandu.api.decoratecustomer.service;

import com.sandu.api.decoratecustomer.model.DecorateSeckill;

import java.util.List;

public interface DecorateSecKillService {

	void insertDecorateSedKill(DecorateSeckill decorateSeckill);

	/**
	 * 根据客户ID list  过滤出 未参加过抢单的 客户ID
	 *
	 * @param collect
	 * @return
	 */
	List<Integer> findNoneSedKillCustomerIds(List<Integer> collect);


	void insertDecorateSeckillList(List<DecorateSeckill> installList);

	/**
	 * 根据客户ID查询抢单记录
	 * @param asList
	 * @return
	 */
	List<DecorateSeckill> findSedKillByCustomerIds(List<Integer> asList);

	Integer update(DecorateSeckill it);
}
