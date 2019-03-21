package com.nork.design.service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import org.springframework.beans.factory.annotation.Autowired;


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

	/**
	 * 渲染机获得高清渲染任务
	 * @author: chenm
	 * @date: 2019/1/19 15:13
	 * @param redisKey 任务存在redis里的key
	 * @param renderMachineIp 渲染机IP
	 * @param renderLevel 渲染级别（1-7）
	 * @param renderProgramVersion 渲染程序的版本
	 * @return: com.nork.design.model.AutoRenderTask
	 */
	AutoRenderTask getHDRenderTaskByAuto(String redisKey,String renderMachineIp,Integer renderLevel,String renderProgramVersion);

	/**
	 * 获取渲染任务信息
	 * @author: chenm
	 * @date: 2019/1/19 16:57
	 * @param taskId
	 * @return: com.nork.design.model.AutoRenderTask
	 */
	AutoRenderTask getRenderTaskById(Integer taskId);

	/**
	 * 删除渲染任务及其下的信息
	 * @author: chenm
	 * @date: 2019/1/19 17:05
	 * @param taskId
	 * @param loginUser
	 * @return: boolean
	 */
	boolean deleteTaskAndStateByTaskId(Integer taskId,LoginUser loginUser);
}
