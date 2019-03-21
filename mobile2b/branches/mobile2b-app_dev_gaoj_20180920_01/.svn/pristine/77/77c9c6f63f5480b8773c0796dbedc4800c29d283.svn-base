package com.nork.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.demo.model.Exp;
import com.nork.demo.model.search.ExpSearch;

/**   
 * @Title: ExpMapper.java 
 * @Package com.nork.demo.dao
 * @Description:演示模块-参考例子Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-17 20:11:49
 * @version V1.0   
 */
@Repository
@Transactional
public interface ExpMapper {
    int insertSelective(Exp record);

    int updateByPrimaryKeySelective(Exp record);
  
    int deleteByPrimaryKey(Integer id);
        
    Exp selectByPrimaryKey(Integer id);
    
    int selectCount(ExpSearch expSearch);
    
	List<Exp> selectPaginatedList(
			ExpSearch expSearch);
			
    List<Exp> selectList(Exp exp);
    
	/**
	 * 其他
	 * 
	 */
}
