package com.sandu.search.storage.product;

import com.sandu.search.common.constant.RedisConstant;
import com.sandu.search.entity.elasticsearch.po.ProductTexturePo;
import com.sandu.search.exception.MetaDataException;
import com.sandu.search.service.metadata.MetaDataService;
import com.sandu.search.service.redis.RedisService;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品材质元数据存储
 *
 * @date 20171214
 * @auth pengxuangang
 */
@Slf4j
@Component
public class ProductTextureMetaDataStorage {

    private final static String CLASS_LOG_PREFIX = "产品材质元数据存储:";
    //默认缓存模式
    private static Integer STORAGE_MODE = StorageComponent.CACHE_MODE;

    private final RedisService redisService;
    private final MetaDataService metaDataService;

    @Autowired
    public ProductTextureMetaDataStorage(RedisService redisService, MetaDataService metaDataService) {
        this.redisService = redisService;
        this.metaDataService = metaDataService;
    }

    private static Map<String, String> productTexturePoMap = null;

    //切换存储模式
    public void changeStorageMode(Integer storageMode) {
        //缓存模式
        if (StorageComponent.CACHE_MODE == storageMode) {
            //清空内存占用
            productTexturePoMap = null;
            //切换
            STORAGE_MODE = storageMode;
            //内存模式
        } else if(StorageComponent.MEMORY_MODE == storageMode) {
            //切换
            STORAGE_MODE = storageMode;
            //写入内存
            updateData();
            //productTexturePoMap = redisService.getMap(RedisConstant.PRODUCT_TEXTURE_DATA);
        }
        log.info(CLASS_LOG_PREFIX + "产品材质存储模式切换成功，当前存储:{}.", StorageComponent.CACHE_MODE == STORAGE_MODE ? "缓存" : "内存");
    }

    //获取Map数据方法兼容
    private String getMap(String mapName, String keyName) {
        //缓存模式
        if (StorageComponent.CACHE_MODE == STORAGE_MODE) {
            return redisService.getMap(mapName, keyName);
            //内存模式
        } else if (StorageComponent.MEMORY_MODE == STORAGE_MODE) {
            if (RedisConstant.PRODUCT_TEXTURE_DATA.equals(mapName)) {
                return productTexturePoMap.get(keyName);
            }
        }
        return null;
    }

    //更新数据
    public void updateData() {
        log.info(CLASS_LOG_PREFIX + "开始获取产品材质元数据....");
        //产品材质元数据
        List<ProductTexturePo> productTexturePoList;
        try {
            productTexturePoList = metaDataService.queryProductTextureMetaData();
        } catch (MetaDataException e) {
            log.error(CLASS_LOG_PREFIX + "获取产品材质元数据失败: MetaDataException:{}", e);
            throw new NullPointerException(CLASS_LOG_PREFIX + "获取产品材质元数据失败,List<ProductTexturePo> is null.MetaDataException:" + e);
        }
        log.info(CLASS_LOG_PREFIX + "获取产品材质元数据完成,总条数:{}", (null == productTexturePoList ? 0 : productTexturePoList.size()));

        Map<String, String> tempProductTexturePoMap = new HashMap<>(null == productTexturePoList ? 10 : productTexturePoList.size());
        //转换Map
        if (null != productTexturePoList && 0 != productTexturePoList.size()) {
            productTexturePoList.forEach(productTexturePo -> tempProductTexturePoMap.put(productTexturePo.getId() + "", productTexturePo.getTextureName()));
        }
        log.info(CLASS_LOG_PREFIX + "格式化产品材质元数据完成....");

        //装载缓存
        redisService.addMapCompatible(RedisConstant.PRODUCT_TEXTURE_DATA, tempProductTexturePoMap);
        log.info(CLASS_LOG_PREFIX + "产品材质元数据装载缓存完成....");

        //内存模式
        if (StorageComponent.MEMORY_MODE == STORAGE_MODE) {
            productTexturePoMap = tempProductTexturePoMap;
            log.info(CLASS_LOG_PREFIX + "产品材质元数据装载内存完成....");
        }
    }

    /**
     * 根据材质ID获取产品材质名
     *
     * @return
     */
    public String getProductTextureNameById(String textureId) {
        if (StringUtils.isEmpty(textureId)) {
            return null;
        }

        //有分隔符表明为材质列表
        if (-1 != textureId.indexOf(",")) {
            String[] textureIds = textureId.split(",");

            StringBuffer textureNameSb = new StringBuffer();
            for (String id : textureIds) {
                String textureName = getMap(RedisConstant.PRODUCT_TEXTURE_DATA, id);
                if (!StringUtils.isEmpty(textureName)) {
                    textureNameSb.append(textureName);
                    textureNameSb.append(",");
                }
            }
            return textureNameSb.toString();
        } else {
            String textureName = getMap(RedisConstant.PRODUCT_TEXTURE_DATA, textureId);
            if (!StringUtils.isEmpty(textureName)) {
                return textureName;
            }
        }

        return null;
    }


}
