package com.nork.system.dao;

import com.nork.system.model.BasePlatform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
			
    List<BasePlatform> selectList(BasePlatform basePlatform);

    int deleteById(@Param("id") Integer id);

    BasePlatform getByPlatformCode(@Param("platformCode") String platformCode);

    List<Integer> groupPlatformWithBussinessType(@Param("list") List<Integer> usedPlatformIds, @Param("bussinessType") String bussinessType);

	BasePlatform selectPlatformInfoByPlatformCode(String platformCode);

    /**
     * 根据平台类型获取
     * @param platformBussinessType
     * @return
     */
    List<Integer> getIdsbyBussinessType(@Param("platformBussinessType") String platformBussinessType);
    /**
	 * 其他
	 * 
	 */
}
