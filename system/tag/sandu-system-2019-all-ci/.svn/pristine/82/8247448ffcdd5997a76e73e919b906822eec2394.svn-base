package com.sandu.service.act.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act.input.WxActBargainAwardAdd;
import com.sandu.api.act.model.WxActBargainAward;
import com.sandu.api.act.model.WxActBargainAwardMsg;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.service.WxActBargainAwardMsgService;
import com.sandu.api.act.service.WxActBargainAwardService;
import com.sandu.api.act.service.WxActBargainRegistrationService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act.dao.WxActBargainAwardMapper;
import com.sandu.util.UUIDUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * award
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:25
 */
@Slf4j
@Service("wxActBargainAwardService")
public class WxActBargainAwardServiceImpl implements WxActBargainAwardService {

    @Autowired
    private WxActBargainAwardMapper wxActBargainAwardMapper;

    
    @Autowired
    private WxActBargainAwardMsgService wxActBargainAwardMsgService;


    @Autowired
    private WxActBargainRegistrationService wxActBargainRegistrationService;


	@Override
	public void createWxActBargainAward(WxActBargainAward wxActBargainAward) {
		// TODO Auto-generated method stub
		wxActBargainAwardMapper.insertWxActBargainAward(wxActBargainAward);
	}

	@Override
	public int modifyWxActBargainAward(WxActBargainAward wxActBargainAward) {
		// TODO Auto-generated method stub
		return wxActBargainAwardMapper.updateWxActBargainAwardById(wxActBargainAward);
	}

	@Override
	public int removeWxActBargainAward(String awardId) {
		// TODO Auto-generated method stub
		return wxActBargainAwardMapper.deleteWxActBargainAwardById(awardId);
	}

	@Override
	public WxActBargainAward getWxActBargainAward(String awardId) {
		// TODO Auto-generated method stub
		return wxActBargainAwardMapper.selectWxActBargainAwardById(awardId);
	}


	@Override
	public void addAwardRecord(WxActBargainAwardAdd wxActBargainAwardAdd, SysUser user) {
		//验证当前用户是否是获奖用户(防止恶意领奖)
		WxActBargainRegistration regEntity = wxActBargainRegistrationService.getWxActBargainRegistration(wxActBargainAwardAdd.getRegId());
		if(regEntity==null) {
			throw new SystemException("任务不存在!");
		}
		if(!regEntity.getOpenId().equals(user.getOpenId())) {
			throw new SystemException("非法用户!");
		}
		//需要保证任务已完成,未领奖并且没异常
		if(regEntity.getCompleteStatus()==WxActBargainRegistration.COMPLETE_STATUS_FINISH
				&&regEntity.getExceptionStatus()==WxActBargainRegistration.EXCEPTION_STATUS_OK
				&&regEntity.getAwardsStatus()==WxActBargainRegistration.AWARDS_STATUS_UNAWRD) {
			int updateCount = wxActBargainRegistrationService.refreshRegAwardStatusToWait(regEntity.getId());
			if(updateCount <= 0) {
				throw new SystemException("请不要重复领奖!");
			}
			//保存领奖记录
			WxActBargainAward awardEntity = buildWxActBargainAward(wxActBargainAwardAdd,regEntity,user);
			wxActBargainAwardMapper.insertWxActBargainAward(awardEntity);
			//生成领奖消息
			WxActBargainAwardMsg awardMsgEntity = buildWxActBargainAwardMsg(wxActBargainAwardAdd,regEntity,user);
			wxActBargainAwardMsgService.createWxActBargainAwardMsg(awardMsgEntity);
		}else {
			throw new SystemException("非法操作!");
		}

	}
	private WxActBargainAward buildWxActBargainAward(WxActBargainAwardAdd wxActBargainAwardAdd,WxActBargainRegistration regEntity,SysUser user) {
		WxActBargainAward awardEntity = new WxActBargainAward();
		awardEntity.setId(UUIDUtil.getUUID());
		awardEntity.setActId(regEntity.getActId());
		awardEntity.setRegistrationId(regEntity.getId());
		awardEntity.setOpenId(user.getOpenId());
		awardEntity.setNickname(user.getNickName());
		awardEntity.setReceiver(wxActBargainAwardAdd.getReceiver());
		awardEntity.setMobile(wxActBargainAwardAdd.getMobile());
		awardEntity.setAddress(wxActBargainAwardAdd.getAddress());
		awardEntity.setAppId(user.getAppId());
		awardEntity.setGmtCreate(new Date());
		awardEntity.setIsDeleted(0);
		return awardEntity;
	}

	private WxActBargainAwardMsg buildWxActBargainAwardMsg(WxActBargainAwardAdd wxActBargainAwardAdd,WxActBargainRegistration regEntity,SysUser user) {
		WxActBargainAwardMsg awardMsgEntity = new WxActBargainAwardMsg();
		Date now = new Date();
		awardMsgEntity.setId(UUIDUtil.getUUID());
		awardMsgEntity.setActId(regEntity.getActId());
		awardMsgEntity.setRegistrationId(regEntity.getId());
		awardMsgEntity.setOpenId(user.getOpenId());
		//如果是整数,则去掉小数点
		if(regEntity.getProductMinPrice().doubleValue()==regEntity.getProductMinPrice().intValue()) {
			awardMsgEntity.setMessage(user.getNickName()+"已"+regEntity.getProductMinPrice().intValue()+"元领取"+regEntity.getProductName());
		}else {
			awardMsgEntity.setMessage(user.getNickName()+"已"+regEntity.getProductMinPrice()+"元领取"+regEntity.getProductName());
		}
		awardMsgEntity.setAppId(user.getAppId());
		awardMsgEntity.setCreator(user.getNickName());
		awardMsgEntity.setGmtCreate(now);
		awardMsgEntity.setModifier(user.getNickName());
		awardMsgEntity.setGmtModified(now);
		awardMsgEntity.setIsDeleted(0);
		return awardMsgEntity;
	}

}
