package com.sandu.order.dao;

import java.math.BigDecimal;
import java.util.List;

import com.sandu.order.MallBaseOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MallBaseOrderMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(MallBaseOrder record);

    int insertSelective(MallBaseOrder record);

    MallBaseOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MallBaseOrder record);

    int updateByPrimaryKey(MallBaseOrder record);

	List<MallBaseOrder> dynamicQueryOrder(MallBaseOrder order);
	
	List<MallBaseOrder> getOrderDetail(MallBaseOrder order);

    List<MallBaseOrder> getOrderByOrderUserId(MallBaseOrder order);

    MallBaseOrder getOrderByOrderNo(String orderNo);

    Integer updateBaseOrderByOrderId(MallBaseOrder id);

    List<MallBaseOrder> selectByOrderIdList(List<Integer> idList);


    int updateOrderStatusByOrderList(MallBaseOrder order);

	int countUserOrder(@Param("userId")Integer userId, @Param("payStatus")int payStatus);

    int updateOrderAmount(@Param("id") Integer id,@Param("orderAmount") BigDecimal orderAmount);
}