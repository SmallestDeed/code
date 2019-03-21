package com.sandu.system.service;

import com.sandu.system.model.task.SysTask;
import com.sandu.web.task.model.DesignPlan;
import com.sandu.web.task.model.search.SysTaskSearch;

import java.util.List;

/**   
 * @Title: SysTaskService.java 
 * @Package com.nork.task.service
 * @Description:任务-系统任务表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-11-18 10:51:21
 * @version V1.0111 
 */
public interface SysTaskService {
	/**
	 * 新增数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	int add(SysTask sysTask);

	/**
	 *    更新数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	int update(SysTask sysTask);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysTask 
	 */
	SysTask get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysTask
	 * @return   List<SysTask>
	 */
	List<SysTask> getList(SysTask sysTask);

	/**
	 *    获取数据数量
	 *
	 * @param  sysTaskSearch
	 * @return   int
	 */
	int getCount(SysTaskSearch sysTaskSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysTasktSearch
	 * @return   List<SysTask>
	 */
	List<SysTask> getPaginatedList(
            SysTaskSearch sysTasktSearch);

	/**
	 *    根据设计方案id，获取designPlan表中的所有数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	public DesignPlan getDesignPlan(Integer id);
}
