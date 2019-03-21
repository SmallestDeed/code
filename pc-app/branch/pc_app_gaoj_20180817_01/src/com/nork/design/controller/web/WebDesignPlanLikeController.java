package com.nork.design.controller.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.cache.DesignPlanLikeCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanLike;
import com.nork.design.model.search.DesignPlanLikeSearch;
import com.nork.design.model.small.DesignPlanLikeSmall;
import com.nork.design.service.DesignPlanLikeService;
import com.nork.design.service.DesignPlanService;

@Controller
@RequestMapping("/{style}/web/design/designPlanLike")
public class WebDesignPlanLikeController {
	private static Logger logger = Logger.getLogger(WebDesignPlanLikeController.class);
	private final JsonDataServiceImpl<DesignPlanLike> JsonUtil = new JsonDataServiceImpl<DesignPlanLike>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/design/designPlanLike";
	private final String BASEMAIN = "/" + STYLE + "/design/base/designPlanLike";
	private final String JSPMAIN = "/" + JSPSTYLE + "/design/designPlanLike";

	@Autowired
	private DesignPlanLikeService designPlanLikeService;
	@Autowired
	private DesignPlanService designPlanService;

	/**
	 * 设计方案点赞---接口
	 */
	@RequestMapping(value = "/getlike")
	@ResponseBody
	public Object getlike(@PathVariable String style, @ModelAttribute("designPlanLike") DesignPlanLike designPlanLike,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DesignPlanLikeSearch designPlanLikeSearch = new DesignPlanLikeSearch();
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanLike = (DesignPlanLike) JsonUtil.getJsonToBean(jsonStr, DesignPlanLike.class);
			if (designPlanLike == null) {
				return new ResponseEnvelope<DesignPlanLike>(false, "传参异常!", "none");
			}
		}
		// 获取登录用户
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<DesignPlanLike>(false, "登录超时，请重新登录!", designPlanLike.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			designPlanLike.setUserId(loginUser.getId());
			designPlanLikeSearch.setUserId(loginUser.getId());
		}

		String msg = "";
		if (StringUtils.isBlank(designPlanLike.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
		}
		if (designPlanLike.getDesignId() == null) {
			msg = "参数designId不能为空";
			return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
		} else if (designPlanLike.getDesignId() != null) {
			DesignPlan dpc = new DesignPlan();
			if(Utils.enableRedisCache()){
				dpc =DesignPlanLikeCacher.getDesignPlan(designPlanLike.getDesignId());
			}else{
				dpc = designPlanService.get(designPlanLike.getDesignId());
			}
			
			if (dpc == null) {
				msg = "该方案不存在";
				return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
			}
		}
		if (designPlanLike.getDesignId() != null) {
			designPlanLikeSearch.setDesignId(designPlanLike.getDesignId());
			List<DesignPlanLike> designPlanLikelist=null;
			if(Utils.enableRedisCache()){
				designPlanLikelist = DesignPlanLikeCacher.getPaginatedList(designPlanLikeSearch);
			}else{
				designPlanLikelist = designPlanLikeService.getPaginatedList(designPlanLikeSearch);
			}
			
			if (designPlanLikelist.size() > 0 && designPlanLikelist != null) {
				msg = "该方案已点赞";
				return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
			}
		}
		try {
			sysSave(designPlanLike, request);
			if (designPlanLike.getId() == null) {
				int id = designPlanLikeService.add(designPlanLike);
				DesignPlanLikeCacher.removeDesignPlan(designPlanLike.getDesignId());/**添加后删除缓存*/
				logger.info("add:id=" + id);
				designPlanLike.setId(id);
			}

			if ("small".equals(style)) {
				String designPlanLikeJson = JsonUtil.getBeanToJsonData(designPlanLike);
				DesignPlanLikeSmall designPlanLikeSmall = new JsonDataServiceImpl<DesignPlanLikeSmall>()
						.getJsonToBean(designPlanLikeJson, DesignPlanLikeSmall.class);

				return new ResponseEnvelope<DesignPlanLikeSmall>(designPlanLikeSmall, designPlanLike.getMsgId(), true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanLike>(false, "数据异常!", designPlanLike.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanLike>(designPlanLike, designPlanLike.getMsgId(), true);
	}

	/**
	 * 取消点赞--接口
	 */
	@RequestMapping(value = "/cancelLike")
	@ResponseBody
	public Object cancelLike(@PathVariable String style,
			@ModelAttribute("designPlanLike") DesignPlanLike designPlanLike, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		
		/** 获取登录用户**/
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<DesignPlanLike>(false, "登录超时，请重新登录!", designPlanLike.getMsgId());
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			designPlanLike.setUserId(loginUser.getId());
		}

		String msg = "";
		if (StringUtils.isBlank(designPlanLike.getMsgId())) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
		}
		if (designPlanLike.getDesignId() == null) {
			msg = "参数designId不能为空";
			return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
		} else if (designPlanLike.getDesignId() != null) {
			DesignPlan dpc = new DesignPlan();
			if(Utils.enableRedisCache()){
				dpc = DesignPlanLikeCacher.getDesignPlan(designPlanLike.getDesignId());
			}else{
				dpc = designPlanService.get(designPlanLike.getDesignId());
			}
			
			if (dpc == null) {
				msg = "该方案不存在";
				return new ResponseEnvelope<DesignPlanLikeSmall>(false, msg, designPlanLike.getMsgId());
			}
		}
		int i = 0;
		try {
			i = designPlanLikeService.deleteById(designPlanLike);
			DesignPlanLikeCacher.removeDesignPlan(designPlanLike.getDesignId());/**删除数据后 清除原本缓存*/
			logger.info("delete:id=" + i);
			if (i == 0) {
				return new ResponseEnvelope<DesignPlanLike>(false, "记录不存在!", designPlanLike.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanLike>(false, "删除失败!", designPlanLike.getMsgId());
		}
		return new ResponseEnvelope<DesignPlanLike>(true, designPlanLike.getMsgId(), true);
	}

	/**
	 * 方案点赞
	 * 
	 * @param designId
	 */
	@RequestMapping(value = "/planLike")
	@ResponseBody
	public Object planLike(@ModelAttribute("DesignPlanLike") DesignPlanLike designPlanLike,
			HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		try {
			designPlanLike.setUserId(loginUser.getId());
			sysSave(designPlanLike, request);
			designPlanLikeService.add(designPlanLike);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanLike>(false, "数据异常!");
		}
		return new ResponseEnvelope<DesignPlanLike>(true);
	}

	/**
	 * 取消方案点赞
	 * 
	 * @param designId
	 */
	@RequestMapping(value = "/revokeLike")
	@ResponseBody
	public Object revokeLike(@ModelAttribute("DesignPlanLike") DesignPlanLike designPlanLike,
			HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		try {
			designPlanLike.setUserId(loginUser.getId());
			List<DesignPlanLike> designPlanLikelist = designPlanLikeService.getList(designPlanLike);
			if (designPlanLikelist.size() > 0 && designPlanLikelist != null) {
				designPlanLikeService.delete(designPlanLikelist.get(0).getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanLike>(false, "数据异常!");
		}
		return new ResponseEnvelope<DesignPlanLike>(true);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanLike model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

}
