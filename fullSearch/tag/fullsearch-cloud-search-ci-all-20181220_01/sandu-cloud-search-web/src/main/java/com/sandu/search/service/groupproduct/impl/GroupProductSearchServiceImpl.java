package com.sandu.search.service.groupproduct.impl;

import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.constant.PlatformConstant;
import com.sandu.search.common.constant.QueryConditionField;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.index.GroupProductIndexMappingData;
import com.sandu.search.entity.elasticsearch.response.SearchObjectResponse;
import com.sandu.search.entity.groupproduct.GroupProductSearchVo;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.exception.GroupProductSearchException;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import com.sandu.search.service.groupproduct.GroupProductSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("groupProductSearchService")
@Slf4j
public class GroupProductSearchServiceImpl implements GroupProductSearchService {

    private final static String CLASS_LOG_PREFIX = "组合产品搜索服务";

    private final ElasticSearchService elasticSearchService;

    @Autowired
    public GroupProductSearchServiceImpl(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @Override
    public GroupProductIndexMappingData searchGroupProductById(Integer groupId) throws GroupProductSearchException{
        if (null == groupId || 0 >= groupId) {
            log.error(CLASS_LOG_PREFIX + "通过组合id获取组合，groupId为空：{}", groupId);
            return null;
        }

        List<QueryBuilder> matchQueryBuilderList = new ArrayList<>();
        //构造查询体--组合id
        QueryBuilder idQueryBuilder = QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_ID, groupId);
        matchQueryBuilderList.add(idQueryBuilder);
        //构造查询体--未删除
        QueryBuilder isDeletedQueryBuilder = QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_ID_DELETED, 0);
        matchQueryBuilderList.add(isDeletedQueryBuilder);

        SearchObjectResponse searchObjectResponse;
        try {
            searchObjectResponse = elasticSearchService.search(matchQueryBuilderList,null,null,null,null, IndexInfoQueryConfig.DEFAULT_SEARCH_DATA_START,1, IndexConstant.GROUP_PRODUCT_INFO);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "通过组合id搜索组合异常,QueryBuilderList={},exception={}", matchQueryBuilderList, e);
            throw new GroupProductSearchException(CLASS_LOG_PREFIX + "搜索组合异常，QueryBuilderList="+matchQueryBuilderList+",exception="+e);
        }

        if (null != searchObjectResponse) {
            List<GroupProductIndexMappingData> list = (List<GroupProductIndexMappingData>) searchObjectResponse.getHitResult();
            if (null != list && 0 < list.size()) {
                return list.get(0);
            }
        }

        return null;
    }

    @Override
    public GroupProductIndexMappingData searchGroupProduct(GroupProductSearchVo groupProductSearchVo) throws GroupProductSearchException {
        if (null == groupProductSearchVo) {
            log.error(CLASS_LOG_PREFIX + "searchGroupProduct 参数为null");
            return null;
        }

        List<QueryBuilder> matchQueryBuilderList = new ArrayList<>();

        //组合id过滤
        Integer groupId = groupProductSearchVo.getGroupId();
        if (null != groupId && 0 != groupId) {
            matchQueryBuilderList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_ID,groupId));
        }

        //组合产品平台及上下架发布过滤
        BoolQueryBuilder boolQueryBuilder = getPlatformQuery(groupProductSearchVo);
        if (null != boolQueryBuilder) {
            matchQueryBuilderList.add(boolQueryBuilder);
        }

        //组合类型
        matchQueryBuilderList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_GROUP_TYPE,groupProductSearchVo.getGroupType()));

        //构造查询体--未删除
        matchQueryBuilderList.add(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_ID_DELETED, 0));

        SearchObjectResponse searchObjectResponse;
        try {
            searchObjectResponse = elasticSearchService.search(matchQueryBuilderList,null,null,null,null, IndexInfoQueryConfig.DEFAULT_SEARCH_DATA_START,1, IndexConstant.GROUP_PRODUCT_INFO);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "通过组合id搜索组合异常,QueryBuilderList={},exception={}", matchQueryBuilderList, e);
            throw new GroupProductSearchException(CLASS_LOG_PREFIX + "搜索组合异常，QueryBuilderList="+matchQueryBuilderList+",exception="+e);
        }

        if (null != searchObjectResponse) {
            List<GroupProductIndexMappingData> list = (List<GroupProductIndexMappingData>) searchObjectResponse.getHitResult();
            if (null != list && 0 < list.size()) {
                return list.get(0);
            }
        }

        return null;

    }

    private BoolQueryBuilder getPlatformQuery(GroupProductSearchVo groupProductSearchVo) {
        String platformCode = groupProductSearchVo.getPlatformCode();
        if (StringUtils.isNotBlank(platformCode)) {
            BoolQueryBuilder platformBoolQuery = new BoolQueryBuilder();
            //组合产品平台集合
            platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_CODE_LIST,platformCode));
            //分配状态
            Integer allotState = groupProductSearchVo.getAllotState();
            if (null != allotState) {
                switch (platformCode) {
                    //2B-移动端
                    case PlatformConstant.PLATFORM_CODE_TOB_MOBILE:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_TOB_MOBILE
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_ALLOT_STATE,
                                allotState
                        ));
                        break;
                    //2B-PC
                    case PlatformConstant.PLATFORM_CODE_TOB_PC:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_TOB_PC
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_ALLOT_STATE,
                                allotState
                        ));
                        break;
                    //品牌2C-网站
                    case PlatformConstant.PLATFORM_CODE_TOC_SITE:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_TOC_SITE
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_ALLOT_STATE,
                                allotState
                        ));
                        break;
                    //Mini-Program
                    case PlatformConstant.PLATFORM_CODE_MINI_PROGRAM:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_MINIPROGRAM
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_ALLOT_STATE,
                                allotState
                        ));
                        break;
                }
            }
            //上下架状态
            Integer putawayState = groupProductSearchVo.getPutawayState();
            if (null != putawayState) {
                switch (platformCode) {
                    //2B-移动端
                    case PlatformConstant.PLATFORM_CODE_TOB_MOBILE:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_TOB_MOBILE
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_PUTAWAY_STATE,
                                putawayState
                        ));
                        break;
                    //2B-PC
                    case PlatformConstant.PLATFORM_CODE_TOB_PC:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_TOB_PC
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_PUTAWAY_STATE,
                                putawayState
                        ));
                        break;
                    //品牌2C-网站
                    case PlatformConstant.PLATFORM_CODE_TOC_SITE:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_TOC_SITE
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_PUTAWAY_STATE,
                                putawayState
                        ));
                        break;
                    //Mini-Program
                    case PlatformConstant.PLATFORM_CODE_MINI_PROGRAM:
                        platformBoolQuery.must(QueryBuilders.termQuery(QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_MINIPROGRAM
                                        + "." +
                                        QueryConditionField.QUERY_CONDITION_FIELD_GROUP_PRODUCT_PLATFORM_PUTAWAY_STATE,
                                putawayState
                        ));
                        break;
                }
            }
            return platformBoolQuery;
        }
        return null;
    }


}
