package com.sandu.service.act3.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act3.model.LuckyWheel;
import com.sandu.api.act3.model.LuckyWheelPrize;
import com.sandu.api.act3.output.LuckyWheelPrizeVO;
import com.sandu.api.act3.service.LuckyWheelPrizeService;
import com.sandu.api.act3.service.LuckyWheelService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act3.dao.LuckyWheelPrizeMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("luckyWheelPrizeService")
public class LuckyWheelPrizeServiceImpl implements LuckyWheelPrizeService{
	
	@Autowired
    private LuckyWheelPrizeMapper luckyWheelPrizeMapper;

	@Autowired
    private LuckyWheelService luckyWheelService;
	
	@Override
	public void create(LuckyWheelPrize entity) {
		luckyWheelPrizeMapper.insert(entity);
	}

	@Override
	public int modifyById(LuckyWheelPrize entity) {
		return luckyWheelPrizeMapper.updateById(entity);
	}

	@Override
	public LuckyWheelPrize get(String id) {
		return luckyWheelPrizeMapper.selectById(id);
	}

	@Override
	public List<LuckyWheelPrize> list(LuckyWheelPrize entity) {
		return luckyWheelPrizeMapper.selectList(entity);
	}

	@Override
	public List<LuckyWheelPrize> getByActId(String actId) {
		LuckyWheelPrize entity = new LuckyWheelPrize();
		entity.setActId(actId);
		return this.list(entity);
	}

	@Override
	public List<LuckyWheelPrizeVO> getPrizeList(String actId,SysUser user) {
		Integer actStatus = luckyWheelService.getStatus(actId, user.getAppId());
		if(RedPacket.STATUS_UNBEGIN==actStatus) {
			throw new SystemException("ACT_UNBEGIN","活动未开始");
		}
		if(RedPacket.STATUS_ENDED==actStatus) {
			throw new SystemException("ACT_END","活动已结束");
		}
		
		List<LuckyWheelPrize> list = this.getByActId(actId);
		if(list==null || list.size()==0) {
			throw new SystemException("未配置奖项.");
		}			
				
		List<LuckyWheelPrizeVO> resultList = new ArrayList<LuckyWheelPrizeVO>();
		
		for(LuckyWheelPrize prize:list) {
			resultList.add(new LuckyWheelPrizeVO(prize.getId(),prize.getName(),prize.getImg()));
		}
		
		return resultList;
	}

	@Override
	public int reducePrizeNum(String id) {
		return luckyWheelPrizeMapper.updateToReducePrizeNum(id);
	}

	@Override
	public void reInitPrizeNum() {
		List<String> activePerDayConfigActIdList = new ArrayList<String>();
		List<LuckyWheel> list = luckyWheelService.getAllActivePerDayConfigActList();
		if(list!=null && list.size()>0) {
			for(LuckyWheel actEntity:list) {
				activePerDayConfigActIdList.add(actEntity.getId());
			}
		}
		luckyWheelPrizeMapper.updateToReinitTodayPrizeNum(activePerDayConfigActIdList);
	}
	

	
}
