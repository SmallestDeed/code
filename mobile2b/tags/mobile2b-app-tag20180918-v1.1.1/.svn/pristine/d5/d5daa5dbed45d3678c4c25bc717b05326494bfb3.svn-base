package com.nork.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.ProductAttribute;
import com.nork.product.model.search.ProductAttributeSearch;

/**   
 * @Title: ProductAttributeMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-产品属性Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 13:17:36
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProductAttributeMapper {
    int insertSelective(ProductAttribute record);

    int updateByPrimaryKeySelective(ProductAttribute record);
  
    int deleteByPrimaryKey(Integer id);
        
    ProductAttribute selectByPrimaryKey(Integer id);
    
    int selectCount(ProductAttributeSearch productAttributeSearch);
    
	List<ProductAttribute> selectPaginatedList(
			ProductAttributeSearch productAttributeSearch);
			
    List<ProductAttribute> selectList(ProductAttribute productAttribute);
    
    List<ProductAttribute> getMergeAttribute(ProductAttribute productAttribute);
    
	/**
	 * 其他
	 * 
	 */

    /**
     * 查询产品属性，包含属性定义内容
     * @param productAttribute
     * @return
     */
    List<ProductAttribute> getAttributeUnionPropsList(ProductAttribute productAttribute);

    /**
     * 查询出满足多个属性的产品
     * @param map
     * @return
     */
    List<ProductAttribute> selectIntersectProductAttribute(Map<String,Object> map);

    /**
     * 查询属性,并且join查出更多的属性
     * 属性1:product_attribute->找出对应的product_props->根据pid找出上级属性product_props->获得filterOrder字段
     * 属性2:getPropValue
     * @author huangsongbo
     * @param productAttribute
     * @return
     */
	List<ProductAttribute> selectListMoreInfo(ProductAttribute productAttribute);

}
