package com.nork.product.service;

import java.util.List;

import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.search.GroupProductDetailsSearch;

/**   
 * @Title: GroupProductDetailsService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品组合关联表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:37:16
 * @version V1.0   
 */
public interface GroupProductDetailsService {
	/**
	 * 新增数据
	 *
	 * @param groupProductDetails
	 * @return  int 
	 */
	public int add(GroupProductDetails groupProductDetails);

	/**
	 *    更新数据
	 *
	 * @param groupProductDetails
	 * @return  int 
	 */
	public int update(GroupProductDetails groupProductDetails);

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
	 * @return  GroupProductDetails 
	 */
	public GroupProductDetails get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  groupProductDetails
	 * @return   List<GroupProductDetails>
	 */
	public List<GroupProductDetails> getList(GroupProductDetails groupProductDetails);

	/**
	 *    获取数据数量
	 *
	 * @param  groupProductDetails
	 * @return   int
	 */
	public int getCount(GroupProductDetailsSearch groupProductDetailsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  groupProductDetails
	 * @return   List<GroupProductDetails>
	 */
	public List<GroupProductDetails> getPaginatedList(
				GroupProductDetailsSearch groupProductDetailstSearch);

	/**
	 * 根据组合id查找组合明细
	 * @author huangsongbo
	 * @param groupId 组合id
	 * @return
	 */
	public List<GroupProductDetails> findDetailsByGroupId(Integer groupId);

	/**
	 * 根据组合id查找产品编码
	 * @author xiaoxc
	 * @param groupId 组合id
	 * @return
	 */
	public List<GroupProductDetails> getByGroupIdProductCodeList(Integer groupId);
	/**
	 * 通过组合id 获取 组合产品，并且获取相关资源
	 * @param id
	 * @return
	 */
	public List<GroupProductDetails> getDataAndResourcesByGroupId(Integer id);


	/**
	 * 获取组合某个产品多维材质
	 * @param groupId
	 * @param productId
	 * @return
	 */
	String getGroupProductTexturesInfo(Integer groupId, Integer productId, String posIndexPath);
}
