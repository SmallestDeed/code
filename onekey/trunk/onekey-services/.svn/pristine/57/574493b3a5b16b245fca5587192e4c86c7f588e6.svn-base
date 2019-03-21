package com.nork.onekeydesign.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.TempletPlanOperationHistory;
import com.nork.onekeydesign.model.search.TempletPlanOperationHistorySearch;

/**   
 * 推荐方案使用历史表Mapper  
 */
@Repository
@Transactional
public interface TempletPlanOperationHistoryMapper {
    int insertSelective(TempletPlanOperationHistory record);

    int updateByPrimaryKeySelective(TempletPlanOperationHistory record);
  
    int deleteByPrimaryKey(Integer id);
        
    TempletPlanOperationHistory selectByPrimaryKey(Integer id);
    
    int selectCount(TempletPlanOperationHistorySearch templetPlanOperationHistorySearch);
    
	List<TempletPlanOperationHistory> selectPaginatedList(
			TempletPlanOperationHistorySearch templetPlanOperationHistorySearch);
			
    List<TempletPlanOperationHistory> selectList(TempletPlanOperationHistory templetPlanOperationHistory);
    
	/**
	 * 其他
	 * 
	 */
}
