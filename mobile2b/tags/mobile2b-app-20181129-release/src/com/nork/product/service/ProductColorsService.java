package com.nork.product.service;

import java.util.List;

import com.nork.product.model.ProductColors;

/**   
 * @Title: ProductColorsService.java 
 * @Package com.nork.product.service
 * @Description:产品色系-产品色系Service
 * @createAuthor pandajun 
 * @CreateDate 2016-12-14 18:25:53
 * @version V1.0   
 */
public interface ProductColorsService {
	/**
	 * 根据longcode查询
	 * @param productColors
	 * @return
	 */
	public ProductColors getByCode(ProductColors productColors);

	/**
	 * 所有数据
	 * 
	 * @param  productColors
	 * @return   List<ProductColors>
	 */
	public List<ProductColors> getList(ProductColors productColors);

}
