package com.sandu.service.act.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.act.model.WxActBargainDecorateRecord;
import com.sandu.api.act.service.WxActBargainDecorateRecordService;
import com.sandu.service.act.dao.WxActBargainDecorateRecordMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorateRecord
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:34
 */
@Slf4j
@Service("wxActBargainDecorateRecordService")
public class WxActBargainDecorateRecordServiceImpl implements WxActBargainDecorateRecordService {

    @Autowired
    private WxActBargainDecorateRecordMapper wxActBargainDecorateRecordMapper;

	@Override
	public void createWxActBargainDecorateRecord(WxActBargainDecorateRecord decorateRecord) {
		// TODO Auto-generated method stub
		wxActBargainDecorateRecordMapper.insertWxActBargainDecorateRecord(decorateRecord);
	}

	@Override
	public int modifyWxActBargainDecorateRecord(WxActBargainDecorateRecord decorateRecord) {
		// TODO Auto-generated method stub
		return wxActBargainDecorateRecordMapper.updateWxActBargainDecorateRecordById(decorateRecord);
	}

	@Override
	public int removeWxActBargainDecorateRecord(String decorateRecordId) {
		// TODO Auto-generated method stub
		return wxActBargainDecorateRecordMapper.deleteWxActBargainDecorateRecordById(decorateRecordId);
	}

	@Override
	public WxActBargainDecorateRecord getWxActBargainDecorateRecord(String decorateRecordId) {
		// TODO Auto-generated method stub
		return wxActBargainDecorateRecordMapper.selectWxActBargainDecorateRecordById(decorateRecordId);
	}

	@Override
	public WxActBargainDecorateRecord getWxActBargainDecorateRecordByRegId(String regId) {
		// TODO Auto-generated method stub
		return wxActBargainDecorateRecordMapper.selectWxActBargainDecorateRecordByRegId(regId);
	}
}
