package com.sandu.service.user.dao;

import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.bo.UserRoleDetailBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleDao {

    List<UserRoleDetailBO> listUserRole(Integer userId);

    List<SysUserRole> listAvailableUserRole(Integer userId);

    Integer updateUserRole(@Param("isDeleted") int isDeleted,
                           @Param("list")List<SysUserRole> deleteRoles);

    Integer batchNewUserRole(List<SysUserRole> listUserRoleNew);
}
