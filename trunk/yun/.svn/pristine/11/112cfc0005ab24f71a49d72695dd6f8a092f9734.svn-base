package com.sandu.system.service;

import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;

import java.util.List;


/**
 * @version V1.0
 * @Title: SysDictionaryService.java
 * @Package com.sandu.system.service
 * @Description:系统管理-数据字典Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
public interface SysDictionaryService {
    /**
     * 新增数据
     *
     * @param sysDictionary
     * @return int
     */
    int add(SysDictionary sysDictionary);

    /**
     * 更新数据
     *
     * @param sysDictionary
     * @return int
     */
    int update(SysDictionary sysDictionary);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysDictionary
     */
    SysDictionary get(Integer id);

    /**
     * 所有数据
     *
     * @param sysDictionary
     * @return List<SysDictionary>
     */
    List<SysDictionary> getList(SysDictionary sysDictionary);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(SysDictionarySearch sysDictionarySearch);

    /**
     * 分页获取数据
     *
     * @return List<SysDictionary>
     */
    List<SysDictionary> getPaginatedList(SysDictionarySearch sysDictionarytSearch);

    /**
     * 根据类型和value获取类型名称数据
     *
     * @return List<SysDictionary>
     */
    SysDictionary getSysDictionaryByValue(String type, Integer value);

    /**
     * 通过type和typeKey查找sysDictionary
     *
     * @param type
     * @param valueKey
     * @return
     * @author huangsongbo
     */
    SysDictionary findOneByTypeAndValueKey(String type, String valueKey);


    /**
     * 通过type查找所有valueKey
     *
     * @param type
     * @return
     * @author huangsongbo
     */
    List<String> findValueKeyByType(String type);

    /**
     * 通过type和value查找sysDictionary
     *
     * @param type
     * @param value
     * @return
     * @author huangsongbo
     */
    SysDictionary findOneByTypeAndValue(String type, Integer value);

    /**
     * 通过ValueKeys  和 type  查列表
     *
     * @param type
     * @return
     */
    List<SysDictionary> getListByValueKeys(String type, List<String> ValueKeys);


    /**
     * 根据type和att1查找SysDictionary
     *
     * @return
     * @author huangsongbo
     */
    List<SysDictionary> findAllByTypeAndAtt1(String type, String att1);

    //查询所有空间类型
    int getAreaCount(String houseType);

    /**
     * 根据type查找sysDictionary
     *
     * @param type
     * @return
     * @author huangsongbo
     */
    List<SysDictionary> findAllByType(String type);
}
