package com.sandu.api.act2.service;

import java.util.List;

import com.sandu.api.act2.model.RedPacketConfig;

public interface RedPacketConfigService {

	void create(RedPacketConfig entity);
	
	int modifyById(RedPacketConfig entity);
	 
	RedPacketConfig get(String id);
	
	List<RedPacketConfig> getList(RedPacketConfig entity);

	List<RedPacketConfig> getByActId(String actId);

	/**
	 * 获取红包配置
	 * @param actId
	 * @param redPacketSeq
	 * @return
	 */
	RedPacketConfig get(String actId, Integer redPacketSeq);
}
