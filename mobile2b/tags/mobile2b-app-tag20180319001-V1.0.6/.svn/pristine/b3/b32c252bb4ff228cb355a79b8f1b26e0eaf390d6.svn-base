package com.nork.mgr.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.mgr.model.MgrRechargeLog;
import com.nork.mgr.model.search.MgrRechargeLogSearch;
import com.nork.system.model.SysUser;

/**   
 * @Title: MgrRechargeLogService.java 
 * @Package com.nork.mgr.service
 * @Description:日常工作-充值记录Service
 * @createAuthor pandajun 
 * @CreateDate 2017-03-26 05:15:26
 * @version V1.0   
 */
public interface MgrRechargeLogService {
	/**
	 * 新增数据
	 *
	 * @param mgrRechargeLog
	 * @return  int 
	 */
	public int add(MgrRechargeLog mgrRechargeLog);

	/**
	 *    更新数据
	 *
	 * @param mgrRechargeLog
	 * @return  int 
	 */
	public int update(MgrRechargeLog mgrRechargeLog);

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
	 * @return  MgrRechargeLog 
	 */
	public MgrRechargeLog get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  mgrRechargeLog
	 * @return   List<MgrRechargeLog>
	 */
	public List<MgrRechargeLog> getList(MgrRechargeLog mgrRechargeLog);

	/**
	 *    获取数据数量
	 *
	 * @param  mgrRechargeLog
	 * @return   int
	 */
	public int getCount(MgrRechargeLogSearch mgrRechargeLogSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  mgrRechargeLog
	 * @return   List<MgrRechargeLog>
	 */
	public List<MgrRechargeLog> getPaginatedList(
				MgrRechargeLogSearch mgrRechargeLogtSearch);

	/**
	 * 条件搜索,得到更多信息
	 * @author huangsongbo
	 * @param mgrRechargeLogSearch
	 * @return
	 */
	public List<MgrRechargeLog> getMoreInfoBySearch(MgrRechargeLogSearch mgrRechargeLogSearch);

	/**
	 * 保存充值日志,并且更新用户余额
	 * @author huangsongbo
	 * @param mgrRechargeLog
	 * @param sysUser
	 * @param rechargeAmount
	 * @param request
	 * @return
	 */
	public String saveLogAndUpdateUserBalance(MgrRechargeLog mgrRechargeLog, SysUser sysUser);

}
