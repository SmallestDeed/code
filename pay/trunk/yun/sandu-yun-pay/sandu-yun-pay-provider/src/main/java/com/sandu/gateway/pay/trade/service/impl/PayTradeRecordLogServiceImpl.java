package com.sandu.gateway.pay.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.trade.dao.PayTradeRecordLogMapper;
import com.sandu.gateway.pay.trade.model.PayTradeRecordLog;
import com.sandu.gateway.pay.trade.service.PayTradeRecordLogService;


@Service("payTradeRecordLogService")
public class PayTradeRecordLogServiceImpl implements PayTradeRecordLogService {

    
    @Autowired
    private PayTradeRecordLogMapper payTradeRecordLogMapper;

    @Override
    public Long addPayTradeRecordLog(PayTradeRecordLog payTradeRecordLog) {
        payTradeRecordLogMapper.insertSelective(payTradeRecordLog);
        return payTradeRecordLog.getId();
    }

	@Override
	public void saveExternalNotifyBody(Long tradeId, String jsonNotifyBody) {
		// TODO Auto-generated method stub
		payTradeRecordLogMapper.updateExtenalNotifyBodyByTradeId(tradeId, jsonNotifyBody);
	}

	@Override
	public void saveInternalSystemNotifyLog(Long tradeId, String request, String response) {
		// TODO Auto-generated method stub
		payTradeRecordLogMapper.updateInternalNotifyReqAndRespByByTradeId(tradeId,request,response);
	}

   
}
