package com.nork.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.controller.web.WebDesignTempletController;
import com.nork.design.model.DesigPlanWithResult;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductResult;
import com.nork.product.service.GroupProductService;

@Controller
@RequestMapping("/{style}/client/groupProduct")
public class GroupProductController {

	private static Logger logger = Logger
			.getLogger(WebDesignTempletController.class);
	
	private final JsonDataServiceImpl<GroupProduct> JpJsonUtil = new JsonDataServiceImpl<GroupProduct>();
	private final JsonDataServiceImpl<DesignPlan> DpJsonUtil = new JsonDataServiceImpl<DesignPlan>();
	
	@Resource
	private GroupProductService groupProductService;
	@Resource
	private DesignPlanService designPlanService;
	
	/**
	 * @Description 获取组合列表接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getGroupList")
	@ResponseBody
	public Object getCombinedList(
			@ModelAttribute("groupProduct") GroupProduct groupProduct,
			HttpServletRequest request, HttpServletResponse response) {


		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			groupProduct = (GroupProduct) JpJsonUtil.getJsonToBean(jsonStr,
					GroupProduct.class);
			if (groupProduct == null) {
				return new ResponseEnvelope<GroupProduct>(false, "传参异常!",
						"none");
			}
		}

		String msgId = request.getParameter("msgId") == null ? "" : request
				.getParameter("msgId"); // 客户端业务ID。服务端原样返回
		String type = request.getParameter("type") == null ? "" : request
				.getParameter("type"); // 组合类型。可通过此参数过滤。
		String groupCode = request.getParameter("groupCode") == null ? ""
				: request.getParameter("groupCode"); // 组合编码
		String start = request.getParameter("start") == null ? "" : request
				.getParameter("start"); // 分页起始下标。默认值为0
		String limit = request.getParameter("limit") == null ? "" : request
				.getParameter("limit"); // 分页使用，表示每页数据量。默认值为20

		String order = request.getParameter("order") == null ? "" : request
				.getParameter("order"); // 排序字段
		String orderNum = request.getParameter("orderNum") == null ? ""
				: request.getParameter("orderNum"); // 排序规则（desc或asc）

		long totalCount = 0l;

		List<GroupProductResult> dataList = new ArrayList<GroupProductResult>();

		boolean result = true;

		String message = "调用成功";

		GroupProduct product = new GroupProduct();
		if (StringUtils.isNotEmpty(type)) {
			product.setType(Integer.valueOf(type));
		}
		if (StringUtils.isNotEmpty(limit)) {
			product.setLimit(Integer.valueOf(limit));
		}
		if (StringUtils.isNotBlank(start)) {
			product.setStart(Integer.valueOf(start));
		}
		product.setGroupCode(groupCode);
		product.setOrder(order);
		product.setOrderNum(orderNum);

		try {
			dataList = groupProductService.selectCommonList(groupProduct);
			totalCount = dataList.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, "数据异常!",
					msgId);
		}
		return new ResponseEnvelope<GroupProductResult>(msgId, result, message,
				totalCount, dataList);
	}

	/**
	 * @Description 获取场景列表 </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getScenariosList")
	@ResponseBody
	public Object getScenariosList(
			@ModelAttribute("designPlan") DesignPlan plan,
			HttpServletRequest request, HttpServletResponse response) {

		String jsonStr = Utils.getJsonStr(request);

		if (jsonStr != null && jsonStr.trim().length() > 0) {
			plan = (DesignPlan) DpJsonUtil.getJsonToBeanList(jsonStr,
					DesignPlan.class);
			if (plan == null) {
				return new ResponseEnvelope<DesignPlan>(false, "传参异常!", "none");
			}
		}

		String msgId = request.getParameter("msgId") == null ? "" : request
				.getParameter("msgId"); // 客户端业务ID。服务端原样返回
		String start = request.getParameter("start") == null ? "" : request
				.getParameter("start"); // 分页起始下标。默认值为0
		String limit = request.getParameter("limit") == null ? "" : request
				.getParameter("limit"); // 分页使用，表示每页数据量。默认值为20
		String order = request.getParameter("order") == null ? "" : request
				.getParameter("order"); // 排序字段
		String orderNum = request.getParameter("orderNum") == null ? ""
				: request.getParameter("orderNum"); // 排序规则（desc或asc）
		long totalCount = 0l;

		List<DesigPlanWithResult> dataList = new ArrayList<DesigPlanWithResult>();

		boolean result = true;

		String message = "调用成功";

		DesignPlan designPlan = new DesignPlan();

		if (StringUtils.isNotEmpty(limit)) {
			designPlan.setLimit(Integer.valueOf(limit));
		}
		if (StringUtils.isNotBlank(start)) {
			designPlan.setStart(Integer.valueOf(start));
		}
		designPlan.setOrder(order);
		designPlan.setOrderNum(orderNum);
		try {
			dataList = designPlanService.selectDesignList(designPlan);
			totalCount = dataList.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, "数据异常!",
					msgId);
		}

		return new ResponseEnvelope<DesigPlanWithResult>(msgId, result,
				message, totalCount, dataList);
	}

}
	
