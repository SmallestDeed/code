package com.nork.design.service;

import java.util.List;

import com.nork.design.model.DesignPlanLike;
import com.nork.design.model.search.DesignPlanLikeSearch;

/**   
 * @Title: DesignPlanLikeService.java 
 * @Package com.nork.设计方案.service
 * @Description:设计方案点赞库-设计方案点赞Service
 * @createAuthor pandajun 
 * @CreateDate 2015-11-25 14:36:56
 * @version V1.0   
 */
public interface DesignPlanLikeService {
	/**
	 * 新增数据
	 *
	 * @param designPlanLike
	 * @return  int 
	 */
	public int add(DesignPlanLike designPlanLike);

	/**
	 *    更新数据
	 *
	 * @param designPlanLike
	 * @return  int 
	 */
	public int update(DesignPlanLike designPlanLike);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);
	
	/**
	 *    删除数据
	 *
	 * @param designPlanLike
	 * @return  int 
	 */
	public int deleteById(DesignPlanLike designPlanLike);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanLike 
	 */
	public DesignPlanLike get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designPlanLike
	 * @return   List<DesignPlanLike>
	 */
	public List<DesignPlanLike> getList(DesignPlanLike designPlanLike);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlanLike
	 * @return   int
	 */
	public int getCount(DesignPlanLikeSearch designPlanLikeSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanLike
	 * @return   List<DesignPlanLike>
	 */
	public List<DesignPlanLike> getPaginatedList(
				DesignPlanLikeSearch designPlanLiketSearch);

	/**
	 * 其他
	 * 
	 */

}
