package com.nork.product.controller.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.product.model.ProductRecommendation;
import com.nork.product.model.RecommendProductResult;
import com.nork.product.model.search.ProductRecommendationSearch;
import com.nork.product.service.ProductRecommendationService;

/**   
 * @Title: ProductRecommendationController.java 
 * @Package com.nork.product.controller
 * @Description:产品-产品推荐Controller
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 14:02:16
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/product/productRecommendation")
public class WebProductRecommendationController {
	private static Logger logger = Logger
			.getLogger(WebProductRecommendationController.class);
	private final JsonDataServiceImpl<ProductRecommendation> JsonUtil = new JsonDataServiceImpl<ProductRecommendation>();
	private final String STYLE="online";
	private final String JSPSTYLE="online";
	private final String MAIN = "/"+ STYLE + "/product/productRecommendation";
	private final String BASEMAIN = "/"+ STYLE + "/product/base/productRecommendation";
	private final String JSPMAIN = "/"+ JSPSTYLE + "/product/productRecommendation";
	
	@Autowired
	private ProductRecommendationService productRecommendationService;

	@Autowired
	private DesignPlanService designPlanService;

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 查询样板间推荐产品
	 * @return
	 */
	@RequestMapping("/designTemplateRecommend")
	@ResponseBody
	public Object designTemplateRecommend(@ModelAttribute(value = "productRecommendationSearch")ProductRecommendationSearch productRecommendationSearch){
		if( productRecommendationSearch == null ){
			return new ResponseEnvelope<RecommendProductResult>(false,"参数为空",productRecommendationSearch.getMsgId());
		}
		if( productRecommendationSearch.getDesignTempletId() == null ){
			return new ResponseEnvelope<RecommendProductResult>(false,"样板房id为空",productRecommendationSearch.getMsgId());
		}
		List<RecommendProductResult> list = null;
		int total = 0 ;
		try {
			DesignPlan designPlan = designPlanService.get(productRecommendationSearch.getDesignTempletId());
			productRecommendationSearch.setDesignTempletId(designPlan.getDesignTemplateId());
			total = productRecommendationService.getCount(productRecommendationSearch);
			if(total > 0 ){
				list = productRecommendationService.getRecommendProduct(productRecommendationSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<RecommendProductResult>(false, "数据异常!");
		}
		return new ResponseEnvelope<RecommendProductResult>(total,list,productRecommendationSearch.getMsgId());
	}
}
