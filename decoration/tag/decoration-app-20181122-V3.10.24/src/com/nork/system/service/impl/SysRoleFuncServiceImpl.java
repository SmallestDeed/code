package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysRoleFuncMapper;
import com.nork.system.model.SysRoleFunc;
import com.nork.system.model.search.SysRoleFuncSearch;
import com.nork.system.service.SysRoleFuncService;

/**   
 * @Title: SysRoleFuncServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-角色菜单管理ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-29 16:46:10
 * @version V1.0   
 */
@Service("sysRoleFuncService")
@Transactional
public class SysRoleFuncServiceImpl implements SysRoleFuncService {

	private SysRoleFuncMapper sysRoleFuncMapper;

	@Autowired
	public void setSysRoleFuncMapper(
			SysRoleFuncMapper sysRoleFuncMapper) {
		this.sysRoleFuncMapper = sysRoleFuncMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysRoleFunc
	 * @return  int 
	 */
	@Override
	public int add(SysRoleFunc sysRoleFunc) {
		sysRoleFuncMapper.insertSelective(sysRoleFunc);
		return sysRoleFunc.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysRoleFunc
	 * @return  int 
	 */
	@Override
	public int update(SysRoleFunc sysRoleFunc) {
		return sysRoleFuncMapper
				.updateByPrimaryKeySelective(sysRoleFunc);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysRoleFuncMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysRoleFunc 
	 */
	@Override
	public SysRoleFunc get(Integer id) {
		return sysRoleFuncMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysRoleFunc
	 * @return   List<SysRoleFunc>
	 */
	@Override
	public List<SysRoleFunc> getList(SysRoleFunc sysRoleFunc) {
	    return sysRoleFuncMapper.selectList(sysRoleFunc);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysRoleFunc
	 * @return   int
	 */
	@Override
	public int getCount(SysRoleFuncSearch sysRoleFuncSearch){
		return  sysRoleFuncMapper.selectCount(sysRoleFuncSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysRoleFunc
	 * @return   List<SysRoleFunc>
	 */
	@Override
	public List<SysRoleFunc> getPaginatedList(
			SysRoleFuncSearch sysRoleFuncSearch) {
		return sysRoleFuncMapper.selectPaginatedList(sysRoleFuncSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
