package com.sandu.system.service;

import com.sandu.system.model.ResPic;

public interface ResPicService {

	/**
	 * 新增数据
	 *
	 * @param resPic
	 * @return  int
	 */
	int add(ResPic resPic);

	/**
	 *    更新数据
	 *
	 * @param resPic
	 * @return  int
	 */
	int update(ResPic resPic);

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
	 * @return  ResPic 
	 */
	ResPic get(Integer id);

}
