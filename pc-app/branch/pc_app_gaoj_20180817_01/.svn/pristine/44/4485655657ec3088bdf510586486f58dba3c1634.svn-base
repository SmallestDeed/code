package com.nork.home.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nork.common.model.LoginUser;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.BaseHouseResult;
import com.nork.home.model.search.BaseHouseSearch;


/**   
 * @Title: BaseHouseService.java 
 * @Package com.nork.business.service
 * @Description:业务-户型Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:53:51
 * @version V1.0   
 */
public interface BaseHouseService {
	/**
	 * 新增数据
	 *
	 * @param baseHouse
	 * @return  int 
	 */
	public int add(BaseHouse baseHouse);

	/**
	 *    更新数据
	 *
	 * @param baseHouse
	 * @return  int 
	 */
	public int update(BaseHouse baseHouse);

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
	 * @return  BaseHouse 
	 */
	public BaseHouse get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseHouse
	 * @return   List<BaseHouse>
	 */
	public List<BaseHouse> getList(BaseHouse baseHouse);
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseHouseSearch
	 * @return   int
	 */
	public int getCount(BaseHouseSearch baseHouseSearch);
	
	/**
	 *    查询单位时间内一人新增的户型数量 
	 *
	 * @param  baseHouseSearch
	 * @return   int
	 */
	public int getCountBySearch(BaseHouseSearch baseHouseSearch);
	
	public Integer getCountByCreator(BaseHouseSearch baseHouseSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseHousetSearch
	 * @return   List<BaseHouse>
	 */
	public List<BaseHouse> getPaginatedList(BaseHouseSearch baseHousetSearch);

	/**
	 * 其他
	 * 
	 */
	
	/**
	 *    小区搜索
	 *
	 * @param  baseHouseResult
	 * @return   List<BaseHouse>
	 */
	public List<BaseHouseResult> getHouseList(BaseHouseResult baseHouseResult);

	/**
	 * 小区搜索count
	 * @param baseHouseResult
	 * @return
	 */
	public int getHouseCount(BaseHouseResult baseHouseResult);
	
	public List<BaseHouse> getHouseCommonCodeList(BaseHouse baseHouse);

	public List<String> findAllName();

	public List<Integer> getAllDistinctLivingId();

	public List<Integer> getIdsBySearch(BaseHouseSearch baseHouseSearch);

	/**
	 * 根据livingId得到户型IdList
	 * @author huangsongbo
	 * @param livingId
	 * @return
	 */
	public List<Integer> getHouseIdsByLivingId(Integer livingId);
	
	public List<BaseHouse> getPaginatedListFilter(BaseHouseSearch baseHouseSearch);
	
	/**
	 * 
	 * @Title: selectUnitsName   
	 * @Description: 获取房间户型名称 </p>   
	 * @param: @param map
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String selectUnitsName(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 根据省市区搜索户型list 
	 * @param provinceCode
	 * @param cityCode
	 * @param livingName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 * @throws Exception
	 */
	public Object newHouseSearchWeb(String provinceCode,String cityCode, String livingName,
			String msgId,String limit,String start,LoginUser loginUser);
	
	/**
	 * 点击小区名字搜索户型
	 * @param style
	 * @param livingId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	public Object newHouseList(String style,String livingId,String msgId,
			String limit, String start,LoginUser loginUser);
	
	
	/**
	 * 根据livingId得到户型IdList
	 * @author jiangwei
	 * @param id
	 * @return
	 */
	List<String> getPaginatedListMoreInfo(@Param("id")Integer id);

	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	public int getHaveSpaceCount(BaseHouseSearch baseHouseSearch);
	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	public List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch);
}
