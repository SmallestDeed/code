package com.nork.system.service.impl;

import com.nork.system.dao.SysUserLevelPriceMapper;
import com.nork.system.model.SysUserLevelPrice;
import com.nork.system.service.SysUserLevelPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yanghz on 2017-08-15.
 */
@Service
public class SysUserLevelPriceServiceImpl implements SysUserLevelPriceService {
    @Autowired
    private SysUserLevelPriceMapper sysUserLevelPriceMapper;

    @Override
    public List<SysUserLevelPrice> getListByUserId(Integer userId) {
        if (userId == null || userId < 1){
            return null;
        }
        return sysUserLevelPriceMapper.getListByUserId(userId);
    }

    @Override
    public SysUserLevelPrice getIdByUserType(int userType) {
        return sysUserLevelPriceMapper.getIdByUserType(userType);
    }
}
