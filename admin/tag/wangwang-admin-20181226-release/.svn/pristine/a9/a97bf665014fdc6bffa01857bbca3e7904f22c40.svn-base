package com.sandu.api.pay.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.pay.model.PayAccount;


public interface PayAccountService {

	public Map<String,Object> updatePayAccount(Integer userId, String userName, Integer amount, int direction);
	
	public List<PayAccount> selectPayAccountByUserId(Integer userId);
	
	public Integer checkAccountBalance(Integer userId,Integer amount);
}
