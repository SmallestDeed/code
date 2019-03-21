package com.sandu.service.act2.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act.input.WxActBargainInviteRecordQuery;
import com.sandu.api.act.model.WxActBargainInviteRecord;
import com.sandu.api.act2.input.ActRedPacketQuery;
import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act2.model.RedPacketAward;
import com.sandu.api.act2.model.RedPacketConfig;
import com.sandu.api.act2.model.RedPacketRegistration;
import com.sandu.api.act2.model.search.RedPacketSearch;
import com.sandu.api.act2.output.RedPacketActAndRegDetailsVO;
import com.sandu.api.act2.output.RedPacketAwardDetailsVO;
import com.sandu.api.act2.service.RedPacketAwardService;
import com.sandu.api.act2.service.RedPacketConfigService;
import com.sandu.api.act2.service.RedPacketRegistrationService;
import com.sandu.api.act2.service.RedPacketService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act2.dao.RedPacketMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPacketService")
public class RedPacketServiceImpl implements RedPacketService{
	
	@Autowired
    private RedPacketMapper redPacketMapper;
	
	@Autowired
    private RedPacketRegistrationService redPacketRegistrationService;
	
	@Autowired
    private RedPacketConfigService redPacketConfigService;
	
	@Autowired
    private RedPacketAwardService redPacketAwardService;
		
	@Override
	public void create(RedPacket entity) {
		redPacketMapper.insert(entity);
	}
	
	@Override
	public int modifyById(RedPacket entity) {
		return redPacketMapper.updateById(entity);
	}
	 
	@Override
	public RedPacket get(String id) {
		return redPacketMapper.selectById(id);
	}
	
	
	@Override
	public List<RedPacket> getList(RedPacketSearch entity){
		return redPacketMapper.selectList(entity);
	}
	
	@Override
	public Integer getStatus(RedPacket actEntity) {
		Date now = new Date();
		if(actEntity!=null) {
			//已结束(过时,结束活动)
			if(actEntity.getEndTime().compareTo(now)<=0
					|| actEntity.getIsEnable()==0 || actEntity.getTotalAmount()<22) {
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
		RedPacket actEntity = this.get(actId,appId);
		if(actEntity==null) {
			throw new SystemException("ACT_NOT_EXIST","活动不存在");//活动不存在
		}
		return this.getStatus(actEntity);
	}
	
	@Override
	public RedPacketActAndRegDetailsVO getUserRedPacketActAndRegDetails(String actId, SysUser user) {
		RedPacketRegistration regEntity = redPacketRegistrationService.get(actId,user.getOpenId());
		
		//5.需要判断活动是否已结束,是否开始.
		RedPacketActAndRegDetailsVO resultVo = new RedPacketActAndRegDetailsVO();
		List<RedPacketAwardDetailsVO> list = new ArrayList<RedPacketAwardDetailsVO>();
		//获取红包配置列表
		List<RedPacketConfig> configList = redPacketConfigService.getByActId(actId);
		if(configList==null || configList.size()==0) {
			throw new SystemException("未配置红包选项规则.");
		}
		//用户已经参加此活动
		if(regEntity!=null) {
			Integer currentInviteNum = regEntity.getInviteRecordCount();
			resultVo.setRegId(regEntity.getId());
			resultVo.setCurrentInviteNum(currentInviteNum);
			List<RedPacketAward> awardList = redPacketAwardService.get(actId,user.getOpenId());
			for(RedPacketConfig config:configList) {
				RedPacketAwardDetailsVO awardVo;
				//邀请人数满足条件,则可以领奖
				if(currentInviteNum>=config.getRequestInviteNum()) {
					awardVo = new RedPacketAwardDetailsVO(config.getRedPacketSeq(), config.getRequestInviteNum(), config.getDesc(),false,true);
					//如果已经领奖,则不能重复领奖
					if(awardList!=null && awardList.size()>0) {
						for(RedPacketAward award:awardList) {
							if(award.getRedPacketSeq().intValue()==config.getRedPacketSeq().intValue()) {
								awardVo.setHasAward(true);
								awardVo.setCanAward(false);
							}
						}
					}
				}
				//不满足条件,不能领奖
				else {
					awardVo = new RedPacketAwardDetailsVO(config.getRedPacketSeq(), config.getRequestInviteNum(), config.getDesc(), false,false);
				}
				list.add(awardVo);
			}
			
		}else {
			resultVo.setCurrentInviteNum(0);
			for(RedPacketConfig config:configList) {
				list.add(new RedPacketAwardDetailsVO(config.getRedPacketSeq(), config.getRequestInviteNum(), config.getDesc(), false,false));
			}
		}
		resultVo.setAwardList(list);
		
		RedPacket actEntity = this.get(actId,user.getAppId());
		Integer actStatus = this.getStatus(actEntity);
		if(RedPacket.STATUS_UNBEGIN == actStatus) {
			resultVo.setActStatus(RedPacketRegistration.STATUS_CODE_ACT_UNBEGIN);
		}else if(RedPacket.STATUS_ONGOING == actStatus) {
			resultVo.setActStatus(RedPacketRegistration.STATUS_CODE_ACT_ONGOING);
		}else if(RedPacket.STATUS_ENDED == actStatus) {
			resultVo.setActStatus(RedPacketRegistration.STATUS_CODE_ACT_ENDED);
		}
		resultVo.setActName(actEntity.getActName());
		resultVo.setActRule(actEntity.getActRule());
		
		
		return resultVo;
		
	}

	
	@Override
	public RedPacket get(String actId,String appId) {
		RedPacketSearch queryEntity = new RedPacketSearch();
		queryEntity.setId(actId);
		queryEntity.setAppId(appId);
		List<RedPacket> list = this.getList(queryEntity);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int reduceTotalAmount(String actId, Double packetAmount) {
		return redPacketMapper.updateToReduceTotalAmount(actId,packetAmount);
	}

	@Override
	public void increaseRegCount(String actId) {
		redPacketMapper.updateToIncreaseRegCount(actId);
	}

	@Override
	public PageInfo<RedPacket> list(ActRedPacketQuery query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		RedPacketSearch searchEntity = new RedPacketSearch();
		if (StringUtils.isNotBlank(query.getName())) {
			searchEntity.setActName(query.getName());
		}
		if (query.getStatusCode() != null) {
			searchEntity.setStatusCode(query.getStatusCode());
		}
		List<RedPacket> list = this.getList(searchEntity);
		return new PageInfo<RedPacket>(list);
	}
	
	
	
	
	
	
}
