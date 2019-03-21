package com.sandu.api.servicepurchase.serivce;

import com.sandu.api.servicepurchase.input.query.ServicesAccountRelQuery;
import com.sandu.api.servicepurchase.model.ServicesAccountRel;

import java.util.List;
public interface ServicesAccountRelService {

	List<ServicesAccountRel> getAccountListByAccount(String account);
	
	int updateByPrimaryKeySelective(ServicesAccountRel rel);

	void batchInsertServiceAccount(List<ServicesAccountRel> accountList);

	/**
	 * 条件查询
	 * @param servicesAccountRelQuery 查询条件
	 * @return 列表
	 */
	List<ServicesAccountRel> queryByOption(ServicesAccountRelQuery servicesAccountRelQuery);


	ServicesAccountRel  getAccountByUserId(Integer userId);

	/**
	 * 根据userId查询用户套餐
	 * @param userId 用户id
	 * @param isDelete 是否删除 0 ：未删除 ，1：已删除
	 * @return
	 */
	List<ServicesAccountRel> queryByUserId(Integer userId,Integer isDelete);

	List<ServicesAccountRel> listRemainingAccounts(Integer topDays);
}