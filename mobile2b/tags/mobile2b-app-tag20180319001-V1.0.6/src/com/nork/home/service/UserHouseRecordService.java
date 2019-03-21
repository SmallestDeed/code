package com.nork.home.service;

import com.nork.home.model.MyHouseVO;
import com.nork.home.model.UserHouseRecord;
import com.nork.home.model.search.MyHouseSearch;

import java.util.List;

public interface UserHouseRecordService
{
    Integer getRecordCount(MyHouseSearch myHouseSearch);

    List<MyHouseVO> getRecordList(MyHouseSearch myHouseSearch);

    Integer insertRecord(UserHouseRecord userHouseRecord);
}
