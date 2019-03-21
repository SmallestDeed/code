package com.sandu.gateway.pay.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.trade.dao.PayTradeTransfersRecordLogMapper;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecordLog;
import com.sandu.gateway.pay.trade.service.PayTradeTransfersRecordLogService;


@Service("payTradeTransfersRecordLogService")
public class PayTradeTransfersRecordLogServiceImpl implements PayTradeTransfersRecordLogService {
    
    @Autowired
    private PayTradeTransfersRecordLogMapper payTradeTransfersRecordLogMapper;

	@Override
	public Long add(PayTradeTransfersRecordLog payTradeTransfersRecordLog) {
		
		payTradeTransfersRecordLogMapper.insertSelective(payTradeTransfersRecordLog);
		return payTradeTransfersRecordLog.getId();
	}
   
}
