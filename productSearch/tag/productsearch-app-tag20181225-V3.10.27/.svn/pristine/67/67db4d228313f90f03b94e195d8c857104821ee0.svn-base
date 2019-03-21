package com.nork.product.service;

import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductDetails;
import com.nork.product.model.GroupProductResult;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.GroupProductSearch;

/**   
 * @Title: GroupProductService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品组合主表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
public interface GroupProductService {
	/**
	 * 新增数据
	 *
	 * @param groupProduct
	 * @return  int 
	 */
	public int add(GroupProduct groupProduct);

	/**
	 *    更新数据
	 *
	 * @param groupProduct
	 * @return  int 
	 */
	public int update(GroupProduct groupProduct);

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
	 * @return  GroupProduct 
	 */
	public GroupProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  groupProduct
	 * @return   List<GroupProduct>
	 */
	public List<GroupProduct> getList(GroupProduct groupProduct);
	
	/**
	 * @Description 获取组合列表 </p>
	 * @param groupProduct
	 * @return
	 * @throws Exception
	 */
	List<GroupProductResult> selectCommonList(GroupProduct groupProduct)throws Exception;

	/**
	 *    获取数据数量
	 *
	 * @param  groupProduct
	 * @return   int
	 */
	public int getCount(GroupProductSearch groupProductSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  groupProduct
	 * @return   List<GroupProduct>
	 */
	public List<GroupProduct> getPaginatedList(
				GroupProductSearch groupProducttSearch);

	/**
	 * 其他
	 * 
	 */

	int getGroupCountByProduct(GroupProductSearch groupSearch);

	List<GroupProduct> getGroupListByProduct(GroupProductSearch productSearch);
	
	
	/**
	 * 根据品牌查询组合（组合中包括关联产品）
	 *
	 * @param brandName
	 * @return  GroupProduct 
	 */
	public List<GroupProductDetails> queryBrandGroupList(BaseBrandSearch ResBrand);

	public int queryBrandGroupCount(BaseBrandSearch ResBrand);

	/**
	 * 得到组合的价格->组合表有价格信息就取价格信息,没有的话就把该组合的产品价格相加
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	public Double getSalePrice(Integer groupId);

//	public int getGroupCountByStructureId(GroupProductSearch groupSearch);
//
//	public List<GroupProduct> getGroupListByStructureId(GroupProductSearch groupSearch);

	public int getGroupCountByStructureId(Integer structureId);

	public List<GroupProduct> getGroupListByStructureId(Integer structureId);

	public GroupProduct getStructureByGroupId(Integer groupId);

	/**
	 * 判断该产品是不是否是主产品
	 * @param cacheEnable 
	 * @param productId 产品id
	 * @param cacheEnable 是否开启缓存
	 * @return
	 */
	public Integer getIsMainProduct(Integer productId, String cacheEnable);

	public Integer getIsMainProductV2(Integer id);
	
	/**
	 * 根据结构id和组合状态查找结构组合数量
	 * @author huangsongbo
	 * @param structureId
	 * @param usertype  用户类型   add by yanghz(根据用户类型查询不同状态数据)
	 * @return
	 */
	public int getGroupCountByStructureIdAndStatus(Integer structureId, Integer usertype,String [] brandIdList,String versionType, Integer groupType);

	/**
	 * 通过id查找组合(关联查找更多信息,比如:品牌名,风格名)
	 * @author huangsongbo
	 * @param groupId
	 * @return
	 */
	public GroupProduct getMoreInfoById(Integer groupId);

	/**
	 * 批量修改组合状态
	 * @author huangsongbo
	 * @param oldStatus
	 * @param newStatus
	 */
	public void updateStatus(Integer oldStatus, Integer newStatus);

	public SearchProductGroupResult productSelectGroupReplace(BaseProduct baseProduct, StructureProduct structureProduct, Integer productIndex,Integer spaceCommonId,String mediaType,Integer userType);

	public Integer getGroupType(Map<Integer, Integer> groupTypeMap, Integer productGroupId);
	
	/**
	 * 方案已使用组合列表
	 * @author xiaoxc
	 * @param designPlanId
	 * @param start
	 * @param limit
	 * @param mediaType
	 * @return
	 */
	public ResponseEnvelope<SearchProductGroupResult> getPlanGroupList(Integer designPlanId, Integer start, Integer limit, LoginUser loginUser, String mediaType, String msgId, Integer spaceCommonId, Integer groupId);

	/**
	 * 获取组合信息(groupInfoMap起一个缓存的作用)
	 * 
	 * @author huangsongbo
	 * @param groupInfoMap 起缓存的作用
	 * @param groupId 组合id
	 * @return
	 */
	public GroupProduct getGroupProductByCache(Map<Integer, GroupProduct> groupInfoMap, Integer groupId);

}
