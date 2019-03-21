package com.sandu.service.user.dao;

import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysRoleFunc;
import com.sandu.api.user.model.SysRoleGroupRef;
import com.sandu.api.user.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SysRoleDao {


    Set<SysUserRole> selectUserRolesByUserId(@Param("userId") Long userId);

    Set<SysRoleFunc> selectRoleFuncsByRoleIds(@Param("roleIds") Set<Long> roleIds);

    Set<SysRoleFunc> selectRoleFuncByRoleId(@Param("roleId") Long roleId);

    Set<SysRoleGroupRef> selectRoleGroupRefsByRoleGroupId(@Param("roleGroupId") Long roleGroupId);

    Set<SysRoleGroupRef> selectRoleGroupRefsByRoleGroupIds(@Param("roleGroupIds") Set<Long> roleGroupIds);

    SysRole selectRoleByRoleId(@Param("roleId") Long roleId);

    SysRole selectRoleByCode(@Param("code") String code);

    int save(SysUserRole sr);

    void delUserRole(@Param("userId") Integer userId, @Param("oldRoleIds")List<Integer> oldRoleIds);

    void batchUserRole(List<SysUserRole> userRoles);

    Set<Long> getRolePlatformId(@Param("roleIds")List<Integer> roleIds);

    Set<Long> batchRoleByRoleRroupIds(@Param("roleGroupIds") Set<Long> roleGroupIds);
}
