package com.nork.design.service;

import java.net.UnknownHostException;
import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.ThumbData;

public interface DesignPlanAutoRenderService {
	
	public List<AutoRenderTask> getTaskList(AutoRenderTask autoRenderTask);
		
	//获取渲染任务接口服务
	public ResponseEnvelope<AutoRenderTask> getAutoRenderTaskList(Integer maxSize, String msgId,LoginUser loginUser);
	
	//更新接口服务
	public ResponseEnvelope<AutoRenderTaskState> updateAutoRenderTaskState(AutoRenderTaskState renderTask, String msgId,String token);

	//通过业务规则选择适应才方案的样板房
	public List<AutoRenderTask> mappingForDesignTemplateByDesignPlan(DesignPlanRecommended designPlanRecommended,LoginUser loginUser);
	
	public List<AutoRenderTask> mappingForDesignPlanByDesignTemplate(DesignTemplet DesignTemplet);
	
	//把任务状态插入DB
	public void addTaskStateToDB(AutoRenderTaskState taskState);
	
	//把任务状态插入缓存 list TODO: Confirm this should be list?
	public void addTaskStateToCache(AutoRenderTaskState taskState);
	
	//取消发布时，删除还没有开始渲染的任务
	public void deleteRenderTaskByTaskId(Integer taskId);
		
	//取消发布时时，通过方案ID更新已渲染完成的任务状态。
	public Object updateTaskState(AutoRenderTaskState autoRenderTaskState,String token);
	//取消发布 TODO Confirm what is the key?
	public void updateTaskStateCach();
	
	
	//批量插入渲染任务列表
	public void addRenderTasksToDB(List<AutoRenderTask> tasks,LoginUser loginUser);
	
	//public List<AutoRenderTask> getRenderTasks(Integer maxSize) ;
	public void createTaskListByDesignTemplet(DesignTemplet template, LoginUser loginUser, Integer designPlanId);
	public void createTaskListByDesignPlan(DesignPlanRecommended designPlanRecommended, LoginUser loginUser, Integer designPlanId);
	  /**
     * 分页查询出自己名下的缩略图
       
     * getrenderPicByPage(这里用一句话描述这个方法的作用)        
       
     * @param thumbData
     * @return 
    
     * @return ResponseEnvelope    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public ResponseEnvelope<ThumbData> getrenderPicByPage(ThumbData thumbData);

	public ResponseEnvelope<ThumbData> getrenderPicByPageV2(DesignPlanRenderScene designPlanRenderScene,Integer type);

	public Object updateCoverPic(String picId, String planId, String msgId, String designPlanType);
	//获取替换渲染任务
	public ResponseEnvelope<AutoRenderTask> getTaskList(Integer maxSize, String msgId,LoginUser loginUser);

	public int add(AutoRenderTask autoRenderTask);
	
	public int addTask(AutoRenderTask autoRenderTask);
	
	public List<AutoRenderTask> getReplaceProductTask(Integer maxSize,LoginUser loginUser);
	
	public int addRedisLists(AutoRenderTask autoRenderTask);
	
	public AutoRenderTask getRedisTaskList(Integer maxSize,LoginUser loginUser
			,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;
	
	public int addRedisReplaceList(AutoRenderTask autoRenderTask) ;
	
	public AutoRenderTask getRedisReplaceTaskList(Integer maxSize,LoginUser loginUser
			,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;
	
	public AutoRenderTask getRedisStickTaskList(Integer maxSize,LoginUser loginUser
			,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException ;
	
	public AutoRenderTaskState getStateByTaskId(Integer taskId);
	
	public void getRedisStickList(Integer taskId);

	/**
	 * 任务超时处理的逻辑
	 * @param taskState
	 * @return
	 */
    ResponseEnvelope<AutoRenderTaskState> handleTimeOut(AutoRenderTaskState taskState);

    int updateBaseHousePicFullHousePlanRelSuccessByTaskId(Integer taskId);

	int updateBaseHousePicFullHousePlanRelFalseByTaskId(Integer taskId);
}
