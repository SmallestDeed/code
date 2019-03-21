package com.sandu.gateway.pay.trade.service;

import com.sandu.gateway.pay.trade.model.PayTradeRefundRecordLog;



public interface PayTradeRefundRecordLogService {

    
   /**
    * 记录交易日志
    * @param payTradeRecordLog
    * @return
    */
    Long add(PayTradeRefundRecordLog payTradeRefundRecordLog);

    /**
     * 记录外部通知信息
     * @param tradeId
     * @param jsonNotifyBody
     */
    void saveExternalNotifyBody(Long tradeRefundId, String jsonNotifyBody);

    /**
     * 保存内部系统通知报文
     * @param tradeId
     * @param request
     * @param response
     */
	void saveInternalSystemNotifyLog(Long tradeRefundId, String request, String response);

    
}
