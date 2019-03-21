package com.sandu.service.operatorLog.impl;

import com.sandu.api.operatorLog.model.SysUserOperatorLog;
import com.sandu.api.operatorLog.service.SysUserOperatorLogService;
import com.sandu.service.operatorLog.dao.SysUserOperatorLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/7/27  14:40
 */
@Service("sysUserOperatorLogService")
public class SysUserOperatorLogServiceImpl implements SysUserOperatorLogService {

    @Autowired
    private SysUserOperatorLogDao sysUserOperatorLogDao;

    @Override
    public int addLog(SysUserOperatorLog operatorLog) {
        return sysUserOperatorLogDao.insert(operatorLog);
    }

    @Override
    public List<SysUserOperatorLog> getLogByUserId(Long userId) {
        return sysUserOperatorLogDao.selectByUserId(userId);
    }

    @Override
    public int updateByUserId(Long userId) {
        return sysUserOperatorLogDao.updateByUserId(userId);
    }
}
