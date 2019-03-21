package com.nork.system.service;

import com.nork.system.model.BasePlatform;

import java.util.List;
import java.util.Map;


/**
 * @Title: BasePlatformService.java 
 * @Package com.nork.platform.service
 * @Description:基础-平台表Service
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
public interface BasePlatformService {
	/**
	 * 新增数据
	 *
	 * @param basePlatform
	 * @return  int 
	 */
	public int add(BasePlatform basePlatform);

	/**
	 *    更新数据
	 *
	 * @param basePlatform
	 * @return  int 
	 */
	public int update(BasePlatform basePlatform);

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
	 * @return  BasePlatform 
	 */
	public BasePlatform get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  basePlatform
	 * @return   List<BasePlatform>
	 */
	public List<BasePlatform> getList(BasePlatform basePlatform);

	/**
	 * 根据编码获取平台所属类别
	 * @param platformCode
	 * @return
	 */
    BasePlatform getByPlatformCode(String platformCode);

	/**
	 * 将一组平台ID按业务类型分组
	 * @param usedPlatformIds
	 * @return
	 */
	Map<String,List> groupPlatformWithBussinessType(List<Integer> usedPlatformIds);

	public BasePlatform getBasePlatform(String bRAND_WEBSITE_PLATFORM);

	/**
	 * 通过平台所属分类获取平台id
	 * @param platformBussinessType
	 * @return
	 */
	List<Integer> getIdsbyBussinessType(String platformBussinessType);

	/**
	 * 其他
	 * 
	 */

}
