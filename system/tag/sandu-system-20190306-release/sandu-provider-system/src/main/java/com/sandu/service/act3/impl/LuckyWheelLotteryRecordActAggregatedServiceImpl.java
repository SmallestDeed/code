package com.sandu.service.act3.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act3.model.LuckyWheelLotteryRecordActAggregated;
import com.sandu.api.act3.service.LuckyWheelLotteryRecordActAggregatedService;
import com.sandu.service.act3.dao.LuckyWheelLotteryRecordActAggregatedMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("luckyWheelLotteryRecordActAggregatedService")
public class LuckyWheelLotteryRecordActAggregatedServiceImpl implements LuckyWheelLotteryRecordActAggregatedService{
	
	@Autowired
    private LuckyWheelLotteryRecordActAggregatedMapper luckyWheelLotteryRecordActAggregatedMapper;

	@Override
	public void create(LuckyWheelLotteryRecordActAggregated entity) {
		luckyWheelLotteryRecordActAggregatedMapper.insert(entity);
	}

	@Override
	public int modifyById(LuckyWheelLotteryRecordActAggregated entity) {
		return luckyWheelLotteryRecordActAggregatedMapper.updateById(entity);
	}
	
	@Override
	public LuckyWheelLotteryRecordActAggregated get(String id) {
		return luckyWheelLotteryRecordActAggregatedMapper.selectById(id);
	}

	@Override
	public LuckyWheelLotteryRecordActAggregated get(String actId, String openId) {
		LuckyWheelLotteryRecordActAggregated query = new LuckyWheelLotteryRecordActAggregated();
		query.setActId(actId);
		query.setOpenId(openId);
		List<LuckyWheelLotteryRecordActAggregated> list = luckyWheelLotteryRecordActAggregatedMapper.selectList(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<LuckyWheelLotteryRecordActAggregated> list(LuckyWheelLotteryRecordActAggregated entity) {
		return luckyWheelLotteryRecordActAggregatedMapper.selectList(entity);
	}

	@Override
	public boolean isExist(String actId, String openId) {
		LuckyWheelLotteryRecordActAggregated entity = this.get(actId, openId);
		if(entity!=null) {
			return true;
		}
		return false;
	}

	@Override
	public int increaseLotteryCount(String actId, String openId) {
		return luckyWheelLotteryRecordActAggregatedMapper.updateToIncreaseLotteryCount(actId,openId);
	}

	@Override
	public int reduceLotteryCount(String actId, String openId) {
		return luckyWheelLotteryRecordActAggregatedMapper.updateToReduceLotteryCount(actId,openId);
	}
	
	

}
