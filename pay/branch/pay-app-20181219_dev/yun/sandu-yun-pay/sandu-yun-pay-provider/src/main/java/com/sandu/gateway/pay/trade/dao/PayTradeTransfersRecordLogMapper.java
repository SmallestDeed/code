package com.sandu.gateway.pay.trade.dao;

import org.springframework.stereotype.Repository;

import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecordLog;

@Repository
public interface PayTradeTransfersRecordLogMapper {

	/**
	 * 新增交易日志
	 * @param recordLog
	 * @return
	 */
    void insertSelective(PayTradeTransfersRecordLog recordLog);

}
