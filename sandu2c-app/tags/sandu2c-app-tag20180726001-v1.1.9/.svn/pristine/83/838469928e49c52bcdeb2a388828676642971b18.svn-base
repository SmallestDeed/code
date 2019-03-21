package com.sandu.cache.service;

import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;

import java.util.List;

/***
 * 字典数据缓存层
 * @date 20171030
 * @auth pengxuangang
 */
public interface SystemDictionaryCacheService {

    /***
     * 根据类别获取所有字典数据
     * @param type 字典类型
     * @return
     */
    List<SysDictionary> getSystemDictionaryListByType(String type);

    /***
     * 根据查询条件分页获取字典数据
     * @param sysDictionarySearch 搜索条件
     * @return
     */
    List<SysDictionary> getSystemDictionaryListPageByCondition(SysDictionarySearch sysDictionarySearch);
}
