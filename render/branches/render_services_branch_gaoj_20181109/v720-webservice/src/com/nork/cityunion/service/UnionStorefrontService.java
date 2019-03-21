package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.search.UnionStorefrontSearch;

/**   
 * @Title: UnionStorefrontService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟店面信息表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:22:46
 * @version V1.0   
 */
public interface UnionStorefrontService {
	/**
	 * 新增数据
	 *
	 * @param unionStorefront
	 * @return  int 
	 */
	public int add(UnionStorefront unionStorefront);

	/**
	 *    更新数据
	 *
	 * @param unionStorefront
	 * @return  int 
	 */
	public int update(UnionStorefront unionStorefront);

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
	 * @return  UnionStorefront 
	 */
	public UnionStorefront get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionStorefront
	 * @return   List<UnionStorefront>
	 */
	public List<UnionStorefront> getList(UnionStorefront unionStorefront);

	/**
	 *    获取数据数量
	 *
	 * @param  unionStorefront
	 * @return   int
	 */
	public int getCount(UnionStorefrontSearch unionStorefrontSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionStorefront
	 * @return   List<UnionStorefront>
	 */
	public List<UnionStorefront> getPaginatedList(
				UnionStorefrontSearch unionStorefronttSearch);

	/**
	 * 其他
	 * 
	 */

}
