package com.sandu.api.account.service;

import com.sandu.api.account.model.PayOrder;

import java.util.List;
import java.util.Set;

public interface PayOrderService {

    List<PayOrder> selectExpensesRecordList(Long loginUserId, String expenseType, Long platformId, Integer start, Integer limit);

    int countExpensesRecordList(Long userId, String expenseType, Long platformId);

    List<PayOrder> selectPayOrdersByOrderNos(Set<String> orderNos);

    int countUserRechargeRecord(Long id);

    List<PayOrder> getUserRechargeRecords(Long userId, Integer start, Integer limit);
}
