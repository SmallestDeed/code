package com.sandu.service.user.dao;

import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.output.RoleGroupVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SysRoleGroupDao {

    Set<SysUserRoleGroup> selectRoleGroupsByUserId(@Param("userId") Long userId);

    Integer batchInsertUserRoleGroupSet(@Param("urgs") Set<SysUserRoleGroup> urgs);

    List<RoleGroupVO> listRoleGroup(@Param("type") Integer type);

    List<SysUserRoleGroup> listAvailableRoleGroup(Integer userId);

    Integer updateUserRoleGroup(@Param("isDeleted") Integer isDeleted,
                                @Param("list") List<SysUserRoleGroup> deleteRoleGroups);

    void batchDel(@Param("userId")Long id, @Param("roleGroupIds")List<Integer> roleGroupIds);
}
