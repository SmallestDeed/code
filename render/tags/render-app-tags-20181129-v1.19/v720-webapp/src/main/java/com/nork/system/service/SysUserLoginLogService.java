package com.nork.system.service;

import com.nork.system.model.SysUserLoginLog;

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
	 *   txt存数据
	 * @param  sysUserLoginLog
	 * @return void
	 */
	public void txtSaveUserLog(SysUserLoginLog sysUserLoginLog);
	
	
	
}
