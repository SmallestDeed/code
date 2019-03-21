package com.sandu.service.act.impl;

import com.sandu.api.act.input.WxActBargainAwardMsgQuery;
import com.sandu.api.act.output.WxActBargainAwardMsgVO;
import com.sandu.api.act.output.WxActBargainAwardMsgWebVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act.input.WxActBargainAwardAdd;
import com.sandu.api.act.model.WxActBargainAwardMsg;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.service.WxActBargainAwardMsgService;
import com.sandu.api.act.service.WxActBargainRegistrationService;
import com.sandu.api.user.model.SysUser;
import com.sandu.commons.exceptions.BizException;
import com.sandu.service.act.dao.WxActBargainAwardMsgMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * awardMsg
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:33
 */
@Slf4j
@Service("wxActBargainAwardMsgService")
public class WxActBargainAwardMsgServiceImpl implements WxActBargainAwardMsgService {

    @Autowired
    private WxActBargainAwardMsgMapper wxActBargainAwardMsgMapper;


	@Override
	public void createWxActBargainAwardMsg(WxActBargainAwardMsg wxActBargainAwardMsg) {
		wxActBargainAwardMsgMapper.insertWxActBargainAwardMsg(wxActBargainAwardMsg);
	}

	@Override
	public int modifyWxActBargainAwardMsg(WxActBargainAwardMsg wxActBargainAwardMsg) {
		// TODO Auto-generated method stub
		return wxActBargainAwardMsgMapper.updateWxActBargainAwardMsgById(wxActBargainAwardMsg);
	}

	@Override
	public int removeWxActBargainAwardMsg(String awardMsgId,String modifier) {
		return wxActBargainAwardMsgMapper.deleteWxActBargainAwardMsgById(awardMsgId,modifier);
	}

	@Override
	public WxActBargainAwardMsg getWxActBargainAwardMsg(String awardMsgId) {
		// TODO Auto-generated method stub
		return wxActBargainAwardMsgMapper.selectWxActBargainAwardMsgById(awardMsgId);
	}




	@Override
	public List<WxActBargainAwardMsgVO> getListByActId(WxActBargainAwardMsgQuery query) {
		return wxActBargainAwardMsgMapper.selectListByActId(query);
	}

	@Override
	public int getCountByActId(WxActBargainAwardMsgQuery query) {
		return wxActBargainAwardMsgMapper.selectCountByActId(query);
	}

	@Override
	public List<WxActBargainAwardMsgWebVO> getMsgRandomList(String actId) {
		return wxActBargainAwardMsgMapper.selectMsgRandomList(actId);
	}


}
