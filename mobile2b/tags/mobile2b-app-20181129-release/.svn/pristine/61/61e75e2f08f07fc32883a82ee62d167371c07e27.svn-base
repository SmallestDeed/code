package com.nork.decorateOrder.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.decorateOrder.model.DecorateCustomer;

@Repository
public interface DecorateCustomerMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(DecorateCustomer record);

    int insertSelective(DecorateCustomer record);

    DecorateCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DecorateCustomer record);

    int updateByPrimaryKey(DecorateCustomer record);

	List<Long> selectGetOverTimeBidCustomerIdList(@Param("date") Date date);
	
}