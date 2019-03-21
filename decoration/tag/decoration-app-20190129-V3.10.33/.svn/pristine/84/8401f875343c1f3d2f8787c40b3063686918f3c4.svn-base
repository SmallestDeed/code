package com.nork.product.service2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.util.ServiceHint;
import com.nork.product.controller2.web.StructureProductParameter;
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
public interface StructureProductServiceV2 {
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
	public SearchStructureProductResult getSearchResult(StructureProduct structureProduct, Integer spaceCommonId, String mediaType, Integer planProductId, HttpServletRequest request);

	public int getCountByGroupFlag(String structureGroupFlag, List<Integer> statusList,Integer start, Integer limit);

	/**
	 * 清楚该结构在所有样板房中对应的结构关系
	 * @author huangsongbo
	 * @param id
	 */
	public void updateDesignTempletStructureRelationByStructureId(Integer id);

	public SearchStructureProductResult getSearchStructureProductResult(StructureProduct structureProduct);


	public List<StructureProduct> getStructureObject(StructureProductSearch structureProductSearch);

	/**
	 * 批量
	 * @param oldStatus
	 * @param newStatus
	 */
	public void updateStatus(Integer oldStatus, Integer newStatus);
	
	/**
	 * 批量删除
	 * */
	public int deleteBatch(List<Integer> list);
	
	/**
	 *   通过编码存入结构类型和序号（方便一键装修结构匹配）
	 */
	public StructureProduct saveStructureTypeOrNumber(String structureCode, StructureProduct structureProduct);
	
	/**
	 * 定义结构接口(新增结构,用于编辑器调用)
	 */
	public ServiceHint createStructureProduct(StructureProductParameter sParameter,int userId);
	
	/**
	 * 查询结构接口
	 */
	public StructureProductParameter searchStructureProduct(StructureProductParameter sParameter);
	
	/**
	 * 替换结构接口
	 */
	public StructureProductParameter unityStructureProduct(StructureProductParameter sParameter);
	
	/**
	 * 一键替换结构匹配(多个结构)
	 */
	public StructureProductParameter matchingStructureData(StructureProductParameter sParameter);

}
