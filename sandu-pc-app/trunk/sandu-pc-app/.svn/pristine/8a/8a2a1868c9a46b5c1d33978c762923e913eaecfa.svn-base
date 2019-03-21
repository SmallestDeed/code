package com.sandu.system.service.impl;


import com.sandu.system.dao.ResRenderPicMapper;
import com.sandu.system.model.ResRenderPic;
import com.sandu.system.service.ResRenderPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resRenderPicService")
public class ResRenderPicServiceImpl implements ResRenderPicService{

    @Autowired
    private ResRenderPicMapper resRenderPicMapper;

    @Override
    public int add(ResRenderPic resPic) {
        return resRenderPicMapper.insertSelective(resPic);
    }

    @Override
    public int update(ResRenderPic resPic) {
        return resRenderPicMapper.updateByPrimaryKeySelective(resPic);
    }

    @Override
    public int delete(Integer id) {
        return resRenderPicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResRenderPic get(Integer id) {
        return resRenderPicMapper.selectByPrimaryKey(id);
    }

}
