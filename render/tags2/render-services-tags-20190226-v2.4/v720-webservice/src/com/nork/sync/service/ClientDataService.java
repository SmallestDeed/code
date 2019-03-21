package com.nork.sync.service;

import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.product.model.BaseBrand;

/**   
 * @Title: BaseAreaService.java 
 * @Package com.nork.system.service
 * @Description:同步-数据同步Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:43:41
 * @version V1.0   
 */
public interface ClientDataService {
	public Map<String,Object> spaceCommonCollect(Integer spaceCommonId,LoginUser loginUser);
	public Map<String,Object> designTempletCollect(Integer designTempletId,LoginUser loginUser);
	public BaseBrand baseBrandCollect(Integer baseBrandId);
	public Map<String,Object> baseProductCollect(Integer baseProductId,LoginUser loginUser);

	/**
	 * 组装样板房和关联数据
	 * @param designTempletId
	 * @return
	 */
	public Map<String,Object> assembleDesignTemplet(Integer designTempletId,LoginUser loginUser);

	/**
	 * 组装空间信息
	 * @param spaceCommonId
	 * @return
	 */
	public Map<String,Object> assembleSpaceCommon(Integer spaceCommonId,LoginUser loginUser);

	/**
	 * 组装产品信息
	 * @param productId
	 * @return
	 */
	public Map<String,Object> assembleProduct(Integer productId,LoginUser loginUser);

	/**
	 * 组装样板房中产品
	 * @param designTempletId
	 * @return
	 */
	public Map<String,Object> assembleDesignTempletProduct(Integer designTempletId,LoginUser loginUser);

	/**
	 * 组装样板房产品推荐
	 * @param designTempletId
	 * @return
	 */
	public Map<String,Object> assembleProductRecommendation(Integer designTempletId,LoginUser loginUser);
}
