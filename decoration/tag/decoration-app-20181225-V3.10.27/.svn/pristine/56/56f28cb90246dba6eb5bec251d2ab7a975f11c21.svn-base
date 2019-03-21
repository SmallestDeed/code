package com.nork.product.controller2.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.product.model.StructureProductDetails;
import com.nork.product.model.search.StructureProductDetailsSearch;
import com.nork.product.model.small.StructureProductDetailsSmall;
import com.nork.product.service.StructureProductDetailsService;

/**
 * @Title: StructureProductDetailsControllerV2.java
 * @Package com.nork.product.controller2
 * @Description:产品模块-结构明细表Controller 接口重构
 * @createAuthor jiangwei
 * @CreateDate 2017-06-19 16:08:46
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/structureProductDetailsV2")
public class WebStructureProductDetailsControllerV2 {
	private static Logger logger = Logger.getLogger(WebStructureProductDetailsControllerV2.class);
	private final JsonDataServiceImpl<StructureProductDetails> JsonUtil = new JsonDataServiceImpl<StructureProductDetails>();

	@Autowired
	private StructureProductDetailsService structureProductDetailsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 保存 结构明细表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@PathVariable String style,
			@ModelAttribute("structureProductDetails") StructureProductDetails structureProductDetails,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductDetails = (StructureProductDetails) JsonUtil.getJsonToBean(jsonStr,StructureProductDetails.class);
			
			if (structureProductDetails == null) {
				return new ResponseEnvelope<StructureProductDetails>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
			sysSave(structureProductDetails, request);
			int id = 0;
			if (structureProductDetails.getId() == null) {
				 id = structureProductDetailsService.add(structureProductDetails);
				logger.info("add:id=" + id);
				structureProductDetails.setId(id);
			} else {
				 id = structureProductDetailsService.update(structureProductDetails);
				logger.info("update:id=" + id);
			}

			if ("small".equals(style)) {
				String structureProductDetailsJson = JsonUtil.getBeanToJsonData(structureProductDetails);
				StructureProductDetailsSmall structureProductDetailsSmall = new JsonDataServiceImpl<StructureProductDetailsSmall>()
						.getJsonToBean(structureProductDetailsJson, StructureProductDetailsSmall.class);

				return new ResponseEnvelope<StructureProductDetailsSmall>(structureProductDetailsSmall,structureProductDetails.getMsgId(), true);
			}
		return new ResponseEnvelope<StructureProductDetails>(structureProductDetails,structureProductDetails.getMsgId(), true);
	}

	/**
	 * 获取 结构明细表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style,
			@ModelAttribute("structureProductDetails") StructureProductDetails structureProductDetails,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductDetails = (StructureProductDetails) JsonUtil.getJsonToBean(jsonStr,StructureProductDetails.class);
			if (structureProductDetails == null) {
				return new ResponseEnvelope<StructureProductDetails>(false, "none", SystemCommonConstant.PARAM_EXCEPTION);
			}
		}
		Integer id = structureProductDetails.getId();
		String  msgId = structureProductDetails.getMsgId();
		if (id == null) {
			return new ResponseEnvelope<StructureProductDetails>(false, SystemCommonConstant.ID_NOT_NULL, msgId);
		}
			structureProductDetails = structureProductDetailsService.get(id);
			if ("small".equals(style) && structureProductDetails != null) {
				String structureProductDetailsJson = JsonUtil.getBeanToJsonData(structureProductDetails);
				StructureProductDetailsSmall structureProductDetailsSmall = new JsonDataServiceImpl<StructureProductDetailsSmall>()
						.getJsonToBean(structureProductDetailsJson, StructureProductDetailsSmall.class);

				return new ResponseEnvelope<StructureProductDetailsSmall>(structureProductDetailsSmall, msgId, true);
			}
		return new ResponseEnvelope<StructureProductDetails>(structureProductDetails, msgId, true);
	}

	/**
	 * 删除结构明细表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public ResponseEnvelope<StructureProductDetails> del(@PathVariable String style,
			@ModelAttribute("structureProductDetails") StructureProductDetails structureProductDetails,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductDetails = (StructureProductDetails) JsonUtil.getJsonToBean(jsonStr,StructureProductDetails.class);
			if (structureProductDetails == null) {
				return new ResponseEnvelope<StructureProductDetails>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		} 
		String msgId = structureProductDetails.getMsgId();
		String ids = structureProductDetails.getIds();
		int i = 0;
		if (ids != null) {
			if (ids.contains(",")) {
//				i = structureProductDetailsService.deleteBatch(Utils.StringToIntegerLst(Utils.getListFromStr(ids)));
				logger.info("批量delete:"+i+"条记录");
			} else {
				Integer id = new Integer(ids);
				i = structureProductDetailsService.delete(id);
				logger.info("delete:id=" + id);
			}
		}
		if (i == 0) {
			return new ResponseEnvelope<StructureProductDetails>(false, SystemCommonConstant.RECORD_DOES_NOT_EXIST, msgId);
		}
		return new ResponseEnvelope<StructureProductDetails>(true, msgId, true);
	}

	/**
	 * 结构明细表列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@PathVariable String style,
			@ModelAttribute("structureProductDetailsSearch") StructureProductDetailsSearch structureProductDetailsSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 每页不同页码时使用
		structureProductDetailsSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductDetailsSearch = (StructureProductDetailsSearch) JsonUtil.getJsonToBean(jsonStr,StructureProductDetailsSearch.class);
			if (structureProductDetailsSearch == null) {
				return new ResponseEnvelope<StructureProductDetails>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		List<StructureProductDetails> list = new ArrayList<StructureProductDetails>();
		int total = structureProductDetailsService.getCount(structureProductDetailsSearch);
		logger.info("total:" + total);
		if (total > 0) {
			list = structureProductDetailsService.getPaginatedList(structureProductDetailsSearch);
		}
		if ("small".equals(style) && list != null && list.size() > 0) {
			String structureProductDetailsJsonList = JsonUtil.getListToJsonData(list);
			List<StructureProductDetailsSmall> smallList = new JsonDataServiceImpl<StructureProductDetailsSmall>()
					.getJsonToBeanList(structureProductDetailsJsonList, StructureProductDetailsSmall.class);
			return new ResponseEnvelope<StructureProductDetailsSmall>(total, smallList,structureProductDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<StructureProductDetails>(total, list, structureProductDetailsSearch.getMsgId());
	}

	/**
	 * 结构明细表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@ModelAttribute("structureProductDetailsSearch") StructureProductDetailsSearch structureProductDetailsSearch,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductDetailsSearch = (StructureProductDetailsSearch) JsonUtil.getJsonToBean(jsonStr,StructureProductDetailsSearch.class);
			if (structureProductDetailsSearch == null) {
				return new ResponseEnvelope<StructureProductDetails>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		List<StructureProductDetails> list = new ArrayList<StructureProductDetails>();
		int total = structureProductDetailsService.getCount(structureProductDetailsSearch);
		logger.info("total:" + total);
		if (total > 0) {
			list = structureProductDetailsService.getList(structureProductDetailsSearch);
		}
		if ("small".equals(style) && list != null && list.size() > 0) {
			String structureProductDetailsJsonList = JsonUtil.getListToJsonData(list);
			List<StructureProductDetailsSmall> smallList = new JsonDataServiceImpl<StructureProductDetailsSmall>()
					.getJsonToBeanList(structureProductDetailsJsonList, StructureProductDetailsSmall.class);
			return new ResponseEnvelope<StructureProductDetailsSmall>(total, smallList,structureProductDetailsSearch.getMsgId());
		}
		return new ResponseEnvelope<StructureProductDetails>(total, list, structureProductDetailsSearch.getMsgId());
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(StructureProductDetails model, HttpServletRequest request) {
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
}
