package com.nork.system.service;

import java.io.IOException;
import java.util.List;

import com.nork.system.model.SysUserSystemOperationLog;
import com.nork.system.model.search.SysUserSystemOperationLogSearch;

/**   
 * @Title: SysUserSystemOperationLogService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-用户系统操作记录Service
 * @createAuthor pandajun 
 * @CreateDate 2017-07-05 19:43:31
 * @version V1.0   
 */
public interface SysUserSystemOperationLogService {
	/**
	 * 新增数据
	 *
	 * @param sysUserSystemOperationLog
	 * @return  int 
	 */
	public int add(SysUserSystemOperationLog sysUserSystemOperationLog);

	/**
	 *    更新数据
	 *
	 * @param sysUserSystemOperationLog
	 * @return  int 
	 */
	public int update(SysUserSystemOperationLog sysUserSystemOperationLog);

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
	 * @return  SysUserSystemOperationLog 
	 */
	public SysUserSystemOperationLog get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysUserSystemOperationLog
	 * @return   List<SysUserSystemOperationLog>
	 */
	public List<SysUserSystemOperationLog> getList(SysUserSystemOperationLog sysUserSystemOperationLog);

	/**
	 *    获取数据数量
	 *
	 * @param  sysUserSystemOperationLog
	 * @return   int
	 */
	public int getCount(SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserSystemOperationLog
	 * @return   List<SysUserSystemOperationLog>
	 */
	public List<SysUserSystemOperationLog> getPaginatedList(
				SysUserSystemOperationLogSearch sysUserSystemOperationLogtSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 *   txt存数据
	 * @param  sysUserLoginLog
	 * @return void
	 */
	public void txtSaveOperationLog(SysUserSystemOperationLog sysUserSystemOperationLog);
	
	
	/**
	 *   txt数据解析到数据库
	 * @param  sysUserLoginLog
	 * @return void
	 */
	public void userOperationLog();
	
	/**
	 *   txt数据解析到数据库
	 * @return void
	 */
	public void deleteTextFile();
	
	
	/**
	 *   APP端文件解析
	 * @return void
	 */
	public void analysisAppTextFile() throws IOException;
	
}
