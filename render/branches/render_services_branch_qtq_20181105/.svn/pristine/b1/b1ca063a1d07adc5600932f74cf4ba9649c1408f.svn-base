package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.search.UnionGroupSearch;
import com.nork.cityunion.model.vo.UnionGroupVo;
import com.nork.pano.model.DesignPlanStoreRelease;

/**   
 * @Title: UnionGroupService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟分组表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
public interface UnionGroupService {
	/**
	 * 新增数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	public int add(UnionGroup unionGroup);

	/**
	 *    更新数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	public int update(UnionGroup unionGroup);

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
	 * @return  UnionGroup 
	 */
	public UnionGroup get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	public List<UnionGroup> getList(UnionGroup unionGroup);

	/**
	 *    获取数据数量
	 *
	 * @param  unionGroupSearch
	 * @return   int
	 */
	public int getCount(UnionGroupSearch unionGroupSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionGrouptSearch
	 * @return   List<UnionGroup>
	 */
	public List<UnionGroup> getPaginatedList(
				UnionGroupSearch unionGrouptSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据720制作主表信息查询联盟门店信息
	 * @param id
	 * @return
	 */
	public UnionGroupVo getUnionGroupByStoreRelease(Integer id);

	/**
	 * 根据同城联盟分享方案主表信息查询联盟门店信息
	 * @param id
	 * @return
	 */
	public UnionGroupVo getUnionGroupByUnionStoreRelease(Integer id);

}
