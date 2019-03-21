package com.sandu.service.space.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.house.model.HouseSpaceNew;
import com.sandu.api.house.service.HouseSpaceNewService;
import com.sandu.service.space.dao.HouseSpaceNewMapper;
import com.sandu.util.Utils;

@Service("houseSpaceNewService")
public class HouseSpaceNewServiceImpl implements HouseSpaceNewService{

	@Autowired
	private HouseSpaceNewMapper houseSpaceNewMapper;
	
	@Override
	public void add(HouseSpaceNew houseSpaceNew) {
		// 参数验证 ->start
		if(houseSpaceNew == null){
			return;
		}
		// 参数验证 ->end
		
		houseSpaceNewMapper.insertSelective(houseSpaceNew);
	}

	@Override
	public HouseSpaceNew getHouseSpaceNewBySpaceCommonIdAndBaseHouseId(Long spaceCommonId, Long baseHouseId) {
		// 参数验证 ->start
		if (spaceCommonId == null) {
			return null;
		}
		if (baseHouseId == null) {
			return null;
		}
		// 参数验证 ->end

		String randomNumStr = Utils.generateRandomDigitString(6);
		String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
		Date now = new Date();

		HouseSpaceNew houseSpaceNew = new HouseSpaceNew();
		houseSpaceNew.setSpaceId(Integer.valueOf(spaceCommonId + ""));
		houseSpaceNew.setStandardSpaceId(Integer.valueOf(spaceCommonId + ""));
		houseSpaceNew.setHouseId(Integer.valueOf(baseHouseId + ""));
		houseSpaceNew.setSysCode(sysCode);
		houseSpaceNew.setCreator("unknown");
		houseSpaceNew.setGmtCreate(now);
		houseSpaceNew.setModifier("unknown");
		houseSpaceNew.setGmtModified(now);
		houseSpaceNew.setIsDeleted(0);

		return houseSpaceNew;
	}

}
