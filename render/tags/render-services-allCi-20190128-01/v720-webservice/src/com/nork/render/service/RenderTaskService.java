package com.nork.render.service;

import java.util.List;

import com.nork.base.definedvalue.ConstValue.DEFAULT_IS_DELETED;
import com.nork.render.model.RenderTask;
import com.nork.render.model.search.RenderTaskSearch;
import com.nork.render.model.vo.RenderCheckVo;
import com.nork.render.model.vo.RenderPriceInfoVo;
import com.nork.task.model.SysTask;

/**   
 * @Title: RenderTaskService.java 
 * @Package com.nork.render.service
 * @Description:渲染-渲染任务Service
 * @createAuthor pandajun 
 * @CreateDate 2017-01-17 20:31:06
 * @version V1.0   
 */
public interface RenderTaskService{
	/**
	 * 新增数据
	 *
	 * @param renderTask
	 * @return  int 
	 */
	public int add(RenderTask renderTask);

	/**
	 *    更新数据
	 *
	 * @param renderTask
	 * @return  int 
	 */
	public int update(RenderTask renderTask);

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
	 * @return  RenderTask 
	 */
	public RenderTask get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  renderTask
	 * @return   List<RenderTask>
	 */
	public List<RenderTask> getList(RenderTask renderTask);

	/**
	 *    获取数据数量
	 *
	 * @param  renderTask
	 * @return   int
	 */
	public int getCount(RenderTaskSearch renderTaskSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  renderTask
	 * @return   List<RenderTask>
	 */
	public List<RenderTask> getPaginatedList(
				RenderTaskSearch renderTasktSearch);

	/**
	 * 
	* @Description: 获取该主机正在渲染的任务数量
	* @param renderTaskSearch
	* @return int    任务数量
	* @throws
	 */
	public int getUsingTaskCount(RenderTaskSearch renderTaskSearch);
	/**
	 *  接收来自渲染主机任务开始的通知
	 *
	 * @param
	 * @return   String 
	 */
	//public String receiveTaskHostRenderStart(Integer taskId,Integer state,String data);
	
	/**
	 *  接收来自渲染主机渲染结束通知
	 *
	 * @param
	 * @return   String 
	 */
	//public String receiveTaskHostRenderEnd(Integer taskId,Integer state,String data);
	
	/**
	 *    根据sys_task表的id获取数据详情
	 *
	 * @param taskId
	 * @return  RenderTask 
	 */
	public RenderTask getByTaskId(Integer taskId);

	public void renderRefund(SysTask sysTask,String renderErroMsg);

	/**
	 * 判断当前时间是否在免费渲染时间段内
	 * add by yanghz
	 * @return
	 */
	public Boolean renderFreeTime();
	
	
	
	/**
	 * 根据渲染 id，是否已删除，来获取列表
	 * @author louxinhua
	 * @since 2017-05-03
	 * @param taskId 渲染任务 id
	 * @param isDeleted 是否已删除
	 * @return
	 */
	public List<RenderTask> getByTaskId(Integer taskId, DEFAULT_IS_DELETED isDeleted);
	
	/**
	 * 判断渲染免费时间段和返回免费时间段
	 * @author yanghz
	 * @since 2017-05-26
	 * @return
	 */
	public RenderCheckVo renderFreeTimeInfo();
	/**
	 * 根据渲染类型查询价格列表和免费信息
	 * @author yanghz
	 * @since 2017-05-26
	 * @return
	 */
	public List<RenderPriceInfoVo> findPriceInfo(Integer renderingType);
	
	
	
}
