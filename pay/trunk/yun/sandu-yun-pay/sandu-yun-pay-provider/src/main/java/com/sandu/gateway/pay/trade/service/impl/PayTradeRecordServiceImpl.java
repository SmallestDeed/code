package com.sandu.gateway.pay.trade.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
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

	@Override
	public String getTradeRecordByIntenalTradeNo(String internalTradeNo) {
		if(StringUtils.isBlank(internalTradeNo)) {
			return null;
		}
		List<PayTradeRecord> list = this.queryPayTradeRecord("internalTradeNo",internalTradeNo);
		if (list != null && list.size() > 0) {
			return list.get(0).getPayTradeNo();
		}
		return null;
	}

	@Override
	public String getTradeRecordByPayTradeNo(String payTradeNo) {
		if(StringUtils.isBlank(payTradeNo)) {
			return null;
		}
		List<PayTradeRecord> list = this.queryPayTradeRecord("payTradeNo",payTradeNo);
		if (list != null && list.size() > 0) {
			return list.get(0).getExtenalTradeNo();
		}
		return null;
	}

	private List<PayTradeRecord> queryPayTradeRecord(String queryFlagByTradeNo, String payTradeNo) {
		PayTradeQueryVo queryVo = new PayTradeQueryVo();
		if (Objects.equals("payTradeNo",queryFlagByTradeNo)){
			queryVo.setPayTradeNo(payTradeNo);
		}else if(Objects.equals("internalTradeNo",queryFlagByTradeNo)){
			queryVo.setIntenalTradeNo(payTradeNo);
		}
		return payTradeRecordMapper.selectList(queryVo);
	}
}
