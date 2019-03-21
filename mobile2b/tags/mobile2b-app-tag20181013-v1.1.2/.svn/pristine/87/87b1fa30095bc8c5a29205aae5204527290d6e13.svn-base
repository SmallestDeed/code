package com.nork.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nork.home.model.SpaceCommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.home.model.HouseSpaceResult;
import com.nork.home.service.BaseHouseService;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.service.MobileHouseSearchService;
import com.nork.system.model.BaseArea;
import com.nork.system.service.BaseAreaService;

@Service("mobileHouseSearchService")
public class MobileHouseSearchServiceImpl implements MobileHouseSearchService {

	@Autowired
	private BaseHouseService baseHouseService;
	@Autowired
	private SpaceCommonService spaceCommonService;
	@Autowired
	private BaseAreaService baseAreaService;

	/**
	 * 查询所有省份和市区编码
	 */
	@Override
	public Object searchProvinceCodeAndCityCode(String pid) {
		BaseArea baseArea = new BaseArea();
		baseArea.setIsDeleted(0);
		if(pid != null && pid != "") {
			baseArea.setPid(pid);//根据省级的编码查询市区
		}else {
			baseArea.setLevelId(new Integer(1));//查询所有省级
		}
		List<BaseArea> list = baseAreaService.getList(baseArea);

		return new ResponseEnvelope<BaseArea>(list.size(),list);
	}

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
		List<HouseSpaceResult> list2 = new ArrayList<>();
		if (spaceFunctionId == null && spaceFunctionId == "" ||
				SpaceCommonConstant.FULL_HOUSE_SPACE_FUNCTION_ID.equals(spaceFunctionId)) {
			//如果是全屋空间或没有传入空间类型id，返回全屋信息
			HouseSpaceResult fullHouseSpace = spaceCommonService.getFullHouseSpace(houseId);
			list2.add(fullHouseSpace);
		} else {
			//不是全屋空间类型查对应的空间信息
			//如果有户型绘制的新空间图就显示新的,如果没有就显示旧的.
			List<HouseSpaceResult> spaceList = list.stream().filter((HouseSpaceResult h) -> h.getSpaceFunctionId().intValue() == Integer.parseInt(spaceFunctionId)).collect(Collectors.toList());
			if(spaceList == null || spaceList.size() ==0 ){
				res.setTotalCount(0);
				res.setDatalist(null);
				res.setMessage("未查询到空间数据");
				return res;
			}
			List<HouseSpaceResult> newSpaceList = spaceList.stream().filter((HouseSpaceResult h) -> h.getOrigin() == 1).collect(Collectors.toList());
			if(newSpaceList != null && newSpaceList.size() > 0 ){
				res.setTotalCount(newSpaceList.size());
				res.setDatalist(newSpaceList);
				return res;
			}else {
				res.setTotalCount(spaceList.size());
				res.setDatalist(spaceList);
				return res;
			}
		}
		res.setTotalCount(list2.size());
		res.setDatalist(list2);

		return res;


	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getSpaceNameInHouse(String houseId, String msgId, String limit,
									  String start, LoginUser loginUser) {

		ResponseEnvelope<HouseSpaceResult> res = (ResponseEnvelope<HouseSpaceResult>)
				spaceCommonService.newHouseSpaceList(houseId, msgId, limit, start, loginUser);
		List<HouseSpaceResult> list = res.getDatalist();
		List list2 = new ArrayList<>();
		HouseSpaceResult fullHouseSpace = spaceCommonService.getFullHouseSpace(houseId);
		list2.add(fullHouseSpace.getSpaceFunctionId());
		if(list != null && list.size() > 0) {
			for(HouseSpaceResult hsr: list) {
				if(hsr.getSpaceFunctionId() == 9 || hsr.getSpaceFunctionId() == 10
						|| hsr.getSpaceFunctionId() == 11 || hsr.getSpaceFunctionId() == 12){
					continue;
				}

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
