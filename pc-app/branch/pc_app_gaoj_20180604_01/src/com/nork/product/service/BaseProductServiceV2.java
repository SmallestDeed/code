package com.nork.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlan;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.FindSameTypeProductListDTO;

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
	 * 通过品牌获取产品列表
	 * @param baseProduct
	 * @return
	 */
	public List<CategoryProductResult> getBrandProductsList(BaseProduct baseProduct);
	
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
	public void productMaterial(BaseProduct baseProduct,Integer id,String planProductId,Integer designPlanId,Integer spaceCommonId,
			LoginUser loginUser,HttpServletRequest request) throws Exception;	
	
	/**
	 * 获取该用户绑定的序列号品牌
	 * 
	 */
	public BaseProduct getAuthorized(BaseProduct baseProduct,LoginUser loginUser);
	
	/**
	 * 所有数据
	 * 
	 * @param  authorizedConfig
	 * @return   List<AuthorizedConfig>
	 */
	public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig);
	
	/**
	 * 通过品牌获取产品数量
	 * @param baseProduct
	 * @return
	 */
	public int getBrandProductsCount(BaseProduct baseProduct);
	
	/**
	 * 产品库列表
	 * @param baseProduct
	 * @return
	 */
	public List<CategoryProductResult> list(BaseProduct baseProduct,LoginUser loginUser,int total);
	
	
	/**
	 *    同类型数据数量
	 *
	 * @param  baseProduct
	 * @return   int
	 */
	public int getSameTypeProductCount(BaseProduct baseProduct);
	
	/**
	 * 产品同类型数据
	 * 
	 * @param  baseProduct
	 * @return   List<BaseProduct>
	 */
	public List<BaseProduct> getSameTypeProductList(BaseProduct baseProduct);
	
	/**
	 * 同类型产品列表接口
	 */
	public FindSameTypeProductListDTO findSameTypeProductList(BaseProduct baseProductNew,LoginUser loginUser,BaseProduct bp,Integer productId);
	
	/**
	 * 产品详情
	 */
	public BaseProduct categoryProductList(BaseProduct baseProduct);
	
	
	/**
	 * 批量查询
	 * */
	public List<BaseProduct> getBatchData(List<Integer> list, List<Integer> putawayStateList);
}
