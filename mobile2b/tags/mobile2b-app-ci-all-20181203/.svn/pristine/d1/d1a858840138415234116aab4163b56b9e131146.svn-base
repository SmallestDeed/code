package com.nork.system.service;

import java.util.List;

import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.search.ResRenderVideoSearch;

/**   
 * @Title: ResRenderVideoService.java 
 * @Package com.nork.system.service
 * @Description:渲染视频资源库-渲染视频资源表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 19:03:29
 * @version V1.0   
 */
public interface ResRenderVideoService {
	/**
	 * 新增数据
	 *
	 * @param resRenderVideo
	 * @return  int 
	 */
	public int add(ResRenderVideo resRenderVideo);

	/**
	 *    更新数据
	 *
	 * @param resRenderVideo
	 * @return  int 
	 */
	public int update(ResRenderVideo resRenderVideo);

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
	 * @return  ResRenderVideo 
	 */
	public ResRenderVideo get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resRenderVideo
	 * @return   List<ResRenderVideo>
	 */
	public List<ResRenderVideo> getList(ResRenderVideo resRenderVideo);

	/**
	 *    获取数据数量
	 *
	 * @param  resRenderVideo
	 * @return   int
	 */
	public int getCount(ResRenderVideoSearch resRenderVideoSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resRenderVideo
	 * @return   List<ResRenderVideo>
	 */
	public List<ResRenderVideo> getPaginatedList(
				ResRenderVideoSearch resRenderVideotSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据封面图id（渲染任务id）查到视频资源
	 * @param sysTaskPicId
	 * @return
	 */
	public ResRenderVideo selectBySysTaskPicId(Integer sysTaskPicId);
}
