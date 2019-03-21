package com.sandu.gateway.pay.trade.service;

import java.util.List;

import com.sandu.gateway.pay.input.PayTradeRefundQueryVo;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;


public interface PayTradeRefundRecordService {

	/**
	 * 增加交易流水
	 * @param payTradeRecord
	 * @return
	 */
	Long add(PayTradeRefundRecord payTradeRefundRecord);


	int modify(PayTradeRefundRecord payTradeRefundRecord);
	
	List<PayTradeRefundRecord> getList(PayTradeRefundQueryVo queryVo);


	int changeToProcessStatus(String payRefundNo);


	Object getAliRefundApiConfig();
}
