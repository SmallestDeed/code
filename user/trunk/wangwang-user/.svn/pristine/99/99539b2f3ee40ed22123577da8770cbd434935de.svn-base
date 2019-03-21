package com.sandu.service.system.impl;

import com.sandu.api.system.model.SysUserLoginAggregated;

import com.sandu.api.system.service.SysUserLoginAggregatedService;
import com.sandu.service.system.dao.SysUserLoginAggregatedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserLoginAggregatedService")
public class SysUserLoginAggregatedServiceImpl implements SysUserLoginAggregatedService {

    @Autowired
    private SysUserLoginAggregatedDao sysUserLoginAggregatedDao;

    @Override
    public SysUserLoginAggregated queryUserAggregatedInfo(Long userId, Long platformId) {
        return sysUserLoginAggregatedDao.queryUserAggregatedInfo(userId,platformId);
    }

    @Override
    public void addUserLoginAggregated(SysUserLoginAggregated sl) {
        sysUserLoginAggregatedDao.insertUserLoginAggregated(sl);
    }

    @Override
    public int increaseLoginCount(Long userId,Long platformId) {
       return sysUserLoginAggregatedDao.updateUserLoginAggregated(userId,platformId);
    }
}
