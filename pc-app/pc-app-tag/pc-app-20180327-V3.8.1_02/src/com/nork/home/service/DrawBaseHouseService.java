package com.nork.home.service;

import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.model.search.DrawBaseHouseQuery;

/**
 * 
 * @author songjianming@sanduspace.cn 2018 2018年1月27日
 *
 */
public interface DrawBaseHouseService {
	
	Map<String, Object> listDrawHouse(DrawBaseHouseQuery query, LoginUser login);

	/**
	 * 删除我的户型
	 * 
	 * @return
	 */
	ResponseEnvelope<?> deleteDrawHouse(LoginUser user, String houseId);
}
