package com.nork.product.service2;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ProductCategoryVO;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.model.SearchProductVO;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.product.model.small.SearchProCategorySmall;
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
	 * @return int
	 */
	public int add(ProductCategoryRel productCategoryRel);

	/**
	 * 更新数据
	 *
	 * @param productCategoryRel
	 * @return int
	 */
	public int update(ProductCategoryRel productCategoryRel);

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	public int delete(Integer id);

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return ProductCategoryRel
	 */
	public ProductCategoryRel get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param productCategoryRel
	 * @return List<ProductCategoryRel>
	 */
	public List<ProductCategoryRel> getList(ProductCategoryRel productCategoryRel);

	/**
	 * 获取数据数量
	 *
	 * @param productCategoryRel
	 * @return int
	 */
	public int getCount(ProductCategoryRelSearch productCategoryRelSearch);

	/**
	 * 分页获取数据
	 *
	 * @param productCategoryRel
	 * @return List<ProductCategoryRel>
	 */
	public List<ProductCategoryRel> getPaginatedList(ProductCategoryRelSearch productCategoryReltSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据产品编号和类目编号查询
	 * 
	 * @param pid
	 * @param cid
	 * @return
	 */
	public ProductCategoryRel findByPidAndCid(Map<String, Object> paramsMap);

	/**
	 * 根据产品ID删除关联信息
	 * 
	 * @param pid
	 * @param cid
	 * @return
	 */
	public int deletedByProductId(Integer productId);

	/**
	 * 根据分类编号查询产品
	 * 
	 * @param longCode
	 * @return
	 */
	public List<ProductCategoryRel> findCategoryProductIds(String longCode);

	/**
	 * 根据分类code和商品名称查询
	 * 
	 * @param params
	 * @return
	 */
	List<CategoryProductResult> findProductByCategoryCode(ProductCategoryRel productCategoryRel);

	/**
	 * 根据分类code和商品名称查询汇总
	 * 
	 * @param productCategoryRel
	 * @return
	 */
	public int findProductByCategoryCodeCount(ProductCategoryRel productCategoryRel);

	/**
	 * 删除所有productId为:id的productCategoryRel
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAllByProductId(Integer id);

	/**
	 * 通过Longcode查找CategoryProductResult
	 * 
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findCategoryProductResultByLongCode(ProductCategoryRel productCategoryRel);

	public int findCategoryProductResultByLongCodeCount(ProductCategoryRel productCategoryRel);

	public int getProCategoryListCountByCode(ProductCategoryRel productCategoryRelSearch);

	public List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRelSearch);

	/**
	 * 建材家居搜索产品
	 * 
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findBuildingHomeProductResult(ProductCategoryRel productCategoryRel);

	public int findBuildingHomeProductCount(ProductCategoryRel productCategoryRel);

	/**
	 * 通过Longcode查找 定制CategoryProductResult
	 * 
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findCustomizedCategoryProductResult(ProductCategoryRel productCategoryRel);

	public int findCustomizedCategoryProductResultCount(ProductCategoryRel productCategoryRel);

	/**
	 * 通过Longcode查找 推荐与分类CategoryProductResult
	 * 
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findRecommendCategoryProductResult(ProductCategoryRel productCategoryRel);

	public int findRecommendCategoryProductResultCount(ProductCategoryRel productCategoryRel);

	/**
	 * 通过Longcode查找所有分类产品
	 * 
	 * @param string
	 * @return
	 */
	public List<CategoryProductResult> findCategoryProductResult(ProductCategoryRel productCategoryRel);

	/**
	 * 按照序列号过滤list
	 * 
	 * @author huangsongbo
	 * @param list
	 * @param userId
	 * @return
	 */
	public List<CategoryProductResult> filterByAuthorizedConfig(List<CategoryProductResult> list, Integer userId,
			String terminalImei);

	/**
	 * 按照序列号过滤list方法2
	 * 
	 * @author huangsongbo
	 * @param list
	 * @param userId
	 * @return
	 */
	public List<CategoryProductResult> filterByAuthorizedConfig2(List<CategoryProductResult> list, Integer userId,
			String terminalImei);

	/**
	 * 根据分类code查找该产品以及该产品类型子类的产品
	 * 
	 * @param code
	 * @return
	 */
	// public List<CategoryProductResult> findProductByCategoryCode(String
	// code);

	CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel);

	/**
	 * diy分类搜索产品数量
	 * 
	 * @param params
	 * @return
	 */
	int getCategoryProductCount(ProductCategoryRel productCategoryRel);

	/**
	 * diy分类搜索产品
	 * 
	 * @param params
	 * @return
	 */
	List<CategoryProductResult> getCategoryProductResult(ProductCategoryRel productCategoryRel);

	/**
	 * diy分类搜索推荐产品数量
	 * 
	 * @param params
	 * @return
	 */
	int getRecommendResultCount(ProductCategoryRel productCategoryRel);

	/**
	 * diy分类搜索推荐产品
	 * 
	 * @param params
	 * @return
	 */
	List<CategoryProductResult> getRecommendResult(ProductCategoryRel productCategoryRel);

	/**
	 * 分类搜索产品背景墙内容和特殊分类信息条件
	 * 
	 * @param productCategoryRel
	 * @return
	 */
	ProductCategoryRel bgWallAndSpecialTypeInfo(ProductCategoryRel productCategoryRel, boolean isShowbgWall,
			SysDictionary bigSd, SysDictionary smallSd, BaseProduct baimoProduct, BaseProduct productSelected);

	/**
	 * qiangm分类搜索中的逻辑
	 * 
	 * @param productCategoryRel
	 * @return
	 */
	ProductCategoryRel getWallTypeLogic(ProductCategoryRel productCategoryRel, DesignPlanProduct designPlanProduct,
			SysDictionary bigSd);

	/**
	 * findRecommendCategoryProductResult优化版
	 * 
	 * @author huangsongbo
	 */
	public List<CategoryProductResult> findRecommendCategoryProductResultV2(ProductCategoryRel productCategoryRel);

	/**
	 * findCustomizedCategoryProductResult优化版
	 * 
	 * @author huangsongbo
	 */
	public List<CategoryProductResult> findCustomizedCategoryProductResultV2(ProductCategoryRel productCategoryRel);

	public List<CategoryProductResult> findCustomizedCategoryProductResultV3(ProductCategoryRel productCategoryRel);

	public List<CategoryProductResult> findRecommendCategoryProductResultV3(ProductCategoryRel productCategoryRel);

	public ResponseEnvelope<CategoryProductResult> searchProduct(ProductCategoryRel productCategoryRel,HttpServletRequest  request,
			SearchProductVO serachProductVO, LoginUser loginUser, DesignPlan designPlan);

	/**
	 * 获得分类查询
	 * 
	 * @param response
	 * @param msgId
	 * @return
	 */
	public Object getCategory(String msgId);

	/**
	 * 查询分类
	 * 
	 * @return
	 */
	public SearchProCategorySmall getcategorySmall();

	/**
	 * 查询分类1
	 * 
	 * @return
	 */
	public SearchProCategorySmall getCategorySmall1();

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public SearchProCategorySmall getAllCategorySmall();
    /**
     * 根据分类查询产品
     *
     * @param request
     * @param productCategoryRel
     * @return
     */
	public  List<CategoryProductResult> searchProduct( 
			 ProductCategoryRel productCategoryRel,  
			 ProductCategoryVO productCategoryVO,LoginUser loginUser,String mediaType );
}
