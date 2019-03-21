package com.sandu.design.dao;

import com.sandu.design.model.input.DesignPlanRenderSceneSearch;
import com.sandu.design.model.output.DesignPlanRenderSceneVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignPlanRenderSceneMapper {

    /**
     * 获取效果图方案总数
     * @param search
     * @return
     */
    int selectCount(DesignPlanRenderSceneSearch search);

    /**
     * 获取效果图方案列表
     * @param search
     * @return
     */
    List<DesignPlanRenderSceneVo> selectList(DesignPlanRenderSceneSearch search);

    /**
     * 获取方案风格 TEMP
     * @param designStyleId
     * @return
     */
    String getDesignStyle(Integer designStyleId);

    /**
     * 根据渲染类型和效果图方案ID获取最新渲染资源ID TEMP
     * @param designPlanRenderSceneId
     * @param renderingType
     * @return
     */
    Integer getRenderResourceId(@Param("designPlanRenderSceneId") Integer designPlanRenderSceneId, @Param("fileKey") String fileKey, @Param("renderingType") Integer renderingType);

    /**
     * 通过视频封面获取视频资源地址 TEMP
     * @param designPlanRenderSceneId
     * @return
     */
    Integer getVideoResourceId(@Param("designPlanRenderSceneId") Integer designPlanRenderSceneId);
}
