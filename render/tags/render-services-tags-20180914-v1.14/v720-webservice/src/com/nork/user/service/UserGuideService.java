package com.nork.user.service;

import java.util.List;

import com.nork.user.model.UserGuide;
import com.nork.user.model.search.UserGuideSearch;

/**
 * @Title: UserGuideService.java
 * @Package com.nork.user.service
 * @Description:用户指南-用户指南Service
 * @createAuthor pandajun
 * @CreateDate 2016-11-21 20:22:33
 * @version V1.0
 */
public interface UserGuideService {
	/**
	 * 新增数据
	 *
	 * @param userGuide
	 * @return int
	 */
	public int add(UserGuide userGuide);

	/**
	 * 更新数据
	 *
	 * @param userGuide
	 * @return int
	 */
	public int update(UserGuide userGuide);

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	public int delete(Integer id);

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return UserGuide
	 */
	public UserGuide get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param userGuide
	 * @return List<UserGuide>
	 */
	public List<UserGuide> getList(UserGuide userGuide);

	/**
	 * 获取数据数量
	 *
	 * @param userGuide
	 * @return int
	 */
	public int getCount(UserGuideSearch userGuideSearch);

	/**
	 * 分页获取数据
	 *
	 * @param userGuide
	 * @return List<UserGuide>
	 */
	public List<UserGuide> getPaginatedList(UserGuideSearch userGuidetSearch);

	/**
	 * 获得排序比我大的最小的一条
	 * 
	 * @param sort
	 * @return
	 */
	public UserGuide getNextUserGuide(Integer sort);

	/**
	 * 交换排序
	 * 
	 * @param sysFaq
	 * @param sysFaq_current
	 * @return
	 */
	public int exchangeSort(Integer id, Integer sort,String type);

	public int getMaxSort();

}
