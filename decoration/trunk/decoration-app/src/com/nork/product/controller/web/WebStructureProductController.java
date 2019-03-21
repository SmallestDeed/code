package com.nork.product.controller.web;
import com.nork.product.model.ProductSimpleModel;
import com.nork.product.model.ProductSpellingFlowerModel;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.design.model.ProductDTO;
import com.nork.design.model.UnityPlanProduct;
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
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignCacher;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.StructureProductStatusCode;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.StructureProductSearch;
import com.nork.product.model.small.StructureProductSmall;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.StructureProductDetailsService;
import com.nork.product.service.StructureProductService;
import com.nork.system.model.BaseArea;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.BaseAreaService;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.SysDictionaryService;
import com.nork.user.model.UserTypeCode;

/**
 * @Title: StructureProductController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-结构表Controller
 * @createAuthor huangsongbo
 * @CreateDate 2016-12-02 15:32:05
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/structureProduct")
public class WebStructureProductController {
	private static Logger logger = Logger.getLogger(WebStructureProductController.class);
	private final JsonDataServiceImpl<StructureProduct> JsonUtil = new JsonDataServiceImpl<StructureProduct>();
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	@Autowired
	private StructureProductService structureProductService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private StructureProductDetailsService structureProductDetailsService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	@Autowired
	private GroupProductService groupProductService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private DesignTempletProductService designTempletProductService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private BaseAreaService baseAreaService;
	
	@Autowired
	private ResDesignService resDesignService; 
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
				return new ResponseEnvelope<StructureProduct>(false, "传参异常!", "none");
			}
		}

		try {
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

				return new ResponseEnvelope<StructureProductSmall>(structureProductSmall, structureProduct.getMsgId(),
						true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<StructureProduct>(false, "数据异常!", structureProduct.getMsgId());
		}
		return new ResponseEnvelope<StructureProduct>(structureProduct, structureProduct.getMsgId(), true);
	}

	/**
	 * 获取 结构表详情
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object get(@PathVariable String style, @ModelAttribute("structureProduct") StructureProduct structureProduct,
			HttpServletRequest request, HttpServletResponse response) {
		String msgId = "";
		String jsonStr = Utils.getJsonStr(request);
		Integer id = null;
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProduct = (StructureProduct) JsonUtil.getJsonToBean(jsonStr, StructureProduct.class);
			if (structureProduct == null) {
				return new ResponseEnvelope<StructureProduct>(false, "none", "传参异常!");
			}
			id = structureProduct.getId();
			msgId = structureProduct.getMsgId();
		} else {
			id = structureProduct.getId();
			msgId = structureProduct.getMsgId();
		}

		if (id == null) {
			return new ResponseEnvelope<StructureProduct>(false, "参数缺少id!", msgId);
		}

		try {
			structureProduct = structureProductService.get(id);

			if ("small".equals(style) && structureProduct != null) {
				String structureProductJson = JsonUtil.getBeanToJsonData(structureProduct);
				StructureProductSmall structureProductSmall = new JsonDataServiceImpl<StructureProductSmall>()
						.getJsonToBean(structureProductJson, StructureProductSmall.class);

				return new ResponseEnvelope<StructureProductSmall>(structureProductSmall, msgId, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<StructureProduct>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<StructureProduct>(structureProduct, msgId, true);
	}

	/**
	 * 删除结构表,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style, @ModelAttribute("structureProduct") StructureProduct structureProduct,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		String msgId = "";
		String ids = "";
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProduct = (StructureProduct) JsonUtil.getJsonToBean(jsonStr, StructureProduct.class);
			if (structureProduct == null) {
				return new ResponseEnvelope<StructureProduct>(false, "传参异常!", "none");
			}
			ids = structureProduct.getIds();
			msgId = structureProduct.getMsgId();
		} else {
			ids = structureProduct.getIds();
			msgId = structureProduct.getMsgId();
		}

		if (ids == null) {
			return new ResponseEnvelope<StructureProduct>(false, "参数ids不能为空!", msgId);
		}
		int i = 0;
		try {
			if (ids != null) {
				if (ids.contains(",")) {
					String[] strs = ids.split(",");
					for (String c : strs) {
						Integer id = new Integer(c);
						i = structureProductService.delete(id);
						logger.info("delete:id=" + id);
					}
				} else {
					Integer id = new Integer(ids);
					i = structureProductService.delete(id);
					logger.info("delete:id=" + id);
				}
			}

			if (i == 0) {
				return new ResponseEnvelope<StructureProduct>(false, "记录不存在!", msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<StructureProduct>(false, "删除失败!", msgId);
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
			HttpServletRequest request, HttpServletResponse response) {
		// 每页不同页码时使用
		structureProductSearch.setLimit(new Integer(20));

		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			structureProductSearch = (StructureProductSearch) JsonUtil.getJsonToBean(jsonStr,
					StructureProductSearch.class);
			if (structureProductSearch == null) {
				return new ResponseEnvelope<StructureProduct>(false, "传参异常!", "none");
			}
		}
		structureProductSearch.setIsDeleted(0);
		List<StructureProduct> list = new ArrayList<StructureProduct>();
		int total = 0;
		try {
			total = structureProductService.getCount(structureProductSearch);
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

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<StructureProduct>(false, "数据异常!", structureProductSearch.getMsgId());
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
				return new ResponseEnvelope<StructureProduct>(false, "传参异常!", "none");
			}
		}
		structureProductSearch.setIsDeleted(0);
		List<StructureProduct> list = new ArrayList<StructureProduct>();
		int total = 0;
		try {
			total = structureProductService.getCount(structureProductSearch);
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

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<StructureProduct>(false, "数据异常!", structureProductSearch.getMsgId());
		}
		return new ResponseEnvelope<StructureProduct>(total, list, structureProductSearch.getMsgId());
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(StructureProduct model, HttpServletRequest request) {
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
	public Object createStructureProduct(Integer styleId, String regionMark, String measureCode, String structureCode,
			String structureName, String templetCode, String groupFlag, String config, String msgId, Integer isCommon,Integer structureSmallType,
			@ModelAttribute("structureProduct") StructureProduct structureProduct, String center, HttpServletRequest request) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if(loginUser == null) {
			loginUser = new LoginUser();
			loginUser.setLoginName("noLogin");
			loginUser.setId(-1);
		}
		return structureProductService.createStructureProduct(styleId, isCommon, regionMark, measureCode, structureCode,
				structureName, templetCode, groupFlag, config, msgId, structureSmallType,loginUser, center);

	}

	/**
	 * 通过编码存入结构类型和序号（方便一键装修结构匹配）
	 * @author xiaoxc
	 * @param structureCode
	 * @param structureProduct
	 * @return StructureProduct
	 */
	public StructureProduct saveStructureTypeOrNumber(String structureCode,StructureProduct structureProduct){
		if( StringUtils.isNotEmpty(structureCode) ){
			String number = structureCode.substring(structureCode.lastIndexOf("_")+1,structureCode.length());
			String strCode = structureCode.substring(0,structureCode.lastIndexOf("_"));
			String type = strCode.substring(strCode.lastIndexOf("_")+1,strCode.length());
			structureProduct.setStructureType(type);
			structureProduct.setStructureNumber(number==null?0:Utils.getIntValue(number));
		}
		return structureProduct;
	}
	
	/**
	 * 查询结构接口
	 * @author huangsongbo
	 * @param planProductId
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchStructureProduct")
	@ResponseBody
	public Object searchStructureProduct(
			Integer planProductId, Integer spaceCommonId,
			String msgId,Integer start,Integer designPlanId,
			Integer limit, HttpServletRequest request,Integer isStandard,
			Integer styleId,String regionMark,String measureCode){
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser == null) {
//			loginUser = new LoginUser();
//			loginUser.setUserType(1);
			return new ResponseEnvelope<>(false, "请重新登录", msgId);
		}
		String mediaType = SystemCommonUtil.getMediaType(request);
//		String mediaType = "3";

		return structureProductService.searchStructureProduct(planProductId,spaceCommonId,designPlanId,msgId,start,limit,loginUser,mediaType,isStandard,styleId,regionMark,measureCode);
	}
	
	/**
	 * 替换结构接口
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
	public Object unityStructureProduct(
			Integer designPlanId,Integer groupId,
			String planGroupId,
			String groupProductJson,String context,
			Integer groupType,
			String msgId,HttpServletRequest request,
			Integer isStandard,String center,String regionMark,Integer styleId,String measureCode,
			@RequestParam(value = "houseId",required = false) String houseId,
			@RequestParam(value = "livingId",required = false) String livingId,
			@RequestParam(value = "residentialUnitsName",required = false) String residentialUnitsName,
			@RequestParam(value = "newFlag",required = false) Integer newFlag) {

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		ResponseEnvelope responseEnvelope = (ResponseEnvelope) structureProductService.unityStructureProduct(designPlanId,
				groupId,planGroupId,groupProductJson,context,groupType,msgId,loginUser, isStandard, center, regionMark, styleId, measureCode);

		//异步更新进入设计方案缓存
		/* 设计方案产品列表不走缓存
		designPlanService.updateDesignPlanCache(designPlanId,newFlag,houseId,livingId,residentialUnitsName,request);*/

		return responseEnvelope;
	}
	


	/**
	 * 一键替换结构匹配(多个结构)
	 * @param structureCodes
	 * @param designTempletId
	 * @return
	 */
	@RequestMapping("/matchingStructureData")
	@ResponseBody
	public Object matchingStructureData( String structureCodes, Integer designTempletId,
			String msgId,HttpServletRequest request ) {
		if ( StringUtils.isEmpty(msgId) ) {
			return new ResponseEnvelope<DesignPlan>(false, "参数msgId为空！", msgId);
		}
		if ( StringUtils.isEmpty(structureCodes) ) {
			return new ResponseEnvelope<DesignPlan>(false, "参数structureCodes为空！", msgId);
		}
		if ( designTempletId == null ) {
			return new ResponseEnvelope<DesignPlan>(false, "参数designTempletId为空！", msgId);
		}
		DesignTemplet designTemplet = null;
		if(Utils.enableRedisCache()){
			designTemplet = DesignCacher.getTemplet(designTempletId);
		}else{
			designTemplet = designTempletService.get(designTempletId);
		}
		if (designTemplet == null) {
			return new ResponseEnvelope<DesignPlan>(false, "样板房不存在", msgId);
		}
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		List<SearchStructureProductResult> list = new ArrayList<SearchStructureProductResult>();
		String codesArry[] = structureCodes.split(",");
		for (String structureCode : codesArry) {
			StructureProductSearch structureProductSearch = new StructureProductSearch();
			StructureProduct structureProduct = null;
			//通过结构编码找到结构对象
			structureProductSearch.setStructureCode(structureCode);
			structureProductSearch.setIsDeleted(0);
			structureProductSearch.setStart(0);
			structureProductSearch.setLimit(1);
			List<StructureProduct> structureList = structureProductService.getPaginatedList(structureProductSearch);

			if (Lists.isNotEmpty(structureList)) {
				structureProduct = structureList.get(0);
			} else {
				return new ResponseEnvelope<DesignPlan>(false, "模板结构不存在！structureCode=" + structureCode, msgId);
			}
			//搜索匹配的结构及结构明细
			StructureProductSearch templetStructureSearch = new StructureProductSearch();
			templetStructureSearch.setStructureType(structureProduct.getStructureType());//结构类型
			templetStructureSearch.setTempletId(designTempletId);
			templetStructureSearch.setStructureNumber(structureProduct.getStructureNumber());
			templetStructureSearch.setStyleId(structureProduct.getStyleId());//结构款式
			List<Integer> statusList = new ArrayList<>();
			if (loginUser.getUserType().intValue() == UserTypeCode.USER_TYPE_INNER.intValue()) {
				statusList.add(StructureProductStatusCode.HAS_BEEN_PUTAWAY);
				statusList.add(StructureProductStatusCode.TESTING);
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			} else {
				statusList.add(StructureProductStatusCode.HAS_BEEN_RELEASE);
			}
			templetStructureSearch.setStatusList(statusList);
			templetStructureSearch.setIsDeleted(0);
			templetStructureSearch.setStart(0);
			templetStructureSearch.setLimit(1);
			List<StructureProduct> templetStructureList = structureProductService.getStructureObject(templetStructureSearch);
			StructureProduct structureProduct1 = null;
			if (Lists.isNotEmpty(templetStructureList)) {
				structureProduct1 = templetStructureList.get(0);
			} else {
				return new ResponseEnvelope<DesignPlan>(false, "找不到匹配" + structureCode + "的结构！designTempletId=" + designTempletId, msgId);
			}
			//结构列表json
			SearchStructureProductResult searchStructureProductResult = structureProductService.getSearchStructureProductResult(structureProduct1,"");
			list.add(searchStructureProductResult);
		}
		int total = Utils.getListTotal(list) ;

		return new ResponseEnvelope<>(total,list, msgId);
	}

	
	private List<UnityPlanProduct>  wrapperData(Integer designPlanId, List<UnityPlanProduct> dataList) {
		List<ProductDTO> list = designPlanService.getProductDTOList(designPlanId);
		for(UnityPlanProduct upp : dataList) {
			Integer upp_productId = upp.getProductId();
			for(ProductDTO productDTO : list) {
				Integer productId = productDTO.getProductId();
				if(upp_productId.equals(productId)) {
					String valueKey = productDTO.getValueKey();
					if(StringUtils.isNotBlank(valueKey)) {
						if(valueKey.indexOf("_") != -1) {
							String[] split = valueKey.split("_");
							upp.setBasicModelType(split[1]);
						} else {
							upp.setBasicModelType(valueKey);
						}
					}
				}
			}
		}
		
		return dataList;
	}
	
	
	
	
	
	/**
	 * 用于结构数据修正
	 * 产品拼花  取结构功能
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/productSpellingFlower")
	@ResponseBody
	public Object productSpellingFlower(HttpServletRequest request ,HttpServletResponse response) {
		
		String limit = request.getParameter("limit");
		String start = request.getParameter("start");
		String msgId = request.getParameter("msgId");
		String timeStart = request.getParameter("timeStart");
		String timeEnd = request.getParameter("timeEnd");
		String area = request.getParameter("area");
		String id = request.getParameter("id");
		String designTempletCode = request.getParameter("designTempletCode");
		 
		ProductSpellingFlowerModel model = new ProductSpellingFlowerModel();
		model.setDesignTempletCode(designTempletCode);
		model.setLimit(limit);
		model.setStart(start);
		model.setMsgId(msgId);
		model.setTimeEnd(timeEnd);
		model.setTimeStart(timeStart);
		model.setArea(area);
		model.setId(id);
		return structureProductService.productSpellingFlower(model);

	}
	

	
	
	
	/**
	 * 保存结构拼花文本信息、产品拼花文本信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/saveProductSpellingFlower")
	@ResponseBody
	public Object saveProductSpellingFlower(HttpServletRequest request ,HttpServletResponse response) {
		String productSpellingFlower = request.getParameter("productSpellingFlower");
		String structureSpellingFlower = request.getParameter("structureSpellingFlower");
		String structureId = request.getParameter("structureId");
		String msgId = request.getParameter("msgId");
		if(StringUtils.isEmpty(productSpellingFlower) || StringUtils.isEmpty(structureSpellingFlower) 
				|| StringUtils.isEmpty(structureId) ||StringUtils.isEmpty(msgId)) {
			return new ResponseEnvelope<>(false, "缺少参数", msgId);
		}
		return structureProductService.saveProductSpellingFlower(productSpellingFlower,structureSpellingFlower,structureId,msgId); 
	}
	
	
	
	/**
	 * 通过 结构ids  获取   结构拼花文本 集合
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping("/getStructureSpellingFlowerTxt")
	@ResponseBody
	public Object getStructureSpellingFlowerTxt(HttpServletRequest request ,HttpServletResponse response){
		String msgId = request.getParameter("msgId");
		String structureIds = request.getParameter("structureIds");
		if(StringUtils.isEmpty(msgId) || StringUtils.isEmpty(structureIds) ){
			return new ResponseEnvelope<>(false, "缺少参数", msgId);
		}	
		return structureProductService.getStructureSpellingFlowerTxt(msgId,structureIds);
	}
}
