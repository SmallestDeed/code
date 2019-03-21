package com.sandu.api.act.service;

import org.springframework.stereotype.Component;

import com.sandu.api.act.model.WxActBargainInviteRecordActAggregated;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Component
public interface WxActBargainInviteRecordActAggregatedService {

    /**
     * 插入
     *
     * @param wxactbargain
     * @return
     */
    void create(WxActBargainInviteRecordActAggregated entity);

    /**
     * 更新
     *
     * @param wxactbargain
     * @return
     */
    int modifyById(WxActBargainInviteRecordActAggregated entity);

    
    /**
     * 通过ID获取详情
     *
     * @return
     */
    WxActBargainInviteRecordActAggregated get(String id);
    
    /**
     * 获取详情
     *
     * @return
     */
    WxActBargainInviteRecordActAggregated get(String actId, String openId);

    /**
     * 是否已砍价
     * @param actId
     * @param openId
     * @return
     */
	boolean isCut(String actId, String openId);

	/**
	 * 被邀请砍价次数
	 * @param actId
	 * @param openId
	 * @return
	 */
	int getInviteCutCount(String actId, String openId);

	
	void increaseInviteCutCount(String id, String openId);

	

    
}
