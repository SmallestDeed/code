package com.sandu.api.solution.service;

import com.sandu.api.solution.model.DesignRenderRoam;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 17:36 2018/5/17
 */
public interface DesignRenderRoamService {

	/**
	 * 新增数据
	 * @param designRenderRoam
	 * @return  int 
	 */
	public int add(DesignRenderRoam designRenderRoam);
	
	/**
	 * 根据主键获取id信息 
	 * @param id
	 * @return
	 */
	public DesignRenderRoam selectByScreenShotId(Integer id);
	
}
