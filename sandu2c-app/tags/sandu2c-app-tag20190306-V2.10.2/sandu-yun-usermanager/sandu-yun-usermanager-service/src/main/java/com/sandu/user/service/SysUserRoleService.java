package com.sandu.user.service;

import com.sandu.pay.order.model.ResultMessage;
import com.sandu.user.model.SysUserRole;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserRoleService.java
 * @Package com.sandu.system.service
 * @Description:系统模块-用户角色表Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-02 17:26:50
 */
public interface SysUserRoleService {

    /**
     * 所有数据
     *
     * @param sysUserRole
     * @return List<SysUserRole>
     */
    List<SysUserRole> getList(SysUserRole sysUserRole);
    
    /**
	 * 新增数据
	 *
	 * @param sysUserRole
	 * @return  int 
	 */
	int add(SysUserRole sysUserRole);
	/**
	 * 根据游客ID更新为正式用户
	 * @param registerId
	 * @return
	 */
	int updateByRegisterId(Integer registerId,Integer roleId);

	int addUserRole(SysUserRole sysUserRole);

	ResultMessage addUserJurisdiction(int userId);

	SysUserRole getByRegisterId(Integer registerId,Integer roleId);


}
