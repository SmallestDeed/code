package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.search.UnionDesignPlanStoreSearch;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionDesignPlanStoreService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟设计方案素材库表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:21:19
 * @version V1.0   
 */
public interface UnionDesignPlanStoreService {
	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStore
	 * @return  int 
	 */
	public int add(UnionDesignPlanStore unionDesignPlanStore);

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStore
	 * @return  int 
	 */
	public int update(UnionDesignPlanStore unionDesignPlanStore);

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
	 * @return  UnionDesignPlanStore 
	 */
	public UnionDesignPlanStore get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStore
	 * @return   List<UnionDesignPlanStore>
	 */
	public List<UnionDesignPlanStore> getList(UnionDesignPlanStore unionDesignPlanStore);

	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStore
	 * @return   int
	 */
	public int getCount(UnionDesignPlanStoreSearch unionDesignPlanStoreSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStore
	 * @return   List<UnionDesignPlanStore>
	 */
	public List<UnionDesignPlanStore> getPaginatedList(
				UnionDesignPlanStoreSearch unionDesignPlanStoretSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 获取用户素材库数量
	 * @param designPlanName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	int findMyDesignPlanStoreCount(String designPlanName,Integer userId,Integer start,Integer limit);

	/**
	 * 获取用户素材库list
	 * @param designPlanName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<UnionDesignPlanStore> findMyDesignPlanStoreList(String designPlanName,Integer userId,Integer start,Integer limit);


	/**
	 * 自动存储系统字段
	 * @param model
	 * @param loginUser
	 */
	void sysSave(UnionDesignPlanStore model,LoginUser loginUser);

	/**
	 * 获取打包发布优先进入方案素材ID
	 * @param storeIdList
	 * @return
	 */
	Integer getStoreIdByStoreIds(List<Integer> storeIdList);

	/**
	 * 打包发布设计方案素材排序
	 * @param storeIdList
	 * @return
	 */
	List<Integer> findStoreIdByStoreIdsList(List<Integer> storeIdList);

	/**
	 * 删除渲染图时查询是否存在方案素材库中
	 * @param designPlanId
	 * @param renderPicSmallId
	 * @return
	 */
	UnionDesignPlanStore getDesignPlanStore(Integer designPlanId, Integer renderPicSmallId);


	/**
	 * 删除草图渲染图时删除同盟设计方案素材库相关数据及发布方案素材库相关数据
	 * @param designPlanId
	 * @param renderPicSmallId
	 */
	void delDesignPlanStoreRelatedData(Integer designPlanId, Integer renderPicSmallId);


	/**
	 * 根据素材Id获取渲染资源code
	 * @param storeId
	 * @return
	 */
	String getRenderPicCodeByStoreId(Integer storeId);

	/**
	 * 根据设计方案Id删除同城联盟方案素材及发布的方案素材
	 * @param designPlanId
	 * @return
	 */
	void deleteCityUnionPlanStoreByDesignPlanId(Integer designPlanId);

	/**
	 * 根据storeids获取store对象
	 * @param storeIdList
	 * @return
	 */
	List<UnionDesignPlanStore> getStorePicByStoreIds(List<Integer> storeIdList);

}
