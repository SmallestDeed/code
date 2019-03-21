package com.nork.onekeydesign.service;

import com.nork.onekeydesign.model.DesignRenderRoam;
import com.nork.onekeydesign.model.search.DesignRenderRoamSearch;

import java.util.List;

/**   
 * @Title: DesignRenderRoamService.java 
 * @Package com.nork.render.service
 * @Description:渲染漫游-720漫游Service
 * @createAuthor pandajun 
 * @CreateDate 2017-07-13 17:41:16
 * @version V1.0   
 */
public interface DesignRenderRoamService {
	  public int countByScreenShotId(int screenShotId);
	/**
	 * 新增数据
	 *
	 * @param designRenderRoam
	 * @return  int 
	 */
	public int add(DesignRenderRoam designRenderRoam);

	/**
	 *    更新数据
	 *
	 * @param designRenderRoam
	 * @return  int 
	 */
	public int update(DesignRenderRoam designRenderRoam);

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
	 * @return  DesignRenderRoam 
	 */
	public DesignRenderRoam get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designRenderRoam
	 * @return   List<DesignRenderRoam>
	 */
	public List<DesignRenderRoam> getList(DesignRenderRoam designRenderRoam);

	/**
	 *    获取数据数量
	 *
	 * @param  designRenderRoamSearch
	 * @return   int
	 */
	public int getCount(DesignRenderRoamSearch designRenderRoamSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designRenderRoamtSearch
	 * @return   List<DesignRenderRoam>
	 */
	public List<DesignRenderRoam> getPaginatedList(
            DesignRenderRoamSearch designRenderRoamtSearch);

	/**
	 * 其他
	 * 
	 */


	/**
	 * 通过UUID查询一个漫游
	 * @param uuid
	 * @return
	 */
	DesignRenderRoam selectByUUID(String uuid);

	/**
	 * 通过截图ID查询一个720漫游
	 * @param screenId
	 * @return
	 */
	DesignRenderRoam selectByScreenId(Integer screenId);
}
