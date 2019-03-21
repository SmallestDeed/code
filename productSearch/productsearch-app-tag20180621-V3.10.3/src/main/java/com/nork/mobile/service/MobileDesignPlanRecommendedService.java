package com.nork.mobile.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.system.model.ResRenderPic;

public interface MobileDesignPlanRecommendedService {

	/**
	 * 
	 * 方案推荐 列表 数据
	 * @param houseType
	 * @param livingName
	 * @param areaValue
	 * @param designRecommendedStyleId
	 * @param isMainList
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	public Object getPlanRecommendedList(/*String all,*/String creator,String brandName,String houseType, String livingName, String areaValue,
			String designRecommendedStyleId, String isMainList, String msgId, LoginUser loginUser,Integer limit,Integer start,Integer templateId,String planName);
	
	
	/**
	 * 从推荐方案页面里获取一个推荐方案的缩略图集
	 * @param model
	 * @return
	 */
	List<ResRenderPic> getRecommendedPictureList(ResRenderPic resRenderPic);
}
