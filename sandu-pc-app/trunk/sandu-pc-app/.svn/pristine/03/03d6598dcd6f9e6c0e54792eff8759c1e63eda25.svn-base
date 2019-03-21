package com.sandu.product.dao;

import com.sandu.product.model.CompanyShop;
import com.sandu.product.model.input.CompanyShopSearch;
import com.sandu.product.model.output.CompanyShopVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**   
 * @Title: CompanyShopMapper.java 
 * @Package com.sandu.product.dao
 * @Description:企业商铺-企业店铺Mapper
 * @version V1.0
 */
@Repository
@Transactional
public interface CompanyShopMapper {
    int insertSelective(CompanyShop record);

    int updateByPrimaryKeySelective(CompanyShop record);
  
    int deleteByPrimaryKey(Integer id);
        
    CompanyShop selectByPrimaryKey(Integer id);
    
    List<CompanyShop> selectList(CompanyShop companyShop);

    /**
     * 根据企业Id更新店铺分类
     *
     * @param companyShop
     * @return
     */
    int updateCategoryByCompanyId(CompanyShop companyShop);

    /**
     * 通过企业ID查询店铺主键列表
     * @param companyId
     * @return
     */
    List<Integer> findIdListByCompanyId(Integer companyId);

    /**
     * add by WangHL
     * 通过用户ID查询属于用户的店铺列表
     * @param userId 用户Id
     * @return list
     */
    List<CompanyShop> getShopListByUserId(@Param("userId") Long userId);

    /**
     * add by WangHL
     * 通过用户Id删除属于用户的店铺
     * @param userId 用户Id
     * @return
     */
    Integer deleteShopByUserId(@Param("userId") Long userId, @Param("loginUserName") String loginUserName);

    /**
     * 通过企业Id查询企业店铺
     * @param companyId 企业Id
     * @return list
     */
    List<CompanyShop> getShopListByCompanyId(@Param("companyId") Long companyId);

    /**
     * 通过企业Id删除企业店铺
     * @param companyId 企业Id
     * @return int
     */
    int deleteShopByCompanyId(@Param("companyId") Long companyId, @Param("loginUserName") String loginUserName);

    /**
     * 通过企业id获取品牌馆
     * 如果有多个就获取最早创建的
     * @param companyId
     * @return
     */
    CompanyShop getCompanyShopByCompanyId(Integer companyId);

    /**
     * 查询店铺总数
     * @param search
     * @return
     */
    int getCountBySearch(CompanyShopSearch search);

    /**
     * 按条件查询店铺信息
     * @param search
     * @return
     */
    List<CompanyShopVo> selectListBySearch(CompanyShopSearch search);
}
