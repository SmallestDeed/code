package com.sandu.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.customer.model.CustomerScoreDetail;

public interface CustomerScoreDetailMapper {
	
	int deleteByPrimaryKey(Long id);

	int insert(CustomerScoreDetail record);

	int insertSelective(CustomerScoreDetail record);

	CustomerScoreDetail selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(CustomerScoreDetail record);

	int updateByPrimaryKey(CustomerScoreDetail record);

	int deleteDetailByUserId(@Param("userId") Integer userId);

	int batchInsert(@Param("insertList") List<CustomerScoreDetail> insertList);
}