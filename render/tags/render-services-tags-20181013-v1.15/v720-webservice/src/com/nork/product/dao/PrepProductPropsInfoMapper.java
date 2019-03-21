package com.nork.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.PrepProductPropsInfo;
import com.nork.product.model.search.PrepProductPropsInfoSearch;

/**   
 * @Title: PrepProductPropsInfoMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-预处理表(属性匹配)Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-02-25 14:55:01
 * @version V1.0   
 */
@Repository
@Transactional
public interface PrepProductPropsInfoMapper {
    int insertSelective(PrepProductPropsInfo record);

    int updateByPrimaryKeySelective(PrepProductPropsInfo record);
  
    int deleteByPrimaryKey(Integer id);
        
    PrepProductPropsInfo selectByPrimaryKey(Integer id);
    
    int selectCount(PrepProductPropsInfoSearch prepProductPropsInfoSearch);
    
	List<PrepProductPropsInfo> selectPaginatedList(
			PrepProductPropsInfoSearch prepProductPropsInfoSearch);
			
    List<PrepProductPropsInfo> selectList(PrepProductPropsInfo prepProductPropsInfo);
    
    String getThirdStageCodeByProductId(@Param("productId") Integer productId);
    
    void deleteByProductId(@Param("productId") Integer productId);
    
    void deleteByMatchProductId(@Param("productId") Integer productId);
    
    /**
     * 批量保存
     * @param list
     */
	void batchSave(@Param("list") List<PrepProductPropsInfo> list);

	void deleteByProductIdList(@Param("productIdList") List<Integer> productIdList);

	void deleteByMatchProductIdList(@Param("productIdList") List<Integer> productIdList);

	void deleteByIdList(@Param("idList") List<Integer> idList);

	List<Integer> getIdListByProductIdList(@Param("productIdList") List<Integer> productIdList);

	List<Integer> getIdListByMatchProductIdList(@Param("productIdList") List<Integer> productIdList);
    
}
