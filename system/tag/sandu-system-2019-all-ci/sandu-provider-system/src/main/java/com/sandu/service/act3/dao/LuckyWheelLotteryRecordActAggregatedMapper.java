package com.sandu.service.act3.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.act3.model.LuckyWheelLotteryRecordActAggregated;

public interface LuckyWheelLotteryRecordActAggregatedMapper {
  
	LuckyWheelLotteryRecordActAggregated selectById(String id);
	
	List<LuckyWheelLotteryRecordActAggregated> selectList(LuckyWheelLotteryRecordActAggregated record);

    int insert(LuckyWheelLotteryRecordActAggregated record);

    int updateById(LuckyWheelLotteryRecordActAggregated record);

	int updateToIncreaseLotteryCount(@Param("actId")String actId, @Param("openId")String openId);

	int updateToReduceLotteryCount(@Param("actId") String actId, @Param("openId") String openId);

}