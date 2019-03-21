package com.sandu.web.base.controller;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseLivingObject;
import com.sandu.common.tool.EscapeUnescape;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.SpaceCommonStatus;
import com.sandu.home.service.BaseHouseService;

/**
 * @version V1.0
 * @Title: BaseLivingController.java
 * @Package com.sandu.system.controller
 * @Description:系统-小区Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 14:41:11
 */
@Controller
@RequestMapping("/v1/tocmobile/base/baseliving")
public class BaseLivingController {
	@SuppressWarnings("unused")
	private static final Gson gson = new Gson();
	@SuppressWarnings("unused")
	private static final String CLASS_LOG_PREFIX = "[小区搜索服务]:";
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BaseLivingController.class);

	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private BaseAreaService baseAreaService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 小区搜索列表接口
	 *
	 * @param BaseHouseResult
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getlvinglist",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEnvelope houseSearch(@ModelAttribute BaseHouseResult baseHouseResult,
			@ModelAttribute PageModel pageModel, HttpServletRequest request) {
		baseHouseResult.setLivingName(EscapeUnescape.unescape(baseHouseResult.getLivingName()));
		baseHouseResult.setStart(pageModel.getStart());
		baseHouseResult.setLimit(pageModel.getLimit());
		List<BaseHouseResult> list = new ArrayList<BaseHouseResult>();
		int total = 0;
		String areaCode_p = baseHouseResult.getProvinceCode();
		String areaCode_c = baseHouseResult.getCityCode();
		StringBuffer areaLongCode = new StringBuffer();
		if (StringUtils.isNotBlank(areaCode_p)) {
			areaLongCode.append("." + areaCode_p);
		}
		if (StringUtils.isNotBlank(areaCode_c)) {
			areaLongCode.append("." + areaCode_c);
		}
		if (StringUtils.isNotBlank(areaLongCode.toString())) {
			areaLongCode.append(".");
		}
		baseHouseResult.setAreaLongCode(areaLongCode.toString());
		try {
			this.internalPermissions(baseHouseResult);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
			total = baseHouseService.getHouseCount(baseHouseResult);
			if(total>0) {
				list = baseHouseService.getHouseList(baseHouseResult);
			}
						
			if (CustomerListUtils.isNotEmpty(list)) {
				for (BaseHouseResult houseResult : list) {
					StringBuffer areaName = new StringBuffer();
					String areaCode = houseResult.getAreaLongCode();
					if (!StringUtils.isEmpty(areaCode)) {
						if (areaCode.contains(".")) {
							String area[] = areaCode.split("\\.");
							BaseArea baseArea = new BaseArea();
							for (int i = 0; i < area.length; i++) {
								baseArea.setAreaCode(area[i]);
								List<BaseArea> areaList = null;
								areaList = baseAreaService.getList(baseArea);
								if (CustomerListUtils.isNotEmpty(areaList)) {
									areaName.append(areaList.get(0).getAreaName());
								}
								// 取区名
								while (i == area.length - 1) {
									if (CustomerListUtils.isNotEmpty(areaList)) {
										houseResult.setDistrictName(areaList.get(0).getAreaName());
									}
									break;
								}
							}
						} else {
							BaseArea baseArea = new BaseArea();
							baseArea.setAreaCode(areaCode);
							List<BaseArea> areaList = null;
							areaList = baseAreaService.getList(baseArea);
							if (CustomerListUtils.isNotEmpty(areaList)) {
								areaName.append(areaList.get(0).getAreaName());
							}
						}
						houseResult.setAreaName(areaName.toString());
					} else {
						houseResult.setAreaName("未知");
					}
				}
			}
			
			// 户型搜索结果应按照开头字母顺序排序
			if (null != list && !list.isEmpty()) {
				List<String> livingNames = new ArrayList<>();
				for (BaseHouseResult baseHouseResult2 : list) {
					livingNames.add(null == baseHouseResult2.getLivingName() ? "该小区没有名字" : baseHouseResult2.getLivingName());
				}
				Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
				Collections.sort(livingNames, com);
				List<BaseHouseResult> list2 = new ArrayList<>();
				for (String str : livingNames) {
					for (BaseHouseResult baseHouseResult2 : list) {
						if (baseHouseResult2.getLivingName().equals(str)) {
							list2.add(baseHouseResult2);
						}
					}
				}
				list = list2;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope(false, "数据异常!");
		}
		return new ResponseEnvelope(true, "success", BaseLivingObject.convertTobaseLivingVo(list), total);
	}

	/**
	 * 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
	 *
	 * @param loginUser
	 * @param baseHouseSearch
	 */
	public void internalPermissions(BaseHouseResult baseHouseResult) {
		Integer spaceCommonStatusList[] = null;// 存放空间状态的list 用于in 查询
		Integer designTempletPutawayStateList[] = null; // 存放样板房状态的list 用于in 查询
		spaceCommonStatusList = new Integer[] { SpaceCommonStatus.IS_RELEASE };// 只查询状态为发布中的空间
		designTempletPutawayStateList = new Integer[] { DesignTempletPutawayState.IS_RELEASE };// 只查询状态为发布中的样板房
		baseHouseResult.setSpaceCommonStatusList(spaceCommonStatusList);
		baseHouseResult.setDesignTempletPutawayStateList(designTempletPutawayStateList);
	}

}
