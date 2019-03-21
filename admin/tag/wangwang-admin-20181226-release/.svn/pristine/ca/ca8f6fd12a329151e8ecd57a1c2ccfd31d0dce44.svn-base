package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.MiniProgramDashboard;
import com.sandu.api.solution.service.MiniProgramDashboardService;
import com.sandu.service.solution.dao.MiniProgramDashboardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/18
 * @since : sandu_yun_1.0
 */
@Slf4j
@Service("miniProgramDashboardService")
public class MiniProgramDashboardServiceImpl implements MiniProgramDashboardService{

    @Autowired
    private MiniProgramDashboardMapper miniProgramDashboardMapper;

    @Override
    public Long createMiniProgramDashboard(MiniProgramDashboard dashboard) {
        miniProgramDashboardMapper.createMiniProgramDashboard(dashboard);
        return dashboard.getId();
    }

    @Override
    public int updateMiniProgramDashboard(MiniProgramDashboard dashboard) {
        return miniProgramDashboardMapper.updateMiniProgramDashboard(dashboard);
    }

    @Override
    public MiniProgramDashboard getMiniProgramDashboardByAppId(String appId) {
        return miniProgramDashboardMapper.getMiniProgramDashboardByAppId(appId);
    }
}
