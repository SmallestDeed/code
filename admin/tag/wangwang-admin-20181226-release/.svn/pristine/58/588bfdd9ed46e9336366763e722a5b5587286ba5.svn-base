package com.sandu.service.solution.impl.biz;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.sandu.api.solution.model.*;
import com.sandu.api.solution.service.DesignPlanRecommendedService;
import com.sandu.api.solution.service.RenderService;
import com.sandu.api.solution.service.biz.SolutionCopyService;
import com.sandu.constant.PlatformEnum;
import com.sandu.util.Randomer;
import com.sandu.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.sandu.constant.Constants.STATUS_NOT_DELETE;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 * +
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 2018/5/24 11:10
 */
@Slf4j
@Service("solutionCopyService")
public class SolutionCopyServiceImpl implements SolutionCopyService {

    private static int fixedCopySolutionBugCounter = 1;

    private final String actionRemark = "fix-copy-bug";

    @Autowired
    private RenderService renderService;
    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;

    /**
     * 根路径
     */
    @Value("${file.storage.path}")
    private String rootPath;

    @Override
    public int clone(int originSolutionId, int targetCompanyId, Integer targetBrandId) {
        /**
         * 1、复制方案信息
         */
        int newSolutionId = this.copyInfo(originSolutionId, targetCompanyId, targetBrandId);
        /**
         * 2、复制方案渲染的图片资源
         */
        this.executeCopy(originSolutionId, newSolutionId, "copy");

        return 0;
    }

    @Override
    public int copyInfo(int originSolutionId, int targetCompanyId, Integer targetBrandId) {
        return 0;
    }

    @Override
    public Map<String, Integer> copyRenderPic(int originSolutionId, int targetSolutionId) {
        Map<String, Integer> resultMap = Maps.newConcurrentMap();

        //查询来源方案的渲染图片列表
        List<ResRenderPic> results = renderService.queryPictureBySolutionId(originSolutionId);
        if (results == null || results.size() == 0) {
            return resultMap;
        }

        log.debug("0-NeedCopyPicture Num: {}", results.size());
        results.forEach(res -> {
            //复制图片
            Map<String, String> fileMap = this.copyFile(res.getPicPath(), "designPlanRecommended", res.getPicSuffix());
            log.debug("FileMap: {}", fileMap);
            if (!fileMap.isEmpty()) {
                //保存旧渲染图片ID
                final int oldRenderPicId = Long.valueOf(res.getId()).intValue();
                //修改资源信息
                res.setId(null);
                res.setBusinessId(null);
                res.setDesignSceneId(0L);
                res.setPicName(fileMap.get("name"));
                res.setPicCode(TimeUtil.currentTimeToString() + Randomer.randomNum(4));
                res.setPicPath(fileMap.get("path"));
                Date now = new Date();
                res.setGmtCreate(now);
                res.setGmtModified(now);
                res.setPlanRecommendedId(targetSolutionId);
                res.setPlatformId(PlatformEnum.MERCHANT_MANAGE.getCode());
                res.setIsDeleted(STATUS_NOT_DELETE);

                //todo 更新当前用户ID
//            res.setCreateUserId(userId);

                //插入渲染图片记录
                final int pictureId = Long.valueOf(renderService.insertPicture(res)).intValue();
                if (pictureId > 0) {
                    log.debug("pictureType={}, fileKey={}, pictureId={}", res.getPicType(), res.getFileKey(), pictureId);

                    //缩略图的父ID
                    int thumbParentId = 0;

                    //获取照片级原图Id
                    if ("照片级原图".equals(res.getPicType())) {
                        resultMap.put("originPhotoId", pictureId);
                    }
                    //设置照片级缩略图的原图ID = 照片级原图Id
                    if ("照片级缩略图".equals(res.getPicType())) {
                        thumbParentId = resultMap.get("originPhotoId");
                    }
                    //获取单点3DMax渲染原图Id， RenderingType=4 是普通720
                    if ("3DMax渲染原图".equals(res.getPicType()) && res.getRenderingType() == 4) {
                        resultMap.put("origin3DMaxId", pictureId);
                    }
                    //设置单点3DMax渲染图-缩略图的原图ID = 3DMax渲染图原图ID
                    if ("3DMax渲染图-缩略图".equals(res.getPicType()) && res.getRenderingType() == 4) {
                        thumbParentId = resultMap.get("origin3DMaxId");
                    }
                    //获取渲染截图ID
                    if ("渲染截图".equals(res.getPicType())) {
                        resultMap.put("screenShotId", pictureId);
                        //保存旧渲染截图ID
                        resultMap.put("oldScreenShotId", oldRenderPicId);
                    }
                    //设置多点3DMax渲染图-缩略图的原图ID =  渲染截图Id， RenderingType=8 是多点720
                    if ("3DMax渲染图-缩略图".equals(res.getPicType()) && res.getRenderingType() == 8) {
                        thumbParentId = resultMap.get("screenShotId");
                    }
                    //获取视频截屏图片ID - design.designPlanRecommended.render.video.cover
                    if ("720渲染视频封面".equals(res.getPicType())) {
                        resultMap.put("videoCoverId", pictureId);
                    }

                    //更新缩略图的父ID
                    if (thumbParentId > 0) {
                        ResRenderPic renderPic = new ResRenderPic();
                        renderPic.setId(Long.valueOf(pictureId));
                        renderPic.setPid(thumbParentId);
                        renderService.updatePicture(renderPic);
                    }
                }
            }
        });
        return resultMap;
    }

    @Override
    public void copyRenderVideo(int originSolutionId, int targetSolutionId, int videoPictureId) {
        //查询原方案渲染视频列表
        List<ResRenderVideo> results = renderService.queryVideoBySolutionId(originSolutionId);
        if (results != null && results.size() > 0) {
            results.forEach(res -> {
                //复制视频
                Map<String, String> fileMap = this.copyFile(res.getVideoPath(), "designPlanRecommended", res.getVideoSuffix());
                log.debug("File Map: {}", fileMap);
                if (!fileMap.isEmpty()) {
                    //修改资源信息
                    res.setId(null);
                    res.setBusinessId(targetSolutionId);
                    res.setVideoCode(TimeUtil.currentTimeToString() + Randomer.randomNum(4));
                    res.setVideoName(fileMap.get("name"));
                    res.setVideoPath(fileMap.get("path"));
                    Date now = new Date();
                    res.setGmtCreate(now);
                    res.setGmtModified(now);
                    res.setSysTaskPicId(videoPictureId);
                    res.setSysCode(TimeUtil.currentTimeToString() + Randomer.randomNum(4));
                    res.setPlatformId(PlatformEnum.MERCHANT_MANAGE.getCode());
                    res.setIsDeleted(STATUS_NOT_DELETE);

                    //todo 更新当前用户ID     res.setCreateUserId(userId);

                    int videoId = renderService.insertVideo(res);
                    log.debug("fileKey={}, videoId={}", res.getFileKey(), videoId);
                }
            });
        }
    }

    @Override
    public int copyRenderRoam(int oldScreenShotId, int newScreenShotId) {
        //获取原方案漫游
        DesignRenderRoam result = renderService.getRoamByScreenShotId(oldScreenShotId);
        if (result != null) {
            result.setId(null);
            result.setScreenShotId(newScreenShotId);
            String uuid = UUID.randomUUID().toString();
            result.setUuid(uuid.replace("-", ""));

            //复制roam 配置
            int roamConfigId = this.copyRenderRoamConfig(result.getRoamConfig());
            result.setRoamConfig(roamConfigId);
            Date now = new Date();
            result.setGmtCreate(now);
            result.setGmtModified(now);
            result.setIsDeleted(STATUS_NOT_DELETE);

            log.debug("New Render Roam: screenShotId= {}, uuid={}, roamConfigId={}",
                    result.getScreenShotId(), result.getUuid(), result.getRoamConfig());

            return renderService.insertRoam(result);
        }
        return 0;
    }

    @Override
    public int copyRenderRoamConfig(int originRoamConfigId) {
        //获取原方案漫游配置
        RenderRoamConfig config = renderService.getRoamConfigById(originRoamConfigId);
        if (config != null) {
            //复制漫游配置
            Map<String, String> fileMap = this.copyFile(config.getFilePath(), "roam/config", config.getFileSuffix());
            if (!fileMap.isEmpty()) {
                //更新部分信息
                config.setId(null);
                config.setFileCode(TimeUtil.currentTimeToString() + Randomer.randomNum(2));
                config.setFileName(fileMap.get("name"));
                config.setFilePath(fileMap.get("path"));
                config.setSysCode(TimeUtil.currentTimeToString() + Randomer.randomNum(4));
                Date now = new Date();
                config.setGmtCreate(now);
                config.setGmtModified(now);
                config.setIsDeleted(STATUS_NOT_DELETE);

                return renderService.insertRoamConfig(config);
            }
        }

        return 0;
    }

    /**
     * 复制资源
     *
     * @param originPath 源文件路径
     * @param module     目标模块名
     * @param suffix     源文件后缀
     * @return Map 新文件名 name 及路径 path
     */
    protected Map<String, String> copyFile(String originPath, String module, String suffix) {
        log.debug("1-Copy file: path={}, suffix={}", originPath, suffix);
        Map<String, String> resultMap = Maps.newHashMap();

        //生成目标文件相对路径
        StringBuilder targetPath = new StringBuilder()
                .append("/AA/c_basedesign_recommended/")
                .append(TimeUtil.formatToString(LocalDateTime.now(), "yyyy/MM/dd"))
                .append("/")
                .append(module)
                .append("/");

        String fileName = TimeUtil.currentTimeToString() + Randomer.randomLetter(3);
        targetPath.append(fileName).append(suffix);
        //目标
        File to = new File(this.rootPath + targetPath.toString());

        //来源
        File from = new File(this.rootPath + originPath);
        try {
            log.debug("Copy File: from={}, to={}", from.getPath(), to.getPath());
            //检查目标目录是否存在
            File dirPath = new File(to.getParent());
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }

            resultMap.put("name", fileName);
            resultMap.put("path", targetPath.toString());

            Files.copy(from, to);
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }

        return resultMap;
    }

    @Override
    public void fixedBugSolution() {
        log.info("*********** Start running fixed copy solution bug, This is {} time, at {}. ****",
                fixedCopySolutionBugCounter, TimeUtil.formatToString(LocalDateTime.now(), "yyyy-MM-dd H:mm:ss.SSS"));

        /**
         * 1、先查询存在bug的方案列表
         */
        List<Map<String, Object>> bugs = renderService.queryBugSolutionDueToCopy(this.actionRemark, 200);
        if (bugs != null && bugs.size() > 0) {
            /**
             * 2、修复未复制渲染资源bug
             */
            bugs.parallelStream().forEach(map -> {
                int originSolutionId = ((Number) map.get("originSolutionId")).intValue();
                int targetSolutionId = ((Number) map.get("targetSolutionId")).intValue();
                log.info("OriginSolutionId={}, targetSolutionId={}", originSolutionId, targetSolutionId);
                /**
                 * 2.1 先清除以前复制 res_render_pic与res_render_video 中记录
                 */
                renderService.clearErrorPictures(targetSolutionId);

                /**
                 * 2.2 复制渲染资源
                 */
                this.executeCopy(originSolutionId, targetSolutionId, actionRemark);

            });
            log.info("*********** Finished {} time, fixed copy solution bug, at {}. ****",
                    fixedCopySolutionBugCounter++, TimeUtil.formatToString(LocalDateTime.now(), "yyyy-MM-dd H:mm:ss.SSS"));
        } else {
            log.info("*********** Not found bug solution, at {} time, {}. ****", fixedCopySolutionBugCounter++,
                    TimeUtil.formatToString(LocalDateTime.now(), "yyyy-MM-dd H:mm:ss.SSS"));
        }
    }

    @Override
    public void fixedOne(int originSolutionId, int targetSolutionId) {
        renderService.clearErrorPictures(targetSolutionId);

        /**
         * 2.2 复制渲染资源
         */
        this.executeCopy(originSolutionId, targetSolutionId, this.actionRemark);
    }

    /**
     * 执行资源复制
     *
     * @param originSolutionId 原方案Id
     * @param targetSolutionId 目标方案ID
     * @param remark           操作标注，如： copy 第一次复制， fix-copy-bug 修复以前复制bug
     */
    protected void executeCopy(int originSolutionId, int targetSolutionId, String remark) {
        log.debug("Execute Copy: originSolutinId={}, targetSolutionId={}, remark={}",
                originSolutionId, targetSolutionId, remark);

        //1 复制渲染图片资源
        Map<String, Integer> copyMap = this.copyRenderPic(originSolutionId, targetSolutionId);
        int videoCoverId = copyMap.containsKey("videoCoverId") ? copyMap.get("videoCoverId") : 0;
        if (!copyMap.isEmpty()) {
            //2 复制方案渲染的视频资源
            if (videoCoverId > 0) {
                //2.1 复制视频
                this.copyRenderVideo(originSolutionId, targetSolutionId, videoCoverId);
            }

            //2.2 更新方案封面图片
            DesignPlanRecommended recommended = new DesignPlanRecommended();
            recommended.setId(Long.valueOf(targetSolutionId));
            recommended.setCoverPicId(videoCoverId);
            //标注是复制，还是修复
            recommended.setRemark(remark);
            int result = designPlanRecommendedService.save(recommended);
            log.debug("Update Solution Cover Picture Id result={}", result);

            //3 复制方案渲染的漫游资源与配置
            if (copyMap.containsKey("oldScreenShotId") && copyMap.containsKey("screenShotId")) {
                //通过旧渲染截图ID
                this.copyRenderRoam(copyMap.get("oldScreenShotId"), copyMap.get("screenShotId"));
            }
        }
    }

    @Override
    public void fixedDeliverPlan() {
        //修复交付主方案未打组bug
        designPlanRecommendedService.fixedDeliverPlanByPrimary();
        //查询交付主从方案信息
        List<DesignPlanRecommended> lists = designPlanRecommendedService.selectDeliverPlanByPrimary();
        //修复交付从方案未打组bug
        for(DesignPlanRecommended plan : lists) {
            designPlanRecommendedService.fixedDeliverPlanBySide(plan.getId(),plan.getGroupPrimaryId());
        }
    }
}
