package com.nork.mobile.service;

import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.system.model.search.ResRenderPicSearch;

public interface MobileResRenderPicService {
	
	String getPanoPicturePath(Integer id);
	
	/**
	 * 通过sourcePlanId和templateId获取720全景图的sys_code
	 * 
	 * @param sourcePlanId
	 * @param sourceTemplateId
	 * @param renderingType
	 * @return
	 */
	public Map<String,String> getResRenderPic(Integer sourcePlanId,
			Integer templateId, Integer renderingType,Integer taskId);
	
	
	/**
	 * 从推荐页面   获取720全景图
	 * @param resRenderPicSearch
	 * @return
	 */
	public String getRecommendedPicturePath(ResRenderPicSearch resRenderPicSearch);

	/**
	 * 从我的设计里获取一个设计方案的缩略图集
	 * @param id
	 * @param remark
	 * @return
	 */
	public Object getPictureList(Integer id, String remark);
	
	/**
	 * 获取一个可以生成有按钮的720全景图的二维码路径
	 * @param picId
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	public Object getQRCodePicUrl(Integer picId,String msgId,LoginUser loginUser,String remark);
	
}
