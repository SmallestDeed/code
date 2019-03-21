package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysAuditorUser;
import com.nork.system.model.search.SysAuditorUserSearch;

/**   
 * @Title: SysAuditorUserService.java 
 * @Package com.nork.system.service
 * @Description:系统-审核人员和被审核人员绑定表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-12-25 17:34:30
 * @version V1.0   
 */
public interface SysAuditorUserService {
	/**
	 * 新增数据
	 *
	 * @param sysAuditorUser
	 * @return  int 
	 */
	public int add(SysAuditorUser sysAuditorUser);

	/**
	 *    更新数据
	 *
	 * @param sysAuditorUser
	 * @return  int 
	 */
	public int update(SysAuditorUser sysAuditorUser);

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
	 * @return  SysAuditorUser 
	 */
	public SysAuditorUser get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysAuditorUser
	 * @return   List<SysAuditorUser>
	 */
	public List<SysAuditorUser> getList(SysAuditorUser sysAuditorUser);

	/**
	 *    获取数据数量
	 *
	 * @param  sysAuditorUser
	 * @return   int
	 */
	public int getCount(SysAuditorUserSearch sysAuditorUserSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysAuditorUser
	 * @return   List<SysAuditorUser>
	 */
	public List<SysAuditorUser> getPaginatedList(
				SysAuditorUserSearch sysAuditorUsertSearch);

	/**
	 * 其他
	 * 
	 */

}
