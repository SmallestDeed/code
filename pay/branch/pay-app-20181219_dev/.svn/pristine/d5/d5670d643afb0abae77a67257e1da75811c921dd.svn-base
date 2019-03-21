package com.sandu.gateway.pay.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.input.PayTradeQueryVo;
import com.sandu.gateway.pay.trade.dao.PayTradeRecordMapper;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;


@Service("payTradeRecordService")
public class PayTradeRecordServiceImpl implements PayTradeRecordService {

	@Autowired
	private PayTradeRecordMapper payTradeRecordMapper;
   
	@Override
	public Long addPayTradeRecord(PayTradeRecord payTradeRecord) {
		payTradeRecordMapper.insertSelective(payTradeRecord);
		return payTradeRecord.getId();
	}

	@Override
	public PayTradeRecord getTradeRecord(String payTradeNo) {
		return payTradeRecordMapper.getByTradeNos(payTradeNo);
	}

	@Override
	public int modifyExtenalTradeNoAndStatus(Long id, String extenalTradeNo,Integer status) {
		return payTradeRecordMapper.updateExtenalTradeNoAndStatusById(id,extenalTradeNo,status);
	}

	@Override
	public void modifyInternalNotifyResult(Long id, Integer notifyResult) {
		// TODO Auto-generated method stub
		payTradeRecordMapper.updateNotifyResultById(id, notifyResult);
	}

	@Override
	public int changeToProcessStatus(String payTradeNo) {
		// TODO Auto-generated method stub
		return payTradeRecordMapper.updateToProcessStatus(payTradeNo);
	}

	@Override
	public List<PayTradeRecord> getList(PayTradeQueryVo queryVo) {
		// TODO Auto-generated method stub
		return payTradeRecordMapper.selectList(queryVo);
	}

   
}
