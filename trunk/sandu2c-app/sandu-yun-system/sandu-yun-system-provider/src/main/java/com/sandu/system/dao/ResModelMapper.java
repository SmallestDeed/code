package com.sandu.system.dao;

import com.sandu.system.model.ResModel;
import com.sandu.system.model.search.ResModelSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResModelMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统-模型资源库Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 16:05:22
 */
@Repository
public interface ResModelMapper {
    int insertSelective(ResModel record);

    int updateByPrimaryKeySelective(ResModel record);

    int deleteByPrimaryKey(Integer id);

    ResModel selectByPrimaryKey(Integer id);

    int selectCount(ResModelSearch resModelSearch);

    List<ResModel> selectPaginatedList(
            ResModelSearch resModelSearch);

    List<ResModel> selectList(ResModel resModel);
}
