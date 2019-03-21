package com.sandu.designplan.dao;

import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.DesignPlanRenderScene;
import com.sandu.designplan.vo.MydecorationPlanVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignPlanEctypeMapper.java
 * @Package com.sandu.design.dao
 * @Description:设计方案-设计方案表Mapper
 * @createAuthor huangsongbo
 * @CreateDate 2017.7.14
 */
@Repository
public interface DesignPlanRenderSceneMapper {

    /**
     * 新增一条数据
     *
     * @return
     * @author huangsongbo
     */
    int insert(DesignPlanRenderScene designPlanRenderScene);

    /**
     * 删除数据
     *
     * @param id
     * @author huangsongbo
     */
    void delete(@Param("id") long id);

    /**
     * 根据设计方案方案id获取最新的一条副本
     *
     * @param designPlanId
     * @return
     * @author huangsongbo
     */
    DesignPlanRenderScene getLatelyPlanScenByPlanId(int designPlanId);

    /**
     * 更新数据
     *
     * @param designPlanEctype
     */
    void update(DesignPlanRenderScene designPlanEctype);

    /**
     * 通过id查找数据
     *
     * @param id
     * @return
     * @author huangsongbo
     */
    DesignPlanRenderScene get(Integer id);

    int getDesignPlanSceneState(Integer designPlanId);

    int getVendorCount(DesignPlanRenderScene designPlanRenderScene);

    List<DesignPlanRenderScene> getVendorList(DesignPlanRenderScene designPlanRenderScene);


	int getVendorCountV2(DesignPlanRenderScene designPlanRenderScene);

	List<DesignPlanRenderScene> getVendorListV2(DesignPlanRenderScene designPlanRenderScene);

    MydecorationPlanVo selectDesignPlanInfo(Integer planId);

    List<MydecorationPlanVo> selectDesignPlanInfoList(List<Integer> signleSpaceScenePlanIds);

    List<MydecorationPlanVo> selectPcDesignPlanRenderScene(List<Integer> signleSpaceScenePlanIds);
}
