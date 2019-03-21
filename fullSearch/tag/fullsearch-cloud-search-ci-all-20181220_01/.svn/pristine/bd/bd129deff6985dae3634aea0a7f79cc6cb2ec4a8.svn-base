package com.sandu.search.storage.groupproduct;

import com.google.gson.reflect.TypeToken;
import com.sandu.search.common.constant.ProductTypeValue;
import com.sandu.search.common.constant.RedisConstant;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.entity.elasticsearch.po.metadate.GroupProductRelPo;
import com.sandu.search.exception.MetaDataException;
import com.sandu.search.service.metadata.MetaDataService;
import com.sandu.search.service.redis.RedisService;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class GroupProductRelMetaDataStorage {

    private final static String CLASS_LOG_PREFIX = "产品组合关系元数据存储:";

    // 默认缓存模式
    private static Integer STORAGE_MODE = StorageComponent.CACHE_MODE;

    //产品组合关系元数据map<groupId, List<GroupProductRelPo>>>
    private static Map<Integer, List<GroupProductRelPo>> productGroupRelMap = new HashMap<>();
    // 主产品为key，组合产品id集合为value
    private static Map<Integer, List<Integer>> mainProductIdOfGroupMap = new HashMap<>();
    // 主产品为key，普通组合产品id集合为value
    private static Map<Integer, List<Integer>> mainProductIdOfNormalGroupMap = new HashMap<>();
    //元数据服务
    private final MetaDataService metaDataService;
    private final RedisService redisService;

    @Autowired
    public GroupProductRelMetaDataStorage(MetaDataService metaDataService,
                                          RedisService redisService) {
        this.metaDataService = metaDataService;
        this.redisService = redisService;
    }

    // 切换存储模式
    public void changeStorageMode(Integer storageMode) {
        if (StorageComponent.CACHE_MODE == storageMode) {
            // 清空内存占用
            productGroupRelMap = null;
            // 切换存储模式
            STORAGE_MODE = storageMode;
        } else if (StorageComponent.MEMORY_MODE == storageMode) {
            // 切换存储模式
            STORAGE_MODE = storageMode;
            // 更新内存数据
            updateData();
        }
    }

    /**
     * 更新数据
     *
     * @param
     */
    public void updateData() {

        //若无强制更新则更新前判断是否已有数据
        // if (!isEnforceLoad) {
        //     if (null != productGroupRelMap && 0 < productGroupRelMap.size()) {
        //         return;
        //     }
        // }

        //产品组合关系元数据
        List<GroupProductRelPo> productGroupRelList;
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
        Map<Integer, List<GroupProductRelPo>> tempProductGroupRelMap = new HashMap<>();
        Map<Integer, List<Integer>> tempProductIdOfGroupMap = new HashMap<>();

        //转换为Map元数据
        if (null != productGroupRelList && 0 != productGroupRelList.size()) {
            productGroupRelList.forEach(productGroupRel -> {
                //产品组合ID
                int productGroupId = productGroupRel.getProductGroupId();
                //封装productGroupRelMap
                if (0 != productGroupId) {
                    if (tempProductGroupRelMap.containsKey(productGroupId)) {
                        // 这里必须先创建arrayList对象才能使用add方法，list的add方法会抛异常
                        ArrayList<GroupProductRelPo> groupProductRelPoList = new ArrayList<>();
                        List<GroupProductRelPo> groupProductRelPos = tempProductGroupRelMap.get(productGroupId);
                        groupProductRelPoList.addAll(groupProductRelPos);
                        groupProductRelPoList.add(productGroupRel);
                        tempProductGroupRelMap.put(productGroupId, groupProductRelPoList);
                    } else {
                        tempProductGroupRelMap.put(productGroupRel.getProductGroupId(), Collections.singletonList(productGroupRel));
                    }
                }
                //封装productIdOfGroupMap
                int productId = productGroupRel.getProductId();
                if (ProductTypeValue.IS_MAIN_PRODUCT == productGroupRel.getProductIsMain()) {
                    if (tempProductIdOfGroupMap.containsKey(productId)) {
                        ArrayList<Integer> groupIdList = new ArrayList<>();
                        List<Integer> groupIds = tempProductIdOfGroupMap.get(productId);
                        groupIdList.addAll(groupIds);
                        groupIdList.add(productGroupId);
                        tempProductIdOfGroupMap.put(productId, groupIdList);
                    } else {
                        tempProductIdOfGroupMap.put(productId, Collections.singletonList(productGroupId));
                    }
                }
            });

            //装回对象
            productGroupRelMap = null;
            productGroupRelMap = tempProductGroupRelMap;
            log.info(CLASS_LOG_PREFIX + "产品组合关联元数据装载内存完成....");
            mainProductIdOfGroupMap = null;
            mainProductIdOfGroupMap = tempProductIdOfGroupMap;
            log.info(CLASS_LOG_PREFIX + "主产品组合元数据装载内存完成。。。。");

            // 转换为json数据
            Map<String, String> productGroupRelJsonMap = new HashMap<>();
            tempProductGroupRelMap.forEach((k, v) -> productGroupRelJsonMap.put(k + "", JsonUtil.toJson(v)));
            // 存入缓存
            redisService.addMapCompatible(RedisConstant.GROUP_PRODUCT_REL_DATA, productGroupRelJsonMap);
            log.info(CLASS_LOG_PREFIX + "产品组合关联元数据装载缓存完成....");
            Map<String, String> productIdOfGroupJsonMap = new HashMap<>();
            tempProductIdOfGroupMap.forEach((k, v) -> productIdOfGroupJsonMap.put(k + "", JsonUtil.toJson(v)));
            redisService.addMapCompatible(RedisConstant.MAIN_PRODUCT_OF_GROUP_DATA, productIdOfGroupJsonMap);
        }

        //获取非一键装修组合数据
        try {
            log.info(CLASS_LOG_PREFIX + "开始获取普通产品组合关系元数据....");
            productGroupRelList = metaDataService.queryNormalProductGroupRelMetaData();
            log.info(CLASS_LOG_PREFIX + "获取普通产品组合关系元数据完成,总条数:{}", (null == productGroupRelList ? 0 : productGroupRelList.size()));
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取普通产品组合关系元数据失败: MetaDataException:{}", e);
            throw new NullPointerException(CLASS_LOG_PREFIX + "获取普通产品组合关系元数据失败,List<ProductStylePo> is null.MetaDataException:" + e);
        }

        if (null != productGroupRelList && 0 < productGroupRelList.size()) {
            Map<Integer, List<Integer>> tempProductIdOfNormalGroupMap = new HashMap<>();
            productGroupRelList.forEach(productGroupRelPo -> {
                int productId = productGroupRelPo.getProductId();
                if (tempProductIdOfNormalGroupMap.containsKey(productId)) {
                    ArrayList<Integer> groupIdList = new ArrayList<>();
                    List<Integer> groupIds = tempProductIdOfNormalGroupMap.get(productId);
                    groupIdList.addAll(groupIds);
                    groupIdList.add(productGroupRelPo.getProductGroupId());
                    tempProductIdOfNormalGroupMap.put(productId,groupIdList);
                } else {
                    tempProductIdOfNormalGroupMap.put(productId,Collections.singletonList(productGroupRelPo.getProductGroupId()));
                }
            });
            //非一键组合主产品对应的组合id map存内存
            mainProductIdOfNormalGroupMap = null;
            mainProductIdOfNormalGroupMap = tempProductIdOfNormalGroupMap;
            //非一键组合主产品对应的组合id map存缓存
            Map<String, String> normalGroupProductJsonMap = new HashMap<>();
            tempProductIdOfNormalGroupMap.forEach((k,v) -> normalGroupProductJsonMap.put(k+"",JsonUtil.toJson(v)));
            redisService.addMapCompatible(RedisConstant.MAIN_PRODUCT_OF_NORMAL_GROUP_DATA,normalGroupProductJsonMap);
        }
    }

    /**
     * 根据组合产品id获取主产品id
     *
     * @param groupId
     * @return
     */
    public Integer getMainProductIdByGroupId(Integer groupId) {
        if (groupId == null || groupId == 0) {
            return null;
        }

        List<GroupProductRelPo> groupProductRelPoList = getGroupProductRelPoList(groupId);
        if (groupProductRelPoList == null || groupProductRelPoList.size() <= 0) {
            return null;
        }

        for (GroupProductRelPo groupProductRelPo : groupProductRelPoList) {
            if (groupProductRelPo.getProductIsMain() == 1) {
                return groupProductRelPo.getProductId();
            }
        }
        return null;
    }

    /**
     * 根据组合id获取组合产品关联数据
     *
     * @param groupId
     * @return
     */
    public List<GroupProductRelPo> getGroupProductRelPoList(Integer groupId) {
        if (groupId == null || groupId == 0) {
            return null;
        }

        if (StorageComponent.MEMORY_MODE == STORAGE_MODE && null != productGroupRelMap && productGroupRelMap.size() > 0) {
            return productGroupRelMap.get(groupId);
        } else {
            String listStr = redisService.getMap(RedisConstant.GROUP_PRODUCT_REL_DATA, groupId + "");

            if (StringUtils.isEmpty(listStr)) {
                return null;
            }
            return JsonUtil.fromJson(listStr, new TypeToken<List<GroupProductRelPo>>() {
            }.getType());
        }
    }

    /**
     * 根据主产品id找组合id集合
     * @param productId
     * @return
     */
    public List<Integer> getGroupIdByMainProductId(Integer productId) {
        if (null == productId || 0 == productId) {
            return null;
        }

        if (StorageComponent.MEMORY_MODE == STORAGE_MODE && null != mainProductIdOfGroupMap && mainProductIdOfGroupMap.size() > 0) {
            return mainProductIdOfGroupMap.get(productId);
        } else {
            String json = redisService.getMap(RedisConstant.MAIN_PRODUCT_OF_GROUP_DATA, productId + "");
            if (StringUtils.isBlank(json)) {
                return null;
            }
            return JsonUtil.fromJson(json, new TypeToken<List<Integer>>(){}.getType());
        }
    }

    /**
     * 根据组合id获取关联产品id集合
     *
     * @param groupId
     * @return
     */
    public List<Integer> getProductIdListByGroupId(Integer groupId) {
        if (groupId == null || groupId == 0) {
            return null;
        }

        List<GroupProductRelPo> groupProductRelPoList = getGroupProductRelPoList(groupId);
        if (groupProductRelPoList == null || groupProductRelPoList.size() <= 0) {
            return null;
        }

        List<Integer> productIdList = new ArrayList<>();
        for (GroupProductRelPo groupProductRelPo : groupProductRelPoList) {
            productIdList.add(groupProductRelPo.getProductId());
        }
        return productIdList;
    }

    public List<Integer> getNormalGroupIdListByMainProductId(Integer productId) {
        if (null == productId || 0 == productId) {
            return null;
        }

        if (StorageComponent.MEMORY_MODE == STORAGE_MODE && null != mainProductIdOfNormalGroupMap && mainProductIdOfNormalGroupMap.size() > 0) {
            return mainProductIdOfNormalGroupMap.get(productId);
        } else {
            String json = redisService.getMap(RedisConstant.MAIN_PRODUCT_OF_NORMAL_GROUP_DATA, productId + "");
            if (StringUtils.isBlank(json)) {
                return null;
            }
            return JsonUtil.fromJson(json, new TypeToken<List<Integer>>(){}.getType());
        }
    }
}
