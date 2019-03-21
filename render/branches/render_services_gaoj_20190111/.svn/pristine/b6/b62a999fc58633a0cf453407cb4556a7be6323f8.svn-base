package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysVersion;
import com.nork.system.model.search.SysVersionSearch;

/**   
 * @Title: SysVersionMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-版本管理Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-05-05 14:18:01
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysVersionMapper {
    int insertSelective(SysVersion record);

    int updateByPrimaryKeySelective(SysVersion record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysVersion selectByPrimaryKey(Integer id);
    
    int selectCount(SysVersionSearch sysVersionSearch);
    
	List<SysVersion> selectPaginatedList(
			SysVersionSearch sysVersionSearch);
			
    List<SysVersion> selectList(SysVersion sysVersion);

	List<String> findAllVersionName();
    
}
