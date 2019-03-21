package com.nork.design.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.home.model.SpaceCommon;

@Repository
@Transactional
public interface DesignPlanAutoRenderMapper {
	
	public int addTaskStateToDB(AutoRenderTaskState taskState); 
	
	public int updateAutoRenderTaskState(AutoRenderTaskState taskState);
	
	public int deleteRenderTaskByTaskId(Integer taskId); 

	public int updateTaskStateByPlanId(AutoRenderTaskState autoRenderTaskState); 
	
	List<AutoRenderTask> getAutoRenderTaskList(@Param("autoRenderTask")AutoRenderTask autoRenderTask); 
	
	List<AutoRenderTaskState> getTaskStateList(@Param("AutoRenderTaskState")AutoRenderTaskState autoRenderTaskState); 
	
	List<AutoRenderTask> getRenderTaskListByPlanId(@Param("planId")Integer planId); 
	
	List<AutoRenderTask> getRenderTaskListBytemplateId(@Param("templateId")Integer templateId); 
	
	public int  insertSelective(AutoRenderTask autoRenderTask); 
	
	int batchInsertDataList (@Param("list")List<AutoRenderTask> list);
	
	//通过方案Id从任务状态表里查
	List<AutoRenderTaskState> getTaskStateListByDesignPlanId(@Param("planId")Integer planId);
	

	//通过templateId从任务状态表里查
	List<AutoRenderTaskState> getTaskStateListByTemplateId(@Param("templateId")Integer templateId);

	public AutoRenderTaskState getTaskStateByPlanIdAndTemplateId(@Param("planId")Integer planId, @Param("templateId")Integer templateId);

	/**
	 * 根据样板房id 查询 所有自动渲染成功的  任务状态
	 * @param templateId
	 * @return
	 * add by yangzhun
	 */
	List<AutoRenderTaskState> getSuccessTaskStateList(@Param("templateId")Integer templateId);
	
	int createTask(AutoRenderTask autoRenderTask);
	
	List<AutoRenderTask> getReplaceTaskList(@Param("autoRenderTask")AutoRenderTask autoRenderTask); 
	

	/**
	 * 查询该用户的所有替换记录  add by yangzhun
	 * @param autoRenderTask
	 * @return
	 */
	List<AutoRenderTask> getALLReplaceRecordByUserId(AutoRenderTask autoRenderTask);
	/**
	 * 查询该用户的所有替换记录和方案名称的list    已经开始渲染的任务 add by yangzhun
	 * @param model
	 * @return
	 */
	List<AutoRenderTask> getAllReplaceRecordByUserId2(AutoRenderTask model);

	public int updateTaskStateByTaskId(AutoRenderTaskState autoRenderTaskState);

	/**
	 * 查询任务状态
	 * @param autoRenderTaskState add by yangzhun
	 * @return
	 */
	AutoRenderTaskState getTaskType(AutoRenderTaskState autoRenderTaskState);
	
	public AutoRenderTaskState getStateByTaskId(@Param("taskId")Integer taskId);
	
	public AutoRenderTask getRenderTaskById(@Param("taskId")Integer taskId);

	public List<AutoRenderTask> getTaskList(AutoRenderTask autoRenderTask);
	
	/**
	 * 查询该用户的渲染任务的数量
	 * @param autoRenderTask
	 * @return
	 */
	int getStateCountByUserId(AutoRenderTask autoRenderTask);
	
	/**
	 * 根据business_id 逻辑删除任务状态
	 * @param planId
	 * @return
	 */
	void updateAutoRenderTaskStateByBusinessId(Integer businessId);
	/**
	 * 根据business_id查询任务状态
	 * @param businessId
	 * @return
	 */
	AutoRenderTaskState selectTaskStateByBusinessId(Integer businessId);
	/**
	 * 将任务致为无效，只是在前端不显示
	 * @param id
	 */
	void updateAutoRenderTaskById(Integer id);
	/**
	 * 根据id查询任务状态
	 * @param businessId
	 * @return
	 */
	AutoRenderTaskState selectAuroRenderTaskStateById(Integer id);
	
}
