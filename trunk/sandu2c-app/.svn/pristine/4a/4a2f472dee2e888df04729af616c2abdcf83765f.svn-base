package com.sandu.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.customer.model.CustomerOperateLog;

public interface CustomerOperateLogMapper {

	int deleteByPrimaryKey(Long id);

    int insert(CustomerOperateLog record);

    int insertSelective(CustomerOperateLog record);

    CustomerOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerOperateLog record);

    int updateByPrimaryKey(CustomerOperateLog record);

	int queryPayModelGroupRef(@Param("userId")Integer userId);

	int queryAutoRenderTaskStateCount(@Param("userId")Integer userId, @Param("taskType")String taskType);

	int queryCustomerOperateLog(@Param("userId")Integer userId, @Param("operateType")String operateType);
}