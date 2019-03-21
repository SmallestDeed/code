package com.sandu.api.user.service;

import com.sandu.api.user.input.RoleGroupUpdate;
import com.sandu.api.user.input.RoleUpdate;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.output.RoleGroupVO;
import com.sandu.api.user.output.UserRoleDetailVO;
import com.sandu.api.user.output.UserRoleGroupDetailVO;

import java.util.List;
import java.util.Set;

public interface SysRoleGroupService {

	Set<SysUserRoleGroup> getUserRoleGroupByUserId(Long id);
	
	void batchSaveUserRoleGroupSet(Set<SysUserRoleGroup> urgs);

    List<RoleGroupVO> listRoleGroup(Integer type);

	Integer updateUserRoleGroup(RoleGroupUpdate roleGroup, LoginUser loginUser);

	UserRoleGroupDetailVO getUserRoleGroup(Integer userId);

	List<UserRoleDetailVO> getUserRole(Integer userId);

	Integer updateUserRole(RoleUpdate update, LoginUser loginUser);

    void batchDel(Long id, List<Integer> roleGroupIds);
}
