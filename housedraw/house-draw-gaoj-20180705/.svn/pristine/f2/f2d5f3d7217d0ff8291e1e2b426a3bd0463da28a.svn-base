package com.sandu.service.living.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.house.bo.BaseAreaBO;
import com.sandu.api.house.bo.LivingCodeBO;
import com.sandu.api.house.model.BaseLiving;
import com.sandu.api.house.service.BaseLivingService;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.service.living.dao.BaseLivingMapper;

/**
 * Description: 小区地理区域逻辑类
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Service
public class BaseLivingServiceImpl implements BaseLivingService {

	@Autowired
	private BaseLivingMapper baseLivingMapper;

	@Override
	public List<BaseAreaBO> listBaseLiving() {
		// 所有的地理区域
		List<BaseAreaBO> areaBos = baseLivingMapper.listAreas();

		// 地理区域按等级分组
		Map<Integer, List<BaseAreaBO>> areaMap = getAreaMap(areaBos);
		// 所有的小区 以地理区域分组
		//Map<String, List<BaseLiving>> livingMap = getLivingMap();

		List<BaseAreaBO> first = areaMap.get(DrawBaseHouseConstant.AREA_FIRST);
		for (BaseAreaBO baseArea : first) {
			baseArea.setChildAreas(getChild(baseArea.getAreaCode(), areaBos));
		}

		return first;
	}

	@Override
	public BaseLiving getLiving(Long livingId) {
		return baseLivingMapper.get(livingId);
	}

	@Override
	public BaseLiving getLivingByName(String areaId, String livingName) {
		if (!StringUtils.isNoneBlank(areaId)) {
			return null;
		}
		return baseLivingMapper.getLivingByName(areaId, livingName);
	}

	@Override
	public List<BaseLiving> listLivings(String areaCode,String livingName) {
		return baseLivingMapper.listLiving(areaCode,livingName);
	}
	
	@Override
	public List<BaseLiving> getLivingForSave(String areaCode, String livingName) {
		return baseLivingMapper.getLivingForSave(areaCode, livingName);
	}

	private Map<Integer, List<BaseAreaBO>> getAreaMap(List<BaseAreaBO> areaBos) {
		Map<Integer, List<BaseAreaBO>> areaMap = new HashMap<>(areaBos.size());
		for (BaseAreaBO area : areaBos) {
			Integer key = area.getLevelId();
			if (areaMap.containsKey(key)) {
				areaMap.get(key).add(area);
			} else {
				List<BaseAreaBO> BaseAreaBOs = new ArrayList<>();
				BaseAreaBOs.add(area);
				areaMap.put(key, BaseAreaBOs);
			}
		}
		return areaMap;
	}

	private static List<BaseAreaBO> getChild(String areaCode, List<BaseAreaBO> rootAreas) {

		List<BaseAreaBO> childList = new ArrayList<>();
		// 循环一级设置2级
		for (BaseAreaBO baseArea : rootAreas) {
			if (StringUtils.isNotBlank(baseArea.getPid())) {
				if (baseArea.getPid().equals(areaCode)) {
					childList.add(baseArea);
				}
			}
		}
		// 循环2级设置3级
		for (BaseAreaBO baseArea : childList) {
			List<BaseAreaBO> childs = new ArrayList<>();
			for (BaseAreaBO baseAreaBO : rootAreas) {
				if (StringUtils.isNoneBlank(baseArea.getAreaCode())) {
					if (baseArea.getAreaCode().equals(baseAreaBO.getPid())) {
						childs.add(baseAreaBO);
					}
				}
			}
			baseArea.setChildAreas(childs);
		}

		if (childList.size() == 0) {
			return null;
		}
		return childList;
	  }



	@Override
	public String getDetailAddress(String...args) {
		StringBuilder value = new StringBuilder();
		if (args != null && args.length > 0) {
			List<String> params = Arrays.asList(args);
			List<BaseAreaBO> areaForMap = baseLivingMapper.getDetailAddress(params.get(0), params.get(1), params.get(2));
			if (areaForMap != null && !areaForMap.isEmpty()) {
				for (BaseAreaBO bo : areaForMap) {
					value.append(Objects.toString(bo.getAreaName(), ""));
				}
			}
		}
		
		return value.toString();
	}
	
	@Override
	public int insertSelective(BaseLiving living) {
		return baseLivingMapper.insertSelective(living);
	}

	@Override
	public int updateByPrimaryKeySelective(BaseLiving living) {
		return baseLivingMapper.updateByPrimaryKeySelective(living);
	}

	@Override
	public LivingCodeBO getLivingCode(String areaId) {
		return baseLivingMapper.getLivingCode(areaId);
	}
}
