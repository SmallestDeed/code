package com.sandu.api.company.service;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.CustomerAlotZone;
import com.sandu.api.user.model.SysUserRole;

import java.util.Date;
import java.util.List;

public interface CustomerAlotZoneService {

	/**
	 * 移除经销商分配规则
	 *
	 * @param companyId
	 * @return
	 */
	int removeCustomerAlotZone(Integer companyId);

	/**
	 * 查询经销商分配规则
	 *
	 * @param type
	 * @param distributorId
	 * @return
	 */
	BaseCompany getDistributorByType(int type, int distributorId);

	List<BaseCompany> queryDistributorByInfo(BaseCompany distributor);

	int updateByPrimaryKeySelective(CustomerAlotZone customerAlotZone);

	List<CustomerAlotZone> queryDistributorByCompanyId(Integer id);

	int insertSelective(CustomerAlotZone customerAlotZone);

	List<CustomerAlotZone> queryCustomer(Integer distributorId, Integer delType);

	int updateCustomerZone(Integer id);

	List<SysUserRole> selectByPrimaryUserRole(SysUserRole userRole);

	void updateStatusById(Long id, int i, String modifier, Date gmtModified);

	void insertUserRoleSelective(SysUserRole userRole);
}
