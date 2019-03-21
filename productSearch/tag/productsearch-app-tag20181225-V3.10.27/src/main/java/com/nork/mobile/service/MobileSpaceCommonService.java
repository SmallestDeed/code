package com.nork.mobile.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.model.SysDictionary;

public interface MobileSpaceCommonService {

	/**
	 * 空间布局列表查询接口:根据空间类型、面积查询出空间列表
	 * 
	 * @param spaceFunctionId
	 * @param areaValue
	 * @param spaceShape
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newSpaceSearch(String spaceFunctionId/* 空间类型 */,
			String areaValue/* 面积 */, String spaceShape, String msgId,
			String limit, String start, LoginUser loginUser);
	
	/**
	 * 获取所有空间形状的图片
	 * @return
	 */
	public Object getSpaceShape(String type);
	
	/**
	 * 查询空间类型及对应的面积范围
	 * @return
	 */
//	public Map<String, Object> getAreaRange();
	public List<SysDictionary> getAreaRange(String type);
	/**
	 * 根据空间名称获取风格接口
	 * @param spaceName eg： "客餐厅"
	 * @return
	 */
	public ResponseEnvelope<?> getDesignStyleList(String spaceName);

	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 * 
	 * @param spaceCommonIdText
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newSpaceDesign(String spaceCommonIdText, String msgId,
			String limit, String start, LoginUser loginUser);

	

}
