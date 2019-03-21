package com.sandu.api.product.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.product.bo.BaseProductBO;
import com.sandu.api.product.bo.ProductTypeBO;
import com.sandu.api.product.input.BaseProductQuery;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月21日
 */

public interface BaseProductService {
	
	Map<String, Object> listBaimoProduct(BaseProductQuery query);

	Map<String, Object> listBaimoProduct(BaseProductQuery query, boolean useCache);

	Map<Long, ProductTypeBO> listProductType(List<BaseProductBO> products);

	void buildProductType(ProductTypeBO proType, BaseProductBO product);
}
