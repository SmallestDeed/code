package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysGroup;
import com.nork.system.model.search.SysGroupSearch;

/**   
 * @Title: SysGroupMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-组织表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-01 15:44:23
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysGroupMapper {
    int insertSelective(SysGroup record);

    int updateByPrimaryKeySelective(SysGroup record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysGroup selectByPrimaryKey(Integer id);
    
    int selectCount(SysGroupSearch sysGroupSearch);
    
	List<SysGroup> selectPaginatedList(
			SysGroupSearch sysGroupSearch);
			
    List<SysGroup> selectList(SysGroup sysGroup);
    
	/**
	 * 其他
	 * 
	 */
}
