package com.sandu.api.act.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.sandu.api.act.model.WxActBargainInviteRecordActDayAggregated;


@Component
public interface WxActBargainInviteRecordActDayAggregatedService {

    /**
     * 插入
     *
     * @param wxactbargain
     * @return
     */
    void create(WxActBargainInviteRecordActDayAggregated entity);

    /**
     * 更新
     *
     * @param wxactbargain
     * @return
     */
    int modifyById(WxActBargainInviteRecordActDayAggregated entity);

    
    /**
     * 通过ID获取详情
     *
     * @return
     */
    WxActBargainInviteRecordActDayAggregated get(String id);
    
    /**
     * 获取详情
     *
     * @return
     */
    WxActBargainInviteRecordActDayAggregated get(String actId, String openId,Date date);

    /**
     * 当天被邀请砍价次数
     * @param id
     * @param openId
     * @return
     */
	int getCurrentDateInviteCutCount(String actId, String openId);

	/**
	 * 当天是否已砍价
	 * @param actId
	 * @param openId
	 * @return
	 */
	boolean isCutCurrentDay(String actId, String openId);

	/**
	 * 当天被邀请砍价次数+1
	 * @param id
	 * @param openId
	 */
	void increaseCurrentDayInviteCutCount(String id, String openId);

	
     
    
}
