package com.nork.pano.service;

import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.output.DesignPlanStoreReleaseVo;
import com.nork.pano.model.roam.Roam;

import java.util.List;

public interface DesignPlanStoreReleaseService {


    /**
     * 获取720分享
     * @param search
     * @return
     */
    DesignPlanStoreReleaseVo getPanorama(SceneDataSearch search, LoginUser loginUser) throws BizException;

    /**
     * 更新浏览量
     * @param releaseId
     * @return
     */
    int updatePv(Integer releaseId);

    /**
     * 根据渲染图Id获取漫游场景坐标信息
     * @param screenShotId
     * @return
     */
    List<Roam> getWalkCoordinate(Integer screenShotId);
}
