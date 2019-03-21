package com.sandu.api.act2.service;

import java.util.List;

import com.sandu.api.act2.model.RedPacketAward;

public interface RedPacketAwardService {

	void create(RedPacketAward entity);
	
	int modifyById(RedPacketAward entity);
	 
	RedPacketAward get(String id);
	
	List<RedPacketAward> getList(RedPacketAward entity);

	List<RedPacketAward> get(String actId, String openId);

	/**
	 *是否存在
	 */
	boolean isExist(String regId, Integer redPacketSeq);
}
