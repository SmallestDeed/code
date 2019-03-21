package com.sandu.service.applyHouse.dao;

import com.sandu.api.applyHouse.model.BaseHouseApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseHouseApplyDao {
    BaseHouseApply selectByPrimaryKey(Integer applyHouseId);

    List<BaseHouseApply> selectApplyHouseOverSixHour();

    int updateEnteringAdviceMsgFlag(Integer id);

    void batchUpdateEnteringAdviceMsgFlag(@Param("houseIds") List<Integer> houseIds);
}
