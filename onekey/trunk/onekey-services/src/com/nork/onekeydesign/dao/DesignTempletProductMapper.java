package com.nork.onekeydesign.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.DesignTempletProduct;
import com.nork.onekeydesign.model.DesignTempletProductResult;
import com.nork.onekeydesign.model.search.DesignTempletProductSearch;

/**   
 * @Title: DesignTempletProductMapper.java 
 * @Package com.nork.onekeydesign.dao
 * @Description:设计模块-方案产品表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-07-07 11:04:44
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignTempletProductMapper {
    int insertSelective(DesignTempletProduct record);

    int updateByPrimaryKeySelective(DesignTempletProduct record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignTempletProduct selectByPrimaryKey(Integer id);
    
    int selectCount(DesignTempletProductSearch designTempletProductSearch);
    
	List<DesignTempletProduct> selectPaginatedList(
			DesignTempletProductSearch designTempletProductSearch);
			
    List<DesignTempletProduct> selectList(DesignTempletProduct designTempletProduct);

    List<DesignTempletProduct> byTempletIdMainProduct(DesignTempletProduct designTempletProduct);

    int deleteByDesignTempletCode(String designCode);

    int deleteByDesignId(Integer designTempletId);

	/**
	 * 得到design_templet_product表中的产品idList(去重复)
	 * @author huangsongbo
	 * @return
	 */
	List<Integer> getDistinctProductIdList();

    List<DesignTempletProductResult> byTempletIdProductList(DesignTempletProduct designTempletProduct);

    int updateSameProductTypeIndex(DesignTempletProduct designTempletProduct);

    List<DesignTempletProduct> getStructureProductByDesignId(@Param("designTempletId") Integer designTempletId);

	List<DesignTempletProduct> getListByTempletId(@Param("designTempletId") Integer designTempletId);
	
    int updateDesignTemplateProduct(DesignTempletProduct designTempletProduct);

	List<String> getWallOrientationList(@Param("templetId") Integer templetId);

	List<DesignTempletProduct> getBeijingProductInfo(@Param("designTempletId") Integer designTempletId);

	int getCountByPlanIdAndProductTypeValue(
		@Param("designTempletId") Integer designTempletId,
		@Param("bigTypeValue") Integer bigTypeValue, 
		@Param("smallTypeValue") Integer smallTypeValue);

	int getCountByTempletIdAndProductTypeValueList(
			@Param("designTempletId") Integer designTempletId,
			@Param("bigTypeValue") Integer bigTypeValue,
			@Param("smallTypeValueList") List<Integer> smallTypeValueList);

}
