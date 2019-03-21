package com.nork.product.service2;

import java.util.List;
import java.util.Map;

import com.nork.product.controller2.web.StructureProductParameter;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.UserProductsConllect;
import com.nork.product.model.search.UserProductCollectSearch;

/**   
 * @Title: UserProductCollectService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-我的产品收藏Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 18:27:21
 * @version V1.0   
 */
public interface UserProductCollectServiceV2 {
	/**
	 * 新增数据
	 *
	 * @param userProductCollect
	 * @return  int 
	 */
	public int add(UserProductCollect userProductCollect);

	/**
	 *    更新数据
	 *
	 * @param userProductCollect
	 * @return  int 
	 */
	public int update(UserProductCollect userProductCollect);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);
	/**
	 *    取消收藏
	 *
	 * @param id
	 * @return  int 
	 */
	public Integer cancelConllect(UserProductCollect userProductCollect);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserProductCollect 
	 */
	public UserProductCollect get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  userProductCollect
	 * @return   List<UserProductCollect>
	 */
	public List<UserProductCollect> getList(UserProductCollect userProductCollect);

	/**
	 *    获取数据数量
	 *
	 * @param  userProductCollect
	 * @return   int
	 */
	public int getCount(UserProductCollectSearch userProductCollectSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  userProductCollect
	 * @return   List<UserProductCollect>
	 */
	public List<UserProductCollect> getPaginatedList(
				UserProductCollectSearch userProductCollectSearch);

	/**
	 * 其他
	 * 
	 */
	public UserProductCollect getUserProductConllect (UserProductCollect userProductCollect);

	public List<UserProductsConllect> getUserProductsConllectList(UserProductsConllect userProductsConllect);
	
	public List<UserProductsConllect> getAllList(UserProductsConllect userProductsConllect);
	
	public int getUserProductsConllectCount(UserProductsConllect userProductsConllect);
	
	public List<UserProductsConllect> selectUserNamelist(UserProductsConllect userProductsConllect);
	
	
	/**
	 * (转移方法)  通过用户id 和 收藏夹Id，将将收藏夹ID  改为 默认Id  
	 * @param userProductCollectSearch
	 */
	public void transferCollection(UserProductCollectSearch userProductCollectSearch);

	
	/**
	 * (转移方法)  通过大小value  查询  该用户收藏的产品
	 * @param getUserProductsCollectByValue
	 */
	public List getUserProductsCollectByValue(List<Map<Object, Object>> valueList);

	public int getUserProductsConllectCount_All(UserProductsConllect userProductsCollect);

	public List<UserProductsConllect> getAllList_All(UserProductsConllect userProductsCollect);
	
	/**
	 * 批量删除
	 * */
	public int deleteBatch(List<Integer> list);
	
	public StructureProductParameter collectionList(UserProductsConllect userProductsCollect,StructureProductParameter sParameter);
	
	public StructureProductParameter jsplist(UserProductCollectSearch userProductCollectSearch,StructureProductParameter sParameter);
}
