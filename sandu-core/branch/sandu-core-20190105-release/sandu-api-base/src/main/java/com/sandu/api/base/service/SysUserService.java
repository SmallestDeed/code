package com.sandu.api.base.service;

import com.sandu.api.base.model.SysUser;
import org.springframework.stereotype.Component;

@Component
public interface SysUserService {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    String getUserDefaultPic(int i);
}
