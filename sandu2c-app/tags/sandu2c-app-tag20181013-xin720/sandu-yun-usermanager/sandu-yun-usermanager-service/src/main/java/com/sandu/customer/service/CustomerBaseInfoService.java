package com.sandu.customer.service;

import java.util.List;
import java.util.Map;

import com.sandu.customer.model.CustomerBaseInfo;

public interface CustomerBaseInfoService {

	/**
	 * 修改客户信息
	 * @param info
	 * @return
	 */
	public int updateCustomerBaseInfo(CustomerBaseInfo info);
	
	/**
	 * 查看客户信息
	 * @param info
	 * @return
	 */
	public CustomerBaseInfo selectCustomerBaseInfoByUserId(Integer userId);

	/**
	 * 查询符合条件的客户信息
	 * @param infoMap
	 * @return
	 */
	public List<CustomerBaseInfo> queryCustomerInfoByMap(Map<String, Object> infoMap);

	/**
	 * 批量更新客户积分
	 * @param updateList
	 */
	public int batchUpdate(List<CustomerBaseInfo> updateList);
}
