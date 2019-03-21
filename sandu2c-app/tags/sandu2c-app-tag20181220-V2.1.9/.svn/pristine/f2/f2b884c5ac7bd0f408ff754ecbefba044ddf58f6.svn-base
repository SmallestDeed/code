package com.sandu.product.dao;

import com.sandu.product.model.StructureProduct;
import com.sandu.product.model.StructureProductSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: StructureProductMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品模块-结构表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-12-02 15:32:05
 */
@Repository
public interface StructureProductMapper {
    int insertSelective(StructureProduct record);

    int updateByPrimaryKeySelective(StructureProduct record);

    int deleteByPrimaryKey(Integer id);

    StructureProduct selectByPrimaryKey(Integer id);

    int selectCount(StructureProductSearch structureProductSearch);

    List<StructureProduct> selectPaginatedList(StructureProductSearch structureProductSearch);

    List<StructureProduct> selectList(StructureProduct structureProduct);

    List<StructureProduct> getStructureObject(StructureProductSearch structureProductSearch);

    List<StructureProduct> getStructuresByRecommendedPlanId(@Param("recommendedPlanId") Integer recommendedPlanId);
}
