package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysConsumingRecords;
import com.nork.system.model.search.SysConsumingRecordsSearch;

/**   
 * @Title: SysConsumingRecordsMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-消费记录Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-07-18 16:49:19
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysConsumingRecordsMapper {
    int insertSelective(SysConsumingRecords record);

    int updateByPrimaryKeySelective(SysConsumingRecords record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysConsumingRecords selectByPrimaryKey(Integer id);
    
    int selectCount(SysConsumingRecordsSearch sysConsumingRecordsSearch);
    
	List<SysConsumingRecords> selectPaginatedList(
			SysConsumingRecordsSearch sysConsumingRecordsSearch);
			
    List<SysConsumingRecords> selectList(SysConsumingRecords sysConsumingRecords);
    
	/**
	 * 其他
	 * 
	 */
}
