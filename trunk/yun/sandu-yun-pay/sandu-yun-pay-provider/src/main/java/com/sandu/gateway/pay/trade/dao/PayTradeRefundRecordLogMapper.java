package com.sandu.gateway.pay.trade.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.gateway.pay.trade.model.PayTradeRefundRecordLog;

@Repository
public interface PayTradeRefundRecordLogMapper {

	/**
	 * 新增交易日志
	 * @param recordLog
	 * @return
	 */
    void insertSelective(PayTradeRefundRecordLog recordLog);

    /**
     * 更新外部系统(tx,ali)通知日志
     * @param tradeId
     * @param jsonNotifyBody
     */
	void updateExtenalNotifyBodyByTradeId(@Param("tradeRefundId")Long tradeRefundId, @Param("externalNotifyBody")String externalNotifyBody);

	/**
	 * 更新内部系统(订单,充值,套餐购买)通知日志
	 * @param tradeId
	 * @param request
	 * @param response
	 */
	void updateInternalNotifyReqAndRespByByTradeId(@Param("tradeRefundId")Long tradeRefundId, @Param("request")String request, @Param("response")String response);
    
}
