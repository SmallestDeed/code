package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.MiniProgramDashboard;
import org.springframework.stereotype.Repository;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/18
 * @since : sandu_yun_1.0
 */
@Repository
public interface MiniProgramDashboardMapper {

    int createMiniProgramDashboard(MiniProgramDashboard dashboard);

    int updateMiniProgramDashboard(MiniProgramDashboard dashboard);

    MiniProgramDashboard getMiniProgramDashboardByAppId(String appId);
}
