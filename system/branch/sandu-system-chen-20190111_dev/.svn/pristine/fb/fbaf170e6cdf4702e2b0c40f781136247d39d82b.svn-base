package com.sandu.api.act3.service;

import java.util.Date;
import java.util.List;

import com.sandu.api.act3.model.LuckyWheelLotteryRecordActDayAggregated;

public interface LuckyWheelLotteryRecordActDayAggregatedService {

	void create(LuckyWheelLotteryRecordActDayAggregated entity);
	
	int modifyById(LuckyWheelLotteryRecordActDayAggregated entity);
	 
	LuckyWheelLotteryRecordActDayAggregated get(String id);	
	
	List<LuckyWheelLotteryRecordActDayAggregated> list(LuckyWheelLotteryRecordActDayAggregated entity);

	boolean isExist(String actId, String openId, Date date);

	LuckyWheelLotteryRecordActDayAggregated get(String actId, String openId, Date date);

	/**
	 * 增加抽奖次数
	 * @param actId
	 * @param openId
	 * @param date
	 * @return
	 */
	int increaseLotteryCount(String actId, String openId, Date date);


	/**
	 * 扣减抽奖次数
	 * @param actId
	 * @param openId
	 * @param date
	 * @return
	 */
	int reduceLotteryCount(String actId, String openId, Date date);

}
