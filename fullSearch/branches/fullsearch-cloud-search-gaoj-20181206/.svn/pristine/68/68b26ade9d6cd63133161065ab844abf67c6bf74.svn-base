package com.sandu.search.datasync.handler;

import com.sandu.search.common.tools.EntityUtil;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.constant.TypeConstant;
import com.sandu.search.entity.elasticsearch.dto.UpdateRequestDTO;
import com.sandu.search.entity.elasticsearch.index.GroupProductIndexMappingData;
import com.sandu.search.entity.elasticsearch.po.GroupProductPO;
import com.sandu.search.entity.elasticsearch.response.BulkStatistics;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.exception.GroupProductIndexException;
import com.sandu.search.initialize.GroupProductIndex;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import com.sandu.search.service.index.GroupProductIndexService;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GroupProductMessageHandler {

    private final static String CLASS_LOG_PREFIX = "Rabbit组合产品消息处理器:";

    //待更新组合产品列表
    private static volatile List<Integer> waitUpdateGroupProductIdList = new ArrayList<>();

    private final ElasticSearchService elasticSearchService;
    private final StorageComponent storageComponent;
    private final GroupProductIndexService groupProductIndexService;
    private final GroupProductIndex groupProductIndex;

    @Autowired
    public GroupProductMessageHandler(ElasticSearchService elasticSearchService,
                                      StorageComponent storageComponent,
                                      GroupProductIndexService groupProductIndexService,
                                      GroupProductIndex groupProductIndex) {
        this.elasticSearchService = elasticSearchService;
        this.storageComponent = storageComponent;
        this.groupProductIndexService = groupProductIndexService;
        this.groupProductIndex = groupProductIndex;
    }


    public boolean addAndUpdate(List<GroupProductIndexMappingData> groupProductIndexMappingDataList) {
        if (null == groupProductIndexMappingDataList || 0 >= groupProductIndexMappingDataList.size()) {
            log.error(CLASS_LOG_PREFIX + "消费消息失败，消息对象为空");
            return false;
        }

        //根据组合产品id更新
        List<Integer> groupProductIdList = new ArrayList<>(groupProductIndexMappingDataList.size());
        //根据产品属性更新
        List<GroupProductIndexMappingData> groupProductList = new ArrayList<>(groupProductIndexMappingDataList.size());

        groupProductIndexMappingDataList.forEach(groupProduct -> {
            //判断更新类型
            List<String> fieldList = null;

            try {
                fieldList = EntityUtil.queryHasValueExcludeNameIsIdInField(groupProduct);
            } catch (IllegalAccessException e) {
                log.warn(CLASS_LOG_PREFIX + "判断组合产品更新类型失败,IllegalAccessException:{}", e);
            }

            if (null != fieldList && 0 < fieldList.size()) {
                //属性增量更新
                groupProductList.add(groupProduct);
            } else {
                //id更新
                groupProductIdList.add(groupProduct.getGroupId());
            }
        });

        //id更新
        if (null != groupProductIdList && 0 < groupProductIdList.size()) {
            waitUpdateGroupProductIdList.addAll(groupProductIdList);
            log.info(CLASS_LOG_PREFIX + "");
        }

        //属性增量更新
        if (null != groupProductList && 0 < groupProductList.size()) {
            //索引属性
            updateIncrGroupProductInfo(groupProductList);
            log.info(CLASS_LOG_PREFIX + "");
        }
        return true;
    }

    /**
     * 属性增量更新
     *
     * @param groupProductList
     * @return
     */
    private boolean updateIncrGroupProductInfo(List<GroupProductIndexMappingData> groupProductList) {
        if (null == groupProductList || 0 >= groupProductList.size()) {
            return false;
        }

        List<Object> bulkUpdateList = new ArrayList<>();

        groupProductList.forEach(groupProduct -> {
            if (null == groupProduct || null == groupProduct.getGroupId() || 0 >= groupProduct.getGroupId()) {
                return;
            }

            UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO(IndexConstant.GROUP_PRODUCT_INFO,
                    TypeConstant.TYPE_GROUP_PRODUCT, groupProduct.getGroupId().toString(), JsonUtil.toJson(groupProduct));

            bulkUpdateList.add(updateRequestDTO);
        });

        BulkStatistics bulkStatistics = null;
        try {
            bulkStatistics = elasticSearchService.bulk(bulkUpdateList, null);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "组合产品属性增量更新异常，ElasticSearchException：{}", e);
        }
        log.info(CLASS_LOG_PREFIX + "组合产品增量索引完成，共需索引数：{}，索引结果：{}", bulkUpdateList.size(), null == bulkStatistics ? null : bulkStatistics);

        return true;
    }

    /**
     * 更新组合产品信息
     *
     * @return
     */
    public boolean updateGroupProductInfo() {
        //去空
        waitUpdateGroupProductIdList = waitUpdateGroupProductIdList.stream().filter(groupProductId -> null != groupProductId && 0 < groupProductId).collect(Collectors.toList());
        //去重
        waitUpdateGroupProductIdList = waitUpdateGroupProductIdList.stream().distinct().collect(Collectors.toList());

        if (null != waitUpdateGroupProductIdList && 0 < waitUpdateGroupProductIdList.size()) {
            //把内存待更新集合导出之后清空
            List<Integer> tempUpdateGroupProductIdList = waitUpdateGroupProductIdList;
            waitUpdateGroupProductIdList.clear();

            //更新必要的元数据
            storageComponent.reloadAllStorageInCache(StorageComponent.ChangeStorageModeModules.groupProduct);

            //根据id集合查找组合产品集合
            List<GroupProductPO> groupProductList;
            try {
                groupProductList = groupProductIndexService.queryGroupProductListByIdList(tempUpdateGroupProductIdList);
            } catch (GroupProductIndexException e) {
                log.error(CLASS_LOG_PREFIX + "根据组合产品id集合获取组合产品数据异常，exception：{}", e);
                //失败了再把原始集合重新添加进内存
                waitUpdateGroupProductIdList.addAll(tempUpdateGroupProductIdList);
                return false;
            }

            //调用组合产品索引的方法索引数据
            log.info(CLASS_LOG_PREFIX + "开始更新数据，数据总条数:{}.", groupProductList.size());
            int successCount = groupProductIndex.indexGroupProdcutData(groupProductList);
            log.info(CLASS_LOG_PREFIX + "更新数据完成，成功{}条,失败{}条.", successCount, groupProductList.size() - successCount);
        } else {
            //更新元数据
            storageComponent.reloadAllStorageInCache(StorageComponent.ChangeStorageModeModules.groupProduct);
        }
        return true;
    }

    public boolean delete(Object o) {
        return true;
    }
}
