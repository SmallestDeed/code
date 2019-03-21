package com.sandu.design.service.impl;

import com.sandu.design.dao.DesignPlanRenderSceneMapper;
import com.sandu.design.model.input.DesignPlanRenderSceneSearch;
import com.sandu.design.model.output.DesignPlanRenderSceneVo;
import com.sandu.design.service.DesignPlanRenderSceneService;
import com.sandu.panorama.dao.DesignPlanStoreReleaseDetailsMapper;
import com.sandu.panorama.dao.DesignPlanStoreReleaseMapper;
import com.sandu.panorama.model.DesignPlanStoreRelease;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("designPlanRenderSceneService")
@Transactional
public class DesignPlanRenderSceneServiceImpl implements DesignPlanRenderSceneService {

    @Autowired
    private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;
    @Autowired
    private DesignPlanStoreReleaseMapper designPlanStoreReleaseMapper;
    @Autowired
    private DesignPlanStoreReleaseDetailsMapper designPlanStoreReleaseDetailsMapper;

    /**
     * 获取效果图方案总数
     * @param search
     * @return
     */
    @Override
    public int selectCount(DesignPlanRenderSceneSearch search) {
        if( search == null ){
            return 0;
        }
        return designPlanRenderSceneMapper.selectCount(search);
    }

    /**
     * 获取效果图方案列表
     * @param search
     * @return
     */
    @Override
    public List<DesignPlanRenderSceneVo> selectList(DesignPlanRenderSceneSearch search) {
        if( search == null || search.getShareType() == null ){
            return null;
        }
        List<DesignPlanRenderSceneVo> designPlanRenderSceneVoList = designPlanRenderSceneMapper.selectList(search);
        if( designPlanRenderSceneVoList != null && designPlanRenderSceneVoList.size() > 0 ){
            // TODO 1、获取方案的封面图。2、获取方案的每种渲染类型（照片、720、多点、视频）的资源，同一类下多个资源取最新的。3、如果分享类型（shareType）是全户型分享，则只需要720和多点的资源。
            for( DesignPlanRenderSceneVo designPlanRenderSceneVo : designPlanRenderSceneVoList ){
                // 方案封面图（临时写法）TEMP
                Integer coverPicId = designPlanRenderSceneVo.getCoverPicId();
                String coverPicPath = "";
                if( coverPicId == null ){
                    coverPicPath = designPlanStoreReleaseMapper.getCoverPicPath(designPlanRenderSceneVo.getId());
                }else{
                    coverPicPath = designPlanStoreReleaseDetailsMapper.getResourcePathById(coverPicId, "res_render_pic");
                }
                designPlanRenderSceneVo.setCoverPicPath(coverPicPath);

                // 设计风格 TEMP
                Integer designStyleId = designPlanRenderSceneVo.getDesignStyleId();
                String designStyle = designPlanRenderSceneMapper.getDesignStyle(designStyleId);
                designPlanRenderSceneVo.setDesignStyle(designStyle);

                if( DesignPlanStoreRelease.ShareType.CASUAL == search.getShareType().intValue() ){
                    // 最新照片渲染资源 TEMP
                    Integer normalRenderId = designPlanRenderSceneMapper.getRenderResourceId(designPlanRenderSceneVo.getId(), "design.designPlan.render.pic", 1);
                    designPlanRenderSceneVo.setNormalRenderId(normalRenderId);
                    // 最新视频级渲染资源 TEMP
                    Integer videoRenderCoverId = designPlanRenderSceneMapper.getRenderResourceId(designPlanRenderSceneVo.getId(), "design.designPlan.render.video.cover", 6);
                    if( videoRenderCoverId != null ) {
                        Integer videoRenderId = designPlanRenderSceneMapper.getVideoResourceId(videoRenderCoverId);
                        designPlanRenderSceneVo.setVideoRenderId(videoRenderId);
                    }
                }
                // 最新720渲染资源 TEMP
                Integer panoramaRenderId = designPlanRenderSceneMapper.getRenderResourceId(designPlanRenderSceneVo.getId(), "design.designPlan.render.pic", 4);
                designPlanRenderSceneVo.setPanoramaRenderId(panoramaRenderId);

                // 最新720多点渲染资源，存封面截图ID TEMP
                Integer roamRenderId = designPlanRenderSceneMapper.getRenderResourceId(designPlanRenderSceneVo.getId(), "design.designPlan.render.pic", 8);
                designPlanRenderSceneVo.setRoamRenderId(roamRenderId);
            }
        }
        return designPlanRenderSceneVoList;
    }

}
