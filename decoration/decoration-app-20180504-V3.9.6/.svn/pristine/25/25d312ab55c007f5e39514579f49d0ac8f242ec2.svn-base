package com.nork.product.controller2.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service2.GroupProductService2;

/**
 * @Title: WebGroupProductController2.java
 * @Package com.nork.product.controller2.web
 * @Description:组合产品表Controller
 * @CreateAuthor yangzhun
 * @CreateDate 2017-6-21 14:11:56
 */
@Controller
@RequestMapping("/{style}/web/product/groupProduct2")
public class WebGroupProductController2 {

	@Autowired
	private GroupProductService2 groupProductService2;

	/**
	 * 产品组合详情接口
	 * 
	 * @param groupId 组合产品Id
	 */
	@RequestMapping(value = "/getGroupProductDetails")
	@ResponseBody
	public Object getGroupProductDetails(String msgId, Integer groupId,
			HttpServletRequest request, HttpServletResponse response) {

		// 数据验证
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<GroupProduct>(false,
					SystemCommonConstant.MSGID_NOT_NULL, msgId);
		}
		if (groupId == null) {
			return new ResponseEnvelope<GroupProduct>(false,
					SystemCommonConstant.GROUPID_NOT_NULL, msgId);
		}

		// 当前登录人是否已经收藏该产品组合
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		String mediaType = SystemCommonUtil.getMediaType(request);

		return groupProductService2.getGroupProductDetails(msgId, groupId,
				loginUser, mediaType);
	}

	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/queryGroupByBrand")
	@ResponseBody
	public Object queryGroupByBrand(
			@ModelAttribute("@baseBrandSearch") BaseBrandSearch baseBrandSearch,
			String msgId, HttpServletRequest request)
			throws UnsupportedEncodingException {

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<GroupProduct>(false, SystemCommonConstant.USER_NOT_LOGINNING, msgId);
		}

		return groupProductService2.queryGroupByBrand(baseBrandSearch, msgId,
				loginUser);

	}

	/**
	 * 一键替换组合
	 * 
	 * @param templetId 模板ID
	 * @param designTempletId 样板房ID
	 * @return object
	 */
	@RequestMapping("/getGroupProductData")
	@ResponseBody
	public Object getGroupProductData(Integer templetId,
			Integer designTempletId, String msgId, HttpServletRequest request) {

		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.MSGID_NOT_NULL, msgId);
		}
		if (templetId == null) {
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.TEMPLETID_NOT_NULL, msgId);
		}
		if (designTempletId == null) {
			return new ResponseEnvelope<>(false,
					SystemCommonConstant.DESIGNTEMPLETID_NOT_NULL, msgId);
		}

		return groupProductService2.getGroupProductData(templetId,
				designTempletId, msgId);
	}

}
