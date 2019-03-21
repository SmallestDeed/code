package com.sandu.service.act.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act.model.WxActBargain;
import com.sandu.api.act.model.WxActBargainInviteRecordActAggregated;
import com.sandu.api.act.model.WxActBargainInviteRecordActDayAggregated;
import com.sandu.api.act.service.WxActBargainInviteRecordActDayAggregatedService;
import com.sandu.service.act.dao.WxActBargainInviteRecordActDayAggregatedMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Slf4j
@Service("wxActBargainInviteRecordActDayAggregatedService")
public class WxActBargainInviteRecordActDayAggregatedServiceImpl implements WxActBargainInviteRecordActDayAggregatedService {

    @Autowired
    private WxActBargainInviteRecordActDayAggregatedMapper inviteRecordActDayAggregatedMapper;

	@Override
	public void create(WxActBargainInviteRecordActDayAggregated entity) {
		inviteRecordActDayAggregatedMapper.insert(entity);
	}

	@Override
	public int modifyById(WxActBargainInviteRecordActDayAggregated entity) {
		return inviteRecordActDayAggregatedMapper.updateById(entity);
	}

	@Override
	public WxActBargainInviteRecordActDayAggregated get(String id) {
		return inviteRecordActDayAggregatedMapper.selectById(id);
	}
	
	@Override
	public WxActBargainInviteRecordActDayAggregated get(String actId, String openId,Date date) {
		WxActBargainInviteRecordActDayAggregated query = new WxActBargainInviteRecordActDayAggregated();
		query.setActId(actId);
		query.setOpenId(openId);
		query.setInviteDate(date);
		List<WxActBargainInviteRecordActDayAggregated> list = inviteRecordActDayAggregatedMapper.selectList(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int getCurrentDateInviteCutCount(String actId, String openId) {
		WxActBargainInviteRecordActDayAggregated entity = this.get(actId, openId,new Date());
		if(entity!=null) {
			return entity.getInviteCutCount();
		}
		return 0;
	}

	@Override
	public boolean isCutCurrentDay(String actId, String openId) {
		WxActBargainInviteRecordActDayAggregated entity = this.get(actId, openId,new Date());
		if(entity!=null) {
			return true;
		}
		return false;
	}

	@Override
	public void increaseCurrentDayInviteCutCount(String actId, String openId) {
		WxActBargainInviteRecordActDayAggregated entity = this.get(actId, openId,new Date());
		WxActBargainInviteRecordActDayAggregated updEntity = new WxActBargainInviteRecordActDayAggregated();
		updEntity.setInviteCutCount(entity.getInviteCutCount()+1);
		updEntity.setId(entity.getId());
		this.modifyById(updEntity);
	}
}
