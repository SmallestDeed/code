package com.sandu.api.system.service;

import com.sandu.api.system.model.SysUserLoginLog;

import java.util.List;

public interface SysUserLoginLogService {

     void insertSysUserLoginLog(SysUserLoginLog sysUserLoginLog);

     /**
      * add by WangHaiLin
      * 查询账号上一次登录信息
      * @param userId  id
      * @param platformId  平台Id
      * @return SysUserLoginLog
      */
     SysUserLoginLog getLastLoginInfo(Long userId, Long platformId);
}
