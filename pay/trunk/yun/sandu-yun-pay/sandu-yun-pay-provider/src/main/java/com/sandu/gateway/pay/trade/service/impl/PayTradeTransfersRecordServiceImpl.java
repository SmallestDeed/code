package com.sandu.gateway.pay.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.trade.dao.PayTradeTransfersRecordMapper;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;
import com.sandu.gateway.pay.trade.service.PayTradeTransfersRecordService;


@Service("payTradeTransfersRecordService")
public class PayTradeTransfersRecordServiceImpl implements PayTradeTransfersRecordService {

	@Autowired
	private PayTradeTransfersRecordMapper payTradeTransfersRecordMapper;
   
	@Override
	public Long add(PayTradeTransfersRecord payTradeTransfersRecord) {
		payTradeTransfersRecordMapper.insertSelective(payTradeTransfersRecord);
		return payTradeTransfersRecord.getId();
	}

	@Override
	public int modify(PayTradeTransfersRecord payTradeTransfersRecord) {
		return payTradeTransfersRecordMapper.updateById(payTradeTransfersRecord);
	}
	
	/*@Override
	public List<PayTradeTransfersRecord> getList(PayTradeTransfersQueryVo queryVo) {
		return payTradeTransfersRecordMapper.selectList(queryVo);
	}*/

	

   
}
