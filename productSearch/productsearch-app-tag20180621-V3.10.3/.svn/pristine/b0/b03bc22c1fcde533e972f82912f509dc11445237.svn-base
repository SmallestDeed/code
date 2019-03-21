package com.nork.mobile.service;

import com.nork.common.model.LoginUser;

public interface MobileHouseSearchService {

	/**
	 * 根据省市区搜索户型list
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param livingName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newHouseSearchWeb(String provinceCode, String cityCode,
			String livingName, String msgId, String limit, String start,
			LoginUser loginUser);

	/**
	 * 点击小区名字 搜索户型
	 * 
	 * @param style
	 * @param livingId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	public Object newHouseList(String style, String livingId, String msgId,
			String limit, String start, LoginUser loginUser);

	/**
	 * 通过户型过滤空间布局图
	 * 
	 * @param houseId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newHouseSpaceList(String houseId, String msgId, String limit,
			String start, LoginUser loginUser,String spaceFunctionId);

	public Object getSpaceNameInHouse(String houseId, String msgId, String limit, String start, LoginUser loginUser);

}
