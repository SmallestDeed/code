package com.sandu.service.pay.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sandu.api.pay.model.PayAccount;
import com.sandu.api.pay.service.PayAccountService;
import com.sandu.service.pay.dao.PayAccountMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PayAccountServiceImpl implements PayAccountService {

	@Resource
	private PayAccountMapper payAccountMapper;
	
	public  static Gson gson = new Gson();

	@Override
	public Map<String,Object> updatePayAccount(Integer userId, String userName, Integer amount, int direction) {
		log.info("userId{},userName{},amount{},direction{}",userId,userName,amount,direction);
		Map<String,Object> resultMap = new HashMap<>();
		//校验账号是否异常
		List<PayAccount> accountList = selectPayAccountByUserId(userId);
		if(accountList == null || accountList.size() ==0 ) {
			resultMap.put("fail", "用户ID【"+userId+"】账号异常!");
			return resultMap;
		}
		if(accountList.size()> 1 ) {
			resultMap.put("fail", "用户ID【"+userId+"】账号重复!");
			return resultMap;
		}
		//正向的标识增加
		PayAccount accountVo = accountList.get(0);
		log.info("userId{},原始度币信息{}",userId,gson.toJson(accountVo));
		if(direction >= 0) {
			BigDecimal balanceAmount = new BigDecimal(accountVo.getBalanceAmount());
			BigDecimal consumAcount = new BigDecimal(accountVo.getConsumAmount());
			accountVo.setBalanceAmount(balanceAmount.add(new BigDecimal(amount)).doubleValue());
			accountVo.setConsumAmount(consumAcount.subtract(new BigDecimal(amount)).doubleValue());
			accountVo.setModifier(userName);
			accountVo.setGmtModified(new Date());
			resultMap.put("currentIntegral", accountVo.getBalanceAmount());
			log.info("userId{},增加操作度币信息{}",userId,gson.toJson(accountVo));
			int i1 = this.payAccountMapper.updatePayAccountByUserId(accountVo);
			if (i1 != 1) {
				resultMap.put("fail", "用户【"+userId+"】增加度币失败!");
				return resultMap;
			}
		//反向的表示扣减
		}else {
			//检查账户余额是否充足
			Integer count = checkAccountBalance(userId,amount);
			if(count <=0) {
				resultMap.put("fail", "用户ID【"+userId+"】余额不足!");
				return resultMap;
			}
			accountVo.setLeftBalanceAmount(accountVo.getBalanceAmount());
			BigDecimal balanceAmount = new BigDecimal(accountVo.getBalanceAmount());
			BigDecimal consumAcount = new BigDecimal(accountVo.getConsumAmount());
			accountVo.setBalanceAmount(balanceAmount.subtract(new BigDecimal(amount)).doubleValue());
			accountVo.setConsumAmount(consumAcount.add(new BigDecimal(amount)).doubleValue());
			accountVo.setModifier(userName);
			accountVo.setGmtModified(new Date());
			resultMap.put("currentIntegral", accountVo.getBalanceAmount());
			log.info("userId{},扣减操作度币信息{}",userId,gson.toJson(accountVo));
			int i1 = this.payAccountMapper.updatePayAccountByUserId(accountVo);
			if (i1 != 1) {
				resultMap.put("fail", "用户【"+userId+"】扣减度币失败!");
				return resultMap;
			}
		}
		resultMap.put("success", "操作成功!");
		return resultMap;
	}

	@Override
	public List<PayAccount> selectPayAccountByUserId(Integer userId) {
		return payAccountMapper.selectByUserId(userId);
	}
	
	
	@Override
	public Integer checkAccountBalance(Integer userId,Integer amount) {
		return payAccountMapper.checkAccountBalance(userId,amount);
	}
}
