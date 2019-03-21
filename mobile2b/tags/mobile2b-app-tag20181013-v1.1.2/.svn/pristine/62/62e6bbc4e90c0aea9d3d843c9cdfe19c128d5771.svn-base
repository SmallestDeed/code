package com.nork.system.service;

import java.util.List;

import com.nork.system.model.ResVersion;
import com.nork.system.model.search.ResVersionSearch;

/**   
 * @Title: ResVersionService.java 
 * @Package com.nork.system.service
 * @Description:系统版本-系统版本资源表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-08-17 11:41:05
 * @version V1.0   
 */
public interface ResVersionService {
	/**
	 * 新增数据
	 *
	 * @param resVersion
	 * @return  int 
	 */
	public int add(ResVersion resVersion);

	/**
	 *    更新数据
	 *
	 * @param resVersion
	 * @return  int 
	 */
	public int update(ResVersion resVersion);

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
	 * @return  ResVersion 
	 */
	public ResVersion get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resVersion
	 * @return   List<ResVersion>
	 */
	public List<ResVersion> getList(ResVersion resVersion);

	/**
	 *    获取数据数量
	 *
	 * @param  resVersion
	 * @return   int
	 */
	public int getCount(ResVersionSearch resVersionSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resVersion
	 * @return   List<ResVersion>
	 */
	public List<ResVersion> getPaginatedList(
				ResVersionSearch resVersiontSearch);

	/**
	 * 其他
	 * 
	 */

}
