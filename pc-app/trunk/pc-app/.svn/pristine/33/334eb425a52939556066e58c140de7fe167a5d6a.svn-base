package com.nork.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.repair.model.CategoryProductRel;
import com.nork.repair.model.ProductCategoryInfo;
import com.nork.system.model.SysDictionary;

/**   
 * @Title: ProductCategoryRelService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品与产品类目关联Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:50:47
 * @version V1.0   
 */
public interface ProductCategoryRelService {
	/**
	 * 新增数据
	 *
	 * @param productCategoryRel
	 * @return  int 
	 */
	public int add(ProductCategoryRel productCategoryRel);

	/**
	 *    更新数据
	 *
	 * @param productCategoryRel
	 * @return  int 
	 */
	public int update(ProductCategoryRel productCategoryRel);

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
	 * @return  ProductCategoryRel 
	 */
	public ProductCategoryRel get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  productCategoryRel
	 * @return   List<ProductCategoryRel>
	 */
	public List<ProductCategoryRel> getList(ProductCategoryRel productCategoryRel);

	/**
	 *    获取数据数量
	 *
	 * @param  productCategoryRel
	 * @return   int
	 */
	public int getCount(ProductCategoryRelSearch productCategoryRelSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  productCategoryRel
	 * @return   List<ProductCategoryRel>
	 */
	public List<ProductCategoryRel> getPaginatedList(
				ProductCategoryRelSearch productCategoryReltSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据产品编号和类目编号查询
	 * @param pid
	 * @param cid
	 * @return
	 */
	public ProductCategoryRel findByPidAndCid(Map<String,Object> paramsMap);
	
	/**
	 * 根据产品ID删除关联信息
	 * @param pid
	 * @param cid
	 * @return
	 */
	public int deletedByProductId(Integer productId);
	
	/**
	 * 根据分类编号查询产品
	 * @param longCode
	 * @return
	 */
	public List<ProductCategoryRel> findCategoryProductIds(String longCode);

	/**
	 * 根据分类code和商品名称查询
	 * @param params
	 * @return
	 */
	List<CategoryProductResult> findProductByCategoryCode(ProductCategoryRel productCategoryRel);
	/**
	 * 根据分类code和商品名称查询汇总
	 * @param productCategoryRel
	 * @return
	 */
	public int findProductByCategoryCodeCount(ProductCategoryRel productCategoryRel);

	/**
	 * 删除所有productId为:id的productCategoryRel
	 * @param id
	 * @return
	 */
	public int deleteAllByProductId(Integer id);

	/**
	 * 通过Longcode查找CategoryProductResult
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findCategoryProductResultByLongCode(
			ProductCategoryRel productCategoryRel);

	public int findCategoryProductResultByLongCodeCount(
			ProductCategoryRel productCategoryRel);

	public int getProCategoryListCountByCode(
			ProductCategoryRel productCategoryRelSearch);

	public List<CategoryProductResult> getProCategoryListByCode(
			ProductCategoryRel productCategoryRelSearch);
	
	/**
	 * 建材家居搜索产品
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findBuildingHomeProductResult(
			ProductCategoryRel productCategoryRel);
	
	public int findBuildingHomeProductCount(
			ProductCategoryRel productCategoryRel);
	/**
	 * 通过Longcode查找 定制CategoryProductResult
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findCustomizedCategoryProductResult(
			ProductCategoryRel productCategoryRel);
	public int findCustomizedCategoryProductResultCount(
			ProductCategoryRel productCategoryRel);
	/**
	 * 通过Longcode查找 推荐与分类CategoryProductResult
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findRecommendCategoryProductResult(
			ProductCategoryRel productCategoryRel);
	public int findRecommendCategoryProductResultCount(
			ProductCategoryRel productCategoryRel);
	/**
	 * 通过Longcode查找所有分类产品
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findCategoryProductResult(
			ProductCategoryRel productCategoryRel);
	/**
	 * 按照序列号过滤list
	 * @author huangsongbo
	 * @param list
	 * @param userId
	 * @return
	 */
	public List<CategoryProductResult> filterByAuthorizedConfig(List<CategoryProductResult> list, Integer userId, String terminalImei);

	/**
	 * 按照序列号过滤list方法2
	 * @author huangsongbo
	 * @param list
	 * @param userId
	 * @return
	 */
	public List<CategoryProductResult> filterByAuthorizedConfig2(List<CategoryProductResult> list, Integer userId, String terminalImei);
	
	/**
	 * 根据分类code查找该产品以及该产品类型子类的产品
	 * @param code
	 * @return
	 */
	//public List<CategoryProductResult> findProductByCategoryCode(String code);

	CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel);
	
	/**
	 * diy分类搜索产品数量
	 * @param params
	 * @return
	 */
	int getCategoryProductCount(ProductCategoryRel productCategoryRel);
	/**
	 * diy分类搜索产品
	 * @param params
	 * @return
	 */
	List<CategoryProductResult> getCategoryProductResult(ProductCategoryRel productCategoryRel);
	
	/**
	 * diy分类搜索推荐产品数量
	 * @param params
	 * @return
	 */
	int getRecommendResultCount(ProductCategoryRel productCategoryRel);
	/**
	 * diy分类搜索推荐产品
	 * @param params
	 * @return
	 */
	List<CategoryProductResult> getRecommendResult(ProductCategoryRel productCategoryRel);
	/**
	 * 分类搜索产品背景墙内容和特殊分类信息条件
	 * @param productCategoryRel
	 * @return
	 */
	ProductCategoryRel bgWallAndSpecialTypeInfo(ProductCategoryRel productCategoryRel,boolean isShowbgWall, SysDictionary bigSd, SysDictionary smallSd, BaseProduct baimoProduct, BaseProduct productSelected);

	/**
	 * qiangm分类搜索中的逻辑
	 * @param productCategoryRel
	 * @return
	 */
	ProductCategoryRel getWallTypeLogic(ProductCategoryRel productCategoryRel, DesignPlanProduct designPlanProduct, SysDictionary bigSd,  HttpServletRequest request);

	/**
	 * findRecommendCategoryProductResult优化版
	 * @author huangsongbo
	 */
	public List<CategoryProductResult> findRecommendCategoryProductResultV2(ProductCategoryRel productCategoryRel);

	/**
	 * findCustomizedCategoryProductResult优化版
	 * @author huangsongbo
	 */
	public List<CategoryProductResult> findCustomizedCategoryProductResultV2(ProductCategoryRel productCategoryRel);

	public List<CategoryProductResult> findCustomizedCategoryProductResultV3(ProductCategoryRel productCategoryRel);

	public List<CategoryProductResult> findRecommendCategoryProductResultV3(ProductCategoryRel productCategoryRel);
	
	public ResponseEnvelope<CategoryProductResult> searchProduct(ProductCategoryRel productCategoryRel, HttpServletRequest request,String houseType,Integer designPlanId,Integer planProductId,Integer spaceCommonId,String templateProductId,String productTypeValue,String smallTypeValue,String queryType,String productModelNumber,LoginUser loginUser,DesignPlan designPlan);

	public ResponseEnvelope<CategoryProductResult> searchProductV6(
			String templateProductId,
			ProductCategoryRel productCategoryRel,
			LoginUser loginUser,
			String productModelOrBrandName,
			Integer planProductId,
			String houseType,
			Integer productTypeValue,
			Integer spaceCommonId,
			DesignPlan designPlan,
			Integer smallTypeValue,
			HttpServletRequest request,
			Integer statusType,
			Integer isStandard,
			String regionMark,
			Integer styleId,
			String measureCode,
			String smallpox);

	/**
	 * 获取材质信息组装成SplitTextureDTO,应对拼花产品
	 *
	 * @author huangsongbo
	 * @param parquetTextureIds
	 * @return
	 */
	public List<SplitTextureDTO> getSplitTexturesChooseForSpellingflower(String parquetTextureIds, Integer productId);

	/**
	 * 获取自动携带白模产品配置
	 */
	Map<String, AutoCarryBaimoProducts> getAutoCarryMap();

	/**
	 * 设置产品多维材质
	 * @param categoryProductResult
	 */
	void dealWithTextureInfo(CategoryProductResult categoryProductResult);

	/**
	 * 获取形象墙分类产品信息
	 * @param productId
	 * @return
	 */
	List<ProductCategoryInfo> getXingxCategoryProductInfo(Integer productId, Integer start, Integer limit);

	/**
	 * 查询产品挂到这个分类下的数量
	 * @param productId
	 * @param longCode
	 * @param name
	 * @return
	 */
	CategoryProductRel getCategoryProductInfo(String longCode, String name, Integer productId);

	/**
	 * 初始化对象
	 * @param categoryId
	 * @param productId
	 * @param status
	 * @return
	 */
	ProductCategoryRel initCategoryProductRel(Integer categoryId, Integer productId, Integer status);

	/**
	 * 批量插入分类产品关联表
	 * @param list
	 * @return
	 */
	int batchAdd(List<ProductCategoryRel> list);
}
