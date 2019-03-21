package com.sandu.service.pointmall.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.pointmall.model.ImallOrder;
import com.sandu.api.pointmall.model.ImallOrderAddress;
import com.sandu.api.pointmall.model.ImallOrderItem;
import com.sandu.api.pointmall.model.ImallOrderLog;
import com.sandu.api.pointmall.model.vo.OrderGiftDetailVo;
import com.sandu.api.pointmall.service.OrderService;
import com.sandu.service.pointmall.dao.OrderAddressDao;
import com.sandu.service.pointmall.dao.OrderDao;
import com.sandu.service.pointmall.dao.OrderItemDao;
import com.sandu.service.pointmall.dao.OrderLogDao;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderAddressDao orderAddressDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderLogDao orderLogDao;

    @Override
    public List<OrderGiftDetailVo> getOrderGiftDetailVoList(int uid) {
        return orderDao.getOrderGiftDetailVoList(uid);
    }

    @Override
    public int updateOrderStatus(int id, int status) {
        return orderDao.updateOrderStatus(id,status);
    }

    @Override
    public ImallOrder selectOrderId(String orderNo) {
        return orderDao.selectOrderId(orderNo);
    }

    @Override
    public int insertSelective(ImallOrder record) {
        return orderDao.insertSelective(record);
    }

    @Override
    public int insertImallOrderAddressSelective(ImallOrderAddress record) {
        return orderAddressDao.insertSelective(record);
    }

    @Override
    public int inserImallOrderItemSelective(ImallOrderItem record) {
        return orderItemDao.insertSelective(record);
    }

    @Override
    public int inserImallOrderLogSelective(ImallOrderLog record) {
        return orderLogDao.inserImallOrderLogSelective(record);
    }


}
