package com.nork.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanService;
import com.nork.mobile.model.search.MobileSearchProductModel;
import com.nork.mobile.model.search.SearchProCategory;
import com.nork.mobile.service.MobileSearchDesignPlanProductService;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

/**
 * 移动端 查询产品的controller
 * 
 * @CreateDate 2017年9月26日14:31:28
 * @author yangz
 */
@Controller
@RequestMapping("/{style}/mobile/searchProduct")
public class MobileSearchProductController {

	private static Logger logger = Logger.getLogger(MobileSearchProductController.class);
	private static final String TYPE_NOT_NULL = "参数type不能为空";
	private static final String MSGID_NOT_NULL = "参数msgId不能为空";
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ProductCategoryRelService productCategoryRelService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DesignPlanRenderSceneService designPlanRenderSceneService;
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private MobileSearchDesignPlanProductService mobileSearchDesignPlanProductService;

	/**
	 * 根据分类查询产品接口 主逻辑放在service
	 * 
	 * @param request
	 * @param model
	 * @return Object
	 */
	@RequestMapping(value = "/searchProduct")
	@ResponseBody
	public Object searchProductV6(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {

		String templateProductId = null;
		String productModelOrBrandName = null;
		String smallpox = null;
		
		Integer planProductId = model.getPlanProductId();
		Integer productId = model.getProductId();
		String houseType = model.getHouseType();
		Integer productTypeValue = model.getProductTypeValue();
		Integer smallTypeValue = model.getSmallTypeValue();
		Integer spaceCommonId = model.getSpaceCommonId();
		Integer designPlanId = model.getDesignPlanId();
		Integer isStandard = model.getIsStandard();
		String regionMark = model.getRegionMark();
		Integer styleId = model.getStyleId();
		String measureCode = model.getMeasureCode();
		Integer userId = model.getUserId();
		Integer limit = model.getLimit();
		Integer start = model.getStart();
		String msgId = model.getMsgId();
		String categoryCode = model.getCategoryCode();
		// 获取配置:决定是否走老接口还是新接口
		// 用户信息
		SysUser sysUser = sysUserService.get(userId);
		LoginUser loginUser = sysUser.toLoginUser();
		
		Integer userStatusType = loginUser.getUserType();
		DesignPlan designPlan = new DesignPlan();
		// 先測試是否可行，節后改造
		if ("design".equals(msgId)) {
			DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanId);
			if(designPlanRenderScene != null) {
				designPlan = designPlanRenderScene.designPlanCopy();
				logger.error("searchProductV6 designPlanRenderScene designPlanId ==>"+designPlanId);
			}else{
				designPlan = designPlanService.get(designPlanId);
				logger.error("searchProductV6 designPlanService designPlanId ==>"+designPlanId);
			}
		}else if("recommended".equals(msgId)){
			DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanId);
			if(designPlanRecommended != null) {
				designPlan = designPlanRecommended.recommendedCopy();
				logger.error("searchProductV6 designPlanRecommended designPlanId ==>"+designPlanId);
			}else{
				designPlan = designPlanService.get(designPlanId);
				logger.error("searchProductV6 designPlanService2 designPlanId ==>"+designPlanId);
			}
		}
		
		/*if (productId != null) {
			BaseProduct baseProduct = baseProductService.get(productId);
			if (baseProduct != null) {
				productTypeValue = Integer.valueOf(baseProduct.getProductTypeValue());
				smallTypeValue = baseProduct.getProductSmallTypeValue();
				categoryCode = baseProduct.getProductTypeMark();
				styleId = baseProduct.getStyleId();
			}
		}*/
		if (StringUtils.isBlank(categoryCode)) {
			return new ResponseEnvelope<ProductCategoryRel>(false, "参数categoryCode不能为空", model.getMsgId());
		}
		/*if (designPlan != null) {
			return new ResponseEnvelope<ProductCategoryRel>(false, "designPlan不能为空", model.getMsgId());
		}*/
		ProductCategoryRel productCategoryRel = new ProductCategoryRel();
		productCategoryRel.setCategoryCode(categoryCode);
		productCategoryRel.setLimit(limit);
		productCategoryRel.setStart(start);
		productCategoryRel.setMsgId(msgId);

		logger.error("param:designPlanId=" + designPlanId + ";productId=" + productId + ";spaceCommonId=" + spaceCommonId
				+ ";houseType=" + houseType + ";productTypeValue=" + productTypeValue + ";smallTypeValue="
				+ smallTypeValue + ";categoryCode=" + categoryCode + ";styleId=" + styleId);

		if (loginUser == null || loginUser.getId() == null || loginUser.getId() < 1) {
			return new ResponseEnvelope<ProductCategoryRel>(false, "未检测到登录信息,请登录", msgId);
		} // 参数验证 ->end

//		DesignPlan designPlan = new DesignPlan();
//		if (designPlanId != null && designPlanId != 0)
//			designPlan = designPlanService.get(designPlanId);
//		if (designPlan == null)
//			return new ResponseEnvelope<ProductCategoryRel>(false, "找不到该设计方案：" + designPlanId, msgId);

		ResponseEnvelope<CategoryProductResult> result = productCategoryRelService.searchProductV6(templateProductId,
				productCategoryRel, loginUser, productModelOrBrandName, planProductId, houseType, productTypeValue,
				spaceCommonId, designPlan, smallTypeValue, request, userStatusType, isStandard, regionMark, styleId,
				measureCode, smallpox);
		return result;
	}
	
	
	/**
	 * 分类查询接口供移动端调用
	 * @return
	 */
	@RequestMapping(value = "/searchProCategory")
	@ResponseBody
	public Object searchProCategory(@RequestBody SearchProCategory small){
		String type = small.getType();
		Integer cid = small.getCid();
		String msgId = small.getMsgId();
				String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = MSGID_NOT_NULL;
			return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
		}
		if (StringUtils.isBlank(type)) {
			msg = TYPE_NOT_NULL;
			return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
		}
		return mobileSearchDesignPlanProductService.searchProCategory(type, cid, msgId);
	}
	
}
