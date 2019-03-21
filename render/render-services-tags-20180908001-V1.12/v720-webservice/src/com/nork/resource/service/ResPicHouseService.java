package com.nork.resource.service;


import java.util.List;

import com.nork.resource.model.ResPicHouse;
import com.nork.resource.model.search.ResPicHouseSearch;


/**   
 * @Title: ResPicService.java 
 * @Package com.nork.system.service
 * @Description:系统-图片资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
public interface ResPicHouseService {
	/**
	 * 新增数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	public int add(ResPicHouse resPicHouse);

	public int update(ResPicHouse resPicHouse);
	
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
	 * @return  ResPic 
	 */
	public ResPicHouse get(Integer id);
	
	
	/**
	 *    获取数据数量
	 *
	 * @param  resPic
	 * @return   int
	 */
	public int getCount(ResPicHouseSearch resPicHouseSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resPic
	 * @return   List<ResPic>
	 */
	public List<ResPicHouse> getPaginatedList(ResPicHouseSearch resPicHouseSearch);
	
	public ResPicHouse getPrevResPicHouseId(Integer resPicHouseId);
	
	public ResPicHouse getNextResPicHouseId(Integer resPicHouseId);

}
