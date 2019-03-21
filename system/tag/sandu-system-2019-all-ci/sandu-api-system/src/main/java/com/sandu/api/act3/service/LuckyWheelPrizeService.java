package com.sandu.api.act3.service;

import java.util.List;

import com.sandu.api.act3.model.LuckyWheelPrize;
import com.sandu.api.act3.output.LuckyWheelPrizeVO;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

@Component
public interface LuckyWheelPrizeService {

	void create(LuckyWheelPrize entity);
	
	int modifyById(LuckyWheelPrize entity);
	 
	LuckyWheelPrize get(String id);	
	
	List<LuckyWheelPrize> list(LuckyWheelPrize entity);

	List<LuckyWheelPrize> getByActId(String actId);

	List<LuckyWheelPrizeVO> getPrizeList(String actId, SysUser user);

	
	/**
	 * 扣减奖品数量
	 * @param id
	 * @return
	 */
	int reducePrizeNum(String id);

	
	/**
	 * 重新初始化每天奖品数量
	 */
	void reInitPrizeNum();
	
}
