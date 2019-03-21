package com.sandu.service.account.impl;

import com.sandu.api.account.model.PayOrder;
import com.sandu.api.account.service.PayOrderService;
import com.sandu.service.account.dao.PayOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service(value = "payOrderService2")
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PayOrderDao payOrderDao;

    @Override
    public List<PayOrder> selectExpensesRecordList(Long loginUserId, String bizType, Long platformId, Integer start, Integer limit) {
        return payOrderDao.selectExpensesRecordList(loginUserId,bizType,platformId,start,limit);
    }

    @Override
    public int countExpensesRecordList(Long userId, String bizType, Long platformId) {
        return payOrderDao.countExpensesRecordList(userId,bizType,platformId);
    }

    @Override
    public List<PayOrder> selectPayOrdersByOrderNos(Set<String> orderNos) {
        return payOrderDao.selectPayOrdersByOrderNos(orderNos);
    }

    @Override
    public int countUserRechargeRecord(Long userId) {
        return payOrderDao.countUserRechargeRecord(userId);
    }

    @Override
    public List<PayOrder> getUserRechargeRecords(Long userId, Integer start, Integer limit) {
        return payOrderDao.getUserRechargeRecords(userId,start,limit);
    }
}
