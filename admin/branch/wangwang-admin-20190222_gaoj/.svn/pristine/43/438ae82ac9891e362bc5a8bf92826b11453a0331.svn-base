package com.sandu.api.brand.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.model.bo.BrandQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sandu
 * @date 2017/12/15
 */

public interface BrandService {
    /**
     * 新增数据
     *
     * @param brand
     * @return int
     */
     int saveBrand(Brand brand);

    /**
     * 更新数据
     *
     * @param brand
     * @return int
     */
     int updateBrand(Brand brand);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
     int deleteBrandById(long id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return Brand
     */
     Brand getBrandById(long id);

    /**
     * 根据品牌编码获取品牌详情
     * @param brandCode
     * @return
     */
     Brand getBrandByBrandCode(String brandCode);

    /**
     * 所有数据的ID和品牌名称
     *
     * @return List<Brand>
     */
     List<Brand> findAllBrandNameAndId();

    /**
     * @param page  页码数
     * @param limit  页面条数
     * 无条件查询
     * @return 品牌集合
     */
     List<Brand> findAllBrand(int page, int limit);



    /**
     * 过滤分页查询
     * @param brandCode
     * @param brandName
     * @param brandRefered
     * @param companyId
     * @param orders
     * @param order
     * @param orderWay
     * @return
     */
     List<Brand> queryBrandByBrandInfo(String brandCode, String brandName, String brandRefered,
                                             Integer companyId, String orders, String order, String orderWay,
                                             int page, int limit);

    /**
     * 根据企业ID查询最大的品牌编码
     * @param companyId
     * @return
     */
     String queryMaxBrandCodeByCompanyId(int companyId);

    /**
     * 根据品牌风格查询品牌(只查询ID / log)
     * @param brandStyleId
     * @return
     */
     List<Brand> queryBrandByBrandStyleId(int brandStyleId);

    /**
     * 根据企业ID删除品牌(逻辑删除)
     * @param companyId
     * @return
     */
    int deleteBrandCompanyId(long companyId);

    /**
     * 根据品牌ID获取品牌ID-名称(K-V)
     * @param brandIds
     * @return
     */
    Map<Integer,String> getBrandNameByIds(List<Integer> brandIds);

    /**
     * 获取企业下的所有品牌
     * @param companyId
     * @return
     */
    List<Brand> getBrandByCompanyId(Integer companyId);

    /**
     * 获取同行的brandIds
     * @param companyId gongsi
     * @return 同行brand id
     */
    List<Integer> listPeerBrandIds(Integer companyId);

    /**
     * 条件分页查询
     * @param brandQuery
     * @return
     */
    PageInfo<Brand> pageQueryBrand(BrandQuery brandQuery,Integer pageNo,Integer pageSize);

    /**
     * 条件查询
     * @param brandQuery
     * @return
     */
    List<Brand> queryBrand(BrandQuery brandQuery);

    /**
     *    查询最大序列
     *
     * @param  brand
     * @return   List<BaseBrand>
     */
    public String createBrandCode(Brand brand);

    /**
     * 根据brandIds查询对应的品牌名称
     * @param brandIds
     * @return
     */
    String getBrandNamesByIds(List<String> brandIds);

    /**
     * 品牌id，名称集合
     * @param brandIds
     * @return
     */
    Map<Long,String> idAndNameMap(List<String> brandIds);
}
