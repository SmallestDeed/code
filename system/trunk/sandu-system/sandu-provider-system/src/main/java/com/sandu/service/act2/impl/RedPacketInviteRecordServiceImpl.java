package com.sandu.service.act2.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sandu.api.act2.model.RedPacketInviteRecord;
import com.sandu.api.act2.service.RedPacketInviteRecordService;
import com.sandu.service.act2.dao.RedPacketInviteRecordMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPacketInviteRecordService")
public class RedPacketInviteRecordServiceImpl implements RedPacketInviteRecordService{

	@Autowired
    private RedPacketInviteRecordMapper redPacketInviteRecordMapper;
	
	
	@Override
	public void create(RedPacketInviteRecord entity) {
		redPacketInviteRecordMapper.insert(entity);
	}
	
	@Override
	public int modifyById(RedPacketInviteRecord entity) {
		return redPacketInviteRecordMapper.updateById(entity);
	}
	 
	@Override
	public RedPacketInviteRecord get(String id) {
		return redPacketInviteRecordMapper.selectById(id);
	}
	
	@Override
	public List<RedPacketInviteRecord> getList(RedPacketInviteRecord entity){
		return redPacketInviteRecordMapper.selectList(entity);
	}

	@Override
	public boolean isExist(String openId) {
		RedPacketInviteRecord inviteRecord = this.getByOpenId(openId);
		if(inviteRecord!=null) {
			return true;
		}
		return false;
	}

	@Override
	public RedPacketInviteRecord getByOpenId(String openId) {
		if(StringUtils.isBlank(openId)) {
			return null;
		}
		RedPacketInviteRecord entity = new RedPacketInviteRecord();
		entity.setOpenId(openId);
		List<RedPacketInviteRecord> list = this.getList(entity);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
