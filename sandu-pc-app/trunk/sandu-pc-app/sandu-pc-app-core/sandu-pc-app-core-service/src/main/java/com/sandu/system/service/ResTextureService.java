package com.sandu.system.service;

import com.sandu.system.model.ResTexture;

public interface ResTextureService {

	/**
	 * 新增数据
	 *
	 * @param resTexture
	 * @return  int
	 */
	int add(ResTexture resTexture);

	/**
	 *    更新数据
	 *
	 * @param resTexture
	 * @return  int
	 */
	int update(ResTexture resTexture);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int
	 */
	int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResTexture
	 */
	ResTexture get(Integer id);
	
}
