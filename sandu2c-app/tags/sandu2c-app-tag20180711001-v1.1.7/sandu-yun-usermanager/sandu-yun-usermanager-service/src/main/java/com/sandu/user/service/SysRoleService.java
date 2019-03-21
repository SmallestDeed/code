package com.sandu.user.service;

import com.sandu.user.model.SysRole;
import com.sandu.user.model.SysRoleSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleService.java
 * @Package com.sandu.system.service
 * @Description:系统-角色Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 17:30:08
 */
public interface SysRoleService {
    /**
     * 分页获取数据
     *
     * @return List<SysRole>
     */
    List<SysRole> getPaginatedList(SysRoleSearch sysRoletSearch);
    SysRole getRoleByName(String name);

    SysRole getRoleByCodeAndPlatformId(SysRole sysRole);
}
