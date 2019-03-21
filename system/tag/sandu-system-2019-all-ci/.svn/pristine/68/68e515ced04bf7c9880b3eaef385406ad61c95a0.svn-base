package com.sandu.api.merchantManagePay.service;


import com.sandu.api.merchantManagePay.model.BatchPayPlanFee;
import com.sandu.api.merchantManagePay.model.PayOrder;

import java.util.Map;

public interface PayOrderService {
    String insertPayOrder(Long id, Double money,Integer payType,String platformCode);

    int updatePayState(String recordStatus, String orderNo);

    Map<String, Integer> insertMgrRechargeLog(String orderNo);

    void updateUserDubi(Map<String, Integer> resultMap);

    void handlerBatchPay(BatchPayPlanFee batchPayPlanFee);

    PayOrder getOrderSuccess(String orderNo);
}
