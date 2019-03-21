package com.nork.system.service;

import java.util.List;

import com.nork.system.model.UserAccess;
import com.nork.system.model.search.UserAccessSearch;

/**   
 * @Title: UserAccessService.java 
 * @Package com.nork.system.service
 * @Description:系统-用户访问表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-11-26 14:17:19
 * @version V1.0   
 */
public interface UserAccessService {
	/**
	 * 新增数据
	 *
	 * @param userAccess
	 * @return  int 
	 */
	public int add(UserAccess userAccess);

	/**
	 *    更新数据
	 *
	 * @param userAccess
	 * @return  int 
	 */
	public int update(UserAccess userAccess);

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
	 * @return  UserAccess 
	 */
	public UserAccess get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  userAccess
	 * @return   List<UserAccess>
	 */
	public List<UserAccess> getList(UserAccess userAccess);

	/**
	 *    获取数据数量
	 *
	 * @param  userAccess
	 * @return   int
	 */
	public int getCount(UserAccessSearch userAccessSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  userAccess
	 * @return   List<UserAccess>
	 */
	public List<UserAccess> getPaginatedList(
				UserAccessSearch userAccesstSearch);

	/**
	 * 其他
	 * 
	 */

}
