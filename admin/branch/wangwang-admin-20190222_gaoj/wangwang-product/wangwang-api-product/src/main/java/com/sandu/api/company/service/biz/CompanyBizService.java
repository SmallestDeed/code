package com.sandu.api.company.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.company.input.CompanyShopQuery;
import com.sandu.api.company.input.CompanyUpdate;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.model.bo.CompanyBO;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.model.bo.CompanyShopListBO;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
public interface CompanyBizService {
    /**
     * 保存企业
     */
    boolean saveCompanyBiz(CompanyBO companyBO);

    /**
     * 获取
     * key:brandId
     * value : companyName
     *
     * @param brandIds
     * @return
     */
    Map<Integer, CompanyBrandBo> getCompanyByBrandIds(List<Integer> brandIds);

    /**
     * 根据公司ID查询信息
     *
     * @param id
     * @return
     */
    CompanyBO getCompanyInfoById(long id);

    /**
     * 更新企业信息(商家后台)
     *
     * @param companyUpdate
     * @return
     */
    boolean updateCompanyInfo(CompanyUpdate companyUpdate);

    PageInfo<CompanyShopListBO> listCompanyShop(CompanyShopQuery query);

    List<Company> listCompanyForCustomerOrder(Integer id);
}
