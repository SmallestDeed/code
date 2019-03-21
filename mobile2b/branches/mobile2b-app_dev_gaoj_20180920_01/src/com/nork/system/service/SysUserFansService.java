package com.nork.system.service;

import java.util.List;

import com.nork.system.model.SysUserFans;
import com.nork.system.model.search.SysUserFansSearch;

/**   
 * @Title: SysUserFansService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-用户粉丝表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:34:55
 * @version V1.0   
 */
public interface SysUserFansService {
	/**
	 * 新增数据
	 *
	 * @param sysUserFans
	 * @return  int 
	 */
	public int add(SysUserFans sysUserFans);

	/**
	 *    更新数据
	 *
	 * @param sysUserFans
	 * @return  int 
	 */
	public int update(SysUserFans sysUserFans);

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
	 * @return  SysUserFans 
	 */
	public SysUserFans get(Integer id);
	
	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUserFans 
	 */
	public SysUserFans getObject(SysUserFans sysUserFans);

	/**
	 * 所有数据
	 * 
	 * @param  sysUserFans
	 * @return   List<SysUserFans>
	 */
	public List<SysUserFans> getList(SysUserFans sysUserFans);

	/**
	 *    获取数据数量
	 *
	 * @param  sysUserFans
	 * @return   int
	 */
	public int getCount(SysUserFansSearch sysUserFansSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserFans
	 * @return   List<SysUserFans>
	 */
	public List<SysUserFans> getPaginatedList(
				SysUserFansSearch sysUserFanstSearch);

	/**
	 * 其他
	 * 
	 */
	
	/**
	 *    获取粉丝数量
	 *
	 * @param  sysUserFans
	 * @return   int
	 */
	public int getFansCount(SysUserFans sysUserFans);

}
