package com.nork.design.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.model.*;
import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.search.DesignPlanSearch;
import com.nork.design.model.unity.ItemExt;
import com.nork.design.model.unity.RoomExt;
import com.nork.design.model.unity.Root;
import com.nork.system.model.ResRenderPic;
import com.nork.task.model.SysTask;

/**   
 * @Title: DesignPlanService.java 
 * @Package com.nork.design.service
 * @Description:设计模块-设计方案Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 17:09:51
 * @version V1.0   
 */
public interface DesignPlanAutoRenderResouceService {

	/**
	 *    根据设计方案id，获取designPlan表中的所有数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	public DesignPlan get(Integer id);

	
 
	Integer saveRenderPicOf720(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
									  Integer scene, Integer isTurnOn, Integer renderingType,LoginUser loginUser,
									  Integer taskId, Integer panoLevel,String roamJson,Integer sourcePlanId,
									  Integer templateId, Integer needShear,String cameraLocation);

	/**
	 * 保存照片级渲染图
	 * @param designPlan
	 * @param fileMap
	 * @param viewPoint
	 * @param scene
	 * @param isTurnOn
	 * @param renderingType
	 * @param loginUser
	 * @return
	 */
	public Integer saveRenderPicOfPhoto(DesignPlan designPlan, Map<String, MultipartFile> fileMap, Integer viewPoint,
			Integer scene, Integer isTurnOn, Integer renderingType,String level, LoginUser loginUser,Integer taskId,Integer sourcePlanId, Integer templateId);
	


	/**
	 * 保存720渲染视频   add by yangzhun
	 * @param designPlan
	 * @param fileMap
	 * @param viewPoint
	 * @param scene
	 * @param isTurnOn
	 * @param renderingType
	 * @param loginUser
	 * @param taskId
	 * @return
	 */
	public Integer saveRenderPicOfVideo(DesignPlan designPlan,
			Map<String, MultipartFile> fileMap, Integer renderingType,
			LoginUser loginUser, Integer taskId, Integer sourcePlanId, Integer templateId);



	int add(ResRenderPic resRenderPic);

	
	
}
