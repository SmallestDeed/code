package com.nork.product.service;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.onekeydesign.model.search.DesignTempletProductSearch;
import com.nork.product.model.DesignTempletProducts;
import com.nork.product.model.ProductRecommendation;
import com.nork.product.model.RecommendProductResult;
import com.nork.product.model.search.ProductRecommendationSearch;

/**   
 * @Title: ProductRecommendationService.java 
 * @Package com.nork.product.service
 * @Description:产品-产品推荐Service
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 14:02:16
 * @version V1.0   
 */
public interface ProductRecommendationService {
	/**
	 * 新增数据
	 *
	 * @param productRecommendation
	 * @return  int 
	 */
	public int add(ProductRecommendation productRecommendation);

	/**
	 *    更新数据
	 *
	 * @param productRecommendation
	 * @return  int 
	 */
	public int update(ProductRecommendation productRecommendation);

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
	 * @return  ProductRecommendation 
	 */
	public ProductRecommendation get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  productRecommendation
	 * @return   List<ProductRecommendation>
	 */
	public List<ProductRecommendation> getList(ProductRecommendation productRecommendation);

	/**
	 *    获取数据数量
	 *
	 * @param  productRecommendation
	 * @return   int
	 */
	public int getCount(ProductRecommendationSearch productRecommendationSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  productRecommendation
	 * @return   List<ProductRecommendation>
	 */
	public List<ProductRecommendation> getPaginatedList(
				ProductRecommendationSearch productRecommendationtSearch);
	

	/**
	 * 其他
	 * 
	 */

	/**
	 * 查询产品推荐详情列表
	 * @param DesignTempletProducts
	 * @return
	 */
	public List<DesignTempletProducts> getDesignProductList(
			DesignTempletProducts DesignTempletProducts);

	/**
	 * 根据大类小类和样板房ID查询
	 * @param DesignTempletProducts
	 * @return
	 */
	public List<RecommendProductResult> getRecommendProduct(ProductRecommendationSearch productRecommendation);
	/**
	 * 产品推荐详情
	 * @param DesignTempletProducts
	 * @return
	 */
	public List<RecommendProductResult> recommendProductList(ProductRecommendationSearch productRecommendation);
	
	public int recommendProductCount(ProductRecommendationSearch productRecommendation);
	/**
	 * 产品推荐详情/历史推荐列表
	 * @param DesignTempletProducts
	 * @return
	 */
	public List<RecommendProductResult> getRecommendProList(DesignTempletProductSearch designTempletProductSearch);
	public int getRecommendProCount(DesignTempletProductSearch designTempletProductSearch);
	/**
	 * 产品管理--产品推荐列表
	 * @param RecommendProductResult
	 * @return
	 */
	public List<RecommendProductResult> getProductRecommendList(RecommendProductResult recommendProductResult);
	
	public int getProductRecommendCount(RecommendProductResult recommendProductResult);
	
	/**
	 * 通过样板房编码删除推荐
	 * @param designCode
	 * @return
	 */
	int deleteByDesignCode(String designCode);

	ProductRecommendation sysSave(ProductRecommendation productRecommendation,LoginUser loginUser);
}
