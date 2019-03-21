package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysUserLoginLog;
import com.nork.system.model.search.SysUserLoginLogSearch;

/**   
 * @Title: SysUserLoginLogService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-用户信息采集Service
 * @createAuthor pandajun 
 * @CreateDate 2017-06-28 16:14:59
 * @version V1.0   
 */
public interface SysUserLoginLogService {
	/**
	 * 新增数据
	 *
	 * @param sysUserLoginLog
	 * @return  int 
	 */
	public int add(SysUserLoginLog sysUserLoginLog);

	/**
	 *    更新数据
	 *
	 * @param sysUserLoginLog
	 * @return  int 
	 */
	public int update(SysUserLoginLog sysUserLoginLog);

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
	 * @return  SysUserLoginLog 
	 */
	public SysUserLoginLog get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysUserLoginLog
	 * @return   List<SysUserLoginLog>
	 */
	public List<SysUserLoginLog> getList(SysUserLoginLog sysUserLoginLog);

	/**
	 *    获取数据数量
	 *
	 * @param  sysUserLoginLog
	 * @return   int
	 */
	public int getCount(SysUserLoginLogSearch sysUserLoginLogSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserLoginLog
	 * @return   List<SysUserLoginLog>
	 */
	public List<SysUserLoginLog> getPaginatedList(
				SysUserLoginLogSearch sysUserLoginLogtSearch);

	/**
	 * 其他
	 * 
	 */
	
	
	/**
	 *   批量插入
	 * @param  List<SysUserLoginLog>
	 * @return int
	 */
	public int insertBatch(List<SysUserLoginLog> list);
	
	/**
	 *   txt存数据
	 * @param  sysUserLoginLog
	 * @return void
	 */
	public void txtSaveUserLog(SysUserLoginLog sysUserLoginLog);
	
	/**
	 *   txt数据录入到数据库表
	 * @param  sysUserLoginLog
	 * @return void
	 */
	public void userLoginDataEntry();
	
	/**
	 *  删除备份文件夹中七天前的txt
	 * @return void
	 */
	public void deleteText();
	
}
