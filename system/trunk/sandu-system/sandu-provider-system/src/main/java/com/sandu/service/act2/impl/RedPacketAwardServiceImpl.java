package com.sandu.service.act2.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act2.model.RedPacketAward;
import com.sandu.api.act2.service.RedPacketAwardService;
import com.sandu.service.act2.dao.RedPacketAwardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPacketAwardService")
public class RedPacketAwardServiceImpl implements RedPacketAwardService{
	
	@Autowired
    private RedPacketAwardMapper redPacketAwardMapper;
	
	@Override
	public void create(RedPacketAward entity) {
		redPacketAwardMapper.insert(entity);
	}
	
	@Override
	public int modifyById(RedPacketAward entity) {
		return redPacketAwardMapper.updateById(entity);
	}
	 
	@Override
	public RedPacketAward get(String id) {
		return redPacketAwardMapper.selectById(id);
	}
	
	@Override
	public List<RedPacketAward> getList(RedPacketAward entity){
		return redPacketAwardMapper.selectList(entity);
	}

	@Override
	public List<RedPacketAward> get(String actId, String openId) {
		RedPacketAward entity = new RedPacketAward();
		entity.setActId(actId);
		entity.setOpenId(openId);
		return this.getList(entity);
	}

	@Override
	public boolean isExist(String regId, Integer redPacketSeq) {
		RedPacketAward entity = new RedPacketAward();
		entity.setRegId(regId);
		entity.setRedPacketSeq(redPacketSeq);
		List<RedPacketAward> list = this.getList(entity);
		if(list!=null && list.size()>0) {
			return true;
		}
		return false;
	}
}
