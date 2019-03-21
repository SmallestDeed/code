/**
 * 
 */
package com.nork.product.dao;

import java.util.List;
import java.util.Map;

import com.nork.product.model.result.SearchProductGroupInfoResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.GroupProductAllTypeModel;
import com.nork.product.model.ProductGroupBaseInfoModel;
import com.nork.product.model.ProductInfoInGroupModel;
import com.nork.product.model.search.GroupProductSearch;

/**
 * 
 * 产品组合 Dao
 * @author louxinhua
 *
 */
@Repository
@Transactional
public interface ProductGroupDao {
	
	
	/**
	 * 获取结构组合产品信息
	 * @param map
	 * @return
	 */
	public List<ProductGroupBaseInfoModel> getGroupListByStructureIdV2(Integer structureId);
	
	
	/**
	 * 获取一些产品信息
	 * @param map
	 * @return
	 */
	public List<ProductGroupBaseInfoModel> getGroupListByProductV2(GroupProductSearch params); 
	
	/**
	 * 获取一些产品信息
	 * @param map
	 * @return
	 */
	public List<ProductGroupBaseInfoModel> getGroupListByProductV3(GroupProductSearch params); 
	
	
	/**
	 * 获取用户收藏的组合产品
	 * @param map
	 * @return
	 */
	public List<Map<String, Integer>> getProductGroupUserCollectedUsingUnion(Map<String, Object> map); 
	
	
	/**
	 * 根据产品组 id 获取产品组中产品的一些信息， 比如产品编码、产品类型值、产品小类型值、售卖价格、长宽高等等
	 * @param map的 list 需要 groupid 以及 base_product 中对应建模类型的列名
	 * @return
	 */
	public List<ProductInfoInGroupModel> getProductInfoInTheGroupByGroupIDAndModelIDType(List<Map<String, Object>> list);
	
	/**
	 * 获取每个产品组中各个产品的大类、小类、父类
	 * @param list
	 * @return
	 */
	public List<GroupProductAllTypeModel> getProductBSPTypeValueUsingUnion(List<Map<String, Object>> list);
	
	
	/**
	 * 获取一个产品组里面相关产品的 res_pic记录
	 * @param productIDList
	 * @return
	 */
	public List<Map<String, Object>> getProductesResPicUsingUnion(List<Integer> productIDList);
	
	/**
	 * 获取这些产品的属性
	 * @param productIDList
	 * @return
	 */
	public List<Map<String, Object>> getProductesAttrisUsingUnion(List<Integer> productIDList);

	/**
	 * 通过结构id(structureId)和组合状态(status)查找组合列表
	 * @author huangsongbo
	 * @param structureId
	 * @param groupType 
	 * @param status
	 * @return
	 */
	public List<ProductGroupBaseInfoModel> getGroupListByStructureIdAndStatus(
			@Param("structureId") Integer structureId,
			@Param("userType") Integer userType, 
			@Param("start") Integer start,
			@Param("limit") Integer limit,@Param("brandIdList") String [] brandIdList,
			@Param("versionType")String versionType, @Param("groupType") Integer groupType);

	/**
	 * 获取方案已使用的组合数量
	 * @author xiaoxc
	 * @param designPlanId
	 * @return
	 */
	public int getPlanGroupCount(@Param("designPlanId") Integer designPlanId,@Param("compositeType") Integer compositeType);

	/**
	 * 获取方案已使用的组合列表
	 * @author xiaoxc
	 * @param designPlanId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ProductGroupBaseInfoModel> getPlanGroupList(@Param("designPlanId") Integer designPlanId,
															@Param("compositeType") Integer compositeType,
															@Param("start") Integer start,
															@Param("limit") Integer limit);

	 List<SearchProductGroupInfoResult> getGroupListBySearchParam(GroupProductSearch params);
}
