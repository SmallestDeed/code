package com.nork.product.controller2.web;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.ServiceHint;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignTempletService;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.model.small.StructureProductSmall;
import com.nork.product.service.StructureProductService;
import com.nork.product.service2.StructureProductServiceV2;

/**
 * @Title: StructureProductController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-结构表Controller
 * @createAuthor huangsongbo
 * @CreateDate 2016-12-02 15:32:05
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/structureProduct2")
public class WebStructureProductControllerv2 {
	private static Logger logger = Logger.getLogger(WebStructureProductControllerv2.class);
	private final JsonDataServiceImpl<StructureProduct> JsonUtil = new JsonDataServiceImpl<StructureProduct>();
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	@Autowired
	private StructureProductService structureProductService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private StructureProductServiceV2 structureProductServiceV2;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}


	/**
	 * 保存 结构表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@PathVariable String style,
			@ModelAttribute("structureProduct") StructureProduct structureProduct, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProduct = (StructureProduct) JsonUtil.getJsonToBean(jsonStr, StructureProduct.class);

			if (structureProduct == null) {
				return new ResponseEnvelope<StructureProduct>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		sysSave(structureProduct, request);
		if (structureProduct.getId() == null) {
			int id = structureProductService.add(structureProduct);
			logger.info("add:id=" + id);
			structureProduct.setId(id);
		} else {
			int id = structureProductService.update(structureProduct);
			logger.info("update:id=" + id);
		}
		if ("small".equals(style)) {
			String structureProductJson = JsonUtil.getBeanToJsonData(structureProduct);
			StructureProductSmall structureProductSmall = new JsonDataServiceImpl<StructureProductSmall>()
					.getJsonToBean(structureProductJson, StructureProductSmall.class);
			return new ResponseEnvelope<StructureProductSmall>(structureProductSmall, structureProduct.getMsgId(),true);
		}
		return new ResponseEnvelope<StructureProduct>(structureProduct, structureProduct.getMsgId(), true);
	}

	/**
	 * 获取 结构表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style, @ModelAttribute("structureProduct") StructureProduct structureProduct,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProduct = (StructureProduct) JsonUtil.getJsonToBean(jsonStr, StructureProduct.class);
			if (structureProduct == null) {
				return new ResponseEnvelope<StructureProduct>(false, "none", SystemCommonConstant.PARAM_EXCEPTION);
			}
		}
		String msgId = structureProduct.getMsgId();
		Integer id = structureProduct.getId();
		if (id == null) {
			return new ResponseEnvelope<StructureProduct>(false, SystemCommonConstant.ID_NOT_NULL, msgId);
		}

		structureProduct = structureProductService.get(id);
		if ("small".equals(style) && structureProduct != null) {
			String structureProductJson = JsonUtil.getBeanToJsonData(structureProduct);
			StructureProductSmall structureProductSmall = new JsonDataServiceImpl<StructureProductSmall>()
					.getJsonToBean(structureProductJson, StructureProductSmall.class);
			return new ResponseEnvelope<StructureProductSmall>(structureProductSmall, msgId, true);
		}
		return new ResponseEnvelope<StructureProduct>(structureProduct, msgId, true);
	}

	/**
	 * 删除结构表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style, @ModelAttribute("structureProduct") StructureProduct structureProduct,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProduct = (StructureProduct) JsonUtil.getJsonToBean(jsonStr, StructureProduct.class);
			if (structureProduct == null) {
				return new ResponseEnvelope<StructureProduct>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		String msgId = structureProduct.getMsgId();
		String ids = structureProduct.getIds();
		int i = 0;
		if (ids != null) {
			if (ids.contains(",")) {
				// 把带,的String传给Utils里转成List<String>数据,然后再转成List<Integer>类型进行批量删除
				i = structureProductService.deleteBatch(Utils.StringToIntegerLst(Utils.getListFromStr(ids)));
				logger.info("批量delete:" + i + "条记录");
			} else {
				Integer id = new Integer(ids);
				i = structureProductService.delete(id);
				logger.info("delete:id=" + id);
			}
		}
		if (i == 0) {
			return new ResponseEnvelope<StructureProduct>(false, SystemCommonConstant.RECORD_DOES_NOT_EXIST, msgId);
		}
		return new ResponseEnvelope<StructureProduct>(true, msgId, true);
	}

	/**
	 * 结构表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style,
			@ModelAttribute("structureProductSearch") StructureProductSearch structureProductSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 每页不同页码时使用
		structureProductSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductSearch = (StructureProductSearch) JsonUtil.getJsonToBean(jsonStr,
					StructureProductSearch.class);
			if (structureProductSearch == null) {
				return new ResponseEnvelope<StructureProduct>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		List<StructureProduct> list = new ArrayList<StructureProduct>();
		int total = structureProductService.getCount(structureProductSearch);
		logger.info("total:" + total);
		if (total > 0) {
			list = structureProductService.getPaginatedList(structureProductSearch);
		}
		if ("small".equals(style) && list != null && list.size() > 0) {
			String structureProductJsonList = JsonUtil.getListToJsonData(list);
			List<StructureProductSmall> smallList = new JsonDataServiceImpl<StructureProductSmall>()
					.getJsonToBeanList(structureProductJsonList, StructureProductSmall.class);
			return new ResponseEnvelope<StructureProductSmall>(total, smallList, structureProductSearch.getMsgId());
		}
		return new ResponseEnvelope<StructureProduct>(total, list, structureProductSearch.getMsgId());
	}

	/**
	 * 结构表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@ModelAttribute("structureProductSearch") StructureProductSearch structureProductSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductSearch = (StructureProductSearch) JsonUtil.getJsonToBean(jsonStr,
					StructureProductSearch.class);
			if (structureProductSearch == null) {
				return new ResponseEnvelope<StructureProduct>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		List<StructureProduct> list = new ArrayList<StructureProduct>();
		int total = structureProductService.getCount(structureProductSearch);
		logger.info("total:" + total);
		if (total > 0) {
			list = structureProductService.getList(structureProductSearch);
		}
		if ("small".equals(style) && list != null && list.size() > 0) {
			String structureProductJsonList = JsonUtil.getListToJsonData(list);
			List<StructureProductSmall> smallList = new JsonDataServiceImpl<StructureProductSmall>()
					.getJsonToBeanList(structureProductJsonList, StructureProductSmall.class);
			return new ResponseEnvelope<StructureProductSmall>(total, smallList, structureProductSearch.getMsgId());
		}
		return new ResponseEnvelope<StructureProduct>(total, list, structureProductSearch.getMsgId());
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(StructureProduct model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 定义结构接口(新增结构,用于编辑器调用)
	 * 
	 * @author huangsongbo
	 * @param structureCode
	 * @param structureName
	 * @param config
	 * @param msgId
	 * @param request
	 * @return
	 */
	@RequestMapping("/createStructureProduct")
	@ResponseBody
	public Object createStructureProduct(String structureCode, String structureName, String templetCode,
			String groupFlag, String config, String msgId, HttpServletRequest request) {
		/* 验证参数 */
	    
	    ResponseEnvelope envelope = new ResponseEnvelope();

        envelope.setMsgId(msgId);

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            envelope.setMsgId(msgId);
            return envelope;
        }
		if (StringUtils.isBlank(structureCode) || StringUtils.isBlank(templetCode) || StringUtils.isBlank(config))
			return new ResponseEnvelope<>(false, SystemCommonConstant.PARAMETER_IS_NULL, msgId);
		/* 验证样板房是否存在 */
		StructureProductParameter sParameter = new StructureProductParameter();
		sParameter.setStructureCode(structureCode);
		sParameter.setStructureName(structureName);
		sParameter.setTempletCode(templetCode);
		sParameter.setGroupFlag(groupFlag);
		sParameter.setConfig(config);
		sParameter.setMsgId(msgId);
		
		ServiceHint serviceHint = structureProductServiceV2.createStructureProduct(sParameter,loginUser.getId());
		if(serviceHint != null){
			return new ResponseEnvelope<>(serviceHint.isTrueOrFalse(), serviceHint.getMessage(), serviceHint.getMsgId());
		}else{
			return new ResponseEnvelope<>(true, SystemCommonConstant.CREATE_SUCCESS, msgId);
		}
	}


	/**
	 * 查询结构接口
	 * 
	 * @author huangsongbo
	 * @param planProductId
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchStructureProduct")
	@ResponseBody
	public Object searchStructureProduct(Integer planProductId, Integer spaceCommonId, String msgId, Integer start,
			Integer limit, HttpServletRequest request) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		/* 参数验证 */
		List<SearchStructureProductResult> list = new ArrayList<SearchStructureProductResult>();
		if (planProductId == null || planProductId < 1) {
			/* 返回空列表 */
			return new ResponseEnvelope<>(new Integer(0), list, msgId);
		}
		StructureProductParameter sParameter = new StructureProductParameter();
		sParameter.setPlanProductId(planProductId);
		sParameter.setSpaceCommonId(spaceCommonId);
		sParameter.setMsgId(msgId);
		sParameter.setStart(start);
		sParameter.setLimit(limit);
		sParameter.setList(list);
		sParameter.setRequest(request);
		sParameter.setLoginUser(loginUser);
		StructureProductParameter spp = structureProductServiceV2.searchStructureProduct(sParameter);
		if(spp.getServiceHint() != null){
			return new ResponseEnvelope<>(spp.getServiceHint().getInts(), spp.getServiceHint().getList(), spp.getServiceHint().getMsgId());
		}
			return new ResponseEnvelope<>(spp.getTotal(), spp.getList(), spp.getMsgId());
	}

	/**
	 * 替换结构接口
	 * 
	 * @param designPlanId
	 * @param groupId
	 * @param planGroupId
	 * @param groupType
	 * @param groupProductJson
	 * @param context
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/unityStructureProduct")
	@ResponseBody
	public Object unityStructureProduct(Integer designPlanId, Integer groupId, String planGroupId,
			String groupProductJson, String context, Integer groupType, String msgId, HttpServletRequest request,
			@RequestParam(value = "houseId", required = false) String houseId,
			@RequestParam(value = "livingId", required = false) String livingId,
			@RequestParam(value = "residentialUnitsName", required = false) String residentialUnitsName,
			@RequestParam(value = "newFlag", required = false) Integer newFlag)
			throws InvocationTargetException, IllegalAccessException {
		if (designPlanId == null || StringUtils.isEmpty(context) || StringUtils.isEmpty(groupProductJson)) {
			return new ResponseEnvelope<DesignPlan>(false, SystemCommonConstant.PARAMETER_IS_NULL, msgId);
		}
		StructureProductParameter sParameter = new StructureProductParameter();
		sParameter.setDesignPlanId(designPlanId);
		sParameter.setGroupId(groupId);
		sParameter.setPlanGroupId(planGroupId);
		sParameter.setGroupProductJson(groupProductJson);
		sParameter.setContext(context);
		sParameter.setGroupType(groupType);
		sParameter.setMsgId(msgId);
		sParameter.setHouseId(houseId);
		sParameter.setLivingId(livingId);
		sParameter.setResidentialUnitsName(residentialUnitsName);
		sParameter.setNewFlag(newFlag);
		StructureProductParameter spp = structureProductServiceV2.unityStructureProduct(sParameter);
		if(spp.getServiceHint() != null){
			return new ResponseEnvelope<DesignPlan>(spp.getServiceHint().isTrueOrFalse(), spp.getServiceHint().getMessage(), spp.getServiceHint().getMessage());
		}
		return new ResponseEnvelope<>(spp.getUlist(), spp.getMsgId());
	}

	/**
	 * 一键替换结构匹配(多个结构)
	 * 
	 * @param structureCodes
	 * @param designTempletId
	 * @return
	 */
	@RequestMapping("/matchingStructureData")
	@ResponseBody
	public Object matchingStructureData(String structureCodes, Integer designTempletId, String msgId,
			HttpServletRequest request) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (StringUtils.isEmpty(msgId) || StringUtils.isEmpty(structureCodes) || designTempletId == null) {
			return new ResponseEnvelope<DesignPlan>(false, SystemCommonConstant.MSGID_NOT_NULL, msgId);
		}
		StructureProductParameter sParameter = new StructureProductParameter();
		sParameter.setStructureCodes(structureCodes);
		sParameter.setDesignTempletId(designTempletId);
		sParameter.setLoginUser(loginUser);
		StructureProductParameter spp = structureProductServiceV2.matchingStructureData(sParameter);
		return new ResponseEnvelope<>(spp.getTotal(), spp.getList(), spp.getMsgId());
	}

}