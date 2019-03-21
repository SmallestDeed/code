package com.nork.render.service;

import com.nork.pano.model.RenderTaskState;
import com.nork.pano.model.RenderTaskStateSearch;
import com.nork.render.model.BaseHouseGuidePicInfo;

import java.util.List;

/**
 * Created by chenm on 2018/10/10.
 */
public interface BaseHouseGuidePicInfoService {
    int deleteByPrimaryKey(Long id);

    int insert(BaseHouseGuidePicInfo record);

    int insertSelective(BaseHouseGuidePicInfo record);

    BaseHouseGuidePicInfo getByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseHouseGuidePicInfo record);

    int updateByPrimaryKey(BaseHouseGuidePicInfo record);

    List<BaseHouseGuidePicInfo> getList(BaseHouseGuidePicInfo record);

    List<RenderTaskState> getRenderTaskStateList(RenderTaskStateSearch search);
    List<RenderTaskState> getRenderTaskListByFullHouseIdAndTemplateIds(RenderTaskStateSearch search);

}
