package com.nork.product.service2;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlan;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;

/**   
 * @Title: BaseProductService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
public interface BaseProductServiceV2 {

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseProduct 
	 */
	public BaseProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getList(BaseProduct baseProduct);

	/**
	 * 获取是否是硬装
	 * @param mainProductId
	 * @return
	 */
	public boolean isHard(BaseProduct baseProduct);
	
	/**
	 * 处理拆分材质信息
	 * @author huangsongbo
	 * @param splitTexturesInfo
	 * @param type 
	 * @return
	 */
	public Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type);
	
	public Map<String,CategoryProductResult> setbaimoRuleMap(Integer spaceCommonId, HttpServletRequest request, Integer userId,
			String productSmallTypeCode, DesignPlan designPlan, Map<String, String> map);
	
	public String getU3dModelId(String mediaType,BaseProduct baseProduct);
	
	public CategoryProductResult assemblyUnityPanProduct(Integer productId,Integer spaceCommonId,DesignPlan designPlan,Integer userId,HttpServletRequest request);
	
	/**
	 * 产品材质处理
	 * 
	 */
	public void productMaterial(BaseProduct baseProduct,Integer id,String planProductId,Integer designPlanId,Integer spaceCommonId,LoginUser loginUser,HttpServletRequest request) throws Exception;	
	
	/**
	 * 获取该用户绑定的序列号品牌
	 * 
	 */
	public void getAuthorized(BaseProduct baseProduct,LoginUser loginUser);
}
