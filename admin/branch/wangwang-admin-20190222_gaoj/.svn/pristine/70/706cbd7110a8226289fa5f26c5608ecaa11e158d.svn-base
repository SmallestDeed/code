package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignRenderRoam;
import com.sandu.api.solution.model.RenderRoamConfig;
import com.sandu.api.solution.model.ResRenderPic;
import com.sandu.api.solution.model.ResRenderVideo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 2018/5/24 21:19
 */
@Repository
public interface RenderMapper {


    List<ResRenderPic> queryPictureBySolutionId(@Param("solutionId") int solutionId);

    int insertPicture(ResRenderPic renderPic);

    int updatePicture(ResRenderPic renderPic);

    List<ResRenderVideo> queryVideoBySolutionId(@Param("solutionId") int solutionId);

    int insertVideo(ResRenderVideo renderVideo);

    DesignRenderRoam getRoamByScreenShotId(@Param("screenShotId") int screenShotId);

    int insertRoam(DesignRenderRoam renderRoam);

    RenderRoamConfig getRoamConfigById(@Param("roamConfigId") int roamConfigId);

    int insertRoamConfig(RenderRoamConfig config);

    List<Map<String,Object>> queryBugSolutionDueToCopy(@Param("actionRemark") String actionRemark,
                                                       @Param("limit") int limit);

    int clearErrorPictures(@Param("targetSolutionId") int targetSolutionId);

    int clearErrorVideos(@Param("targetSolutionId") int targetSolutionId);
}
