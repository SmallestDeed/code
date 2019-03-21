package com.sandu.service.act3.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.input.LuckyWheelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act3.model.LuckyWheel;
import com.sandu.api.act3.service.LuckyWheelService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.service.act3.dao.LuckyWheelMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("luckyWheelService")
public class LuckyWheelServiceImpl implements LuckyWheelService{
	
	@Autowired
    private LuckyWheelMapper luckyWheelMapper;

	@Override
	public void create(LuckyWheel entity) {
		luckyWheelMapper.insert(entity);
	}

	@Override
	public int modifyById(LuckyWheel entity) {
		return luckyWheelMapper.updateById(entity);
	}

	@Override
	public LuckyWheel get(String id) {
		return luckyWheelMapper.selectById(id);
	}
	
	@Override
	public LuckyWheel get(String actId,String appId) {
		LuckyWheel queryEntity = new LuckyWheel();
		queryEntity.setId(actId);
		queryEntity.setAppId(appId);
		List<LuckyWheel> list = this.list(queryEntity);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<LuckyWheel> list(LuckyWheel entity) {
		return luckyWheelMapper.selectList(entity);
	}
	
	@Override
	public Integer getStatus(LuckyWheel actEntity) {
		Date now = new Date();
		if(actEntity!=null) {
			//已结束(过时,结束活动)
			if(actEntity.getEndTime().compareTo(now)<=0
					|| actEntity.getIsEnable()==0) {
				return RedPacket.STATUS_ENDED;
			}
			//未开始
			if(now.compareTo(actEntity.getBeginTime())<=0) {
				return RedPacket.STATUS_UNBEGIN;
			}
			//进行中
			return RedPacket.STATUS_ONGOING;
		}
		
		throw new SystemException("ACT_NOT_EXIST","活动不存在"); //活动不存在
	}

	@Override
	public Integer getStatus(String actId,String appId) {
		LuckyWheel actEntity = this.get(actId,appId);
		if(actEntity==null) {
			throw new SystemException("ACT_NOT_EXIST","活动不存在");//活动不存在
		}
		return this.getStatus(actEntity);
	}

	@Override
	public void increaseLotteryAndRegCount(String actId,boolean isAward,boolean isFirstLottery) {
		luckyWheelMapper.updateToIncreaseLotteryAndRegCount(actId,isAward,isFirstLottery);
		
	}


	@Override
	public PageInfo<LuckyWheel> pageList(LuckyWheelQuery query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		LuckyWheel queryEntity = new LuckyWheel();
		if (StringUtils.isNotBlank(query.getName())){
			queryEntity.setName(query.getName());
		}
		if (query.getStatusCode() != null) {
			queryEntity.setStatusCode(query.getStatusCode());
		}
		queryEntity.setAppId(query.getAppId());
		List<LuckyWheel> list = this.list(queryEntity);
		return new PageInfo<LuckyWheel>(list);
	}

	@Override
	public List<LuckyWheel> getAllActivePerDayConfigActList() {
		return luckyWheelMapper.selectAllActivePerDayConfigActList();	
	}


	@Override
	public Integer remove(String actId) {
		return luckyWheelMapper.delete(actId);
	}

}
