package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.input.query.ServicesRoleRelQuery;
import com.sandu.api.servicepurchase.model.ServicesRoleRel;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseListBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicesRoleRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServicesRoleRel record);

    int insertSelective(ServicesRoleRel record);


    ServicesRoleRel selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(ServicesRoleRel record);

    int updateByPrimaryKey(ServicesRoleRel record);

    List<ServicesRoleRel> getByServiceId(@Param("id") Long id);

    List<ServicesPurchaseListBO> getBePurchasedServices(@Param("companyId") Integer companyId);

    List<ServicesRoleRel> queryByOption(ServicesRoleRelQuery servicesRoleRelQuery);
}