/**
 * 
 */
package com.nork.product.service;

import java.util.List;
import java.util.Map;

import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;

/**
 * 产品组相关服务类 v2 主要用于特定业务逻辑的重构
 * @author louxinhua 菜刀
 * @since 2016-12-23
 */
public interface GroupProductServiceV2 {
	
	
	/**
	 * 产品组合总数
	 * @param search
	 * @return
	 */
	public Integer getGroupCountByProduct(GroupProductSearch search);
	
	/**
	 * 产品组列表， 返回包含了 location、picpath、code、name
	 * @param search
	 * @return
	 */
	public List<SearchProductGroupResult> getGroupListByProduct(GroupProductSearch search, Integer userID, Integer structureId, Integer usertype, String versionType);
	
	/**
	 * 查询用户在groupIDList中收藏了哪些产品组
	 * @param userID
	 * @param groupIDList
	 * @return
	 */
	public List<Map<String, Integer>> getUserCollectedProductGroupByIDS(Integer userID, List<Integer> groupIDList);
	
	
	
	/**
	 * 返回产品组里面的产品相信信息，比如长宽高、产品大小类型等等
	 * @param groupIDList
	 * @return
	 */
	public Map<Integer, List<SearchProductGroupDetail>> getGroupProductDetailsByGroupIDList(List<SearchProductGroupResult> groupList,
			String mediaType, String brandIds, Integer spaceCommonId);
	
	/**
	 * 根据条件配置GroupProductSearch
	 * @param groupSearch
	 * @param productTypeValue
	 * @param smallTypeValue
	 * @return
	 */
	public GroupProductSearch configSearchFor(GroupProductSearch groupSearch, String  productTypeValue, String  smallTypeValue);
	
	public Map<Integer, List<SearchProductGroupDetail>> getGroupProductDetailsByGroupIDReplaceList(List<SearchProductGroupResult> groupList,
			String mediaType,  Integer spaceCommonId);
	
}
