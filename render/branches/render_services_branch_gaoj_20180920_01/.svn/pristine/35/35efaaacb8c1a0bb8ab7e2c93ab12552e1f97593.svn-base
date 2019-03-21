package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysGroupUserMapper;
import com.nork.system.model.SysGroupUser;
import com.nork.system.model.search.SysGroupUserSearch;
import com.nork.system.service.SysGroupUserService;

/**   
 * @Title: SysGroupUserServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-用户组用户表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-18 18:35:04
 * @version V1.0   
 */
@Service("sysGroupUserService")
@Transactional
public class SysGroupUserServiceImpl implements SysGroupUserService {

	private SysGroupUserMapper sysGroupUserMapper;

	@Autowired
	public void setSysGroupUserMapper(
			SysGroupUserMapper sysGroupUserMapper) {
		this.sysGroupUserMapper = sysGroupUserMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysGroupUser
	 * @return  int 
	 */
	@Override
	public int add(SysGroupUser sysGroupUser) {
		sysGroupUserMapper.insertSelective(sysGroupUser);
		return sysGroupUser.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysGroupUser
	 * @return  int 
	 */
	@Override
	public int update(SysGroupUser sysGroupUser) {
		return sysGroupUserMapper
				.updateByPrimaryKeySelective(sysGroupUser);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysGroupUserMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysGroupUser 
	 */
	@Override
	public SysGroupUser get(Integer id) {
		return sysGroupUserMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysGroupUser
	 * @return   List<SysGroupUser>
	 */
	@Override
	public List<SysGroupUser> getList(SysGroupUser sysGroupUser) {
	    return sysGroupUserMapper.selectList(sysGroupUser);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysGroupUser
	 * @return   int
	 */
	@Override
	public int getCount(SysGroupUserSearch sysGroupUserSearch){
		return  sysGroupUserMapper.selectCount(sysGroupUserSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysGroupUser
	 * @return   List<SysGroupUser>
	 */
	@Override
	public List<SysGroupUser> getPaginatedList(
			SysGroupUserSearch sysGroupUserSearch) {
		return sysGroupUserMapper.selectPaginatedList(sysGroupUserSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
