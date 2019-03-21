package com.nork.onekeydesign.dao;

import java.util.List;

import com.nork.onekeydesign.model.vo.UnityDesignPlanRecommended;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.DesignPlanRecommended;
import com.nork.onekeydesign.model.DesignPlanRecommendedResult;
 

@Repository
@Transactional
public interface DesignPlanRecommendedMapperV2 {

	 int insertSelective(DesignPlanRecommended designPlanRecommended);
		
	 int updateByPrimaryKeySelective(DesignPlanRecommended designPlanRecommended);
	
	 int deleteByPrimaryKey(Integer id);
	
	 DesignPlanRecommended selectByPrimaryKey(Integer id);
	
	 List<DesignPlanRecommended> selectList(DesignPlanRecommended designPlanRecommended);

	 int selectCount(DesignPlanRecommended designPlanRecommended);

	Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	Integer getPlanRecommendedCountMobile(DesignPlanRecommended designPlanRecommended);

	List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);
	List<DesignPlanRecommendedResult> getPlanRecommendedListMobile(DesignPlanRecommended designPlanRecommended);
	/**
	 * 
	   
	 * getStatusByIds 批量查询id对应的发布状态      
	   
	 * @param ids
	 * @return 
	
	 * @return List<DesignPlanRecommended>    返回类型   
	   
	 * @Exception 异常对象    
	   
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public List<DesignPlanRecommended> getStatusByIds(List<Long> ids);
	/**
	 * 获取该方案推荐的白膜产品的数量，如果大于0那么是m+3 未装修完成的推荐（m+3 是不装修直接渲染的快捷键）
	 * @param id
	 * @return
	 */
	int recommendedDecorateState(Integer id);
	/**
	 *  获取方案推荐收藏夹数据量
	 * @param designPlanRecommended
	 * @return
	 */
	Integer getFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	/**
	 *  获取方案推荐收藏夹列表
	 * @param designPlanRecommended
	 * @return
	 */
	List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended);

	List<DesignPlanRecommended> getListByGroupPrimaryIdAndIsDeleted(
			@Param("groupPrimaryId") Integer groupPrimaryId, 
			@Param("isDeleted") Integer isDeleted
			);


	/**
	 * 获取方案资源
	 * @author chenqiang
	 * @param recommendedId 方案id
	 * @param sourceType 	表
	 * @return
	 * @date 2018/11/28 0028 15:07
	 */
	UnityDesignPlanRecommended selectDesignPlanRecommendedInfo(@Param("recommendedId") Integer recommendedId);

	UnityDesignPlanRecommended selectDesignPlanRecommendedInfoByMath(UnityDesignPlanRecommended unityDesignPlanRecommended);
}
