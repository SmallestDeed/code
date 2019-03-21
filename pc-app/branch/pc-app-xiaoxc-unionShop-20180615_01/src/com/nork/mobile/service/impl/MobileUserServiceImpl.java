package com.nork.mobile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.collections.CustomerListUtils;
import com.nork.mobile.service.MobileUserService;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.model.SysUser;

@Service("mobileUserService")
public class MobileUserServiceImpl implements MobileUserService{

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public SysUser findByMobileAndPwd(String mobile, String pwd) {
		SysUser sysUser = null;
		SysUser conditionSysUser = new SysUser();
		conditionSysUser.setMobile(mobile);
		conditionSysUser.setPassword(pwd);
		List<SysUser> sysUserList = sysUserMapper.selectList(conditionSysUser);
		if (CustomerListUtils.isNotEmpty(sysUserList)) {
			sysUser = sysUserList.get(0);
		}
		return sysUser;
	}
	
}
