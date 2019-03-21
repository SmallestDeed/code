package com.sandu.product.dao;


import com.sandu.product.model.BaseBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: BaseBrandMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品-品牌表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-16 10:03:47
 */
@Repository
public interface BaseBrandMapper {
    int insertSelective(BaseBrand record);

    int updateByPrimaryKeySelective(BaseBrand record);

    int deleteByPrimaryKey(Integer id);

    BaseBrand selectByPrimaryKey(Integer id);

    /**
     * 查询公司所拥有的品牌
     * @param companyId 公司ID
     * @return      公司品牌LIST
     */
    List<Integer> queryBrandListByCompanyId(@Param("companyId") Integer companyId);

	List<BaseBrand> selectBrandListByCompanyId(Integer id);

    /**
     * 通过公司id获取和它品牌联盟的所有品牌集合
     * @param companyId
     * @return
     */
    List<Integer> getAllBrandIdsByCompanyId(Integer companyId);

    /**
     * 根据品牌id查询品牌名称
     * @param brandIds
     * @return
     */
    List<BaseBrand> selectBrandByIds(@Param("brandIds") List<Integer> brandIds);
}
