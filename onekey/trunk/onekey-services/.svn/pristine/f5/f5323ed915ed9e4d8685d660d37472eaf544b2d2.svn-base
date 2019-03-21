package com.nork.onekeydesign.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.TempletProductIndexInfo;
import com.nork.onekeydesign.model.search.TempletProductIndexInfoSearch;

/**   
 * @Title: TempletProductIndexInfoMapper.java 
 * @Package com.nork.onekeydesign.dao
 * @Description:设计模块-同分类产品序号表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-06-13 16:19:25
 * @version V1.0   
 */
@Repository
@Transactional
public interface TempletProductIndexInfoMapper {
    int insertSelective(TempletProductIndexInfo record);

    int updateByPrimaryKeySelective(TempletProductIndexInfo record);
  
    int deleteByPrimaryKey(Integer id);
        
    TempletProductIndexInfo selectByPrimaryKey(Integer id);
    
    int selectCount(TempletProductIndexInfoSearch templetProductIndexInfoSearch);
    
	List<TempletProductIndexInfo> selectPaginatedList(
			TempletProductIndexInfoSearch templetProductIndexInfoSearch);
			
    List<TempletProductIndexInfo> selectList(TempletProductIndexInfo templetProductIndexInfo);

    List<TempletProductIndexInfo> findIndexInfoByCode(@Param("designCode") String designCode);

    int updateIndexInfoByIds(TempletProductIndexInfo templetProductIndexInfo);
	/**
	 * 其他
	 * 
	 */
}
