package com.sandu.order.service;

import com.sandu.order.MallBaseOrderService;
import com.sandu.order.dao.MallBaseOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallBaseOrderServiceImpl implements MallBaseOrderService {

    @Autowired
    private MallBaseOrderDao mallBaseOrderDao;

    @Override
    public int countUserConsumerSuccessOrder(Long userId) {
        return mallBaseOrderDao.countMallBaseOrderSuccess(userId);
    }
}
