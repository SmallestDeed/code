package com.nork.product.controller2.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.search.GroupProductCollectSearch;
import com.nork.product.service2.GroupProductCollectService2;

/**
 * @Title: WebGroupProductCollectController2.java
 * @Package com.nork.product.controller2.web
 * @Description:产品模块-组合收藏表Controller
 * @CreateAuthor yangzhun
 * @CreateDate 2017-6-19 19:06:55
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/{style}/web/product/groupProductCollect2")
public class WebGroupProductCollectController2 {

	@Autowired
	private GroupProductCollectService2 groupProductCollectService2;

	/**
	 * 编辑组合收藏接口(未完成,备用)
	 * 
	 * @author huangsongbo
	 * @param groupId 组合id
	 * @param productId 产品id
	 * @param type 0:新增
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/updateDetails")
	@ResponseBody
	public ResponseEnvelope updateDetails(Integer groupId, Integer productId,
			Integer type, String msgId, HttpServletRequest request) {

		return groupProductCollectService2.updateDetails(groupId, productId,
				type, msgId);
	}

	/**
	 * 组合收藏列表
	 * 
	 * @param groupProductCollectSearch
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ResponseEnvelope list(
			GroupProductCollectSearch groupProductCollectSearch,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			HttpServletRequest request) {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<UserProductCollect>(false,
					SystemCommonConstant.LOGIN_OVERTIME,
					groupProductCollectSearch.getMsgId());
		}

		String mediaType = com.nork.common.constant.util.SystemCommonUtil.getMediaType(request);

		return groupProductCollectService2.list(groupProductCollectSearch,
				spaceCommonId, loginUser, mediaType);
	}

	/**
	 * 得到组合收藏详情接口
	 * 
	 * @author huangsongbo
	 * @param collectId
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/getDetails")
	@ResponseBody
	public ResponseEnvelope getDetails(Integer collectId, String msgId) {

		return groupProductCollectService2.getDetails(collectId, msgId);
	}

	/**
	 * 收藏组合接口
	 * 
	 * @author huangsongbo
	 * @param groupId
	 * @param msgId
	 * @param type 1:删除该组合的收藏
	 * @return
	 */
	@RequestMapping("/collectGroup")
	@ResponseBody
	public ResponseEnvelope collectGroup(Integer groupId, String type,
			String msgId, HttpServletRequest request) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		if (loginUser==null) {
			throw new RuntimeException("------收藏组合接口->未找到登录用户信息");
		}
		return groupProductCollectService2.collectGroup(groupId, type, msgId,
				loginUser);
	}
}
