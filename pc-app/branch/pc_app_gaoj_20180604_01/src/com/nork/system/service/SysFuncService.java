package com.nork.system.service;

import java.util.List;

import com.nork.system.model.AppMenuModel;
import com.nork.system.model.SysFunc;
import com.nork.system.model.search.SysFuncSearch;

/**   
 * @Title: SysFuncService.java 
 * @Package com.nork.system.service
 * @Description:系统-功能菜单Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-28 10:10:35
 * @version V1.0   
 */
public interface SysFuncService {
	/**
	 * 新增数据
	 *
	 * @param sysFunc
	 * @return  int 
	 */
	public int add(SysFunc sysFunc);

	/**
	 *    更新数据
	 *
	 * @param sysFunc
	 * @return  int 
	 */
	public int update(SysFunc sysFunc);

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
	 * @return  SysFunc 
	 */
	public SysFunc get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysFunc
	 * @return   List<SysFunc>
	 */
	public List<SysFunc> getList(SysFunc sysFunc);

	/**
	 *    获取数据数量
	 *
	 * @param  sysFunc
	 * @return   int
	 */
	public int getCount(SysFuncSearch sysFuncSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysFunc
	 * @return   List<SysFunc>
	 */
	public List<SysFunc> getPaginatedList(
				SysFuncSearch sysFunctSearch);

	/**
	 * 获取用的菜单权限
	 * @param  userId
	 * @return   List<SysFunc>
	 */
	public List<SysFunc> getUserMenus(Integer userId);

	/**
	 * 获取app菜单
	 * @param userId
	 * @return
	 */
	public List<AppMenuModel> getAppMenu(int userId);
	/**
	 * 获取登录用户的U3D权限菜单
	 * @param userId
	 * @return
	 */
	public List<SysFunc> getUserU3DMenus(Integer userId);
	
	/**
	 * 根据编码查询菜单
	 * @param code
	 * @return
	 */
	public SysFunc getSysFuncByCode(String code);
}
