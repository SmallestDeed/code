package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysGroupUser;
import com.nork.system.model.search.SysGroupUserSearch;

/**   
 * @Title: SysGroupUserService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-用户组用户表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-18 18:35:04
 * @version V1.0   
 */
public interface SysGroupUserService {
	/**
	 * 新增数据
	 *
	 * @param sysGroupUser
	 * @return  int 
	 */
	public int add(SysGroupUser sysGroupUser);

	/**
	 *    更新数据
	 *
	 * @param sysGroupUser
	 * @return  int 
	 */
	public int update(SysGroupUser sysGroupUser);

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
	 * @return  SysGroupUser 
	 */
	public SysGroupUser get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysGroupUser
	 * @return   List<SysGroupUser>
	 */
	public List<SysGroupUser> getList(SysGroupUser sysGroupUser);

	/**
	 *    获取数据数量
	 *
	 * @param  sysGroupUser
	 * @return   int
	 */
	public int getCount(SysGroupUserSearch sysGroupUserSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysGroupUser
	 * @return   List<SysGroupUser>
	 */
	public List<SysGroupUser> getPaginatedList(
				SysGroupUserSearch sysGroupUsertSearch);

	/**
	 * 其他
	 * 
	 */

}
