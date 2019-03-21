package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;

/**   
 * @Title: BaseWaterjetTemplateMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-水刀模版Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-06-04 14:30:27
 * @version V1.0   
 */
@Repository
public interface BaseWaterjetTemplateMapper {
	
    int insertSelective(BaseWaterjetTemplate record);

    int updateByPrimaryKeySelective(BaseWaterjetTemplate record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseWaterjetTemplate selectByPrimaryKey(Integer id);
    
    int selectCount(BaseWaterjetTemplateSearch baseWaterjetTemplateSearch);
    
	List<BaseWaterjetTemplate> selectPaginatedList(
			BaseWaterjetTemplateSearch baseWaterjetTemplateSearch);
			
    List<BaseWaterjetTemplate> selectList(BaseWaterjetTemplate baseWaterjetTemplate);

	List<BaseWaterjetTemplate> findAllBySearch(BaseWaterjetTemplateSearch search);

	int getCountBySearch(BaseWaterjetTemplateSearch search);

}
