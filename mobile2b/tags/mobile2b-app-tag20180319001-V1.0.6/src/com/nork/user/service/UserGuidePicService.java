package com.nork.user.service;

import java.util.List;

import com.nork.user.model.UserGuidePic;
import com.nork.user.model.search.UserGuidePicSearch;

/**   
 * @Title: UserGuidePicService.java 
 * @Package com.nork.user.service
 * @Description:用户指南-用户指南图片列表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-12-07 14:42:28
 * @version V1.0   
 */
public interface UserGuidePicService {
	/**
	 * 新增数据
	 *
	 * @param userGuidePic
	 * @return  int 
	 */
	public int add(UserGuidePic userGuidePic);

	/**
	 *    更新数据
	 *
	 * @param userGuidePic
	 * @return  int 
	 */
	public int update(UserGuidePic userGuidePic);

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
	 * @return  UserGuidePic 
	 */
	public UserGuidePic get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  userGuidePic
	 * @return   List<UserGuidePic>
	 */
	public List<UserGuidePic> getList(UserGuidePic userGuidePic);

	/**
	 *    获取数据数量
	 *
	 * @param  userGuidePic
	 * @return   int
	 */
	public int getCount(UserGuidePicSearch userGuidePicSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  userGuidePic
	 * @return   List<UserGuidePic>
	 */
	public List<UserGuidePic> getPaginatedList(
				UserGuidePicSearch userGuidePictSearch);

	/**
	 * 获得排序比我大的最小的一条
	 * 
	 * @param sort
	 * @return
	 */
	public UserGuidePic getNextUserGuide(Integer sort);

	/**
	 * 交换排序
	 * 
	 * @param sysFaq
	 * @param sysFaq_current
	 * @return
	 */
	public int exchangeSort(Integer id, Integer sort,String type);

	public int getMaxSort();
	
	/**
	 * 其他
	 * 
	 */

}
