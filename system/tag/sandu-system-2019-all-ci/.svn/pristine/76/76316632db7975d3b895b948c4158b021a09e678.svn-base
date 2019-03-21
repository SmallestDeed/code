package com.sandu.service.act3.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.model.LuckyWheelAwardMsg;
import com.sandu.api.act3.service.LuckyWheelAwardMsgService;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.act3.dao.LuckyWheelAwardMsgMapper;
import com.sandu.util.UUIDUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("luckyWheelAwardMsgService")
public class LuckyWheelAwardMsgServiceImpl implements LuckyWheelAwardMsgService{
	
	@Autowired
    private LuckyWheelAwardMsgMapper luckyWheelAwardMsgMapper;
	
	@Override
	public void create(LuckyWheelAwardMsg entity) {
		luckyWheelAwardMsgMapper.insert(entity);
	}

	@Override
	public int modifyById(LuckyWheelAwardMsg entity) {
		return luckyWheelAwardMsgMapper.updateById(entity);
	}

	@Override
	public LuckyWheelAwardMsg get(String id) {
		return luckyWheelAwardMsgMapper.selectById(id);
	}

	@Override
	public List<LuckyWheelAwardMsg> list(LuckyWheelAwardMsg entity) {
		return luckyWheelAwardMsgMapper.selectList(entity);
	}
	
	@Override
	public LuckyWheelAwardMsg buildLuckyWheelAwardMsg(String actId, String regId,
			String prizeName, SysUser user) {
		Date now = new Date();
		LuckyWheelAwardMsg entity = new LuckyWheelAwardMsg();
		entity.setId(UUIDUtil.getUUID());
		entity.setActId(actId);
		entity.setRegId(regId);
		entity.setOpenId(user.getOpenId());
		entity.setHeadPic(user.getHeadPic());
		StringBuilder sb = new StringBuilder(user.getMobile());
		entity.setMessage("恭喜用户"+ sb.replace(3, 7, "****") +"抽中了"+ prizeName);
		
		entity.setAppId(user.getAppId());
		entity.setCompanyId(user.getMiniProgramCompanyId());
		entity.setCreator(user.getId().toString());
		entity.setGmtCreate(now);
		entity.setModifier(user.getId().toString());
		entity.setGmtModified(now);
		entity.setIsDeleted(0);
		return entity;
	}
	
	@Override
	public PageInfo<LuckyWheelAwardMsg> pageList(String actId,Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		LuckyWheelAwardMsg queryEntity = new LuckyWheelAwardMsg();
		queryEntity.setActId(actId);
		List<LuckyWheelAwardMsg> list = this.list(queryEntity);
		return new PageInfo<LuckyWheelAwardMsg>(list);
	}

	@Override
	public List<LuckyWheelAwardMsg> listNewest15(String actId) {
		PageInfo<LuckyWheelAwardMsg> list = this.pageList(actId, 1, 15);
		return list.getList();
	}

}
