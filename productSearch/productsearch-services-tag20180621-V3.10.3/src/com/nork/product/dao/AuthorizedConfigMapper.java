package com.nork.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.search.AuthorizedConfigSearch;

/**   
 * @Title: AuthorizedConfigMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品-授权配置Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:07:34
 * @version V1.0   
 */
@Repository
@Transactional
public interface AuthorizedConfigMapper {
    int insertSelective(AuthorizedConfig record);

    int updateByPrimaryKeySelective(AuthorizedConfig record);
  
    int deleteByPrimaryKey(Integer id);
        
    AuthorizedConfig selectByPrimaryKey(Integer id);
    
    int selectCount(AuthorizedConfigSearch authorizedConfigSearch);

	List<AuthorizedConfig> selectPaginatedList(
			AuthorizedConfigSearch authorizedConfigSearch);
			
    List<AuthorizedConfig> selectList(AuthorizedConfig authorizedConfig);
    
    List<AuthorizedConfig> selectListByMobile(AuthorizedConfig authorizedConfig);
    
    List<AuthorizedConfig> selectBigNum(AuthorizedConfig authorizedConfig);

    List<AuthorizedConfig> selectPastDueAuthorizedList();
    
    List<AuthorizedConfig> selectPastDueAuthorizedLists();
    
    int selectNotOutAuthorizedCount(Integer userId);

	int findCountByCompanyTypeAndUserId(@Param("value") Integer value, @Param("userId") Integer userId);
	
	int updateValidStateByIds(@Param("validState") Integer validState, @Param("updateIds") List <Integer>updateIds);
	/**
	 * 获取 该用户为厂商的所有 授权码数据
	 * @param authorizedConfig
	 * @return
	 */
	List<AuthorizedConfig> vendorAuthorizedConfigList(AuthorizedConfig authorizedConfig);
	/**
	 * 通过品牌集合 获取 所有经销商
	 * @param brandIds
	 * @return
	 */
	List<AuthorizedConfig> getDealersAuthorizedConfigByBrandId(AuthorizedConfig authorizedConfig);
	
}
