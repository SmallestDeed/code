package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysMessageRecordMapper;
import com.nork.system.model.SysMessageRecord;
import com.nork.system.model.search.SysMessageRecordSearch;
import com.nork.system.service.SysMessageRecordService;

/**   
 * @Title: SysMessageRecordServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:聊天记录-聊天记录表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-10-28 16:30:54
 * @version V1.0   
 */
@Service("sysMessageRecordService")
@Transactional
public class SysMessageRecordServiceImpl implements SysMessageRecordService {

	private SysMessageRecordMapper sysMessageRecordMapper;

	@Autowired
	public void setSysMessageRecordMapper(
			SysMessageRecordMapper sysMessageRecordMapper) {
		this.sysMessageRecordMapper = sysMessageRecordMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysMessageRecord
	 * @return  int 
	 */
	@Override
	public int add(SysMessageRecord sysMessageRecord) {
		sysMessageRecordMapper.insertSelective(sysMessageRecord);
		return sysMessageRecord.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysMessageRecord
	 * @return  int 
	 */
	@Override
	public int update(SysMessageRecord sysMessageRecord) {
		return sysMessageRecordMapper
				.updateByPrimaryKeySelective(sysMessageRecord);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysMessageRecordMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysMessageRecord 
	 */
	@Override
	public SysMessageRecord get(Integer id) {
		return sysMessageRecordMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysMessageRecord
	 * @return   List<SysMessageRecord>
	 */
	@Override
	public List<SysMessageRecord> getList(SysMessageRecord sysMessageRecord) {
	    return sysMessageRecordMapper.selectList(sysMessageRecord);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysMessageRecord
	 * @return   int
	 */
	@Override
	public int getCount(SysMessageRecordSearch sysMessageRecordSearch){
		return  sysMessageRecordMapper.selectCount(sysMessageRecordSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysMessageRecord
	 * @return   List<SysMessageRecord>
	 */
	@Override
	public List<SysMessageRecord> getPaginatedList(
			SysMessageRecordSearch sysMessageRecordSearch) {
		return sysMessageRecordMapper.selectPaginatedList(sysMessageRecordSearch);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 查询最近联系人列表
	 * @param sysMessageRecordSearch
	 * @return
	 */
	@Override
	public List<SysMessageRecord> selectRecentContacts(SysMessageRecordSearch sysMessageRecordSearch){
		return sysMessageRecordMapper.selectRecentContacts(sysMessageRecordSearch);
	}

	/**
	 * 获取聊天记录
	 * @param sysMessageRecord
	 * @return
	 */
	@Override
	public List<SysMessageRecord> getMessageRecord(SysMessageRecord sysMessageRecord){
		return sysMessageRecordMapper.getMessageRecord(sysMessageRecord);
	}

	/**
	 * 修改未读状态
	 * @param sysMessageRecord
	 */
	@Override
	public void updateIsRead(SysMessageRecord sysMessageRecord){
		sysMessageRecordMapper.updateIsRead(sysMessageRecord);
	}
}
