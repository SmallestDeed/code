package com.sandu.service.system.dao;

import com.sandu.api.system.model.SysUserLastLoginLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserLastLoginLogDao {

    SysUserLastLoginLog selectByPrimaryKey(Long id);

    int insertSelective(SysUserLastLoginLog record);

    int updateByPrimaryKeySelective(SysUserLastLoginLog record);

}
