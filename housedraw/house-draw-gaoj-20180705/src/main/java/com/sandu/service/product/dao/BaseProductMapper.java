package com.sandu.service.product.dao;

import java.util.List;

import com.sandu.api.house.model.DrawBaseProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.product.bo.BaseProductBO;
import com.sandu.api.product.input.BaseProductQuery;
import com.sandu.api.product.model.BaseProduct;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */

@Repository
public interface BaseProductMapper {

	List<BaseProductBO> listProductByType(@Param("query")BaseProductQuery query, @Param("args")String[] args);
	
	Long getProductByTypeCount(@Param("query")BaseProductQuery query, @Param("args")String[] args);

	Integer findBaseProductByProductCode(String productCode);
	
	BaseProduct getBaseProductByProductCode(String productCode);
	
	Integer updateByPrimaryKeySelective(BaseProduct baseProduct);

	BaseProduct selectByPrimaryKey(Long id);
	
	Integer insertSelective(BaseProduct baseProduct);

    Integer addBatchBaseProduct(List<DrawBaseProduct> addProducts);

	Integer updateByPrimaryKeySelective1(DrawBaseProduct updateProduct);
}
