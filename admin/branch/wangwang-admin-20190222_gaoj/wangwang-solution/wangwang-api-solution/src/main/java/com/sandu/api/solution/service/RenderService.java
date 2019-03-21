package com.sandu.api.solution.service;

import com.sandu.api.solution.model.DesignRenderRoam;
import com.sandu.api.solution.model.RenderRoamConfig;
import com.sandu.api.solution.model.ResRenderPic;
import com.sandu.api.solution.model.ResRenderVideo;

import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 2018/5/24 18:00
 */
public interface RenderService {

    /**
     * 通过方案ID查询渲染图片列表
     *  需按图片Id 升序 排序
     * @param solutionId 方案ID
     * @return 渲染图片列表
     */
    List<ResRenderPic> queryPictureBySolutionId(int solutionId);

    /**
     * 保存渲染图片
     * @param res
     * @return
     */
    long insertPicture(ResRenderPic res);

    int updatePicture(ResRenderPic renderPic);

    /**
     * 通过方案Id查询渲染视频列表
     * @param solutionId 方案ID
     * @return
     */
    List<ResRenderVideo> queryVideoBySolutionId(int solutionId);

    /**
     * 保存渲染视频
     * @param renderVideo
     * @return
     */
    int insertVideo(ResRenderVideo renderVideo);

    /**
     * 通过渲染视频截图ID获取漫游记录
     * @return
     */
    DesignRenderRoam getRoamByScreenShotId(int screenShotId);

    /**
     * 保存漫游记录
     * @param renderRoam
     * @return
     */
    int insertRoam(DesignRenderRoam renderRoam);

    /**
     * 获取漫游配置
     * @param roamConfigId
     * @return
     */
    RenderRoamConfig getRoamConfigById(int roamConfigId);

    /**
     * 保存漫游配置
     * @param config
     * @return
     */
    int insertRoamConfig(RenderRoamConfig config);

    /**
     * 查询以前复制方案产生的bug方案列表
     * @param actionRemark 识别标注
     * @param limit 每次修复的数量
     * @return bug方案列表
     */
    List<Map<String,Object>> queryBugSolutionDueToCopy(String actionRemark, int limit);

    /**
     * 清除bug方案的有误资源列表
     * @param targetSolutionId
     */
    int clearErrorPictures(int targetSolutionId);
}
