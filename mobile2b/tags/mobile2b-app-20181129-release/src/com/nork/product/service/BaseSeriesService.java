package com.nork.product.service;

import java.util.List;

import com.nork.product.model.BaseSeries;
import com.nork.product.model.search.BaseSeriesSearch;

/**   
 * @Title: BaseSeriesService.java 
 * @Package com.nork.product.service
 * @Description:product-系列表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-11-04 11:06:16
 * @version V1.0   
 */
public interface BaseSeriesService {
	/**
	 * 新增数据
	 *
	 * @param baseSeries
	 * @return  int 
	 */
	public int add(BaseSeries baseSeries);

	/**
	 *    更新数据
	 *
	 * @param baseSeries
	 * @return  int 
	 */
	public int update(BaseSeries baseSeries);

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
	 * @return  BaseSeries 
	 */
	public BaseSeries get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseSeries
	 * @return   List<BaseSeries>
	 */
	public List<BaseSeries> getList(BaseSeries baseSeries);

	/**
	 *    获取数据数量
	 *
	 * @param  baseSeries
	 * @return   int
	 */
	public int getCount(BaseSeriesSearch baseSeriesSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseSeries
	 * @return   List<BaseSeries>
	 */
	public List<BaseSeries> getPaginatedList(
				BaseSeriesSearch baseSeriestSearch);

	/**
	 * 获取用户授权码绑定的品牌系列
	 * @param userId
	 * @return
	 */
	List<BaseSeries> getSeriesByUserAuthorizedBrandCode(Integer userId);


	/**
	 * 获取用户品牌系列数量
	 * @param idsList
	 * @return
	 */
	int getSeriesCount(List<Integer> idsList, Integer level);


	/**
	 * 获取用户品牌系列列表
	 * @param idsList
	 * @return
	 */
	List<BaseSeries> getSeriesList(List<Integer> idsList, Integer level);

}
