package com.nork.render.service.impl;

import com.nork.pano.model.RenderTaskState;
import com.nork.pano.model.RenderTaskStateSearch;
import com.nork.render.dao.BaseHouseGuidePicInfoMapper;
import com.nork.render.model.BaseHouseGuidePicInfo;
import com.nork.render.service.BaseHouseGuidePicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenm on 2018/10/10.
 */
@Service("baseHouseGuidePicInfoService")
public class BaseHouseGuidePicInfoServiceImpl implements BaseHouseGuidePicInfoService {

    @Autowired
    private BaseHouseGuidePicInfoMapper baseHouseGuidePicInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseHouseGuidePicInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BaseHouseGuidePicInfo record) {
        return baseHouseGuidePicInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(BaseHouseGuidePicInfo record) {
        return baseHouseGuidePicInfoMapper.insertSelective(record);
    }

    @Override
    public BaseHouseGuidePicInfo getByPrimaryKey(Long id) {
        return baseHouseGuidePicInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BaseHouseGuidePicInfo record) {
        return baseHouseGuidePicInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BaseHouseGuidePicInfo record) {
        return baseHouseGuidePicInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<BaseHouseGuidePicInfo> getList(BaseHouseGuidePicInfo record) {
        return baseHouseGuidePicInfoMapper.selectList(record);
    }

    @Override
    public List <RenderTaskState> getRenderTaskStateList(RenderTaskStateSearch search) {
        return baseHouseGuidePicInfoMapper.getTaskStateList(search);
    }

    @Override
    public List <RenderTaskState> getRenderTaskListByFullHouseIdAndTemplateIds(RenderTaskStateSearch search) {
        return baseHouseGuidePicInfoMapper.getRenderTaskListByFullHouseIdAndTemplateIds(search);
    }
}
