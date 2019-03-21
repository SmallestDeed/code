package com.sandu.service.designplan.dao;

import com.sandu.api.designplan.input.PlanInput;
import com.sandu.api.designplan.input.PlanRenderSceneInput;
import com.sandu.api.designplan.model.*;
import com.sandu.api.designplan.output.DesignPlanRenderSceneVo;
import com.sandu.api.designplan.output.SingleDesignPlanVo;
import com.sandu.api.designplan.model.DesignPlanProduct;
import com.sandu.design.model.DesignRenderRoam;
import com.sandu.home.model.SpaceCommon;
import com.sandu.system.model.ResDesign;
import com.sandu.system.model.ResDesignRenderScene;
import com.sandu.system.model.ResModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignPlanRenderSceneMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanRenderScene record);

    int insertSelective(DesignPlanRenderScene record);

    DesignPlanRenderScene selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanRenderScene record);

    int updateByPrimaryKey(DesignPlanRenderScene record);

    DesignPlanRenderSceneVo selectDesignPlanRenderSceneListByfullHousePlanId(@Param("fullHousePlanId") Integer fullHousePlanId,@Param("designTemplateId") Integer designTemplateId);

    List<SingleDesignPlanVo> selectDesignPlanListByDemandId(@Param("demandId") Integer demandId, @Param("userId") Integer userId);

    List<DesignPlan> getDesignPlan(@Param("list") List<PlanInput> planInputList);

    List<ResModel> getResModelByPlanModelId(@Param("list")List<DesignPlan> designplanList);

    List<ResDesign> getResDesignByConfigFileId(@Param("list")List<DesignPlan> designplanList);

    List<DesignPlanProduct> getDesignPlanProductByPlanId(@Param("list")List<DesignPlan> designplanList);

    List<Integer> selectRender720ResourceByPlanId(List<SingleDesignPlanVo> singleDesignPlanVoList);

    DesignPlanRenderSceneVo selectDesignPlanRenderSceneList(@Param("planId") Integer planId);

    List<PlanRenderSceneInput> selectDesignPlanRenderSceneListByIds(List<Integer> renderSceneIds);

    int insertDesignPlanRenderSceneList(@Param("list") List<DesignPlanRenderScene> designPlanRenderSceneList);

    int deleteList(List<Integer> designPlanRenderSceneIdList);

    int updateList(@Param("list") List<DesignPlanRenderScene> designPlanRenderSceneList);

    int addDesignPlanProductRenderSceneList(@Param("designPlanProductRenderSceneList") List<DesignPlanProductRenderScene> designPlanProductRenderSceneList);

    int insertResdesignRenderScene(ResDesignRenderScene resDesignRenderScene);

    ResDesign getResDesignByspellingFlowerFileId(Integer spellingFlowerFileId);

    int updateDesignPlanRenderScene(DesignPlanRenderScene designPlanRenderScenePic);

    SpaceCommon selectSpaceCommonInfo(Integer spaceCommonId);

    int insertResRenderPic(ResRenderPic resRenderPic);

    List<ResRenderPic> selectResRenderPicList(ResRenderPic res);

    ResRenderPic selectResRenderPic(Integer renerSmallId);

    int updateResRenderPic(ResRenderPic renderPicSmall);

    List<ResRenderVideo> selectResRenderVideoList(ResRenderVideo resRenderVideoSearch);

    Integer addResRenderVideo(ResRenderVideo renderVideo);

    int updateResRenderPicSysTaskId(@Param("sysTaskId") Integer renderId, @Param("idList") List<Integer> idList);

    List<DesignRenderRoam> getDesignRenderRoamList(DesignRenderRoam designRenderRoam);

    ResDesign getResDesign(Integer roamConfig);

    Integer addDesignRenderRoam(DesignRenderRoam designRenderRoam);

    Integer addResDesign(ResDesign resDesignNew);

    DesignRenderRoam selectDesignRenderRoam(Integer renderRoamId);

    int updateDesignRenderRoam(DesignRenderRoam renderRoam);
}