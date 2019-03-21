package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.onekeydesign.model.UsedProducts;
import com.nork.onekeydesign.model.UserProductPlan;
import com.nork.onekeydesign.model.search.UsedProductsSearch;

/**   
 * @Title: UsedProductsService.java 
 * @Package com.nork.onekeydesign.service
 * @Description:设计方案-已使用产品表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-10 16:23:04
 * @version V1.0   
 */
public interface UsedProductsService {
	/**
	 * 新增数据
	 *
	 * @param usedProducts
	 * @return  int 
	 */
	public int add(UsedProducts usedProducts);

	/**
	 *    更新数据
	 *
	 * @param usedProducts
	 * @return  int 
	 */
	public int update(UsedProducts usedProducts);

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
	 * @return  UsedProducts 
	 */
	public UsedProducts get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  usedProducts
	 * @return   List<UsedProducts>
	 */
	public List<UsedProducts> getList(UsedProducts usedProducts);

	/**
	 *    获取数据数量
	 *
	 * @param  usedProducts
	 * @return   int
	 */
	public int getCount(UsedProductsSearch usedProductsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  usedProducts
	 * @return   List<UsedProducts>
	 */
	public List<UsedProducts> getPaginatedList(
				UsedProductsSearch usedProductstSearch);
	
	public List<UserProductPlan> getUserProductPlan (
			UserProductPlan userProductPlan);
	
	public List<UserProductPlan> getUsedProductPlanList(
			UsedProductsSearch usedProductsSearch);

	public Integer getUsedProductPlanCount(
			UsedProductsSearch usedProductsSearch);
	
}
