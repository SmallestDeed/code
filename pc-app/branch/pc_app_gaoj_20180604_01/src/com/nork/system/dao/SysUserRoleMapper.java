package com.nork.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserRole;
import com.nork.system.model.search.SysUserRoleSearch;

/**   
 * @Title: SysUserRoleMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-用户角色表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-02 17:26:50
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserRoleMapper {
    int insertSelective(SysUserRole record);

    int updateByPrimaryKeySelective(SysUserRole record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserRole selectByPrimaryKey(Integer id);
    
    int selectCount(SysUserRoleSearch sysUserRoleSearch);
    
	List<SysUserRole> selectPaginatedList(
			SysUserRoleSearch sysUserRoleSearch);
			
    List<SysUserRole> selectList(SysUserRole sysUserRole);
    int selectCountByRoleGroup(SysUserRole sysUserRole);

	/**
	 * 其他
	 * 
	 */
    SysUserRole getByUserIdAndRoleId(@Param("userId")Integer userId,@Param("roleId")Integer roleId);
    
    int deleteByUserIdAndRoleId(@Param("userId")Integer userId,@Param("roleId")Integer roleId);
    
    int selectCountByUserIdAndRoleId(@Param("userId")Integer userId,@Param("roleId")Integer roleId);
}
