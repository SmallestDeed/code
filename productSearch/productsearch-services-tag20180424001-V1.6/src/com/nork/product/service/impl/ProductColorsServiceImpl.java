package com.nork.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.dao.ProductColorsMapper;
import com.nork.product.model.ProductColors;
import com.nork.product.service.ProductColorsService;

/**   
 * @Title: ProductColorsServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品色系-产品色系ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-12-14 18:25:53
 * @version V1.0   
 */
@Service("productColorsService")
public class ProductColorsServiceImpl implements ProductColorsService {

	private ProductColorsMapper productColorsMapper;

	@Autowired
	public void setProductColorsMapper(
			ProductColorsMapper productColorsMapper) {
		this.productColorsMapper = productColorsMapper;
	}

	/**
	 * 所有数据
	 * 
	 * @param  productColors
	 * @return   List<ProductColors>
	 */
	@Override
	public List<ProductColors> getList(ProductColors productColors) {
	    return productColorsMapper.selectList(productColors);
	}
	

	@Override
	public ProductColors getByCode(ProductColors productColors) {
		return productColorsMapper.selectByCode(productColors);
	}

}
