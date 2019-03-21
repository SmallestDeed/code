package com.sandu.service.base.impl;

import com.sandu.api.base.model.BaseBrand;
import com.sandu.api.base.service.BaseBrandService;
import com.sandu.service.base.dao.BaseBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("baseBrandService")
public class BaseBrandServiceImpl implements BaseBrandService {
    @Autowired
    private BaseBrandMapper baseBrandMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BaseBrand record) {
        return baseBrandMapper.insert(record);
    }

    @Override
    public int insertSelective(BaseBrand record) {
        return baseBrandMapper.insertSelective(record);
    }

    @Override
    public BaseBrand selectByPrimaryKey(Long id) {
        return baseBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BaseBrand record) {
        return baseBrandMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BaseBrand record) {
        return baseBrandMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<BaseBrand> selectByCompanyId(Long id) {
        return baseBrandMapper.selectByCompanyId(id);
    }

    @Override
    public String getHouseNameByPrimaryKey(Integer houseId) {
        return baseBrandMapper.getHouseNameByPrimaryKey(houseId);
    }
}
