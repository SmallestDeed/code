package com.sandu.pay.system.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.PayRedisService;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;
import com.sandu.system.service.SysDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/***
 * 字典数据缓存层
 * @date 20171030
 * @auth pengxuangang
 */
public class SysDictionaryCacher {

    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[字典数据缓存层]:";
    private static Logger logger = LoggerFactory.getLogger(SysDictionaryCacher.class);
    private static PayRedisService payRedisService = SpringContextHolder.getBean(PayRedisService.class);
    private static SysDictionaryService sysDictionaryService = SpringContextHolder.getBean(SysDictionaryService.class);

    /***
     * 根据类别获取所有字典数据
     * @return
     */
    public static List<SysDictionary> getAllListWithType(String type) {
        SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
        sysDictionarySearch.setType(type);
        return getPageList(sysDictionarySearch);
    }

    /***
     * 根据查询条件分页获取字典数据
     * @param sysDictionarySearch 搜索条件
     * @return
     */
    public static List<SysDictionary> getPageList(SysDictionarySearch sysDictionarySearch) {
        List<SysDictionary> sysDictionaryList = null;

        //从缓存获取数据
        String systemDictionaryJson = payRedisService.get(RedisKeyConstans.SYSTEM_DICTIONARY_KEY, null);

        if (org.springframework.util.StringUtils.isEmpty(systemDictionaryJson)) {
            //从数据库获取所有数据
            List<SysDictionary> dbSysDictionaryList = loadSystemDictionaryDataFromDB();
            if (null != dbSysDictionaryList && dbSysDictionaryList.size() > 0) {
                //装入缓存
                payRedisService.set(RedisKeyConstans.SYSTEM_DICTIONARY_KEY, gson.toJson(dbSysDictionaryList));
                //过滤符合条件数据
                sysDictionaryList = filterConformConditionSystemDictionaryData(dbSysDictionaryList, sysDictionarySearch);
            }
        } else {
            //转换数据
            List<SysDictionary> redisSystemDictionaryList = gson.fromJson(systemDictionaryJson, new TypeToken<List<SysDictionary>>() {
            }.getType());
            //过滤符合条件数据
            sysDictionaryList = filterConformConditionSystemDictionaryData(redisSystemDictionaryList, sysDictionarySearch);
            //若缓存中无可用数据则重新从数据库加载
            if (null == sysDictionaryList || sysDictionaryList.size() == 0) {
                //从数据库获取所有数据
                List<SysDictionary> dbSysDictionaryList = loadSystemDictionaryDataFromDB();
                //过滤符合条件数据
                sysDictionaryList = filterConformConditionSystemDictionaryData(dbSysDictionaryList, sysDictionarySearch);
            }
        }

        return sysDictionaryList;
    }

    //从数据库加载字典数据至缓存
    private static List<SysDictionary> loadSystemDictionaryDataFromDB() {

        logger.info(CLASS_LOG_PREFIX + "从数据库加载字典数据至缓存->查询字典数据开始.......");
        List<SysDictionary> dbSystemDictionaryList = sysDictionaryService.getList(new SysDictionary());
        logger.info(CLASS_LOG_PREFIX + "从数据库加载字典数据至缓存->查询字典数据完成:JSON[List<SysDictionary>]:{}", gson.toJson(dbSystemDictionaryList));
        if (null != dbSystemDictionaryList && dbSystemDictionaryList.size() > 0) {
            //装入缓存
            logger.info(CLASS_LOG_PREFIX + "从数据库加载字典数据至缓存->将字典数据装入缓存:JSON[List<SysDictionary>]:{}", gson.toJson(dbSystemDictionaryList));
            boolean setStatus = payRedisService.set(RedisKeyConstans.SYSTEM_DICTIONARY_KEY, gson.toJson(dbSystemDictionaryList));
            logger.info(CLASS_LOG_PREFIX + "从数据库加载字典数据至缓存->将字典数据装入缓存完成，操作状态:{}", setStatus);
        }

        return dbSystemDictionaryList;
    }

    //过滤符合条件数据
    private static List<SysDictionary> filterConformConditionSystemDictionaryData(List<SysDictionary> sysDictionaryList, SysDictionarySearch sysDictionarySearch) {

        if (null == sysDictionaryList || 0 == sysDictionaryList.size()) {
            return null;
        }

        if (null == sysDictionarySearch) {
            return sysDictionaryList;
        }

        List<SysDictionary> resultSysDictionaryList = new ArrayList<>(sysDictionaryList.size());

        //遍历数据
        sysDictionaryList.forEach(sysDictionary -> {
            if ((org.springframework.util.StringUtils.isEmpty(sysDictionarySearch.getType()) || sysDictionarySearch.getType().equals(sysDictionary.getType()))
                    && (org.springframework.util.StringUtils.isEmpty(sysDictionarySearch.getAtt4()) || sysDictionarySearch.getAtt4().equals(sysDictionary.getAtt4()))
                    && (org.springframework.util.StringUtils.isEmpty(sysDictionarySearch.getAtt6()) || sysDictionarySearch.getAtt6().equals(sysDictionary.getAtt6()))
                    && (org.springframework.util.StringUtils.isEmpty(sysDictionarySearch.getValue()) || sysDictionarySearch.getValue().equals(sysDictionary.getValue()))) {
                resultSysDictionaryList.add(sysDictionary);
            }
        });

        return resultSysDictionaryList;
    }
}
