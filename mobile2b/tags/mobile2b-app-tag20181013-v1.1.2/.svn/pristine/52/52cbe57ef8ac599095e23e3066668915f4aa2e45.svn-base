package com.nork.design.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.design.model.TempletProductIndexInfo;
import com.nork.design.model.search.TempletProductIndexInfoSearch;

/**   
 * @Title: TempletProductIndexInfoService.java 
 * @Package com.nork.design.service
 * @Description:设计模块-同分类产品序号表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-06-13 16:19:25
 * @version V1.0   
 */
public interface TempletProductIndexInfoService {
	/**
	 * 新增数据
	 *
	 * @param templetProductIndexInfo
	 * @return  int 
	 */
	public int add(TempletProductIndexInfo templetProductIndexInfo);

	/**
	 *    更新数据
	 *
	 * @param templetProductIndexInfo
	 * @return  int 
	 */
	public int update(TempletProductIndexInfo templetProductIndexInfo);

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
	 * @return  TempletProductIndexInfo 
	 */
	public TempletProductIndexInfo get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  templetProductIndexInfo
	 * @return   List<TempletProductIndexInfo>
	 */
	public List<TempletProductIndexInfo> getList(TempletProductIndexInfo templetProductIndexInfo);

	/**
	 *    获取数据数量
	 *
	 * @param  templetProductIndexInfo
	 * @return   int
	 */
	public int getCount(TempletProductIndexInfoSearch templetProductIndexInfoSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  templetProductIndexInfo
	 * @return   List<TempletProductIndexInfo>
	 */
	public List<TempletProductIndexInfo> getPaginatedList(
				TempletProductIndexInfoSearch templetProductIndexInfotSearch);

	/**
	 * 通过样板房code获取productIndexInfo
	 * @param designCode
	 */
	public List<TempletProductIndexInfo> findIndexInfoByCode(String designCode);

	/**
	 * 自动保存系统字段值
	 * @param model
	 * @param loginUser
	 */
	public void sysSave(TempletProductIndexInfo model, LoginUser loginUser);

	/**
	 *    更新数据
	 *
	 * @param templetProductIndexInfo
	 * @return  int
	 */
	public int updateIndexInfoByIds(TempletProductIndexInfo templetProductIndexInfo);
}
