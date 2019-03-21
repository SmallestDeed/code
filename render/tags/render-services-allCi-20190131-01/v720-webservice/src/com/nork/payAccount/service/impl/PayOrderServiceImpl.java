package com.nork.payAccount.service.impl;

import com.nork.payAccount.dao.PayOrderDao;
import com.nork.payAccount.model.PayOrder;
import com.nork.payAccount.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "payOrderService2")
public class PayOrderServiceImpl implements PayOrderService{

    @Autowired
    private PayOrderDao payOrderDao;

    @Override
    public int add(PayOrder payOrder) {
        return payOrderDao.insertSelective(payOrder);
    }
}
