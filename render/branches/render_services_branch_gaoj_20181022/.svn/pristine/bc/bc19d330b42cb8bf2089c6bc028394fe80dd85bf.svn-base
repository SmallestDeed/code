package com.nork.customerservice.service;

import java.util.List;

import com.nork.customerservice.model.SysFaq;
import com.nork.customerservice.model.search.SysFaqSearch;

/**   
 * @Title: SysFaqService.java 
 * @Package com.nork.customerservice.service
 * @Description:客服中心-常见问题Service
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:35:55
 * @version V1.0   
 */
public interface SysFaqService {
	/**
	 * 新增数据
	 *
	 * @param sysFaq
	 * @return  int 
	 */
	public int add(SysFaq sysFaq);

	/**
	 *    更新数据
	 *
	 * @param sysFaq
	 * @return  int 
	 */
	public int update(SysFaq sysFaq);

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
	 * @return  SysFaq 
	 */
	public SysFaq get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysFaq
	 * @return   List<SysFaq>
	 */
	public List<SysFaq> getList(SysFaq sysFaq);

	/**
	 *    获取数据数量
	 *
	 * @param  sysFaq
	 * @return   int
	 */
	public int getCount(SysFaqSearch sysFaqSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysFaq
	 * @return   List<SysFaq>
	 */
	public List<SysFaq> getPaginatedList(
				SysFaqSearch sysFaqtSearch);

	/**
	 * 其他
	 * 
	 */

}
