package com.nork.product.controller2.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.AuthorizedVO;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.search.AuthorizedConfigSearch;
import com.nork.product.service2.AuthorizedConfigService;

@Controller
@RequestMapping("/{style}/web/product/authorizedConfig2")
public class WebAuthorizedConfigController {

	private static Logger logger = Logger.getLogger(WebAuthorizedConfigController.class);
	@Autowired
	private AuthorizedConfigService authorizedConfigService2;

	/**
	 * 授权品牌列表接口
	 */
	@RequestMapping(value = "/authorizedBrandList")
	@ResponseBody
	public Object authorizedBrandList(
			@ModelAttribute("authorizedConfigSearch") AuthorizedConfigSearch authorizedConfigSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO : 判断是否需要抽取到一公共方法里
		String msgId = authorizedConfigSearch.getMsgId();
		if (StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<DesignPlan>(false, SystemCommonConstant.MSGID_NOT_NULL, msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser == null) {
			return new ResponseEnvelope<DesignPlan>(false, SystemCommonConstant.PLEASE_LOGIN, msgId);
		}
		ResponseEnvelope<AuthorizedConfig> result = authorizedConfigService2.authorizedBrandList(authorizedConfigSearch,
				loginUser);
		return result;
	}

	/**
	 * 修改序列号状态(绑定用户/解绑)
	 * 
	 * @param msgId
	 * @param terminalImei
	 *            设备IMEI
	 * @param authorizedCode
	 *            序列号
	 * @param password
	 *            密码
	 * @param status
	 *            状态值(0:取消授权;1:绑定授权)
	 * @return
	 */
	@RequestMapping(value = "/updateAuthorizedConfigStatus")
	@ResponseBody
	public Object updateAuthorizedConfigStatus(AuthorizedVO authorizedVO, HttpServletRequest request) throws Exception {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		Map<String, String> reslutMap = authorizedConfigService2.updateAuthorizedConfigStatus(loginUser, authorizedVO);
		if (reslutMap == null) {
			throw new RuntimeException("系统出现异常");
		}
		boolean flag = Boolean.parseBoolean(reslutMap.get("success"));
		return new ResponseEnvelope<>(flag, reslutMap.get("msg"), authorizedVO.getMsgId());
	}

	/**
	 * 序列号续费接口
	 * 
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/continueCost")
	@ResponseBody
	public Object continueCost(AuthorizedVO authorizedVO, HttpServletRequest request) {

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser == null) {
			return new ResponseEnvelope<DesignPlan>(false, SystemCommonConstant.PLEASE_LOGIN, authorizedVO.getMsgId());
		}
		return authorizedConfigService2.continueCost(authorizedVO);
	}

	/***
	 * 清除序列号到期的用户
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/clearCacheUser")
	@ResponseBody
	public Object clearCacheUser() throws IOException {
		authorizedConfigService2.delAuthorizedPastDueUserCacheJob();
		return new ResponseEnvelope<>(true, SystemCommonConstant.CLEAN_SUCCESS, SystemCommonConstant.KO);
	}

	/**
	 * 购买序列号
	 * 
	 * @param msgId
	 * @param terminalImei
	 * @param authorizedCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/binding")
	@ResponseBody
	public Object binding(String msgId, String terminalImei, String authorizedCode, HttpServletRequest request,
			Integer userId) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		userId = loginUser.getId();
		return authorizedConfigService2.binding(msgId, terminalImei, authorizedCode, loginUser);
	}

	/**
	 * 序列号购买之前的验证
	 * 
	 * @param authorizedCode
	 * @return
	 */
	@RequestMapping("/verify")
	@ResponseBody
	public Object verify(String authorizedCode, String msgId, HttpServletRequest request, Integer userId) {
		// 得到userId
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		userId = loginUser.getId();
		return authorizedConfigService2.verify(authorizedCode, msgId, userId);
	}
}
