package com.sandu.search.service.design.impl;

import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.constant.QueryConditionField;
import com.sandu.search.entity.common.PageVo;
import com.sandu.search.entity.designplan.vo.RecommendationPlanSearchVo;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.index.RecommendationPlanIndexMappingData;
import com.sandu.search.entity.elasticsearch.response.SearchObjectResponse;
import com.sandu.search.entity.elasticsearch.search.ShouldMatchSearch;
import com.sandu.search.entity.elasticsearch.search.SortOrderObject;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.exception.RecommendationPlanSearchException;
import com.sandu.search.service.design.RecommendationPlanSearchService;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 设计方案搜索服务
 *
 * @date 20180103
 * @auth pengxuangang
 */
@Slf4j
@Service("recommendationPlanSearchService")
public class RecommendationPlanSearchServiceImpl implements RecommendationPlanSearchService {

    private final static String CLASS_LOG_PREFIX = "设计方案搜索服务:";

    private final ElasticSearchService elasticSearchService;

    @Autowired
    public RecommendationPlanSearchServiceImpl(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @Override
    public SearchObjectResponse searchRecommendationPlan(RecommendationPlanSearchVo recommendationPlanSearchVo, PageVo pageVo) throws RecommendationPlanSearchException {

        if (null == recommendationPlanSearchVo || null == pageVo) {
            log.warn(CLASS_LOG_PREFIX + "通过条件搜索设计方案失败，必需参数为空.");
            throw new RecommendationPlanSearchException(CLASS_LOG_PREFIX + "通过条件搜索设计方案失败，必需参数为空.");
        }

        /********************************* Step 1: 组装搜索条件 *************************************************/
        //匹配条件List
        List<QueryBuilder> matchQueryList = new ArrayList<>();
        //非匹配条件List
        List<QueryBuilder> noMatchQueryList = new ArrayList<>(1);
        //或匹配条件List
        List<ShouldMatchSearch> shouldMatchSearchList = new ArrayList<>();

        //公司
        Integer companyId = recommendationPlanSearchVo.getCompanyId();
        if (null != companyId && 0 < companyId) {
            matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_COMPANY_ID, companyId));
        }

        //房屋类型
        Integer houseType = recommendationPlanSearchVo.getHouseType();
        if (null != houseType && 0 < houseType) {
            matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_HOUSE_TYPE, houseType));
        }

        //推荐方案类型
        Integer recommendationPlanType = recommendationPlanSearchVo.getRecommendationPlanType();
        if (null != recommendationPlanType && 0 < recommendationPlanType) {
            matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_RECOMMENDATIONPLAN_TYPE, recommendationPlanType));
        }

        //发布状态
        Integer releaseStatus = recommendationPlanSearchVo.getReleaseStatus();
        if (null != releaseStatus) {
            matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_RELEASE_STATUS, releaseStatus));
        }

        //风格
        Integer designStyleId = recommendationPlanSearchVo.getDesignStyleId();
        if (null != designStyleId && 0 < designStyleId) {
            matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_DESIGN_STYLEID, designStyleId));
        }

        //空间面积
        String spaceAreas = recommendationPlanSearchVo.getSpaceAreas();
        if (!StringUtils.isEmpty(spaceAreas)) {
            matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_SPACE_AREAS, spaceAreas));
        }

        //发布时间
        SortOrderObject sortOrderObject = null;
        int sortType = recommendationPlanSearchVo.getSortType();
        if (RecommendationPlanSearchVo.RELEASE_TIME_SORT_ASC == sortType) {
            //ASC
            sortOrderObject = new SortOrderObject(
                    QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_PUBLISH_TIME,
                    SortOrder.ASC,
                    SortMode.MEDIAN
            );
        } else if (RecommendationPlanSearchVo.RELEASE_TIME_SORT_DESC == sortType) {
            //DSC
            sortOrderObject = new SortOrderObject(
                    QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_PUBLISH_TIME,
                    SortOrder.DESC,
                    SortMode.MEDIAN
            );
        }

        //方案来源
        List<String> planSourceList = recommendationPlanSearchVo.getPlanSource();
        if (null != planSourceList && 0 < planSourceList.size()) {
            noMatchQueryList.add(QueryBuilders.termsQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_SOURCE, planSourceList));
        }

        //数据未被删除
        matchQueryList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_STATUS, 0));

        /********************************* Step 2: 搜索数据 *************************************************/
        //搜索数据
        SearchObjectResponse searchObjectResponse;
        try {
            searchObjectResponse = elasticSearchService.search(matchQueryList, noMatchQueryList, shouldMatchSearchList, null, sortOrderObject, pageVo.getStart(), pageVo.getPageSize(), IndexConstant.RECOMMENDATION_PLAN_INFO);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "通过条件搜索设计方案失败! ElasticSearchException:{}.", e);
            throw new RecommendationPlanSearchException(CLASS_LOG_PREFIX + "通过条件搜索设计方案失败! ElasticSearchException:{}.", e);
        }
        log.info(CLASS_LOG_PREFIX + "通过条件搜索设计方案列表成功!SearchObjectResponse:{}.", searchObjectResponse.getHitTotal());

        if (null == searchObjectResponse.getHitResult()) {
            log.info(CLASS_LOG_PREFIX + "通过条件搜索设计方案失败,查询结果为空。RecommendationPlanVo:{}.", recommendationPlanSearchVo.toString());
            throw new RecommendationPlanSearchException(CLASS_LOG_PREFIX + "通过条件搜索设计方案列表失败,查询结果为空。RecommendationPlanVo:" + recommendationPlanSearchVo.toString());
        }

        return searchObjectResponse;
    }

    @Override
    public RecommendationPlanIndexMappingData searchRecommendationPlanById(Integer recommendationPlanId) throws RecommendationPlanSearchException {

        if (null == recommendationPlanId || 0 >= recommendationPlanId) {
            return null;
        }

        //构造查询体
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(QueryConditionField.QUERY_CONDITION_FIELD_RECOMMENDATION_PLAN_ID, recommendationPlanId);

        //查询数据
        SearchObjectResponse searchObjectResponse;
        try {
            searchObjectResponse = elasticSearchService.search(Collections.singletonList(matchQueryBuilder), null, null, null, null, IndexInfoQueryConfig.DEFAULT_SEARCH_DATA_START, 1, IndexConstant.RECOMMENDATION_PLAN_INFO);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "通过设计方案ID查询产设计方案失败，QueryBuilder:{}, ElasticSearchException:{}.", matchQueryBuilder.toString(), e);
            throw new RecommendationPlanSearchException(CLASS_LOG_PREFIX + "通过设计方案ID查询设计方案失败，QueryBuilder:" + matchQueryBuilder.toString() + ", ElasticSearchException:" + e);
        }

        if (null != searchObjectResponse) {
            List<RecommendationPlanIndexMappingData> recommendationPlanIndexMappingDataList = (List<RecommendationPlanIndexMappingData>) searchObjectResponse.getHitResult();
            if (null != recommendationPlanIndexMappingDataList && 0 < recommendationPlanIndexMappingDataList.size()) {
                return recommendationPlanIndexMappingDataList.get(0);
            }
        }

        return null;
    }
}
