package com.nork.system.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysRoleMapper;
import com.nork.system.model.SysRole;
import com.nork.system.model.search.SysRoleSearch;
import com.nork.system.service.SysRoleService;

/**   
 * @Title: SysRoleServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-角色ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-26 17:30:08
 * @version V1.0   
 */
@Service("sysRoleService")
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	private SysRoleMapper sysRoleMapper;

	@Autowired
	public void setSysRoleMapper(
			SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysRole
	 * @return  int 
	 */
	@Override
	public int add(SysRole sysRole) {
		sysRoleMapper.insertSelective(sysRole);
		return sysRole.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysRole
	 * @return  int 
	 */
	@Override
	public int update(SysRole sysRole) {
		return sysRoleMapper
				.updateByPrimaryKeySelective(sysRole);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysRoleMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysRole 
	 */
	@Override
	public SysRole get(Integer id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysRole
	 * @return   List<SysRole>
	 */
	@Override
	public List<SysRole> getList(SysRole sysRole) {
	    return sysRoleMapper.selectList(sysRole);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysRole
	 * @return   int
	 */
	@Override
	public int getCount(SysRoleSearch sysRoleSearch){
		return  sysRoleMapper.selectCount(sysRoleSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysRole
	 * @return   List<SysRole>
	 */
	@Override
	public List<SysRole> getPaginatedList(
			SysRoleSearch sysRoleSearch) {
		return sysRoleMapper.selectPaginatedList(sysRoleSearch);
	}
	/**
	 * 通过userId 获取该用户的角色列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysRole> getListByUserId(int userId) {
		return sysRoleMapper.getListByUserId(userId);
	}

    @Override
    public int getRoleByCode(String roleCode) {
		if (StringUtils.isEmpty(roleCode)){
			return 0;
		}
		return sysRoleMapper.getRoleByCode(roleCode);
    }

	@Override
	public List<Long> getSysRoleGroupIdList(Integer id) {
		return sysRoleMapper.getSysRoleGroupIdList(id);
	}

	@Override
	public List<Long>  getRoleIdByGroupId(Long g) {
		return sysRoleMapper.getRoleIdByGroupId(g);
	}

	@Override
	public Set<String> getRolesByUserId(Integer userId) {
		// 参数验证 ->start
		if(userId == null) {
			return null;
		}
		// 参数验证 ->end
		return sysRoleMapper.getRolesByUserId(userId);
	}

	/**
	 * 其他
	 * 
	 */

}
