package com.nork.resource.service;

import java.util.List;

import com.nork.resource.model.ResUsedProducts;
import com.nork.resource.model.search.ResUsedProductsSearch;

/**   
 * @Title: ResUsedProductsService.java 
 * @Package com.nork.resource.service
 * @Description:文件资源-已使用产品资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:50:18
 * @version V1.0   
 */
public interface ResUsedProductsService {
	/**
	 * 新增数据
	 *
	 * @param resUsedProducts
	 * @return  int 
	 */
	public int add(ResUsedProducts resUsedProducts);

	/**
	 *    更新数据
	 *
	 * @param resUsedProducts
	 * @return  int 
	 */
	public int update(ResUsedProducts resUsedProducts);

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
	 * @return  ResUsedProducts 
	 */
	public ResUsedProducts get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resUsedProducts
	 * @return   List<ResUsedProducts>
	 */
	public List<ResUsedProducts> getList(ResUsedProducts resUsedProducts);

	/**
	 *    获取数据数量
	 *
	 * @param  resUsedProducts
	 * @return   int
	 */
	public int getCount(ResUsedProductsSearch resUsedProductsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resUsedProducts
	 * @return   List<ResUsedProducts>
	 */
	public List<ResUsedProducts> getPaginatedList(
				ResUsedProductsSearch resUsedProductstSearch);

	/**
	 * 其他
	 * 
	 */

}
