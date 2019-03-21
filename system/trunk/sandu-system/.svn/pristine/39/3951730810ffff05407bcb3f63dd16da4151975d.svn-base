package com.sandu.service.act.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act.model.WxActBargainInviteRecordActAggregated;
import com.sandu.api.act.service.WxActBargainInviteRecordActAggregatedService;
import com.sandu.service.act.dao.WxActBargainInviteRecordActAggregatedMapper;

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
@Service("wxActBargainInviteRecordActAggregatedService")
public class WxActBargainInviteRecordActAggregatedServiceImpl implements WxActBargainInviteRecordActAggregatedService {

    @Autowired
    private WxActBargainInviteRecordActAggregatedMapper inviteRecordActAggregatedMapper;

	@Override
	public void create(WxActBargainInviteRecordActAggregated entity) {
		inviteRecordActAggregatedMapper.insert(entity);
	}

	@Override
	public int modifyById(WxActBargainInviteRecordActAggregated entity) {
		return inviteRecordActAggregatedMapper.updateById(entity);
	}

	@Override
	public WxActBargainInviteRecordActAggregated get(String id) {
		return inviteRecordActAggregatedMapper.selectById(id);
	}

	@Override
	public boolean isCut(String actId, String openId) {
		WxActBargainInviteRecordActAggregated entity = this.get(actId, openId);
		if(entity!=null) {
			return true;
		}
		return false;
	}

	@Override
	public int getInviteCutCount(String actId, String openId) {
		WxActBargainInviteRecordActAggregated entity = this.get(actId, openId);
		if(entity!=null) {
			return entity.getInviteCutCount();
		}
		return 0;
	}
	
	@Override
	public WxActBargainInviteRecordActAggregated get(String actId, String openId) {
		WxActBargainInviteRecordActAggregated query = new WxActBargainInviteRecordActAggregated();
		query.setActId(actId);
		query.setOpenId(openId);
		List<WxActBargainInviteRecordActAggregated> list = inviteRecordActAggregatedMapper.selectList(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void increaseInviteCutCount(String actId, String openId) {
		WxActBargainInviteRecordActAggregated entity = this.get(actId, openId);
		WxActBargainInviteRecordActAggregated updEntity = new WxActBargainInviteRecordActAggregated();
		updEntity.setInviteCutCount(entity.getInviteCutCount()+1);
		updEntity.setId(entity.getId());
		this.modifyById(updEntity);
	}

	
	

	
}
