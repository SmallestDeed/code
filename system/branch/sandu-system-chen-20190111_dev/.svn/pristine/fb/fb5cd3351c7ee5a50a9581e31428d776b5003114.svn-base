package com.sandu.api.act.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.act.input.WxActBargainInviteRecordQuery;
import com.sandu.api.act.model.WxActBargainInviteRecord;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * inviteRecord
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:36
 */
public interface WxActBargainInviteRecordService {

    /**
     * 插入
     *
     * @param inviterecord
     * @return
     */
    void createWxActBargainInviteRecord(WxActBargainInviteRecord inviteRecord);

    /**
     * 更新
     *
     * @param inviterecord
     * @return
     */
    int modifyWxActBargainInviteRecord(WxActBargainInviteRecord inviteRecord);

    /**
     * 删除
     *
     * @param inviterecordIds
     * @return
     */
    int removeWxActBargainInviteRecord(String inviteRecordId);

    /**
     * 获取详情
     *
     * @param inviterecordId
     * @return
     */
     WxActBargainInviteRecord getWxActBargainInviteRecord(String inviteRecordId);
     
     /**
      * 获取详情
      * @param registrationId
      * @param openId
      * @return
      */
     WxActBargainInviteRecord getWxActBargainInviteRecord(String registrationId, String openId);
     
     
     
     /**
      * 查询列表
      * @param queryEntity
      * @return
      */
     List<WxActBargainInviteRecord> getWxActBargainInviteRecordList(WxActBargainInviteRecordQuery queryEntity);

     /**
      * 是否已帮好友砍过价
      * @param registrationId
      * @param openId
      * @return
      */
	boolean isCut(String registrationId, String openId);

	/**
	 * 分页查询邀请记录
	 * @param regId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<WxActBargainInviteRecord> getWxActBargainInviteRecordPageList(String regId, Integer pageNum, Integer pageSize);

	/**
	 * 获取好友列表,以逗号分隔
	 * @param regId
	 * @return
	 */
	String getCutFriends(String regId);

	 

}
