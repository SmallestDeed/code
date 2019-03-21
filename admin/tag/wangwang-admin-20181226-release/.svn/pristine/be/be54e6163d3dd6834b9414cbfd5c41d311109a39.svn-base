package com.sandu.api.decoratecustomer.service;

import com.sandu.api.decoratecustomer.model.DecoratePrice;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-23 14:24
 */
public interface DecoratePriceService {

	/**
	 * 插入
	 *
	 * @param decoratePrice
	 * @return
	 */
	int insert(DecoratePrice decoratePrice);

	/**
	 * 更新
	 *
	 * @param decoratePrice
	 * @return
	 */
	int update(DecoratePrice decoratePrice);

	/**
	 * 删除
	 *
	 * @param decoratePriceIds
	 * @return
	 */
	int delete(Set<Integer> decoratePriceIds);

	/**
	 * 通过ID获取详情
	 *
	 * @param decoratePriceId
	 * @return
	 */
	DecoratePrice getById(int decoratePriceId);


	List<DecoratePrice> listByCustomerIds(Set<Integer> ids);

	/**
	 * 根据业主获取同城装企
	 *
	 * @param proprietorInfoId
	 * @param limit
	 * @return
	 */
	List<Integer> listCompanyIdForDispatchPrice(Integer proprietorInfoId, int limit);

	List<Integer> findNoneDispatchCustomerIds(List<Integer> ids);

	void insertList(List<DecoratePrice> installList);

}
