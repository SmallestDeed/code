package com.sandu.api.act3.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.act3.model.LuckyWheelAwardMsg;
import com.sandu.api.user.model.SysUser;

public interface LuckyWheelAwardMsgService {

	void create(LuckyWheelAwardMsg entity);
	
	int modifyById(LuckyWheelAwardMsg entity);
	 
	LuckyWheelAwardMsg get(String id);	
	
	List<LuckyWheelAwardMsg> list(LuckyWheelAwardMsg entity);

	LuckyWheelAwardMsg buildLuckyWheelAwardMsg(String actId, String regId, String prizeName, SysUser user);

	List<LuckyWheelAwardMsg> listNewest15(String actId);

	PageInfo<LuckyWheelAwardMsg> pageList(String actId, Integer pageNum, Integer pageSize);

}
