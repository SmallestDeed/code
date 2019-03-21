package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.onekeydesign.model.DesignPlan;
import com.nork.onekeydesign.model.DesignTemplet;
import com.nork.onekeydesign.model.PosNameInfo;
import com.nork.onekeydesign.model.UnityDesignPlan;

public interface DesignPlanService {

	DesignPlan createDesignPlanForOneKey(DesignPlan designPlan, Object object,
			DesignTemplet designTemplet, String mediaType, LoginUser loginUser,
			int i, Integer opType);

    /**
     * 根据配置文件新建设计方案,返回设计方案id
     * 
     * @author huangsongbo
     * @param designTempletId
     * @param recommendedPlanId
     * @param configEncrypt
     * @param loginUser 
     * @param mediaType 
     * @param posNameInfoList 
     * @return
     */
	public Integer createPlanByConfig(
			Integer designTempletId, Integer recommendedPlanId,
			String configEncrypt, String mediaType,
			LoginUser loginUser, List<PosNameInfo> posNameInfoList,
			Integer opType);
	
	/**
	 * 获取进入设计方案信息
	 * @param designPlanId
	 * @param newFlag
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param nO_CACHE 
	 * @param request
	 * @return Object
	 */
	public Object getDesignPlanInfoForOneKey(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName, Boolean isRelease, LoginUser loginUser, String mediaType, String needOrNoCache);
	
	/**
	 * describe: 获取初始白膜类型
	 * 
	 * creat_user: xiaoxc
	 * creat_date: 2017-09-05
	 **/
	public UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan);

	/**
	 *    更新数据
	 * @param designPlan
	 * @return  int 
	 */
	public int update(DesignPlan designPlan);


	/**
	 * 获取推荐方案资源信息
	 * @author chenqiang
	 * @param planId	推荐方案id|草图方案产品表id
	 * @return
	 * @date 2018/10/31 0031 16:42
	 */
	Object getDesignRecommendedInfo(String planId,String planRecommendedId,String designTempletId,LoginUser loginUser,String msgId);

	/**
	 * 获取产品资源详情信息
	 * @author chenqiang
	 * @param idList 产品id集合
	 * @param msgId
	 * @return
	 * @date 2018/12/14 0014 18:15
	 */
	Object getProductSourceInfo(List<Integer> idList,LoginUser loginUser,String msgId);
}
