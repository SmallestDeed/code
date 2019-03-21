package com.nork.mgr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.mgr.model.MgrRechargeLog;
import com.nork.mgr.model.search.MgrRechargeLogSearch;
import com.nork.mgr.model.small.MgrRechargeLogSmall;
import com.nork.mgr.service.MgrRechargeLogService;

/**
 * @Title: MgrRechargeLogController.java
 * @Package com.nork.mgr.controller
 * @Description:日常工作-充值记录Controller
 * @createAuthor pandajun
 * @CreateDate 2017-03-26 05:15:26
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/mgr/mgrRechargeLog")
public class MgrRechargeLogController {
	private static Logger logger = Logger.getLogger(MgrRechargeLogController.class);
	private final JsonDataServiceImpl<MgrRechargeLog> JsonUtil = new JsonDataServiceImpl<MgrRechargeLog>();
	private final String STYLE = "jsp";
	private final String JSPSTYLE = "jsp";
	private final String MAIN = "/" + STYLE + "/mgr/mgrRechargeLog";
	private final String BASEMAIN = "/" + STYLE + "/mgr/base/mgrRechargeLog";
	private final String JSPMAIN = "/" + JSPSTYLE + "/mgr/mgrRechargeLog";

	@Autowired
	private MgrRechargeLogService mgrRechargeLogService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 充值记录 基础主页面
	 *
	 * @param
	 * @return String
	 */
	@RequestMapping(value = "/base/main")
	public String baseMain() {
		return BASEMAIN;
	}

	/**
	 * 充值记录 主页面
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
	 * 获取 充值记录详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style, @ModelAttribute("mgrRechargeLog") MgrRechargeLog mgrRechargeLog,
			HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			mgrRechargeLog = (MgrRechargeLog) JsonUtil.getJsonToBean(jsonStr, MgrRechargeLog.class);
			if (mgrRechargeLog == null) {
				return new ResponseEnvelope<MgrRechargeLog>(false, "none", "传参异常!");
			}
			id = mgrRechargeLog.getId();
			msgId = mgrRechargeLog.getMsgId();
		} else {
			id = mgrRechargeLog.getId();
			msgId = mgrRechargeLog.getMsgId();
		}

		if (id == null) {
			return new ResponseEnvelope<MgrRechargeLog>(false, "参数缺少id!", msgId);
		}

		try {
			mgrRechargeLog = mgrRechargeLogService.get(id);

			if ("small".equals(style) && mgrRechargeLog != null) {
				String mgrRechargeLogJson = JsonUtil.getBeanToJsonData(mgrRechargeLog);
				MgrRechargeLogSmall mgrRechargeLogSmall = new JsonDataServiceImpl<MgrRechargeLogSmall>()
						.getJsonToBean(mgrRechargeLogJson, MgrRechargeLogSmall.class);

				return new ResponseEnvelope<MgrRechargeLogSmall>(mgrRechargeLogSmall, msgId, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<MgrRechargeLog>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<MgrRechargeLog>(mgrRechargeLog, msgId, true);
	}

	/**
	 * 删除充值记录,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style, @ModelAttribute("mgrRechargeLog") MgrRechargeLog mgrRechargeLog,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids = "";
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			mgrRechargeLog = (MgrRechargeLog) JsonUtil.getJsonToBean(jsonStr, MgrRechargeLog.class);
			if (mgrRechargeLog == null) {
				return new ResponseEnvelope<MgrRechargeLog>(false, "传参异常!", "none");
			}
			ids = mgrRechargeLog.getIds();
			msgId = mgrRechargeLog.getMsgId();
		} else {
			ids = mgrRechargeLog.getIds();
			msgId = mgrRechargeLog.getMsgId();
		}

		if (ids == null) {
			return new ResponseEnvelope<MgrRechargeLog>(false, "参数ids不能为空!", msgId);
		}
		int i = 0;
		try {
			if (ids != null) {
				if (ids.contains(",")) {
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = mgrRechargeLogService.delete(id);
						logger.info("delete:id=" + id);
					}
				} else {
					Integer id = new Integer(ids);
					i = mgrRechargeLogService.delete(id);
					logger.info("delete:id=" + id);
				}
			}

			if (i == 0) {
				return new ResponseEnvelope<MgrRechargeLog>(false, "记录不存在!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<MgrRechargeLog>(false, "删除失败!", msgId);
		}
		return new ResponseEnvelope<MgrRechargeLog>(true, msgId, true);
	}

	/**
	 * 充值记录列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style,
			@ModelAttribute("mgrRechargeLogSearch") MgrRechargeLogSearch mgrRechargeLogSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		mgrRechargeLogSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			mgrRechargeLogSearch = (MgrRechargeLogSearch) JsonUtil.getJsonToBean(jsonStr, MgrRechargeLogSearch.class);
			if (mgrRechargeLogSearch == null) {
				return new ResponseEnvelope<MgrRechargeLog>(false, "传参异常!", "none");
			}
		}

		List<MgrRechargeLog> list = new ArrayList<MgrRechargeLog>();
		int total = 0;
		try {
			total = mgrRechargeLogService.getCount(mgrRechargeLogSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = mgrRechargeLogService.getPaginatedList(mgrRechargeLogSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String mgrRechargeLogJsonList = JsonUtil.getListToJsonData(list);
				List<MgrRechargeLogSmall> smallList = new JsonDataServiceImpl<MgrRechargeLogSmall>()
						.getJsonToBeanList(mgrRechargeLogJsonList, MgrRechargeLogSmall.class);
				return new ResponseEnvelope<MgrRechargeLogSmall>(total, smallList, mgrRechargeLogSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<MgrRechargeLog>(false, "数据异常!", mgrRechargeLogSearch.getMsgId());
		}
		return new ResponseEnvelope<MgrRechargeLog>(total, list, mgrRechargeLogSearch.getMsgId());
	}

	/**
	 * 充值记录全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@ModelAttribute("mgrRechargeLogSearch") MgrRechargeLogSearch mgrRechargeLogSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			mgrRechargeLogSearch = (MgrRechargeLogSearch) JsonUtil.getJsonToBean(jsonStr, MgrRechargeLogSearch.class);
			if (mgrRechargeLogSearch == null) {
				return new ResponseEnvelope<MgrRechargeLog>(false, "传参异常!", "none");
			}
		}

		List<MgrRechargeLog> list = new ArrayList<MgrRechargeLog>();
		int total = 0;
		try {
			total = mgrRechargeLogService.getCount(mgrRechargeLogSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = mgrRechargeLogService.getList(mgrRechargeLogSearch);
			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String mgrRechargeLogJsonList = JsonUtil.getListToJsonData(list);
				List<MgrRechargeLogSmall> smallList = new JsonDataServiceImpl<MgrRechargeLogSmall>()
						.getJsonToBeanList(mgrRechargeLogJsonList, MgrRechargeLogSmall.class);
				return new ResponseEnvelope<MgrRechargeLogSmall>(total, smallList, mgrRechargeLogSearch.getMsgId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<MgrRechargeLog>(false, "数据异常!", mgrRechargeLogSearch.getMsgId());
		}
		return new ResponseEnvelope<MgrRechargeLog>(total, list, mgrRechargeLogSearch.getMsgId());
	}

	/**
	 * 获取 充值记录详情---jsp
	 */
	@RequestMapping(value = "/jspget")
	public Object jspget(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		MgrRechargeLog mgrRechargeLog = null;
		try {
			/*mgrRechargeLog = mgrRechargeLogService.get(id);*/
			MgrRechargeLogSearch mgrRechargeLogSearch = new MgrRechargeLogSearch();
			mgrRechargeLogSearch.setStart(0);
			mgrRechargeLogSearch.setLimit(1);
			mgrRechargeLogSearch.setId(id);
			List<MgrRechargeLog> mgrRechargeLogList = mgrRechargeLogService.getMoreInfoBySearch(mgrRechargeLogSearch);
			if(mgrRechargeLogList != null && mgrRechargeLogList.size() > 0){
				mgrRechargeLog = mgrRechargeLogList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<MgrRechargeLog>(false, "数据异常!");
		}
		ResponseEnvelope<MgrRechargeLog> res = new ResponseEnvelope<MgrRechargeLog>(mgrRechargeLog);
		request.setAttribute("res", res);

		String url = "";
		String type = (String) request.getParameter("pageType");
		if ("edit".equals(type)) {
			url = JSPMAIN + "/mgrRechargeLog_edit";
		} else {
			url = JSPMAIN + "/mgrRechargeLog_view";
		}
		return Utils.getPageUrl(request, url);
	}

	/**
	 * 充值记录列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(@ModelAttribute("mgrRechargeLogSearch") MgrRechargeLogSearch mgrRechargeLogSearch,
			HttpServletRequest request, HttpServletResponse response) {
		List<MgrRechargeLog> list = new ArrayList<MgrRechargeLog>();
		int total = 0;
		try {
			total = mgrRechargeLogService.getCount(mgrRechargeLogSearch);
			logger.info("total:" + total);
			if (total > 0) {
				list = mgrRechargeLogService.getMoreInfoBySearch(mgrRechargeLogSearch);
			}
			// 识别是否有充值权限
			String rechangePermission = (String) request.getSession().getAttribute("rechangePermission");
			if(rechangePermission != null){
				
			}else{
				rechangePermission = "false";
			}
			request.setAttribute("rechangePermission", rechangePermission);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<MgrRechargeLog>(false, "数据异常!");
		}
		ResponseEnvelope<MgrRechargeLog> res = new ResponseEnvelope<MgrRechargeLog>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("search", mgrRechargeLogSearch);
		return Utils.getPageUrl(request, JSPMAIN + "/mgrRechargeLog_list");
	}

}
