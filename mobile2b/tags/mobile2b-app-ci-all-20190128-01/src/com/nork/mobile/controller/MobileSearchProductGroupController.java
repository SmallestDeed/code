package com.nork.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.mobile.model.search.MobileSearchProductModel;
import com.nork.mobile.service.MobileSearchProductGroupService;
import com.nork.product.model.BaseProduct;

/**
 * 移动端 查询组合的controller
 * 
 * @CreateDate 2017年10月26日15:29:39
 * @author yangzhun
 */
@Controller
@RequestMapping("/{style}/mobile/searchProductGroup")
public class MobileSearchProductGroupController {

	@Autowired
	private MobileSearchProductGroupService mobileSearchProductGroupService;

	/**
	 * 查询产品组合列表
	 * 
	 * @author yangzhun
	 */
	@RequestMapping(value = "/searchProductGroup")
	@ResponseBody
	public Object searchProductGroup(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {

		return mobileSearchProductGroupService.searchProductGroup(model);
	}

	/**
	 * 同类型产品列表接口
	 */
	@RequestMapping(value = "/findSameTypeProductList")
	@ResponseBody
	public Object findSameTypeProductList(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
		Integer productId = model.getProductId();
		String msgId = model.getMsgId();
		Integer planProductId = model.getPlanProductId();
		Integer userId = model.getUserId();

		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<BaseProduct>(false, "msgId为空!", msgId);
		}
		if (productId == null) {
			return new ResponseEnvelope<BaseProduct>(false, "参数缺少产品productId!", msgId);
		}

		return mobileSearchProductGroupService.findTextureOfSameTypeProduct(productId, msgId, planProductId, userId);
	}
	
	/**
	 * 根据产品id查询材质
	 */
	@RequestMapping(value = "/selectProductById")
	@ResponseBody
	public Object selectProductById(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
		Integer id = model.getProductId();
		boolean onlyDefault = model.getOnlyDefault();
		
		return mobileSearchProductGroupService.selectProductById(id,onlyDefault);
	}
	
	/**
	 * 根据材质id查询材质详情
	 */
	@RequestMapping(value = "/selectTextureById")
	@ResponseBody
	public Object selectTextureById(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
		Integer id = model.getTextureId();
		
		
		return mobileSearchProductGroupService.selectTextureById(id);
	}

}
