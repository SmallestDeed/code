package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.onekeydesign.model.DesignTempletProduct;
import com.nork.onekeydesign.model.ProductListByTypeInfo;

public interface DesignTempletProductService {

	/**
	 * 遍历样板房产品列表
	 * 讲单品/结构/组合分类放置
	 * 
	 * @author huangsongbo
	 * @param designTempletProductList
	 * @param matchType 0:全屋替换;1:硬装替换
	 * @param planRecommendedId 
	 * 检测推荐方案中有没有定制浴室柜,如果有的话,会进行特殊逻辑处理:样板房的浴室柜组合变成单品匹配逻辑 
	 * add by huangsongbo 2018.01.03
	 * @return
	 */
	ProductListByTypeInfo getProductListByTypeInfo(
			List<DesignTempletProduct> designTempletProductList,
			Integer matchType, Integer planRecommendedId);

	/**
	 * 得到样板房产品列表以及额外的信息
	 * 
	 * @author huangsongbo
	 * @param designTempletId
	 * @return
	 */
	List<DesignTempletProduct> getListByTempletId(Integer designTempletId);

}
