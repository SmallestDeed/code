package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreReleaseVO;
import com.nork.common.model.LoginUser;

import javax.persistence.criteria.CriteriaBuilder;

/**   
 * @Title: UnionDesignPlanStoreReleaseService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟素材发布表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
public interface UnionDesignPlanStoreReleaseService {
	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	public int add(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease);

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	public int update(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease);

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
	 * @return  UnionDesignPlanStoreRelease 
	 */
	public UnionDesignPlanStoreRelease get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreRelease
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	public List<UnionDesignPlanStoreRelease> getList(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease);

	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreReleaseSearch
	 * @return   int
	 */
	public int getCount(UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreReleasetSearch
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	public List<UnionDesignPlanStoreRelease> getPaginatedList(
				UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleasetSearch);

	/**
	 *   删除联盟组的时候更新联盟信息
	 *
	 * @param id
	 * @return  int 
	 */
	public int deleteByGroupId(Integer id);

	/**
	 * 根据分类Id删除发布素材
	 * @param storeRelease
	 * @return
	 */
	int updateByStoreRelease(UnionDesignPlanStoreRelease storeRelease);

	/**
	 * 保存、编辑发布素材数据
	 * @param storeRelease
	 * @param loginUser
	 * @return
	 */
	String save(UnionDesignPlanStoreRelease storeRelease, LoginUser loginUser);

	/**
	 * 发布列表查询
	 * @param catoryId
	 * @param releaseName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	int findStoreReleaseCount(Integer catoryId, String releaseName, Integer userId, Integer start, Integer limit);

	/**
	 * 发布列表查询
	 * @param catoryId
	 * @param releaseName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<UnionDesignPlanStoreReleaseVO> findStoreReleaseList(Integer catoryId, String releaseName, Integer userId, Integer start, Integer limit);

	/**
	 * 通过设计效果图方案ID查询联盟设计方案发布的集合
	 * @param designPlanId
	 * @return
	 */
	List<UnionDesignPlanStoreRelease> findStoreReleaseListByDesignSceneId(Integer designPlanId);
}
