package com.sandu.service.company.impl;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.CustomerAlotZone;
import com.sandu.api.company.service.CustomerAlotZoneService;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.service.company.dao.CustomerAlotZoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("customerAlotZoneService")
public class CustomerAlotZoneServiceImpl implements CustomerAlotZoneService {

	@Autowired
	private CustomerAlotZoneMapper customerAlotZoneMapper;

	@Override
	public int removeCustomerAlotZone(Integer companyId) {
		return customerAlotZoneMapper.removeCustomerAlotZone(companyId);
	}

	@Override
	public BaseCompany getDistributorByType(int type, int distributorId) {
		return customerAlotZoneMapper.getDistributorByType(type, distributorId);
	}

	@Override
	public List<BaseCompany> queryDistributorByInfo(BaseCompany distributor) {
		return customerAlotZoneMapper.queryDistributorByInfo(distributor);
	}

	@Override
	public int updateByPrimaryKeySelective(CustomerAlotZone customerAlotZone) {
		return customerAlotZoneMapper.updateByPrimaryKeySelective(customerAlotZone);
	}

	@Override
	public List<CustomerAlotZone> queryDistributorByCompanyId(Integer id) {
		return customerAlotZoneMapper.queryDistributorByCompanyId(id.longValue());
	}

	@Override
	public int insertSelective(CustomerAlotZone customerAlotZone) {
		return customerAlotZoneMapper.insertSelective(customerAlotZone);
	}

	@Override
	public List<CustomerAlotZone> queryCustomer(Integer distributorId, Integer delType) {
		return customerAlotZoneMapper.queryCustomer(distributorId, delType);
	}

	@Override
	public int updateCustomerZone(Integer id) {
		return customerAlotZoneMapper.updateCustomerZone(id);
	}

	@Override
	public List<SysUserRole> selectByPrimaryUserRole(SysUserRole userRole) {
		return customerAlotZoneMapper.selectByPrimaryUserRole(userRole);
	}

	@Override
	public void updateStatusById(Long id, int isDelete, String modifier, Date gmtModified) {
		customerAlotZoneMapper.updateStatusById(id.intValue(), isDelete, modifier, gmtModified);
	}

	@Override
	public void insertUserRoleSelective(SysUserRole userRole) {
		customerAlotZoneMapper.insertUserRoleSelective(userRole);
	}
}

