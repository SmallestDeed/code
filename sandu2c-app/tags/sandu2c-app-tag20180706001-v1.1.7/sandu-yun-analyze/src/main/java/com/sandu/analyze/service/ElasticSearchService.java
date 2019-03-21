package com.sandu.analyze.service;

import com.sandu.analyze.entity.elasticseatch.dto.*;
import com.sandu.analyze.entity.elasticseatch.response.BulkStatistics;
import com.sandu.analyze.entity.elasticseatch.response.SearchObjectResponse;
import com.sandu.analyze.exception.ElasticSearchException;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import java.util.List;

/**
 * 搜索引擎服务
 *
 * @date 20171211
 * @auth pengxuangang
 */
public interface ElasticSearchService {

    /**
     * 索引数据
     *
     * @param indexRequestDTO 索引对象
     * @return
     * @throws ElasticSearchException
     */
    boolean index(IndexRequestDTO indexRequestDTO) throws ElasticSearchException;

    /**
     * 索引数据
     *
     * @param indexRequestDTO 索引对象
     * @param headers         请求头
     * @return
     * @throws ElasticSearchException
     */
    boolean index(IndexRequestDTO indexRequestDTO, org.apache.http.Header... headers) throws ElasticSearchException;

    /**
     * 获取数据
     *
     * @param getRequestDTO 请求对象
     * @return
     * @throws ElasticSearchException
     */
    Object get(GetRequestDTO getRequestDTO, Class clazz) throws ElasticSearchException;

    /**
     * 获取数据
     *
     * @param getRequestDTO 请求对象
     * @param headers       请求头
     * @return
     * @throws ElasticSearchException
     */
    Object get(GetRequestDTO getRequestDTO, Class clazz, org.apache.http.Header... headers) throws ElasticSearchException;

    /**
     * 删除数据
     *
     * @param deleteRequestDTO 索引对象
     * @return
     * @throws ElasticSearchException
     */
    boolean delete(DeleteRequestDTO deleteRequestDTO) throws ElasticSearchException;

    /**
     * 删除数据
     *
     * @param deleteRequestDTO 索引对象
     * @param headers          请求头
     * @return
     * @throws ElasticSearchException
     */
    boolean delete(DeleteRequestDTO deleteRequestDTO, org.apache.http.Header... headers) throws ElasticSearchException;

    /**
     * 批量操作
     *
     * @param bulkRequestList 操作List
     * @return
     * @throws ElasticSearchException
     */
    BulkStatistics bulk(List<Object> bulkRequestList) throws ElasticSearchException;

    /**
     * 批量操作
     *
     * @param bulkRequestList 操作List
     * @param headers         请求头
     * @return
     * @throws ElasticSearchException
     */
    BulkStatistics bulk(List<Object> bulkRequestList, org.apache.http.Header... headers) throws ElasticSearchException;

    /**
     * 搜索分类数据
     *
     * @param index                 索引
     * @param matchQueryBuilderList 搜索条件
     * @param start                 数据起始数
     * @param dataSize              数据条数
     * @return
     * @throws ElasticSearchException
     */
    SearchObjectResponse search(String index, List<QueryBuilder> matchQueryBuilderList, int start, int dataSize) throws ElasticSearchException;

    /**
     * 搜索分类数据
     *
     * @param index                   索引
     * @param matchQueryBuilderList   搜索条件
     * @param noMatchQueryBuilderList 不匹配的搜索条件
     * @param start                   数据起始数
     * @param dataSize                数据条数
     * @return
     * @throws ElasticSearchException
     */
    SearchObjectResponse search(String index, List<QueryBuilder> matchQueryBuilderList, List<QueryBuilder> noMatchQueryBuilderList, int start, int dataSize) throws ElasticSearchException;

    /**
     * 搜索分类数据
     *
     * @param index                   索引
     * @param matchQueryBuilderList   搜索条件
     * @param noMatchQueryBuilderList 不匹配的搜索条件
     * @param shouldMatchSearchList   或匹配条件
     * @param start                   数据起始数
     * @param dataSize                数据条数
     * @return
     * @throws ElasticSearchException
     */
    SearchObjectResponse search(String index, List<QueryBuilder> matchQueryBuilderList, List<QueryBuilder> noMatchQueryBuilderList, List<ShouldMatchSearch> shouldMatchSearchList, int start, int dataSize) throws ElasticSearchException;

    /**
     * 搜索分类数据
     *
     * @param index                   索引
     * @param matchQueryBuilderList   搜索条件
     * @param noMatchQueryBuilderList 不匹配的搜索条件
     * @param shouldMatchSearchList   或匹配条件
     * @param aggregationBuilderList  聚合条件
     * @param start                   数据起始数
     * @param dataSize                数据条数
     * @return
     * @throws ElasticSearchException
     */
    SearchObjectResponse search(String index, List<QueryBuilder> matchQueryBuilderList, List<QueryBuilder> noMatchQueryBuilderList, List<ShouldMatchSearch> shouldMatchSearchList, List<AggregationBuilder> aggregationBuilderList, int start, int dataSize) throws ElasticSearchException;

    /**
     * 搜索分类数据
     *
     * @param index                   索引
     * @param matchQueryBuilderList   搜索条件
     * @param noMatchQueryBuilderList 不匹配的搜索条件
     * @param shouldMatchSearchList   或匹配条件
     * @param aggregationBuilderList  聚合条件
     * @param sortOrderObject         排序对象
     * @param start                   数据起始数
     * @param dataSize                数据条数
     * @return
     * @throws ElasticSearchException
     */
    SearchObjectResponse search(String index, List<QueryBuilder> matchQueryBuilderList, List<QueryBuilder> noMatchQueryBuilderList, List<ShouldMatchSearch> shouldMatchSearchList, List<AggregationBuilder> aggregationBuilderList, SortOrderObject sortOrderObject, int start, int dataSize) throws ElasticSearchException;
}