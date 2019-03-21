package com.sandu.service.pay.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sandu.api.pay.model.PayOrder;
import com.sandu.api.pay.service.PayOrderService;
import com.sandu.service.pay.dao.PayOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Service("payOrderService")
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

	@Resource
	private PayOrderMapper payOrderMapper;
	
	
	@Override
	public int addPayOrder(PayOrder order) {
		return payOrderMapper.insertSelective(order);
	}

}
