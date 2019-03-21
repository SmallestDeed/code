package com.nork.product.controller2.web;

 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.product.model.search.ProductRecommendationSearch;
import com.nork.product.service2.ProductRecommendationService;

/**
 * @Title: ProductRecommendationController.java
 * @Package com.nork.product.controller
 * @Description:产品-产品推荐Controller
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 14:02:16
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/product/productRecommendation2")
public class WebProductRecommendationController {
	private static Logger logger = Logger.getLogger(WebProductRecommendationController.class);
	@Autowired
	private ProductRecommendationService productRecommendationService2;
	//日期类型的属性转换
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 查询样板间推荐产品
	 * 
	 * @return
	 */
	@RequestMapping("/designTemplateRecommend")
	@ResponseBody
	public Object designTemplateRecommend( @ModelAttribute(value = "productRecommendationSearch") ProductRecommendationSearch productRecommendationSearch) {
		return productRecommendationService2.designTemplateRecommend(productRecommendationSearch);
	}
}
