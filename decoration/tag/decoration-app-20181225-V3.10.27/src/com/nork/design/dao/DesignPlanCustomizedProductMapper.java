package com.nork.design.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanCustomizedProduct;
import com.nork.design.model.search.DesignPlanCustomizedProductSearch;

/**   
 * @Title: DesignPlanCustomizedProductMapper.java 
 * @Package com.nork.design.dao
 * @Description:设计方案-设计方案定制产品表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-08-28 11:04:09
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanCustomizedProductMapper {
    int insertSelective(DesignPlanCustomizedProduct record);

    int updateByPrimaryKeySelective(DesignPlanCustomizedProduct record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignPlanCustomizedProduct selectByPrimaryKey(Integer id);
    
    int selectCount(DesignPlanCustomizedProductSearch designPlanCustomizedProductSearch);
    
	List<DesignPlanCustomizedProduct> selectPaginatedList(
            DesignPlanCustomizedProductSearch designPlanCustomizedProductSearch);
			
    List<DesignPlanCustomizedProduct> selectList(DesignPlanCustomizedProduct designPlanCustomizedProduct);
    
	/**
	 * 其他
	 * 
	 */
}
