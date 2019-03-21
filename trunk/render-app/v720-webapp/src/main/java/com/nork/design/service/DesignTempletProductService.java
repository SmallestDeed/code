package com.nork.design.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.DesignTempletProductResult;
import com.nork.design.model.ProductListByTypeInfo;
import com.nork.design.model.search.DesignTempletProductSearch;

/**   
 * @Title: DesignTempletProductService.java 
 * @Package com.nork.design.service
 * @Description:设计模块-方案产品表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-07 11:04:44
 * @version V1.0   
 */
public interface DesignTempletProductService {
	/**
	 * 新增数据
	 *
	 * @param designTempletProduct
	 * @return  int 
	 */
	public int add(DesignTempletProduct designTempletProduct);

	/**
	 *    更新数据
	 *
	 * @param designTempletProduct
	 * @return  int 
	 */
	public int update(DesignTempletProduct designTempletProduct);

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
	 * @return  DesignTempletProduct 
	 */
	public DesignTempletProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designTempletProduct
	 * @return   List<DesignTempletProduct>
	 */
	public List<DesignTempletProduct> getList(DesignTempletProduct designTempletProduct);

	/**
	 *    获取数据数量
	 *
	 * @param  designTempletProduct
	 * @return   int
	 */
	public int getCount(DesignTempletProductSearch designTempletProductSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designTempletProduct
	 * @return   List<DesignTempletProduct>
	 */
	public List<DesignTempletProduct> getPaginatedList(
				DesignTempletProductSearch designTempletProducttSearch);

	/**
	 * 根据样板房id删除关联的产品
	 * @param designTempletId
	 * @return
	 */
	public int deleteByDesignTempletCode(String designCode);

	public int batchSave(Integer designTempletId,List<DesignTempletProduct> designProduct);

	DesignTempletProduct sysSave(DesignTempletProduct designTempletProduct,LoginUser loginUser);
	/**
	 * 得到design_templet_product表中的产品idList(去重复)
	 * @author huangsongbo
	 * @return
	 */
	public List<Integer> getDistinctProductIdList();

	public List<DesignTempletProductResult> getTempletIdProductList(DesignTempletProduct designTempletProduct);

	public List<DesignTempletProduct> getByTempletIdMainProduct(DesignTempletProduct designTempletProduct);

	/**
	 * 更新产品序号
	 * @author xiaoxc
	 * @param designTempletProduct
	 * @return  int
	 */
	public int updateSameProductTypeIndex(DesignTempletProduct designTempletProduct);

	/**
	 * 根据样板房ID搜索结构产品
	 * 补充 by huangsongbo:
	 * 查询该样板房中非背景墙的结构?
	 * @author xiaoxc
	 * @return
	 */
	public List<DesignTempletProduct> getStructureProductByDesignId(Integer designTempletId);

	/**
	 * 根据样板房id查询该样板房中存在的白膜组合的主产品
	 * 
	 * @author huangsongbo
	 * @param id 样板房id
	 * @return
	 */
	public List<DesignTempletProduct> getMainProductListByTempletId(Integer id);

	/**
	 * 得到样板房产品列表以及额外的信息
	 * 
	 * @author huangsongbo
	 * @param designTempletId
	 * @return
	 */
	public List<DesignTempletProduct> getListByTempletId(Integer designTempletId);

	/**
	 * 遍历样板房产品列表
	 * 讲单品/结构/组合分类放置
	 * 
	 * @author huangsongbo
	 * @param designTempletProductList
	 * @param matchType 0:全屋替换;1:硬装替换
	 * @return
	 */
	public ProductListByTypeInfo getProductListByTypeInfo(List<DesignTempletProduct> designTempletProductList, Integer matchType);
	
	public int updateDesignTemplateProduct(DesignTempletProduct designTempletProduct);

	/**
	 * 获取样板房产品表中墙体方位list
	 * 
	 * @author huangsongbo
	 * @param templetId
	 * @return
	 */
	public List<String> getWallOrientationList(Integer templetId);

	/**
	 * 获取样板房中所有的背景墙信息
	 * 
	 * @author huangsongbo
	 * @param designTempletId
	 * @return
	 */
	public List<DesignTempletProduct> getBeijingProductInfo(Integer designTempletId);
}
