package com.nork.task.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskResult;
import com.nork.task.model.search.SysTaskSearch;
 
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
	public int add(SysTask sysTask);

	/**
	 *    更新数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	public int update(SysTask sysTask);

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
	 * @return  SysTask 
	 */
	public SysTask get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysTask
	 * @return   List<SysTask>
	 */
	public List<SysTask> getList(SysTask sysTask);

	/**
	 *    获取数据数量
	 *
	 * @param  sysTask
	 * @return   int
	 */
	public int getCount(SysTaskSearch sysTaskSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysTask
	 * @return   List<SysTask>
	 */
	public List<SysTask> getPaginatedList(
			SysTaskSearch sysTasktSearch);

	/**
	 * 其他
	 * 
	 */
	
	public List<SysTask> getRenderPlanList(
			SysTask sysTask);
	
	/**
	 *    获取有效批次号数量
	 * @return   int
	 */
	public Integer getRenderBatchSum();
	
	/**
	 * 
	* @Title: getMaxPriority 
	* @Description: 获得优先级最高的一条任务
	* @param  
	* @return SysTask    返回类型 
	* @throws
	 */
	public SysTask getMaxPriorityTask();

	/**
	 * @param integer 
	 * 
	* @Description:获取某台渲染服务器上当前分配的任务数量
	* @param id
	* @return     
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer getCountOfRender(Integer renderType, Integer id);

	/**
	 * 
	* @Description: 计算渲染任务等待时间
	* @param taskList
	* @return     
	* @return SysTaskResult    返回类型 
	* @throws
	 */
	public List<SysTaskResult> calculateTaskRenderTime(List<SysTask> taskList);
	
	/**
	 * 
	 * @Title: getSysTaskState   
	 * @Description: 查询设计方案是否有正在进行的渲染任务    
	 * @param: @param businessId
	 * @param: @return      
	 * @return: int      
	 */
	int getSysTaskState(int businessId);

	/**
	 * 创建一个未付款状态的渲染任务
	 * @author huangsongbo
	 * @param renderingType 
	 * @param scene 
	 * @param viewPoint 
	 * @param priority 
	 * @param params 
	 * @param planId 
	 * @param isTurnOn 
	 * @param level 
	 * @param orderNo 
	 * @param temporaryPic 
	 * @param renderingType 
	 * @param scene 
	 * @param viewPoint 
	 * @param priority 
	 * @param params 
	 * @param planId 
	 * @param level 
	 * @param request 
	 * @param id
	 * @return 
	 */
	public SysTask createNonPaymentTask(Integer isTurnOn, Integer planId, String params, Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType,Integer renderChanne,String priceInfo, HttpServletRequest request);
	
	/**
	 * 5.6版本 创建一个未付款状态的渲染任务
	 * @param isTurnOn
	 * @param planId
	 * @param params
	 * @param priority
	 * @param viewPoint
	 * @param scene
	 * @param renderingType
	 * @param renderChanne
	 * @param priceInfo
	 * @param request
	 * @return
	 */
	public SysTask createNonPaymentTaskNew(Integer isTurnOn, Integer planId, Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType,String priceInfo,LoginUser loginUser);

	public String updateNonPaymentTask(SysTask sysTask, String orderId,HttpServletRequest request);
	
	/**
	 * 5.6版本：渲染任务状态变更
	 * @param sysTask
	 * @param payState 
	 * @param msgSendIsSuccess 
	 * @param orderId
	 * @param request
	 * @return
	 */
	public String updateNonPaymentTaskNew(SysTask sysTask,LoginUser loginUser,String payType, String payState, Boolean msgSendIsSuccess);
	

	public void deleteByIdList(List<Integer> taskIdList, String remark);

	/**
	 *add by yanghz
	 * 查询免费渲染的次数
	 * @param designPlanId
	 * @return
	 */
    int getAllownFreeRenderTiems(Integer designPlanId);
}
