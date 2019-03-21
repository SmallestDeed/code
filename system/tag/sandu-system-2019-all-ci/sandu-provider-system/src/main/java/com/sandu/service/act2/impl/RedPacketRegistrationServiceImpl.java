package com.sandu.service.act2.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act2.model.RedPacketAward;
import com.sandu.api.act2.model.RedPacketConfig;
import com.sandu.api.act2.model.RedPacketInviteRecord;
import com.sandu.api.act2.model.RedPacketRegistration;
import com.sandu.api.act2.output.InviteResultVO;
import com.sandu.api.act2.output.ReceiveRedPacketResultVO;
import com.sandu.api.act2.output.RedPacketJoinVO;
import com.sandu.api.act2.service.RedPacketAwardService;
import com.sandu.api.act2.service.RedPacketConfigService;
import com.sandu.api.act2.service.RedPacketInviteRecordService;
import com.sandu.api.act2.service.RedPacketRegistrationService;
import com.sandu.api.act2.service.RedPacketService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act2.dao.RedPacketRegistrationMapper;
import com.sandu.util.UUIDUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPacketRegistrationService")
public class RedPacketRegistrationServiceImpl implements RedPacketRegistrationService{

	@Autowired
    private RedPacketRegistrationMapper redPacketRegistrationMapper;
	
	@Autowired
    private RedPacketService redPacketService;
	
	@Autowired
    private RedPacketConfigService redPacketConfigService;
	
	@Autowired
    private RedPacketInviteRecordService redPacketInviteRecordService;
	
	@Autowired
    private RedPacketAwardService redPacketAwardService;
		
	@Override
	public void create(RedPacketRegistration entity) {
		redPacketRegistrationMapper.insert(entity);
	}
	
	@Override
	public int modifyById(RedPacketRegistration entity) {
		return redPacketRegistrationMapper.updateById(entity);
	}
	 
	@Override
	public RedPacketRegistration get(String id) {
		return redPacketRegistrationMapper.selectById(id);
	}
	
	@Override
	public RedPacketRegistration get(String actId,String openId) {
		RedPacketRegistration entity = new RedPacketRegistration();
		entity.setActId(actId);
		entity.setOpenId(openId);
		List<RedPacketRegistration> list = this.getList(entity);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<RedPacketRegistration> getList(RedPacketRegistration entity){
		return redPacketRegistrationMapper.selectList(entity);
	}

	

	@Override
	public RedPacketJoinVO join(String actId, SysUser user) {
		
		RedPacket actEntity = redPacketService.get(actId);
		Integer actStatus = redPacketService.getStatus(actEntity);
		if(RedPacket.STATUS_UNBEGIN==actStatus) {
			throw new SystemException("ACT_UNBEGIN","活动未开始");
		}
		if(RedPacket.STATUS_ENDED==actStatus) {
			throw new SystemException("ACT_END","活动已结束");
		}
		
		//如果任务已存在,则返回任务id
		RedPacketRegistration regEntity = this.get(actId,user.getOpenId());
		if(regEntity!=null) {
			return new RedPacketJoinVO(regEntity.getId());
		}
		
		//创建用户活动任务
		regEntity = buildRegEntity(actEntity,user);
		redPacketRegistrationMapper.insert(regEntity);
		
		redPacketService.increaseRegCount(actId);
		
		//记录邀请记录 (防止两个用户短时间内互相邀请)
		RedPacketInviteRecord entity = buildInviteRecord(regEntity.getId(),user);
		redPacketInviteRecordService.create(entity);
		
		//返回任务id
		return new RedPacketJoinVO(regEntity.getId());
		
	}

	private RedPacketRegistration buildRegEntity(RedPacket actEntity,SysUser user) {
		Date now = new Date();
		RedPacketRegistration regEntity = new RedPacketRegistration();
		regEntity.setId(UUIDUtil.getUUID());
		regEntity.setActId(actEntity.getId());
		regEntity.setOpenId(user.getOpenId());
		regEntity.setNickname(user.getNickName());
		regEntity.setHeadPic(user.getHeadPic());
		//regEntity.setMobile(mobile);
		regEntity.setJoinTime(now);
		regEntity.setInviteStatus(RedPacketRegistration.INVITE_STATUS_UNINVITE);
		regEntity.setAwardsStatus(RedPacketRegistration.AWARDS_STATUS_UNAWRD);
		regEntity.setAwardsCount(0);
		regEntity.setAwardsAmountSum(0.0d);
		regEntity.setInviteRecordCount(0);
		regEntity.setAppId(user.getAppId());
		regEntity.setCompanyId(user.getMiniProgramCompanyId());
		regEntity.setCreator(user.getNickName());
		regEntity.setGmtCreate(now);
		regEntity.setModifier(user.getNickName());
		regEntity.setGmtModified(now);
		regEntity.setIsDeleted(0);
		
		return regEntity;
	}

	@Override
	public InviteResultVO recordInvitation(String regId, SysUser user) {
		RedPacketRegistration regEntity = redPacketRegistrationMapper.selectById(regId);
		if(regEntity==null) {
			throw new SystemException("活动任务不存在!");
		}
		RedPacket actEntity = redPacketService.get(regEntity.getActId());
		Integer actStatus = redPacketService.getStatus(actEntity);
		if(RedPacket.STATUS_UNBEGIN==actStatus) {
			throw new SystemException("ACT_UNBEGIN","活动未开始");
		}
		if(RedPacket.STATUS_ENDED==actStatus) {
			throw new SystemException("ACT_END","活动已结束");
		}
		
		//检查用户有效性
		checkUser(user,regEntity);
		
		//记录邀请记录
		RedPacketInviteRecord entity = buildInviteRecord(regId,user);
		redPacketInviteRecordService.create(entity);
		
		//更新已邀请人数及邀请状态
		modifyInviteStatusAndIncreaseInviteNum(regEntity);
		
		List<RedPacketConfig> configList = redPacketConfigService.getByActId(regEntity.getActId());
		if(configList==null || configList.size()==0) {
			throw new SystemException("未配置红包选项规则.");
		}
		for(RedPacketConfig config:configList) {
			if((regEntity.getInviteRecordCount()+1)==config.getRequestInviteNum()){
				String packetAmount = (config.getMinAmount().doubleValue()==config.getMinAmount().intValue())?String.valueOf(config.getMinAmount().intValue()):String.valueOf(config.getMinAmount().doubleValue());
				return new InviteResultVO(packetAmount,config.getRequestInviteNum(),regEntity.getActId());
			}
		}
		return null;
	}
	
	private void modifyInviteStatusAndIncreaseInviteNum(RedPacketRegistration regEntity) {
		RedPacketRegistration entity = new RedPacketRegistration();
		entity.setId(regEntity.getId());
		entity.setInviteStatus(RedPacketRegistration.INVITE_STATUS_INVITED);
		entity.setInviteRecordCount(regEntity.getInviteRecordCount()+1);
		entity.setGmtModified(new Date());
		redPacketRegistrationMapper.updateById(entity);
		
	}

	private RedPacketInviteRecord buildInviteRecord(String regId,SysUser user) {
		RedPacketInviteRecord inviteRecord = new RedPacketInviteRecord();
		inviteRecord.setId(UUIDUtil.getUUID());
		inviteRecord.setRegId(regId);
		inviteRecord.setOpenId(user.getOpenId());
		inviteRecord.setNickname(user.getNickName());
		inviteRecord.setHeadPic(user.getHeadPic());
		inviteRecord.setAppId(user.getAppId());
		inviteRecord.setCompanyId(user.getMiniProgramCompanyId());
		inviteRecord.setGmtCreate(new Date());
		inviteRecord.setIsDeleted(0);
		return inviteRecord;
	}
	
	
	private void checkUser(SysUser user,RedPacketRegistration regEntity) {
		
		//不能自己邀请自己
		if(user.getOpenId().equals(regEntity.getOpenId())){
			throw new SystemException("不能自己邀请自己");
		}
		
		//是否重复邀请
		boolean  isExist = redPacketInviteRecordService.isExist(user.getOpenId());
		if(isExist) {
			throw new SystemException("重复邀请用户");
		}
		
		
		long sec = (new Date().getTime()-user.getGmtCreate().getTime())/ 1000; //用户创建时间距离当前时间(秒数)
		//一分钟内算新用户
		if(sec>=60) {
			throw new SystemException("老用户不算邀请用户");
		}
		
	}

	@Override
	public boolean isRechargeMobileBind(String regId, SysUser user) {
		RedPacketRegistration regEntity = redPacketRegistrationMapper.selectById(regId); 
		if(regEntity==null) {
			throw new SystemException("任务不存在!");
		}
		if(!regEntity.getOpenId().equals(user.getOpenId())) {
			throw new SystemException("非法用户!");
		}
		if(StringUtils.isBlank(regEntity.getMobile())) {
			return false;
		}		
		return true;
	}

	@Override
	public void bindRechargeMobile(String regId,String mobile, SysUser user) {
		RedPacketRegistration regEntity = redPacketRegistrationMapper.selectById(regId); 
		if(regEntity==null) {
			throw new SystemException("任务不存在!");
		}
		
		if(!regEntity.getOpenId().equals(user.getOpenId())) {
			throw new SystemException("非法用户!");
		}
		if(isBind(mobile)) {
			throw new SystemException("该手机号已参加过本次活动,请更换手机号!");
		}
		this.bindMobile(regId, mobile, user);
	}
	
	private boolean isBind(String mobile) {
		RedPacketRegistration entity = new RedPacketRegistration();
		entity.setMobile(mobile);
		List<RedPacketRegistration> list = this.getList(entity);
		if(list!=null && list.size()>0) {
			return true;
		}
		return false;
	}
	
	private void bindMobile(String regId,String mobile, SysUser user) {
		RedPacketRegistration entity = new RedPacketRegistration();
		entity.setId(regId);
		entity.setMobile(mobile);
		entity.setModifier(user.getNickName());
		entity.setGmtModified(new Date());
		redPacketRegistrationMapper.updateById(entity);
	}

	@Override
	public ReceiveRedPacketResultVO receiveRedPacket(String regId, Integer redPacketSeq,SysUser user) {
		RedPacketRegistration regEntity = redPacketRegistrationMapper.selectById(regId); 
		if(regEntity==null) {
			throw new SystemException("任务不存在!");
		}
		
		if(!regEntity.getOpenId().equals(user.getOpenId())) {
			throw new SystemException("非法用户!");
		}
		
		if(StringUtils.isBlank(regEntity.getMobile())) {
			throw new SystemException("充值手机号未绑定!");
		}
		
 
		RedPacket actEntity = redPacketService.get(regEntity.getActId());
		Integer actStatus = redPacketService.getStatus(actEntity);
		if(RedPacket.STATUS_UNBEGIN==actStatus) {
			throw new SystemException("ACT_UNBEGIN","活动未开始");
		}
		if(RedPacket.STATUS_ENDED==actStatus) {
			throw new SystemException("ACT_END","活动已结束");
		}
		
		
		RedPacketConfig config = redPacketConfigService.get(regEntity.getActId(),redPacketSeq);
		if(config==null) {
			throw new SystemException("活动对应红包选项不存在!");
		}
		
		if(regEntity.getInviteRecordCount()<config.getRequestInviteNum()) {
			throw new SystemException("邀请人数不满足!");
		}
		
		if(redPacketAwardService.isExist(regId,redPacketSeq)) {
			throw new SystemException("不能重复领取红包!");
		}
		//计算红包金额
		Double packetAmount = calRandomPacketAmount(config.getMaxAmount(),config.getMinAmount());
		
		//创建领奖记录
		RedPacketAward awdEntity = buildAward(regEntity,user,redPacketSeq,packetAmount);
		redPacketAwardService.create(awdEntity);
		
		//更新任务领奖信息
		modifyRegAwardInfo(regEntity,packetAmount,user);
		
		//扣减活动总金额
		int updFlag = redPacketService.reduceTotalAmount(actEntity.getId(),packetAmount);
		if(updFlag<=0) {
			throw new SystemException("ACT_END","活动已结束");
		}
		String packetAmountStr = (packetAmount.doubleValue()==packetAmount.intValue())
				?String.valueOf(packetAmount.intValue())
						:String.valueOf(packetAmount.doubleValue());
		return new ReceiveRedPacketResultVO(packetAmountStr,regEntity.getMobile(),awdEntity.getId());
	}
	
	private void modifyRegAwardInfo(RedPacketRegistration regEntity,Double packetAmount,SysUser user) {
		RedPacketRegistration entity = new RedPacketRegistration();
		entity.setId(regEntity.getId());
		entity.setAwardsStatus(RedPacketRegistration.AWARDS_STATUS_AWARDED);
		entity.setAwardsCount(regEntity.getAwardsCount()+1);
		entity.setAwardsAmountSum(regEntity.getAwardsAmountSum()+packetAmount);
		entity.setModifier(user.getNickName());
		entity.setGmtModified(new Date());
		redPacketRegistrationMapper.updateById(entity);
	}

	private Double calRandomPacketAmount(Double max,Double min) {
		if(max.doubleValue()==min.doubleValue()) {
			return max;
		}
		max = max*100;
		min = min*100;
		Random random = new Random();
		Double packetAmount = Double.valueOf(random.nextInt(max.intValue() - min.intValue())+ min.intValue())/100;
		return packetAmount;
	}
	
	private RedPacketAward buildAward(RedPacketRegistration regEntity,SysUser user,Integer redPacketSeq,Double packetAmount) {
		RedPacketAward awardEntity = new RedPacketAward();
		awardEntity.setId(UUIDUtil.getUUID());
		awardEntity.setActId(regEntity.getActId());
		awardEntity.setRegId(regEntity.getId());
		awardEntity.setOpenId(user.getOpenId());
		awardEntity.setNickname(user.getNickName());
		awardEntity.setMobile(regEntity.getMobile());
		awardEntity.setRedPacketSeq(redPacketSeq);
		awardEntity.setPacketAmount(packetAmount);
		awardEntity.setAppId(user.getAppId());
		awardEntity.setCompanyId(user.getMiniProgramCompanyId());
		awardEntity.setGmtCreate(new Date());
		awardEntity.setIsDeleted(0);
		return awardEntity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
