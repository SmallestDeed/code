package com.sandu.home.dao;

import com.sandu.home.model.BaseHouseApply;
import com.sandu.home.model.search.BaseHouseApplySearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: BaseHouseApplyMapper.java
 * @Package com.sandu.home.dao
 * @Description:户型房型-户型申请表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-10-13 11:45:31
 */
@Repository
public interface BaseHouseApplyMapper {
    int insertSelective(BaseHouseApply record);

    int updateByPrimaryKeySelective(BaseHouseApply record);

    int deleteByPrimaryKey(Integer id);

    BaseHouseApply selectByPrimaryKey(Integer id);

    int selectCount(BaseHouseApplySearch baseHouseApplySearch);

    List<BaseHouseApply> selectPaginatedList(
            BaseHouseApplySearch baseHouseApplySearch);

    List<BaseHouseApply> selectList(BaseHouseApply baseHouseApply);

    int isExistSubmit30Min(@Param("livingInfo") String livingInfo, @Param("userId")Integer userId);
}
