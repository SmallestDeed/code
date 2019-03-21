package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysFunc;
import com.nork.system.model.search.SysFuncSearch;

/**   
 * @Title: SysFuncMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-功能菜单Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-28 10:10:35
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysFuncMapper {
    int insertSelective(SysFunc record);

    int updateByPrimaryKeySelective(SysFunc record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysFunc selectByPrimaryKey(Integer id);
    
    int selectCount(SysFuncSearch sysFuncSearch);
    
	List<SysFunc> selectPaginatedList(
			SysFuncSearch sysFuncSearch);
			
    List<SysFunc> selectList(SysFunc sysFunc);
    
    List<SysFunc> getUserMenus(Integer userId);
    /**
	 * 获取登录用户的U3D权限菜单
	 * add  by yangzhun
	 * @param userId
	 * @return
	 */
    List<SysFunc> getUserU3DMenus(Integer userId);
}
