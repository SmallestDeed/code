package com.sandu.service.pointmall.dao;

import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.ImallOrderItem;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository
public interface OrderItemDao extends CrudDao<OrderItemDao, GiftQuery, GiftVo> {

    int insertSelective(ImallOrderItem record);
}
