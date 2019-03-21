package com.nork.home.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.model.DrawBaseHouse;
import com.nork.home.model.search.DrawBaseHouseQuery;
import com.nork.home.service.DrawBaseHouseService;

/**
 * 我的绘制
 * 
 * @author songjianming@sanduspace.cn
 * @craete 2018 2018年1月27日
 *
 */

@Controller
@RequestMapping("/{style}/web/home/drawBaseHouse")
public class WebDrawBaseHouseController {

	private static final Logger LOGGER = Logger.getLogger(WebDrawBaseHouseController.class);

	@Autowired
	private DrawBaseHouseService drawBaseHouseService;

	final Integer DEFAULT_START = 0;
	final Integer DEFAULT_LIMIT = 20;

	/**
	 * 我的户型
	 * 
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/drawHouseList")
	public ResponseEnvelope<DrawBaseHouse> findDrawHouse(DrawBaseHouseQuery query, HttpServletRequest request) {

		try {
			if (!StringUtils.isNotBlank(query.getMsgId())) {
				return new ResponseEnvelope<>(Boolean.FALSE, "参数msgId不能为空", query.getMsgId());
			}

			LoginUser login = SystemCommonUtil.getCurrentLoginUserInfo(request);
			if (login == null || login.getId() < 1) {
				return new ResponseEnvelope<>(Boolean.FALSE, "请重新登录", query.getMsgId());
			}
			// userId
			if (!StringUtils.isNotBlank(query.getUserId())) {
				query.setUserId(login != null ? String.valueOf(login.getId()) : "");
			}

			Map<String, Object> map = drawBaseHouseService.listDrawHouse(query, login);
			Long total = (Long) map.get("total");
			List<DrawBaseHouse> list = (List<DrawBaseHouse>) map.get("list");
			return new ResponseEnvelope<DrawBaseHouse>(Boolean.TRUE, query.getMsgId(), total, list);
		} catch (Exception e) {
			LOGGER.error("查询我的绘制异常 ==>" + e.getMessage(), e);
			return new ResponseEnvelope<>(Boolean.FALSE, "查询我的绘制异常", query.getMsgId());
		}
	}

	/**
	 * 删除我的户型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete/drawBaseHouse")
	public ResponseEnvelope<?> deleteDrawHouse(String msgId, String houseId, HttpServletRequest request) {
		try {
			if (!StringUtils.isNotBlank(msgId)) {
				return new ResponseEnvelope<>(Boolean.FALSE, "参数msgId不能为空", msgId);
			}
			LoginUser login = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			if (login == null || login.getId() < 1) {
				return new ResponseEnvelope<>(Boolean.FALSE, "请重新登录", msgId);
			}
			if (!StringUtils.isNotBlank(houseId)) {
				return new ResponseEnvelope<>(Boolean.FALSE, "参数houseId不能为空", msgId);
			}
			ResponseEnvelope<?> deleteDrawHouse = drawBaseHouseService.deleteDrawHouse(new LoginUser(), houseId);
			deleteDrawHouse.setMsgId(msgId);
			return deleteDrawHouse;
		} catch (Exception e) {
			LOGGER.error("删除我的户型 ==>" + e.getMessage(), e);
			return new ResponseEnvelope<>(Boolean.FALSE, "删除我的户型", msgId);
		}
	}

	Integer getLimit(Integer limit) {
		return (limit != null) ? limit : DEFAULT_LIMIT;
	}

	Integer getOffset(Integer start, Integer limit) {
		return getLimit(limit) * ((start != null) ? start : DEFAULT_START);
	}
}
