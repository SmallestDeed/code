package com.nork.designconfig.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.model.search.DesignRulesSearch;

/**   
 * @Title: DesignRulesMapper.java 
 * @Package com.nork.designconfig.dao
 * @Description:设计配置-设计规则Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 19:56:47
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignRulesMapper {
    int insertSelective(DesignRules record);

    int updateByPrimaryKeySelective(DesignRules record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignRules selectByPrimaryKey(Integer id);
    
    int selectCount(DesignRulesSearch designRulesSearch);
    
	List<DesignRules> selectPaginatedList(
			DesignRulesSearch designRulesSearch);
			
    List<DesignRules> selectList(DesignRules designRules);
    
	/**
	 * 其他
	 * 
	 */
}
