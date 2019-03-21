package com.sandu.api.company.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.company.input.CompanyShopQuery;
import com.sandu.api.company.model.bo.CompanyShopListBO;
import com.sandu.api.shop.model.CompanyShop;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 企业店铺-service接口
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Component
public interface CompanyShopService {

	PageInfo<CompanyShopListBO> listCompanyShop(CompanyShopQuery query);

	CompanyShop getById(int id);

	Map<String, String> shopId2CompanyName(List<Integer> shopIds);
}
