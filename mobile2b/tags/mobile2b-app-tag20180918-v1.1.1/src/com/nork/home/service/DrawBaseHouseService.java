package com.nork.home.service;

import com.nork.home.model.MyHouseVO;
import com.nork.home.model.search.MyHouseSearch;

import java.util.List;

public interface DrawBaseHouseService
{
    List<MyHouseVO> getMyDrawHouseList(MyHouseSearch myHouseSearch);

    Integer getMyDrawHouseCount(MyHouseSearch myHouseSearch);
}
