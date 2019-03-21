package com.sandu.service.act3.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act3.model.LuckyWheelLotteryRecordActDayAggregated;
import com.sandu.api.act3.service.LuckyWheelLotteryRecordActDayAggregatedService;
import com.sandu.service.act3.dao.LuckyWheelLotteryRecordActDayAggregatedMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("luckyWheelLotteryRecordActDayAggregatedService")
public class LuckyWheelLotteryRecordActDayAggregatedServiceImpl implements LuckyWheelLotteryRecordActDayAggregatedService{
	
	@Autowired
    private LuckyWheelLotteryRecordActDayAggregatedMapper luckyWheelLotteryRecordActDayAggregatedMapper;

	@Override
	public void create(LuckyWheelLotteryRecordActDayAggregated entity) {
		luckyWheelLotteryRecordActDayAggregatedMapper.insert(entity);
	}

	@Override
	public int modifyById(LuckyWheelLotteryRecordActDayAggregated entity) {
		return luckyWheelLotteryRecordActDayAggregatedMapper.updateById(entity);
	}

	@Override
	public LuckyWheelLotteryRecordActDayAggregated get(String id) {
		return luckyWheelLotteryRecordActDayAggregatedMapper.selectById(id);
	}

	@Override
	public List<LuckyWheelLotteryRecordActDayAggregated> list(LuckyWheelLotteryRecordActDayAggregated entity) {
		return luckyWheelLotteryRecordActDayAggregatedMapper.selectList(entity);
	}
	
	@Override
	public LuckyWheelLotteryRecordActDayAggregated get(String actId, String openId,Date date) {
		LuckyWheelLotteryRecordActDayAggregated query = new LuckyWheelLotteryRecordActDayAggregated();
		query.setActId(actId);
		query.setOpenId(openId);
		query.setLotteryDate(date);
		List<LuckyWheelLotteryRecordActDayAggregated> list = luckyWheelLotteryRecordActDayAggregatedMapper.selectList(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isExist(String actId, String openId,Date date) {
		LuckyWheelLotteryRecordActDayAggregated entity = this.get(actId, openId,date);
		if(entity!=null) {
			return true;
		}
		return false;
	}

	@Override
	public int increaseLotteryCount(String actId, String openId, Date date) {
		return luckyWheelLotteryRecordActDayAggregatedMapper.updateToIncreaseLotteryCount(actId,openId,date);
	}

	@Override
	public int reduceLotteryCount(String actId, String openId, Date date) {
		return luckyWheelLotteryRecordActDayAggregatedMapper.updateToReduceLotteryCount(actId,openId,date);
	}

}
