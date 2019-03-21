package com.nork.product.service;

import java.util.List;
import java.util.Map;

import com.nork.product.model.ProductAttribute;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.model.search.ProductAttributeSearch;

/**   
 * @Title: ProductAttributeService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品属性Service
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 13:17:36
 * @version V1.0   
 */
public interface ProductAttributeService {
	/**
	 * 新增数据
	 *
	 * @param productAttribute
	 * @return  int 
	 */
	public int add(ProductAttribute productAttribute);

	/**
	 *    更新数据
	 *
	 * @param productAttribute
	 * @return  int 
	 */
	public int update(ProductAttribute productAttribute);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ProductAttribute 
	 */
	public ProductAttribute get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  productAttribute
	 * @return   List<ProductAttribute>
	 */
	public List<ProductAttribute> getList(ProductAttribute productAttribute);

	/**
	 *    获取数据数量
	 *
	 * @param  productAttribute
	 * @return   int
	 */
	public int getCount(ProductAttributeSearch productAttributeSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  productAttribute
	 * @return   List<ProductAttribute>
	 */
	public List<ProductAttribute> getPaginatedList(
				ProductAttributeSearch productAttributetSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  productAttribute
	 * @return   List<ProductAttribute>
	 */
	public List<ProductAttribute> getMergeAttribute(
				ProductAttribute productAttributet);
	
	
	
	/**
	 * getPropertyMap
	 * 通过产品id  获取属性值 的map   map的结构（ 键是product_attribute表中attribute_key  值是product_props表中的prop_value）
	 * @param productId
	 * @return
	 */
	public Map<String,String>  getPropertyMap(Integer productId);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 查询产品属性，包含属性定义内容
	 * @param productAttribute
	 * @return
	 */
	public List<ProductAttribute> getAttributeUnionPropsList(ProductAttribute productAttribute);

	/**
	 * 查询出满足多个属性的产品
	 * @param map
	 * @return
	 */
	public List<ProductAttribute> selectIntersectProductAttribute(Map<String,Object> map);
	/**
	 * 查询满足多个属性的条件
	 * @param String
	 * @return
	 */
	public ProductCategoryRel getAttributeCondition(ProductCategoryRel productCategoryRel,Integer productId);
	
	/**
	 * getPropertyOrderMap
	 * 通过产品id  获取属性类型为排序 的map   map的结构（ 键是product_attribute表中attribute_key  值是product_attribute表中的attribute_value_id）
	 * @param productId
	 * @return
	 */
	public Map<String,String>  getPropertyOrderMap(Integer productId);
	
	/**
	 * 设置主产品属性过滤查询条件
	 * @param groupSearch
	 * @param productId
	 */
	public void setProductAttributeQueryCondition(GroupProductSearch groupSearch, Integer productId);

	public Map<String, String> getPropertyMapV2(Integer productId);

	public void getOnekeyDecorateProductAttributeFilterCondition(BaseProductSearch baseProductSearch, Integer productId);
	
}
