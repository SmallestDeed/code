package com.sandu.api.order.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.order.input.OrderManageAdd;
import com.sandu.api.order.input.OrderManageQuery;
import com.sandu.api.order.input.OrderManageUpdate;
import com.sandu.api.order.model.OrderManage;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 14:47 2018/10/24
 */
public interface OrderManageService {
    PageInfo<OrderManage> queryAll(OrderManageQuery query);

    int addOrder(OrderManageAdd add);

    int deleteOrderById(Integer id);

    OrderManage getById(int orderManageId);

    int update(OrderManageUpdate input);
}
