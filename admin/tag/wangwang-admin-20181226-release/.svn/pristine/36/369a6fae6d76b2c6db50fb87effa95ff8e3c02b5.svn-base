package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.SpaceCommon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpaceCommonMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpaceCommon record);

    int insertSelective(SpaceCommon record);

    SpaceCommon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpaceCommon record);

    int updateByPrimaryKey(SpaceCommon record);

    List<SpaceCommon> listByIds(@Param("spaceTypeIds") List<String> spaceTypeIds);

    List<SpaceCommon> getByFunctionId(@Param("functionIds") List<Integer> functionIds);
}