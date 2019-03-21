package com.sandu.gateway.pay.trade.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.gateway.pay.trade.model.PayTradeRecordLog;

@Repository
public interface PayTradeRecordLogMapper {

	/**
	 * 新增交易日志
	 * @param recordLog
	 * @return
	 */
    int insertSelective(PayTradeRecordLog recordLog);

    /**
     * 更新外部系统(tx,ali)通知日志
     * @param tradeId
     * @param jsonNotifyBody
     */
	void updateExtenalNotifyBodyByTradeId(@Param("tradeId")Long tradeId, @Param("externalNotifyBody")String externalNotifyBody);

	/**
	 * 更新内部系统(订单,充值,套餐购买)通知日志
	 * @param tradeId
	 * @param request
	 * @param response
	 */
	void updateInternalNotifyReqAndRespByByTradeId(@Param("tradeId")Long tradeId, @Param("request")String request, @Param("response")String response);
    
}
