package com.sandu.service.pic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.res.model.ResPic;
import com.sandu.api.res.service.ResPicService;
import com.sandu.service.pic.dao.ResPicMapper;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年3月15日
 */

@Service
public class ResPicServiceImpl implements ResPicService {

	@Autowired
	private ResPicMapper resPicMapper;

	@Override
	public Map<String, ResPic> getResPicMap(Set<Long> args) {
		final Map<String, ResPic> map = new HashMap<>();
		if (args == null || args.isEmpty()) {
			return map;
		}

		List<ResPic> pics = resPicMapper.listResPicById(args);
		if (pics != null && !pics.isEmpty()) {
			pics.forEach(pic -> map.put(pic.getId().toString(), pic));
		}

		return map;
	}
}
