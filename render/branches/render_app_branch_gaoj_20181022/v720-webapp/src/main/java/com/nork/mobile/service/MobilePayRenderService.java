package com.nork.mobile.service;

import java.util.List;

import com.nork.design.model.AutoRenderTask;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;

public interface MobilePayRenderService {

	/**
	 * 查询该用户的所有渲染记录
	 * @param model
	 * @return
	 */
	public Object getALLReplaceRecordByUserId(AutoRenderTask model);
	
	/**
	 * 获取用户所有渲染记录
	 * @param autoRenderTask
	 * @return
	 */
	public Object getAllTaskByUserId(AutoRenderTask autoRenderTask);
	
	public List<ProductReplaceTaskDetail> selectListByTaskId(Integer taskId,String msgId);
	
	/**
	 * 获取待删除的产品
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	public List<ProductReplaceTaskDetail> selectDelListByTaskId(Integer taskId, String msgId);
	
	/**
	 * 获取组合替换产品
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	public List<ProductGroupReplaceTaskDetail> selectGroupReplaceListByTaskId(Integer taskId, String msgId);
	/**
	 * 查询结构
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	public List<ProductReplaceTaskDetail> selectTextureReplaceListByTaskId(Integer taskId, String msgId);
}
