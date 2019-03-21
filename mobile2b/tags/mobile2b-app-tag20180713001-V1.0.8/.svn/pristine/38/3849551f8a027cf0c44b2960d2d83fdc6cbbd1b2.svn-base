package com.nork.home.dao;


import com.nork.home.model.MyHouseVO;
import com.nork.home.model.UserHouseRecord;
import com.nork.home.model.search.MyHouseSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHouseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserHouseRecord record);

    int insertSelective(UserHouseRecord record);

    UserHouseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserHouseRecord record);

    int updateByPrimaryKey(UserHouseRecord record);

    Integer getRecordCount(MyHouseSearch myHouseSearch);

    List<MyHouseVO> getRecordList(MyHouseSearch myHouseSearch);

    UserHouseRecord selectByUserAndHouse(Integer userId, Integer houseId);
}