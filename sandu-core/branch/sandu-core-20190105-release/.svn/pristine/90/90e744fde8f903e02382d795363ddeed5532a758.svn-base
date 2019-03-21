package com.sandu.service.base.dao;

import com.sandu.api.base.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    String selectUserDefaultPic(int i);
}