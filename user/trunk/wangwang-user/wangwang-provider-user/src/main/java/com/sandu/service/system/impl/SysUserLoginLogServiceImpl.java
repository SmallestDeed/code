package com.sandu.service.system.impl;

import com.sandu.api.system.model.SysUserLoginLog;
import com.sandu.api.system.service.SysUserLoginLogService;
import com.sandu.service.system.dao.SysUserLoginLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserLoginLogServiceImpl implements SysUserLoginLogService {

    @Autowired
    private SysUserLoginLogDao sysUserLoginLogDao;

    @Override
    public void insertSysUserLoginLog(SysUserLoginLog sysUserLoginLog) {
        sysUserLoginLogDao.insertSysUserLoginLog(sysUserLoginLog);
    }

    /**
     * 查询用户上一次登录时间
     * @param userId  账号Id
     * @param platformId  平台Id
     * @return SysUserLoginLog
     */
    @Override
    public SysUserLoginLog getLastLoginInfo(Long userId, Long platformId) {
        return sysUserLoginLogDao.getLastLoginInfo(userId,platformId);
    }
}
