package com.sandu.product.service;

import com.sandu.product.model.StructureProductDetails;
import com.sandu.product.model.StructureProductDetailsSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: StructureProductDetailsService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-结构明细表Service
 * @createAuthor pandajun
 * @CreateDate 2016-12-02 15:42:46
 */
public interface StructureProductDetailsService {
    /**
     * 通过structureId查找StructureProductDetails
     *
     * @return
     * @author huangsongbo
     */
    List<StructureProductDetails> findAllByStructureId(Integer structureId);

    /**
     * 分页获取数据
     *
     * @return List<StructureProductDetails>
     */
    List<StructureProductDetails> getPaginatedList(StructureProductDetailsSearch structureProductDetailstSearch);

}
