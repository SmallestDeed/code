package com.nork.design.service;

import java.util.List;

import com.nork.design.model.TempletPlanOperationHistory;
import com.nork.design.model.search.TempletPlanOperationHistorySearch;



/**   
 * @Title: TempletPlanOperationHistoryService.java 
 * @Package com.nork.demo.service
 * @Description:演示模块-推荐方案使用历史表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-07-06 18:07:55
 * @version V1.0   
 */
public interface TempletPlanOperationHistoryService {
	/**
	 * 新增数据
	 *
	 * @param templetPlanOperationHistory
	 * @return  int 
	 */
	public int add(TempletPlanOperationHistory templetPlanOperationHistory);

	/**
	 *    更新数据
	 *
	 * @param templetPlanOperationHistory
	 * @return  int 
	 */
	public int update(TempletPlanOperationHistory templetPlanOperationHistory);

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
	 * @return  TempletPlanOperationHistory 
	 */
	public TempletPlanOperationHistory get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  templetPlanOperationHistory
	 * @return   List<TempletPlanOperationHistory>
	 */
	public List<TempletPlanOperationHistory> getList(TempletPlanOperationHistory templetPlanOperationHistory);

	/**
	 *    获取数据数量
	 *
	 * @param  templetPlanOperationHistory
	 * @return   int
	 */
	public int getCount(TempletPlanOperationHistorySearch templetPlanOperationHistorySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  templetPlanOperationHistory
	 * @return   List<TempletPlanOperationHistory>
	 */
	public List<TempletPlanOperationHistory> getPaginatedList(
				TempletPlanOperationHistorySearch templetPlanOperationHistorytSearch);

	/**
	 * 其他
	 * 
	 */
	
	void saveOrUpdate(Integer templetId,Integer designTempletId, Integer userId,Integer isAllreplace);

}
