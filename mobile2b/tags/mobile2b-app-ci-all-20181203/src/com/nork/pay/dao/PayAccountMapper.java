package com.nork.pay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.pay.model.PayAccount;
import com.nork.pay.model.search.PayAccountSearch;

@Repository
public interface PayAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayAccount record);

    int insertSelective(PayAccount record);

    PayAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayAccount record);

    int updateByPrimaryKey(PayAccount record);

	List<PayAccount> selectBySearch(PayAccountSearch payAccountSearch);

	int updateBalance(
			@Param("payAccount") PayAccount payAccount, 
			@Param("id") Integer id, 
			@Param("balanceAmount") Double balanceAmount);
}