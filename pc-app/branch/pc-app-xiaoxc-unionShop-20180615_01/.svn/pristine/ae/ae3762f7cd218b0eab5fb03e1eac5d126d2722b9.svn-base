package com.nork.home.service;

import java.util.List;

import com.nork.home.model.BaseHouseApply;
import com.nork.home.model.search.BaseHouseApplySearch;
import com.nork.system.model.SysUser;

/**   
 * @Title: BaseHouseApplyService.java 
 * @Package com.nork.home.service
 * @Description:户型房型-户型申请表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-10-13 11:45:31
 * @version V1.0   
 */
public interface BaseHouseApplyService {
	/**
	 * 新增数据
	 *
	 * @param baseHouseApply
	 * @return  int 
	 */
	public int add(BaseHouseApply baseHouseApply);

	/**
	 *    更新数据
	 *
	 * @param baseHouseApply
	 * @return  int 
	 */
	public int update(BaseHouseApply baseHouseApply);

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
	 * @return  BaseHouseApply 
	 */
	public BaseHouseApply get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  baseHouseApply
	 * @return   List<BaseHouseApply>
	 */
	public List<BaseHouseApply> getList(BaseHouseApply baseHouseApply);

	/**
	 *    获取数据数量
	 *
	 * @param  baseHouseApply
	 * @return   int
	 */
	public int getCount(BaseHouseApplySearch baseHouseApplySearch);

	/**
	 *    分页获取数据
	 *
	 * @param  baseHouseApply
	 * @return   List<BaseHouseApply>
	 */
	public List<BaseHouseApply> getPaginatedList(
				BaseHouseApplySearch baseHouseApplytSearch);

	public void sysSave(BaseHouseApply baseHouseApply, SysUser sysUser);

}
