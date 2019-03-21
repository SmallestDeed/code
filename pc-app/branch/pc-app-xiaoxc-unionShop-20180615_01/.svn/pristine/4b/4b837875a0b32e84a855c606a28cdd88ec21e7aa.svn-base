package com.nork.product.service;

import java.util.List;

import com.nork.product.model.UserProductConfig;
import com.nork.product.model.search.UserProductConfigSearch;

/**   
 * @Title: UserProductConfigService.java 
 * @Package com.nork.product.service
 * @Description:用户产品配置-用户产品配置Service
 * @createAuthor pandajun 
 * @CreateDate 2016-03-28 14:40:39
 * @version V1.0   
 */
public interface UserProductConfigService {
	/**
	 * 新增数据
	 *
	 * @param userProductConfig
	 * @return  int 
	 */
	public int add(UserProductConfig userProductConfig);

	/**
	 *    更新数据
	 *
	 * @param userProductConfig
	 * @return  int 
	 */
	public int update(UserProductConfig userProductConfig);

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
	 * @return  UserProductConfig 
	 */
	public UserProductConfig get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  userProductConfig
	 * @return   List<UserProductConfig>
	 */
	public List<UserProductConfig> getList(UserProductConfig userProductConfig);

	/**
	 *    获取数据数量
	 *
	 * @param  userProductConfig
	 * @return   int
	 */
	public int getCount(UserProductConfigSearch userProductConfigSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  userProductConfig
	 * @return   List<UserProductConfig>
	 */
	public List<UserProductConfig> getPaginatedList(
				UserProductConfigSearch userProductConfigtSearch);

	/**
	 * 其他
	 * 
	 */
	
	/**
	 *  根据用户id查询数据
	 * 
	 * @param  userProductConfig
	 * @return   List<UserProductConfig>
	 */
	public List<UserProductConfig> getUserConfigList(UserProductConfig userProductConfig);

}
