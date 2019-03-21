package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysRoleFunc;
import com.nork.system.model.search.SysRoleFuncSearch;

/**   
 * @Title: SysRoleFuncMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-角色菜单管理Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-29 16:46:10
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysRoleFuncMapper {
    int insertSelective(SysRoleFunc record);

    int updateByPrimaryKeySelective(SysRoleFunc record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysRoleFunc selectByPrimaryKey(Integer id);
    
    int selectCount(SysRoleFuncSearch sysRoleFuncSearch);
    
	List<SysRoleFunc> selectPaginatedList(
			SysRoleFuncSearch sysRoleFuncSearch);
			
    List<SysRoleFunc> selectList(SysRoleFunc sysRoleFunc);
    
	/**
	 * 其他
	 * 
	 */
}
