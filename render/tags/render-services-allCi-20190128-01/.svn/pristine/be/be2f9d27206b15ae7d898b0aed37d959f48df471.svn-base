package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.model.HouseSpaceResult;
import com.nork.home.service.BaseHouseService;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.service.MobileHouseSearchService;
import org.apache.log4j.Logger;

@Service("mobileHouseSearchService")
public class MobileHouseSearchServiceImpl implements MobileHouseSearchService {
	private static Logger logger = Logger
			.getLogger(MobileHouseSearchServiceImpl.class);
	
	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private SpaceCommonService spaceCommonService;

	/**
	 * 根据省市区搜索户型list
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param livingName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	@Override
	public Object newHouseSearchWeb(String provinceCode, String cityCode,
			String livingName, String msgId, String limit, String start,
			LoginUser loginUser) {
		// 调用APP端 原有的serviceImpl里的方法
		return baseHouseService.newHouseSearchWeb(provinceCode, cityCode,
				livingName, msgId, limit, start, loginUser);
	}

	/**
	 * 点击小区名字搜索户型
	 * 
	 * @param style
	 * @param livingId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param request
	 * @return
	 */
	@Override
	public Object newHouseList(String style, String livingId, String msgId,
			String limit, String start, LoginUser loginUser) {
		// 调用APP端 原有的serviceImpl里的方法
		return baseHouseService.newHouseList(style, livingId, msgId, limit,
				start, loginUser);
	}

	/**
	 * 通过户型过滤空间布局图
	 * 
	 * @param houseId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	@Override
	public Object newHouseSpaceList(String houseId, String msgId, String limit,
			String start, LoginUser loginUser,String spaceFunctionId) {
		// 调用APP端 原有的serviceImpl里的方法
//		return spaceCommonService.newHouseSpaceList(houseId, msgId, limit,
//				start, loginUser);
		ResponseEnvelope<HouseSpaceResult> res = (ResponseEnvelope<HouseSpaceResult>) 
				spaceCommonService.newHouseSpaceList(houseId, msgId, limit, start, loginUser);
		List<HouseSpaceResult> list = res.getDatalist();
		if(spaceFunctionId != null && spaceFunctionId != "") {
			List<HouseSpaceResult> list2 = new ArrayList<>();
			if(list != null && list.size() > 0) {
				for(HouseSpaceResult hsr: list) {
//					//System.out.println("-------------hsr"+hsr);
					if(spaceFunctionId != null && hsr.getSpaceFunctionId() != 0) {
						if(Integer.valueOf(spaceFunctionId) == hsr.getSpaceFunctionId()) {
							list2.add(hsr);
						}
					}else {
						return res;
					}
				}
			}
			res.setTotalCount(list2.size());
			res.setDatalist(list2);
		}
		return res;
		
		
	}

	@Override
	public Object getSpaceNameInHouse(String houseId, String msgId, String limit, 
			String start, LoginUser loginUser) {
		
		ResponseEnvelope<HouseSpaceResult> res = (ResponseEnvelope<HouseSpaceResult>) 
				spaceCommonService.newHouseSpaceList(houseId, msgId, limit, start, loginUser);
		List<HouseSpaceResult> list = res.getDatalist();
		List list2 = new ArrayList<>();
		if(list != null && list.size() > 0) {
			for(HouseSpaceResult hsr: list) {
				if(list2.size()>0) {
					if(!list2.contains(hsr.getSpaceFunctionId())) {
						list2.add(hsr.getSpaceFunctionId());
					}
				}else{
					list2.add(hsr.getSpaceFunctionId());
				}
			}
		}
		res.setTotalCount(list2.size());
		res.setDatalist(list2);
		return res;
	}

}
