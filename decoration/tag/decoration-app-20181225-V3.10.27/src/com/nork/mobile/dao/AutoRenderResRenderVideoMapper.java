package com.nork.mobile.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.mobile.model.search.AutoRenderResRenderVideo;
/**
 * 
 * @author yangzhun
 *
 */
@Repository
@Transactional
public interface AutoRenderResRenderVideoMapper {
	
	/**
	 * 查询所有自动渲染得到的漫游视频
	 * @param autoRenderResRenderVideo
	 * @return
	 */
	public List<AutoRenderResRenderVideo> selectList(AutoRenderResRenderVideo autoRenderResRenderVideo);

}
