package com.nork.business.service;

import java.util.List;

import com.nork.business.model.RoomCommon;
import com.nork.business.model.search.RoomCommonSearch;

/**   
 * @Title: RoomCommonService.java 
 * @Package com.nork.business.service
 * @Description:业务-通用房型Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:08:11
 * @version V1.0   
 */
public interface RoomCommonService {
	/**
	 * 新增数据
	 *
	 * @param roomCommon
	 * @return  int 
	 */
	public int add(RoomCommon roomCommon);

	/**
	 *    更新数据
	 *
	 * @param roomCommon
	 * @return  int 
	 */
	public int update(RoomCommon roomCommon);

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
	 * @return  RoomCommon 
	 */
	public RoomCommon get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  roomCommon
	 * @return   List<RoomCommon>
	 */
	public List<RoomCommon> getList(RoomCommon roomCommon);

	/**
	 *    获取数据数量
	 *
	 * @param  roomCommon
	 * @return   int
	 */
	public int getCount(RoomCommonSearch roomCommonSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  roomCommon
	 * @return   List<RoomCommon>
	 */
	public List<RoomCommon> getPaginatedList(
				RoomCommonSearch roomCommontSearch);

	/**
	 * 其他
	 * 
	 */

}
