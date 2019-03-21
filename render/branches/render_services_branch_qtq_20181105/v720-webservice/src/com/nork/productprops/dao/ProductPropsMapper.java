package com.nork.productprops.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.search.ProductPropsSearch;

/**   
 * @Title: ProductPropsMapper.java 
 * @Package com.nork.productprops.dao
 * @Description:产品属性-产品属性表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 10:40:03
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProductPropsMapper {
    int insertSelective(ProductProps record);

    int updateByPrimaryKeySelective(ProductProps record);
  
    int deleteByPrimaryKey(Integer id);
        
    ProductProps selectByPrimaryKey(Integer id);
    
    int selectCount(ProductPropsSearch productPropsSearch);
    
	List<ProductProps> selectPaginatedList(
			ProductPropsSearch productPropsSearch);
			
    List<ProductProps> selectList(ProductProps productProps);
    
	/**
	 * 其他
	 * 
	 */

    /**
     * 异步加载产品属性
     * @param pid
     * @return
     */
    List<ProductProps> asyncLoadTree(Integer pid);

	List<ProductProps> getAttributeKeyAndValueByProductId(@Param("productId") Integer productId);

	List<String> getStructureProductPropCodeByProductId(@Param("productId") Integer productId, @Param("productAttrCode") String productAttrCode);

	List<String> getStructureProductPropValueByProductId(@Param("productId") Integer productId, @Param("productAttrCode") String productAttrCode);
	
}
