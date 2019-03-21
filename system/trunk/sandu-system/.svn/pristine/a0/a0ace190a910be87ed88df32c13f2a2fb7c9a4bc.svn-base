package com.sandu.api.servicepurchase.serivce;

import com.github.pagehelper.PageInfo;
import com.sandu.api.servicepurchase.input.ServicesBaseInfoAdd;
import com.sandu.api.servicepurchase.input.ServicesBaseInfoUpdate;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.ServicesRole;
import com.sandu.api.servicepurchase.model.bo.ServicesFuncBO;
import com.sandu.api.servicepurchase.model.bo.ServicesListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseLogListBO;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import com.sandu.commons.ResponseEnvelope;
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

    /**
     * 查询套餐列表
     * @param query
     * @return
     */
    PageInfo<ServicesBaseInfo> queryAll(ServicesBaseInfoQuery query);

    /**
     * 新增套餐
     * @param input
     * @return
     */
    ResponseEnvelope saveServices(ServicesBaseInfoAdd input);

    /**
     * 修改套餐
     * @param input
     * @return
     */
    ResponseEnvelope updateServices(ServicesBaseInfoUpdate input);

    /**
     * 删除套餐
     * @param id
     * @param creator
     * @return
     */
    int deleteSetMeal(String id,String creator);

    /**
     * 启用或禁用套餐
     * @param id
     * @param servicesStatus
     * @return
     */
    int openOrCloseServices(String id, String servicesStatus);

    /**
     * 获取套餐详情
     * @param id
     * @return
     */
    ServicesBaseInfo getServicesById(int id);

    /**
     * 为企业设置价格
     * @param input
     * @return
     */
    ResponseEnvelope addCompanyPrice(ServicesBaseInfoUpdate input);

    /**
     * 根据套餐id获取企业套餐信息
     * @param query
     * @return
     */
    List<ServicesPriceVO> queryServicesPriceInfo(ServicesBaseInfoQuery query);

    /**
     * 删除企业价格设置
     * @param companyId
     * @param servicesId
     * @return
     */
    int deleteCompanyPrice(Integer companyId, Integer servicesId);

    /**
     * 获取企业设置价格详情信息
     * @param companyId
     * @param servicesId
     * @return
     */
    ServicesBaseInfo getCompanyPriceDetail(Integer companyId, Integer servicesId);

    /**
     * 修改企业设置价格
     * @param input
     * @return
     */
    ResponseEnvelope updateCompanyPrice(ServicesBaseInfoUpdate input);

    /**
     * 根据套餐id获取关联功能
     * @param servicesId
     * @return
     */
    List<ServicesFuncBO> getFunctionList(Integer servicesId);

    /**
     * 保存套餐关联功能
     * @param servicesId
     * @param roleIds
     * @return
     */
    Integer saveFunction(Integer servicesId, String roleIds);

    /**
     * 移除套餐关联功能
     * @param servicesId
     * @param roleIds
     * @return
     */
    Integer removeFunction(Integer servicesId, String roleIds);

    List<ServicesBaseInfo> queryServiceBaseInfo(ServicesBaseInfoQuery servicesBaseInfoQuery);
}