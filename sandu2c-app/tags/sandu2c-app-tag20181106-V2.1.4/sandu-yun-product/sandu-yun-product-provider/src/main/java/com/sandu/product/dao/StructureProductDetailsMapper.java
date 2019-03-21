package com.sandu.product.dao;

import com.sandu.product.model.StructureProductDetails;
import com.sandu.product.model.StructureProductDetailsSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: StructureProductDetailsMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品模块-结构明细表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-12-02 15:42:46
 */
@Repository
public interface StructureProductDetailsMapper {

    List<StructureProductDetails> selectPaginatedList(StructureProductDetailsSearch structureProductDetailsSearch);
}
