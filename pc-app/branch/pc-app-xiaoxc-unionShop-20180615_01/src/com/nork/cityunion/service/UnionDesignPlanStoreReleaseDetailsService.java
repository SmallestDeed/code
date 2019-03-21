package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStoreReleaseDetails;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseDetailsSearch;
import com.nork.common.model.LoginUser;

/**   
 * @Title: UnionDesignPlanStoreReleaseDetailsService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟素材发布明细表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:26:23
 * @version V1.0   
 */
public interface UnionDesignPlanStoreReleaseDetailsService {
	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreReleaseDetails
	 * @return  int 
	 */
	public int add(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails);

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreReleaseDetails
	 * @return  int 
	 */
	public int update(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails);

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
	 * @return  UnionDesignPlanStoreReleaseDetails 
	 */
	public UnionDesignPlanStoreReleaseDetails get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreReleaseDetails
	 * @return   List<UnionDesignPlanStoreReleaseDetails>
	 */
	public List<UnionDesignPlanStoreReleaseDetails> getList(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails);

	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreReleaseDetailsSearch
	 * @return   int
	 */
	public int getCount(UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreReleaseDetailstSearch
	 * @return   List<UnionDesignPlanStoreReleaseDetails>
	 */
	public List<UnionDesignPlanStoreReleaseDetails> getPaginatedList(
				UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailstSearch);

	/**
	 * 其他
	 * 
	 */
	/**
	 * 自动存储系统字段
	 */
	void sysSave(UnionDesignPlanStoreReleaseDetails model, LoginUser loginUser);

	/**
	 * 根据发布ID更新发布详情数据
	 * @param releaseDetails
	 * @return
	 */
	int updateByReleaseId(UnionDesignPlanStoreReleaseDetails releaseDetails);

	/**
	 * 根据设计方案Id删除发布方案素材库数据
	 * @param designPlanId
	 * @return
	 */
	Integer deleteStoreReleaseDetailsByDesignPlanId(Integer designPlanId);

	/**
	 * 根据设计效果图方案Id删除发布方案素材库数据
	 * @param designSceneId
	 * @return
	 */
	Integer deleteStoreReleaseDetailsByDesignSceneId(Integer designSceneId);

}
