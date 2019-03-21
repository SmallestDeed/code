package com.sandu.service.space.dao;

import com.sandu.api.house.model.HouseSpaceNew;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseSpaceNewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HouseSpaceNew record);

    int insertSelective(HouseSpaceNew record);

    HouseSpaceNew selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HouseSpaceNew record);

    int updateByPrimaryKey(HouseSpaceNew record);
}