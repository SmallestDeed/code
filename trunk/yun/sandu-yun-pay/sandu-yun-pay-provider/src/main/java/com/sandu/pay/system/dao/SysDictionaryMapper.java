package com.sandu.pay.system.dao;

import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysDictionaryMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统管理-数据字典Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
@Repository
public interface SysDictionaryMapper {
    int insertSelective(SysDictionary record);

    int updateByPrimaryKeySelective(SysDictionary record);

    int deleteByPrimaryKey(Integer id);

    SysDictionary selectByPrimaryKey(Integer id);

    int selectCount(SysDictionarySearch sysDictionarySearch);

    List<SysDictionary> selectPaginatedList(SysDictionarySearch sysDictionarySearch);

    List<SysDictionary> selectList(SysDictionary sysDictionary);

    List<String> findValueKeyByType(String type);

    SysDictionary findOneByTypeAndValueAndValue(String type, Integer productTypeValue, Integer smallTypeValue);

    /**
     * 通过ValueKeys  和 type  查列表
     *
     * @param type
     * @return
     */
    List<SysDictionary> getListByValueKeys(@Param("type") String type, @Param("ValueKeys") List<String> ValueKeys);

    int selectAreaCount(String houseType);

    List<SysDictionary> selectAreaList(String houseType);

}
