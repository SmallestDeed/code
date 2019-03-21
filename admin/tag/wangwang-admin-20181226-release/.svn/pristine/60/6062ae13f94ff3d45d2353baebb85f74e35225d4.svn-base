package com.sandu.service.order.dao;


import com.sandu.api.order.model.OrderManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderManage record);

    int insertSelective(OrderManage record);

    OrderManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderManage record);

    int updateByPrimaryKey(OrderManage record);

    List<OrderManage> queryAll(@Param("queryParam") String queryParam);
}