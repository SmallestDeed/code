package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStoreCatory;
import com.nork.cityunion.model.search.UnionDesignPlanStoreCatorySearch;

/**   
 * @Title: UnionDesignPlanStoreCatoryService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟设计方案库类别Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:06
 * @version V1.0   
 */
public interface UnionDesignPlanStoreCatoryService {
	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreCatory
	 * @return  int 
	 */
	public int add(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory);

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreCatory
	 * @return  int 
	 */
	public int update(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory);

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
	 * @return  UnionDesignPlanStoreCatory 
	 */
	public UnionDesignPlanStoreCatory get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreCatory
	 * @return   List<UnionDesignPlanStoreCatory>
	 */
	public List<UnionDesignPlanStoreCatory> getList(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory);

	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreCatory
	 * @return   int
	 */
	public int getCount(UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreCatorytSearch
	 * @return   List<UnionDesignPlanStoreCatory>
	 */
	public List<UnionDesignPlanStoreCatory> getPaginatedList(
				UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorytSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 方案脚本素材分类数量
	 * @param userId
	 * @return
	 */
	int findStoreCatoryCount(Integer userId, String catoryName);

	/**
	 * 方案脚本素材分类集合
	 * @param userId
	 * @return
	 */
	List<UnionDesignPlanStoreCatory> findStoreCatoryList(Integer userId);

	/**
	 * 根据删除数据及关联关系数据
	 * @param id
	 * @return
	 */
	boolean del(Integer id);

}
