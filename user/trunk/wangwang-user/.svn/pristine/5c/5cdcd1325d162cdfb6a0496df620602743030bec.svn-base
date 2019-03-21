package com.sandu.service.system.dao;

import com.sandu.api.system.model.SysUserLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserLoginLogDao {

    void insertSysUserLoginLog(SysUserLoginLog sysUserLoginLog);

    /**
     * add by WangHaiLin
     * 查询账号上一次登录信息
     * @param userId  账号id
     * @param platformId  平台id
     * @return SysUserLoginLog
     */
    SysUserLoginLog getLastLoginInfo(@Param("userId") Long userId, @Param("platformId") Long platformId);

}
