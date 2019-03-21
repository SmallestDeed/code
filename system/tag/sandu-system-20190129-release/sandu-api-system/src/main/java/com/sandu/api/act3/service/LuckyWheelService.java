package com.sandu.api.act3.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.input.LuckyWheelQuery;
import com.sandu.api.act3.model.LuckyWheel;
import org.springframework.stereotype.Component;

@Component
public interface LuckyWheelService {

	void create(LuckyWheel entity);
	
	int modifyById(LuckyWheel entity);
	 
	LuckyWheel get(String id);	
	
	List<LuckyWheel> list(LuckyWheel entity);

	LuckyWheel get(String actId, String appId);

	Integer getStatus(LuckyWheel actEntity);

	Integer getStatus(String actId, String appId);

	/**
	 * 更新活动计数字段
	 * @param actId
	 * @param isAward
	 * @param isFirstLottery
	 */
	void increaseLotteryAndRegCount(String actId,boolean isAward,boolean isFirstLottery);

	PageInfo<LuckyWheel> pageList(LuckyWheelQuery query);

	/**
	 * 获取所有进行中,并且配置了每天规则的活动
	 * @return
	 */
	List<LuckyWheel> getAllActivePerDayConfigActList();

	Integer remove(String actId);

	

}
