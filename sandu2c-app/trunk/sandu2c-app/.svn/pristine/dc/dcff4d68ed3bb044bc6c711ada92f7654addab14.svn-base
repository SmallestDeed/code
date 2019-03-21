package com.sandu.user.dao;

import com.sandu.user.model.SysRole;
import com.sandu.user.model.SysRoleSearch;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统-角色Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 17:30:08
 */
@Repository
public interface SysRoleMapper {
    List<SysRole> selectPaginatedList(SysRoleSearch sysRoleSearch);
    SysRole getRoleByName(@Param("name")String name);

    SysRole getRoleByCodeAndPlatformId(SysRole sysRole);
}
