package com.nork.user.service;

import java.util.List;

import com.nork.user.model.SysUserRegisterInfo;
import com.nork.user.model.bo.UserRegisterInfoBo;
import com.nork.user.model.search.SysUserRegisterInfoSearch;

/**   
 * @Title: SysUserRegisterInfoService.java 
 * @Package com.nork.user.service
 * @Description:用户注册信息-用户注册信息Service
 * @createAuthor yanghuanzhi
 * @CreateDate 2017-08-07 16:53:22
 * @version V1.0   
 */
public interface SysUserRegisterInfoService {
	/**
	 * 新增数据
	 *
	 * @param sysUserRegisterInfo
	 * @return  int 
	 */
	public int add(SysUserRegisterInfo sysUserRegisterInfo);

	/**
	 *    更新数据
	 *
	 * @param sysUserRegisterInfo
	 * @return  int 
	 */
	public int update(SysUserRegisterInfo sysUserRegisterInfo);

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
	 * @return  SysUserRegisterInfo 
	 */
	public SysUserRegisterInfo get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysUserRegisterInfo
	 * @return   List<SysUserRegisterInfo>
	 */
	public List<SysUserRegisterInfo> getList(SysUserRegisterInfo sysUserRegisterInfo);

	/**
	 *    获取数据数量
	 *
	 * @param  sysUserRegisterInfo
	 * @return   int
	 */
	public int getCount(SysUserRegisterInfoSearch sysUserRegisterInfoSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserRegisterInfo
	 * @return   List<SysUserRegisterInfo>
	 */
	public List<SysUserRegisterInfo> getPaginatedList(
            SysUserRegisterInfoSearch sysUserRegisterInfotSearch);

	/**
	 * describe: 用户注册收集信息
	 * creat_user: yanghz
	 * creat_date: 2017年8月7日
	 * creat_time: 下午 02:50
	 *
	 * @param registerInfoBo
	 * */
	int addRegisterInfo(UserRegisterInfoBo registerInfoBo);

	/**
	 * 其他
	 * 
	 */

}
