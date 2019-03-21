package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.ProductColors;

/**   
 * @Title: ProductColorsMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品色系-产品色系Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-12-14 18:25:53
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProductColorsMapper {

    List<ProductColors> selectList(ProductColors productColors);

	ProductColors selectByCode(ProductColors productColors);
}
