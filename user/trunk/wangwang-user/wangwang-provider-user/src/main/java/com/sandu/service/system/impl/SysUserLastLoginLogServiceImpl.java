package com.sandu.service.system.impl;

import com.sandu.api.system.model.SysUserLastLoginLog;
import com.sandu.api.system.service.SysUserLastLoginLogService;
import com.sandu.service.system.dao.SysUserLastLoginLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sysUserLastLoginLogService")
public class SysUserLastLoginLogServiceImpl implements SysUserLastLoginLogService {

    @Autowired
    private SysUserLastLoginLogDao sysUserLastLoginLogDao;

    /**
     * 新增数据
     *
     * @param sysUserLastLoginLog
     * @return int
     */
    @Override
    public Long add(SysUserLastLoginLog sysUserLastLoginLog) {
        sysUserLastLoginLogDao.insertSelective(sysUserLastLoginLog);
        return sysUserLastLoginLog.getId();
    }

    /**
     * 更新数据
     *
     * @param sysUserLastLoginLog
     * @return int
     */
    @Override
    public int update(SysUserLastLoginLog sysUserLastLoginLog) {
        return sysUserLastLoginLogDao
                .updateByPrimaryKeySelective(sysUserLastLoginLog);
    }


    /**
     * 获取数据详情
     * 这个方法被改成了 按用户Id查找用户最后登录记录，慎用
     *
     * @param id
     * @return SysUserLastLoginLog
     */
    @Override
    public SysUserLastLoginLog get(Long id) {
        return sysUserLastLoginLogDao.selectByPrimaryKey(id);
    }


}
