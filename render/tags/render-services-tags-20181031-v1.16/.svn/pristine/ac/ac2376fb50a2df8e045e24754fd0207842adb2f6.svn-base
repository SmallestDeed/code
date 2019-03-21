package com.nork.product.service;

import java.util.List;
import java.util.Set;

import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.search.PrepProductSearchInfoSearch;

/**   
 * @Title: PrepProductSearchInfoService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-预处理表(产品搜索)Service
 * @createAuthor pandajun 
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0   
 */
public interface PrepProductSearchInfoService {
	/**
	 * 新增数据
	 *
	 * @param prepProductSearchInfo
	 * @return  int 
	 */
	public int add(PrepProductSearchInfo prepProductSearchInfo);

	/**
	 *    更新数据
	 *
	 * @param prepProductSearchInfo
	 * @return  int 
	 */
	public int update(PrepProductSearchInfo prepProductSearchInfo);

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
	 * @return  PrepProductSearchInfo 
	 */
	public PrepProductSearchInfo get(Integer id,int userId);

	/**
	 * 所有数据
	 * 
	 * @param  prepProductSearchInfo
	 * @return   List<PrepProductSearchInfo>
	 */
	public List<PrepProductSearchInfo> getList(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode);

	/**
	 *    获取数据数量
	 *
	 * @param  prepProductSearchInfo
	 * @return   int
	 */
	public int getCount(PrepProductSearchInfoSearch prepProductSearchInfoSearch,int userId,String categoryCode);

	/**
	 *    分页获取数据
	 *
	 * @param  prepProductSearchInfo
	 * @return   List<PrepProductSearchInfo>
	 */
	public List<PrepProductSearchInfo> getPaginatedList(
				PrepProductSearchInfoSearch prepProductSearchInfotSearch,int userId,String categoryCode);

	/**
	 * 产品搜索
	 * @author huangsongbo
	 * @param prepProductSearchInfo
	 * @return
	 */
	public List<Integer> getProductIdList(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode);

	/**
	 * 产品数量搜索
	 * @author huangsongbo
	 * @param prepProductSearchInfo
	 * @return
	 */
	public Integer getCount(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode);
	
	/**
	 * 产品搜索(返回List<CategoryProductResult>)
	 * @author huangsongbo
	 * @param prepProductSearchInfo
	 * @return
	 */
	public List<CategoryProductResult> getProductIdListV2(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode);

	/**
	 * 更新已上架的产品(增量)
	 * 更新预处理主表,更新预处理属性表
	 * @author huangsongbo
	 */
	public void noTranUpdatePutawayProductAndSetStatus();

	/**
	 * 预处理数据(只更新指定产品的数据)
	 * @author huangsongbo
	 * @param updateMainProductIdList
	 */
	public void updateByProductIdList(Set<Integer> updateMainProductIdList);

	void deleteByProductIdList(Set<Integer> updateMainProductIdList);

	/**
	 * 删除数据(根据产品状态):
	 * 删除产品状态为productStatusList的产品数据对应的预处理主表数据
	 * @author huangsongbo
	 * @param productStatusList
	 */
	void deleteByProductStatusList(List<Integer> productStatusList);
	
	public int batchSavePrepSearchList(List<PrepProductSearchInfo> list);

}
