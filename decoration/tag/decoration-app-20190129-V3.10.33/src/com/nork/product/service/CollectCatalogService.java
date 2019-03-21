package com.nork.product.service;

import java.util.List;

import com.nork.product.model.CollectCatalog;
import com.nork.product.model.search.CollectCatalogSearch;

/**   
 * @Title: CollectCatalogService.java 
 * @Package com.nork.product.service
 * @Description:产品管理-收藏目录表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-07-01 10:46:26
 * @version V1.0   
 */
public interface CollectCatalogService {
	/**
	 * 新增数据
	 *
	 * @param collectCatalog
	 * @return  int 
	 */
	public int add(CollectCatalog collectCatalog);

	/**
	 *    更新数据
	 *
	 * @param collectCatalog
	 * @return  int 
	 */
	public int update(CollectCatalog collectCatalog);

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
	 * @return  CollectCatalog 
	 */
	public CollectCatalog get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  collectCatalog
	 * @return   List<CollectCatalog>
	 */
	public List<CollectCatalog> getList(CollectCatalog collectCatalog);

	/**
	 *    获取数据数量
	 *
	 * @param  collectCatalog
	 * @return   int
	 */
	public int getCount(CollectCatalogSearch collectCatalogSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  collectCatalog
	 * @return   List<CollectCatalog>
	 */
	public List<CollectCatalog> getPaginatedList(
				CollectCatalogSearch collectCatalogtSearch);

	/**
	 * 其他
	 * 
	 */

}
