package com.nork.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.nork.client.model.DesignTempletSingleResponse;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.controller.web.WebDesignTempletController;
import com.nork.design.model.DesigPlanWithResult;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;

/**
 * @Title: DesignTempletController.java
 * @Package com.nork.client.controller
 * @Description:设计场景-样板房Controller
 * @createAuthor xiaoxc
 * @CreateDate 2016-09-14 16:56:35
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/client/designTemplet")
public class DesignTempletController {
	private static Logger logger = Logger
			.getLogger(WebDesignTempletController.class);
	private final JsonDataServiceImpl<DesignTemplet> JsonUtil = new JsonDataServiceImpl<DesignTemplet>();

	private final JsonDataServiceImpl<DesignPlan> DpJsonUtil = new JsonDataServiceImpl<DesignPlan>();

	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	@Resource
	private DesignPlanService designPlanService;

	/**
	 * 设计方案样板房表列表
	 */
	@RequestMapping(value = "/getSceneList")
	@ResponseBody
	public Object getSceneList(
			@ModelAttribute("designTempletSearch") DesignTempletSearch designTempletSearch,
			HttpServletRequest request, HttpServletResponse response) {

	    LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            ResponseEnvelope envelope = new ResponseEnvelope();
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            envelope.setMsgId(designTempletSearch.getMsgId());
            return envelope;
        }
        
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			designTempletSearch = (DesignTempletSearch) JsonUtil.getJsonToBean(
					jsonStr, DesignTempletSearch.class);
			if (designTempletSearch == null) {
				return new ResponseEnvelope<DesignTemplet>(false, "传参异常!",
						"none");
			}
		}
		List<DesignTempletSingleResponse> list = new ArrayList<DesignTempletSingleResponse>();
		DesignTempletSingleResponse designTempletSingleResponse = null;
		int total = 0;
		try {
			designTempletSearch.setIsDeleted(0);
			total = designTempletService.getCount(designTempletSearch,loginUser.getId() );
			if (total > 0) {
				List<DesignTemplet> dtList = designTempletService
						.getPaginatedList(designTempletSearch,loginUser.getId() );
				for (DesignTemplet designTemplet : dtList) {
					designTempletSingleResponse = new DesignTempletSingleResponse();
					// 场景配置文件路径
					if (designTemplet.getConfigFileId() != null
							&& designTemplet.getConfigFileId() > 0) {
						designTempletSingleResponse.setFileName(designTemplet.getDesignName());
					}
					list.add(designTempletSingleResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignTempletSingleResponse>(false, "数据异常!",
					designTempletSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignTempletSingleResponse>(total, list,
				designTempletSearch.getMsgId());
	}

	/**
	 * 验证产品编码
	 */
	@RequestMapping(value = "/verifyDesignTempletCode")
	@ResponseBody
	public Object verifyDesignTempletCode(HttpServletRequest request,HttpServletResponse response) {
		
		String designTemplets = request.getParameter("designTempletCode");
		String msgId = request.getParameter("msgId");
				
		
		if (StringUtils.isEmpty(designTemplets)) {
			return new ResponseEnvelope<>(false, "样板房编码为空！", msgId);
		}
		DesignTemplet designTemplet = new DesignTemplet();
		designTemplet.setDesignCode(designTemplets);
		designTemplet.setIsDeleted(0);
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		try {
			List<DesignTemplet> list = designTempletService
					.getList(designTemplet);
			if (Lists.isNotEmpty(list)) {
				DesignTemplet templet = list.get(0);
				List<DesignTempletProduct> dtpList = new ArrayList<DesignTempletProduct>();
				DesignTempletProduct templetProduct = new DesignTempletProduct();
				templetProduct.setDesignTempletId(templet.getId());
				templetProduct.setIsDeleted(0);
				dtpList = designTempletProductService.getList(templetProduct);
				int fbx = 0;
				int cfg = 0;
				for (DesignTempletProduct designProduct : dtpList) {
					BaseProduct baseProduct = baseProductService
							.get(designProduct.getProductId());
					if (baseProduct.getFbxFileId() == null || baseProduct.getFbxFileId() == 0) {
						fbx++;
					}
					if (baseProduct.getConfigId() == null || baseProduct.getConfigId() == 0) {
						cfg++;
					}
				}
				if(fbx != 0){
					obj.put("hasFbx", "noExist");
				}else if(fbx == 0){
					obj.put("hasFbx", "exist");
				}
				if(cfg != 0){
					obj.put("hasCfg", "noExist");
				}else if(cfg != 0){
					obj.put("hasCfg", "exist");
				}
				if(fbx == 0 && cfg == 0){
					if (templet.getConfigId() != null && templet.getConfigId() > 0) {
						return new ResponseEnvelope<DesignTemplet>(true, "exist", msgId);
					}
					else {
						return new ResponseEnvelope<DesignTemplet>(true, "noExist", msgId);
					}
				}
			} else {
				return new ResponseEnvelope<DesignTemplet>(false, "", msgId);
			}
			return new ResponseEnvelope<DesignTemplet>(obj, msgId, true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignTemplet>(false, "数据异常!",
					msgId);
		}
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
			logger.info(e.getMessage());
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, "数据异常!",
					msgId);
		}

		return new ResponseEnvelope<DesigPlanWithResult>(msgId, result,
				message, totalCount, dataList);
	}

}
