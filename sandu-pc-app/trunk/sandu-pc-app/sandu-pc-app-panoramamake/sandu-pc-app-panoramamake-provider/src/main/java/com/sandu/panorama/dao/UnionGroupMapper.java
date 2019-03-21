package com.sandu.panorama.dao;

import com.sandu.cityunion.model.UnionGroup;
import com.sandu.panorama.model.input.UnionGroupSearch;
import com.sandu.panorama.model.output.UnionGroupVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: UnionGroupMapper.java 
 * @Package
 * @Description:同城联盟-联盟分组表Mapper
 * @createAuthor
 * @CreateDate
 */
@Repository
public interface UnionGroupMapper {
    int insertSelective(UnionGroup record);

    int updateByPrimaryKeySelective(UnionGroup record);
    
    int deleteById(Integer id);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionGroup selectByPrimaryKey(Integer id);

    int selectCount(UnionGroupSearch unionGroupSearch);
    
/*	List<UnionGroup> selectPaginatedList(
            UnionGroupSearch unionGroupSearch);*/
	
	List<UnionGroup> getListByUserId(Integer userId);
			
    List<UnionGroup> selectList(UnionGroup unionGroup);

    List<UnionGroupVo> selectUnionGroupVoListBySearch(UnionGroupSearch unionGroupSearch);
	/**
	 * 其他
	 * 
	 */
}
