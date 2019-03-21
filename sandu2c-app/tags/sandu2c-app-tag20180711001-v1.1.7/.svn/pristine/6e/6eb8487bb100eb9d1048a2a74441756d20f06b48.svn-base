package com.sandu.user.service.impl;

import com.sandu.common.util.Utils;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.platform.BasePlatFormConstant;
import com.sandu.user.dao.SysUserRoleMapper;
import com.sandu.user.dao.UserJurisdictionMapper;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.SysUserRole;
import com.sandu.user.model.UserJurisdiction;
import com.sandu.user.service.SysUserRoleService;
import com.sandu.user.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserRoleServiceImpl.java
 * @Package com.sandu.system.service.impl
 * @Description:系统模块-用户角色表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-02 17:26:50
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserJurisdictionMapper userJurisdictionMapper;
    /**
     * 所有数据
     *
     * @param sysUserRole
     * @return List<SysUserRole>
     */
    @Override
    public List<SysUserRole> getList(SysUserRole sysUserRole) {
        return sysUserRoleMapper.selectList(sysUserRole);
    }

	@Override
	public int add(SysUserRole sysUserRole) {
		sysUserRoleMapper.insertSelective(sysUserRole);
        return sysUserRole.getId();
    }

	@Override
	public int updateByRegisterId(Integer registerId,Integer roleId) {
		// TODO Auto-generated method stub
		return sysUserRoleMapper.updateByRegisterId(registerId,roleId);
	}

	@Override
	public int addUserRole(SysUserRole sysUserRole) {
		int roleGroupId = sysUserRoleMapper.sysRoleGroup(sysUserRole);
		if(roleGroupId==0) {
			return 0;
		}
		sysUserRole.setRoleGroupId(roleGroupId);
		return sysUserRoleMapper.insertUserRoleGroup(sysUserRole);
	}

	@Override
	public ResultMessage addUserJurisdiction(int userId) {
		ResultMessage message = new ResultMessage();
		SysUser sysUser = sysUserService.get(userId);
		if (null == sysUser) {
			message.setMessage("用户信息为空：sysUser:{}" + sysUser);
			return message;
		}
		//C端注册分配运营网站平台权限
		UserJurisdiction brand2CJurisdiction= new UserJurisdiction();
		brand2CJurisdiction.setUserId(userId);
		brand2CJurisdiction.setPlatformId(BasePlatFormConstant.BRAND2C_PLATFORM_ID);
		brand2CJurisdiction.setJurisdictionStatus(1);
		brand2CJurisdiction.setModifierUserId(userId);
		brand2CJurisdiction.setGmtCreate(new Date());
		brand2CJurisdiction.setCreator(sysUser.getUserName());
		brand2CJurisdiction.setModifier(sysUser.getUserName());
		brand2CJurisdiction.setGmtModified(new Date());
		brand2CJurisdiction.setIsDeleted(0);
		brand2CJurisdiction.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
		int i = userJurisdictionMapper.insertSelective(brand2CJurisdiction);
		if (i != 1) {
			message.setMessage("用户id：userId:{}" + userId + "分配BRAND2C的平台权限失败");
			return message;
		}
		//C端注册分配移动端2C平台权限
		UserJurisdiction mobile2CJurisdiction= new UserJurisdiction();
		mobile2CJurisdiction.setUserId(userId);
		mobile2CJurisdiction.setPlatformId(BasePlatFormConstant.MOBILE2C_PLATFORM_ID);
		mobile2CJurisdiction.setJurisdictionStatus(1);
		mobile2CJurisdiction.setModifierUserId(userId);
		mobile2CJurisdiction.setGmtCreate(new Date());
		mobile2CJurisdiction.setCreator(sysUser.getUserName());
		mobile2CJurisdiction.setModifier(sysUser.getUserName());
		mobile2CJurisdiction.setGmtModified(new Date());
		mobile2CJurisdiction.setIsDeleted(0);
		mobile2CJurisdiction.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
		int i2 = userJurisdictionMapper.insertSelective(mobile2CJurisdiction);
		if (i2 != 1) {
			message.setMessage("用户id：userId:{}" + userId + "分配MOBILE2C的平台权限失败");
			return message;
		}
		message.setMessage("用户id：userId:{}" + userId + "分配C端平台权限成功");
		message.setSuccess(true);
		return message;
	}

	@Override
	public SysUserRole getByRegisterId(Integer registerId,Integer roleId) {
    	if (null == registerId || null == roleId) {
    		return null;
		}
		return sysUserRoleMapper.getByRegisterId(registerId,roleId);
	}
}
