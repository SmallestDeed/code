package com.sandu.customer.service;

import java.util.List;

import com.sandu.customer.model.CustomerScoreDetail;

public interface CustomerScoreDetailService {
	
	public int deleteDetailByUserId(Integer userId);
	
	public int batchInsert(List<CustomerScoreDetail> insertList);

}
