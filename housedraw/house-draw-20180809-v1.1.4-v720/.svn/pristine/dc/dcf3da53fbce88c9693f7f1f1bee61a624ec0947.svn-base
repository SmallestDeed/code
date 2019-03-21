package com.sandu.web.house.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.house.bo.BaseAreaBO;
import com.sandu.api.house.model.BaseLiving;
import com.sandu.api.house.service.BaseLivingService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月17日
 */

@RestController
@RequestMapping("/v1/region")
@Api(tags="省、市、小区...查询")
public class RegionController extends BaseController {

	@Autowired
	private BaseLivingService baseLivingService;

	/**
	 * 请求示例：http://127.0.0.1:9898/v1/draw/house/livingList
	 * </p>
	 * 加载小区列表
	 *
	 * @return
	 */
	@PostMapping("/get/area")
	@ApiOperation(value = "查询小区列表", response = BaseAreaBO.class)
	public ReturnData getLiving(HttpServletRequest request) {
		ReturnData data = setMsgId(request);
		List<BaseAreaBO> baseAreaBo = baseLivingService.listBaseLiving();
		return data.list(baseAreaBo);
	}

	/**
	 * 上面获取小区和地理区域组成tree状结构自测试感觉很耗性能，增加根据区域查询小区备用
	 * http://localhost:9898/v1/draw/house/base/living
	 * 
	 * @param request
	 * @param areaCode
	 *            地理区域编码
	 * @return
	 */
	@PostMapping("/get/living")
	@ApiOperation(value = "地理区域编码", response = BaseAreaBO.class)
	public ReturnData queryBaseLiving(HttpServletRequest request,String areaCode,String livingName) {
		ReturnData response = setMsgId(request);
		List<BaseLiving> baseLivings = baseLivingService.listLivings(areaCode,livingName);
		return response.list(baseLivings);
	}
	
	/**
	 * 请求示例：http://127.0.0.1:9898/v1/region/get/living/forsave
	 * </p>
	 * 加载小区列表
	 *
	 * @return
	 */
	@PostMapping("/get/living/forsave")
	@ApiOperation(value = "保存户型的小区查询", response = BaseAreaBO.class)
	public ReturnData getLivingForSave(HttpServletRequest request, String areaCode, String livingName) {
		ReturnData response =  setMsgId(request);
		List<BaseLiving> baseLivings = new ArrayList<>();
		if (StringUtils.isNotBlank(areaCode) || StringUtils.isNotBlank(livingName)) {
			baseLivings = baseLivingService.getLivingForSave(areaCode, livingName);
		}
		return response.list(baseLivings);
	}
}
