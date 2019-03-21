package com.sandu.service.pointmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.ImallOrder;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;
import com.sandu.api.pointmall.model.vo.OrderGiftDetailVo;

@Repository
public interface OrderDao extends CrudDao<OrderDao, GiftQuery, GiftVo> {

    List<OrderGiftDetailVo> getOrderGiftDetailVoList(@Param("uid") int uid);

    int updateOrderStatus(@Param("id") int id, @Param("status") int status);

    int insertSelective(ImallOrder record);

    ImallOrder selectOrderId(@Param("orderNo") String orderNo);

}
