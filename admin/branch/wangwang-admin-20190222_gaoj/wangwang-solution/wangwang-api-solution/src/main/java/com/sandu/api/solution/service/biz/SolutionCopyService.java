package com.sandu.api.solution.service.biz;

import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/5/24 11:05
 */
public interface SolutionCopyService {

    /**
     * 克隆方案
     * @param originSolutionId 原方案Id
     * @return int 新方案ID
     */
    int clone(int originSolutionId, int targetCompanyId, Integer targetBrandId);

    /**
     * 复制方案信息
     * @param originSolutionId
     * @return
     */
    int copyInfo(int originSolutionId, int targetCompanyId, Integer targetBrandId);

    /**
     * 复制方案渲染图片资源
     * @param originSolutionId
     */
    Map<String, Integer> copyRenderPic(int originSolutionId, int targetSolutionId);

    /**
     * 复制方案渲染视频资源
      * @param originSolutionId
     */
    void copyRenderVideo(int originSolutionId, int targetSolutionId, int videoPictureId);

    /**
     * 复制方案渲染漫游资源
     * @param oldScreenShotId
     */
    int copyRenderRoam(int oldScreenShotId, int newScreenShotId);

    /**
     * 复制漫游配置
     * @param originRoamConfigId
     * @return
     */
    int copyRenderRoamConfig(int originRoamConfigId);

    /**
     * 修得有bug的复制方案
     */
    void fixedBugSolution();

    void fixedOne(int originSolutionId, int targetSolutionId);

    /**
     * 修复交付方案未打组bug
     */
    void fixedDeliverPlan();
}
