package com.sandu.service.act.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act.input.WxActBargainInviteRecordQuery;
import com.sandu.api.act.model.WxActBargainInviteRecord;
import com.sandu.api.act.service.WxActBargainInviteRecordService;
import com.sandu.service.act.dao.WxActBargainInviteRecordMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * inviteRecord
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:36
 */
@Slf4j
@Service("wxActBargainInviteRecordService")
public class WxActBargainInviteRecordServiceImpl implements WxActBargainInviteRecordService {

    @Autowired
    private WxActBargainInviteRecordMapper inviterecordMapper;

	@Override
	public void createWxActBargainInviteRecord(WxActBargainInviteRecord inviteRecord) {
		// TODO Auto-generated method stub
		inviterecordMapper.insertWxActBargainInviteRecord(inviteRecord);
	}

	@Override
	public int modifyWxActBargainInviteRecord(WxActBargainInviteRecord inviteRecord) {
		// TODO Auto-generated method stub
		return inviterecordMapper.updateWxActBargainInviteRecordById(inviteRecord);
	}

	@Override
	public int removeWxActBargainInviteRecord(String inviteRecordId) {
		// TODO Auto-generated method stub
		return inviterecordMapper.deleteWxActBargainInviteRecordById(inviteRecordId);
	}

	@Override
	public WxActBargainInviteRecord getWxActBargainInviteRecord(String inviteRecordId) {
		// TODO Auto-generated method stub
		return inviterecordMapper.selectWxActBargainInviteRecordById(inviteRecordId);
	}

	@Override
	public List<WxActBargainInviteRecord> getWxActBargainInviteRecordList(WxActBargainInviteRecordQuery queryEntity) {
		// TODO Auto-generated method stub
		return inviterecordMapper.selectList(queryEntity);
	}

	@Override
	public WxActBargainInviteRecord getWxActBargainInviteRecord(String registrationId, String openId) {
		WxActBargainInviteRecordQuery queryEntity = new WxActBargainInviteRecordQuery();
		queryEntity.setIsDeleted(0);
		queryEntity.setOpenId(openId);
		queryEntity.setRegistrationId(registrationId);
		List<WxActBargainInviteRecord> list = this.getWxActBargainInviteRecordList(queryEntity);
		if(list==null || list.size()==0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean isCut(String registrationId, String openId) {
		// TODO Auto-generated method stub
		if(this.getWxActBargainInviteRecord(registrationId,openId)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public PageInfo<WxActBargainInviteRecord> getWxActBargainInviteRecordPageList(String regId, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		WxActBargainInviteRecordQuery queryEntity = new WxActBargainInviteRecordQuery();
		queryEntity.setIsDeleted(0);
		queryEntity.setRegistrationId(regId);
		List<WxActBargainInviteRecord> list = this.getWxActBargainInviteRecordList(queryEntity);
		return new PageInfo<WxActBargainInviteRecord>(list);
	}
	
	
	@Override
	public String getCutFriends(String regId) {
		if(StringUtils.isBlank(regId)) {
			return null;
		}
		WxActBargainInviteRecordQuery queryEntity = new WxActBargainInviteRecordQuery();
		queryEntity.setIsDeleted(0);
		queryEntity.setRegistrationId(regId);
		List<WxActBargainInviteRecord> list = this.getWxActBargainInviteRecordList(queryEntity);
		String cutFriendsStr = "";
		if(list!=null && list.size()>0) {
			int i=1;
			for(WxActBargainInviteRecord inviteRecord:list) {
				if(StringUtils.isNotBlank(inviteRecord.getNickname())) {
					cutFriendsStr += inviteRecord.getNickname()+",";
					i++;
				}
				if(i>=15) {
					break;
				}
			}
		}
		if(cutFriendsStr.length()>0) {
			cutFriendsStr = cutFriendsStr.substring(0, cutFriendsStr.length()-1);
		}
		return cutFriendsStr;
	}
}
