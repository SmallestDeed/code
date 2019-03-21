package com.nork.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProCategorySearch;

/**   
 * @Title: ProCategoryMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-产品类目Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 14:46:36
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProCategoryMapper {
    int insertSelective(ProCategory record);

    int updateByPrimaryKeySelective(ProCategory record);
  
    int deleteByPrimaryKey(Integer id);
        
    ProCategory selectByPrimaryKey(Integer id);
    
    int selectCount(ProCategorySearch proCategorySearch);
    
	List<ProCategory> selectPaginatedList(
			ProCategorySearch proCategorySearch);
			
    List<ProCategory> selectList(ProCategory proCategory);
    
	/**
	 * 其他
	 * 
	 */
    
    /**
	 * 根据分类Code查询
	 * @return
	 */
	List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRelSearch);

    /**
     * 根据分类Code查询(前台使用)
     * @return
     */
    List<CategoryProductResult> getProCategoryListByCodeForWeb(ProductCategoryRel productCategoryRelSearch);

    /**
     * 根据类目ID查询子类目
     * @param pid
     * @return
     */
    List<ProCategory> asyncLoadTree(Integer pid);

    /**
     * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
     * @return
     */
    int recursionUpdate(Map<String,Object> params);

	int getProCategoryListCountByCode(
			ProductCategoryRel productCategoryRelSearch);

	ProCategory findOneByCode(@Param("code") String code);

	List<ProCategory> selectListV2(ProCategory proCategory);
	
	/**
	 * 根据可见范围查询到所有可见的分类编码
	 * @param visibilityRangeIdList
	 * @return
	 */
	List<String> getCodeListByIdList(
			@Param("visibilityRangeIdList") List<Integer> visibilityRangeIdList);
	
}
