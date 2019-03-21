package com.sandu.system.dao;

import java.util.List;

import com.sandu.render.model.AutoRenderTaskState;
import com.sandu.system.model.BaseHousePicFullHousePlanRelSearch;
import com.sandu.system.model.RenderTaskState;
import com.sandu.system.model.RenderTaskStateSearch;
import org.apache.ibatis.annotations.Param;

import com.sandu.system.model.BaseHousePicFullHousePlanRel;

public interface BaseHousePicFullHousePlanRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseHousePicFullHousePlanRel record);

    int insertSelective(BaseHousePicFullHousePlanRel record);

    BaseHousePicFullHousePlanRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseHousePicFullHousePlanRel record);

    int updateByPrimaryKey(BaseHousePicFullHousePlanRel record);

	List<BaseHousePicFullHousePlanRel> getListByFullPlanId(@Param("fullHousePlanId") Integer fullHousePlanId);

    List<RenderTaskState> getTaskStateList(RenderTaskStateSearch search);

    List<BaseHousePicFullHousePlanRel> getBaseHousePicFullHousePlanRelList(BaseHousePicFullHousePlanRelSearch search);

    List<RenderTaskState> getRenderTaskListByFullHouseIdAndTemplateIds(RenderTaskStateSearch search);

    BaseHousePicFullHousePlanRel getBaseHousePicFullHousePlanRelByTemplateId(RenderTaskStateSearch search);
}