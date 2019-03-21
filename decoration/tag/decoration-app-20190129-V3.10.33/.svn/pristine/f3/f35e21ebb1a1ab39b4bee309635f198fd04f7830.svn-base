package com.nork.design.dao;

import java.util.List;

import com.nork.design.model.DesginPlanSpellingflowerRecord;
import com.nork.design.model.search.DesginPlanSpellingflowerRecordSearch;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**   
 * @Title: DesginPlanSpellingflowerRecordMapper.java 
 * @Package com.nork.spellingflower.dao
 * @Description:拼花-设计方案瓷砖拼花记录Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-11-23 14:41:02
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesginPlanSpellingflowerRecordMapper {
    int insertSelective(DesginPlanSpellingflowerRecord record);

    int updateByPrimaryKeySelective(DesginPlanSpellingflowerRecord record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesginPlanSpellingflowerRecord selectByPrimaryKey(Integer id);
    
    int selectCount(DesginPlanSpellingflowerRecordSearch desginPlanSpellingflowerRecordSearch);
    
	List<DesginPlanSpellingflowerRecord> selectPaginatedList(
            DesginPlanSpellingflowerRecordSearch desginPlanSpellingflowerRecordSearch);
			
    List<DesginPlanSpellingflowerRecord> selectList(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord);
    
	/**
	 * 其他
	 * 
	 */
}
