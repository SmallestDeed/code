package com.sandu.service.company.dao;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.CustomerAlotZone;
import com.sandu.api.user.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerAlotZoneMapper {

	/**
	 * 移除经销商分配规则
	 *
	 * @param companyId
	 * @return
	 */
	int removeCustomerAlotZone(@Param("companyId") Integer companyId);

	BaseCompany getDistributorByType(@Param("type") Integer type, @Param("channelId") Integer channelId);

	List<BaseCompany> queryDistributorByInfo(BaseCompany distributor);

	int updateByPrimaryKeySelective(CustomerAlotZone customerAlotZone);

	List<CustomerAlotZone> queryDistributorByCompanyId(@Param("companyId") Long companyId);

	int insertSelective(CustomerAlotZone record);

	List<CustomerAlotZone> queryCustomer(@Param("companyId") Integer distributorId, @Param("delType") Integer delType);

	int updateCustomerZone(@Param("id") Integer id);

	List<SysUserRole> selectByPrimaryUserRole(SysUserRole userRole);

	void updateStatusById(@Param("id") Integer id, @Param("isDeleted") Integer isDeleted, @Param("modifier") String modifier, @Param("gmtModified") Date gmtModified);

	void insertUserRoleSelective(SysUserRole userRole);
}
