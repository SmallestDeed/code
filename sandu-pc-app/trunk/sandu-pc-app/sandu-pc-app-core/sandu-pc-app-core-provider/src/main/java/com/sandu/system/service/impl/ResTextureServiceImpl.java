package com.sandu.system.service.impl;

import com.sandu.system.dao.ResTextureMapper;
import com.sandu.system.model.ResTexture;
import com.sandu.system.service.ResTextureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resTextureService")
public class ResTextureServiceImpl implements ResTextureService {

    @Autowired
    private ResTextureMapper resTextureMapper;

    @Override
    public int add(ResTexture resTexture) {
        return resTextureMapper.insertSelective(resTexture);
    }

    @Override
    public int update(ResTexture resTexture) {
        return resTextureMapper.updateByPrimaryKeySelective(resTexture);
    }

    @Override
    public int delete(Integer id) {
        return resTextureMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResTexture get(Integer id) {
        return resTextureMapper.selectByPrimaryKey(id);
    }

}
