package com.sandu.api.servicepurchase.serivce;

import com.github.pagehelper.PageInfo;
import com.sandu.api.servicepurchase.input.query.ServicesRoleRelQuery;
import com.sandu.api.servicepurchase.model.ServicesRoleRel;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseListBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ServicesRoleRelService{

    List<ServicesRoleRel> getByServiceId(Long id);

    PageInfo<ServicesPurchaseListBO> getBePurchasedServices(Integer companyId, Integer start, Integer limit);

    List<ServicesRoleRel> queryByOption(ServicesRoleRelQuery servicesRoleRelQuery);

    List<ServicesRoleRel> queryByIds(Set<Long> ids,Integer isDelete);

    List<ServicesRoleRel> queryByServiceId(Long id,Integer isDelete);
}