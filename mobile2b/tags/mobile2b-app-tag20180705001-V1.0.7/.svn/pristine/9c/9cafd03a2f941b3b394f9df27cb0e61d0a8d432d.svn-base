package com.nork.home.dao;


import com.nork.home.model.DrawBaseHouse;
import com.nork.home.model.MyHouseVO;
import com.nork.home.model.search.MyHouseSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawBaseHouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrawBaseHouse record);

    int insertSelective(DrawBaseHouse record);

    DrawBaseHouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrawBaseHouse record);

    int updateByPrimaryKey(DrawBaseHouse record);

    List<MyHouseVO> getMyDrawHouseList(MyHouseSearch myHouseSearch);

    Integer getMyDrawHouseCount(MyHouseSearch myHouseSearch);
}