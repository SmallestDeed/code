package com.nork.sync.service;

import net.sf.json.JSONObject;

import com.nork.common.model.LoginUser;

/**
 * @Title: BaseAreaService.java 
 * @Package com.nork.system.service
 * @Description:同步-数据同步Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:43:41
 * @version V1.0   
 */
public interface SyncDataService {
	/**
	 * 样板房同步
	 *
	 * @param baseArea
	 * @return  int 
	 */
	public JSONObject syncDesignTemplet(String designTempletIds,LoginUser loginUser);

	/**
	 * 同步空间
	 * @param spaceCommonId
	 * @return
	 */
	public JSONObject syncSpaceCommon(Integer spaceCommonId,LoginUser loginUser);

	/**
	 * 分类同步
	 *
	 * @param baseArea
	 * @return  int 
	 */
	public JSONObject syncProductCatory();
	
	/**
	 * 产品同步
	 *
	 * @param baseArea
	 * @return  int 
	 */
	public JSONObject syncBaseProduct(Integer productId,LoginUser loginUser);
}
