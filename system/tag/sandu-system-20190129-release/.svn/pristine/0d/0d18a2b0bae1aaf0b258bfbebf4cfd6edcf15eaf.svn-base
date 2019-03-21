package com.sandu.api.act2.service;

import java.util.List;

import com.sandu.api.act2.model.RedPacketRegistration;
import com.sandu.api.act2.output.InviteResultVO;
import com.sandu.api.act2.output.ReceiveRedPacketResultVO;
import com.sandu.api.act2.output.RedPacketJoinVO;
import com.sandu.api.user.model.SysUser;

public interface RedPacketRegistrationService {

	void create(RedPacketRegistration entity);
	
	int modifyById(RedPacketRegistration entity);
	 
	RedPacketRegistration get(String id);
	
	RedPacketRegistration get(String actId,String openId);
	
	List<RedPacketRegistration> getList(RedPacketRegistration entity);

	

	/**
	 * 加入活动
	 * @param actId
	 * @param user
	 * @return
	 */
	RedPacketJoinVO join(String actId, SysUser user);

	/**
	 * 好友进入时,更新任务进度
	 * @param regId
	 * @param user
	 * @return
	 */
	InviteResultVO recordInvitation(String regId, SysUser user);
	
	
	/**
	 * 是否已绑定充值手机号
	 * @param regId 任务id
	 * @param user  用户
	 * @return
	 */
	boolean isRechargeMobileBind(String regId, SysUser user);

	
	/**
	 * 绑定充值手机号
	 * @param regId
	 */
	void bindRechargeMobile(String regId,String mobile, SysUser user);

	/**
	 * 领取红包
	 * @param regId
	 * @param user
	 * @return
	 */
	ReceiveRedPacketResultVO receiveRedPacket(String regId, Integer redPacketSeq,SysUser user);
}
