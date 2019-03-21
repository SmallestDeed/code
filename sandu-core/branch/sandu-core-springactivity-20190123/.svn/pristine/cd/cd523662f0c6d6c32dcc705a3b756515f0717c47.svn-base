package com.sandu.service.base.impl;

import com.sandu.api.base.model.SysUser;
import com.sandu.api.base.service.SysUserService;
import com.sandu.service.base.dao.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Long id) {
        if (id == null) {
            return null;
        }
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public String getUserDefaultPic(int i) {
        return sysUserMapper.selectUserDefaultPic(i);
    }
}
