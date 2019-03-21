package com.sandu.system.service;

import com.sandu.design.model.DesignRenderRoam;
import com.sandu.design.model.ResRenderPicQO;
import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.render.model.ResRenderData;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: ResRenderPicService.java
 * @Package com.sandu.system.service
 * @Description:渲染图片资源库-渲染图片资源库Service
 * @createAuthor pandajun
 * @CreateDate 2017-03-22 14:39:08
 */
public interface ResRenderPicService {


    /**
     * 所有数据
     *
     * @param resRenderPic
     * @return List<ResRenderPic>
     */
    List<ResRenderPic> getList(ResRenderPic resRenderPic);

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResRenderPic
     */
    ResRenderPic get(Integer id);

    /**
     * 通过sourcePlanId和templateId获取720全景图的sys_code
     *
     * @param sourcePlanId
     * @param renderingType
     * @return
     */
    Map<String, String> getResRenderPic(Integer sourcePlanId, Integer templateId, Integer renderingType);

    /**
     * 其他
     *
     */

    /**
     * 根据推荐方案Id和渲染类型获得最新渲染原图
     */
    ResRenderData getResRenderPicByPlanRecommended(Integer planRecommendedId, Integer renderingType);

	String getQRCodeInfo(ResRenderPic smallRenderPic);

    /**
     * 获取720多点CODE
     * @param picResId  资源id
     * @return  codeUUID
     */
    String get720RomanCode(Integer picResId);

    /**
     * 通过pid获取缩略图
     * @param pid
     * @return
     */
    ResRenderPic selectOneByPid(Integer pid);

    /**
     * 从自动渲染里面取720全景效果图
     * @param resRenderPic
     * @return
     */
    List<ResRenderPic> selectListOfMobile(ResRenderPic resRenderPic);

    /**
     * 从自动渲染里面通过pid取缩略图
     * @param pid
     * @return
     */
    ResRenderPic selectOneByPidOfMobile(Integer pid);

    /**
     * 查询渲染任务完成后的数据--为提高性能，减少字段
     * @param resRenderPic  渲染对象
     * @return
     */
    List<ResRenderPic> queryPicListByResRenderPic(ResRenderPic resRenderPic);

	List<ResRenderPic> selectListByFileKeys(ResRenderPicQO resRenderPicQO);

	ResRenderPic get720SingleCode(ResRenderPic resPic);

	DesignRenderRoam get720RomanCode(ResRenderPic resPic);


    Map<String,List<ResRenderData>> getAllResRenderPic(Integer designPlanRecommendedId,renderResEnum type);

    ResRenderPic getResRenderPicByPlanRecommended2(Integer planRecommendedId);


    List<ResRenderPic> getResRenderPicListByRecommendedIds(List<Integer> planRecommendedIds);

    Map<Integer,String> idAndPathMap(List<Integer> picIds);

    ResRenderPic getMyPlanCoverResRenderPic(Integer planId);

    ResRenderPic getFullHouseCoverResRenderPic(Integer planId);

    ResRenderPic getSingleSpaceCoverResRenderPic(Integer planId);

    enum renderResEnum {
        designPlanRenderScene, designPlanRecommended
    }
}
