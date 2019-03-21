package com.sandu.service.llt.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.llt.trade.model.LltRechargeLog;
import com.sandu.api.llt.trade.service.LltRechargeLogService;
import com.sandu.service.llt.trade.dao.LltRechargeLogMapper;

@Service("lltRechargeLogService")
public class LltRechargeLogServiceImpl implements LltRechargeLogService{

	@Autowired
	private LltRechargeLogMapper lltRechargeLogMapper;
	
	public void create(LltRechargeLog entity) {
		lltRechargeLogMapper.insert(entity);
	}
	
	public int modifyById(LltRechargeLog entity) {
		return lltRechargeLogMapper.updateById(entity);
	}
	 
	public LltRechargeLog get(String id) {
		return lltRechargeLogMapper.selectById(id);
	}
	
	public List<LltRechargeLog> getList(LltRechargeLog entity){
		return lltRechargeLogMapper.selectList(entity);
	}

	@Override
	public int modifyByOrderId(LltRechargeLog entity) {
		return lltRechargeLogMapper.updateByOrderId(entity);
	}

	
	
}
