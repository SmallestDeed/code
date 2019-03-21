package com.sandu.service.merchantManagePay.dao;

import com.sandu.api.merchantManagePay.model.MgrRechargeLog;
import com.sandu.api.merchantManagePay.model.PayOrder;

import com.sandu.pay.order.model.PayAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayOrderDao {

    void insert(PayOrder payOrder);

    int updatePayState(@Param("payState") String payState, @Param("orderNo")String orderNo);

    PayOrder getbyOrderNo(String orderNo);

    void inserMgrRechargeLog(MgrRechargeLog log);

    int updateUserDubi(@Param("totalFee") Integer totalFee, @Param("userId") Integer userId);

    void batchInsertPayorder(@Param("payOrders") List<PayOrder> payOrders);

    PayOrder getOrderSuccess(String orderNo);

    PayAccount getPayAccountByUserId(int userId);

    void updatePayAccount(@Param("id") Integer id, @Param("totalFee") Double totalFee);
}
