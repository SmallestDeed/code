package com.nork.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.controller.web.WebSpaceCommonController;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.BaseHouseResult;
import com.nork.home.model.HouseSpaceResult;
import com.nork.mobile.model.search.HouseSearchModel;
import com.nork.mobile.service.MobileHouseSearchService;
import com.sandu.common.LoginContext;

/**
 * 搜索户型的controller
 * @author yangzhun
 *
 */
@Controller
@RequestMapping("/{style}/mobile/houseSearch")
public class MobileHouseSearchController {
	
	private static Logger logger = Logger
			.getLogger(WebSpaceCommonController.class);
	@Autowired
	private MobileHouseSearchService mobileHouseSearchService;

	/**
	 * 查询所有省份和市区编码
	 * @param houseSearchModel
	 * @return
	 */
	@RequestMapping(value = "/searchProvinceCodeAndCityCode")
	@ResponseBody
	public Object searchProvinceCodeAndCityCode(@RequestBody HouseSearchModel houseSearchModel) {
		String pid = houseSearchModel.getProvinceCode();
		
		return mobileHouseSearchService.searchProvinceCodeAndCityCode(pid);
	}
	
	/**
	 * 根据省市区搜索户型list =============>
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param livingName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/newHouseSearchWeb")
	@ResponseBody
	public Object newHouseSearch(
			@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request) throws Exception {

		String provinceCode = houseSearchModel.getProvinceCode();
		String cityCode = houseSearchModel.getCityCode();
		String livingName = houseSearchModel.getLivingName();
		String msgId = houseSearchModel.getMsgId();
		String limit = houseSearchModel.getLimit();
		String start = houseSearchModel.getStart();

		logger.info("msgId=======================" + msgId);
		logger.info(StringUtils.isBlank(msgId));
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<BaseHouseResult>(false, "参数msgId不能为空",
					msgId);
		}
		//获取登录用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		return mobileHouseSearchService.newHouseSearchWeb(provinceCode,
				cityCode, livingName, msgId, limit, start, loginUser);
	}
	
	/**
	 * 点击小区名字搜索户型
	 * 
	 * @param style
	 * @param livingId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/newHouseListWeb")
	@ResponseBody
	public Object newHouseList(@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request) {

		String style = houseSearchModel.getStyle();
		String livingId = houseSearchModel.getLivingId();
		String msgId = houseSearchModel.getMsgId();
		String limit = houseSearchModel.getLimit();
		String start = houseSearchModel.getStart();

		String msg = "";
		if (StringUtils.isBlank(livingId)) {
			msg = "参数livingId不能为空";
			return new ResponseEnvelope<BaseHouse>(false, msg, msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<BaseHouse>(false, msg, msgId);
		}
		//获取登录用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		return mobileHouseSearchService.newHouseList(style, livingId, msgId,
				limit, start, loginUser);
	}
	/**
	 * 通过户型过滤空间布局图
	 * 
	 * @param houseId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	@RequestMapping(value = "/newHouseSpaceListWeb")
	@ResponseBody
	public Object newHouseSpaceList(
			@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request) {

		String houseId = houseSearchModel.getHouseId();
		String msgId = houseSearchModel.getMsgId();
		String limit = houseSearchModel.getLimit();
		String start = houseSearchModel.getStart();
		String spaceFunctionId = houseSearchModel.getSpaceFunctionId();
		String msg = "";
		if (StringUtils.isBlank(houseId)) {
			msg = "参数houseId不能为空!";
			return new ResponseEnvelope<HouseSpaceResult>(false, msg, msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空!";
			return new ResponseEnvelope<HouseSpaceResult>(false, msg, msgId);
		}

		//获取登录用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		return mobileHouseSearchService.newHouseSpaceList(houseId, msgId,
				limit, start, loginUser,spaceFunctionId);
	}
	/***
	 * 获取一个户型中所有的 空间的类型
	 * @param houseSearchModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSpaceNameInHouse")
	@ResponseBody
	public Object getSpaceNameInHouse(
			@RequestBody HouseSearchModel houseSearchModel,
			HttpServletRequest request) {

		String houseId = houseSearchModel.getHouseId();
		String msgId = houseSearchModel.getMsgId();
		String limit = houseSearchModel.getLimit();
		String start = houseSearchModel.getStart();
		String msg = "";
		if (StringUtils.isBlank(houseId)) {
			msg = "参数houseId不能为空!";
			return new ResponseEnvelope<HouseSpaceResult>(false, msg, msgId);
		}
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空!";
			return new ResponseEnvelope<HouseSpaceResult>(false, msg, msgId);
		}

		//获取登录用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		return mobileHouseSearchService.getSpaceNameInHouse(houseId, msgId,
				limit, start, loginUser);
	}
}
