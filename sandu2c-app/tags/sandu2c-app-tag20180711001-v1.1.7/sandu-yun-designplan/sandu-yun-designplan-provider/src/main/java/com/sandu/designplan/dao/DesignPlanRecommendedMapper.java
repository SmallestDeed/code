package com.sandu.designplan.dao;

import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.PlanRecommendedListQuery;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignPlanRecommendedMapper {


    int insertSelective(DesignPlanRecommended designPlanRecommended);

    int updateByPrimaryKeySelective(DesignPlanRecommended designPlanRecommended);

    int deleteByPrimaryKey(Integer id);

    DesignPlanRecommended selectByPrimaryKey(Integer id);

    List<DesignPlanRecommended> selectList(DesignPlanRecommended designPlanRecommended);

    int selectCount(DesignPlanRecommended designPlanRecommended);

    Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended);


    List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取该方案推荐的白膜产品的数量，如果大于0那么是m+3 未装修完成的推荐（m+3 是不装修直接渲染的快捷键）
     *
     * @param id
     * @return
     */
    int getRecommendedDecorateState(@Param("id") Integer id);
    /**
     * 获取方案推荐收藏夹数据量
     *
     * @param designPlanRecommended
     * @return
     */
    Integer getFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 获取方案推荐收藏夹列表
     *
     * @param designPlanRecommended
     * @return
     */
    List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended);

	List<DesignPlanRecommended> getStatusByIds(List<Long> ids);

	Long selectRenderCountById(Integer planRecommendedId);
	Integer getPlanRecommendCount(PlanRecommendedListQuery pq);
	List<DesignPlanRecommendedResult> getPlanRecommendList(PlanRecommendedListQuery pq);
	
	Integer getPlanRecommendOpenCount(PlanRecommendedListQuery pq);
	List<DesignPlanRecommendedResult> getPlanRecommendOpenList(PlanRecommendedListQuery pq);

    /**
     * 查询所有
     * @param planRecommendedId
     * @return
     */
    List<DesignPlanRecommendedResult> selectAllById(Integer planRecommendedId);
}
