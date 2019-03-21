package com.sandu.api.act3.service;

import java.util.Date;
import java.util.List;

import com.sandu.api.act3.model.LuckyWheelLotteryRecordActAggregated;

public interface LuckyWheelLotteryRecordActAggregatedService {

	void create(LuckyWheelLotteryRecordActAggregated entity);
	
	int modifyById(LuckyWheelLotteryRecordActAggregated entity);
	 
	LuckyWheelLotteryRecordActAggregated get(String id);	
	
	LuckyWheelLotteryRecordActAggregated get(String actId, String openId);
	
	List<LuckyWheelLotteryRecordActAggregated> list(LuckyWheelLotteryRecordActAggregated entity);

	boolean isExist(String actId, String openId);

	int increaseLotteryCount(String actId, String openId);

	/**
	 * 扣减抽奖次数
	 * @param actId
	 * @param openId
	 * @return
	 */
	int reduceLotteryCount(String actId, String openId);

	

}
