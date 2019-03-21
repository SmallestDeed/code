package com.nork.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nork.system.model.SysConsumingRecords;
import com.nork.system.model.search.SysConsumingRecordsSearch;

/**   
 * @Title: SysConsumingRecordsService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-消费记录Service
 * @createAuthor pandajun 
 * @CreateDate 2016-07-18 16:49:19
 * @version V1.0   
 */
public interface SysConsumingRecordsService {
	/**
	 * 新增数据
	 *
	 * @param sysConsumingRecords
	 * @return  int 
	 */
	public int add(SysConsumingRecords sysConsumingRecords);

	/**
	 *    更新数据
	 *
	 * @param sysConsumingRecords
	 * @return  int 
	 */
	public int update(SysConsumingRecords sysConsumingRecords);

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
	 * @return  SysConsumingRecords 
	 */
	public SysConsumingRecords get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysConsumingRecords
	 * @return   List<SysConsumingRecords>
	 */
	public List<SysConsumingRecords> getList(SysConsumingRecords sysConsumingRecords);

	/**
	 *    获取数据数量
	 *
	 * @param  sysConsumingRecords
	 * @return   int
	 */
	public int getCount(SysConsumingRecordsSearch sysConsumingRecordsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysConsumingRecords
	 * @return   List<SysConsumingRecords>
	 */
	public List<SysConsumingRecords> getPaginatedList(
				SysConsumingRecordsSearch sysConsumingRecordstSearch);

	public void sysSave(SysConsumingRecords model,HttpServletRequest request);
	
}
