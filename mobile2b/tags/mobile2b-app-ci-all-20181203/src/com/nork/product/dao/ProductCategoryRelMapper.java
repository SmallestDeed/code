package com.nork.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProductCategoryRelSearch;

/**   
 * @Title: ProductCategoryRelMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-产品与产品类目关联Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:50:47
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProductCategoryRelMapper {
    int insertSelective(ProductCategoryRel record);

    int updateByPrimaryKeySelective(ProductCategoryRel record);
  
    int deleteByPrimaryKey(Integer id);
        
    ProductCategoryRel selectByPrimaryKey(Integer id);
    
    int selectCount(ProductCategoryRelSearch productCategoryRelSearch);
    
	List<ProductCategoryRel> selectPaginatedList(
			ProductCategoryRelSearch productCategoryRelSearch);
			
    List<ProductCategoryRel> selectList(ProductCategoryRel productCategoryRel);
    
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
	public ProductCategoryRel findByPidAndCid(Map<String, Object> paramsMap);
	
	/**
	 * 根据产品ID删除关联信息
	 * @param pid
	 * @param cid
	 * @return
	 */
	public int deletedByProductId(Integer productId);
	
	/**
	 * 根据分类编号查询产品
	 * @param pid
	 * @param cid
	 * @return
	 */
	public List<ProductCategoryRel> findCategoryProductIds(String longCode);

	/**
	 * 根据分类code和商品名称查询
	 * @param productCategoryRel
	 * @return
	 */
	List<CategoryProductResult> findProductByCategoryCode(ProductCategoryRel productCategoryRel);
	/**
	 * 根据分类code和商品名称查询汇总
	 * @param productCategoryRel
	 * @return
	 */
	public int findProductByCategoryCodeCount(ProductCategoryRel productCategoryRel);

	public int deleteAllByProductId(Integer id);

	List<CategoryProductResult> findCategoryProductResultByLongCode(ProductCategoryRel productCategoryRel);
	
	List<CategoryProductResult> findBuildingHomeProductResult(ProductCategoryRel productCategoryRel);
	
	int findBuildingHomeProductCount(ProductCategoryRel productCategoryRel);
	
	List<CategoryProductResult> findCustomizedCategoryProductResult(ProductCategoryRel productCategoryRel);
	
	int findCustomizedCategoryProductResultCount(ProductCategoryRel productCategoryRel);
	
	List<CategoryProductResult> findRecommendCategoryProductResult(ProductCategoryRel productCategoryRel);
	
	int findRecommendCategoryProductResultCount(ProductCategoryRel productCategoryRel);

	int findCategoryProductResultByLongCodeCount(ProductCategoryRel productCategoryRel);

	int getProCategoryListCountByCode(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRel);
	
	List<CategoryProductResult> findCategoryProductResult(ProductCategoryRel productCategoryRel);

	CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel);
	
	/**
	 * diy分类搜索数量
	 * @param productCategoryRel
	 * @return
	 */
	int getCategoryProductCount(ProductCategoryRel productCategoryRel);
	/**
	 * diy分类搜索
	 * @param productCategoryRel
	 * @return
	 */
	List<CategoryProductResult> getCategoryProductResult(ProductCategoryRel productCategoryRel);
	
	List<CategoryProductResult> findRecommendResult(ProductCategoryRel productCategoryRel);
	
	int findRecommendResultCount(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findRecommendCategoryProductResultV2(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findCustomizedCategoryProductResultV2(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findCustomizedCategoryProductResultV3(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findRecommendCategoryProductResultV3(ProductCategoryRel productCategoryRel);

}
