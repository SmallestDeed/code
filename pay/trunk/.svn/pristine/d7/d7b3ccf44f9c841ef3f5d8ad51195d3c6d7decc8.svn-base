package com.sandu.pay.system.service.impl;

import com.google.gson.Gson;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.pay.system.cache.SysDictionaryCacher;
import com.sandu.pay.system.dao.SysDictionaryMapper;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;
import com.sandu.system.service.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysDictionaryServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统管理-数据字典ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl implements SysDictionaryService {

    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[系统管理-数据字典服务]:";
    private static Logger logger = LoggerFactory.getLogger(SysDictionaryServiceImpl.class);
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * 新增数据
     *
     * @param sysDictionary
     * @return int
     */
    @Override
    public int add(SysDictionary sysDictionary) {
        sysDictionaryMapper.insertSelective(sysDictionary);

        return sysDictionary.getId();
    }

    /**
     * 更新数据
     *
     * @param sysDictionary
     * @return int
     */
    @Override
    public int update(SysDictionary sysDictionary) {

        return sysDictionaryMapper
                .updateByPrimaryKeySelective(sysDictionary);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return sysDictionaryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysDictionary
     */
    @Override
    public SysDictionary get(Integer id) {
        return sysDictionaryMapper.selectByPrimaryKey(id);
    }


    /**
     * 所有数据
     *
     * @param sysDictionary
     * @return List<SysDictionary>
     */
    @Override
    public List<SysDictionary> getList(SysDictionary sysDictionary) {
        logger.info(CLASS_LOG_PREFIX + "根据条件查询字典数据:SysDictionary:{}.", sysDictionary.toString());
        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectList(sysDictionary);
        logger.info(CLASS_LOG_PREFIX + "查询字典数据完成:JSON[List<SysDictionary>]:{}.", null == sysDictionaryList ? null : gson.toJson(sysDictionaryList));
        return sysDictionaryList;
    }


    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(SysDictionarySearch sysDictionarySearch) {
        logger.info("getCount");
        return sysDictionaryMapper.selectCount(sysDictionarySearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<SysDictionary>
     */
    @Override
    public List<SysDictionary> getPaginatedList(
            SysDictionarySearch sysDictionarySearch) {
        logger.info("getPaginatedList");
        return sysDictionaryMapper.selectPaginatedList(sysDictionarySearch);
    }

    /**
     * 获取SysDictionary对象
     *
     * @param type,value
     * @return SysDictionary
     */
    @Override
    public SysDictionary getSysDictionaryByValue(String type, Integer value) {
        if (StringUtils.isEmpty(type) || value == null || new Integer(value).intValue() <= 0) {
            return new SysDictionary();
        }

        List<SysDictionary> dictionaryList = null;
            SysDictionary sysDictionary = new SysDictionary();
            sysDictionary.setIsDeleted(0);
            sysDictionary.setType(type);
            dictionaryList = getList(sysDictionary);

        if (CustomerListUtils.isNotEmpty(dictionaryList)) {
            for (SysDictionary dictionary : dictionaryList) {
                if (dictionary == null || dictionary.getType() == null || dictionary.getValue() == null) {
                    logger.info("dictionary=" + dictionary + ";dictionary.type=" + (dictionary != null ? dictionary.getType() : "")
                            + ";dictionary.value=" + (dictionary != null ? dictionary.getValue() : ""));
                    continue;
                }
                if (type.equals(dictionary.getType()) && value.equals(dictionary.getValue())) {
                    return dictionary;
                }
            }
        }
        return new SysDictionary();
    }

    //根据类型和valuekey获取类型名称数据
    @Override
    public SysDictionary findOneByTypeAndValueKey(String type, String valueKey) {
        SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
        sysDictionarySearch.setStart(0);
        sysDictionarySearch.setLimit(1);
        sysDictionarySearch.setType(type);
        sysDictionarySearch.setValuekey(valueKey);
        List<SysDictionary> sysDictionaries = getPaginatedList(sysDictionarySearch);
        if (sysDictionaries != null && sysDictionaries.size() > 0) {
            return sysDictionaries.get(0);
        }
        return null;
    }

    /**
     * 通过type查找所有valueKey
     *
     * @param type
     * @return
     * @author huangsongbo
     */
    @Override
    public List<String> findValueKeyByType(String type) {
        return sysDictionaryMapper.findValueKeyByType(type);
    }

    /**
     * 通过type和value查找sysDictionary
     *
     * @param type
     * @param value
     * @return
     * @author huangsongbo
     */
    @Override
    public SysDictionary findOneByTypeAndValue(String type, Integer value) {
        SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
        sysDictionarySearch.setStart(0);
        sysDictionarySearch.setLimit(1);
        sysDictionarySearch.setType(type);
        sysDictionarySearch.setValue(value);

        List<SysDictionary> sysDictionaries = null;
        if (Utils.enableRedisCache()) {
            sysDictionaries = SysDictionaryCacher.getPageList(sysDictionarySearch);
        } else {
            sysDictionaries = getPaginatedList(sysDictionarySearch);
        }

        if (sysDictionaries != null && sysDictionaries.size() > 0) {
            return sysDictionaries.get(0);
        }
        return null;
    }

    /**
     * 通过ValueKeys  和 type  查列表
     *
     * @param type
     * @return
     */
    @Override
    public List<SysDictionary> getListByValueKeys(String type, List<String> ValueKeys) {
        return sysDictionaryMapper.getListByValueKeys(type, ValueKeys);
    }


    /**
     * 根据type和att1查找SysDictionary
     *
     * @return
     * @author huangsongbo
     */
    @Override
    public List<SysDictionary> findAllByTypeAndAtt1(String type, String att1) {
        SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
        sysDictionarySearch.setStart(-1);
        sysDictionarySearch.setLimit(-1);
        sysDictionarySearch.setType(type);
        sysDictionarySearch.setAtt1(att1);
        sysDictionarySearch.setIsDeleted(0);
        return getPaginatedList(sysDictionarySearch);
    }

    @Override
    public int getAreaCount(String houseType) {
        logger.info("getAreaCount");
        return sysDictionaryMapper.selectAreaCount(houseType);
    }

    /**
     * 根据type查找sysDictionary
     *
     * @param type
     * @return
     */
    @Override
    public List<SysDictionary> findAllByType(String type) {
        SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
        sysDictionarySearch.setStart(-1);
        sysDictionarySearch.setLimit(-1);
        sysDictionarySearch.setOrder("ordering");
        sysDictionarySearch.setOrderNum("asc");
        sysDictionarySearch.setType(type);
        sysDictionarySearch.setIsDeleted(0);
        return getPaginatedList(sysDictionarySearch);
    }
}
