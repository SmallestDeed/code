package com.nork.product.service;

import java.util.List;

import com.nork.product.model.UserProductCombinationCollect;
import com.nork.product.model.search.UserProductCombinationCollectSearch;

/**   
 * @Title: UserProductCombinationCollectService.java 
 * @Package com.nork.porduct.service
 * @Description:产品组合收藏-产品组合收藏Service
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 11:13:32
 * @version V1.0   
 */
public interface UserProductCombinationCollectService {
	/**
	 * 新增数据
	 *
	 * @param userProductCombinationCollect
	 * @return  int 
	 */
	public int add(UserProductCombinationCollect userProductCombinationCollect);

	/**
	 *    更新数据
	 *
	 * @param userProductCombinationCollect
	 * @return  int 
	 */
	public int update(UserProductCombinationCollect userProductCombinationCollect);

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
	 * @return  UserProductCombinationCollect 
	 */
	public UserProductCombinationCollect get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  userProductCombinationCollect
	 * @return   List<UserProductCombinationCollect>
	 */
	public List<UserProductCombinationCollect> getList(UserProductCombinationCollect userProductCombinationCollect);

	/**
	 *    获取数据数量
	 *
	 * @param  userProductCombinationCollect
	 * @return   int
	 */
	public int getCount(UserProductCombinationCollectSearch userProductCombinationCollectSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  userProductCombinationCollect
	 * @return   List<UserProductCombinationCollect>
	 */
	public List<UserProductCombinationCollect> getPaginatedList(
				UserProductCombinationCollectSearch userProductCombinationCollecttSearch);

	/**
	 * 其他
	 * 
	 */

}
