package com.sandu.service.brand.dao;

import com.github.pagehelper.PageInfo;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.model.bo.BrandQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 * @date 2017/12/15
 */
@Repository
public interface BrandDao {

    /**
     * 添加
     *
     * @param brand record
     * @return 1, 0
     */
    Integer saveBrand(Brand brand);

    /**
     * 修改
     *
     * @param brand record
     * @return 1, 0
     */
    int updateBrand(Brand brand);

    /**
     * 删除
     *
     * @param id id
     * @return 1, 0
     */
    int deleteBrandById(@Param("id") long id);

    /**
     * 获取单条
     *
     * @param id id
     * @return record
     */
    Brand getBrandById(@Param("id") long id);

    /**
     * 查询所有
     *
     * @return all
     */
    List<Brand> findAllBrand();

    /**
     * 条件搜索查询
     *
     * @param brandCode    品牌编码
     * @param brandName    品牌名称
     * @param brandRefered 品牌
     * @param companyId    公司
     * @param orders       排序
     * @param order        排序
     * @param orderWay     排序方式
     * @return 查询结果
     */
    List<Brand> queryBrandByBrandInfo(@Param("brandCode") String brandCode,
                                      @Param("brandName") String brandName,
                                      @Param("brandRefered") String brandRefered,
                                      @Param("companyId") Integer companyId,
                                      @Param("orders") String orders,
                                      @Param("order") String order,
                                      @Param("orderWay") String orderWay
    );

    /**
     * 查询所有品牌的名称和id
     *
     * @return record
     */
    List<Brand> findAllBrandNameAndId();

    /**
     * 通过风格获取品牌
     *
     * @param brandStyleId 风格
     * @return 品牌
     */
    List<Brand> queryBrandByBrandStyleId(@Param("brandStyleId") int brandStyleId);

    /**
     * 通过公司 获取品牌的最大编码
     *
     * @param companyId 公司
     * @return record
     */
    String queryMaxBrandCodeByCompanyId(@Param("companyId") int companyId);

    /**
     * 删除公司的品牌
     *
     * @param companyId 公司
     * @return 1, 0
     */
    int deleteBrandCompanyId(long companyId);

    /**
     * 通过code获取品牌
     *
     * @param brandCode 编码
     * @return record
     */
    Brand getBrandByBrandCode(@Param("brandCode") String brandCode);

    /**
     * 通过id集获取品牌
     *
     * @param brandIds id集
     * @return record
     */
    List<Brand> getBrandNameByIds(@Param("brandIds") List<Integer> brandIds);

    /**
     * 获取公司的品牌
     *
     * @param companyId 公司
     * @return record
     */
    List<Brand> getBrandByCompanyId(Integer companyId);

    /**
     * 获取同行的brandIds
     *
     * @param companyId gongsi
     * @return 同行brand id
     */
    List<Integer> listPeerBrandIds(Integer companyId);

    /**
     * 条件查询
     * @param brandQuery
     * @return
     */
    List<Brand> queryByOption(BrandQuery brandQuery);

    /**
     * 查询最大code
     * @param brand
     * @return
     */
    String createBrandCode(Brand brand);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Brand record);

    String getBrandNamesByIds(@Param("brandIds") List<String> brandIds);
}
