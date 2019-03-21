package com.sandu.search.storage.product;

import com.sandu.search.entity.elasticsearch.po.metadate.ProductGroupRelPo;
import com.sandu.search.exception.MetaDataException;
import com.sandu.search.service.metadata.MetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 产品组合关系元数据存储
 *
 * @date 20171225
 * @auth pengxuangang
 */
@Slf4j
@Component
public class ProductGroupRelMetaDataStorage {

    private final static String CLASS_LOG_PREFIX = "产品组合关系元数据存储:";
    //产品组合关系元数据map<groupId, List<ProductGroupRelPo>>>
    private static Map<Integer, List<ProductGroupRelPo>> productGroupRelMap = new HashMap<>();
    //元数据服务
    private final MetaDataService metaDataService;

    @Autowired
    public ProductGroupRelMetaDataStorage(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    /**
     * 更新数据
     * @param isEnforceLoad 是否强制更新
     */
    public void updateData(boolean isEnforceLoad) {

        //若无强制更新则更新前判断是否已有数据
        if (!isEnforceLoad) {
            if (null != productGroupRelMap && 0 < productGroupRelMap.size()) {
                return;
            }
        }

        //产品组合关系元数据
        List<ProductGroupRelPo> productGroupRelList;
        try {
            //获取数据
            log.info(CLASS_LOG_PREFIX + "开始获取产品组合关系元数据....");
            productGroupRelList = metaDataService.queryProductGroupRelMetaData();
            log.info(CLASS_LOG_PREFIX + "获取产品组合关系元数据完成,总条数:{}", (null == productGroupRelList ? 0 : productGroupRelList.size()));
        } catch (MetaDataException e) {
            log.error(CLASS_LOG_PREFIX + "获取产品组合关系元数据失败: MetaDataException:{}", e);
            throw new NullPointerException(CLASS_LOG_PREFIX + "获取产品组合关系元数据失败,List<ProductStylePo> is null.MetaDataException:" + e);
        }

        //临时对象
        Map<Integer, List<ProductGroupRelPo>> tempProductGroupRelMap = new HashMap<>();

        //转换为Map元数据
        if (null != productGroupRelList && 0 != productGroupRelList.size()) {
            productGroupRelList.forEach(productGroupRel -> {
                //产品组合ID
                int productGroupId = productGroupRel.getProductGroupId();
                if (0 != productGroupId) {
                    if (tempProductGroupRelMap.containsKey(productGroupId)) {
                        List<ProductGroupRelPo> productGroupRelPoList = new ArrayList<>();
                        productGroupRelPoList.addAll(tempProductGroupRelMap.get(productGroupId));
                        tempProductGroupRelMap.put(productGroupId, productGroupRelPoList);
                    } else {
                        tempProductGroupRelMap.put(productGroupRel.getProductGroupId(), Collections.singletonList(productGroupRel));
                    }
                }
            });

            //装回对象
            productGroupRelMap = null;
            productGroupRelMap = tempProductGroupRelMap;
        }
    }
}
