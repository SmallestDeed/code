package com.nork.customerservice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.customerservice.model.SysFaq;
import com.nork.customerservice.model.search.SysFaqSearch;

/**   
 * @Title: SysFaqMapper.java 
 * @Package com.nork.customerservice.dao
 * @Description:客服中心-常见问题Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:35:55
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysFaqMapper {
    int insertSelective(SysFaq record);

    int updateByPrimaryKeySelective(SysFaq record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysFaq selectByPrimaryKey(Integer id);
    
    int selectCount(SysFaqSearch sysFaqSearch);
    
	List<SysFaq> selectPaginatedList(
			SysFaqSearch sysFaqSearch);
			
    List<SysFaq> selectList(SysFaq sysFaq);
    
	/**
	 * 其他
	 * 
	 */
}
