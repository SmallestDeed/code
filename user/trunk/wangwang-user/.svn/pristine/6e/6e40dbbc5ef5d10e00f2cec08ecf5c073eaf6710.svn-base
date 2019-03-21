package com.sandu.api.user.service;

import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysRoleFunc;
import com.sandu.api.user.model.SysRoleGroupRef;
import com.sandu.api.user.model.SysUserRole;

import java.util.List;
import java.util.Set;

public interface SysRoleService {

    Set<SysRoleGroupRef> getRoleGroupRefsByRoleGroupIds(Set<Long> groupIds);

    Set<SysUserRole> getUserRolesByUserId(Long userId);

    Set<SysRoleFunc> getRoleFuncByRoleIds(Set<Long> userRoleIds);

    List<SysRole> getRolesByRoleIds(Set<Long> roleIds);

    SysRole getRoleByCode(String code);

    int saveUserRole(SysUserRole sr);

    void delUserRole(Integer userId, List<Integer> oldRoleIds);

    void batchUserRole(List<SysUserRole> userRoles);

    Set<Long> getRolePlatformId(List<Integer> roleIds);

    Set<Long> batchRoleByRoleRroupIds(Set<Long> roleGroupIds);
}
