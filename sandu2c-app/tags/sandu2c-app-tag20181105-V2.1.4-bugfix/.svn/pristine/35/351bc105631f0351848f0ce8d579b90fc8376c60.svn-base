package com.sandu.order.dao;


import com.sandu.order.MallBaseOrder;
import com.sandu.order.MallOrderAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallOrderActionMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(MallOrderAction record);

    int insertSelective(MallOrderAction record);


    MallOrderAction selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(MallOrderAction record);

    int updateByPrimaryKey(MallOrderAction record);

    Integer updateBaseOrderByOrderId(MallOrderAction id);


    int updateOrderActionStatusByOrderList(MallBaseOrder order);
}