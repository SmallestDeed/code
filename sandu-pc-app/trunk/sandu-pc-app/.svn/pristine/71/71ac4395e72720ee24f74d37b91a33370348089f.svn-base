package com.sandu.system.service;

import java.util.List;

import com.sandu.system.model.BaseArea;
import com.sandu.system.model.bo.HouseAreaBo;

/**   
 * @Title: BaseAreaService.java 
 * @Package com.sandu.system.service
 * @Description:系统-行政区域Service
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
	 * 所有数据
	 * 
	 * @param  baseArea
	 * @return   List<BaseArea>
	 */
	public List<BaseArea> getList(BaseArea baseArea);

	/**
	 * 根据户型关联的区域编码取得省市区信息
	 * @param baseArea
	 * @return
	 */
	HouseAreaBo selectAreaInfoByAreaLongCode(BaseArea baseArea);
}
