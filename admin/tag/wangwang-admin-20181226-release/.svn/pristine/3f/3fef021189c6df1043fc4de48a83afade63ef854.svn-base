package com.sandu.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.company.input.CompanyShopQuery;
import com.sandu.api.company.model.bo.CompanyShopListBO;
import com.sandu.api.company.service.CompanyShopService;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.service.company.dao.CompanyShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
@Service
public class CompanyShopServiceImpl implements CompanyShopService {


	@Autowired
	private CompanyShopDao companyShopDao;


	@Override
	public PageInfo<CompanyShopListBO> listCompanyShop(CompanyShopQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(companyShopDao.listCompanyShop(query));
	}

	@Override
	public CompanyShop getById(int id) {
		return companyShopDao.get(id);
	}

	@Override
	public Map<String, String> shopId2CompanyName(List<Integer> shopIds) {
		if (shopIds == null || shopIds.isEmpty()) {
			return Collections.emptyMap();
		}
		HashMap<String, String> result = new HashMap<>();
		List<Map<String, String>> stringStringMap = companyShopDao.shopId2CompanyName(shopIds);
		stringStringMap.forEach(item -> {
			result.put(item.get("shopId"), item.get("companyName"));
		});
		return result;
	}
}
