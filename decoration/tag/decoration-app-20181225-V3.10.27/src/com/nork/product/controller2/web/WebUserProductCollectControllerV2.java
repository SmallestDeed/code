package com.nork.product.controller2.web;

import java.util.ArrayList;
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
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.UserProductPlan;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.cache.UserProductCollectCacher;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.UserProductsConllect;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.model.small.UserProductCollectSmall;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service2.UserProductCollectServiceV2;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;

/**
 * Created by lizf on 2015/7/14.
 */
@Controller
@RequestMapping("/{style}/web/product/userProductCollect2")
public class WebUserProductCollectControllerV2 {
	private static Logger logger = Logger.getLogger(WebUserProductCollectControllerV2.class);
	private final JsonDataServiceImpl<UserProductCollect> JsonUtil = new JsonDataServiceImpl<UserProductCollect>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/product/userProductCollect";
	private final String BASEMAIN = "/" + STYLE + "/product/base/userProductCollect";
	private final String JSPMAIN = "/" + JSPSTYLE + "/product/userProductCollect";

	@Autowired
	private UserProductCollectServiceV2 userProductCollectService;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseBrandService baseBrandService;

	@RequestMapping(value = "/jsplist")
	@ResponseBody
	public Object jsplist(@ModelAttribute("userProductCollectSearch") UserProductCollectSearch userProductCollectSearch,
			HttpServletRequest request, HttpServletResponse response) {

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String mediaType = SystemCommonUtil.getMediaType(request);
		String productTypeValue = request.getParameter("productTypeValue");
		productTypeValue = (productTypeValue == null || "undefined".equals(productTypeValue)) ? "" : productTypeValue;
		String brandIdStr = (request.getParameter("brandId") == null
				|| "undefined".equals(request.getParameter("brandId"))) ? "" : request.getParameter("brandId");

		StructureProductParameter sParameter = new StructureProductParameter();
		sParameter.setBrandIdStr(brandIdStr);
		sParameter.setProductTypeValue(productTypeValue);
		sParameter.setMediaType(mediaType);

		StructureProductParameter ssp = userProductCollectService.jsplist(userProductCollectSearch, sParameter);

		String userProductsConllectList = JsonUtil.getListToJsonData(ssp.getUpcList());
		if (("").equals(userProductsConllectList)) {
			List<UserProductPlan> smallList = new ArrayList<UserProductPlan>();
			return new ResponseEnvelope<UserProductPlan>(ssp.getTotal(), smallList);
		}
		List<UserProductsConllect> smallList = new JsonDataServiceImpl<UserProductsConllect>()
				.getJsonToBeanList(userProductsConllectList, UserProductsConllect.class);

		ResponseEnvelope<UserProductCollect> res = new ResponseEnvelope<UserProductCollect>(ssp.getTotal(), ssp.getLists());
		request.setAttribute("list", ssp.getLists());
		request.setAttribute("res", res);
		request.setAttribute("search", userProductCollectSearch);

		return new ResponseEnvelope<UserProductsConllect>(ssp.getTotal(), smallList);
	}

	/**
	 * 通过品牌类型查询产品---list
	 */
	@RequestMapping(value = "/getProductListByBrandType")
	public Object getProductListByBrandType(@ModelAttribute("baseBrandSearch") BaseBrandSearch baseBrandSearch,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		List<BaseBrand> list = new ArrayList<BaseBrand>();
		List<BaseProduct> baseProductlist = new ArrayList<BaseProduct>();
		List<UserProductsConllect> upcList = new ArrayList<UserProductsConllect>();
		UserProductsConllect userProductsConllect = new UserProductsConllect();
		BaseProductSearch baseProducttSearch = new BaseProductSearch();

		int total = 0;
		try {
			total = baseBrandService.getCount(baseBrandSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = baseBrandService.getPaginatedList(baseBrandSearch);
				if (CustomerListUtils.isNotEmpty(list)) {
					for (int i = 0; i < list.size(); i++) {
						baseProducttSearch.setBrandId(list.get(i).getId());
						baseProductlist = baseProductService.getPaginatedList(baseProducttSearch);
						for (BaseProduct baseProduct : baseProductlist) {
							userProductsConllect.setProductId(baseProduct.getId().toString());
							userProductsConllect.setPicId(baseProduct.getPicId());
							userProductsConllect.setBrandId(baseProduct.getBrandId());
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseBrand>(false, SystemCommonConstant.DATA_EXCEPTION);
		}

		ResponseEnvelope<BaseBrand> res = new ResponseEnvelope<BaseBrand>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("totalSize", total);
		request.setAttribute("search", baseBrandSearch);

		return Utils.getPageUrl(request, JSPMAIN + "/baseBrand_list");
	}

	@RequestMapping(value = "/getNamelist")
	@ResponseBody
	public Object getNamelist(
			@ModelAttribute("userProductCollectSearch") UserProductCollectSearch userProductCollectSearch,
			HttpServletRequest request, HttpServletResponse response) {

		List<BaseBrand> brandNameList = new ArrayList<BaseBrand>();
		brandNameList = baseBrandService.getList(new BaseBrand());
		long total = 0;

		request.setAttribute("search", userProductCollectSearch);
		String baseBrandList = JsonUtil.getListToJsonData(brandNameList);
		List<BaseBrand> smallList = new JsonDataServiceImpl<BaseBrand>().getJsonToBeanList(baseBrandList,
				BaseBrand.class);
		return new ResponseEnvelope<BaseBrand>(total, smallList);
	}

	/**
	 * 删除我的产品收藏,支持批量删除，传递ids=1,2,3格式即可
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(@PathVariable String style,
			@ModelAttribute("userProductCollect") UserProductCollect userProductCollect, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			userProductCollect = (UserProductCollect) JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
			if (userProductCollect == null) {
				return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		String ids = userProductCollect.getIds();
		String msgId = userProductCollect.getMsgId();
		int i = 0;
		if (ids != null) {
			if (ids.contains(",")) {
				// 把带,的String传给Utils里转成List<String>数据,然后再转成List<Integer>类型进行批量删除
				i = userProductCollectService.deleteBatch(Utils.StringToIntegerLst(Utils.getListFromStr(ids)));
				logger.info("批量delete:" + i + "条记录");
			} else {
				Integer id = new Integer(ids);
				i = userProductCollectService.delete(id);
				logger.info("delete:id=" + id);
			}
		}
		if (i == 0) {
			return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.RECORD_DOES_NOT_EXIST, msgId);
		}
		return new ResponseEnvelope<UserProductCollect>(true, msgId, true);
	}

	/**
	 * 产品收藏删除-----接口
	 * 
	 * @param style
	 * @param usedProducts
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delUserProduct")
	@ResponseBody
	public Object delUserProduct(@PathVariable String style,
			@ModelAttribute("userProductCollect") UserProductCollect userProductCollect, HttpServletRequest request) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String jsonStr = Utils.getJsonStr(request);
		if (jsonStr != null && jsonStr.trim().length() > 0) {
			userProductCollect = (UserProductCollect) JsonUtil.getJsonToBean(jsonStr, UserProductCollect.class);
			if (userProductCollect.getId() == null) {
				return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
			}
		}
		if (userProductCollect.getProductId() == null) {
			return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.PRODUCTID_PARAMETERS, userProductCollect.getMsgId());
		}
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if (list != null && list.size() > 0) {
			int flag = userProductCollectService.delete(list.get(0).getId());
			if (flag == 1) {
				UserProductCollectCacher.remove(list.get(0).getId());
				ProductCategoryRelCacher.remove(1);
				return new ResponseEnvelope<UserProductCollect>(true, userProductCollect.getMsgId(), true);
			} else {
				return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.DATA_DELETE_FAILED, userProductCollect.getMsgId());
			}
		} else {
			return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.EXIST_OR_HAS_BEEN_DELETED, userProductCollect.getMsgId());
		}
	}

	/**
	 * 保存 我的产品收藏
	 */
	@RequestMapping(value = "/saveProducts")
	@ResponseBody
	public Object saveProducts(@PathVariable String style,
			@ModelAttribute("userProductCollect") UserProductCollect userProductCollect, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (userProductCollect.getProductId() == null) {
			return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.PARAM_EXCEPTION, "none");
		}
		// 验证用户是否已收藏过该商品
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if (list != null && list.size() > 0) {
			return new ResponseEnvelope<UserProductCollect>(false, SystemCommonConstant.HAVE_TO_COLLECT_THE_GOODS, userProductCollect.getMsgId());
		}
		sysSave(userProductCollect, request);
		if (userProductCollect.getId() == null) {
			int id = userProductCollectService.add(userProductCollect);
			logger.info("add:id=" + id);
			userProductCollect.setId(id);
		} else {
			int id = userProductCollectService.update(userProductCollect);
			logger.info("update:id=" + id);
		}
		if ("small".equals(style)) {
			String userProductCollectJson = JsonUtil.getBeanToJsonData(userProductCollect);
			UserProductCollectSmall userProductCollectSmall = new JsonDataServiceImpl<UserProductCollectSmall>()
					.getJsonToBean(userProductCollectJson, UserProductCollectSmall.class);
			return new ResponseEnvelope<UserProductCollectSmall>(userProductCollectSmall, userProductCollect.getMsgId(),
					true);
		}
		return new ResponseEnvelope<UserProductCollect>(true, "收藏成功!", userProductCollect.getMsgId());
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UserProductCollect model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
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
	 * 品牌馆--我的收藏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myCollectProduct")
	public Object myCollectProduct(
			@ModelAttribute(value = "userProductsCollect") UserProductsConllect userProductsCollect,
			HttpServletRequest request) {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		userProductsCollect.setUserId(loginUser.getId());
		List<UserProductsConllect> list = userProductCollectService.getUserProductsConllectList(userProductsCollect);
		request.setAttribute("list", list);

		SysDictionary sysDic = new SysDictionary();
		sysDic.setType("productType");
		List<SysDictionary> productTypeList = sysDictionaryService.getList(sysDic);
		request.setAttribute("productTypeList", productTypeList);

		if (StringUtils.isNotBlank(userProductsCollect.getProductTypeValue())) {
			SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue("productType",
					Integer.valueOf(userProductsCollect.getProductTypeValue()));
			sysDic.setType(sysDictionary.getValuekey());
			List<SysDictionary> productSmallTypeList = sysDictionaryService.getList(sysDic);
			request.setAttribute("productSmallTypeList", productSmallTypeList);
		}

		request.setAttribute("userProductsCollect", userProductsCollect);
		List<BaseBrand> brandList = baseBrandService.getList(new BaseBrand());
		request.setAttribute("brandList", brandList);
		return Utils.getPageUrl(request, "/online/product/baseBrand/myCollect");
	}

	/**
	 * 用户中心--产品收藏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myCollects")
	public Object myCollects(@ModelAttribute(value = "userProductsCollect") UserProductsConllect userProductsCollect,
			HttpServletRequest request) {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		userProductsCollect.setUserId(loginUser.getId());
		List<UserProductsConllect> list = userProductCollectService.getAllList(userProductsCollect);
		String modelShow = "pDetails";
		String value = request.getParameter("value") == null ? "" : request.getParameter("value");
		request.setAttribute("value", value);
		request.setAttribute("type", modelShow);
		request.setAttribute("list", list);

		return Utils.getPageUrl(request, "/online/user/myCollections");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectionValue")
	public Object selectionValue(
			@ModelAttribute(value = "userProductsCollect") UserProductsConllect userProductsCollect,
			HttpServletRequest request) {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		String value = request.getParameter("value") == "" ? null : request.getParameter("value");
		userProductsCollect.setUserId(loginUser.getId());
		userProductsCollect.setProductTypeValue(value);
		List<UserProductsConllect> list = userProductCollectService.getAllList(userProductsCollect);
		request.setAttribute("list", list);

		return Utils.getPageUrl(request, "/online/user/myCollections");
	}

	/**
	 * 用户中心--产品收藏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myProductCollects")
	public Object myProductCollects(
			@ModelAttribute(value = "userProductsCollect") UserProductsConllect userProductsCollect,
			HttpServletRequest request) {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		List<UserProductsConllect> list = new ArrayList<UserProductsConllect>();
		int total = 0;
		userProductsCollect.setUserId(loginUser.getId());
		total = userProductCollectService.getUserProductsConllectCount(userProductsCollect);
		if (total > 0) {
			list = userProductCollectService.getUserProductsConllectList(userProductsCollect);
		}
		request.setAttribute("list", list);

		return Utils.getPageUrl(request, "/online/user/myProductCollections");
	}

	/**
	 * 产品收藏列表----接口
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/collectionList")
	@ResponseBody
	public Object collectionList(@PathVariable String style,
			@ModelAttribute(value = "userProductsCollect") UserProductsConllect userProductsCollect,
			Integer designPlanId, Integer spaceCommonId, HttpServletRequest request) {
		/** 获取登录用户 */
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		StructureProductParameter ssp = new StructureProductParameter();
		try {
			String msg = "";
			String cacheEnable = Utils.getValue(SystemCommonConstant.REDIS_CACHE_ENABLE,
					SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE);
			UserProductCollect userProductCollect = new UserProductCollect();

			if (StringUtils.isBlank(userProductsCollect.getMsgId())) {
				msg = "参数msgId不能为空";
				return new ResponseEnvelope<UserProductsConllect>(false, msg, userProductsCollect.getMsgId());
			}
			StructureProductParameter sParameter = new StructureProductParameter();
			sParameter.setDesignPlanId(designPlanId);
			sParameter.setSpaceCommonId(spaceCommonId);
			sParameter.setLoginUser(loginUser);
			sParameter.setCacheEnable(cacheEnable);
			sParameter.setRequest(request);

			ssp = userProductCollectService.collectionList(userProductsCollect, sParameter);

			if ("small".equals(style) && ssp.getUpcList() != null && ssp.getUpcList().size() > 0) {
				String userProductsConllectJsonList = JsonUtil.getListToJsonData(ssp.getUpcList());
				List<UserProductsConllect> smallList = new JsonDataServiceImpl<UserProductsConllect>()
						.getJsonToBeanList(userProductsConllectJsonList, UserProductsConllect.class);
				return new ResponseEnvelope<UserProductsConllect>(ssp.getTotal(), smallList,
						userProductsCollect.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<UserProductsConllect>(false, "数据异常!", userProductsCollect.getMsgId());
		}
		return new ResponseEnvelope<UserProductsConllect>(ssp.getTotal(), ssp.getUpcList(), userProductsCollect.getMsgId());
	}

	/**
	 * 取消产品收藏
	 */
	@RequestMapping(value = "/cancelCollect")
	@ResponseBody
	public Object cancelCollect(@PathVariable String style,
			@ModelAttribute("userProductCollect") UserProductCollect userProductCollect, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取登录用户
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollect.setUserId(loginUser.getId());
		userProductCollectSearch.setUserId(loginUser.getId());
		int total = userProductCollectService.getCount(userProductCollectSearch);
		if (userProductCollect != null && userProductCollect.getUserId() != null
				&& userProductCollect.getProductId() != null) {
			Integer i = userProductCollectService.cancelConllect(userProductCollect);
			if (i != null && new Integer(i).intValue() > 0) {
				userProductCollectService.delete(i);
			}
			logger.info("delete:id=" + i);
		}
		if (total == 0) {
			return new ResponseEnvelope<UserProductCollect>(false, "记录不存在!", userProductCollect.getMsgId());
		}
		return new ResponseEnvelope<UserProductCollect>(true, userProductCollect.getMsgId(), true);
	}

	/**
	 * 保存我的产品收藏
	 * 
	 * @param style
	 * @param userProductCollect
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveProduct")
	@ResponseBody
	public Object saveProduct(@PathVariable String style,
			@ModelAttribute("userProductCollect") UserProductCollect userProductCollect, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		Integer userId = 0;
		String msg = "";
		if (StringUtils.isBlank(userProductCollect.getMsgId()) || userProductCollect.getUserId() == null
				|| userProductCollect.getUserId() == 0 || userProductCollect.getProductId() == null) {
			msg = "关键参数不能为空";
			return new ResponseEnvelope<UserProductCollectSmall>(false, msg, userProductCollect.getMsgId());
		}
		if (userProductCollect.getProductId() != null) {
			BaseProduct bp = new BaseProduct();
			bp = baseProductService.get(userProductCollect.getProductId());
			if (bp == null) {
				msg = "该产品不存在";
				return new ResponseEnvelope<UserProductCollectSmall>(false, msg, userProductCollect.getMsgId());
			}
		}
		userProductCollect.setUserId(userId);
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		if (CustomerListUtils.isNotEmpty(list)) {
			msg = "该产品已收藏，请查看收藏夹！";
			return new ResponseEnvelope<UserProductCollectSmall>(false, msg, userProductCollect.getMsgId());
		}
		sysSave(userProductCollect, request);
		if (userProductCollect.getId() == null) {
			int id = userProductCollectService.add(userProductCollect);
			logger.info("add:id=" + id);
			userProductCollect.setId(id);
		} else {
			int id = userProductCollectService.update(userProductCollect);
			logger.info("update:id=" + id);
		}
		UserProductCollectCacher.remove(userProductCollect.getId());
		if ("small".equals(style)) {
			String message = "ok";
			return new ResponseEnvelope<UserProductCollect>(true, message, userProductCollect.getMsgId());
		}
		return new ResponseEnvelope<UserProductCollect>(userProductCollect, userProductCollect.getMsgId(), true);
	}

	/**
	 * 我的产品收藏全部列表
	 */
	/*
	 * @RequestMapping(value = "/userProductList")
	 * 
	 * @ResponseBody public Object userProductList(@PathVariable String style,
	 * 
	 * @ModelAttribute("userProductCollect") UserProductCollect
	 * userProductCollect, HttpServletRequest request, HttpServletResponse
	 * response) throws Exception { String jsonStr = Utils.getJsonStr(request);
	 * if (jsonStr != null && jsonStr.trim().length() > 0) { userProductCollect
	 * = (UserProductCollect) JsonUtil.getJsonToBean(jsonStr,
	 * UserProductCollect.class); if (userProductCollect == null) { return new
	 * ResponseEnvelope<UserProductCollect>(false, "传参异常!", "none"); } }
	 * List<BaseProduct> userProductList = new ArrayList<BaseProduct>();
	 * List<UserProductCollect> list = new ArrayList<UserProductCollect>(); list
	 * = userProductCollectService.getList(userProductCollect); if (list.size()
	 * > 0) { for (UserProductCollect userProduct : list) { BaseProduct product
	 * = baseProductService.get(userProduct.getProductId()); if (product !=
	 * null) { product.setBrandName(product.getBrandId() == null ? "" :
	 * baseBrandService.get(product.getBrandId()) == null ? "" :
	 * baseBrandService.get(product.getBrandId()).getBrandName());
	 * product.setProductStyle(product.getProStyleId() == null ? "" :
	 * sysDictionaryService.getSysDictionaryByValue("productStyle",
	 * product.getProStyleId()).getName());
	 * product.setProductType(StringUtils.isEmpty(product.getProductTypeValue())
	 * ? "" : sysDictionaryService.getSysDictionaryByValue("productType", new
	 * Integer(product.getProductTypeValue())).getName());
	 * product.setProductPicPath(product.getPicId() == 0 ? "" :
	 * resPicService.get(product.getPicId()) == null ? "" :
	 * resPicService.get(product.getPicId()).getPicPath()); String houseTypeName
	 * = ""; if (StringUtils.isNotBlank(product.getHouseTypeValues())) {
	 * String[] arr = product.getHouseTypeValues().split(","); for (int j = 0; j
	 * < arr.length; j++) { if (StringUtils.isNotBlank(arr[j])) { houseTypeName
	 * += sysDictionaryService.getSysDictionaryByValue("houseType",
	 * Integer.valueOf(arr[j])).getName(); if ((j + 1) < arr.length) {
	 * houseTypeName += ","; } } } } product.setHouseType(houseTypeName); }
	 * userProductList.add(product); } } if ("small".equals(style) && list !=
	 * null && list.size() > 0) { String userProductCollectJsonList =
	 * JsonUtil.getListToJsonData(list); List<UserProductCollectSmall> smallList
	 * = new JsonDataServiceImpl<UserProductCollectSmall>()
	 * .getJsonToBeanList(userProductCollectJsonList,
	 * UserProductCollectSmall.class); return new
	 * ResponseEnvelope<UserProductCollectSmall>(smallList,
	 * userProductCollect.getMsgId()); } return new
	 * ResponseEnvelope<BaseProduct>(userProductList,
	 * userProductCollect.getMsgId()); }
	 */
}
