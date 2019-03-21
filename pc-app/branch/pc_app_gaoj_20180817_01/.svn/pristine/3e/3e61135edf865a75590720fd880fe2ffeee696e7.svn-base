package com.nork.system.service;

import java.util.List;

import com.nork.system.model.BaseArea;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.web.WBaseArea;

/**   
 * @Title: BaseAreaService.java 
 * @Package com.nork.system.service
 * @Description:系统-行政区域Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:43:41
 * @version V1.0   
 */
public interface BaseAreaService {
	/**
	 * 新增数据
	 *
	 * @param baseArea
	 * @return  int 
	 */
	public int add(BaseArea baseArea);

	/**
	 *    更新数据
	 *
	 * @param baseArea
	 * @return  int 
	 */
	public int update(BaseArea baseArea);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseArea 
	 */
	public BaseArea get(Integer id);
	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseArea 
	 */
	public BaseArea selectCityName(BaseAreaSearch baseAreaSearch);

	public WBaseArea getByCode(BaseAreaSearch baseAreaSearch);
	
	/**
	 * 所有数据
	 * 
	 * @param  baseArea
	 * @return   List<BaseArea>
	 */
	public List<BaseArea> getList(BaseArea baseArea);

	/**
	 *    获取数据数量
	 *
	 * @param  baseArea
	 * @return   int
	 */
	public int getCount(BaseAreaSearch baseAreaSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseArea
	 * @return   List<BaseArea>
	 */
	public List<BaseArea> getPaginatedList(
				BaseAreaSearch baseAreatSearch);

	public List<BaseArea> getCityList();


	/**
	 * 获取行政区域名称
	 * @param areaCode 省、市、区
	 * @return
	 */
	String getAreaNameByCode(String[] areaCode);
}
