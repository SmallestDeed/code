package com.sandu.user.service.impl;

import com.sandu.user.dao.SysRoleMapper;
import com.sandu.user.model.SysRole;
import com.sandu.user.model.SysRoleSearch;
import com.sandu.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统-角色ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 17:30:08
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 分页获取数据
     *
     * @return List<SysRole>
     */
    @Override
    public List<SysRole> getPaginatedList(SysRoleSearch sysRoleSearch) {
        return sysRoleMapper.selectPaginatedList(sysRoleSearch);
    }

	@Override
	public SysRole getRoleByName(String name) {
		// TODO Auto-generated method stub
		return sysRoleMapper.getRoleByName(name);
	}

	@Override
    public SysRole getRoleByCodeAndPlatformId(SysRole sysRole) {
        return sysRoleMapper.getRoleByCodeAndPlatformId(sysRole);
    }
}
