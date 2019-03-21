package com.sandu.api.sysusermessage.service;

import com.sandu.api.sysusermessage.model.SysUserMessage;
import org.springframework.stereotype.Component;

@Component
public interface SysUserMessageService {
	
	public int insertSysUserMessage(SysUserMessage message);

	int deleteByTaskId(Integer taskId);

}
