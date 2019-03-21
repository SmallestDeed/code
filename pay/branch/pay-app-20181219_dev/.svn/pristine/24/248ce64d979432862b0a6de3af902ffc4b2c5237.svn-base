package com.sandu.gateway.pay.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.trade.dao.PayTradeRefundRecordLogMapper;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecordLog;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordLogService;


@Service("payTradeRefundRecordLogService")
public class PayTradeRefundRecordLogServiceImpl implements PayTradeRefundRecordLogService {

    
    @Autowired
    private PayTradeRefundRecordLogMapper payTradeRefundRecordLogMapper;

	@Override
	public Long add(PayTradeRefundRecordLog payTradeRefundRecordLog) {
		
		payTradeRefundRecordLogMapper.insertSelective(payTradeRefundRecordLog);
		return payTradeRefundRecordLog.getId();
	}

	@Override
	public void saveExternalNotifyBody(Long tradeRefundId, String jsonNotifyBody) {
		
		payTradeRefundRecordLogMapper.updateExtenalNotifyBodyByTradeId(tradeRefundId, jsonNotifyBody);
	}

	@Override
	public void saveInternalSystemNotifyLog(Long tradeRefundId, String request, String response) {
		
		payTradeRefundRecordLogMapper.updateInternalNotifyReqAndRespByByTradeId(tradeRefundId, request, response);
		
	}

   

   
}
