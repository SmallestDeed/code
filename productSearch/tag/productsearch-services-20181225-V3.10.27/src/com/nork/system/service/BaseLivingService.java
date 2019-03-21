package com.nork.system.service;

import java.util.List;

import com.nork.system.model.BaseLiving;
import com.nork.system.model.search.BaseLivingSearch;

/**   
 * @Title: BaseLivingService.java 
 * @Package com.nork.system.service
 * @Description:系统-小区Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 14:41:11
 * @version V1.0   
 */
public interface BaseLivingService {
	/**
	 * 新增数据
	 *
	 * @param baseLiving
	 * @return  int 
	 */
	public int add(BaseLiving baseLiving);

	/**
	 *    更新数据
	 *
	 * @param baseLiving
	 * @return  int 
	 */
	public int update(BaseLiving baseLiving);

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
	 * @return  BaseLiving 
	 */
	public BaseLiving get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseLiving
	 * @return   List<BaseLiving>
	 */
	public List<BaseLiving> getList(BaseLiving baseLiving);

	/**
	 *    获取数据数量
	 *
	 * @param  baseLiving
	 * @return   int
	 */
	public int getCount(BaseLivingSearch baseLivingSearch);
	/**
	 *    获取单位时间内一个人新增小区数量
	 *
	 * @param  baseLiving
	 * @return   int
	 */
	public int getCountBySearch(BaseLivingSearch baseLivingSearch);

	public Integer getCountByCreator(BaseLivingSearch baseLivingSearch);
	/**
	 *    分页获取数据
	 *
	 * @param  baseLiving
	 * @return   List<BaseLiving>
	 */
	public List<BaseLiving> getPaginatedList(
				BaseLivingSearch baseLivingtSearch);

	/**
	 * 获取同一区域下最大的小区编码数
	 * @param livingCode
	 * @return
	 */
	public int getMaxCodeNum(BaseLivingSearch baseLivingtSearch);

	public List<String> findAllName();

	/**满足条件的id*/
	public List<Integer> getIdsBySearch(BaseLivingSearch baseLivingSearch);

}
