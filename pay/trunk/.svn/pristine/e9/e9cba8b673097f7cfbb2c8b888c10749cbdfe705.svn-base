package com.sandu.gateway.pay.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.input.PayTradeRefundQueryVo;
import com.sandu.gateway.pay.trade.dao.PayTradeRefundRecordMapper;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;


@Service("payTradeRefundRecordService")
public class PayTradeRefundRecordServiceImpl implements PayTradeRefundRecordService {

	@Autowired
	private PayTradeRefundRecordMapper payTradeRefundRecordMapper;
   
	@Override
	public Long add(PayTradeRefundRecord payTradeRefundRecord) {
		payTradeRefundRecordMapper.insertSelective(payTradeRefundRecord);
		return payTradeRefundRecord.getId();
	}

	@Override
	public int modify(PayTradeRefundRecord payTradeRefundRecord) {
		return payTradeRefundRecordMapper.updateById(payTradeRefundRecord);
	}
	
	@Override
	public List<PayTradeRefundRecord> getList(PayTradeRefundQueryVo queryVo) {
		return payTradeRefundRecordMapper.selectList(queryVo);
	}

	@Override
	public int changeToProcessStatus(String payRefundNo) {
		// TODO Auto-generated method stub
		return payTradeRefundRecordMapper.updateToProcessStatus(payRefundNo);
	}

   
}
