package com.nork.system.service;

import java.util.List;
import java.util.Map;

import com.nork.system.model.SysVersion;
import com.nork.system.model.search.SysVersionSearch;

/**   
 * @Title: SysVersionService.java 
 * @Package com.nork.system.service
 * @Description:系统模块-版本管理Service
 * @createAuthor pandajun 
 * @CreateDate 2016-05-05 14:18:01
 * @version V1.0   
 */
public interface SysVersionService {
	/**
	 * 新增数据
	 *
	 * @param sysVersion
	 * @return  int 
	 */
	public int add(SysVersion sysVersion);

	/**
	 *    更新数据
	 *
	 * @param sysVersion
	 * @return  int 
	 */
	public int update(SysVersion sysVersion);

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
	 * @return  SysVersion 
	 */
	public SysVersion get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  sysVersion
	 * @return   List<SysVersion>
	 */
	public List<SysVersion> getList(SysVersion sysVersion);

	/**
	 *    获取数据数量
	 *
	 * @param  sysVersion
	 * @return   int
	 */
	public int getCount(SysVersionSearch sysVersionSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  sysVersion
	 * @return   List<SysVersion>
	 */
	public List<SysVersion> getPaginatedList(
				SysVersionSearch sysVersiontSearch);

	/**
	 * 查找所有版本号名称(用于下拉菜单)
	 * @author huangsongbo
	 * @return
	 */
	public List<String> findAllVersionName();

	/**
	 * 得到最新版本信息
	 * @author huangsongbo
	 * @param systemType 平台类型
	 * @return
	 */
	public SysVersion getLatestVersion(Integer systemType,String verType);

	/**
	 * versionCheck接口验证参数
	 * @author huangsongbo
	 * @param msgId
	 * @param systemType
	 * @return
	 */
	public Map<String, String> versionCheckVerifyParams(String msgId, Integer systemType);

	/**
	 * 验证版本名唯一性
	 * @author huangsongbo
	 * @param version
	 * @param id
	 * @return
	 */
	public boolean verifyVersionName(String version, Integer systemType, Integer id);

	/**
	 * 回填附件信息(businessId)
	 * @author huangsongbo
	 * @param sysVersion
	 */
	public void setFileAttribute(SysVersion sysVersion);

	/**
	 * 得到版本下载地址
	 * @author huangsongbo
	 * @param path 
	 * @return
	 */
	public String getAppPathInfo(String path);

}
