package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.BaseProduct;
import com.nork.product.model.StructureProductDetails;
import com.nork.product.model.search.StructureProductDetailsSearch;

/**   
 * @Title: StructureProductDetailsMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-结构明细表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:42:46
 * @version V1.0   
 */
@Repository
@Transactional
public interface StructureProductDetailsMapper {
    int insertSelective(StructureProductDetails record);

    int updateByPrimaryKeySelective(StructureProductDetails record);
  
    int deleteByPrimaryKey(Integer id);
        
    StructureProductDetails selectByPrimaryKey(Integer id);
    
    int selectCount(StructureProductDetailsSearch structureProductDetailsSearch);
    
	List<StructureProductDetails> selectPaginatedList(
			StructureProductDetailsSearch structureProductDetailsSearch);
			
    List<StructureProductDetails> selectList(StructureProductDetails structureProductDetails);

	void deleteByStructureId(Integer structureId);
	
	/**
	 * 批量删除
	 * */
	int deleteBatch(List<Integer> list);
}
