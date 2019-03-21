package com.sandu.service.house.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.input.BaseHouseQuery;
import com.sandu.api.house.service.BaseHouseService;
import com.sandu.api.house.service.BaseLivingService;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.util.Utils;

/**
 * Description: 户型业务实现类
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */

@Service
public class BaseHouseServiceImpl implements BaseHouseService {
	
	@Autowired
	private BaseHouseMapper baseHouseMapper;
	
	@Autowired
	private BaseLivingService baseLivingService;
	
	@Override
	@Deprecated
	public Map<String, Object> queryBaseHouse(BaseHouseQuery query) {
		
		Map<String, Object> map = new HashMap<>();
		List<DrawBaseHouseBO> list = new ArrayList<>();
		
		// TODO 权限处理
		if ("1".length() == 1) {
			// 户型绘制员
			query.setCheckArgs(new Integer[] {1, 2});
		} else {
			// 普通（无绘制角色）用户
			query.setCheckArgs(new Integer[] {1});
		}
		
		long total = baseHouseMapper.countBaseHouse(query);
		if (total > 0) {
			list = baseHouseMapper.queryBaseHouse(query);
			if (list != null && !list.isEmpty()) {
				list.forEach(house -> {
					if (StringUtils.isNotBlank(house.getAreaLongCode())) {
						String[] split = house.getAreaLongCode().substring(1, house.getAreaLongCode().length() - 1).split("\\.");
						house.setDetailAddress(baseLivingService.getDetailAddress(split) + house.getLivingName());
					}
					
					// 户型名=小区名+户型名
					house.setHouseName(Utils.getConverterValue(house.getLivingName()) + Utils.getConverterValue(house.getHouseName()));
				});
			}
		}
		
		map.put("total", total);
		map.put("list", list);
		
		return map;
	}
}
