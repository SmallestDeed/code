package com.nork.system.dao;

import com.nork.system.model.UserSpellingflowerCollet;
import com.nork.system.model.search.UserSpellingflowerColletSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**   
 * @Title: UserSpellingflowerColletMapper.java 
 * @Package com.nork.system.dao
 * @Description:拼花-我的瓷砖拼花收藏Mapper
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:38:44
 * @version V1.0   
 */
@Repository
@Transactional
public interface UserSpellingflowerColletMapper {
    int insertSelective(UserSpellingflowerCollet record);

    int updateByPrimaryKeySelective(UserSpellingflowerCollet record);
  
    int deleteByPrimaryKey(Integer id);
        
    UserSpellingflowerCollet selectByPrimaryKey(Integer id);
    
    int selectCount(UserSpellingflowerColletSearch userSpellingflowerColletSearch);
    
	List<UserSpellingflowerCollet> selectPaginatedList(
            UserSpellingflowerColletSearch userSpellingflowerColletSearch);
			
    List<UserSpellingflowerCollet> selectList(UserSpellingflowerCollet userSpellingflowerCollet);


    /**
     * 根据用户id查询所绑定的品牌id
     * @param userId 用户id
     * @return
     */
    Integer selectBrandIdByUserId(@Param("userId")Integer userId);
    /**
     * 查询品牌所在企业所经营的产品大分类
     * @param brandId 品牌id
     * @return 产品小分类valuekey值
     */
    String getCompanyValueKey(@Param("brandId") Integer brandId);

    /**
     * 查询该品牌所经营的大小分类所过滤出的设计方案中产品中不是该品牌的产品编码
     * @param productIdList 产品ids
     * @param valuekeyList 产品小分类valuekey值
     * @param brandId 品牌id
     * @return 产品编码 集合
     */
    List<String> getProductCode(@Param("productIdList")Integer[] productIdList,@Param("valuekeyList")String[] valuekeyList,@Param("brandId")Integer brandId);
}
