package com.sandu.service.pointmall.dao;

import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.ImallOrderLog;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository
public interface OrderLogDao extends CrudDao<OrderLogDao, GiftQuery, GiftVo> {

    int inserImallOrderLogSelective(ImallOrderLog record);
}
