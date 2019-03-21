package com.sandu.api.act3.service;

import java.util.List;

import com.sandu.api.act3.model.LuckyWheelRegistration;
import com.sandu.api.act3.output.LuckyWheelLotteryVO;
import com.sandu.api.user.model.SysUser;

public interface LuckyWheelRegistrationService {

	void create(LuckyWheelRegistration entity);
	
	int modifyById(LuckyWheelRegistration entity);
	 
	LuckyWheelRegistration get(String id);	
	
	List<LuckyWheelRegistration> list(LuckyWheelRegistration entity);

	LuckyWheelLotteryVO lottery(String actId, SysUser user);

	LuckyWheelRegistration get(String actId, String openId);

	void increaseLotteryCount(String actId, SysUser user);

	void doCashAward(SysUser user,Integer payOrderId,Double moneyAccount);

	int buildPayOrderOfSignIn(SysUser user,Integer totalMoney);

}
