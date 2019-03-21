package com.nork.design.dao;

import java.util.List;
import java.util.Set;

import com.nork.design.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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

	List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);
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
	
	Integer getAllFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	/**
	 *  获取方案推荐收藏夹列表
	 * @param designPlanRecommended
	 * @return
	 */
	List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended);
	
	List<DesignPlanRecommendedResult> getAllFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended);

	/**
	 * 获取推荐方案详细信息
	 * @param planRecommendedId
	 * @return
	 */
	DesignPlanRecommendedResult getAllByRecommendedId(Integer planRecommendedId);

	/**
	 * 根据推荐方案id获取方案装修报价信息
	 * @param planRecommendId
	 * @return
	 */
	List<PlanDecoratePriceBO> getPlanDecoratePrice(@Param("planRecommendId") Integer planRecommendId);

	/**
	 * 根据全屋方案id获取方案装修报价信息
	 * @param fullHouseId
	 * @return
	 */
	List<PlanDecoratePriceBO> getPlanDecoratePriceByFullHouseId(@Param("fullHouseId") Integer fullHouseId);

    /**
     * 获取全屋方案数量
     * @param designPlanRecommended
     * @return
     */
    Integer getFullHousePlanCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取全屋方案集合
     * @param designPlanRecommended
     * @return
     */
	List<DesignPlanRecommendedResult> getFullHousePlanList(DesignPlanRecommended designPlanRecommended);

	/**
	 * 获取全屋方案和推荐方案所有数量
	 * @param designPlanRecommended
	 * @return
	 */
	Integer getFullHousePlanAndRecommendPlanCount(DesignPlanRecommended designPlanRecommended);

	/**
	 * 查询全屋方案和推荐方案所有集合
	 * @param designPlanRecommended
	 * @return
	 */
	List<DesignPlanRecommendedResult> getFullHousePlanAndRecommendPlanList(DesignPlanRecommended designPlanRecommended);


    List<Integer> selectDesignPlanIdsFromRecord(Integer userId);

    List<DesignPlanRecommended> bacchQueryByIds(@Param("planRecommendedIds") Set<Integer> planRecommendedIds);
}
