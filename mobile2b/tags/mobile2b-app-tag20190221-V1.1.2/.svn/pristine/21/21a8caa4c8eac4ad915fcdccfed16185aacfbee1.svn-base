package com.nork.render.service;

import java.util.List;

import com.nork.render.model.RenderHost;
import com.nork.render.model.search.RenderHostSearch;

/**   
 * @Title: RenderHostService.java 
 * @Package com.nork.render.service
 * @Description:渲染-渲染主机Service
 * @createAuthor pandajun 
 * @CreateDate 2017-01-15 17:45:34
 * @version V1.0   
 */
public interface RenderHostService {
	/**
	 * 新增数据
	 *
	 * @param renderHost
	 * @return  int 
	 */
	public int add(RenderHost renderHost);

	/**
	 *    更新数据
	 *
	 * @param renderHost
	 * @return  int 
	 */
	public int update(RenderHost renderHost);

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
	 * @return  RenderHost 
	 */
	public RenderHost get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  renderHost
	 * @return   List<RenderHost>
	 */
	public List<RenderHost> getList(RenderHost renderHost);

	/**
	 *    获取数据数量
	 *
	 * @param  renderHost
	 * @return   int
	 */
	public int getCount(RenderHostSearch renderHostSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  renderHost
	 * @return   List<RenderHost>
	 */
	public List<RenderHost> getPaginatedList(
				RenderHostSearch renderHosttSearch);

	/**
	 * 
	* @Title: allocationRenderHost 
	* @Description: 主机分配
	* @param 
	* @return RenderHost    返回类型 
	* @throws
	 */
	public RenderHost allocationRenderHost();
	

}
