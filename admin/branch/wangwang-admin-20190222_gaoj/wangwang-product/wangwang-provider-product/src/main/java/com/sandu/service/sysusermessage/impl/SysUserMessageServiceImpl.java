package com.sandu.service.sysusermessage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.api.sysusermessage.service.SysUserMessageService;
import com.sandu.service.sysusermessage.dao.SysUserMessageMapper;

@Service("sysUserMessageService")
public class SysUserMessageServiceImpl implements SysUserMessageService {

	@Resource
	private SysUserMessageMapper sysUserMessageMapper;
	
	@Override
	public int insertSysUserMessage(SysUserMessage message) {
		return sysUserMessageMapper.insertSelective(message);
	}

}
