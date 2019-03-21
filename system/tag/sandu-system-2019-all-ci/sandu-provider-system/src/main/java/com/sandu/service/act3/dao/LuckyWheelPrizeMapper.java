package com.sandu.service.act3.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.act3.model.LuckyWheelPrize;

public interface LuckyWheelPrizeMapper {
   
    int insert(LuckyWheelPrize record);

    LuckyWheelPrize selectById(String id);

    int updateById(LuckyWheelPrize record);
    
    List<LuckyWheelPrize> selectList(LuckyWheelPrize record);

	int updateToReducePrizeNum(String id);

	void updateToReinitTodayPrizeNum(@Param("actIdList") List<String> actIdList);
}