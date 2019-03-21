package com.nork.product.service;

import java.util.List;

import com.nork.product.model.ProductUsageCount;
import com.nork.product.model.search.ProductUsageCountSearch;

/**   
 * @Title: ProductUsageCountService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-使用次数记录表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:18:44
 * @version V1.0   
 */
public interface ProductUsageCountService {
	/**
	 * 新增数据
	 *
	 * @param productUsageCount
	 * @return  int 
	 */
	public int add(ProductUsageCount productUsageCount);

	/**
	 *    更新数据
	 *
	 * @param productUsageCount
	 * @return  int 
	 */
	public int update(ProductUsageCount productUsageCount);

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
	 * @return  ProductUsageCount 
	 */
	public ProductUsageCount get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  productUsageCount
	 * @return   List<ProductUsageCount>
	 */
	public List<ProductUsageCount> getList(ProductUsageCount productUsageCount);

	/**
	 *    获取数据数量
	 *
	 * @param  productUsageCount
	 * @return   int
	 */
	public int getCount(ProductUsageCountSearch productUsageCountSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  productUsageCount
	 * @return   List<ProductUsageCount>
	 */
	public List<ProductUsageCount> getPaginatedList(
				ProductUsageCountSearch productUsageCounttSearch);

	/**
	 * 更新产品使用次数
	 * @author huangsongbo
	 * @param userId
	 * @param productId
	 * @param length 
	 */
	public void update(Integer userId, Integer productId, int length);
	
}
