package com.nork.customerservice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.customerservice.model.SysFeedback;
import com.nork.customerservice.model.search.SysFeedbackSearch;

/**   
 * @Title: SysFeedbackMapper.java 
 * @Package com.nork.customerservice.dao
 * @Description:客服中心-问题反馈Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-04-29 15:34:27
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysFeedbackMapper {
    int insertSelective(SysFeedback record);

    int updateByPrimaryKeySelective(SysFeedback record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysFeedback selectByPrimaryKey(Integer id);
    
    int selectCount(SysFeedbackSearch sysFeedbackSearch);
    
	List<SysFeedback> selectPaginatedList(
			SysFeedbackSearch sysFeedbackSearch);
			
    List<SysFeedback> selectList(SysFeedback sysFeedback);

	void updateIsReadFeedBack(Integer userId);
    
	/**
	 * 其他
	 * 
	 */
}
