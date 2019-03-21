package com.nork.mobile.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sandu.common.LoginContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.utils.JedisUserUtils;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignTemplet;
import com.nork.home.controller.web.WebSpaceCommonController;
import com.nork.home.model.SpaceCommon;
import com.nork.mobile.model.search.DesignPlanRecommendedModel;
import com.nork.mobile.model.search.HouseSearchModel;
import com.nork.mobile.service.MobileSpaceCommonService;
import com.nork.system.model.SysDictionary;

/**
 * 移动端 搜索空间的所有接口
 * 
 * @author yangzhun
 * 
 */
@Controller
@RequestMapping("/{style}/mobile/spaceCommon")
public class MobileSpaceCommonController {
	private static Logger logger = Logger
			.getLogger(WebSpaceCommonController.class);
	@Autowired
	private MobileSpaceCommonService mobileSpaceCommonService;
	

	/**
	 * 空间布局列表查询接口:根据空间类型、面积查询出空间列表
	 * 
	 * @param houseSearchModel
	 * @return
	 */
	@RequestMapping(value = "/newSpaceSearchWeb")
	@ResponseBody
	public Object newSpaceSearch(
			@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request) {

		String spaceFunctionId = houseSearchModel.getSpaceFunctionId();
		String areaValue = houseSearchModel.getAreaValue();
		String spaceShape = houseSearchModel.getSpaceShape();
		String msgId = houseSearchModel.getMsgId();
		String limit = houseSearchModel.getLimit();
		String start = houseSearchModel.getStart();
		//获取登录用户信息
		@SuppressWarnings("unchecked")

//		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
//		String appKey = map.get("appKey");
//		String cacheKey = "user_H5Token:" + appKey;
//		LoginUser loginUser = (LoginUser) JedisUtils2.getObject(cacheKey);
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		String msg = "";
		if (StringUtils.isBlank(areaValue)) {
			msg = "参数areaValue不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		if (StringUtils.isBlank(spaceFunctionId)) {
			msg = "参数spaceFunctionId不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}

		return mobileSpaceCommonService.newSpaceSearch(spaceFunctionId,
				areaValue, spaceShape, msgId, limit, start, loginUser);
	}

	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 * 

	 * @param houseSearchModel
	 * @return
	 */
	@RequestMapping(value = { "/newSpaceDesign", "/newSpaceDesignWeb" })
	@ResponseBody
	public Object newSpaceDesign(
			@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request) {

		String spaceCommonIdText = houseSearchModel.getSpaceCommonIdText();
		String msgId = houseSearchModel.getMsgId();
		String limit = houseSearchModel.getLimit();
		String start = houseSearchModel.getStart();
		//获取登录用户信息
		@SuppressWarnings("unchecked")

//		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("AuthorizationData");
//		String appKey = map.get("appKey");
//		String cacheKey = "user_H5Token:" + appKey;
//		LoginUser loginUser = (LoginUser) JedisUtils2.getObject(cacheKey);
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		String msg = "";
		if (StringUtils.isBlank(spaceCommonIdText)) {
			msg = "参数spaceCommonIdText不能为空";
			return new ResponseEnvelope<DesignTemplet>(false, msg, msgId);
		}
		return mobileSpaceCommonService.newSpaceDesign(spaceCommonIdText,
				msgId, limit, start, loginUser);
	}

	

	

	/**
	 * 获取空间形状list
	 * @author yangzhun
	 * @return
	 */
	@RequestMapping(value = "/getSpaceShape")
	@ResponseBody
	public Object getSpaceShape() {
		String type = "spaceShape";
		return mobileSpaceCommonService.getSpaceShape(type);
	}
	
	/**
	 * 获取空间list和空间对应的面积范围，其实是在登录时就给前端，让前端本地缓存
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getSpaceAndarea")  
	@ResponseBody
	public Object getSpaceAndarea(@RequestBody DesignPlanRecommendedModel designPlanRecommendedModel) {
		String type = designPlanRecommendedModel.getHouseType();
		
		List<SysDictionary> list = mobileSpaceCommonService.getAreaRange(type);
		return new ResponseEnvelope(list.size(),list);
	}
	/**
	 * 根据空间名称获取风格list
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getDesignStyleList")
	@ResponseBody
	public ResponseEnvelope<?> getDesignStyleList(@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("........................"+houseSearchModel.getHouseName());
		String spaceName = houseSearchModel.getHouseName();
		
		return mobileSpaceCommonService.getDesignStyleList(spaceName);

	}

}
