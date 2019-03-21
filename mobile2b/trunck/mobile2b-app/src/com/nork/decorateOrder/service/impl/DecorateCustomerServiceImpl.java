package com.nork.decorateOrder.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.decorateOrder.dao.DecorateCustomerMapper;
import com.nork.decorateOrder.model.DecorateCustomer;
import com.nork.decorateOrder.service.DecorateCustomerService;

@Service("decorateCustomerService")
public class DecorateCustomerServiceImpl implements DecorateCustomerService {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecorateCustomerServiceImpl.class);
	
	private final static String LOGPREFIX = "[装修客户模块]:";
	
	@Autowired
	private DecorateCustomerMapper decorateCustomerMapper;
	
	@Override
	public void updateBidStatus(Integer bidStatus, Long id) {
		// ------参数验证 ->start
		if(bidStatus == null) {
			LOGGER.error(LOGPREFIX + "bidStatus = null");
			return;
		}
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return;
		}
		// ------参数验证 ->end
		
		DecorateCustomer decorateCustomer = new DecorateCustomer();
		decorateCustomer.setId(id);
		decorateCustomer.setBidStatus(bidStatus);
		this.update(decorateCustomer);
	}

	@Override
	public void update(DecorateCustomer decorateCustomer) {
		// ------参数验证 ->start
		if(decorateCustomer == null) {
			LOGGER.error(LOGPREFIX + "decorateCustomer = null");
			return;
		}
		// ------参数验证 ->end
		
		decorateCustomerMapper.updateByPrimaryKeySelective(decorateCustomer);
	}

	@Override
	public List<Long> getOverTimeBidCustomerIdList() {
		return decorateCustomerMapper.selectGetOverTimeBidCustomerIdList(new Date());
	}

	@Override
	public void updateCompanyId(Long companyId, Long customerId) {
		// ------参数验证 ->start
		if(companyId == null) {
			LOGGER.error("{} companyId = null", LOGPREFIX);
			return;
		}
		if(customerId == null) {
			LOGGER.error("{} customerId = null", LOGPREFIX);
			return;
		}
		// ------参数验证 ->end
		
		DecorateCustomer decorateCustomer = this.get(customerId);
		if(decorateCustomer == null) {
			LOGGER.error("{} decorateCustomer == null", LOGPREFIX);
			return;
		}
		
		// ------构建update bean ->start
		DecorateCustomer decorateCustomerForUpdate = new DecorateCustomer();
		decorateCustomerForUpdate.setId(customerId);
		decorateCustomerForUpdate.setGmtModified(new Date());
		if(decorateCustomer.getFirstSeckillCompany() != null && decorateCustomer.getFirstSeckillCompany() > 0) {
			decorateCustomerForUpdate.setSecondSeckillCompany(companyId);
		}else {
			decorateCustomerForUpdate.setFirstSeckillCompany(companyId);
		}
		// ------构建update bean ->end
		
		this.update(decorateCustomerForUpdate);
	}

	@Override
	public DecorateCustomer get(Long customerId) {
		// ------参数验证 ->start
		if(customerId == null) {
			LOGGER.error("{} customerId = null", LOGPREFIX);
			return null;
		}
		// ------参数验证 ->end
		
		return decorateCustomerMapper.selectByPrimaryKey(customerId);
	}

}
