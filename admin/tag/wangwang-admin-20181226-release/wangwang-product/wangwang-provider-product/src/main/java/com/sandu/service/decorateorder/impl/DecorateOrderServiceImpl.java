package com.sandu.service.decorateorder.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.decorateorder.input.DecorateOrderQuery;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.model.DecorateOrderScore;
import com.sandu.api.decorateorder.service.DecorateOrderService;
import com.sandu.service.decorateorder.dao.DecorateOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * DecorateOrder
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Slf4j
@Service("decorateOrderService")
public class DecorateOrderServiceImpl implements DecorateOrderService {

	@Autowired
	private DecorateOrderMapper decorateOrderMapper;

	@Override
	public int insert(DecorateOrder DecorateOrder) {
		int result = decorateOrderMapper.insert(DecorateOrder);
		if (result > 0) {
			return DecorateOrder.getId();
		}
		return 0;
	}

	@Override
	public int update(DecorateOrder DecorateOrder) {
		return decorateOrderMapper.updateByPrimaryKey(DecorateOrder);
	}

	@Override
	public int delete(Set<Integer> DecorateOrderIds) {
		return decorateOrderMapper.deleteByPrimaryKey(DecorateOrderIds);
	}

	@Override
	public DecorateOrder getById(int DecorateOrderId) {
		return decorateOrderMapper.selectByPrimaryKey(DecorateOrderId);
	}

	@Override
	public PageInfo<DecorateOrder> findAll(DecorateOrderQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		PageInfo<DecorateOrder> pageInfo = new PageInfo<>(decorateOrderMapper.findAll(query));
		return pageInfo;
	}

	@Override
	public int uploadOrder(DecorateOrder decorateOrder) {
		return decorateOrderMapper.updateByPrimaryKey(decorateOrder);
	}

	@Override
	public List<DecorateOrder> queryContractOrderList(DecorateOrderQuery query) {
		return decorateOrderMapper.selectContractOrderList(query);
	}

	@Override
	public List<DecorateOrder> queryRefundOrderList(DecorateOrderQuery query) {
		return decorateOrderMapper.selectRefundOrderList(query);
	}

	@Override
	public List<DecorateOrder> listOrderByCompanyId(Integer companyId) {
		return decorateOrderMapper.listOrderByCompanyId(companyId);
	}

	@Override
	public List<DecorateOrderScore> listOrderScoreByCompanyId(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return decorateOrderMapper.listOrderScoreByCompanyId(ids);
	}

	@Override
	public List<DecorateOrder> listOrderByCustomerId(Integer id) {
		return decorateOrderMapper.listOrderByCustomerId(id);
	}

	@Override
	public DecorateOrder findOrderByPriceId(Integer id) {
		return decorateOrderMapper.findOrderByPriceId(id);
	}
}
