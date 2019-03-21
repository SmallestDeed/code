package com.nork.onekeydesign.service;


import com.nork.onekeydesign.model.AutoRenderTaskState;
import com.nork.onekeydesign.model.DesignPlanRenderScene;
import com.nork.onekeydesign.model.RenderPicVO;

public interface SaveRenderPicService {
	
	/**
	 * 保存720渲染图
	 * @param renderPic
	 * @return
	 */
	public boolean saveRenderPicOf720(RenderPicVO renderPic);
	/**
	 * 保存照片级渲染图
	 * @param renderPic
	 * @return
	 */
	public boolean saveRenderPicOfPhoto(RenderPicVO renderPic);

	/**
	 * 保存720渲染视频 add by yangzhun
	 * @param renderPic
	 * @return
	 */
	public boolean saveRenderPicOfVideo(RenderPicVO renderPic);
	
	
	public void  saveTaskBussinessId(AutoRenderTaskState taskState);
	
	public AutoRenderTaskState getStateByTaskId(Integer taskId);
	
	public DesignPlanRenderScene getDesignPlanRenderScene(Integer businessId);
	
}
