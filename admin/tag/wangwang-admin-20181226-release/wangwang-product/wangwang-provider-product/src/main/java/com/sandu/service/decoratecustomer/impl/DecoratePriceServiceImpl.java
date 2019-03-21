package com.sandu.service.decoratecustomer.impl;

import com.sandu.api.decoratecustomer.model.DecoratePrice;
import com.sandu.api.decoratecustomer.service.DecoratePriceService;
import com.sandu.service.decoratecustomer.dao.DecoratePriceMapper;
import com.sandu.service.decoratecustomer.dao.DecorateSeckillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-23 14:24
 */
@Slf4j
@Service("decoratePriceService")
public class DecoratePriceServiceImpl implements DecoratePriceService {

	@Autowired
	private DecoratePriceMapper decoratePriceMapper;

	@Autowired
	private DecorateSeckillMapper decorateSeckillMapper;

	@Override
	public int insert(DecoratePrice decoratePrice) {
		int result = decoratePriceMapper.insert(decoratePrice);
		if (result > 0) {
			return decoratePrice.getId();
		}
		return 0;
	}

	@Override
	public int update(DecoratePrice decoratePrice) {
		return decoratePriceMapper.updateByPrimaryKey(decoratePrice);
	}

	@Override
	public int delete(Set<Integer> decoratePriceIds) {
		return decoratePriceMapper.deleteByPrimaryKey(decoratePriceIds);
	}

	@Override
	public DecoratePrice getById(int decoratePriceId) {

		return decoratePriceMapper.selectByPrimaryKey(decoratePriceId);
	}

	@Override
	public List<DecoratePrice> listByCustomerIds(Set<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return decoratePriceMapper.listByCustomerIds(ids);
	}

	@Override
	public List<Integer> listCompanyIdForDispatchPrice(Integer proprietorInfoId, int limit) {
		if (limit < 1) {
			return Collections.emptyList();
		}
		return decoratePriceMapper.listCompanyIdForDispatchPrice(proprietorInfoId, limit);
	}

	@Override
	public List<Integer> findNoneDispatchCustomerIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<Integer> beDispatchIds = decoratePriceMapper.findDispatchCustomerIds(ids);
		return ids.stream().filter(it -> !beDispatchIds.contains(it)).distinct().collect(Collectors.toList());
	}

	@Override
	public void insertList(List<DecoratePrice> installList) {
		installList.forEach(this::insert);
	}





}
