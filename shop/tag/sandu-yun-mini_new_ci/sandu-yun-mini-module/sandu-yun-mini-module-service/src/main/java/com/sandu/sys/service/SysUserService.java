package com.sandu.sys.service;

import com.sandu.sys.model.vo.SysUserVo;

public interface SysUserService {
	SysUserVo get(String openId);
	boolean setToken(long userId,String token);
	boolean validateToken(String token);

    String selectUUIDByPrimaryKey(int userId);
	SysUserVo getById(Long userId);

}
