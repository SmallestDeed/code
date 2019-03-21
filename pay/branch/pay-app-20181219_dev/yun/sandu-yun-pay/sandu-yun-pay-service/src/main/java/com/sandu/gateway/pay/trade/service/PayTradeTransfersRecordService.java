package com.sandu.gateway.pay.trade.service;

import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;


public interface PayTradeTransfersRecordService {

	/**
	 * 增加交易流水
	 * @param payTradeRecord
	 * @return
	 */
	Long add(PayTradeTransfersRecord payTradeTransfersRecord);


	int modify(PayTradeTransfersRecord payTradeTransfersRecord);
	
	//List<PayTradeTransfersRecord> getList(PayTradeTransfersQueryVo queryVo);


   
}
