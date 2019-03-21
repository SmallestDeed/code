package com.nork.productprops.service;

import java.util.List;

import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.search.ProductPropsSearch;

/**   
 * @Title: ProductPropsService.java 
 * @Package com.nork.productprops.service
 * @Description:产品属性-产品属性表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 10:40:03
 * @version V1.0   
 */
public interface ProductPropsService {
	/**
	 * 新增数据
	 *
	 * @param productProps
	 * @return  int 
	 */
	public int add(ProductProps productProps);

	/**
	 *    更新数据
	 *
	 * @param productProps
	 * @return  int 
	 */
	public int update(ProductProps productProps);

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
	 * @return  ProductProps 
	 */
	public ProductProps get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  productProps
	 * @return   List<ProductProps>
	 */
	public List<ProductProps> getList(ProductProps productProps);

	/**
	 *    获取数据数量
	 *
	 * @param  productProps
	 * @return   int
	 */
	public int getCount(ProductPropsSearch productPropsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  productProps
	 * @return   List<ProductProps>
	 */
	public List<ProductProps> getPaginatedList(
				ProductPropsSearch productPropstSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 异步加载产品属性
	 * @param pid
	 * @return
	 */
	List<ProductProps> asyncLoadTree(Integer pid);

	/**
	 *    分页获取数据
	 *
	 * @param  productProps
	 * @return   List<ProductProps>
	 */
	List<ProductProps> getPaginatedPropValsList(
			ProductPropsSearch productPropsSearch);

	public List<ProductProps> getAttributeKeyAndValueByProductId(Integer productId);

	/**
	 * 取得结构单品的属性(用于一件装修匹配)
	 * 
	 * @author huangsongbo
	 * @param productId
	 * @param productAttrCode 属性父节点code(墙面结构产品,父节点code是group,dim结构产品,父节点code是structureSign)
	 * @return
	 */
	public String getStructureProductPropValueByProductId(Integer productId, String productAttrCode);
	
}
