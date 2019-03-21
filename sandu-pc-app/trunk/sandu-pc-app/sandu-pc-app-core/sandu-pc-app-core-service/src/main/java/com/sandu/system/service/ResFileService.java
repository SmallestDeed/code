package com.sandu.system.service;

import com.sandu.system.model.ResFile;

public interface ResFileService {

	/**
	 * 新增数据
	 *
	 * @param resFile
	 * @return  int
	 */
	int add(ResFile resFile);

	/**
	 *    更新数据
	 *
	 * @param resFile
	 * @return  int
	 */
	int update(ResFile resFile);

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
	 * @return  ResFile
	 */
	ResFile get(Integer id);
	
}
