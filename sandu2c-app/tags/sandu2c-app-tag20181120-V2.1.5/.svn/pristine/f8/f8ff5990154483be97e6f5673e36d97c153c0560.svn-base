package com.sandu.user.dao;

import com.sandu.user.model.SysUserRole;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserRoleMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统模块-用户角色表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-02 17:26:50
 */
@Repository
public interface SysUserRoleMapper {

    List<SysUserRole> selectList(SysUserRole sysUserRole);
    
    int insertSelective(SysUserRole sysUserRole);
    
    int updateByRegisterId(@Param("registerId")Integer registerId,@Param("roleId")Integer roleId);

	int insertUserRoleGroup(SysUserRole sysUserRole);

	int sysRoleGroup(SysUserRole sysUserRole);

    SysUserRole getByRegisterId(@Param("registerId")Integer registerId,@Param("roleId")Integer roleId);
}
