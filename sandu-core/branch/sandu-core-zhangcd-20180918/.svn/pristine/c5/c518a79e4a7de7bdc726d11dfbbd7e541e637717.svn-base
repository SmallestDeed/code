package com.sandu.api.base.service;

import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.model.BaseCompany;
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

    BaseCompany getCompanyByConpanyAppId(String conpanyAppId);


    BaseCompany getCompanyByDomainUrl(String domainUrl);


    ResponseEnvelope validateCompany(Integer id, Integer brandId);

    BaseCompany getCompanyByBrandId(Integer brandId);
}
