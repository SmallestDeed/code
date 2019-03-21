package com.sandu.base.dao;


import com.sandu.base.model.BaseLiving;
import com.sandu.base.model.search.BaseLivingSearch;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @version V1.0
 * @Title: BaseLivingMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统-小区Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 14:41:11
 */
@Repository
public interface BaseLivingMapper {
    int insertSelective(BaseLiving record);

    int updateByPrimaryKeySelective(BaseLiving record);

    int deleteByPrimaryKey(Integer id);

    BaseLiving selectByPrimaryKey(Integer id);

    int selectCount(BaseLivingSearch baseLivingSearch);

    int getCountBySearch(BaseLivingSearch baseLivingSearch);

    Integer getCountByCreator(BaseLivingSearch baseLivingSearch);

    List<BaseLiving> selectPaginatedList(
            BaseLivingSearch baseLivingSearch);

    List<BaseLiving> selectList(BaseLiving baseLiving);
}
