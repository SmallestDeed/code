package com.nork.business.service;

import java.util.List;

import com.nork.business.model.HouseSpace;
import com.nork.business.model.search.HouseSpaceSearch;

/**   
 * @Title: HouseSpaceService.java 
 * @Package com.nork.business.service
 * @Description:业务-房型空间定义Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:58:14
 * @version V1.0   
 */
public interface HouseSpaceService {
	/**
	 * 新增数据
	 *
	 * @param houseSpace
	 * @return  int 
	 */
	public int add(HouseSpace houseSpace);

	/**
	 *    更新数据
	 *
	 * @param houseSpace
	 * @return  int 
	 */
	public int update(HouseSpace houseSpace);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	public int deleteByHouseId(Integer id);
	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  HouseSpace 
	 */
	public HouseSpace get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  houseSpace
	 * @return   List<HouseSpace>
	 */
	public List<HouseSpace> getList(HouseSpace houseSpace);
	/**
	 * 所有数据并除去重复
	 * 
	 * @param  houseSpace
	 * @return   List<HouseSpace>
	 */
	public List<HouseSpace> getDeduplicationListList(HouseSpace houseSpace);

	public List<HouseSpace> getModifiedList(HouseSpace houseSpace);
	/**
	 *    获取数据数量
	 *
	 * @param  houseSpace
	 * @return   int
	 */
	public int getCount(HouseSpaceSearch houseSpaceSearch);
	/**
	 *    通过类型获取数据数量
	 *
	 * @param  houseSpace
	 * @return   int
	 */
	public int getCountByType(HouseSpaceSearch houseSpaceSearch);
	
	public Integer getHouseCountBySearch(HouseSpaceSearch houseSpaceSearch);
	
	public Integer getLivingCountBySearch(HouseSpaceSearch houseSpaceSearch);
	public Integer gainCountByType(HouseSpaceSearch houseSpaceSearch);
	/**
	 *    分页获取数据
	 *
	 * @param  houseSpace
	 * @return   List<HouseSpace>
	 */
	public List<HouseSpace> getPaginatedList(
				HouseSpaceSearch houseSpacetSearch);

	/**
	 * 其他
	 * 
	 */
	/**
	 *    获取空间使用了的户型图和子空间使用的户型图
	 *
	 * @param  spaceId
	 * @return   List<HouseSpace>
	 */
	public List<HouseSpace> getSpaceHousePicList(
			HouseSpaceSearch houseSpaceSearch);
	
	public int getSpaceHousePicCount(
			HouseSpaceSearch houseSpaceSearch);
	/**
	 *    获取空间使用了的户型图
	 *
	 * @param  spaceId
	 * @return   List<HouseSpace>
	 */
	public List<HouseSpace> getMySpaceHousePicList(
			HouseSpaceSearch houseSpaceSearch);
	
	public int getMySpaceHousePicCount(
			HouseSpaceSearch houseSpaceSearch);

	public List<String> getSpaceTypeListByHouseId(Integer id);

	public List<Integer> getDistinctHouseIdWhereSpaceIdIsNotNull();

}
