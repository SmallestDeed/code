package com.nork.pay.service;

import java.util.List;

import com.nork.pay.model.PayAccount;
import com.nork.pay.model.search.PayAccountSearch;

public interface PayAccountService {

	/**
	 * select * from pay_account where user_id = #{userId} and platform_bussiness_type = #{platformBussinessType}
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @param payaccountPlatformbussinesstype2b
	 * @return
	 */
	PayAccount get(Long userId, String platformBussinessType);

	/**
	 * 
	 * @author huangsongbo
	 * @param payAccountSearch
	 * @return
	 */
	List<PayAccount> getList(PayAccountSearch payAccountSearch);

	/**
	 * 
	 * @author huangsongbo
	 * @param payAccount
	 * @param id
	 * @param balanceAmount
	 * @return
	 */
	int updateBalance(PayAccount payAccount, Integer id, Double balanceAmount);

}
