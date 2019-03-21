package com.sandu.system.service.impl;


import com.sandu.demo.model.ResDesign;
import com.sandu.system.dao.ResDesignMapper;
import com.sandu.system.service.ResDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("resDesignService")
public class ResDesignServiceImpl implements ResDesignService {

    @Autowired
    private ResDesignMapper resDesignMapper;

    @Override
    public int add(ResDesign resDesign) {
        resDesignMapper.insertSelective(resDesign);
        return resDesign.getId();
    }

    @Override
    public int update(ResDesign resDesign) {
        return resDesignMapper.updateByPrimaryKeySelective(resDesign);
    }

    @Override
    public int delete(Integer id) {
        return resDesignMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResDesign get(Integer id) {
        return resDesignMapper.selectByPrimaryKey(id);
    }

}
