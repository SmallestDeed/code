package com.sandu.designtemplate.dao;

import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @version V1.0
 * @Title: DesignTempletMapper.java
 * @Package com.sandu.design.dao
 * @Description:设计模块-设计方案样板房表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 */
@Repository
public interface DesignTempletMapper {
    int insertSelective(DesignTemplet record);

    int updateByPrimaryKeySelective(DesignTemplet record);

    int deleteByPrimaryKey(Integer id);

    DesignTemplet selectByPrimaryKey(Integer id);

    List<DesignTemplet> selectPaginatedList(DesignTempletSearch designTempletSearch);

    int selectCount(DesignTempletSearch designTempletSearch);

	List<DesignTemplet> getListByHouseId(@Param("houseId") Integer houseId);

    DesignTemplet selectPlanStyleAndAreaByTempletId(Integer templateId);
}
