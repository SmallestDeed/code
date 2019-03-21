package com.sandu.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.platform.BasePlatform;
import com.sandu.platform.search.BasePlatformSearch;


/**   
 * @Title: BasePlatformMapper.java 
 * @Package com.nork.platform.dao
 * @Description:基础-平台表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
@Repository
@Transactional
public interface BasePlatformMapper {
    int insertSelective(BasePlatform record);

    int updateByPrimaryKeySelective(BasePlatform record);
  
    int deleteByPrimaryKey(Integer id);
        
    BasePlatform selectByPrimaryKey(Integer id);
    
    int selectCount(BasePlatformSearch basePlatformSearch);
    
	List<BasePlatform> selectPaginatedList(
            BasePlatformSearch basePlatformSearch);
			
    List<BasePlatform> selectList(BasePlatform basePlatform);

    int deleteById(@Param("id") Integer id);

    BasePlatform getByPlatformCode(@Param("platformCode") String platformCode);

    List<Integer> groupPlatformWithBussinessType(@Param("list") List<Integer> usedPlatformIds,@Param("bussinessType") String bussinessType);

	BasePlatform selectPlatformInfoByPlatformCode(String platformCode);

    /**
	 * 其他
	 * 
	 */
}
