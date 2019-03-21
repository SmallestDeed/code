package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUser;
import com.nork.system.model.search.SysUserSearch;

/**
 * @Title: SysUserMapper.java
 * @Package com.nork.system.dao
 * @Description:系统-用户Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 * @version V1.0
 */
@Repository
@Transactional
public interface SysUserMapper {
	int insertSelective(SysUser record);

	int updateByPrimaryKeySelective(SysUser record);
	
	int updateFinanceById(SysUser user);

	int deleteByPrimaryKey(Integer id);
    /***
     * 获取系统用户
     * @param sysUser
     * @return
     */
	List<SysUser> getSysList();

	SysUser selectByPrimaryKey(Integer id);

	int selectCount(SysUserSearch sysUserSearch);

	List<SysUser> selectPaginatedList(SysUserSearch sysUserSearch);

	List<SysUser> selectList(SysUser sysUser);
	
	List<SysUser> selectWithAccount(SysUser sysUser);

	/**
	 * 分页查询用户详细信息
	 * 
	 * @param sysUserSearch
	 * @return
	 */
	List<SysUser> userDetailPaginatedList(SysUserSearch sysUserSearch);

	List<SysUser> findOneByLoginName(String loginName);

	List<String> getTableNames();

	/**
	 * 通过角色CODE查询用户
	 * @param roleCode
	 * @return
	 */
	public List<SysUser> getUserByRoleCode(String roleCode);
	List<Integer> getAllId();
	public void updateBalanceAmountByUserId(SysUser sysUser);
	
	/**
	 * 移动端续费
	 * @param sysUser
	 * @return
	 */
	void renew(SysUser sysUser);
}
