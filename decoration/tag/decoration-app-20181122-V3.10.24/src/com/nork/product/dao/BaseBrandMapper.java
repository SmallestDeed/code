package com.nork.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;

/**   
 * @Title: BaseBrandMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品-品牌表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-16 10:03:47
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseBrandMapper {
    int insertSelective(BaseBrand record);

    int updateByPrimaryKeySelective(BaseBrand record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseBrand selectByPrimaryKey(Integer id);
    
    int selectCount(BaseBrandSearch baseBrandSearch);
    
	List<BaseBrand> selectPaginatedList(
			BaseBrandSearch baseBrandSearch);
			
    List<BaseBrand> selectList(BaseBrand baseBrand);

	List<BaseBrand> getListByIds(BaseBrand baseBrand);
	
	
	int findAllBrandInfoCount(BaseBrandSearch baseBrandSearch);

	List<BaseBrand> findAllBrandInfoList(BaseBrandSearch baseBrandSearch);

	List<BaseBrand> findBrandIndustryValueByUserId(@Param("userId") Integer userId);
}
