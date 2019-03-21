package com.sandu.search.storage.groupproduct;

import com.sandu.search.common.constant.PlatformConstant;
import com.sandu.search.common.constant.RedisConstant;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.entity.elasticsearch.po.groupproduct.GroupProductPlatformData;
import com.sandu.search.entity.elasticsearch.po.metadate.GroupProductPlatformRelPo;
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

@Component
@Slf4j
public class GroupProductPlatformMetaDataStorage {

    private final static String CLASS_LOG_PREFIX = "组合平台元数据存储(全平台数据过滤):";

    //默认缓存模式
    private static Integer STORAGE_MODE = StorageComponent.CACHE_MODE;

    private final RedisService redisService;
    private final MetaDataService metaDataService;

    @Autowired
    public GroupProductPlatformMetaDataStorage(RedisService redisService, MetaDataService metaDataService) {
        this.redisService = redisService;
        this.metaDataService = metaDataService;
    }

    //组合平台元数据Map<组合ID,组合平台数据>
    private static Map<String, String> groupProductPlatformRelMap = null;

    //切换存储模式
    public void changeStorageMode(Integer storageMode) {
        //切换存储模式
        STORAGE_MODE = storageMode;

        //缓存模式
        if (StorageComponent.CACHE_MODE == storageMode) {
            //清空内存占用
            groupProductPlatformRelMap = null;
            //内存模式
        } else if (StorageComponent.MEMORY_MODE == storageMode) {
            updateData();
        }
        log.info(CLASS_LOG_PREFIX + "组合平台存储模式切换成功，当前存储:{}.", StorageComponent.CACHE_MODE == STORAGE_MODE ? "缓存" : "内存");
    }

    //更新数据
    public void updateData() {

        log.info(CLASS_LOG_PREFIX + "开始获取组合平台元数据....");
        //组合平台元数据
        List<GroupProductPlatformRelPo> groupProductPlatformRelPoList;
        try {
            groupProductPlatformRelPoList = metaDataService.queryAllPlatformGroupProductMetaData();
        } catch (MetaDataException e) {
            log.error(CLASS_LOG_PREFIX + "获取组合平台元数据失败: MetaDataException:{}", e);
            throw new NullPointerException(CLASS_LOG_PREFIX + "获取组合平台元数据失败,List<GroupProductPlatformRelPo> is null.MetaDataException:" + e);
        }
        log.info(CLASS_LOG_PREFIX + "获取组合平台元数据完成,总条数:{}", (null == groupProductPlatformRelPoList ? 0 : groupProductPlatformRelPoList.size()));

        //临时对象
        Map<Integer, GroupProductPlatformData> tempGroupProductPlatformRelMap = new HashMap<>();

        //转换Map
        if (null != groupProductPlatformRelPoList && 0 != groupProductPlatformRelPoList.size()) {
            groupProductPlatformRelPoList.forEach(groupProductPlatformRelPo -> {
                //检查对象/组合ID/平台ID
                if (null != groupProductPlatformRelPo && 0 < groupProductPlatformRelPo.getGroupId() && !StringUtils.isEmpty(groupProductPlatformRelPo.getPlatformCode())) {
                    //组合ID
                    int groupId = groupProductPlatformRelPo.getGroupId();
                    //组合平台对象
                    GroupProductPlatformData groupProductPlatformData = new GroupProductPlatformData();
                    //更新数据
                    if (tempGroupProductPlatformRelMap.containsKey(groupId)) {
                        groupProductPlatformData = tempGroupProductPlatformRelMap.get(groupId);
                    }
                    switch (groupProductPlatformRelPo.getPlatformCode()) {
                        //2B-移动端
                        case PlatformConstant.PLATFORM_CODE_TOB_MOBILE:
                            groupProductPlatformData.setPlatformGroupProductToBMobile(groupProductPlatformRelPo);
                            break;
                        //2B-PC
                        case PlatformConstant.PLATFORM_CODE_TOB_PC:
                            groupProductPlatformData.setPlatformGroupProductToBPc(groupProductPlatformRelPo);
                            break;
                        //品牌2C-网站
                        case PlatformConstant.PLATFORM_CODE_TOC_SITE:
                            groupProductPlatformData.setPlatformGroupProductToCSite(groupProductPlatformRelPo);
                            break;
                        //2C-移动端
                        case PlatformConstant.PLATFORM_CODE_TOC_MOBILE:
                            groupProductPlatformData.setPlatformGroupProductToCMobile(groupProductPlatformRelPo);
                            break;
                        //三度-后台管理
                        case PlatformConstant.PLATFORM_CODE_SANDU_MANAGER:
                            groupProductPlatformData.setPlatformGroupProductSanduManager(groupProductPlatformRelPo);
                            break;
                        //户型绘制工具
                        case PlatformConstant.PLATFORM_CODE_HOUSE_DRAW:
                            groupProductPlatformData.setPlatformGroupProductHouseDraw(groupProductPlatformRelPo);
                            break;
                        //商家管理后台
                        case PlatformConstant.PLATFORM_CODE_MERCHANTS_MANAGER:
                            groupProductPlatformData.setPlatformGroupProductMerchantsManager(groupProductPlatformRelPo);
                            break;
                        //测试
                        case PlatformConstant.PLATFORM_CODE_TEST:
                            groupProductPlatformData.setPlatformGroupProductTest(groupProductPlatformRelPo);
                            break;
                        //U3D转换插件
                        case PlatformConstant.PLATFORM_CODE_U3D_PLUGIN:
                            groupProductPlatformData.setPlatformGroupProductU3dPlugin(groupProductPlatformRelPo);
                            break;
                        //小程序
                        case PlatformConstant.PLATFORM_CODE_MINI_PROGRAM:
                            groupProductPlatformData.setPlatformGroupProductMiniProgram(groupProductPlatformRelPo);
                            break;
                        // 随选网
                        case PlatformConstant.PLATFORM_CODE_SELECT_DECORATION:
                            groupProductPlatformData.setPlatformGroupProductSelectDecoration(groupProductPlatformRelPo);
                            break;
                        case PlatformConstant.PLATFORM_CODE_PC_TOB_CUSTOM:
                            groupProductPlatformData.setPlatformGroupProductPcToBCustom(groupProductPlatformRelPo);
                        default:
                            log.warn(CLASS_LOG_PREFIX + "无法识别平台编码,groupProductPlatformRelPo:{}", groupProductPlatformRelPo.toString());
                    }
                    //装入Map
                    tempGroupProductPlatformRelMap.put(groupId, groupProductPlatformData);
                }
            });
        }
        log.info(CLASS_LOG_PREFIX + "格式化组合平台元数据完成....");

        //数据转换
        Map<String, String> groupProductPlatformRelJsonMap = new HashMap<>(tempGroupProductPlatformRelMap.size());
        tempGroupProductPlatformRelMap.forEach((k, v) -> groupProductPlatformRelJsonMap.put(k + "", JsonUtil.toJson(v)));
        log.info(CLASS_LOG_PREFIX + "格式化组合平台Json元数据完成....");

        //装载缓存
        // remove 缓存,应对一种情况,取消上架后(取消所有平台),组合平台数据更新失败(缓存数据还在并且没有更新)
        redisService.del(RedisConstant.GROUP_PRODUCT_PLATFORM_REL_MAP_DATA);
        redisService.addMapCompatible(RedisConstant.GROUP_PRODUCT_PLATFORM_REL_MAP_DATA, groupProductPlatformRelJsonMap);
        log.info(CLASS_LOG_PREFIX + "组合平台元数据装载缓存完成....");

        //内存模式
        if (StorageComponent.MEMORY_MODE == STORAGE_MODE) {
            groupProductPlatformRelMap = groupProductPlatformRelJsonMap;
            log.info(CLASS_LOG_PREFIX + "组合平台元数据装载内存完成....");
        }

    }

    //获取Map数据方法兼容
    private String getMap(String mapName, String keyName) {

        if (StorageComponent.MEMORY_MODE == STORAGE_MODE && null != groupProductPlatformRelMap && 0 < groupProductPlatformRelMap.size()) {
            //内存模式
            return groupProductPlatformRelMap.get(keyName);
        } else {
            //缓存模式
            return redisService.getMap(mapName, keyName);
        }
    }

    /**
     * 根据组合ID获取组合平台数据
     *
     * @param groupId 组合ID
     * @return
     */
    public GroupProductPlatformData queryGroupProductPlatformByGroupId(Integer groupId) {
        if (null == groupId || 0 == groupId) {
            return null;
        }

        String groupProductPlatformRelStr = getMap(RedisConstant.GROUP_PRODUCT_PLATFORM_REL_MAP_DATA, groupId + "");
        if (StringUtils.isEmpty(groupProductPlatformRelStr)) {
            return null;
        }

        return JsonUtil.fromJson(groupProductPlatformRelStr, GroupProductPlatformData.class);
    }
}
