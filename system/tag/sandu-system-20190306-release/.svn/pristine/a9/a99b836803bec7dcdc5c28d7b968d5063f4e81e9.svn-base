package com.sandu.api.act2.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.act2.input.ActRedPacketQuery;
import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act2.model.search.RedPacketSearch;
import com.sandu.api.act2.output.RedPacketActAndRegDetailsVO;
import com.sandu.api.user.model.SysUser;

public interface RedPacketService {

	void create(RedPacket entity);
	
	int modifyById(RedPacket entity);
	 
	RedPacket get(String id);
	
	RedPacket get(String actId,String appId);
	
	
	List<RedPacket> getList(RedPacketSearch entity);

	 /**
     * 获取活动状态
     * @param wxActBargain
     * @return
     */
    Integer getStatus(RedPacket redPacket);
    
    /**
     *  获取活动状态
     * @param actId
     * @return
     */
    Integer getStatus(String actId,String appId);
    
    RedPacketActAndRegDetailsVO getUserRedPacketActAndRegDetails(String actId, SysUser user);

    /**
     * 根据id扣减活动总金额
     * @param id
     * @param packetAmount
     */
	int reduceTotalAmount(String id, Double packetAmount);

	/**
	 * 记录参与活动人数
	 * @param actId
	 */
	void increaseRegCount(String actId);

	/**
	 * 查询活动列表
	 * @param query
	 * @return
	 */
	PageInfo<RedPacket> list(ActRedPacketQuery query);
}
