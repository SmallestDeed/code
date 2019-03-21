package com.sandu.api.act3.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.input.AwardInfo;
import com.sandu.api.act3.input.LuckyWheelLotteryRecordQuery;
import com.sandu.api.act3.model.LuckyWheelLotteryRecord;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

@Component
public interface LuckyWheelLotteryRecordService {

	void create(LuckyWheelLotteryRecord entity);
	
	int modifyById(LuckyWheelLotteryRecord entity);
	 
	LuckyWheelLotteryRecord get(String id);	
	
	List<LuckyWheelLotteryRecord> list(LuckyWheelLotteryRecord entity);

	/**
	 * 小程序（个人）抽奖记录列表查询服务
	 * @param actId 活动ID
	 * @param openId 用户openId
	 * @param pageNum 页数
	 * @param pageSize 每页数量
	 * @return
	 */
	PageInfo<LuckyWheelLotteryRecord> pageList(String actId, String openId,Integer pageNum, Integer pageSize);

	/**
	 * 运营平台抽奖记录列表查询服务
	 * @param query 查询入参
	 * @return
	 */
	PageInfo<LuckyWheelLotteryRecord> pageList(LuckyWheelLotteryRecordQuery query,Date beginTime,Date endTime);

	int award(AwardInfo awardInfo, SysUser user);

}
