package com.sandu.service.act3.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sandu.api.act3.input.LuckyWheelLotteryRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.input.AwardInfo;
import com.sandu.api.act3.model.LuckyWheelAwardMsg;
import com.sandu.api.act3.model.LuckyWheelLotteryRecord;
import com.sandu.api.act3.model.LuckyWheelPrize;
import com.sandu.api.act3.service.LuckyWheelAwardMsgService;
import com.sandu.api.act3.service.LuckyWheelLotteryRecordService;
import com.sandu.api.act3.service.LuckyWheelPrizeService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act3.dao.LuckyWheelLotteryRecordMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("luckyWheelLotteryRecordService")
public class LuckyWheelLotteryRecordServiceImpl implements LuckyWheelLotteryRecordService{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
    private LuckyWheelLotteryRecordMapper luckyWheelLotteryRecordMapper;

	@Autowired
    private LuckyWheelPrizeService luckyWheelPrizeService;
	
	@Autowired
    private LuckyWheelAwardMsgService luckyWheelAwardMsgService;
    
	@Override
	public void create(LuckyWheelLotteryRecord entity) {
		luckyWheelLotteryRecordMapper.insert(entity);
	}

	@Override
	public int modifyById(LuckyWheelLotteryRecord entity) {
		return luckyWheelLotteryRecordMapper.updateById(entity);
	}

	@Override
	public LuckyWheelLotteryRecord get(String id) {
		return luckyWheelLotteryRecordMapper.selectById(id);
	}

	@Override
	public List<LuckyWheelLotteryRecord> list(LuckyWheelLotteryRecord entity) {
		return luckyWheelLotteryRecordMapper.selectList(entity);
	}

	/**
	 * 小程序（个人）抽奖记录列表查询服务
	 * @param actId 活动ID
	 * @param openId 用户openId
	 * @param pageNum 页数
	 * @param pageSize 每页数量
	 * @return
	 */
	@Override
	public PageInfo<LuckyWheelLotteryRecord> pageList(String actId, String openId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> awardsStatusList = new ArrayList<Integer>();
		awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_UNAWRD);
		awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_WAIT_AWARD);
		awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_AWARDED);
		LuckyWheelLotteryRecord queryEntity = new LuckyWheelLotteryRecord();
		queryEntity.setActId(actId);
		queryEntity.setOpenId(openId);
		queryEntity.setAwardsStatusList(awardsStatusList);
		List<LuckyWheelLotteryRecord> list = this.list(queryEntity);
		return new PageInfo<LuckyWheelLotteryRecord>(list);
	}

	/**
	 * 运营平台抽奖记录列表查询服务
	 * @param query 查询入参
	 * @return
	 */
	@Override
	public PageInfo<LuckyWheelLotteryRecord> pageList(LuckyWheelLotteryRecordQuery query,Date beginTime,Date endTime) {
		PageHelper.startPage(query.getPageNum(),query.getPageSize());
		//处理中奖状态，前端未传值时，查询所有状态，前端传值，则查询指定状态
		List<Integer> awardsStatusList = new ArrayList<Integer>();
		if (null==query.getAwardsStatus()){
			awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_NONE_AWARD);//未中奖
			awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_UNAWRD);//未领奖
			awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_WAIT_AWARD);//待发放
			awardsStatusList.add(LuckyWheelLotteryRecord.AWARDS_STATUS_AWARDED);//已发放
		}else{
			awardsStatusList.add(query.getAwardsStatus());
		}
		//构造参数
		LuckyWheelLotteryRecord queryEntity = new LuckyWheelLotteryRecord();
		queryEntity.setActId(query.getActId());
		queryEntity.setNickname(query.getNickName());
		queryEntity.setMobile(query.getMobile());
		queryEntity.setOpenId(query.getOpenId());
		queryEntity.setPrizeName(query.getPrizeName());
		queryEntity.setAreaCode(query.getAreaCode());
		queryEntity.setCityCode(query.getCityCode());
		queryEntity.setProvinceCode(query.getProvinceCode());
		queryEntity.setShipmentStatus(query.getDeliverStatus());
		queryEntity.setLotteryTimeStart(beginTime);
		queryEntity.setLotteryTimeEnd(endTime);
		queryEntity.setAwardsStatusList(awardsStatusList);
		//调用查询服务
		List<LuckyWheelLotteryRecord> list = this.list(queryEntity);
		return new PageInfo<>(list);
	}

	@Override
	public int award(AwardInfo awardInfo, SysUser user) {
		LuckyWheelLotteryRecord lotteryEntity = this.get(awardInfo.getAwardId());
		if(lotteryEntity==null) {
			throw new SystemException("领奖异常:奖品不存在!");
		}
		if(!lotteryEntity.getOpenId().equals(user.getOpenId())) {
			throw new SystemException("领奖异常:非法用户!");
		}
		if(lotteryEntity.getAwardsStatus()!=LuckyWheelLotteryRecord.AWARDS_STATUS_UNAWRD) {
			throw new SystemException("领奖异常:请不要重复领奖!");
		}
		LuckyWheelPrize luckyWheelPrize = luckyWheelPrizeService.get(lotteryEntity.getPrizeId());
		if(luckyWheelPrize==null) {
			throw new SystemException("领奖异常:奖品不存在!");
		}
		if(luckyWheelPrize.getType()==0) {
			throw new SystemException("领奖异常:未中奖!");
		}
		
		//实物商品,需要邮寄
		if(luckyWheelPrize.getType()==LuckyWheelPrize.TYPE_PRODUCT) {
			checkReceiverInfo(awardInfo);
			Date now = new Date();
			lotteryEntity.setReceiveTime(now);
			lotteryEntity.setReceiver(awardInfo.getReceiver());
			lotteryEntity.setMobile(user.getMobile());
			lotteryEntity.setProvinceCode(awardInfo.getProvinceCode());
			lotteryEntity.setCityCode(awardInfo.getCityCode());
			lotteryEntity.setAreaCode(awardInfo.getAreaCode());
			lotteryEntity.setAddress(awardInfo.getAddress());
			lotteryEntity.setAwardsStatus(LuckyWheelLotteryRecord.AWARDS_STATUS_WAIT_AWARD);
		}else {
			lotteryEntity.setAwardsStatus(LuckyWheelLotteryRecord.AWARDS_STATUS_AWARDED);
			//增加中奖信息
			LuckyWheelAwardMsg luckyWheelAwardMsg = luckyWheelAwardMsgService.buildLuckyWheelAwardMsg(lotteryEntity.getActId(),
					lotteryEntity.getRegId(),luckyWheelPrize.getName(),user);
			luckyWheelAwardMsgService.create(luckyWheelAwardMsg);
		}
		
		return this.modifyById(lotteryEntity);
	}
	
	

	private void checkReceiverInfo(AwardInfo awardInfo) {
		if(StringUtils.isBlank(awardInfo.getReceiver())){
			throw new SystemException("领奖异常:收货人不能为空");
		}
		if(StringUtils.isBlank(awardInfo.getProvinceCode())){
			throw new SystemException("领奖异常:省份不能为空!");
		}
		if(StringUtils.isBlank(awardInfo.getCityCode())){
			throw new SystemException("领奖异常:城市不能为空!");
		}
		if(StringUtils.isBlank(awardInfo.getAreaCode())){
			throw new SystemException("领奖异常:区域不能为空!");
		}
		if(StringUtils.isBlank(awardInfo.getAddress())){
			throw new SystemException("领奖异常:详细地址不能为空!");
		}		
	}

}
