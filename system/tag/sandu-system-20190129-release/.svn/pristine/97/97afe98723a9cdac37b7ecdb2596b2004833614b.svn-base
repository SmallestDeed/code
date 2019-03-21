package com.sandu.api.servicepurchase.serivce;

import com.github.pagehelper.PageInfo;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.bo.ServicesListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseLogListBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ServicesBaseInfoService {

    PageInfo<ServicesListBO> getServicesListByUserScope(List<Integer> userTypes,String saleChannel, Integer page, Integer limit);

    ServicesBaseInfo getById(Long id);

    PageInfo<ServicesPurchaseLogListBO> getPurchasedServicesLogsByCompanyId(Integer companyId, Integer start, Integer limit);

    public List<ServicesBaseInfo> getServicesByType(String servicesType, String saleChannel);

    List<ServicesBaseInfo> queryByOption(ServicesBaseInfoQuery servicesBaseInfoQuery);

    /**
     * 查询可购买的套餐
     */
    List<ServicesBaseInfo> queryBuyServices(Long noId, String userScope, String saleChannel, String servicesStatus, Integer servicesType, Integer isDeleted, Integer start, Integer limit);

    /**
     * 校验套餐是否可用
     *
     * @param Id
     * @param userScopeSet
     * @param saleChannel
     * @param servicesStatus
     * @param servicesType
     * @param isDeleted
     * @return
     */
    ServicesBaseInfo queryReBuyServices(Long Id, Set<String> userScopeSet, String saleChannel, String servicesStatus, Integer servicesType,
                                        Integer isDeleted);

    /***
     * 查询记录数
     * @param userScope
     * @param saleChannel
     * @param servicesStatus
     * @param servicesType
     * @param isDeleted
     * @return
     */
    int countQueryBuyServices(Long noId, String userScope, String saleChannel, String servicesStatus, Integer servicesType, Integer isDeleted);

    List<ServicesBaseInfo> getByIds(Set<Long> collect);

    List<ServicesBaseInfo> getServicesListByUserScopeAndTye(String servicesType, String saleChannel, String userScope);
}