package com.sandu.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sandu.customer.model.CustomerBaseInfo;
import com.sandu.customer.model.CustomerOperateLog;

public interface CustomerBaseInfoMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(CustomerBaseInfo record);

    int insertSelective(CustomerBaseInfo record);

    CustomerBaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBaseInfo record);

    int updateByPrimaryKey(CustomerBaseInfo record);

	CustomerBaseInfo selectByUserId(Integer userId);

	List<CustomerBaseInfo> selectCustomerInfoByMap(Map<String, Object> param);

	int updateCustomerByUserId(@Param("userId")Integer userId, @Param("score")double score);
	
	int batchUpdate(@Param("updateList")List<CustomerBaseInfo> updateList);
}