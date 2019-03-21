package com.nork.product.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.product.model.GroupCollectDetails;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.search.GroupCollectDetailsSearch;

/**   
 * @Title: GroupCollectDetailsService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-组合收藏明细表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:41:47
 * @version V1.0   
 */
public interface GroupCollectDetailsService {
	/**
	 * 新增数据
	 *
	 * @param groupCollectDetails
	 * @return  int 
	 */
	public int add(GroupCollectDetails groupCollectDetails);

	/**
	 *    更新数据
	 *
	 * @param groupCollectDetails
	 * @return  int 
	 */
	public int update(GroupCollectDetails groupCollectDetails);

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
	 * @return  GroupCollectDetails 
	 */
	public GroupCollectDetails get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  groupCollectDetails
	 * @return   List<GroupCollectDetails>
	 */
	public List<GroupCollectDetails> getList(GroupCollectDetails groupCollectDetails);

	/**
	 *    获取数据数量
	 *
	 * @param  groupCollectDetails
	 * @return   int
	 */
	public int getCount(GroupCollectDetailsSearch groupCollectDetailsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  groupCollectDetails
	 * @return   List<GroupCollectDetails>
	 */
	public List<GroupCollectDetails> getPaginatedList(
				GroupCollectDetailsSearch groupCollectDetailstSearch);

	/**
	 * 通过组合收藏id查找该收藏下的产品信息
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<GroupCollectDetails> findAllByGroupId(Integer id);

	/**
	 * 保存收藏明细
	 * @author huangsongbo
	 * @param groupProductDetails
	 * @param id
	 * @param loginUser 
	 */
	public void saveByGroupDetails(GroupProductDetails groupProductDetails, int id, LoginUser loginUser);

	/**
	 * 删除收藏明细表中关联的数据
	 * @author huangsongbo
	 * @param id
	 */
	public void deleteByGroupCollectId(Integer id);

}
