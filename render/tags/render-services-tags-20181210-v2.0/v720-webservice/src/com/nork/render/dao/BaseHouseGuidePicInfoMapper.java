package com.nork.render.dao;

import java.util.List;

import com.nork.pano.model.RenderTaskState;
import com.nork.pano.model.RenderTaskStateSearch;
import com.nork.render.model.BaseHouseGuidePicInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BaseHouseGuidePicInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BaseHouseGuidePicInfo record);

    int insertSelective(BaseHouseGuidePicInfo record);

    BaseHouseGuidePicInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseHouseGuidePicInfo record);

    int updateByPrimaryKey(BaseHouseGuidePicInfo record);

    List<BaseHouseGuidePicInfo> selectList(BaseHouseGuidePicInfo record);

    List<RenderTaskState> getTaskStateList(RenderTaskStateSearch search);

    List<RenderTaskState> getRenderTaskListByFullHouseIdAndTemplateIds(RenderTaskStateSearch search);

}