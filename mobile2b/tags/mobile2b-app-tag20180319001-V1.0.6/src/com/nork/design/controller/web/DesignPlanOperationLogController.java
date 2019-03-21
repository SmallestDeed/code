package com.nork.design.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.dao.DesignPlanOperationLogMapper;
import com.nork.design.model.DesignPlanOperationLog;
import com.nork.design.model.search.DesignPlanOperationLogSearch;
import com.nork.design.service.DesignPlanOperationLogService;

/**
 * @Title: DesignPlanOperationLogController.java
 * @Package com.nork.design.controller
 * @Description:设计方案-设计方案操作日志Controller
 * @createAuthor pandajun
 * @CreateDate 2017-06-27 14:41:51
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/design/designPlanOperationLog")
public class DesignPlanOperationLogController {
	private static Logger logger = Logger
			.getLogger(DesignPlanOperationLogController.class);
	private final JsonDataServiceImpl<DesignPlanOperationLog> JsonUtil = new JsonDataServiceImpl<DesignPlanOperationLog>();
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "jsp";
	private final String MAIN = "/" + STYLE + "/design/designPlanOperationLog";
	private final String BASEMAIN = "/" + STYLE
			+ "/design/base/designPlanOperationLog";
	private final String JSPMAIN = "/" + JSPSTYLE
			+ "/design/designPlanOperationLog";

	@Autowired
	private DesignPlanOperationLogService designPlanOperationLogService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 设计方案操作日志 基础主页面
	 * 
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 * 设计方案操作日志 主页面
	 * 
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return MAIN;
	}

	/**
	 * 访问主页面
	 * 
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/jspmain")
	public String jspmain(HttpServletRequest request) {
		request.setAttribute("autoFlag", true);
		return JSPMAIN + "/list";
	}

	/**
	 * 保存 设计方案操作日志
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseEnvelope save(
			@PathVariable String style,
			@ModelAttribute("designPlanOperationLog") DesignPlanOperationLog designPlanOperationLog,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanOperationLog = (DesignPlanOperationLog) JsonUtil
					.getJsonToBean(jsonStr, DesignPlanOperationLog.class);
			if (designPlanOperationLog == null) {
				return new ResponseEnvelope<DesignPlanOperationLog>(false,
						"传参异常!", "none");
			}
		}

		sysSave(designPlanOperationLog, request);
		return designPlanOperationLogService
				.save(style, designPlanOperationLog);
	}

	/**
	 * 获取 设计方案操作日志详情
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/get")
	@ResponseBody
	public ResponseEnvelope get(
			@PathVariable String style,
			@ModelAttribute("designPlanOperationLog") DesignPlanOperationLog designPlanOperationLog,
			HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanOperationLog = (DesignPlanOperationLog) JsonUtil
					.getJsonToBean(jsonStr, DesignPlanOperationLog.class);
			if (designPlanOperationLog == null) {
				return new ResponseEnvelope<DesignPlanOperationLog>(false,
						"none", "传参异常!");
			}
			id = designPlanOperationLog.getId();
			msgId = designPlanOperationLog.getMsgId();
		} else {
			id = designPlanOperationLog.getId();
			msgId = designPlanOperationLog.getMsgId();
		}

		if (id == null) {
			return new ResponseEnvelope<DesignPlanOperationLog>(false,
					"参数缺少id!", msgId);
		}

		return designPlanOperationLogService.get(style, msgId,
				designPlanOperationLog, id);

	}

	/**
	 * 删除设计方案操作日志,支持批量删除，传递ids=1,2,3格式即可
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/del")
	@ResponseBody
	public ResponseEnvelope del(
			@PathVariable String style,
			@ModelAttribute("designPlanOperationLog") DesignPlanOperationLog designPlanOperationLog,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids = "";
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanOperationLog = (DesignPlanOperationLog) JsonUtil
					.getJsonToBean(jsonStr, DesignPlanOperationLog.class);
			if (designPlanOperationLog == null) {
				return new ResponseEnvelope<DesignPlanOperationLog>(false,
						"传参异常!", "none");
			}
			ids = designPlanOperationLog.getIds();
			msgId = designPlanOperationLog.getMsgId();
		} else {
			ids = designPlanOperationLog.getIds();
			msgId = designPlanOperationLog.getMsgId();
		}

		if (ids == null) {
			return new ResponseEnvelope<DesignPlanOperationLog>(false,
					"参数ids不能为空!", msgId);
		}
		return designPlanOperationLogService.del(style, msgId,
				designPlanOperationLog, ids);

	}

	/**
	 * 设计方案操作日志列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEnvelope list(
			@PathVariable String style,
			@ModelAttribute("designPlanOperationLogSearch") DesignPlanOperationLogSearch designPlanOperationLogSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		designPlanOperationLogSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanOperationLogSearch = (DesignPlanOperationLogSearch) JsonUtil
					.getJsonToBean(jsonStr, DesignPlanOperationLogSearch.class);
			if (designPlanOperationLogSearch == null) {
				return new ResponseEnvelope<DesignPlanOperationLog>(false,
						"传参异常!", "none");
			}
		}
		return designPlanOperationLogService.list(style,
				designPlanOperationLogSearch);
	}

	/**
	 * 设计方案操作日志全部列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public ResponseEnvelope listAll(
			@PathVariable String style,
			@ModelAttribute("designPlanOperationLogSearch") DesignPlanOperationLogSearch designPlanOperationLogSearch,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designPlanOperationLogSearch = (DesignPlanOperationLogSearch) JsonUtil
					.getJsonToBean(jsonStr, DesignPlanOperationLogSearch.class);
			if (designPlanOperationLogSearch == null) {
				return new ResponseEnvelope<DesignPlanOperationLog>(false,
						"传参异常!", "none");
			}
		}

		return designPlanOperationLogService.listAll(style,
				designPlanOperationLogSearch);
	}

	/**
	 * 获取 设计方案操作日志详情---jsp
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jspget")
	public String jspget(
			@RequestParam(value = "id", required = true) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseEnvelope<DesignPlanOperationLog> res = designPlanOperationLogService
				.jspget(id);
		request.setAttribute("res", res);

		String url = "";
		String type = (String) request.getParameter("pageType");
		if ("edit".equals(type)) {
			url = JSPMAIN + "/designPlanOperationLog_edit";
		} else {
			url = JSPMAIN + "/designPlanOperationLog_view";
		}
		return Utils.getPageUrl(request, url);
	}

	/**
	 * 设计方案操作日志列表---jsp
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jsplist")
	public Object jsplist(
			@ModelAttribute("designPlanOperationLogSearch") DesignPlanOperationLogSearch designPlanOperationLogSearch,
			HttpServletRequest request, HttpServletResponse response) {

		List<DesignPlanOperationLog> list = new ArrayList<DesignPlanOperationLog>();
		
		int total = 0;
		try {
			total = designPlanOperationLogService
					.selectCount(designPlanOperationLogSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = designPlanOperationLogService
						.selectPaginatedList(designPlanOperationLogSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignPlanOperationLog>(false, "数据异常!");
		}
		
		ResponseEnvelope<DesignPlanOperationLog> res = new ResponseEnvelope<DesignPlanOperationLog>(total, list);
		
//		ResponseEnvelope<DesignPlanOperationLog> res = designPlanOperationLogService
//				.jsplist(designPlanOperationLogSearch, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", designPlanOperationLogSearch);

		return Utils.getPageUrl(request, JSPMAIN
				+ "/designPlanOperationLog_list");
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanOperationLog model,
			HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
			

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
	}
}
