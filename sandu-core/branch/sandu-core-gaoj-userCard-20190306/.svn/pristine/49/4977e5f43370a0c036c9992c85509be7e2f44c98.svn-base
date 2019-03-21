package com.sandu.api.base.service;

import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.input.GroupPurchaseActivitySearch;
import com.sandu.api.base.model.*;
import com.sandu.api.base.output.BaseCompanyVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version V1.0
 * @Title: BaseCompanyService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-企业表Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:45
 */
@Component
public interface BaseCompanyService {

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseCompany
     */
    BaseCompany get(Integer id);

    /**
     * 获取企业列表
     *
     * @param baseCompany
     * @return
     */
    List<BaseCompany> getList(BaseCompany baseCompany);

    /**
     * 根据企业域名查询企业信息
     *
     * @param companyDomainName 企业域名
     * @return
     */
    BaseCompany getCompanyByDomainName(String companyDomainName);

    BaseCompany getCompanyByConpanyAppId(String companyAppId);


    BaseCompany getCompanyByDomainUrl(String domainUrl);


    ResponseEnvelope validateCompany(Integer id, Integer brandId);

    BaseCompany getCompanyByBrandId(Integer brandId);

    MiniProgramDashboard getMiniProgramDashboardByAppId(String appId);


    CompanyBrandDesc getCompanyBrandDesc(String appId, String getRichContext);

    RichContext getRichContext(RichContext context);

    List<MiniProgramActBargain> getMiniProgramActBargainListByAppId(String appId);

    List<GroupPurchaseActivity> getMiniProgramGroupPurchaseActivityListByAppId(GroupPurchaseActivitySearch search);

    List<CompanyMiniProgramConfig> getMiniProgramConfigByCompanyId(Integer companyId);

    List<Integer> getAllDealerCompanyId(Integer companyId);

    List<BaseCompanyVo> getAllDealerCompany(Integer companyId);

    /**
     * 功能描述: 获取用户关联公司的所有公司
     * 如果是厂商用户就返回厂商企业和厂商下的所有经销商企业
     * 其他的用户类型就返回用户关联企业
     *
     * @param sysUser
     * @return java.util.List<com.sandu.api.base.model.BaseCompany>
     * @throws
     * @author gaoj
     * @date 2019/3/11 11:18
     */
    List<BaseCompanyVo> getAllCompany(SysUser sysUser);
}
