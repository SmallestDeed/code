package com.nork.system.service;

import java.util.List;
import java.util.Set;

import com.nork.system.model.SysRole;
import com.nork.system.model.search.SysRoleSearch;

/**   
 * @Title: SysRoleService.java 
 * @Package com.nork.system.service
 * @Description:系统-角色Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-26 17:30:08
 * @version V1.0   
 */
public interface SysRoleService {
	/**
	 * 新增数据
	 *
	 * @param sysRole
	 * @return  int 
	 */
	public int add(SysRole sysRole);

	/**
	 *    更新数据
	 *
	 * @param sysRole
	 * @return  int 
	 */
	public int update(SysRole sysRole);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysRole 
	 */
	public SysRole get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysRole
	 * @return   List<SysRole>
	 */
	public List<SysRole> getList(SysRole sysRole);

	/**
	 *    获取数据数量
	 *
	 * @param  sysRole
	 * @return   int
	 */
	public int getCount(SysRoleSearch sysRoleSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysRole
	 * @return   List<SysRole>
	 */
	public List<SysRole> getPaginatedList(
				SysRoleSearch sysRoletSearch);

	/**
	 * 通过userId 获取该用户的角色列表
	 * @param userId
	 * @return
	 */
	public List<SysRole> getListByUserId(int userId);

	/**
	 * 根据角色code获取角色id
	 * add by yanghz
	 * @param roleCode
	 * @return
	 */
    Integer getRoleByCode(String roleCode);

    List<Long> getSysRoleGroupIdList(Integer id);

	List<Long>  getRoleIdByGroupId(Long g);

	public Set<String> getRolesByUserId(Integer userId);

	/**
	 * 其他
	 * 
	 */

}
