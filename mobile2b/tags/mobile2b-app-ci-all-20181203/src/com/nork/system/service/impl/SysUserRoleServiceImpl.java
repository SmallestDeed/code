package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysUserRoleMapper;
import com.nork.system.model.SysUserRole;
import com.nork.system.model.search.SysUserRoleSearch;
import com.nork.system.service.SysUserRoleService;

/**   
 * @Title: SysUserRoleServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-用户角色表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-02 17:26:50
 * @version V1.0   
 */
@Service("sysUserRoleService")
@Transactional
public class SysUserRoleServiceImpl implements SysUserRoleService {

	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	public void setSysUserRoleMapper(
			SysUserRoleMapper sysUserRoleMapper) {
		this.sysUserRoleMapper = sysUserRoleMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysUserRole
	 * @return  int 
	 */
	@Override
	public int add(SysUserRole sysUserRole) {
		sysUserRoleMapper.insertSelective(sysUserRole);
		return sysUserRole.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysUserRole
	 * @return  int 
	 */
	@Override
	public int update(SysUserRole sysUserRole) {
		return sysUserRoleMapper
				.updateByPrimaryKeySelective(sysUserRole);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysUserRoleMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUserRole 
	 */
	@Override
	public SysUserRole get(Integer id) {
		return sysUserRoleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysUserRole
	 * @return   List<SysUserRole>
	 */
	@Override
	public List<SysUserRole> getList(SysUserRole sysUserRole) {
	    return sysUserRoleMapper.selectList(sysUserRole);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysUserRole
	 * @return   int
	 */
	@Override
	public int getCount(SysUserRoleSearch sysUserRoleSearch){
		return  sysUserRoleMapper.selectCount(sysUserRoleSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserRole
	 * @return   List<SysUserRole>
	 */
	@Override
	public List<SysUserRole> getPaginatedList(
			SysUserRoleSearch sysUserRoleSearch) {
		return sysUserRoleMapper.selectPaginatedList(sysUserRoleSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
