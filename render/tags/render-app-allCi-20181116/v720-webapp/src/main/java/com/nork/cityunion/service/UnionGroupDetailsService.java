package com.nork.cityunion.service;

import java.util.List;
import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.vo.UnionGroupDetailsVo;


/**   
 * @Title: UnionGroupDetailsService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟组明细表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:02
 * @version V1.0   
 */
public interface UnionGroupDetailsService {
	/**
	 * 新增数据
	 *
	 * @param unionGroupDetails
	 * @return  int 
	 */
	public int add(UnionGroupDetails unionGroupDetails);

	/**
	 *    更新数据
	 *
	 * @param unionGroupDetails
	 * @return  int 
	 */
	public int update(UnionGroupDetails unionGroupDetails);

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
	 * @return  UnionGroupDetails 
	 */
	public UnionGroupDetails get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionGroupDetails
	 * @return   List<UnionGroupDetails>
	 */
	public List<UnionGroupDetails> getList(UnionGroupDetails unionGroupDetails);

	/**
	 *    获取数据数量
	 *
	 * @param  unionGroupDetails
	 * @return   int
	 */
	/*public int getCount(UnionGroupDetailsSearch unionGroupDetailsSearch);*/

	/**
	 *    分页获取数据
	 *
	 * @param  unionGroupDetails
	 * @return   List<UnionGroupDetails>
	 */
	/*public List<UnionGroupDetails> getPaginatedList(
				UnionGroupDetailsSearch unionGroupDetailstSearch);*/

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据联盟发布id得到联盟组成员列表
	 * @param releaseId
	 * @return
	 */
	public List<UnionGroupDetailsVo> getUnionGroupDetailsLsByReleaseId(Integer releaseId);
}
