package com.sandu.api.solution.service;


import com.sandu.api.solution.model.MiniProgramDashboard;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/18
 * @since : sandu_yun_1.0
 */
public interface MiniProgramDashboardService {

    Long createMiniProgramDashboard(MiniProgramDashboard dashboard);

    int updateMiniProgramDashboard(MiniProgramDashboard dashboard);

    MiniProgramDashboard getMiniProgramDashboardByAppId(String appId);


}
