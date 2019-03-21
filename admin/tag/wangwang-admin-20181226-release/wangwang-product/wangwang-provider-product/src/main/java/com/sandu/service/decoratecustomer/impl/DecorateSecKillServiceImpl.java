package com.sandu.service.decoratecustomer.impl;

import com.sandu.api.decoratecustomer.model.DecorateSeckill;
import com.sandu.api.decoratecustomer.service.DecorateSecKillService;
import com.sandu.service.decoratecustomer.dao.DecorateSeckillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DecorateSecKillServiceImpl implements DecorateSecKillService {

	@Autowired
	private DecorateSeckillMapper decorateSeckillMapper;

	@Override
	public void insertDecorateSedKill(DecorateSeckill decorateSeckill) {
		decorateSeckillMapper.insert(decorateSeckill);
	}

	@Override
	public List<Integer> findNoneSedKillCustomerIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
//		ids = new ArrayList<>(ids);
		List<Integer> beDispatchIds = decorateSeckillMapper.findSedKillCustomerIds(ids);
		return ids.stream().filter(it -> !beDispatchIds.contains(it)).distinct().collect(Collectors.toList());
	}

	@Override
	public void insertDecorateSeckillList(List<DecorateSeckill> installList) {
		if (installList == null || installList.isEmpty()) {
			return;
		}
		installList.forEach(it -> decorateSeckillMapper.insert(it));
	}

	@Override
	public List<DecorateSeckill> findSedKillByCustomerIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return decorateSeckillMapper.findSedKillByCustomerIds(ids);
	}

	@Override
	public Integer update(DecorateSeckill it) {
		return decorateSeckillMapper.updateByPrimaryKey(it);
	}


}
