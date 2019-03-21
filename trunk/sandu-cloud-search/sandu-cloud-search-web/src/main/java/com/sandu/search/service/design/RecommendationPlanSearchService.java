package com.sandu.search.service.design;

import com.sandu.search.entity.common.PageVo;
import com.sandu.search.entity.designplan.vo.RecommendationPlanSearchVo;
import com.sandu.search.entity.elasticsearch.index.RecommendationPlanIndexMappingData;
import com.sandu.search.entity.elasticsearch.response.SearchObjectResponse;
import com.sandu.search.exception.RecommendationPlanSearchException;

/**
 * 产品搜索服务
 *
 * @date 20180103
 * @auth pengxuangang
 */
public interface RecommendationPlanSearchService {

    /**
     * 通过条件搜索设计方案
     *
     * @param recommendationPlanVo                     设计方案搜索匹配对象
     * @param pageVo                                   分页对象
     * @return
     * @throws RecommendationPlanSearchException       设计方案搜索异常
     */
    SearchObjectResponse searchRecommendationPlan(RecommendationPlanSearchVo recommendationPlanVo, PageVo pageVo) throws RecommendationPlanSearchException;

    /**
     * 通过设计方案ID搜索设计方案
     *
     * @param recommendationPlanId                         设计方案ID
     * @return
     * @throws RecommendationPlanSearchException           设计方案搜索异常
     */
    RecommendationPlanIndexMappingData searchRecommendationPlanById(Integer recommendationPlanId) throws RecommendationPlanSearchException;

}
