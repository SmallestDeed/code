package com.nork.product.service;

import java.util.List;
import java.util.Set;

import com.nork.product.model.PrepProductPropsInfo;
import com.nork.product.model.search.PrepProductPropsInfoSearch;

/**   
 * @Title: PrepProductPropsInfoService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-预处理表(属性匹配)Service
 * @createAuthor pandajun 
 * @CreateDate 2017-02-25 14:55:01
 * @version V1.0   
 */
public interface PrepProductPropsInfoService {
	/**
	 * 新增数据
	 *
	 * @param prepProductPropsInfo
	 * @return  int 
	 */
	public int add(PrepProductPropsInfo prepProductPropsInfo);

	/**
	 *    更新数据
	 *
	 * @param prepProductPropsInfo
	 * @return  int 
	 */
	public int update(PrepProductPropsInfo prepProductPropsInfo);

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
	 * @return  PrepProductPropsInfo 
	 */
	public PrepProductPropsInfo get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  prepProductPropsInfo
	 * @return   List<PrepProductPropsInfo>
	 */
	public List<PrepProductPropsInfo> getList(PrepProductPropsInfo prepProductPropsInfo);

	/**
	 *    获取数据数量
	 *
	 * @param  prepProductPropsInfo
	 * @return   int
	 */
	public int getCount(PrepProductPropsInfoSearch prepProductPropsInfoSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  prepProductPropsInfo
	 * @return   List<PrepProductPropsInfo>
	 */
	public List<PrepProductPropsInfo> getPaginatedList(
				PrepProductPropsInfoSearch prepProductPropsInfotSearch);
	
	/**
	 * 更新属性(根据productIdList)
	 * @author huangsongbo
	 * @param updatePropsProductIdList
	 */
	public void updateByProductIdList(Set<Integer> updatePropsProductIdList);

	void deleteByProductId(Integer productIdItem);

	void batchSave(List<PrepProductPropsInfo> totalList);
	
	/**
	 * 更新属性(根据productStatusList)
	 * @author huangsongbo
	 * @param productStatusList
	 */
	public void noTranUpdateByProductStatusList(List<Integer> productStatusList);

	/**
	 * 顾名思义
	 * @author huangsongbo
	 * @param productIdList
	 */
	void deleteByProductIdList(List<Integer> productIdList);

	/**
	 * 顾名思义
	 * @author huangsongbo
	 * @param productIdList
	 */
	void deleteByMatchProductIdList(List<Integer> productIdList);
	
	public List<PrepProductPropsInfo> getAllPrepProductPropsList(List<Integer> productStatusList);

}
