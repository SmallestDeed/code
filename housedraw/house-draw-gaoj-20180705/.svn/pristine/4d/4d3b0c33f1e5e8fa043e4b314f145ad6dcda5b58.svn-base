package com.sandu.web.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.product.bo.BaseProductBO;
import com.sandu.api.product.input.BaseProductQuery;
import com.sandu.api.product.service.BaseProductService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.house.ProductPutawayState;
import com.sandu.exception.BusinessException;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月21日
 */

@Api(tags = "产品")
@RestController
@RequestMapping("/v1/product")
public class BaseProductController extends BaseController {

	private static final String BAIMO_PREFIX = "baimo";
	private static final Integer UNLIMIT = -1;

	@Autowired
	private BaseProductService baseProductService;

	@ApiOperation(value = "获取软装单品白膜产品列表", response = BaseProductBO.class)
	@PostMapping("/get/productbytype")
	public ReturnData<?> listBaimoProduct(HttpServletRequest request, BaseProductQuery query) {
		ReturnData response = setMsgId(request);

		if (!StringUtils.isNotBlank(query.getProductTypeValue())) {
			throw new BusinessException(Boolean.FALSE, ResponseEnum.PRODUCT_TYPE_VALUE_EMTPY);
		}

		query.setProductCode(BAIMO_PREFIX);
		query.setPutawayState(new Integer[] { ProductPutawayState.Product.SHELVE.getValue(), ProductPutawayState.Product.RELEASE.getValue() });

		// if (query.getPageSize() != UNLIMIT) {
		// query.setPageSize(super.getLimit(query.getPageSize()));
		// query.setPageNum(super.getPage(query.getPageNum(), query.getPageSize()));
		// }

		// TODO 这个前端和产品的要求，我也...(^-^)```懵了 ?
		query.setPageNum(0);
		query.setPageSize(50);

        Map<String, Object> map = baseProductService.listBaimoProduct(query, true);
		Object list = map.get("list");
		List<BaseProductBO> data = list == null ? new ArrayList<>() : (List<BaseProductBO>) list;

		return response.list(data);
	}
}
