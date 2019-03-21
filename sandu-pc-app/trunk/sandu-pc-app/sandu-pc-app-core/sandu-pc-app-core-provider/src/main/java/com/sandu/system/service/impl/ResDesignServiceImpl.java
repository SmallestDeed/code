package com.sandu.system.service.impl;

import com.sandu.system.dao.ResDesignMapper;
import com.sandu.system.model.ResDesign;
import com.sandu.system.service.ResDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resDesignService")
public class ResDesignServiceImpl implements ResDesignService {

    @Autowired
    private ResDesignMapper designMapper;

    @Override
    public int add(ResDesign resDesign) {
        return designMapper.insertSelective(resDesign);
    }

    @Override
    public int update(ResDesign resDesign) {
        return designMapper.updateByPrimaryKeySelective(resDesign);
    }

    @Override
    public int delete(Integer id) {
        return designMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResDesign get(Integer id) {
        return designMapper.selectByPrimaryKey(id);
    }

}
