package com.nork.customerservice.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.customerservice.model.SysFeedback;
import com.nork.customerservice.model.search.SysFeedbackSearch;
import com.nork.customerservice.service.SysFeedbackService;

@Controller
@RequestMapping("/{style}/web/customerservice/sysFeedback")
public class WebSysFeedbackController {

	@Autowired
	private SysFeedbackService sysFeedbackService;
	private final JsonDataServiceImpl<SysFeedback> JsonUtil = new JsonDataServiceImpl<SysFeedback>();
	private static Logger logger = Logger
			.getLogger(WebSysFeedbackController.class);

	/**
	 * 新增问题反馈接口
	 * 
	 * @author huangsongbo
	 * @param title
	 *            标题
	 * @param content
	 *            正文
	 * @param msgId
	 *            msgId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/createFeedback")
	@ResponseBody
	public Object createFeedback(String title, String content, String msgId,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = sysFeedbackService
				.verifyParamsFromCreateFeedback(title, content, msgId);
		if (StringUtils.equals("false", map.get("success")))
			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
		SysFeedback sysFeedback = new SysFeedback();
		LoginUser loginUser = sysSave(sysFeedback, request);
		sysFeedback.setUserId(loginUser.getId());
		sysFeedback.setUsername(loginUser.getName());
		sysFeedback.setTitle(title);
		sysFeedback.setContent(content);
		sysFeedback.setStatus(new Integer(0));
		//将反馈信息默认为已读
		sysFeedback.setNuma1(1);
		sysFeedbackService.add(sysFeedback);
		return new ResponseEnvelope<SysFeedback>(sysFeedback, msgId, true);
	}

	/**
	 * 得到个人问题反馈信息
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object getFreeback(String msgId,
			@ModelAttribute("feedbackSearch") SysFeedbackSearch feedbackSearch,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			loginUser.setId(loginUser.getId()!=null?loginUser.getId():-1);
		}
         /*将客服反馈信息状态改为已读*/
		try {
			sysFeedbackService.updateIsReadFeedBack(loginUser.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ResponseEnvelope<WebSysFeedbackController>(false,
					"数据异常!");
		}
		/*查看前十条反馈消息*/
		List<SysFeedback> list = new ArrayList<SysFeedback>();
		int count = 0;
		try {
			feedbackSearch.setUserId(loginUser.getId());
			feedbackSearch.setIsDeleted(0);
			feedbackSearch.setStart(0);
			feedbackSearch.setLimit(10);
			count = sysFeedbackService.getCount(feedbackSearch);
			logger.info("count:" + count);
			if (count > 0) {
				list = sysFeedbackService.getPaginatedList(feedbackSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<WebSysFeedbackController>(false,
					"数据异常!");
		}

		request.setAttribute("list", list);
		return new ResponseEnvelope<SysFeedback>(count, list, msgId);
	}
	/**
	 * 获取未读客服反馈消息个数
	 * @return
	 */
	@RequestMapping("/countIsNotReadFeedback")
	@ResponseBody
	public Object countIsNotReadFeedback(HttpServletRequest request, String msgId){
		int ret=0;
		SysFeedback feedback=new SysFeedback();
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			loginUser.setId(loginUser.getId()!=null?loginUser.getId():-1);
		}
		//状态为未读
		feedback.setNuma1(0);
		feedback.setUserId(loginUser.getId()!=null?loginUser.getId():-1);
		try {
			ret=sysFeedbackService.countIsNotReadFeedback(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<WebSysFeedbackController>(false,
					"数据异常!");
		}
		return new ResponseEnvelope<SysFeedback>(ret,null,msgId);
	}
	/**
	 * 自动存储系统字段
	 */
	private LoginUser sysSave(SysFeedback model, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
				|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
		return loginUser;
	}


}
