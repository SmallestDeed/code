package com.sandu.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.redis.RedisService;
import com.sandu.common.constant.redis.RedisKeyConstant;
import com.sandu.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.house.bo.SystemDictionaryBO;
import com.sandu.api.house.input.SystemDictionarySearch;
import com.sandu.api.house.model.SystemDictionary;
import com.sandu.api.house.service.SystemDictionaryService;
import com.sandu.common.constant.house.ProductType;
import com.sandu.service.system.dao.SystemDictionaryMapper;
import com.sandu.util.Utils;

/**
 * Description: 数据字典逻辑类
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */

@Slf4j
@Service
public class SystemDictionaryServiceImpl implements SystemDictionaryService {

    public static final String _DOORFRAME_TYPE = "basic_mengk";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Override
    public List<SystemDictionary> listSysDictionary(SystemDictionarySearch search) {
        return systemDictionaryMapper.listSysDictionaryByType(search);
    }

    @Override
    public List<SystemDictionary> listSoftBiamoGroupType(SystemDictionarySearch search, boolean useCache) {
        if (useCache) {
            String key = RedisKeyConstant.HOUSE_DRAW_SOFT_PRODUCT_CATEGORY.toString();
            String hashKey = RedisKeyConstant.HashKey.GROUP_CATEGORY.toString();
            String value = redisService.getCacheByHash(key, hashKey, -1L, null,
                    () -> JSON.stringify(listSysDictionary(search)));

            if (!StringUtils.isEmpty(value)) {
                log.debug("listSoftBiamoGroupType <= get redis cache by hashKey {}", hashKey);
                return JSON.parse(value, new TypeReference<List<SystemDictionary>>() {
                });
            }
        }

        return listSysDictionary(search);
    }

    @Override
    public List<SystemDictionary> listHardBaimoType(SystemDictionarySearch search) {
        return systemDictionaryMapper.listHardBaimoType(search);
    }

    @Override
    public List<SystemDictionary> listHardBaimoSamllType(SystemDictionarySearch search) {
        return systemDictionaryMapper.listHardBaimoSamllType(search);
    }

    @Override
    public List<SystemDictionaryBO> listSoftBiamoType(SystemDictionarySearch search, boolean useCache) {
        if (useCache) {
            String key = RedisKeyConstant.HOUSE_DRAW_SOFT_PRODUCT_CATEGORY.toString();
            String hashKey = RedisKeyConstant.HashKey.SINGLE_CATEGORY.toString();
            String value = redisService.getCacheByHash(key, hashKey, -1L, null,
                    () -> JSON.stringify(listSoftBiamoType(search)));

            if (!StringUtils.isEmpty(value)) {
                log.debug("listSoftBiamoType <= get redis cache by hashKey {}", hashKey);
                return JSON.parse(value, new TypeReference<List<SystemDictionaryBO>>() {
                });
            }
        }

        return listSoftBiamoType(search);
    }

    @Override
    public List<SystemDictionaryBO> listSoftBiamoType(SystemDictionarySearch search) {
        List<SystemDictionaryBO> softType = systemDictionaryMapper.listSoftBiamoType(search);
        Map<String, List<SystemDictionaryBO>> map = new HashMap<>();

        // 合并处理
        this.handlerMergeType(map, softType);

        if (!map.isEmpty()) {
            final StringBuilder valBuf = new StringBuilder();
            final StringBuilder keyBuf = new StringBuilder();
            for (String key : map.keySet()) {
                map.get(key).forEach(dict -> {
                    // value
                    valBuf.append(",").append(Objects.toString(dict.getValue(), Utils.VOID_VALUE));

                    // valuekey
                    keyBuf.append(",").append(Objects.toString(dict.getValuekey(), Utils.VOID_VALUE));
                });

                SystemDictionaryBO proType = new SystemDictionaryBO();
                proType.setName(key);
                proType.setValue(valBuf.toString().replaceFirst(",", ""));
                proType.setValuekey(keyBuf.toString().replaceFirst(",", ""));
                softType.add(proType);

                valBuf.setLength(0);
                keyBuf.setLength(0);
            }

            // TODO 墙面 => 门框_原门框
            // 门框 Fuck Door
//			SystemDictionary _door = systemDictionaryMapper.findOneByValueKey(_DOORFRAME_TYPE);
            SystemDictionaryBO _doorframe = new SystemDictionaryBO();
            _doorframe.setName(ProductType._DOOR.getName());
            _doorframe.setValue("-19");
            _doorframe.setValuekey(ProductType._DOOR.getValuekey());
            softType.add(_doorframe);
        }

        return softType;
    }

    /**
     * 沙发、几类、桌、椅、灯具、家纺
     * 床  => 床架、床品、床垫
     * 饰品 =>  摆件
     * 卫浴 => 浴室配件
     * 柜类 ==> 柜子、厨房地柜、厨房吊柜
     * 厨房 => 厨房（柜）、厨房配件
     * 电器 => 小家电、大家电、厨房电器
     *
     * @param map
     * @param softType
     */
    private void handlerMergeType(Map<String, List<SystemDictionaryBO>> map, List<SystemDictionaryBO> softType) {
        if (softType != null && !softType.isEmpty()) {
            Iterator<SystemDictionaryBO> itr = softType.iterator();
            while (itr.hasNext()) {
                SystemDictionaryBO t = itr.next();
                // 床  => 床架、床品、床垫
                if (t.getValuekey().equals(ProductType.BD.getValuekey())
                        || t.getValuekey().equals(ProductType.BD2.getValuekey())
                        || t.getValuekey().equals(ProductType.BD3.getValuekey())) {
                    handleType(t, ProductType.BED.getName(), map);
                    // remove
                    itr.remove();
                    continue;
                }

                // 饰品 => 饰品 、 摆件
                if (t.getValuekey().equals(ProductType.PE.getValuekey())
                        || t.getValuekey().equals(ProductType.BK.getValuekey())) {
                    handleType(t, ProductType.PE.getName(), map);
                    // remove
                    itr.remove();
                    continue;
                }

                // 卫浴 => 卫浴 、浴室配件
                if (t.getValuekey().equals(ProductType.BA.getValuekey())
                        || t.getValuekey().equals(ProductType.BP.getValuekey())) {
                    handleType(t, ProductType.BA.getName(), map);
                    // remove
                    itr.remove();
                    continue;
                }

                // 柜类 => 柜子、厨房地柜、厨房吊柜
                if (t.getValuekey().equals(ProductType.CA.getValuekey())
                        || t.getValuekey().equals(ProductType.CUKI.getValuekey())
                        || t.getValuekey().equals(ProductType.DGKI.getValuekey())) {
                    handleType(t, ProductType.FORCER.getName(), map);
                    // remove
                    itr.remove();
                    continue;
                }

                // 厨房 => 厨房（柜）、厨房配件
                if (t.getValuekey().equals(ProductType.KI.getValuekey())
                        || t.getValuekey().equals(ProductType.KP.getValuekey())) {
                    handleType(t, ProductType.KITCHEN.getName(), map);
                    // remove
                    itr.remove();
                    continue;
                }

                // 电器 => 电器、小家电、大家电、厨房电器
                if (t.getValuekey().equals(ProductType.SE.getValuekey())
                        || t.getValuekey().equals(ProductType.EL.getValuekey())
                        || t.getValuekey().equals(ProductType.KIEL.getValuekey())) {
                    handleType(t, ProductType.EL.getName(), map);
                    // remove
                    itr.remove();
                }
            }
        }
    }

    private void handleType(SystemDictionaryBO t, String key, Map<String, List<SystemDictionaryBO>> map) {
        if (map.containsKey(key)) {
            map.get(key).add(t);
        } else {
            ArrayList<SystemDictionaryBO> list = new ArrayList<>();
            list.add(t);
            map.put(key, list);
        }
    }

    @Override
    public SystemDictionary findOneByValueKey(String valueKey) {
        if (StringUtils.isEmpty(valueKey)) {
            return null;
        }

        String key = RedisKeyConstant.HOUSE_DRAW_HOUSE_TYPE.toString();
        String value = redisService.getCacheByHash(key, valueKey, -1L, null,
                () -> JSON.stringify(systemDictionaryMapper.findOneByValueKey(valueKey)));

        if (!StringUtils.isEmpty(value)) {
            log.debug("findOneByValueKey <= get redis cache by hashKey {}", valueKey);
            return JSON.parse(value, SystemDictionary.class);
        }

        return systemDictionaryMapper.findOneByValueKey(valueKey);
    }

    @Override
    public SystemDictionary findOneByTypeAndArea(String type, Double area) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }

        if (area == null || area <= 0) {
            return null;
        }

        String key = RedisKeyConstant.HOUSE_DRAW_HOUSE_AREA_TYPE.toString();
        String value = redisService.getCacheByHash(key, type, -1L, null, () -> {
            SystemDictionarySearch query = new SystemDictionarySearch();
            query.setType(type);
            List<SystemDictionary> items = listSysDictionary(query);
            return JSON.stringify(items);
        });

        return getMatchHouseArea(type, value, area);
    }

    /**
     * 根据面积匹配
     *
     * @param value
     * @param area
     * @return
     */
    private SystemDictionary getMatchHouseArea(String type, String value, Double area) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        List<SystemDictionary> dicts = JSON.parse(value, new TypeReference<List<SystemDictionary>>() {
        });

        if (dicts != null && !dicts.isEmpty()) {
            log.debug("findOneByTypeAndArea <= get redis cache by hashKey {}", type);
            try {
                for (SystemDictionary dict : dicts) {
                    Double att4 = dict.getAtt4() == null ? -999999 : Double.valueOf(dict.getAtt4());
                    Double att5 = dict.getAtt5() == null ? -999999 : Double.valueOf(dict.getAtt5());
                    if (att4 <= area && att5 >= area) {
                        return dict;
                    }
                }
            } catch (Exception e) {
                log.error("匹配户型面积异常，type => {}, area => {}, value => {}", type, area, value, e);
            }
        }

        // 没有时查询数据
        return systemDictionaryMapper.findOneByTypeAndArea(type, area);
    }
}
