package com.sandu.gateway.pay.trade.service;

import com.sandu.gateway.pay.trade.model.PayTradeRecordLog;



public interface PayTradeRecordLogService {

    
   /**
    * 记录交易日志
    * @param payTradeRecordLog
    * @return
    */
    Long addPayTradeRecordLog(PayTradeRecordLog payTradeRecordLog);

    /**
     * 记录外部通知信息
     * @param tradeId
     * @param jsonNotifyBody
     */
    void saveExternalNotifyBody(Long tradeId, String jsonNotifyBody);

    /**
     * 保存内部系统通知报文
     * @param tradeId
     * @param request
     * @param response
     */
	void saveInternalSystemNotifyLog(Long tradeId, String request, String response);

    
}
