package com.sandu.service.solution.impl;

import com.google.common.base.Joiner;
import com.sandu.api.solution.model.DesignRenderRoam;
import com.sandu.api.solution.model.RenderRoamConfig;
import com.sandu.api.solution.model.ResRenderPic;
import com.sandu.api.solution.model.ResRenderVideo;
import com.sandu.api.solution.service.RenderService;
import com.sandu.service.solution.dao.RenderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 2018/5/24 18:01
 */
@Slf4j
@Service("renderService")
public class RenderServiceImpl implements RenderService {

    @Autowired
    private RenderMapper renderMapper;

    @Override
    public List<ResRenderPic> queryPictureBySolutionId(int solutionId) {
        return renderMapper.queryPictureBySolutionId(solutionId);
    }

    @Override
    public long insertPicture(ResRenderPic renderPicture) {
        int result = renderMapper.insertPicture(renderPicture);
        if (result > 0) {
            return renderPicture.getId();
        }
        return 0L;
    }

    @Override
    public int updatePicture(ResRenderPic renderPic) {
        return renderMapper.updatePicture(renderPic);
    }

    @Override
    public List<ResRenderVideo> queryVideoBySolutionId(int solutionId) {
        return renderMapper.queryVideoBySolutionId(solutionId);
    }

    @Override
    public int insertVideo(ResRenderVideo renderVideo) {
        int result = renderMapper.insertVideo(renderVideo);
        if (result > 0) {
            return renderVideo.getId();
        }
        return 0;
    }

    @Override
    public DesignRenderRoam getRoamByScreenShotId(int screenShotId) {
        return renderMapper.getRoamByScreenShotId(screenShotId);
    }

    @Override
    public int insertRoam(DesignRenderRoam renderRoam) {
        int result = renderMapper.insertRoam(renderRoam);
        if (result > 0) {
            return renderRoam.getId();
        }
        return 0;
    }

    @Override
    public RenderRoamConfig getRoamConfigById(int roamConfigId) {
        return renderMapper.getRoamConfigById(roamConfigId);
    }

    @Override
    public int insertRoamConfig(RenderRoamConfig config) {
        int result = renderMapper.insertRoamConfig(config);
        if (result > 0) {
            return config.getId();
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryBugSolutionDueToCopy(String actionRemark, int limit) {
        return renderMapper.queryBugSolutionDueToCopy(actionRemark, limit);
    }

    @Override
    public int clearErrorPictures(int targetSolutionId) {
        int result = renderMapper.clearErrorPictures(targetSolutionId);
        result += renderMapper.clearErrorVideos(targetSolutionId);
        return result;
    }
}
