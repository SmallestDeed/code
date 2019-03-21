package com.sandu.service.base.impl;

import com.sandu.api.base.model.BaseProductStyle;
import com.sandu.api.base.service.BaseProductStyleService;
import com.sandu.service.base.dao.BaseProductStyleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("baseProductStyleService")
public class BaseProductStyleServiceImpl implements BaseProductStyleService {
    @Autowired
    private BaseProductStyleMapper baseProductStyleMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseProductStyleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BaseProductStyle record) {
        return baseProductStyleMapper.insert(record);
    }

    @Override
    public int insertSelective(BaseProductStyle record) {
        return baseProductStyleMapper.insertSelective(record);
    }

    @Override
    public BaseProductStyle selectByPrimaryKey(Long id) {
        return baseProductStyleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BaseProductStyle record) {
        return baseProductStyleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BaseProductStyle record) {
        return baseProductStyleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<BaseProductStyle> selectListByParentCode(String code) {
        return baseProductStyleMapper.selectListByParentCode(code);
    }
}
