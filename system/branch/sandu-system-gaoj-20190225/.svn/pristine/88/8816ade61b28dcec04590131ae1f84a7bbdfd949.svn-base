package com.sandu.service.pointmall.dao;

import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.ImallOrderAddress;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository
public interface OrderAddressDao extends CrudDao<OrderAddressDao, GiftQuery, GiftVo> {

    int insertSelective(ImallOrderAddress record);
}
