package com.sandu.sys.service;

import java.util.List;

import com.sandu.sys.model.SysDictionary;
import com.sandu.sys.model.vo.SysDictionaryVo;
import io.swagger.models.auth.In;

/***
 * 数据字典服务接口
 * @author Administrator
 *
 */
public interface SysDictionaryService {
	
	/***
	 * 根据类型获取所有的数据字典项
	 * @param type
	 * @return
	 */
	List<SysDictionaryVo> getListWithType(String type);

	/**
	 * 根据类型和values获取数据字典项
	 * @param type
	 * @param values
	 * @return
	 */
	List<SysDictionaryVo> getDictionoryByTypeOrValues(String type, String values);

	/**
	 * 根据类型和value获取数据字典项
	 * @param type
	 * @param value
	 * @return
	 */
	SysDictionaryVo getDictionoryByTypeOrValue(String type, Integer value);

	/**
	 * 通过数据字典类型获取数据字典列表
	 * @param type 数据字典类型
	 * @return list
	 */
	List<SysDictionary> getListByType(String type, Integer start, Integer pageSize);

}
