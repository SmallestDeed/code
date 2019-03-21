package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.system.dao.ResRenderVideoMapper;
import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.search.ResRenderVideoSearch;
import com.nork.system.service.ResRenderVideoService;

/**   
 * @Title: ResRenderVideoServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:渲染视频资源库-渲染视频资源表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 19:03:29
 * @version V1.0   
 */
@Service("resRenderVideoService")
public class ResRenderVideoServiceImpl implements ResRenderVideoService {

	private ResRenderVideoMapper resRenderVideoMapper;

	@Autowired
	public void setResRenderVideoMapper(
			ResRenderVideoMapper resRenderVideoMapper) {
		this.resRenderVideoMapper = resRenderVideoMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param resRenderVideo
	 * @return  int 
	 */
	@Override
	public int add(ResRenderVideo resRenderVideo) {
		resRenderVideoMapper.insertSelective(resRenderVideo);
		return resRenderVideo.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resRenderVideo
	 * @return  int 
	 */
	@Override
	public int update(ResRenderVideo resRenderVideo) {
		return resRenderVideoMapper
				.updateByPrimaryKeySelective(resRenderVideo);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resRenderVideoMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResRenderVideo 
	 */
	@Override
	public ResRenderVideo get(Integer id) {
		return resRenderVideoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resRenderVideo
	 * @return   List<ResRenderVideo>
	 */
	@Override
	public List<ResRenderVideo> getList(ResRenderVideo resRenderVideo) {
	    return resRenderVideoMapper.selectList(resRenderVideo);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resRenderVideo
	 * @return   int
	 */
	@Override
	public int getCount(ResRenderVideoSearch resRenderVideoSearch){
		return  resRenderVideoMapper.selectCount(resRenderVideoSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resRenderVideo
	 * @return   List<ResRenderVideo>
	 */
	@Override
	public List<ResRenderVideo> getPaginatedList(
			ResRenderVideoSearch resRenderVideoSearch) {
		return resRenderVideoMapper.selectPaginatedList(resRenderVideoSearch);
	}


	/**
	 * 其他
	 * 
	 */


	/**
	 * 根据封面图id（渲染任务id）查到视频资源
	 */
	@Override
	public ResRenderVideo selectBySysTaskPicId(Integer sysTaskPicId) {
		return resRenderVideoMapper.selectBySysTaskPicId(sysTaskPicId);
	}
}
