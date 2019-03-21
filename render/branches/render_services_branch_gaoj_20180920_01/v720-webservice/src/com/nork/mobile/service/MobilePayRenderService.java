package com.nork.mobile.service;

import java.net.UnknownHostException;
import java.util.List;

import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.mobile.model.MobileProductReplace;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.TextureReplaceTaskDetail;
import com.nork.mobile.model.search.MobileRenderRecord;
import com.nork.pay.model.PayOrder;

public interface MobilePayRenderService {

	
	// 扣除用户50积分,并生成消费记录和渲染记录
	public Object deductionOfPoints(MobileRenderRecord model);
	
	/**
	 * 替换记录
	 * @param model
	 * @return
	 * @throws UnknownHostException 
	 */
	public Object replaceRecord(MobileProductReplace model) throws UnknownHostException;
	
	public List<ProductReplaceTaskDetail> selectListByTaskId(Integer taskId,String msgId);
	
	/**
	 * 查询该用户的所有替换记录 add by yangzhun
	 * @param model
	 * @return
	 */
	public Object getALLReplaceRecordByUserId(AutoRenderTask model);
	/**
	 * 生成订单
	 * @param totalFee
	 * @param payType
	 * @param productId
	 * @param productType
	 * @param productDesc
	 * @param tradeType
	 * @param userId
	 * @return
	 */
	public PayOrder getOrder(int totalFee, String payType, Integer productId, String productType, 
			String productDesc, String tradeType,Integer userId,Integer taskId);

	/**
	 * 我的消息list
	 * @param model
	 * @return
	 */
	public Object getMyMessageList(AutoRenderTask model);
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
	
	/**
	 * 根据替换材质参数生成新的材质信息
	 * @param replaceDetail
	 * @return
	 */
	String getNewSplitTexturesChooseInfo(ProductReplaceTaskDetail replaceDetail);
	
	/**
	 * 移动端删除我的任务和设计
	 * @param model
	 * @return
	 */
	public Object deteleMyTaskAndDesign(AutoRenderTaskState model);
	/**
	 * 获取用户所有渲染记录
	 * @param autoRenderTask
	 * @return
	 */
	public Object getAllTaskByUserId(AutoRenderTask autoRenderTask);
}
