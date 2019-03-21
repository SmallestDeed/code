package com.nork.design.service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;


public interface DesignPlanAutoRenderService {
	
	Map<Integer, Integer> getAllRenderTypesStr(AutoRenderTask autoRenderTask);
	
	boolean delRederTaskByDesignPlanId(AutoRenderTask autoRenderTask);
	
	/**
     * 查询用户我的方案中已使用户型
     * @param userId    用户ID
     * @return  Map<HouseId, HouseName>
     */
    Map<Integer, String> queryUsedHouseInMyRenderPlan(Integer userId);

    /**
     * 根据用户id查询任务
     * @param autoRenderTask
     * @return
     */
	List<AutoRenderTask> getALLTaskByUserId(AutoRenderTask autoRenderTask);
	/**
	 * 根据用户id查询任务状态
	 * @param autoRenderTask
	 * @return
	 */
	List<AutoRenderTask> getAllTaskStateByUserId(AutoRenderTask autoRenderTask);
	
	
	
	public AutoRenderTaskState getStateByTaskId(Integer taskId);
	
	//更新接口服务
	public ResponseEnvelope<AutoRenderTaskState> updateAutoRenderTaskState(AutoRenderTaskState renderTask, String msgId,String token,String platformCode);
	
	public AutoRenderTask getRedisStickTaskList(Integer maxSize,LoginUser loginUser
			,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException ;
	
	public AutoRenderTask getRedisReplaceTaskList(Integer maxSize,LoginUser loginUser
			,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;
	
	public AutoRenderTask getRedisTaskList(Integer maxSize,LoginUser loginUser
			,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;
	
	public void getRedisStickList(Integer taskId);
}
