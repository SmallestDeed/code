package com.sandu.service.company.impl;

import com.sandu.api.company.service.SysUserLevelPriceService;
import com.sandu.service.company.dao.SysUserLevelPriceMapper;
import com.sandu.user.model.SysUserLevelPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yanghz on 2017-08-15.
 */
@Service
public class SysUserLevelPriceServiceImpl implements SysUserLevelPriceService {
	@Autowired
	private SysUserLevelPriceMapper sysUserLevelPriceMapper;

	@Override
	public List<SysUserLevelPrice> getListByUserId(Integer userId) {
		if (userId == null || userId < 1) {
			return null;
		}
		return sysUserLevelPriceMapper.getListByUserId(userId);
	}

	@Override
	public SysUserLevelPrice getIdByUserType(int userType) {
		return sysUserLevelPriceMapper.getIdByUserType(userType);
	}
}
