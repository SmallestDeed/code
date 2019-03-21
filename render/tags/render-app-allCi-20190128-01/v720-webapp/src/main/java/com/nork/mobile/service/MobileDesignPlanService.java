package com.nork.mobile.service;


public interface MobileDesignPlanService {
    
    /**
	 * 根据缩略图id获取渲染效果图
	 * @param resRenderPicId
	 * @return
	 */
	Object getPanoPicture(Integer resRenderPicId);
	
	/**
     * 逻辑删除我的设计、我的任务、我的消息
     * @param planId
     * @return
     */
    Object deleteMyDesignPlanAndTask(Integer planId,Integer userId,Integer planHouseType);


}
