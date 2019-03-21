package com.sandu.web.house.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.input.BaseHouseQuery;
import com.sandu.api.house.output.DrawBaseHouseVO;
import com.sandu.api.house.service.BaseHouseService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BusinessException;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月23日
 */

@RestController
@RequestMapping("/v1/house")
public class BaseHouseController extends BaseController {

	@Autowired
	private BaseHouseService baseHouseService;

	/**
	 * 请求示例：http://127.0.0.1:9898/v1/house/list
	 * </p>
	 * 通用PC我的绘制
	 * 
	 * @param query
	 * @param request
	 * @return
	 */
	@GetMapping("/list")
	public ReturnData queryBaseHouse(BaseHouseQuery query, HttpServletRequest request) {
		List<DrawBaseHouseVO> drawBaseHouseVOS;
		ReturnData response = ReturnData.builder();
		try {
			response = setMsgId(request);
			// TODO
			if (query.getUserId() == null) {
				throw new BusinessException(Boolean.FALSE, ResponseEnum.UNAUTHORIZED);
			}

			query.setPageSize(super.getLimit(query.getPageSize()));
			query.setPageNum(super.getPage(query.getPageNum(), query.getPageSize()));
			
			Map<String, Object> map = baseHouseService.queryBaseHouse(query);
			// 转换vo返回
			drawBaseHouseVOS = adapterDrawBaseHouseVOS(((List<DrawBaseHouseBO>) map.get("list")));
			response.list(drawBaseHouseVOS).total((long) map.get("total"));
		} catch (BusinessException e) {
			logger.error("查询我的绘制异常", e);
			response.status(e.isFlag()).code(e.getResponseEnum()).message(e.getResponseEnum().getMessage());
		} catch (Exception e) {
			logger.error("查询我的绘制异常", e);
			response.status(false).code(ResponseEnum.ERROR).message(e.getMessage());
		}

		return response;
	}
	
	private List<DrawBaseHouseVO> adapterDrawBaseHouseVOS(List<DrawBaseHouseBO> list) {
		List<DrawBaseHouseVO> drawBaseHouseVOS = new ArrayList<>();
		for (DrawBaseHouseBO bo : list) {
			DrawBaseHouseVO vo = new DrawBaseHouseVO();
			vo.setHouseCode(bo.getHouseCode());
			vo.setHouseId(bo.getId());
			vo.setHouseName(bo.getHouseName());
			vo.setHousePic(bo.getPicPath());
			vo.setLastUpdateTime(bo.getGmtModified());
			vo.setLivingName(bo.getLivingName());
			vo.setRestoreFile(bo.getFilePath());
			vo.setDetailAddress(bo.getDetailAddress());
			drawBaseHouseVOS.add(vo);
		}
		return drawBaseHouseVOS;
	}
}
