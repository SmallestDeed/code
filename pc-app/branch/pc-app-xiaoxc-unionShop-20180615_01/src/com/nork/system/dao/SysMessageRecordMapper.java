package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysMessageRecord;
import com.nork.system.model.search.SysMessageRecordSearch;

/**   
 * @Title: SysMessageRecordMapper.java 
 * @Package com.nork.system.dao
 * @Description:聊天记录-聊天记录表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-10-28 16:30:54
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysMessageRecordMapper {
    int insertSelective(SysMessageRecord record);

    int updateByPrimaryKeySelective(SysMessageRecord record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysMessageRecord selectByPrimaryKey(Integer id);
    
    int selectCount(SysMessageRecordSearch sysMessageRecordSearch);
    
	List<SysMessageRecord> selectPaginatedList(
			SysMessageRecordSearch sysMessageRecordSearch);
			
    List<SysMessageRecord> selectList(SysMessageRecord sysMessageRecord);
    
	/**
	 * 其他
	 * 
	 */


    /**
     * 查询最近联系人列表
     * @param sysMessageRecordSearch
     * @return
     */
    List<SysMessageRecord> selectRecentContacts(SysMessageRecordSearch sysMessageRecordSearch);

    /**
     * 获取聊天记录
     * @param sysMessageRecord
     * @return
     */
    public List<SysMessageRecord> getMessageRecord(SysMessageRecord sysMessageRecord);

    /**
     * 修改未读状态
     * @param sysMessageRecord
     */
    public void updateIsRead(SysMessageRecord sysMessageRecord);
}
