package com.sandu.api.product.service;

import java.util.Map;

import com.sandu.api.house.input.GroupProductQuery;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月21日
 */

public interface GroupProductService {

	Long countGroupProduct(GroupProductQuery query);
	
	Map<String, Object> listGroupProduct(GroupProductQuery query);

	Map<String, Object> listGroupProduct(GroupProductQuery query, boolean useCache);
}
