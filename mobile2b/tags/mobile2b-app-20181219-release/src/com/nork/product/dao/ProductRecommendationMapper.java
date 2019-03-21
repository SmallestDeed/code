package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.product.model.DesignTempletProducts;
import com.nork.product.model.ProductRecommendation;
import com.nork.product.model.RecommendProductResult;
import com.nork.product.model.search.ProductRecommendationSearch;

/**   
 * @Title: ProductRecommendationMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品-产品推荐Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 14:02:16
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProductRecommendationMapper {
    int insertSelective(ProductRecommendation record);

    int updateByPrimaryKeySelective(ProductRecommendation record);
  
    int deleteByPrimaryKey(Integer id);
        
    ProductRecommendation selectByPrimaryKey(Integer id);
    
    int selectCount(ProductRecommendationSearch productRecommendationSearch);
    
	List<ProductRecommendation> selectPaginatedList(
			ProductRecommendationSearch productRecommendationSearch);
			
    List<ProductRecommendation> selectList(ProductRecommendation productRecommendation);
   
    List<DesignTempletProducts> selectDesignProductList(DesignTempletProducts DesignTempletProducts);
    
	/**
	 * 其他
	 * 
	 */

    /**
     * 根据大类小类和样板房ID查询
     * @param ProductRecommendation
     * @return
     */
    List<RecommendProductResult> getRecommendProduct(ProductRecommendationSearch productRecommendation);
    /**
     * 后台产品推荐详情
     * @param ProductRecommendation
     * @return
     */
    List<RecommendProductResult> recommendProductList(ProductRecommendationSearch productRecommendation);
    
    int recommendProductCount(ProductRecommendationSearch productRecommendation);
    /**
     * 后台产品推荐详情/历史推荐产品
     * @param DesignTempletProductSearch
     * @return
     */
    List<RecommendProductResult> recommendProList(DesignTempletProductSearch designTempletProductSearch);
    int recommendProCount(DesignTempletProductSearch designTempletProductSearch);
    /**
     * 后台产品管理-产品推荐列表
     * @param RecommendProductResult
     * @return
     */
    List<RecommendProductResult> proRecommendList(RecommendProductResult recommendProductResult);
    int proRecommendCount(RecommendProductResult recommendProductResult);
    
    /**
     * 通过样板房编码删除推荐
     * @param designCode
     * @return
     */
    int deleteByDesignCode(String designCode);
}
