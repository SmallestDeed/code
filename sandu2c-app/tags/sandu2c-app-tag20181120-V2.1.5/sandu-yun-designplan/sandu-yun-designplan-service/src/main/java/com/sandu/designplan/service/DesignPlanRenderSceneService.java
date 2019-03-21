package com.sandu.designplan.service;

import java.util.List;

import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.DesignPlanRenderScene;
import com.sandu.designplan.vo.MydecorationPlanVo;

/**
 * copy from DesignPlanService
 *
 * @version V1.0
 * @Title: DesignPlanEctypeService.java
 * @Package com.sandu.design.service
 * @Description:设计模块渲染场景服务
 * @createAuthor huangsongbo
 * @CreateDate 2017.7.14
 */
public interface DesignPlanRenderSceneService {

    /**
     * 添加设计方案副本数据
     *
     * @param designPlanEctype
     * @return
     * @author huangsongbo
     */
    int add(DesignPlanRenderScene designPlanEctype);

    /**
     * 更新/修改DesignPlanRenderScene
     *
     * @param designPlanEctype
     * @author huangsongbo
     */
    void update(DesignPlanRenderScene designPlanEctype);

    /**
     * 删除DesignPlanRenderScene(by id)
     *
     * @param id
     * @author huangsongbo
     */
    void delete(long id);

    /**
     * 根据id查找设计方案副本(DesignPlanRenderScene)
     *
     * @param designPlanRenderSceneId
     * @return
     * @author huangsongbo
     */
    DesignPlanRenderScene get(Integer designPlanRenderSceneId);

	int selectVendorCountV2(DesignPlanRenderScene designPlanRenderScene);

	List<DesignPlanRenderScene> selectVendorListV2(DesignPlanRenderScene designPlanRenderScene);

    MydecorationPlanVo selectDesignPlanInfo(Integer planId);

    List<MydecorationPlanVo> selectDesignPlanInfoList(List<Integer> signleSpaceScenePlanIds);
}
