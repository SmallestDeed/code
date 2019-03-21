package com.sandu.api.applyHouse.service;

import com.sandu.api.applyHouse.model.BaseHouseApply;

import java.util.List;

public interface BaseHouseApplyService {
    BaseHouseApply selectByPrimaryKey(Integer applyHouseId);

    List<BaseHouseApply> selectApplyHouseOverSixHour();

    boolean updateEnteringAdviceMsgFlag(Integer id);

    void batchUpdateEnteringAdviceMsgFlag(List<Integer> houseIds);
}
