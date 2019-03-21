package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysUserMessage;

/**   
 * @Title: SysUserMessageService.java 
 * @Package com.nork.system.service
 * @Description:系统-我的消息表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-12-21 14:50:38
 * @version V1.0   
 */
public interface SysUserMessageService {
	/**
	 * 新增数据
	 *
	 * @param sysUserMessage
	 * @return  int 
	 */
	int add(SysUserMessage sysUserMessage);

	
}
