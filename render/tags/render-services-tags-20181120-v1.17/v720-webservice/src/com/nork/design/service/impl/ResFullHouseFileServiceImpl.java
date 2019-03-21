package com.nork.design.service.impl;

import com.nork.design.service.ResFullHouseFileService;
import com.nork.resource.dao.ResFullHouseFileMapper;
import com.nork.resource.model.ResFullHouseFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenm on 2018/10/8.
 */
@Service("resFullHouseFileService")
public class ResFullHouseFileServiceImpl implements ResFullHouseFileService {

    @Autowired
    private ResFullHouseFileMapper resFullHouseFileMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return resFullHouseFileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ResFullHouseFile record) {
        return resFullHouseFileMapper.insert(record);
    }

    @Override
    public int insertSelective(ResFullHouseFile record) {
        return resFullHouseFileMapper.insertSelective(record);
    }

    @Override
    public ResFullHouseFile selectByPrimaryKey(Integer id) {
        return resFullHouseFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ResFullHouseFile record) {
        return resFullHouseFileMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ResFullHouseFile record) {
        return resFullHouseFileMapper.updateByPrimaryKey(record);
    }
}
