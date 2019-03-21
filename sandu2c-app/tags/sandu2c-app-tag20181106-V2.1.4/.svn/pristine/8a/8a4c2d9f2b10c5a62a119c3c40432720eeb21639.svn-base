package com.sandu.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sandu.customer.dao.CustomerScoreDetailMapper;
import com.sandu.customer.model.CustomerScoreDetail;
import com.sandu.customer.service.CustomerScoreDetailService;

@Service("customerScoreDetailService")
public class CustomerScoreDetailServiceImpl implements CustomerScoreDetailService {

	@Resource
	private CustomerScoreDetailMapper customerScoreDetailMapper;

	@Override
	public int deleteDetailByUserId(Integer userId) {
		return customerScoreDetailMapper.deleteDetailByUserId(userId);
	}

	@Override
	public int batchInsert(List<CustomerScoreDetail> insertList) {
		return customerScoreDetailMapper.batchInsert(insertList);
	}
}
