package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.system.dao.SysAuditorUserMapper;
import com.nork.system.model.SysAuditorUser;
import com.nork.system.model.search.SysAuditorUserSearch;
import com.nork.system.service.SysAuditorUserService;

/**   
 * @Title: SysAuditorUserServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-审核人员和被审核人员绑定表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-12-25 17:34:30
 * @version V1.0   
 */
@Service("sysAuditorUserService")
public class SysAuditorUserServiceImpl implements SysAuditorUserService {

	private SysAuditorUserMapper sysAuditorUserMapper;

	@Autowired
	public void setSysAuditorUserMapper(
			SysAuditorUserMapper sysAuditorUserMapper) {
		this.sysAuditorUserMapper = sysAuditorUserMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysAuditorUser
	 * @return  int 
	 */
	@Override
	public int add(SysAuditorUser sysAuditorUser) {
		sysAuditorUserMapper.insertSelective(sysAuditorUser);
		return sysAuditorUser.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysAuditorUser
	 * @return  int 
	 */
	@Override
	public int update(SysAuditorUser sysAuditorUser) {
		return sysAuditorUserMapper
				.updateByPrimaryKeySelective(sysAuditorUser);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysAuditorUserMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysAuditorUser 
	 */
	@Override
	public SysAuditorUser get(Integer id) {
		return sysAuditorUserMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysAuditorUser
	 * @return   List<SysAuditorUser>
	 */
	@Override
	public List<SysAuditorUser> getList(SysAuditorUser sysAuditorUser) {
	    return sysAuditorUserMapper.selectList(sysAuditorUser);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysAuditorUser
	 * @return   int
	 */
	@Override
	public int getCount(SysAuditorUserSearch sysAuditorUserSearch){
		return  sysAuditorUserMapper.selectCount(sysAuditorUserSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysAuditorUser
	 * @return   List<SysAuditorUser>
	 */
	@Override
	public List<SysAuditorUser> getPaginatedList(
			SysAuditorUserSearch sysAuditorUserSearch) {
		return sysAuditorUserMapper.selectPaginatedList(sysAuditorUserSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
