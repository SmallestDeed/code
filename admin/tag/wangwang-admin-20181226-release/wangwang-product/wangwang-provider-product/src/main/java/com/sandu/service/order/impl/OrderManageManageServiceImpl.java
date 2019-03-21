package com.sandu.service.order.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.order.input.OrderManageAdd;
import com.sandu.api.order.input.OrderManageQuery;
import com.sandu.api.order.input.OrderManageUpdate;
import com.sandu.api.order.model.OrderManage;
import com.sandu.api.order.service.OrderManageService;
import com.sandu.api.user.model.User;
import com.sandu.service.order.dao.OrderManageMapper;
import com.sandu.service.user.dao.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sandu
 */
@Service("orderManageService")
public class OrderManageManageServiceImpl implements OrderManageService {

    @Autowired
    private OrderManageMapper orderManageMapper;

    @Autowired
    private UserDao userMapper;

    @Override
    public PageInfo<OrderManage> queryAll(OrderManageQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(orderManageMapper.queryAll(query.getQueryParam()));
    }

    @Override
    public int addOrder(OrderManageAdd add) {
        OrderManage order = new OrderManage();
        BeanUtils.copyProperties(add, order);
        User user = userMapper.selectByPrimaryKey(order.getIntermediaryId().longValue());
        order.setIntermediaryName(user.getUserName());
        return orderManageMapper.insertSelective(order);
    }

    @Override
    public int deleteOrderById(Integer id) {
        return orderManageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderManage getById(int orderManageId) {
        return orderManageMapper.selectByPrimaryKey(orderManageId);
    }

    @Override
    public int update(OrderManageUpdate input) {
        OrderManage order = new OrderManage();
        BeanUtils.copyProperties(input, order);
        User user = userMapper.selectByPrimaryKey(order.getIntermediaryId().longValue());
        order.setIntermediaryName(user.getUserName());
        return orderManageMapper.updateByPrimaryKeySelective(order);
    }
}
