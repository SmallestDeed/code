package com.sandu.product.service;

import com.sandu.product.model.SearchStructureProductResult;
import com.sandu.product.model.StructureProduct;
import com.sandu.product.model.StructureProductSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: StructureProductService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-结构表Service
 * @createAuthor pandajun
 * @CreateDate 2016-12-02 15:32:05
 */
public interface StructureProductService {
    /**
     * 新增数据
     *
     * @param structureProduct
     * @return int
     */
    int add(StructureProduct structureProduct);

    /**
     * 更新数据
     *
     * @param structureProduct
     * @return int
     */
    int update(StructureProduct structureProduct);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return StructureProduct
     */
    StructureProduct get(Integer id);

    /**
     * 所有数据
     *
     * @param structureProduct
     * @return List<StructureProduct>
     */
    List<StructureProduct> getList(StructureProduct structureProduct);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(StructureProductSearch structureProductSearch);

    /**
     * 分页获取数据
     *
     * @return List<StructureProduct>
     */
    List<StructureProduct> getPaginatedList(StructureProductSearch structureProductSearch);


    SearchStructureProductResult getSearchStructureProductResult(StructureProduct structureProduct, String templetStructureCode);


    List<StructureProduct> getStructureObject(StructureProductSearch structureProductSearch);

    /**
     * 根据结构编码查询结构列表
     * 查询该推荐设计方案非背景墙的结构
     *
     * @param recommendedPlanId 推荐方案id
     * @return
     * @author huangsongbo
     */
    List<StructureProduct> getStructuresByRecommendedPlanId(Integer recommendedPlanId);

}
