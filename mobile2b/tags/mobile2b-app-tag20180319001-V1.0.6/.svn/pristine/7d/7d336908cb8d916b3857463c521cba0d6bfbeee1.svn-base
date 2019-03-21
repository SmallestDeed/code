package com.nork.mobile.service;

import com.nork.mobile.model.search.MobileSearchProductModel;

/**
 * 搜索组合产品的接口
 * 
 * @author yangzhun
 * @createTime 2017年10月27日16:15:04
 */
public interface MobileSearchProductGroupService {
	/**
	 * 查询产品组合列表
	 * 
	 * @author yangzhun
	 * @param model
	 * @return
	 */
	public Object searchProductGroup(MobileSearchProductModel model);

	/**
	 * 查询同类产品的材质
	 * @author yangzhun
	 * @param productId
	 * @param msgId
	 * @param planProductId
	 * @param userId
	 * @return
	 */
	public Object findTextureOfSameTypeProduct(Integer productId, String msgId, Integer planProductId, Integer userId);

	/**
	 * 根据材质id查询材质
	 * @param id
	 * @return
	 */
	Object selectProductById(Integer id,boolean onlyDefault);
	
	/**
	 * 根据材质id查询材质
	 * @param id
	 * @return
	 */
	Object selectTextureById(Integer id);
}
