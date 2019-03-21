package com.nork.pay.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.pay.dao.PayAccountMapper;
import com.nork.pay.model.PayAccount;
import com.nork.pay.model.search.PayAccountSearch;
import com.nork.pay.service.PayAccountService;

@Service("payAccountService")
public class PayAccountServiceImpl implements PayAccountService {

	private final static Logger LOGGER = LoggerFactory.getLogger(PayAccountServiceImpl.class);
	
	private final static String LOGPREFIX = "[账号金额明细模块]";
	
	@Autowired
	private PayAccountMapper payAccountMapper;
	
	@Override
	public PayAccount get(Long userId, String platformBussinessType) {
		// ------参数验证 ->start
		if(userId == null && StringUtils.isEmpty(platformBussinessType)) {
			LOGGER.error("{} (userId == null && StringUtils.isEmpty(platformBussinessType)) = true", LOGPREFIX);
			return null;
		}
		// ------参数验证 ->end
		
		// ------构造查询条件 ->start
		PayAccountSearch payAccountSearch = new PayAccountSearch();
		payAccountSearch.setStart(0L);
		payAccountSearch.setLimit(1L);
		payAccountSearch.setUserId(userId.intValue());
		payAccountSearch.setPlatformBussinessType(platformBussinessType);
		// ------构造查询条件 ->end
		
		List<PayAccount> payAccountList = this.getList(payAccountSearch);
		if(payAccountList != null && payAccountList.size() > 0) {
			return payAccountList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<PayAccount> getList(PayAccountSearch payAccountSearch) {
		// ------参数验证 ->start
		if(payAccountSearch == null) {
			LOGGER.error("{} payAccountSearch == null", LOGPREFIX);
			return null;
		}
		// ------参数验证 ->end
		
		return payAccountMapper.selectBySearch(payAccountSearch);
	}

	@Override
	public int updateBalance(PayAccount payAccount, Integer id, Double balanceAmount) {
		// ------参数验证 ->start
		if(payAccount == null) {
			LOGGER.error("{} payAccount == null", LOGPREFIX);
			return 0;
		}
		if(id == null) {
			LOGGER.error("{} id == null", LOGPREFIX);
			return 0;
		}
		if(balanceAmount == null) {
			LOGGER.error("{} balanceAmount == null", LOGPREFIX);
			return 0;
		}
		// ------参数验证 ->end
		
		return payAccountMapper.updateBalance(payAccount, id, balanceAmount);
	}

}
