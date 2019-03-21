package com.sandu.service.pay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.pay.model.PayAccount;

public interface PayAccountMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(PayAccount record);

    int insertSelective(PayAccount record);

    PayAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayAccount record);

    int updateByPrimaryKey(PayAccount record);

	List<PayAccount> selectByUserId(@Param("userId")Integer userId);

	Integer checkAccountBalance(@Param("userId")Integer userId, @Param("amount")Integer amount);

	int updatePayAccountByUserId(PayAccount accountVo);
}