package com.sandu.service.act3.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.act3.model.LuckyWheelLotteryRecordActDayAggregated;

public interface LuckyWheelLotteryRecordActDayAggregatedMapper {
   
    int insert(LuckyWheelLotteryRecordActDayAggregated record);

    LuckyWheelLotteryRecordActDayAggregated selectById(String id);

    int updateById(LuckyWheelLotteryRecordActDayAggregated record);
    
    List<LuckyWheelLotteryRecordActDayAggregated> selectList(LuckyWheelLotteryRecordActDayAggregated record);

    /**
     * 增加抽奖次数
     * @param actId
     * @param openId
     * @param lotteryDate
     * @return
     */
	int updateToIncreaseLotteryCount(@Param("actId")String actId, @Param("openId")String openId,
			@Param("lotteryDate")Date lotteryDate);

	/**
	 * 扣减抽奖次数
	 * @param actId
	 * @param openId
	 * @param date
	 * @return
	 */
	int updateToReduceLotteryCount(@Param("actId")String actId, @Param("openId")String openId, @Param("lotteryDate")Date lotteryDate);
}