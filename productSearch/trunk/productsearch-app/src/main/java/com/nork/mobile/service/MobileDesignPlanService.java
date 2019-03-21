package com.nork.mobile.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.search.MobileRenderingModel;

public interface MobileDesignPlanService {
	/**
	 * 移动端我的设计获取所有效果图
	 * @param designPlan
	 * @return
	 */
	public ResponseEnvelope<?> getMyDesignPlanListMobile(DesignPlanRenderScene designPlanRenderScene);
	
	/**
	 * 获取所有房间类型
	 * @param baseProductStyle
	 * @return
	 */
	Object getSpace();
	/**
	 * 根据缩略图id获取渲染效果图
	 * @param resRenderPicId
	 * @return
	 */
	Object getPanoPicture(Integer resRenderPicId,LoginUser loginUser);
	
	/**
	 * 获取方案里面的产品清单
	 * @param model
	 * @return
	 */
	public Object getDesignPlanProductList(MobileRenderingModel model);
	/**
	 * 产品替换
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	ResponseEnvelope<ProductReplaceTaskDetail> productReplaceList(Integer taskId,String msgId);
	/**
	 * 产品移除
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	ResponseEnvelope<ProductReplaceTaskDetail> productDelList(Integer taskId,String msgId);
	/**
	 * 组合替换
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	ResponseEnvelope<ProductGroupReplaceTaskDetail> groupProductReplaceList(Integer taskId,String msgId);
	/**
	 * 材质替换
	 * @param taskId
	 * @param msgId
	 * @return
	 */
	ResponseEnvelope<ProductReplaceTaskDetail> textureReplaceList(Integer taskId,String msgId);
}
