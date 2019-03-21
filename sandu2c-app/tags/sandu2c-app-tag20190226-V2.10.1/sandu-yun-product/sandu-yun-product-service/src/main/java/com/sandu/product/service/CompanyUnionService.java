package com.sandu.product.service;

import java.util.List;


/**
 * @version V1.0
 * @Title: CompanyUnionService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-企业表Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:45
 */
public interface CompanyUnionService {

    /**
     * 根据企业id获取和它联盟的所有企业id
     *
     * @param companyId
     * @return
     */
    List<Integer> getAllCompanyId(Integer companyId);

    /**
     * 根据企业id获取和它联盟的所有企业下的品牌id
     *
     * @param companyId
     * @return
     */
    List<Integer> getAllBrandIds(Integer companyId);

}
