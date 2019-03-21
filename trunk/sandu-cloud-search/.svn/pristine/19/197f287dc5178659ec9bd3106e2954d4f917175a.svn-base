package com.sandu.search.initialize;

import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.constant.SystemDictionaryType;
import com.sandu.search.common.tools.EntityCopyUtils;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.config.ElasticSearchConfig;
import com.sandu.search.entity.designplan.po.RecommendationPlanPo;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.constant.TypeConstant;
import com.sandu.search.entity.elasticsearch.dto.IndexRequestDTO;
import com.sandu.search.entity.elasticsearch.index.RecommendationPlanIndexMappingData;
import com.sandu.search.entity.elasticsearch.po.metadate.SpaceCommonPo;
import com.sandu.search.entity.elasticsearch.response.BulkStatistics;
import com.sandu.search.exception.DesignPlanIndexException;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import com.sandu.search.service.index.DesignPlanIndexService;
import com.sandu.search.storage.StorageComponent;
import com.sandu.search.storage.company.BrandMetaDataStorage;
import com.sandu.search.storage.company.CompanyMetaDataStorage;
import com.sandu.search.storage.design.DesignPlanBrandMetaDataStorage;
import com.sandu.search.storage.design.DesignTemplateMetaDataStorage;
import com.sandu.search.storage.product.ProductStyleMetaDataStorage;
import com.sandu.search.storage.resource.RenderPicMetaDataStorage;
import com.sandu.search.storage.space.SpaceCommonMetaDataStorage;
import com.sandu.search.storage.system.SystemDictionaryMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 搜索引擎推荐方案索引初始化
 *
 * @date 20180531
 * @auth pengxuangang
 */
@Slf4j
@Component
public class RecommendationPlanIndex {

    private final static String CLASS_LOG_PREFIX = "搜索引擎推荐方案索引初始化:";

    private final StorageComponent storageComponent;
    private final BrandMetaDataStorage brandMetaDataStorage;
    private final ElasticSearchConfig elasticSearchConfig;
    private final ElasticSearchService elasticSearchService;
    private final DesignPlanIndexService designPlanIndexService;
    private final RenderPicMetaDataStorage renderPicMetaDataStorage;
    private final DesignPlanBrandMetaDataStorage designPlanBrandMetaDataStorage;
    private final DesignTemplateMetaDataStorage designTemplateMetaDataStorage;
    private final SpaceCommonMetaDataStorage spaceCommonMetaDataStorage;
    private final ProductStyleMetaDataStorage productStyleMetaDataStorage;
    private final SystemDictionaryMetaDataStorage systemDictionaryMetaDataStorage;
    private final CompanyMetaDataStorage companyMetaDataStorage;

    @Autowired
    public RecommendationPlanIndex(StorageComponent storageComponent, BrandMetaDataStorage brandMetaDataStorage, ElasticSearchConfig elasticSearchConfig, ElasticSearchService elasticSearchService, DesignPlanIndexService designPlanIndexService, RenderPicMetaDataStorage renderPicMetaDataStorage, DesignPlanBrandMetaDataStorage designPlanBrandMetaDataStorage, DesignTemplateMetaDataStorage designTemplateMetaDataStorage, SpaceCommonMetaDataStorage spaceCommonMetaDataStorage, ProductStyleMetaDataStorage productStyleMetaDataStorage, SystemDictionaryMetaDataStorage systemDictionaryMetaDataStorage, CompanyMetaDataStorage companyMetaDataStorage) {
        this.storageComponent = storageComponent;
        this.brandMetaDataStorage = brandMetaDataStorage;
        this.elasticSearchConfig = elasticSearchConfig;
        this.elasticSearchService = elasticSearchService;
        this.designPlanIndexService = designPlanIndexService;
        this.renderPicMetaDataStorage = renderPicMetaDataStorage;
        this.designPlanBrandMetaDataStorage = designPlanBrandMetaDataStorage;
        this.designTemplateMetaDataStorage = designTemplateMetaDataStorage;
        this.spaceCommonMetaDataStorage = spaceCommonMetaDataStorage;
        this.productStyleMetaDataStorage = productStyleMetaDataStorage;
        this.systemDictionaryMetaDataStorage = systemDictionaryMetaDataStorage;
        this.companyMetaDataStorage = companyMetaDataStorage;
    }

    /**
     * 同步推荐方案数据
     */
    public void syncRecommendationPlanData() {

        log.info(CLASS_LOG_PREFIX + "开始索引推荐方案数据........");
        //开始时间
        long startTime = System.currentTimeMillis();


        List<RecommendationPlanPo> recommendationPlanList;
        /********************************** 查询推荐方案信息 *********************************/
        //更新全量数据
        try {
            recommendationPlanList = designPlanIndexService.queryRecommendationPlanList();
        } catch (DesignPlanIndexException e) {
            log.error(CLASS_LOG_PREFIX + "查询推荐方案信息失败:DesignPlanIndexException:{}", e);
            return;
        }

        //索引推荐方案数据
        int successIndexCount = indexRecommendationPlanData(recommendationPlanList);

        log.info(CLASS_LOG_PREFIX + "索引所有产品分类数据完成!!!产品数据量:{},共耗时:{}ms", new String[]{
                successIndexCount + "",
                (System.currentTimeMillis() - startTime) + ""
        });
    }

    /**
     * 索引推荐方案数据
     *
     * @param recommendationPlanList 推荐方案对象
     * @return 索引成功数
     */
    public int indexRecommendationPlanData(List<RecommendationPlanPo> recommendationPlanList) {

        log.info(CLASS_LOG_PREFIX + "开始解析推荐方案数据...");
        if (null == recommendationPlanList || 0 >= recommendationPlanList.size()) {
            return 0;
        }

        //批量提交数据对象
        List<Object> bulkIndexList = new ArrayList<>(IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT);

        /********************************** 处理产品分类信息数据 *********************************/
        for (int i = 0; i < recommendationPlanList.size(); i++) {
            RecommendationPlanPo recommendationPlanPo = recommendationPlanList.get(i);
            //定义索引数据对象
            RecommendationPlanIndexMappingData recommendationPlanIndexMappingData = new RecommendationPlanIndexMappingData();

            //复制基本对象
            EntityCopyUtils.copyData(recommendationPlanPo, recommendationPlanIndexMappingData);

            //封面图片
            Integer coverPicId = recommendationPlanPo.getCoverPicId();
            if (null != coverPicId && 0 < coverPicId) {
                recommendationPlanIndexMappingData.setCoverPicPath(renderPicMetaDataStorage.getPicPathByPicId(coverPicId));
            }

            //品牌ID
            String brandIdStr = designPlanBrandMetaDataStorage.getBrandIdByDesignPlanId(recommendationPlanPo.getId());
            if (!StringUtils.isEmpty(brandIdStr) && !"null".equals(brandIdStr) && !"-1".equals(brandIdStr)) {
                int brandId = Integer.parseInt(brandIdStr);
                //ID
                recommendationPlanIndexMappingData.setBrandId(brandId);
                //名
                recommendationPlanIndexMappingData.setBrandName(brandMetaDataStorage.getBrandNameById(brandId));
            }

            //公司ID
            String companyIdStr = designPlanBrandMetaDataStorage.getCompanyIdByRecommendationPlanId(recommendationPlanPo.getId());
            if (!StringUtils.isEmpty(companyIdStr) && !"null".equals(companyIdStr) && !"-1".equals(companyIdStr)) {
                int companyId = Integer.parseInt(companyIdStr);
                //ID
                recommendationPlanIndexMappingData.setCompanyId(companyId);
                //名
                recommendationPlanIndexMappingData.setCompanyName(companyMetaDataStorage.getCompanyNameByCompanyId(companyId));
            }

            //空间ID
            Integer designTemplateId = recommendationPlanPo.getDesignTemplateId();
            if (null != designTemplateId && 0 < designTemplateId) {
                int spaceCommonId = designTemplateMetaDataStorage.getSpaceCommonIdByDesignPlanTemplateId(designTemplateId);
                if (0 < spaceCommonId) {
                    recommendationPlanIndexMappingData.setSpaceCommonId(spaceCommonId);
                }
            }

            //空间数据
            Integer spaceCommonId = recommendationPlanIndexMappingData.getSpaceCommonId();
            if (null != spaceCommonId && 0 < spaceCommonId) {
                //获取空间数据
                SpaceCommonPo spaceCommon = spaceCommonMetaDataStorage.getSpaceCommonBySpaceCommonId(spaceCommonId);
                if (null != spaceCommon) {
                    //类型-面积-形状
                    recommendationPlanIndexMappingData.setSpaceFunctionId(spaceCommon.getSpaceFunctionId());
                    recommendationPlanIndexMappingData.setSpaceAreas(spaceCommon.getSpaceAreas());
                    recommendationPlanIndexMappingData.setSpaceShape(spaceCommon.getSpaceShape());
                }
            }

            //风格名
            Integer designStyleId = recommendationPlanIndexMappingData.getDesignStyleId();
            if (null != designStyleId && 0 < designStyleId) {
                String styleName = productStyleMetaDataStorage.getProductStyleNameById(designStyleId);
                if (!StringUtils.isEmpty(styleName)) {
                    recommendationPlanIndexMappingData.setStyleName(styleName);
                }
            }

            //房间类型
            Integer spaceFunctionId = recommendationPlanIndexMappingData.getSpaceFunctionId();
            if (null != spaceFunctionId && 0 < spaceFunctionId) {
                String houseTypeName = systemDictionaryMetaDataStorage.getSystemDictionaryNameByTypeAndValue(SystemDictionaryType.SYSTEM_DICTIONARY_TYPE_HOUSETYPE, spaceFunctionId);
                if (!StringUtils.isEmpty(houseTypeName)) {
                    recommendationPlanIndexMappingData.setHouseTypeName(houseTypeName);
                }
            }

            //适用空间面积
            String applySpaceAreaStr = recommendationPlanPo.getApplySpaceAreas();
            if (!StringUtils.isEmpty(applySpaceAreaStr)) {
                recommendationPlanIndexMappingData.setApplySpaceAreaList(Arrays.asList(applySpaceAreaStr.split(",")));
            }

            //数据状态
            Integer dataIsDeleted = recommendationPlanPo.getDataIsDeleted();
            if (null != dataIsDeleted) {
                recommendationPlanIndexMappingData.setDataIsDeleted(dataIsDeleted);
            }

            //创建索引对象
            IndexRequestDTO indexRequestDTO = new IndexRequestDTO(
                    IndexConstant.RECOMMENDATION_PLAN_INFO,
                    TypeConstant.TYPE_DEFAULT,
                    recommendationPlanIndexMappingData.getId() + "",
                    JsonUtil.toJson(recommendationPlanIndexMappingData)
            );

            //加入批量对象
            bulkIndexList.add(indexRequestDTO);

            //Count
            if (0 == i % 100) {
                log.info(CLASS_LOG_PREFIX + "已解析{}条推荐方案数据,剩余{}条", i, recommendationPlanList.size() - i);
            }
        }

        log.info(CLASS_LOG_PREFIX + "解析推荐方案数据完成，开始索引数据...");
        //索引数据
        BulkStatistics bulkStatistics = null;
        try {
            bulkStatistics = elasticSearchService.bulk(bulkIndexList, null);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "索引推荐方案数据异常:ElasticSearchException:{}", e);
        }
        log.info(CLASS_LOG_PREFIX + "索引推荐方案数据成功:成功索引数:{},BulkStatistics:{}", new String[]{
                bulkIndexList.size() + "",
                null == bulkStatistics ? null : bulkStatistics.toString()
        });

        return bulkIndexList.size();
    }
}
