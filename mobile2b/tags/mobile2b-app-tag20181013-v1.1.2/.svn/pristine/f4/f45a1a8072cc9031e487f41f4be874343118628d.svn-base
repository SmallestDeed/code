package com.nork.product.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.util.collections.Lists;
import com.nork.product.model.*;
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
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.home.model.SpaceCommon;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.small.BaseBrandSmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.BaseProductService;
import com.nork.system.service.ResPicService;
import sun.rmi.runtime.Log;

/**
 * @Title: BaseBrandController.java
 * @Package com.nork.product.controller
 * @Description:产品-品牌表Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-16 10:03:47
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/baseBrand")
public class WebBaseBrandController {
	private static Logger logger = Logger.getLogger(WebBaseBrandController.class);
	private final JsonDataServiceImpl<BaseBrand> JsonUtil = new JsonDataServiceImpl<BaseBrand>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/product/baseBrand";
	private final String BASEMAIN = "/" + STYLE + "/product/base/baseBrand";
	private final String JSPMAIN = "/" + JSPSTYLE + "/product/baseBrand";

	//private final String PIC_UPLOAD_PATH = "product.baseBrand.logo.upload.path";

	@Autowired
	private BaseBrandService baseBrandService;

	@Autowired
	private BaseCompanyService baseCompanyService;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private AuthorizedConfigService authorizedConfigService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 品牌表全部列表
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) throws Exception {

		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		BaseBrandSearch baseBrandSearch = new BaseBrandSearch();
		List<BaseBrand> list = new ArrayList<BaseBrand>();
		int total = 0;
		try {
			/** 设置查询条件 */
			/**
			 * if (StringUtils.isNotBlank(brandName)) {
			 * baseBrandSearch.setSch_BrandName_(brandName); }
			 */
			if (StringUtils.isNotBlank(start)) {
				baseBrandSearch.setStart(Integer.parseInt(start));
			}
			if (StringUtils.isNotBlank(limit)) {
				baseBrandSearch.setLimit(Integer.parseInt(limit));
			}
			if(Utils.enableRedisCache()){
				total = BaseBrandCacher.getBaseBrandTotal(baseBrandSearch);
			}else{
				total = baseBrandService.getCount(baseBrandSearch);
			}
			
			logger.info("total:" + total);
			if (total > 0) {
				if(Utils.enableRedisCache()){
					list = BaseBrandCacher.getPaginatedList(baseBrandSearch);
				}else{
					list = baseBrandService.getPaginatedList(baseBrandSearch);
				}

			}

			if ("small".equals(style) && list != null && list.size() > 0) {
				String baseBrandJsonList = JsonUtil.getListToJsonData(list);
				List<BaseBrandSmall> smallList = new JsonDataServiceImpl<BaseBrandSmall>()
						.getJsonToBeanList(baseBrandJsonList, BaseBrandSmall.class);
				return new ResponseEnvelope<BaseBrandSmall>(total, smallList, baseBrandSearch.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseBrand>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<BaseBrand>(total, list, msgId);
	}

	/**
	 * 品牌表全部列表
	 */
	@RequestMapping(value = "/listAllByPara")
	@ResponseBody
	public Object listAllByPara(
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "brandStyleId", required = false) String brandStyleId,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start,
								HttpServletRequest request) throws Exception {
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = "参数msgId不能为空";
			return new ResponseEnvelope<SpaceCommon>(false, msg, msgId);
		}
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (null == loginUser) {
			return new ResponseEnvelope<SpaceCommon>(false, "user is null", msgId);
		}
//		LoginUser loginUser = new LoginUser();
//		loginUser.setId(2);
//		loginUser.setUserType(3);
		List<BaseBrand> newList = new ArrayList<BaseBrand>();
		BaseBrandSearch baseBrandSearch = this.setBaseBrandSerachAttribute(loginUser.getId(), brandName, brandStyleId, limit, start);
		int total = baseBrandService.getAllBrandInfoCount(baseBrandSearch);
		if (total > 0) {
			newList = baseBrandService.listAllByPara(baseBrandSearch);
		}
		//TODO : The App should use the field 'logopath', Because the brandlogo save the id of logo path.
		for(BaseBrand baseBrand : newList) {
			baseBrand.setBrandLogo(baseBrand.getBrandLogoPath());
		}
		return new ResponseEnvelope<BaseBrand>(total, newList, msgId);
	}


	/**
	 * 设置品牌搜索对象属性值
	 * @param userId
	 * @param brandName
	 * @param brandStyleId
	 * @param limit
	 * @param start
	 * @return
	 */
	private BaseBrandSearch setBaseBrandSerachAttribute(Integer userId,
							String brandName, String brandStyleId,String limit, String start){

		BaseBrandSearch baseBrandSearch = new BaseBrandSearch();
		if (StringUtils.isNotBlank(brandName)) {
			baseBrandSearch.setSch_BrandName_(brandName);
		}
		if (StringUtils.isNotBlank(brandStyleId)) {
			baseBrandSearch.setBrandStyleId(Integer.parseInt(brandStyleId));
		}
		if (StringUtils.isNotBlank(start)) {
			baseBrandSearch.setStart(Integer.parseInt(start));
		}
		if (StringUtils.isNotBlank(limit)) {
			baseBrandSearch.setLimit(Integer.parseInt(limit));
		}

		//设置品牌行业值，用户过滤同行业品牌
		List<BaseBrand> brandList = baseBrandService.getBrandIndustryValueByUserId(userId);
		if (Lists.isNotEmpty(brandList)) {
			List<BrandIndustryModel> brandIndustryModelList = new ArrayList<>();
			for (BaseBrand baseBrand : brandList) {
				BrandIndustryModel brandIndustryModel = new BrandIndustryModel();
				brandIndustryModel.setBrandId(baseBrand.getId());
				brandIndustryModel.setIndustryValue(baseBrand.getCompanyIndustry());
				brandIndustryModel.setSmallTypeValue(baseBrand.getCompanySmallType());
				brandIndustryModelList.add(brandIndustryModel);
			}
			baseBrandSearch.setBrandIndustryModelList(brandIndustryModelList);
		}

		return baseBrandSearch;
	}

	/**
	 * 品牌表列表---jsp
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(@ModelAttribute("baseBrandSearch") BaseBrandSearch baseBrandSearch,
			HttpServletRequest request, HttpServletResponse response) {
		if (baseBrandSearch.getLimit() == null || baseBrandSearch.getLimit() == 20) {
			baseBrandSearch.setLimit(18);
		}
		List<BaseBrand> list = new ArrayList<BaseBrand>();
		int total = 0;
		try {
			total = baseBrandService.getCount(baseBrandSearch);
			logger.info("total:" + total);

			if (total > 0) {
				list = baseBrandService.getPaginatedList(baseBrandSearch);
				if (CustomerListUtils.isNotEmpty(list)) {
					for (int i = 0; i < list.size(); i++) {

						BaseBrand baseBrand = list.get(i);

						BaseCompany baseCompany = baseCompanyService.get(baseBrand.getCompanyId());

						baseBrand.setCompanyName(baseCompany == null ? "" : baseCompany.getCompanyName());
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseBrand>(false, "数据异常!");
		}

		ResponseEnvelope<BaseBrand> res = new ResponseEnvelope<BaseBrand>(total, list);
		request.setAttribute("list", list);
		request.setAttribute("res", res);
		request.setAttribute("totalSize", total);
		request.setAttribute("search", baseBrandSearch);

		return Utils.getPageUrl(request, JSPMAIN + "/baseBrand_list");
		// return Utils.getPageUrl(request, JSPMAIN + "/brandBrand");
	}

	/**
	 * 根据品牌ID查询产品列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchProducts")
	public Object searchProducts(HttpServletRequest request, @ModelAttribute("baseProduct") BaseProduct baseProduct) {
		LoginUser loginUser = new LoginUser();
		if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			loginUser.setId(-1);
			loginUser.setLoginName("nologin");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}

		baseProduct.setOrders(" bp.gmt_modified desc ");
		// 如果是厂商，则只能查询这个厂商品牌下的产品
		if (3 == loginUser.getUserType()) {
			if (baseProduct.getBrandId() != null) {
				if (loginUser.getBrandIds().indexOf(baseProduct.getBrandId().toString()) < 0) {
					baseProduct.setBrandId(-9999);
				}
			}
			if (StringUtils.isNotBlank(loginUser.getBrandIds())) {
				String[] brandIds = loginUser.getBrandIds().split(",");
				baseProduct.setBrandIds(Arrays.asList(brandIds));
			}
		}
		baseProduct.setUserId(loginUser.getId());
		/* 序列号配置过滤 */
		Map<String, List<String>> map = authorizedConfigService.getConfigParams(loginUser.getId());
		baseProduct.setConfigBrandIdList(map.get("brandIdList"));
		baseProduct.setConfigProductIdList(map.get("productIdList"));
		baseProduct.setConfigSmallTypeIdList(map.get("smallTypeIdList"));
		baseProduct.setConfigTypeValueList(map.get("typeValueList"));
		/* 序列号配置过滤END */
		List<CategoryProductResult> baseProducts = baseProductService.selectProductByNameAndBrandId(baseProduct);
		request.setAttribute("products", baseProducts);
		return Utils.getPageUrl(request, JSPMAIN + "/brandProduct_list");
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping("/brandList")
	@ResponseBody
	public Object brandList(@ModelAttribute(value = "baseBrand") BaseBrand baseBrand) {
		List<BaseBrand> list = baseBrandService.getList(baseBrand);
		return new ResponseEnvelope<BaseBrand>(list, null);
	}
}
