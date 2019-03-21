package com.nork.design.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanCustomizedProductOrder;
import com.nork.design.model.search.DesignPlanCustomizedProductOrderSearch;

/**   
 * @Title: DesignPlanCustomizedProductOrderMapper.java 
 * @Package com.nork.design.dao
 * @Description:设计方案-设计方案定制产品订单表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-11-26 17:46:44
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanCustomizedProductOrderMapper {
    int insertSelective(DesignPlanCustomizedProductOrder record);

    int updateByPrimaryKeySelective(DesignPlanCustomizedProductOrder record);

    int updateByPactNo(DesignPlanCustomizedProductOrder record);

    int updateByClientName(DesignPlanCustomizedProductOrder record);

    int deleteByPrimaryKey(Integer id);

    int deleteByPlanId(Integer planId);

    DesignPlanCustomizedProductOrder selectByPrimaryKey(Integer id);
    
    int selectCount(DesignPlanCustomizedProductOrderSearch designPlanCustomizedProductOrderSearch);
    
	List<DesignPlanCustomizedProductOrder> selectPaginatedList(
			DesignPlanCustomizedProductOrderSearch designPlanCustomizedProductOrderSearch);
			
    List<DesignPlanCustomizedProductOrder> selectList(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder);
    
	/**
	 * 其他
	 * 
	 */
}
