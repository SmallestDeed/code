package com.nork.decorateOrder.service;

import java.util.List;

import com.nork.decorateOrder.model.DecorateCustomer;

public interface DecorateCustomerService {

	void updateBidStatus(Integer decoratecustomerBidstatusFinishButNullity, Long customerId);

	void update(DecorateCustomer decorateCustomer);

	/**
	 * 获取报价完成的订单(报价完成+报价超时)
	 * 
	 * @author huangsongbo
	 * @return
	 */
	List<Long> getOverTimeBidCustomerIdList();

	/**
	 * 更新装修客户的抢单公司id
	 * 可能是decorate_customer.first_seckill_company
	 * 也可能是decorate_customer.second_seckill_company
	 * 
	 * @author huangsongbo
	 * @param getpCompanyId
	 * @param customerId
	 */
	void updateCompanyId(Long companyId, Long customerId);

	/**
	 * 
	 * @author huangsongbo
	 * @param customerId
	 * @return
	 */
	DecorateCustomer get(Long customerId);

}
