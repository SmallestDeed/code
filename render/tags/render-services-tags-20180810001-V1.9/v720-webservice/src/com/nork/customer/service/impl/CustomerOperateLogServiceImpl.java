package com.nork.customer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nork.customer.dao.CustomerOperateLogMapper;
import com.nork.customer.model.CustomerOperateLog;
import com.nork.customer.service.CustomerOperateLogService;
@Service("customerOperateLogService")
public class CustomerOperateLogServiceImpl implements CustomerOperateLogService {

	@Resource
	private CustomerOperateLogMapper customerOperateLogMapper;
	
	@Override
	public int addCustomerOperateLog(CustomerOperateLog log) {
		return customerOperateLogMapper.insertSelective(log);
	}

	@Override
	public int queryPayModelGroupRef(Integer userId) {
		return customerOperateLogMapper.queryPayModelGroupRef(userId);
	}

	@Override
	public int queryAutoRenderTaskStateCount(Integer userId, String taskType) {
		return customerOperateLogMapper.queryAutoRenderTaskStateCount(userId,taskType);
	}

	@Override
	public int queryCustomerOperateLog(Integer userId, String operateType) {
		return customerOperateLogMapper.queryCustomerOperateLog(userId,operateType);
	}
}
