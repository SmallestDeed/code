package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysMessageRecord;
import com.nork.system.model.search.SysMessageRecordSearch;

/**   
 * @Title: SysMessageRecordService.java 
 * @Package com.nork.system.service
 * @Description:聊天记录-聊天记录表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-10-28 16:30:54
 * @version V1.0   
 */
public interface SysMessageRecordService {
	/**
	 * 新增数据
	 *
	 * @param sysMessageRecord
	 * @return  int 
	 */
	public int add(SysMessageRecord sysMessageRecord);

	/**
	 *    更新数据
	 *
	 * @param sysMessageRecord
	 * @return  int 
	 */
	public int update(SysMessageRecord sysMessageRecord);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysMessageRecord 
	 */
	public SysMessageRecord get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysMessageRecord
	 * @return   List<SysMessageRecord>
	 */
	public List<SysMessageRecord> getList(SysMessageRecord sysMessageRecord);

	/**
	 *    获取数据数量
	 *
	 * @param  sysMessageRecord
	 * @return   int
	 */
	public int getCount(SysMessageRecordSearch sysMessageRecordSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysMessageRecord
	 * @return   List<SysMessageRecord>
	 */
	public List<SysMessageRecord> getPaginatedList(
				SysMessageRecordSearch sysMessageRecordtSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 查询最近联系人列表
	 * @param sysMessageRecord
	 * @return
	 */
	public List<SysMessageRecord> selectRecentContacts(SysMessageRecordSearch sysMessageRecordSearch);

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
