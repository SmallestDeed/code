package com.sandu.service.servicepurchase.dao;

import com.sandu.api.user.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserRoleMapper.java
 * @Package com.nork.system.dao
 * @Description:系统模块-用户角色表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-02 17:26:50
 */
@Repository
@Transactional
public interface SysUserRoleMapper {
	int insertSelective(SysUserRole record);

	int updateByPrimaryKeySelective(SysUserRole record);

	int deleteByPrimaryKey(Integer id);

	SysUserRole selectByPrimaryKey(Integer id);


	List<SysUserRole> selectList(SysUserRole sysUserRole);

	/**
	 * 其他
	 *
	 */

	/**
	 * 根据权限编码查找用户是否有该权限
	 *
	 * @param sysRoleCode
	 * @param userId
	 * @return
	 */
	List<SysUserRole> getSysUserRoleByUserAndRuleCode(@Param("sysRoleCode") String sysRoleCode, @Param("userId") Integer userId);

	/**
	 * @return
	 */
	int delAllU3DRoleByUserId(SysUserRole sysUserRole);

	/**
	 * 根据角色id 用户id去查询
	 */
	List<SysUserRole> selectByPrimaryUserRole(SysUserRole sysUserRole);

	/**
	 * 批量新增用户角色
	 *
	 * @param userRoleList
	 * @autho xiaoxc-2018-04-23
	 */
	void insertUserRoleList(@Param("userRoleList") List<SysUserRole> userRoleList);

	List<SysUserRole> selectByRoleId(int roleid);

	void delByRoleId(int roleid);

	/**
	 * 按id更新用户角色状态
	 *
	 * @param id
	 * @oaram isDeleted
	 */
	void updateStatusById(@Param("id") Integer id, @Param("isDeleted") Integer isDeleted, @Param("modifier") String modifier, @Param("gmtModified") Date gmtModified);
}
