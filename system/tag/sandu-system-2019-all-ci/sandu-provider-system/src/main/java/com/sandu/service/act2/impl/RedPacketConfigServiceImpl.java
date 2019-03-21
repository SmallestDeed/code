package com.sandu.service.act2.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act2.model.RedPacketConfig;
import com.sandu.api.act2.service.RedPacketConfigService;
import com.sandu.service.act2.dao.RedPacketConfigMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redPacketConfigService")
public class RedPacketConfigServiceImpl implements RedPacketConfigService{

	@Autowired
    private RedPacketConfigMapper redPacketConfigMapper;
	
	@Override
	public void create(RedPacketConfig entity) {
		redPacketConfigMapper.insert(entity);
	}
	
	@Override
	public int modifyById(RedPacketConfig entity) {
		return redPacketConfigMapper.updateById(entity);
	}
	 
	@Override
	public RedPacketConfig get(String id) {
		return redPacketConfigMapper.selectById(id);
	}
	
	@Override
	public List<RedPacketConfig> getList(RedPacketConfig entity){
		return redPacketConfigMapper.selectList(entity);
	}

	@Override
	public List<RedPacketConfig> getByActId(String actId) {
		RedPacketConfig entity = new RedPacketConfig();
		entity.setActId(actId);
		return this.getList(entity);
	}

	@Override
	public RedPacketConfig get(String actId, Integer redPacketSeq) {
		RedPacketConfig entity = new RedPacketConfig();
		entity.setActId(actId);
		entity.setRedPacketSeq(redPacketSeq);
		List<RedPacketConfig> list = this.getList(entity);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
