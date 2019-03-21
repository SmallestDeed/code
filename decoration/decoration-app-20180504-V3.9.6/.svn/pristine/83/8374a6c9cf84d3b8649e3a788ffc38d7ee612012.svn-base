package com.nork.product.controller2.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service2.BaseBrandService;


/**
 * @Title: WebBaseBrandController.java
 * @Package com.nork.product.controller2.web
 * @Description:产品-品牌表Controller
 * @createAuthor yangzhun
 * @CreateDate 2017-06-08 09:23:47
 */
@Controller
@RequestMapping("{style}/web/product/baseBrand2")
public class WebBaseBrandController {
	private final String JSPSTYLE = "online";
	private final String JSPMAIN = "/" + JSPSTYLE + "/product/baseBrand2";

	@Autowired
	private BaseBrandService baseBrandService2;
	
	/**
	 * 根据产品种类 查询所有产品品牌表
	 * @param style
	 * @param brandName
	 * @param brandStyleId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	@RequestMapping(value = "/listAllByPara")
	@ResponseBody
	public Object listAllByPara(@PathVariable String style,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "brandStyleId", required = false) String brandStyleId,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) throws Exception {
		
		return baseBrandService2.listAllByPara(style,brandName,brandStyleId,msgId,limit,start);
	}
	
	
	/**
	 * 品牌表全部列表
	 * @param style
	 * @param brandName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public Object listAll(@PathVariable String style,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "msgId", required = false) String msgId,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "start", required = false) String start) throws Exception {

		return baseBrandService2.listAll(style,brandName, msgId,limit,start);
		//return baseBrandService2.listAll(style,msgId,limit,start);
	}

	
	/**
	 * 品牌表列表---jsp
	 * @param baseBrandSearch
	 * @return
	 */
	@RequestMapping(value = "/jsplist")
	public Object jsplist(@ModelAttribute("baseBrandSearch") BaseBrandSearch baseBrandSearch,
			HttpServletRequest request, HttpServletResponse response) {
		
		ResponseEnvelope<BaseBrand> res = baseBrandService2.jsplist(baseBrandSearch);
		request.setAttribute("list", res.getDatalist());
		request.setAttribute("res", res);
		request.setAttribute("totalSize", res.getTotalCount());
		request.setAttribute("search", baseBrandSearch);
		return Utils.getPageUrl(request,JSPMAIN + "/baseBrand_list");//原來的controller中沒有“/baseBrand_list”接口
		//return baseBrandService2.jspList(baseBrandSearch);
	}
	
	/**
	 * 根据品牌ID查询产品列表
	 * @param request
	 * @param baseProduct
	 * @return
	 */
	@RequestMapping(value = "/searchProducts")
	public Object searchProducts(HttpServletRequest request, @ModelAttribute("baseProduct") BaseProduct baseProduct) {
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		
		List<CategoryProductResult> baseProducts = baseBrandService2.searchProducts(baseProduct,loginUser);
		request.setAttribute("products", baseProducts);
		return Utils.getPageUrl(request, JSPMAIN + "/brandProduct_list");//原來的controller中沒有“/baseProduct_list”接口
	}
	
	/**
	 * @param baseBrand
	 * @return
	 */
	@RequestMapping("/brandList")
	@ResponseBody
	public Object brandList(@ModelAttribute(value = "baseBrand") BaseBrand baseBrand) {
		/*List<BaseBrand> list = baseBrandService2.getList(baseBrand);
		return new ResponseEnvelope<BaseBrand>(list, null);*/
		return baseBrandService2.brandList(baseBrand);
	}
	
}
