package com.sandu.panorama.dao;

import java.util.List;

import com.sandu.cityunion.model.UnionGroupDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: UnionGroupDetailsMapper.java 
 * @Package
 * @Description: 720制作-联盟组明细表Mapper
 */
@Repository
@Transactional
public interface UnionGroupDetailsMapper {
    int insertSelective(UnionGroupDetails record);

    int updateByPrimaryKeySelective(UnionGroupDetails record);
  
    int deleteByPrimaryKey(Integer id);
    
    int deleteById(Integer id);
    
    int deleteByGroupId(Integer id);
        
    UnionGroupDetails selectByPrimaryKey(Integer id);
    
//    int selectCount(UnionGroupDetailsSearch unionGroupDetailsSearch);
    
//	List<UnionGroupDetails> selectPaginatedList(
//            UnionGroupDetailsSearch unionGroupDetailsSearch);

	List<UnionGroupDetails> getListByGroupId(Integer groupId);
			
    List<UnionGroupDetails> selectList(UnionGroupDetails unionGroupDetails);
    
    int batchInsertDataList(@Param("list") List<UnionGroupDetails> list);

    /**
     * 根据Id集合批量删除联盟组明细记录
     * @param idList
     * @return
     */
    int deletedByIdList(@Param(value="idList") List<Integer> idList);
}
