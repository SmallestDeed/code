package com.sandu.search.initialize;

import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.tools.EntityCopyUtils;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.config.ElasticSearchConfig;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.constant.TypeConstant;
import com.sandu.search.entity.elasticsearch.dto.IndexRequestDTO;
import com.sandu.search.entity.elasticsearch.index.GroupProductIndexMappingData;
import com.sandu.search.entity.elasticsearch.po.groupproduct.GroupProductPlatformData;
import com.sandu.search.entity.elasticsearch.po.GroupProductPO;
import com.sandu.search.entity.elasticsearch.response.BulkStatistics;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.exception.ProductIndexException;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import com.sandu.search.service.index.GroupProductIndexService;
import com.sandu.search.storage.groupproduct.GroupProductPlatformMetaDataStorage;
import com.sandu.search.storage.groupproduct.GroupProductRelMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索引擎组合产品索引初始化
 *
 * @date 20171225
 * @auth zhengyoucai
 */
@Slf4j
@Component
public class GroupProductIndex {

    private final static String CLASS_LOG_PREFIX = "搜索引擎组合产品索引初始化:";

    private final ElasticSearchService elasticSearchService;
    private final GroupProductIndexService groupProductIndexService;
    private final ElasticSearchConfig elasticSearchConfig;
    private final GroupProductRelMetaDataStorage groupProductRelMetaDataStorage;
    private final GroupProductPlatformMetaDataStorage groupProductPlatformMetaDataStorage;

    @Autowired
    public GroupProductIndex(ElasticSearchService elasticSearchService, GroupProductIndexService groupProductIndexService,
                             ElasticSearchConfig elasticSearchConfig, GroupProductRelMetaDataStorage groupProductRelMetaDataStorage,
                             GroupProductPlatformMetaDataStorage groupProductPlatformMetaDataStorage) {
        this.elasticSearchService = elasticSearchService;
        this.groupProductIndexService = groupProductIndexService;
        this.elasticSearchConfig = elasticSearchConfig;
        this.groupProductRelMetaDataStorage = groupProductRelMetaDataStorage;
        this.groupProductPlatformMetaDataStorage = groupProductPlatformMetaDataStorage;
    }


    //同步组合产品信息数据
    public void syncGroupProductInfoData() {
        //开始时间
        long startTime = System.currentTimeMillis();
        //数据查询初始位
        int start = 0;
        //每次数据量
        int limit = IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT;

        //是否继续处理
        boolean isContinueHandler = true;
        //总数据量
        int totalProductCount = 0;
        //总索引量
        int totalIndexCount = 0;
        //异常数据
        int totalExceptionCount = 0;

        while (isContinueHandler) {

            List<GroupProductPO> groupProductPoList;
            /********************************** 查询组合产品信息 *********************************/
            try {
                groupProductPoList = groupProductIndexService.queryGroupProductList(start, limit);
            } catch (ProductIndexException e) {
                log.error(CLASS_LOG_PREFIX + "查询组合产品信息失败:GroupProductIndexException:{}", e);
                return;
            }
            //无数据中断操作
            if (null == groupProductPoList || 0 == groupProductPoList.size()) {
                log.info(CLASS_LOG_PREFIX + "查询组合产品息数据为空：start:{},limit:{}.", start, limit);
                return;
            }
            //数据不足指定数据量表示已查询出最后一条数据,终止循环
            if (groupProductPoList.size() < IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT) {
                isContinueHandler = false;
            }

            /********************************** 索引组合产品数据 *********************************/
            int successIndexCount = indexGroupProdcutData(groupProductPoList);

            //递增start下标
            start = start + limit;

            //累加数据量
            totalProductCount += groupProductPoList.size();
            totalIndexCount += groupProductPoList.size();
            totalExceptionCount += groupProductPoList.size() - successIndexCount;
        }


        log.info(CLASS_LOG_PREFIX + "索引所有组合产品数据完成!!!组合产品数据量:{}, 索引数据量:{},失败数:{},共耗时:{}ms", new String[]{
                totalProductCount + "",
                totalIndexCount + "",
                totalExceptionCount + "",
                (System.currentTimeMillis() - startTime) + ""
        });
    }

    //索引数据
    private int indexGroupProdcutData(List<GroupProductPO> groupProductPoList) {

        log.info("开始索引产品组合数据......");
        if (groupProductPoList == null || groupProductPoList.size() <= 0) {
            return 0;
        }

        String indexName = StringUtils.isEmpty(elasticSearchConfig.getReIndexGroupProductDataIndexName()) ? IndexConstant.GROUP_PRODUCT_INFO : elasticSearchConfig.getReIndexGroupProductDataIndexName();
        log.info("产品组合索引名称{}", indexName);

        //批量提交数据对象
        List<Object> bulkIndexList = new ArrayList<>(IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT);

        int failCount = 0;

        /********************************** 1.处理产组合产品基本信息数据 *********************************/
        // 组合产品索引数据对象
        GroupProductIndexMappingData groupProductIndexMappingData;

        for (GroupProductPO groupProduct : groupProductPoList) {
            Integer groupId = groupProduct.getId();
            //组合产品索引对象
            groupProductIndexMappingData = new GroupProductIndexMappingData();

            //复制基本对象
            EntityCopyUtils.copyData(groupProduct, groupProductIndexMappingData);

            //组合产品主键
            groupProductIndexMappingData.setGroupId(groupId);

            // 组合名,和参数name一样,方便U3D前端
            groupProductIndexMappingData.setProductName(groupProduct.getGroupName());

            /******************************** 2.处理组合产品相关产品属性 ***********************************/
            //组合产品主产品id
            Integer mainProductId = groupProductRelMetaDataStorage.getMainProductIdByGroupId(groupId);
            groupProductIndexMappingData.setProductId(mainProductId);


            /****************************   处理组合产品详细信息属性    *******************************/
            // 产品详细信息
            // List<GroupProductDetailPo> groupProductDetails = new ArrayList<>();
            // groupProductDetails = groupProductDetailsMetaDataStorage.getGroupProductDetailsList(groupProduct.getId());
            // groupProductIndexMappingData.setGroupProductDetails(groupProductDetails);

            /****************************   3.处理组合产品平台信息属性    *******************************/
            GroupProductPlatformData groupProductPlatformData = groupProductPlatformMetaDataStorage.queryGroupProductPlatformByGroupId(groupId);
            if (null != groupProductPlatformData) {
                //全平台数据
                EntityCopyUtils.copyData(groupProductPlatformData, groupProductIndexMappingData);
                //平台编码列表
                List<String> platformCodeList = new ArrayList<>();
                if (null != groupProductPlatformData.getPlatformGroupProductToBMobile()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductToBMobile().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductToBPc()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductToBPc().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductToCSite()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductToCSite().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductToCMobile()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductToCMobile().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductSanduManager()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductSanduManager().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductHouseDraw()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductHouseDraw().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductMerchantsManager()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductMerchantsManager().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductTest()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductTest().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductU3dPlugin()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductU3dPlugin().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductMiniProgram()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductMiniProgram().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductSelectDecoration()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductSelectDecoration().getPlatformCode());
                }
                if (null != groupProductPlatformData.getPlatformGroupProductPcToBCustom()) {
                    platformCodeList.add(groupProductPlatformData.getPlatformGroupProductPcToBCustom().getPlatformCode());
                }
                groupProductIndexMappingData.setPlatformCodeList(platformCodeList);
            }



            IndexRequestDTO indexRequestDTO = new IndexRequestDTO(
                    IndexConstant.GROUP_PRODUCT_INFO,
                    TypeConstant.TYPE_GROUP_PRODUCT,
                    groupProductIndexMappingData.getGroupId().toString(),
                    JsonUtil.toJson(groupProductIndexMappingData)
            );
            //加入批量对象
            bulkIndexList.add(indexRequestDTO);
        }

        //索引数据
        BulkStatistics bulkStatistics;
        try {
            bulkStatistics = elasticSearchService.bulk(bulkIndexList, null);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "索引组合产品数据异常:ElasticSearchException:{}", e);
            return 0;
        }
        log.info(CLASS_LOG_PREFIX + "索引组合产品数据成功:成功索引数:{},无效索引数:{},BulkStatistics:{}", new String[]{
                bulkIndexList.size() + "",
                failCount + "",
                bulkStatistics.toString()
        });

        return groupProductPoList.size() - failCount;
    }
}
