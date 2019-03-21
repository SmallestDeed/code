package com.sandu.service.company.impl;

import com.sandu.api.company.service.SysUserLevelConfigService;
import com.sandu.service.company.dao.SysUserLevelConfigMapper;
import com.sandu.user.model.SysUserLevelBo;
import com.sandu.user.model.SysUserLevelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yanghz on 2017-08-15.
 */
@Service
public class SysUserLevelConfigServiceImpl implements SysUserLevelConfigService {
	@Autowired
	private SysUserLevelConfigMapper sysUserLevelConfigMapper;

	@Override
	public SysUserLevelBo getLevelInfo(SysUserLevelConfig levelConfig) {
		if (levelConfig == null || levelConfig.getUserId() < 0) {
			return null;
		}
		return sysUserLevelConfigMapper.getLevelInfo(levelConfig);
	}

	@Override
	public void updateById(int id, int levelId) {
		if (id < 1) {
			return;
		}
		sysUserLevelConfigMapper.updateById(id, levelId);
	}

	@Override
	public void initUserLevelByLevelPriceId(long userId, int id) {
		sysUserLevelConfigMapper.initUserLevelByLevelPriceId((int) userId, id);
	}
}
