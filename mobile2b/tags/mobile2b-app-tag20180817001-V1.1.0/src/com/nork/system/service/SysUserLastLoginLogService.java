package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysUserLastLoginLog;
import com.nork.system.model.search.SysUserLastLoginLogSearch;

/**   
 * @Title: SysUserLastLoginLogService.java 
 * @Package com.nork.系统模块.service
 * @Description:system-用户最后登录时间Service
 * @createAuthor pandajun 
 * @CreateDate 2017-07-04 10:03:13
 * @version V1.0   
 */
public interface SysUserLastLoginLogService {
	/**
	 * 新增数据
	 *
	 * @param sysUserLastLoginLog
	 * @return  int 
	 */
	public int add(SysUserLastLoginLog sysUserLastLoginLog);

	/**
	 *    更新数据
	 *
	 * @param sysUserLastLoginLog
	 * @return  int 
	 */
	public int update(SysUserLastLoginLog sysUserLastLoginLog);

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
	 * @return  SysUserLastLoginLog 
	 */
	public SysUserLastLoginLog get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysUserLastLoginLog
	 * @return   List<SysUserLastLoginLog>
	 */
	public List<SysUserLastLoginLog> getList(SysUserLastLoginLog sysUserLastLoginLog);

	/**
	 *    获取数据数量
	 *
	 * @param  sysUserLastLoginLog
	 * @return   int
	 */
	public int getCount(SysUserLastLoginLogSearch sysUserLastLoginLogSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserLastLoginLog
	 * @return   List<SysUserLastLoginLog>
	 */
	public List<SysUserLastLoginLog> getPaginatedList(
				SysUserLastLoginLogSearch sysUserLastLoginLogtSearch);

	/**
	 * 其他
	 * 
	 */

}
