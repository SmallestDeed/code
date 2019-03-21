package com.nork.home.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.home.model.HouseSpaceResult;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.search.SpaceCommonSearch;

/**   
 * @Title: SpaceCommonService.java 
 * @Package com.nork.home.service
 * @Description:户型房型-通用空间表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 15:48:39
 * @version V1.0   
 */
public interface SpaceCommonService {
	/**
	 * 新增数据
	 *
	 * @param spaceCommon
	 * @return  int 
	 */
	public int add(SpaceCommon spaceCommon);

	/**
	 *    更新数据
	 *
	 * @param spaceCommon
	 * @return  int 
	 */
	public int update(SpaceCommon spaceCommon);

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
	 * @return  SpaceCommon 
	 */
	public SpaceCommon get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  spaceCommon
	 * @return   List<SpaceCommon>
	 */
	public List<SpaceCommon> getList(SpaceCommon spaceCommon);

	/**
	 *    获取数据数量
	 *
	 * @param  spaceCommon
	 * @return   int
	 */
	public int getCount(SpaceCommonSearch spaceCommonSearch);
	public int findAllCode(SpaceCommonSearch spaceCommonSearch);
	
	/**
	 *    分页获取数据
	 *
	 * @param  spaceCommon
	 * @return   List<SpaceCommon>
	 */
	public List<SpaceCommon> getPaginatedList(
				SpaceCommonSearch spaceCommontSearch);

	public List<SpaceCommon> getPageSelectList(
			SpaceCommonSearch spaceCommonSearch);
	
	public int  getPageSelectCount(
			SpaceCommonSearch spaceCommonSearch);
	
	public List<HouseSpaceResult> getHouseSpaceList(
			Integer houseId);

	//public int getHouseSpaceListCount(int parseInt);
	public int getHouseSpaceListCount(SpaceCommon spaceCommon,int userId);
	
	//public int getHouseSpaceListCount2(int parseInt);
	public int getHouseSpaceListCount2(SpaceCommon spaceCommon,int userId);

	/*public List<HouseSpaceResult> getPaginatedHouseSpaceList(int houseId,
			int start, int limit);*/
	public List<HouseSpaceResult> getPaginatedHouseSpaceList(SpaceCommon spaceCommon,int userId);
	/*public List<HouseSpaceResult> getPaginatedHouseSpaceList2(int houseId,
			int start, int limit,int userType);*/
	public List<HouseSpaceResult> getPaginatedHouseSpaceList2(SpaceCommon spaceCommon,int userId);
	/**
	 * 空间搜索
	 * 
	 * @param  spaceCommon
	 * @return   List<SpaceCommon>
	 */
	public List<SpaceCommon> getSpaceSearchList(SpaceCommon spaceCommon,int userId);
	public int  getSpaceSearchCount(SpaceCommon spaceCommon,int userId);
	
	public void backfill(SpaceCommon spaceCommon);
	
	public void clearBackfill(SpaceCommon spaceCommon);

	public String getU3dModelId(String mediaType,SpaceCommon spaceCommon);
	
	public String spaceCodingVerification(SpaceCommon spaceCommon,HttpServletRequest request);

	public List<String> findAllName();

	/**
	 * 通过编码删除
	 * @param spaceCode
	 * @return
	 */
	public int deleteByCode(String spaceCode);


	public SpaceCommon sysSave(SpaceCommon spaceCommon,LoginUser loginUser);

	/**
	 * 通过houseIds查找关联的spaceIds
	 * @param houseIds
	 * @return
	 */
	public List<Integer> getSpaceIdsByHouseIds(List<Integer> houseIds);
	
	
	/**
	 * 通过SpaceCommonIds查找 空间名
	 * @param houseIds
	 * @return
	 */
	public List<SpaceCommon> getSpaceCommonIds(List<Integer> SpaceCommonIds);

	public void setPicParams(SpaceCommon spaceCommonSingle);

	/**
	 * 处理类似于"!xingx"的值
	 * @author huangsongbo
	 * @param categoryBlacklistMap
	 * @return
	 */
	public Map<String, Set<String>> dealWithMap(Map<String, Set<String>> categoryBlacklistMap);
	
	/**
	 * 通过空间状态找出空间idList
	 * @author huangsongbo
	 * @param status
	 * @return
	 */
	public List<Integer> findIdListByStatus(int status);
	
	/**
	 * 修改空间状态
	 * @author huangsongbo
	 * @param hasBeenPutaway 修改前状态
	 * @param releaseing 修改后状态
	 */
	public void updateStatus(int oldStatus, int newStatus);
	
	/**
	 * 空间布局列表查询接口:根据空间类型、面积查询出空间列表
	 * @param spaceFunctionId
	 * @param areaValue
	 * @param spaceShape
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newSpaceSearch(String spaceFunctionId/* 空间类型 */, String areaValue/* 面积 */,
			String spaceShape, String msgId, String limit, String start,LoginUser loginUser);
	
	/**
	 * 通过户型过滤空间布局图
	 * @param houseId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newHouseSpaceList(String houseId, String msgId, String limit,String start,LoginUser loginUser);
	
	/**
	 * 空间智能搜索
	 * @param spaceCommon 
	 */
	int spaceCapacityCount(SpaceCommon spaceCommon);
	
	List<SpaceCommon> spaceCapacityList(SpaceCommon spaceCommon);

	/**
	 * 获得该空间的房型类别
	 */
	Integer getSpaceFunctionIdBySpaceCommonId(Integer id);
}
