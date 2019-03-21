package com.sandu.search.storage.groupproduct;

import com.google.gson.reflect.TypeToken;
import com.sandu.search.common.constant.RedisConstant;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.service.metadata.MetaDataService;
import com.sandu.search.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class GroupProductRelMetaDataStorage {

    private static final String CLASS_LOG_PREFIX = "组合产品关联元数据存储";

    private final RedisService redisService;
    private final MetaDataService metaDataService;

    @Autowired
    public GroupProductRelMetaDataStorage(RedisService redisService, MetaDataService metaDataService) {
        this.redisService = redisService;
        this.metaDataService = metaDataService;
    }

    public List<Integer> getGroupIdListByMainProductId(Integer mainProductId) {
        if (null == mainProductId || 0 >= mainProductId) {
            return null;
        }

        String jsonList = redisService.getMap(RedisConstant.MAIN_PRODUCT_OF_GROUP_DATA, mainProductId + "");
        if (StringUtils.isNotBlank(jsonList)) {
            return JsonUtil.fromJson(jsonList, new TypeToken<List<Integer>>() {}.getType());
        } else {
            //缓存中没有则去查数据库
            List<Integer> GroupIdList;
            try {
                GroupIdList = metaDataService.queryGroupIdListByMainProductId(mainProductId);
            } catch (Exception e) {
                log.error(CLASS_LOG_PREFIX + "getProductGroupRelMetaData in db exception:{}", e);
                return null;
            }
            return GroupIdList;
        }
    }
}
