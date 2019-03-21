package com.sandu.design.service;

import com.sandu.design.model.DesignRenderRoam;

public interface DesignRenderRoamService {

    /**
     * 通过封面图获取
     * @return
     */
    DesignRenderRoam selectByScreenShotId(Integer screenShotId);

}
