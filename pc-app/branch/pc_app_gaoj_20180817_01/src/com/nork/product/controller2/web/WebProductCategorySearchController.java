package com.nork.product.controller2.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ProductCategoryVO;
import com.nork.common.util.Utils;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service2.ProductCategoryRelService;

/**
 * @version V1.0
 * @Title: ProductCategoryRelController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品与产品类目关联Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Controller
@RequestMapping("/{style}/web/product/productCategorySearch2")
public class WebProductCategorySearchController {
	private static Logger logger = Logger.getLogger(WebProductCategorySearchController.class);
	private final JsonDataServiceImpl<ProductCategoryRel> JsonUtil = new JsonDataServiceImpl<ProductCategoryRel>();
	private final String STYLE = "online";
	private final String JSPSTYLE = "online";
	private final String MAIN = "/" + STYLE + "/product/proCategory";
	private final String BASEMAIN = "/" + STYLE + "/product/proCategory";
	private final String JSPMAIN = "/" + JSPSTYLE + "/product/proCategory";

	@Autowired
	private ProductCategoryRelService productCategoryRelService2;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 根据分类查询产品
	 *
	 * @param request
	 * @param productCategoryRel
	 * @return
	 */
	@RequestMapping(value = "/searchProduct")
	public Object searchProduct(HttpServletRequest request,
			@ModelAttribute("productCategoryRel") ProductCategoryRel productCategoryRel,
			ProductCategoryVO productCategoryVO, @RequestParam(value = "houseType", required = false) String houseType,
			@RequestParam(value = "designPlanId", required = false) Integer designPlanId,
			@RequestParam(value = "planProductId", required = false) Integer planProductId,
			@RequestParam(value = "spaceCommonId", required = false) Integer spaceCommonId,
			@RequestParam(value = "templateProductId", required = false) String templateProductId,
			@RequestParam(value = "productTypeValue", required = false) String productTypeValue,
			@RequestParam(value = "smallTypeValue", required = false) String smallTypeValue,
			@RequestParam(value = "queryType", required = false) String queryType) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		// 媒介类型.如果没有值，则表示为web前端（2）
		String mediaType = SystemCommonUtil.getMediaType(request);

		List<CategoryProductResult> list = productCategoryRelService2.searchProduct(productCategoryRel,
				productCategoryVO, loginUser, mediaType);
		request.setAttribute("list", list);
		return Utils.getPageUrl(request, JSPMAIN + "/product_list");
	}

	/**
	 * 查询所有分类
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/design")
	public Object design(HttpServletRequest request) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		if (session.getAttribute("CATEGORY_JSON") != null) {
		} else {
			SearchProCategorySmall allCategorySmall = productCategoryRelService2.getAllCategorySmall();
			JSONObject jsonObject = JSONObject.fromObject(allCategorySmall);
			session.setAttribute("CATEGORY", allCategorySmall);
			session.setAttribute("CATEGORY_JSON_DATA", URLEncoder.encode(jsonObject.toString(), "UTF-8"));
		}
		return Utils.getPageUrl(request, JSPSTYLE + "/decorate/design");
	}

	/**
	 * 查询分类1
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/searchCategory1")
	public Object searchCategory1(HttpServletRequest request) throws UnsupportedEncodingException {
		logger.info("分类查询。。。");
		HttpSession session = request.getSession();
		if (session.getAttribute("CATEGORY") == null) {
			SearchProCategorySmall categorySmall1 = productCategoryRelService2.getCategorySmall1();
			JSONObject jsonObject = JSONObject.fromObject(categorySmall1);
			session.setAttribute("CATEGORY", categorySmall1);
			session.setAttribute("CATEGORY_JSON_DATA", URLEncoder.encode(jsonObject.toString(), "UTF-8"));
		}
		return Utils.getPageUrl(request, JSPSTYLE + "/product/proCategory/categoryDetail");
	}

	/**
	 * 分类查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchCategory")
	public Object searchCategory(HttpServletRequest request) throws UnsupportedEncodingException {
		logger.info("分类查询。。。");
		HttpSession session = request.getSession();
		if (session.getAttribute("CATEGORY") == null) {
			SearchProCategorySmall categorySmall = productCategoryRelService2.getcategorySmall();
			JSONObject jsonObject = JSONObject.fromObject(categorySmall);
			session.setAttribute("CATEGORY", categorySmall);
			session.setAttribute("CATEGORY_JSON_DATA", URLEncoder.encode(jsonObject.toString(), "UTF-8"));
		}
		return Utils.getPageUrl(request, JSPSTYLE + "/product/proCategory/categoryDetail");
	}

	/**
	 * 获得分类查询
	 * 
	 * @param msgId
	 * @return
	 */
	@RequestMapping(value = "/getCategory")
	@ResponseBody
	public Object getCategory(String msgId) {
		return productCategoryRelService2.getCategory(msgId);
	}
}
