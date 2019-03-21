package com.nork.product.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.product.model.ProductSpellingFlowerModel;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.result.SearchStructureProductResult;
import com.nork.product.model.search.StructureProductSearch;

/**   
 * @Title: StructureProductService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-结构表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:32:05
 * @version V1.0   
 */
public interface StructureProductService {
	/**
	 * 新增数据
	 *
	 * @param structureProduct
	 * @return  int 
	 */
	public int add(StructureProduct structureProduct);

	/**
	 *    更新数据
	 *
	 * @param structureProduct
	 * @return  int 
	 */
	public int update(StructureProduct structureProduct);

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
	 * @return  StructureProduct 
	 */
	public StructureProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  structureProduct
	 * @return   List<StructureProduct>
	 */
	public List<StructureProduct> getList(StructureProduct structureProduct);

	/**
	 *    获取数据数量
	 *
	 * @param  structureProduct
	 * @return   int
	 */
	public int getCount(StructureProductSearch structureProductSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  structureProduct
	 * @return   List<StructureProduct>
	 */
	public List<StructureProduct> getPaginatedList(
				StructureProductSearch structureProducttSearch);

	/**
	 * 通过结构组标记查找结构
	 * @author huangsongbo
	 * @param structureGroupFlag
	 * @param limit 
	 * @param start 
	 * @param limit 
	 * @return
	 */
	public List<StructureProduct> findAllByGroupFlag(String structureGroupFlag, List<Integer> statusList,Integer templetId, Integer start, Integer limit);

	/**
	 * StructureProduct->SearchStructureProductResult
	 * @author huangsongbo
	 * @param structureProduct
	 * @param mediaType
	 * @return
	 */
	public SearchStructureProductResult getSearchResult(StructureProduct structureProduct, Integer spaceCommonId, String mediaType, Integer planProductId);

	public int getCountByGroupFlag(String structureGroupFlag, List<Integer> statusList,Integer start, Integer limit);

	/**
	 * 清楚该结构在所有样板房中对应的结构关系
	 * @author huangsongbo
	 * @param id
	 */
	public void updateDesignTempletStructureRelationByStructureId(Integer id);

	public SearchStructureProductResult getSearchStructureProductResult(StructureProduct structureProduct,String templetStructureCode);


	public List<StructureProduct> getStructureObject(StructureProductSearch structureProductSearch);

	/**
	 * 批量
	 * @param oldStatus
	 * @param newStatus
	 */
	public void updateStatus(Integer oldStatus, Integer newStatus);
	/**
	 * 定义结构接口(新增结构,用于编辑器调用)
	 * @author huangsongbo
	 * @param structureCode
	 * @param structureName
	 * @param config
	 * @param msgId
	 * @return
	 */
	public Object createStructureProduct(Integer styleId,Integer isCommon,String regionMark ,String measureCode,String structureCode, String structureName, String templetCode,
			String groupFlag, String config,String msgId,Integer structureSmallType,LoginUser loginUser, String center);


	/**
	 * 查询结构接口
	 * @author xiaoxc
	 * @param planProductId  方案产品ID
	 * @param spaceCommonId  空间ID
	 * @param msgId
	 * @param start
	 * @param limit
	 * @param loginUser  用户对象
	 * @param mediaType  媒介类型
	 * @param styleId  款式Id
	 * @param isStandard  是否是标准
	 * @param regionMark  区域标识
	 * @param measureCode  尺寸代码
	 * @return
	 */
	public Object searchStructureProduct(Integer planProductId, Integer spaceCommonId, Integer designPlanId,
										 String msgId,Integer start,Integer limit,LoginUser loginUser,String mediaType,
										 Integer isStandard,Integer styleId,String regionMark,String measureCode);

	/**
	 * 查询结构接口
	 * @author xiaoxc
	 * @param designPlanId 方案ID
	 * @param groupId 组合、结构ID
	 * @param planGroupId 方案组标识
	 * @param groupProductJson  更新组合产品信息
	 * @param context   配置文件内容
	 * @param groupType 组合类型
	 * @param msgId
	 * @param loginUser  用户对象
	 * @param isStandard 是否是标准
	 * @param center  中心点
	 * @param regionMark 区域标识
	 * @param styleId  款式Id
	 * @param measureCode  尺寸代码
	 * @return
	 */
	public Object unityStructureProduct(Integer designPlanId,Integer groupId,String planGroupId,
										String groupProductJson,String context,Integer groupType,
										String msgId,LoginUser loginUser,Integer isStandard,
										String center,String regionMark,Integer styleId,String measureCode);


	/**
	 * 根据结构编码查询结构列表
	 * 补充 by huangsongbo:
	 * 查询该设计方案非背景墙的结构?
	 * @author xiaoxc
	 * @param designPlanId  方案ID
	 * @return
	 */
	public List<StructureProduct> getStructuresByDesignPlanId(Integer designPlanId);
 
	/**
	 * 批量删除
	 * */
	public int deleteBatch(List<Integer> list);

	/**
	 * 根据结构编码查询结构列表
	 * 查询该推荐设计方案非背景墙的结构
	 * 
	 * @author huangsongbo
	 * @param recommendedPlanId  推荐方案id
	 * @return
	 */
	public List<StructureProduct> getStructuresByRecommendedPlanId(Integer recommendedPlanId);

	/**
	 * 一件装修搜索结构
	 * 
	 * @author huangsongbo
	 * @param measureCode
	 * @param styleId
	 * @param structureNumber
	 * @param identifylist 结构布局标识
	 * @param groundIdentify 
	 * @param designTempletId 样板房id
	 * @return
	 */
	public Integer easySearch(String measureCode, Integer styleId, Integer structureNumber, List<String> identifyList, String groundIdentify, Integer designTempletId);

	public List<PlanProductInfo> getPlanProductInfoListByStructureId(Integer structureIdSeleted);

	/**
	 * 一件装修搜索结构
	 * 
	 * @author huangsongbo
	 * @param structureProductSearch
	 * @return
	 */
	public Integer easySearchV2(StructureProductSearch structureProductSearch);

	public List<StructureProduct> getListV2(StructureProductSearch search);

	public int getCountV2(StructureProductSearch search);

	
	/**
	 * 产品拼花  取结构功能
	 * @param model
	 * @return
	 */
	public Object productSpellingFlower(ProductSpellingFlowerModel model);

	
	
	/**
	 * 保存结构拼花文本信息、产品拼花文本信息
	 * @param productSpellingFlower
	 * @param structureSpellingFlower
	 * @param msgId
	 * @return
	 */
	public Object saveProductSpellingFlower(String productSpellingFlower, String structureSpellingFlower,String structureId, String msgId);

	/**
	 * 通过 结构ids  获取   结构拼花文本 集合
	 * @param msgId
	 * @param structureIds
	 * @return
	 */
	public Object getStructureSpellingFlowerTxt(String msgId, String structureIds);
	
}
